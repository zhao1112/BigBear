package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.FiltrateBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Master
 * @create 2018/8/21 16:30
 */
public class FiltrateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<FiltrateBean> mList;

    public FiltrateAdapter(Context mContext, List<FiltrateBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    public void setData(List<FiltrateBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }


    public String getSelectData() {
        JSONArray jsonArray = new JSONArray();
        try {
            for (int i = 0; i < mList.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                FiltrateBean filtrateBean = mList.get(i);
                jsonObject.put("attrIndex", filtrateBean.getAttrIndex());
                StringBuffer stringBuffer = new StringBuffer();
                for (int j = 0; j < filtrateBean.getOptions().size(); j++) {
                    FiltrateBean.Bean bean = filtrateBean.getOptions().get(j);
                    if (bean.isChecked()) {
                        stringBuffer.append(bean.getOptionsName()).append(",");
                    }
                }
                jsonObject.put("options", stringBuffer.toString());
                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray.toString();

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IViewHolder(View.inflate(mContext, R.layout.filtrate_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            FiltrateBean mFiltrateBean = mList.get(position);
            ((IViewHolder) holder).mTitleTextView.setText(mFiltrateBean.getAttrName());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
            ((IViewHolder) holder).mRecyclerView.setLayoutManager(gridLayoutManager);
            ((IViewHolder) holder).mRecyclerView.setAdapter(new FiltrateChildAdapter(mContext, mList.get(position).getOptions()));
        } catch (Exception e) {
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class IViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.filtrate_title)
        TextView mTitleTextView;
        @BindView(R.id.filtrate_recycler_view)
        RecyclerView mRecyclerView;

        public IViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}

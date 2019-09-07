package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.FiltrateBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Master
 * @create 2018/8/21 16:30
 */
public class FiltrateChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<FiltrateBean.Bean> mList;

    public FiltrateChildAdapter(Context mContext, List<FiltrateBean.Bean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IViewHolder(View.inflate(mContext, R.layout.filtrate_child_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            FiltrateBean.Bean mFiltrateBean = mList.get(position);
            ((IViewHolder) holder).mTitleTextView.setText(mFiltrateBean.getOptionsName());
            if (mFiltrateBean.isChecked()) {
                ((IViewHolder) holder).mTitleTextView.setBackgroundColor(Color.parseColor("#23A064"));
            } else {
                ((IViewHolder) holder).mTitleTextView.setBackgroundColor(Color.parseColor("#91e7e5e5"));
            }
            ((IViewHolder) holder).mTitleTextView.setTag(position);
            ((IViewHolder) holder).mTitleTextView.setOnClickListener(this);
        } catch (Exception e) {
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        int index = (int) v.getTag();
        mList.get(index).setChecked(!mList.get(index).isChecked());
        notifyDataSetChanged();
    }

    class IViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.child_name)
        TextView mTitleTextView;

        public IViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }




}

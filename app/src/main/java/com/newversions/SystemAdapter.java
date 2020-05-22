package com.newversions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.BCMessageData;
import com.yunqin.bearmall.bean.SystemMessageData;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Master
 * On 2019/2/19 14:50
 */
public class SystemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<SystemMessageData.DataBean.ListBean> mLists;
    private RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
            .centerCrop();

    public SystemAdapter(Context mContext, List<SystemMessageData.DataBean.ListBean> mLists) {
        this.mContext = mContext;
        this.mLists = mLists;
    }

    public void setData(List<SystemMessageData.DataBean.ListBean> list) {
        if (mLists == null) {
            mLists = new ArrayList<>();
        }

        if (list != null) {
            mLists.clear();
            mLists.addAll(list);
            notifyDataSetChanged();
        }

    }

    public void addData(List<SystemMessageData.DataBean.ListBean> list) {
        if (mLists == null) {
            mLists = new ArrayList<>();
        }

        if (list != null) {
            mLists.addAll(list);
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_message_system, parent, false);
        return new MYHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SystemMessageData.DataBean.ListBean listBean = mLists.get(position);
        MYHolder myholer = (MYHolder) holder;
        myholer.time_text.setText(listBean.getCreatedDate());
        myholer.title.setText(listBean.getTitle());
        myholer.desc.setText(listBean.getContent());

    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    class MYHolder extends RecyclerView.ViewHolder {

        TextView time_text;
        TextView title;
        TextView desc;


        public MYHolder(View itemView) {
            super(itemView);
            time_text = itemView.findViewById(R.id.time_text);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
        }
    }
}

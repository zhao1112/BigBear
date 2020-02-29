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
import com.newversions.MoneyRewardBean;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.DealMessageData;
import com.yunqin.bearmall.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Master
 * On 2019/2/19 14:50
 */
public class DeallMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<DealMessageData.DataBean.ListBean> mLists;
    private RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
            .centerCrop();

    public DeallMessageAdapter(Context mContext, List<DealMessageData.DataBean.ListBean> mLists) {
        this.mContext = mContext;
        this.mLists = mLists;
    }

    public void setData(List<DealMessageData.DataBean.ListBean> list) {
        if (mLists == null) {
            mLists = new ArrayList<>();
        }

        if (list != null) {
            mLists.clear();
            mLists.addAll(list);
            notifyDataSetChanged();
        }

    }

    public void addData(List<DealMessageData.DataBean.ListBean> list) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_message_bc, parent, false);
        return new MYHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DealMessageData.DataBean.ListBean listBean = mLists.get(position);
        Glide.with(mContext).setDefaultRequestOptions(requestOptions).load("error").into(((MYHolder) holder).bc_img);
        ((MYHolder) holder).time_text.setText(listBean.getCreatedDate());
        ((MYHolder) holder).bc_title.setText(listBean.getCaption());
        ((MYHolder) holder).bc_content.setText(listBean.getContent());
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    class MYHolder extends RecyclerView.ViewHolder {

        CircleImageView bc_img;
        TextView time_text;
        TextView bc_title;
        TextView bc_content;

        public MYHolder(View itemView) {
            super(itemView);
            bc_img = itemView.findViewById(R.id.bc_img);
            time_text = itemView.findViewById(R.id.time_text);
            bc_title = itemView.findViewById(R.id.bc_title);
            bc_content = itemView.findViewById(R.id.bc_content);
        }
    }
}

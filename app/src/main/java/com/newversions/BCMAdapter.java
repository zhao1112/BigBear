package com.newversions;

import android.content.Context;
import android.media.Image;
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
import com.yunqin.bearmall.bean.ActivityMessageData;
import com.yunqin.bearmall.bean.BCMessageData;
import com.yunqin.bearmall.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Master
 * On 2019/2/19 14:50
 */
public class BCMAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<BCMessageData.DataBean.ListBean> mLists;
    private RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
            .centerCrop();

    public BCMAdapter(Context mContext, List<BCMessageData.DataBean.ListBean> mLists) {
        this.mContext = mContext;
        this.mLists = mLists;
    }

    public void setData(List<BCMessageData.DataBean.ListBean> list) {
        if (mLists == null) {
            mLists = new ArrayList<>();
        }

        if (list != null) {
            mLists.clear();
            mLists.addAll(list);
            notifyDataSetChanged();
        }

    }

    public void addData(List<BCMessageData.DataBean.ListBean> list) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_message_deal_goods, parent, false);
        return new MYHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BCMessageData.DataBean.ListBean listBean = mLists.get(position);
        MYHolder myholer = (MYHolder) holder;
        Glide.with(mContext).setDefaultRequestOptions(requestOptions).load("error").into(myholer.bc_img);
        myholer.time_text.setText(listBean.getCreatedDate());
        myholer.bc_title.setText(listBean.getTitle());
        myholer.bc_content.setText(listBean.getCaption());
        myholer.bc_value.setText(listBean.getValue() + "");

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
        TextView bc_value;

        public MYHolder(View itemView) {
            super(itemView);
            bc_img = itemView.findViewById(R.id.bc_img);
            time_text = itemView.findViewById(R.id.time_text);
            bc_title = itemView.findViewById(R.id.bc_title);
            bc_content = itemView.findViewById(R.id.bc_content);
            bc_value = itemView.findViewById(R.id.bc_value);
        }
    }
}

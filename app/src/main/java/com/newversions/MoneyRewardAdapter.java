package com.newversions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Master
 * On 2019/2/19 14:50
 */
public class MoneyRewardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<MoneyRewardBean.DataBean.ListBean> mLists;
    private RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
            .centerCrop();

    public MoneyRewardAdapter(Context mContext, List<MoneyRewardBean.DataBean.ListBean> mLists) {
        this.mContext = mContext;
        this.mLists = mLists;
    }

    public void setData(List<MoneyRewardBean.DataBean.ListBean> list) {
        if (mLists == null) {
            mLists = new ArrayList<>();
        }

        if (list != null) {
            mLists.clear();
            mLists.addAll(list);
            notifyDataSetChanged();
        }

    }

    public void addData(List<MoneyRewardBean.DataBean.ListBean> list) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_money_reward_layout, parent, false);
        return new MYHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MoneyRewardBean.DataBean.ListBean listBean = mLists.get(position);
        Glide.with(mContext).setDefaultRequestOptions(requestOptions).load("error").into(((MYHolder) holder).y_image);
        ((MYHolder) holder).y_title.setText(listBean.getTitle());

        ((MYHolder) holder).y_order.setText("订 单 号  ：" + listBean.getOrderNo());
        ((MYHolder) holder).y_type.setText("订单类型：" + listBean.getContent());
        ((MYHolder) holder).y_money.setText("订单金额：" + listBean.getSpecifications());

//        if (!TextUtils.isEmpty(listBean.getOrderNo()) && listBean.getOrderNo() != "null") {
//            ((MYHolder) holder).y_order.setText("订 单 号  ：" + listBean.getOrderNo());
//        } else {
//            ((MYHolder) holder).view_5.setVisibility(View.GONE);
//        }

//        if (!TextUtils.isEmpty(listBean.getContent()) && listBean.getContent() != "null") {
//            ((MYHolder) holder).y_type.setText("订单类型：" + listBean.getContent());
//        } else {
//            ((MYHolder) holder).view_5.setVisibility(View.GONE);
//        }

//        if (!TextUtils.isEmpty(listBean.getSpecifications()) && listBean.getSpecifications() != "null") {
//            ((MYHolder) holder).y_money.setText("订单金额：" + listBean.getSpecifications());
//        } else {
//            ((MYHolder) holder).view_5.setVisibility(View.GONE);
//        }


        ((MYHolder) holder).y_shou.setText("获得收益：" + listBean.getValue());

//        if (!TextUtils.isEmpty(listBean.getValue()) && listBean.getValue() != "null") {
//            ((MYHolder) holder).y_shou.setText("获得收益：" + listBean.getValue());
//        } else {
//            ((MYHolder) holder).view_5.setVisibility(View.GONE);
//        }


    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    class MYHolder extends RecyclerView.ViewHolder {

        CircleImageView y_image;
        TextView y_title;
        TextView y_order;
        TextView y_type;
        TextView y_money;
        TextView y_shou;

        public MYHolder(View itemView) {
            super(itemView);
            y_image = itemView.findViewById(R.id.y_image);
            y_title = itemView.findViewById(R.id.y_title);
            y_order = itemView.findViewById(R.id.y_order);
            y_type = itemView.findViewById(R.id.y_type);
            y_money = itemView.findViewById(R.id.y_money);
            y_shou = itemView.findViewById(R.id.y_shou);
        }
    }
}

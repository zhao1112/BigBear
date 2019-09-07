package com.newversions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqin.bearmall.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Master
 * On 2019/2/19 14:50
 */
public class MoneyRewardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<MoneyRewardBean.DataBean.ListBean> mLists;

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

        ((MYHolder) holder).titleTime.setText(listBean.getOperDate());
        ((MYHolder) holder).title.setText(listBean.getTitle());
        ((MYHolder) holder).content.setText(listBean.getContent());
        ((MYHolder) holder).moneyNumber.setText(listBean.getValue());
        ((MYHolder) holder).time.setText("获取时间:" + listBean.getCreatedDate());


    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }


    class MYHolder extends RecyclerView.ViewHolder {

        TextView titleTime;
        TextView title;
        TextView content;
        TextView moneyNumber;
        TextView time;

        public MYHolder(View itemView) {
            super(itemView);
            titleTime = itemView.findViewById(R.id.money_reward_title_time);
            title = itemView.findViewById(R.id.money_reward_title);
            content = itemView.findViewById(R.id.money_reward_content);
            moneyNumber = itemView.findViewById(R.id.money_reward_number);
            time = itemView.findViewById(R.id.money_reward_time);
        }


    }


}

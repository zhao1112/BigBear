package com.newreward.apdater;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newreward.bean.RewardDetailBean;
import com.yunqin.bearmall.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenchen on 2018/7/21.
 */

public class RewardDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RewardDetailBean.DataBean.ListBean> datas;
    private Context context;
    private int type;


    public RewardDetailAdapter(List<RewardDetailBean.DataBean.ListBean> datas, Context context, int type) {
        this.datas = datas;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reward_today, parent, false);

        return new RecordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        try {
            RecordHolder recordHolder = (RecordHolder) holder;

            RewardDetailBean.DataBean.ListBean rewardBean = datas.get(position);

            recordHolder.createDateView.setText(rewardBean.getDetail());

            recordHolder.memoView.setText(rewardBean.getTitle());

            if (type == 0) {

            } else {

                recordHolder.bearView.setImageResource(R.drawable.icon_bear_gray);
                recordHolder.creditView.setTextColor(Color.parseColor("#999999"));

            }

            recordHolder.creditView.setText(rewardBean.getBalanceChange());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    class RecordHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_memo)
        TextView memoView;

        @BindView(R.id.item_credit)
        TextView creditView;

        @BindView(R.id.item_date)
        TextView createDateView;

        @BindView(R.id.item_image)
        ImageView bearView;

        public RecordHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

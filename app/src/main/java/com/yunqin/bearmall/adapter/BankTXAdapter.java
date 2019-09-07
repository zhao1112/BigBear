package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.BankTxBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Master
 * On 2019/2/28 18:41
 */
public class BankTXAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<BankTxBean.DataBean.WithdrawListBean> objects;


    public BankTXAdapter(Context mContext) {
        this.mContext = mContext;
        objects = new ArrayList<>();
    }


    public void setData(List<BankTxBean.DataBean.WithdrawListBean> objects) {
        this.objects = objects;
        notifyDataSetChanged();
    }

    public void addData(List<BankTxBean.DataBean.WithdrawListBean> objects) {
        this.objects.addAll(objects);
        notifyDataSetChanged();
    }


    public List<BankTxBean.DataBean.WithdrawListBean> getData() {
        return objects;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bank_tx_layout, parent, false);
        return new BankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        BankTxBean.DataBean.WithdrawListBean bankTxBean = objects.get(position);

        if (holder instanceof BankViewHolder) {
            ((BankViewHolder) holder).item_1.setText("¥" + bankTxBean.getAmount());

            if (bankTxBean.getType() == 0) {
                ((BankViewHolder) holder).item_2.setVisibility(View.VISIBLE);
                ((BankViewHolder) holder).item_3.setVisibility(View.GONE);
                ((BankViewHolder) holder).item_2.setText("绑定微信");
            } else {
                ((BankViewHolder) holder).item_2.setVisibility(View.VISIBLE);
                ((BankViewHolder) holder).item_3.setVisibility(View.VISIBLE);
                ((BankViewHolder) holder).item_2.setText(bankTxBean.getBankCard());
                ((BankViewHolder) holder).item_3.setText(bankTxBean.getAccountName());
            }
            ((BankViewHolder) holder).item_4.setText(bankTxBean.getCreatedDate().split(" ")[0]);
            ((BankViewHolder) holder).item_5.setText(bankTxBean.getCreatedDate().split(" ")[1]);
            if (bankTxBean.getStatus() == 0) {// 待审核
                ((BankViewHolder) holder).item_6.setText("审核中");
                ((BankViewHolder) holder).item_6.setTextColor(Color.parseColor("#333333"));
            } else if (bankTxBean.getStatus() == 1) {// 审核通过
                ((BankViewHolder) holder).item_6.setText("审核通过");
                ((BankViewHolder) holder).item_6.setTextColor(Color.parseColor("#23A064"));
            } else if (bankTxBean.getStatus() == 2) {// 提现失败
                ((BankViewHolder) holder).item_6.setText("审核失败");
                ((BankViewHolder) holder).item_6.setTextColor(Color.parseColor("#E75B56"));
            }
        }

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }


    class BankViewHolder extends RecyclerView.ViewHolder {

        TextView item_1;
        TextView item_2;
        TextView item_3;
        TextView item_4;
        TextView item_5;
        TextView item_6;

        public BankViewHolder(View itemView) {
            super(itemView);

            item_1 = itemView.findViewById(R.id.item_1);
            item_2 = itemView.findViewById(R.id.item_2);
            item_3 = itemView.findViewById(R.id.item_3);
            item_4 = itemView.findViewById(R.id.item_4);
            item_5 = itemView.findViewById(R.id.item_5);
            item_6 = itemView.findViewById(R.id.item_6);


        }
    }


}

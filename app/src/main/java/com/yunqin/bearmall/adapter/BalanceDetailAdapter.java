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
import com.yunqin.bearmall.bean.Balance;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BalanceDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Balance> balanceList;
    String balance;

    public BalanceDetailAdapter(Context context, List<Balance> balanceList, String balance) {
        this.context = context;
        this.balanceList = balanceList;
        this.balance = balance;
    }

    public void changeHeaderPrice(String price){
        balance = price;
        notifyItemChanged(0);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 1){

            View view = LayoutInflater.from(context).inflate(R.layout.item_balance_header,parent,false);

            return new HeaderViewHolder(view);

        }else {

            View view = LayoutInflater.from(context).inflate(R.layout.item_balance_normal,parent,false);

            return new NormalViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder){

            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.priceView.setText("¥"+balance);

        }else if (holder instanceof NormalViewHolder){

            Balance balance = balanceList.get(position-1);
            NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
            normalViewHolder.dateView.setText(balance.getCreatedDate());
            normalViewHolder.titleView.setText(balance.getDetail());
            normalViewHolder.moneyView.setText(balance.getBalanceChange());
            int type = balance.getType();
            //提现 购买 为支出
            if (type == 0 || type==4){
                normalViewHolder.moneyView.setTextColor(Color.parseColor("#999999"));
            }else{
                normalViewHolder.moneyView.setTextColor(Color.parseColor("#23A064"));
            }

        }

    }

    @Override
    public int getItemCount() {
        return (balanceList==null||balanceList.isEmpty())?1:balanceList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return 1;
        }
        return 0;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.balance_header_title)
        TextView priceView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


    }

    class NormalViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView titleView;
        @BindView(R.id.date)
        TextView dateView;
        @BindView(R.id.money)
        TextView moneyView;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


    }


}

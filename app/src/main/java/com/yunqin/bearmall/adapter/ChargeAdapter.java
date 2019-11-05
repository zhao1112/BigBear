package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.Charge;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChargeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Charge> datas;

    private int lastSeletIndex = -1;

    public ChargeAdapter(Context context, List<Charge> datas) {
        this.context = context;
        this.datas = datas;
    }

    public int getLastSeletIndex() {
        return lastSeletIndex;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0){
            View view = LayoutInflater.from(context).inflate(R.layout.item_charge_phone,parent,false);
            return new ChargeViewHolder(view);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_charge_flow,parent,false);
        return new FlowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ChargeViewHolder){
            handlePhoneCharge((ChargeViewHolder)holder,position);
        }else if (holder instanceof FlowViewHolder){
            handleFlowCharge((FlowViewHolder)holder,position);
        }
    }

    private void  handlePhoneCharge(ChargeViewHolder holder,int position){

       holder.itemView.setTag(position);
       Charge charge = datas.get(position);
       boolean isSelect = charge.isSelect();
        holder.itemView.setSelected(isSelect);
        holder.chargeNameView.setSelected(isSelect);
        holder.chargePriceView.setSelected(isSelect);
        holder.chargeBearView.setSelected(isSelect);
        holder.itemView.setOnClickListener(onClickListener);

        holder.chargeNameView.setText(charge.getTitle());
        holder.chargePriceView.setText(String.format("售价 %s元",charge.getPayPrice()));
        holder.chargeBearView.setText(String.format("金熊价 %s元",charge.getMembershipPrice()));

    }

    private void  handleFlowCharge(FlowViewHolder holder,int position){
        Charge charge = datas.get(position);
        boolean isSelect = charge.isSelect();
        holder.itemView.setSelected(isSelect);
        holder.chargeFlowNameView.setSelected(isSelect);
        holder.chargeFlowPriceView.setSelected(isSelect);
        holder.itemView.setOnClickListener(onClickListener);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           int position = (int) view.getTag();
            if (lastSeletIndex != position){
                Charge charge = datas.get(position);
                charge.setSelect(!charge.isSelect());
                notifyItemChanged(position);
                if (lastSeletIndex != -1){
                    Charge last = datas.get(lastSeletIndex);
                    last.setSelect(!last.isSelect());
                    notifyItemChanged(lastSeletIndex);
                }
                lastSeletIndex = position;
            }
        }
    };

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {

        Charge charge = datas.get(position);

        return charge.getType();
    }

    class ChargeViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.charge_name)
        TextView chargeNameView;
        @BindView(R.id.charge_value)
        TextView chargePriceView;
        @BindView(R.id.bear_price)
        TextView chargeBearView;

        public ChargeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

    class FlowViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.charge_name)
        TextView chargeFlowNameView;
        @BindView(R.id.charge_value)
        TextView chargeFlowPriceView;

        public FlowViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }



}

package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.ZeroGoodsBean;
import com.yunqin.bearmall.ui.activity.ZeroMoneyDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class ZeroAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ZeroGoodsBean.DataBean.GroupPurchasingListBean> list;
    private String sume = "0";
    private final String money = "¥";

    public ZeroAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_zeor, parent, false);
        return new ZeorHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ZeorHolder zeorHolder = (ZeorHolder) holder;
        Glide.with(context).
                setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small))
                .load(list.get(position).getProductImages().getThumbnail())
                .into(zeorHolder.image);
        zeorHolder.title.setText(list.get(position).getProductName());
        zeorHolder.iz_zhanjia.setText("已有" + list.get(position).getTotalCount() + "参加");
        zeorHolder.iz_yuanjia.setText("原价" + money + list.get(position).getMembershipPrice());
        zeorHolder.iz_sume.setText(sume);
        zeorHolder.iz_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ZeroMoneyDetailsActivity.class);
                intent.putExtra("groupPurchasing_id", list.get(position).getGroupPurchasing_id() + "");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ZeorHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView title;
        private final TextView iz_zhanjia;
        private final TextView iz_yuanjia;
        private final TextView iz_sume;
        private final RelativeLayout iz_go;

        public ZeorHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iz_image);
            title = itemView.findViewById(R.id.iz_title);
            iz_zhanjia = itemView.findViewById(R.id.iz_zhanjia);
            iz_yuanjia = itemView.findViewById(R.id.iz_yuanjia);
            iz_sume = itemView.findViewById(R.id.iz_sume);
            iz_go = itemView.findViewById(R.id.iz_go);
        }
    }

    public void setData(List<ZeroGoodsBean.DataBean.GroupPurchasingListBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void deleData() {
        this.list.clear();
    }

    public void setSume(String sume) {
        this.sume = sume;
        notifyDataSetChanged();
    }
}

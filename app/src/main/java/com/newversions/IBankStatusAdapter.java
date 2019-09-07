package com.newversions;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.CreditCardBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Master
 * On 2019/3/26 16:57
 */
public class IBankStatusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context iContext;
    private LayoutInflater inflater;
    private List<CreditCardBean.DataBean.CardListBean> mLists;

    public IBankStatusAdapter(Context iContext) {
        this.iContext = iContext;
        inflater = LayoutInflater.from(iContext);
        mLists = new ArrayList<>();
    }


    public void setData(List<CreditCardBean.DataBean.CardListBean> objs) {
        mLists = objs;
        notifyDataSetChanged();
    }

    public void addData(List<CreditCardBean.DataBean.CardListBean> objs) {
        mLists.addAll(objs);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.credit_bank_item, parent, false);
        return new IViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        CreditCardBean.DataBean.CardListBean cardListBean = mLists.get(position);

        if (holder instanceof IViewHolder) {
            ((IViewHolder) holder).topTitle.setText(cardListBean.getCardName());
            ((IViewHolder) holder).centerTitle.setText(String.format("申请时间：%s", cardListBean.getApplyTime()));
            ((IViewHolder) holder).bottomTitle.setText(cardListBean.getDesc());
            String color = "#E3A832";
            if (cardListBean.getApplyStatus() == 5) {
                color = "#E75B56";
            } else if (cardListBean.getApplyStatus() == 4 || cardListBean.getApplyStatus() == 6 || cardListBean.getApplyStatus() == 10) {
                color = "#23A064";
            }

            ((IViewHolder) holder).bottomTitle.setTextColor(Color.parseColor(color));
            ((IViewHolder) holder).first_bank_img.setVisibility(cardListBean.getIsFirstCard() == 1 ? View.VISIBLE : View.INVISIBLE);
            Glide.with(iContext).load(cardListBean.getImg()).into(((IViewHolder) holder).bankImg);
        }

    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }


    class IViewHolder extends RecyclerView.ViewHolder {

        TextView topTitle, centerTitle, bottomTitle;
        ImageView bankImg;
        ImageView first_bank_img;

        public IViewHolder(View itemView) {
            super(itemView);
            topTitle = itemView.findViewById(R.id.top_title);
            centerTitle = itemView.findViewById(R.id.center_title);
            bottomTitle = itemView.findViewById(R.id.bottom_title);
            bankImg = itemView.findViewById(R.id.bank_img);
            first_bank_img = itemView.findViewById(R.id.first_bank_img);
        }
    }


}
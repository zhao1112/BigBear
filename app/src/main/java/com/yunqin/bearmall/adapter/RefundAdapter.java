package com.yunqin.bearmall.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.ShouHouBean;
import com.yunqin.bearmall.ui.activity.RefundDetailsActivity;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yunqin.bearmall.adapter.RefundAdapter.OrderType.AWAITPAY;
import static com.yunqin.bearmall.adapter.RefundAdapter.OrderType.AWAITRECEIVING;
import static com.yunqin.bearmall.adapter.RefundAdapter.OrderType.AWAITSHIPMENTS;
import static com.yunqin.bearmall.adapter.RefundAdapter.OrderType.CNACEL;
import static com.yunqin.bearmall.adapter.RefundAdapter.OrderType.DEALSUCCESS;
import static com.yunqin.bearmall.adapter.RefundAdapter.OrderType.TEST;


public class RefundAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ShouHouBean.DataBean detaBean;
    private List<ShouHouBean.DataBean.AfterSalesListBean> listBeans;

    public RefundAdapter(Context mContext, ShouHouBean.DataBean detaBean) {
        this.mContext = mContext;
        this.listBeans = detaBean.getAfterSalesList();
    }


    public void setData(ShouHouBean.DataBean detaBean) {
        this.listBeans = detaBean.getAfterSalesList();
        notifyDataSetChanged();
    }


    public void addData(ShouHouBean.DataBean detaBean) {
        this.listBeans.addAll(detaBean.getAfterSalesList());
        notifyDataSetChanged();
    }

    public List<ShouHouBean.DataBean.AfterSalesListBean> getData() {
        return listBeans;
    }


    @IntDef({TEST, DEALSUCCESS, AWAITPAY, CNACEL, AWAITSHIPMENTS, AWAITRECEIVING})
    public @interface OrderType {
        int TEST = 0;// 测试
        int DEALSUCCESS = 1;// 交易完成
        int AWAITPAY = 2;// 待支付
        int CNACEL = 3;// 取消
        int AWAITSHIPMENTS = 4;// 待发货
        int AWAITRECEIVING = 5;// 待收货
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_orders, null);
        return new IViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof IViewHolder) {
                ((IViewHolder) holder).orderTyleTextView.setVisibility(View.VISIBLE);
                ((IViewHolder) holder).order_check_info.setVisibility(View.VISIBLE);

                ((IViewHolder) holder).order_check_info.setTag(position);
                ((IViewHolder) holder).order_check_info.setOnClickListener(this);


                ShouHouBean.DataBean.AfterSalesListBean bean = listBeans.get(position);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(bean.getLogo()).into(((IViewHolder) holder).imageView);
                String content = "";
                if ("AftersalesReturns".equals(bean.getDtype())) {
                    content = "退货";
                } else if ("AftersalesReplacement".equals(bean.getDtype())) {
                    content = "换货";
                } else {
                    content = bean.getDtype();
                }

                int afterSalesStatus = bean.getAfterSalesStatus();
                String suf = "";
                if (afterSalesStatus == 0) {
                    suf = "待审核";
                } else if (afterSalesStatus == 1) {
                    suf = "审核通过";
                } else if (afterSalesStatus == 2) {
                    suf = "审核失败";
                } else if (afterSalesStatus == 3) {
                    suf = "已完成";
                } else if (afterSalesStatus == 4) {
                    suf = "已取消";
                } else {
                    suf = "无此状态-" + afterSalesStatus;
                }

                ((IViewHolder) holder).orderTyleTextView.setText(content + "-" + suf);
                ((IViewHolder) holder).titleTextView.setText(bean.getStoreName());

                ((IViewHolder) holder).order_status_view.setVisibility(View.GONE);
                ((IViewHolder) holder).order_info_view.setVisibility(View.GONE);


                int count = bean.getAfterSalesItemList().size();

                if (((IViewHolder) holder).contentLayout.getChildCount() > 0) {
                    return;
                }

                ((IViewHolder) holder).contentLayout.removeAllViews();

                for (int i = 0; i < count; i++) {

                    ShouHouBean.DataBean.AfterSalesListBean.AfterSalesItemListBean itemBean = bean.getAfterSalesItemList().get(i);

                    View view = View.inflate(mContext, R.layout.item_goods, null);
                    ImageView goods_pic = view.findViewById(R.id.goods_pic);
                    TextView goods_name = view.findViewById(R.id.goods_name);
                    TextView goods_color = view.findViewById(R.id.goods_color);
                    TextView goods_price = view.findViewById(R.id.goods_price);
                    TextView goods_count = view.findViewById(R.id.goods_count);

                    StringBuffer specificationItems = new StringBuffer();
                    for (String contents : itemBean.getSpecifications()) {
                        specificationItems.append(contents).append(" ");
                    }
                    Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(itemBean.getThumbnail()).into(goods_pic);
                    goods_name.setText(itemBean.getOrderItemName());


                    // 纯法比
                    goods_price.setText(String.format("￥%s+BC%s", itemBean.getPrice(), itemBean.getBtAmount()));


                    goods_color.setText(specificationItems);
                    goods_count.setText(String.format("X%d", itemBean.getQuantity()));
                    ((IViewHolder) holder).contentLayout.addView(view);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listBeans.size();
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("afterSales_id", listBeans.get(((int) v.getTag())).getAfterSales_id() + "");
        StarActivityUtil.starActivity((Activity) mContext, RefundDetailsActivity.class, bundle);

    }


    class IViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.mall_pic)
        CircleImageView imageView;
        @BindView(R.id.mall_title)
        TextView titleTextView;
        @BindView(R.id.order_type)
        TextView orderTyleTextView;
        @BindView(R.id.order_content)
        LinearLayout contentLayout;

        @BindView(R.id.order_apply_after_sale)
        Button order_apply_after_sale;
        @BindView(R.id.order_check_logistics)
        Button order_check_logistics;
        @BindView(R.id.order_appraise)
        Button order_appraise;
        @BindView(R.id.order_check_info)
        Button order_check_info;

        @BindView(R.id.order_info_view)
        LinearLayout order_info_view;

        @BindView(R.id.order_status_view)
        LinearLayout order_status_view;

        @BindView(R.id.order_status)
        TextView order_status;


        public IViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}

package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.OrderBean;
import com.yunqin.bearmall.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yunqin.bearmall.adapter.AllFragmentAdapter.ButtonType.CHAKANWULIU;
import static com.yunqin.bearmall.adapter.AllFragmentAdapter.ButtonType.CHAKANXIANGQING;
import static com.yunqin.bearmall.adapter.AllFragmentAdapter.ButtonType.QUERENSHOUHUO;
import static com.yunqin.bearmall.adapter.AllFragmentAdapter.ButtonType.QUFUKUAN;
import static com.yunqin.bearmall.adapter.AllFragmentAdapter.ButtonType.QUXIAO;
import static com.yunqin.bearmall.adapter.AllFragmentAdapter.ButtonType.SHAIDANPINGJIA;
import static com.yunqin.bearmall.adapter.AllFragmentAdapter.ButtonType.SHANCHUDINGDAN;
import static com.yunqin.bearmall.adapter.AllFragmentAdapter.ButtonType.SHENQINGSHOUHOU;
import static com.yunqin.bearmall.adapter.AllFragmentAdapter.ButtonType.ZAICIGOUMAI;
import static com.yunqin.bearmall.adapter.AllFragmentAdapter.OrderType.DENGDAIFAHUO;
import static com.yunqin.bearmall.adapter.AllFragmentAdapter.OrderType.DENGDAIFUKUAN;
import static com.yunqin.bearmall.adapter.AllFragmentAdapter.OrderType.YIFAHUO;
import static com.yunqin.bearmall.adapter.AllFragmentAdapter.OrderType.YIJUJUE;
import static com.yunqin.bearmall.adapter.AllFragmentAdapter.OrderType.YIQUXIAO;
import static com.yunqin.bearmall.adapter.AllFragmentAdapter.OrderType.YISHIBAI;
import static com.yunqin.bearmall.adapter.AllFragmentAdapter.OrderType.YISHOUHUO;
import static com.yunqin.bearmall.adapter.AllFragmentAdapter.OrderType.YIWANCHENG;

public class AllFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<OrderBean.DataBean.OrdersListBean> list = new ArrayList<>();

    public AllFragmentAdapter(Context mContext, List<OrderBean.DataBean.OrdersListBean> list) {
        this.mContext = mContext;
        if (list != null) {
            this.list = list;
        } else {
            this.list.clear();
        }
    }

    public void setData(List<OrderBean.DataBean.OrdersListBean> listBeans) {
        if (listBeans != null) {
            this.list = listBeans;
            notifyDataSetChanged();
        }
    }


    public void addData(List<OrderBean.DataBean.OrdersListBean> listBeans) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.addAll(listBeans);
        notifyDataSetChanged();
    }


    public List<OrderBean.DataBean.OrdersListBean> getData() {
        return list;
    }


    @IntDef({DENGDAIFUKUAN, DENGDAIFAHUO, YIFAHUO, YISHOUHUO, YIWANCHENG, YISHIBAI, YIQUXIAO, YIJUJUE})
    public @interface OrderType {
        int DENGDAIFUKUAN = 0;
        int DENGDAIFAHUO = 2;
        int YIFAHUO = 3;
        int YISHOUHUO = 4;
        int YIWANCHENG = 5;
        int YISHIBAI = 6;
        int YIQUXIAO = 7;
        int YIJUJUE = 8;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIRTUAL_ORDER) {
            View view = View.inflate(parent.getContext(), R.layout.new_virtual_item_goods, null);
            return new VirtualHolder(view);
        } else if (viewType == REAL_ORDER) {
            View view = View.inflate(parent.getContext(), R.layout.item_orders, null);
            return new IViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof VirtualHolder) {

            hideView((VirtualHolder) holder);
            ((VirtualHolder) holder).view.setVisibility(View.VISIBLE);

            OrderBean.DataBean.OrdersListBean ordersListBean = list.get(position);
            ((VirtualHolder) holder).name.setText(ordersListBean.getVirtualItem().getTitle());
            ((VirtualHolder) holder).price.setText("￥" + ordersListBean.getVirtualItem().getPrice());
            ((VirtualHolder) holder).count.setText("X" + ordersListBean.getVirtualItem().getQuantity());

            String types = ordersListBean.getVirtualItem().getType() == 0 ? "话费订单-" : "流量订单-";

            if (ordersListBean.getStatus() == DENGDAIFUKUAN) {
                if (ordersListBean.getIsExpire() == 0) {// 是否过期
                    ((VirtualHolder) holder).order_type.setText(types + "待付款");
                    ((VirtualHolder) holder).order_cancel.setVisibility(View.VISIBLE);
                    ((VirtualHolder) holder).order_go_pay.setVisibility(View.VISIBLE);
                    // 设置监听事件
                    ((VirtualHolder) holder).order_cancel.setTag(String.format("%d,%d", position, QUXIAO));
                    ((VirtualHolder) holder).order_go_pay.setTag(String.format("%d,%d", position, QUFUKUAN));
                    ((VirtualHolder) holder).order_cancel.setOnClickListener(this);
                    ((VirtualHolder) holder).order_go_pay.setOnClickListener(this);

                    ((VirtualHolder) holder).goods_view.setTag(String.format("%d#%d,%d", position, 0, CHAKANXIANGQING));
                    ((VirtualHolder) holder).goods_view.setOnClickListener(this);


                } else {
                    ((VirtualHolder) holder).order_type.setText(types + "已过期");
                    ((VirtualHolder) holder).order_del.setVisibility(View.VISIBLE);
                    ((VirtualHolder) holder).order_del.setTag(String.format("%d,%d", position, SHANCHUDINGDAN));
                    ((VirtualHolder) holder).order_del.setOnClickListener(this);
                }
            } else if (ordersListBean.getStatus() == DENGDAIFAHUO) {
                ((VirtualHolder) holder).order_type.setText(types + "待发货");

                ((VirtualHolder) holder).goods_view.setTag(String.format("%d#%d,%d", position, 0, CHAKANXIANGQING));
                ((VirtualHolder) holder).goods_view.setOnClickListener(this);

                ((VirtualHolder) holder).view.setVisibility(View.GONE);


            } else if (ordersListBean.getStatus() == YIWANCHENG) {
                ((VirtualHolder) holder).order_type.setText(types + "已完成");
                ((VirtualHolder) holder).order_buy_again.setVisibility(View.VISIBLE);
                ((VirtualHolder) holder).order_buy_again.setTag(String.format("%d,%d", position, ZAICIGOUMAI));
                ((VirtualHolder) holder).order_buy_again.setOnClickListener(this);


                ((VirtualHolder) holder).goods_view.setTag(String.format("%d#%d,%d", position, 0, CHAKANXIANGQING));
                ((VirtualHolder) holder).goods_view.setOnClickListener(this);
            } else if (ordersListBean.getStatus() == YISHIBAI) {
                ((VirtualHolder) holder).order_type.setText(types + "已失败");

                ((VirtualHolder) holder).order_del.setVisibility(View.VISIBLE);
                ((VirtualHolder) holder).order_del.setTag(String.format("%d,%d", position, SHANCHUDINGDAN));
                ((VirtualHolder) holder).order_del.setOnClickListener(this);

            } else if (ordersListBean.getStatus() == YIQUXIAO) {
                ((VirtualHolder) holder).order_type.setText(types + "已取消");
                ((VirtualHolder) holder).view.setVisibility(View.GONE);
            } else if (ordersListBean.getStatus() == YIJUJUE) {
                ((VirtualHolder) holder).order_type.setText(types + "已拒绝");

                ((VirtualHolder) holder).order_del.setVisibility(View.VISIBLE);
                ((VirtualHolder) holder).order_del.setTag(String.format("%d,%d", position, SHANCHUDINGDAN));
                ((VirtualHolder) holder).order_del.setOnClickListener(this);
            }

            Glide.with(mContext)
                    .setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult))
                    .load(ordersListBean.getVirtualItem().getImg())
                    .into(((VirtualHolder) holder).imgview);
        }


        if (holder instanceof IViewHolder) {

            ((IViewHolder) holder).view.setVisibility(View.VISIBLE);

            OrderBean.DataBean.OrdersListBean bean = list.get(position);
            hideView(((IViewHolder) holder));
            ((IViewHolder) holder).orderTyleTextView.setVisibility(View.VISIBLE);
            ((IViewHolder) holder).titleTextView.setText(bean.getStore_name());
            if (bean.getStatus() == DENGDAIFUKUAN) {

                if (bean.getIsExpire() == 0) {

                    if (bean.getOrderType() == 0) {
                        ((IViewHolder) holder).orderTyleTextView.setText("待付款");
                    } else if (bean.getOrderType() == 1) {
                        ((IViewHolder) holder).orderTyleTextView.setText("折扣订单-待付款");
                    } else if (bean.getOrderType() == 2) {
                        ((IViewHolder) holder).orderTyleTextView.setText("糖果0元兑-待付款");
                    } else if (bean.getOrderType() == 3) {
                        ((IViewHolder) holder).orderTyleTextView.setText("砍价随意拿-待付款");
                    } else {
                        ((IViewHolder) holder).orderTyleTextView.setText("待付款");
                    }


                    ((IViewHolder) holder).order_cancel.setVisibility(View.VISIBLE);
                    ((IViewHolder) holder).order_go_pay.setVisibility(View.VISIBLE);
                    // 设置监听事件

                    ((IViewHolder) holder).order_cancel.setTag(String.format("%d,%d", position, QUXIAO));
                    ((IViewHolder) holder).order_go_pay.setTag(String.format("%d,%d", position, QUFUKUAN));
                    ((IViewHolder) holder).order_cancel.setOnClickListener(this);
                    ((IViewHolder) holder).order_go_pay.setOnClickListener(this);
                } else {


                    if (bean.getOrderType() == 0) {
                        ((IViewHolder) holder).orderTyleTextView.setText(Html.fromHtml("<font color=#999999>已过期</font>"));
                    } else if (bean.getOrderType() == 1) {
                        ((IViewHolder) holder).orderTyleTextView.setText(Html.fromHtml("<font color=#999999>折扣订单-已过期</font>"));
                    } else if (bean.getOrderType() == 2) {
                        ((IViewHolder) holder).orderTyleTextView.setText(Html.fromHtml("<font color=#999999>糖果0元兑-已过期</font>"));
                    } else if (bean.getOrderType() == 3) {
                        ((IViewHolder) holder).orderTyleTextView.setText(Html.fromHtml("<font color=#999999>砍价随意拿-已过期</font>"));
                    } else {
                        ((IViewHolder) holder).orderTyleTextView.setText(Html.fromHtml("<font color=#999999>已过期</font>"));
                    }

                    ((IViewHolder) holder).order_del.setVisibility(View.VISIBLE);
                    ((IViewHolder) holder).order_del.setTag(String.format("%d,%d", position, SHANCHUDINGDAN));
                    ((IViewHolder) holder).order_del.setOnClickListener(this);
                }


            } else if (bean.getStatus() == DENGDAIFAHUO) {

                if (bean.getOrderType() == 0) {
                    ((IViewHolder) holder).orderTyleTextView.setText("待发货");
                } else if (bean.getOrderType() == 1) {
                    ((IViewHolder) holder).orderTyleTextView.setText("折扣订单-待发货");
                } else if (bean.getOrderType() == 2) {
                    ((IViewHolder) holder).orderTyleTextView.setText("糖果0元兑-待发货");
                } else if (bean.getOrderType() == 3) {
                    ((IViewHolder) holder).orderTyleTextView.setText("砍价随意拿-待发货");
                } else {
                    ((IViewHolder) holder).orderTyleTextView.setText("待发货");
                }


                if (bean.getIsAllowAfterSales() == 1) {
                    ((IViewHolder) holder).order_apply_after_sale.setVisibility(View.VISIBLE);
                } else {
                    ((IViewHolder) holder).order_apply_after_sale.setVisibility(View.GONE);
                }


                ((IViewHolder) holder).order_status_view.setVisibility(View.VISIBLE);
                ((IViewHolder) holder).order_buy_again.setVisibility(View.VISIBLE);

                ((IViewHolder) holder).order_buy_again.setTag(String.format("%d,%d", position, ZAICIGOUMAI));
                ((IViewHolder) holder).order_apply_after_sale.setTag(String.format("%d,%d", position, SHENQINGSHOUHOU));
                ((IViewHolder) holder).order_buy_again.setOnClickListener(this);
                ((IViewHolder) holder).order_apply_after_sale.setOnClickListener(this);


            } else if (bean.getStatus() == YIFAHUO) {


                if (bean.getOrderType() == 0) {
                    ((IViewHolder) holder).orderTyleTextView.setText("待收货");
                } else if (bean.getOrderType() == 1) {
                    ((IViewHolder) holder).orderTyleTextView.setText("折扣订单-待收货");
                } else if (bean.getOrderType() == 2) {
                    ((IViewHolder) holder).orderTyleTextView.setText("糖果0元兑-待收货");
                } else if (bean.getOrderType() == 3) {
                    ((IViewHolder) holder).orderTyleTextView.setText("砍价随意拿-待收货");
                } else {
                    ((IViewHolder) holder).orderTyleTextView.setText("待收货");
                }


                ((IViewHolder) holder).order_status_view.setVisibility(View.VISIBLE);
                ((IViewHolder) holder).order_info_view.setVisibility(View.VISIBLE);
                ((IViewHolder) holder).order_status.setText(String.format("你的订单已由%s检货完毕,待出库已交付%s", bean.getStore_name(), bean.getDeliveryCorp()));
                ((IViewHolder) holder).order_number.setText(bean.getTrackingNo());
                ((IViewHolder) holder).order_time.setText(bean.getDeliveryTime());

                if (bean.getIsAllowAfterSales() == 1) {
                    ((IViewHolder) holder).order_apply_after_sale.setVisibility(View.VISIBLE);
                } else {
                    ((IViewHolder) holder).order_apply_after_sale.setVisibility(View.GONE);
                }

                ((IViewHolder) holder).order_check_logistics.setVisibility(View.VISIBLE);
                ((IViewHolder) holder).order_confirm_receipt.setVisibility(View.VISIBLE);
                ((IViewHolder) holder).order_buy_again.setVisibility(View.VISIBLE);

                ((IViewHolder) holder).order_apply_after_sale.setTag(String.format("%d,%d", position, SHENQINGSHOUHOU));
                ((IViewHolder) holder).order_check_logistics.setTag(String.format("%d,%d", position, CHAKANWULIU));
                ((IViewHolder) holder).order_confirm_receipt.setTag(String.format("%d,%d", position, QUERENSHOUHUO));
                ((IViewHolder) holder).order_buy_again.setTag(String.format("%d,%d", position, ZAICIGOUMAI));

                ((IViewHolder) holder).order_apply_after_sale.setOnClickListener(this);
                ((IViewHolder) holder).order_check_logistics.setOnClickListener(this);
                ((IViewHolder) holder).order_confirm_receipt.setOnClickListener(this);
                ((IViewHolder) holder).order_buy_again.setOnClickListener(this);

            } else if (bean.getStatus() == YISHOUHUO || bean.getStatus() == YIWANCHENG) {


                if (bean.isReviewed()) {
                    ((IViewHolder) holder).order_appraise.setVisibility(View.GONE);




                } else {
                    ((IViewHolder) holder).order_appraise.setVisibility(View.VISIBLE);
                }

                if (bean.getIsAllowAfterSales() == 1) {
                    ((IViewHolder) holder).order_apply_after_sale.setVisibility(View.VISIBLE);
                } else {
                    ((IViewHolder) holder).order_apply_after_sale.setVisibility(View.GONE);
                }

                ((IViewHolder) holder).orderTyleTextView.setText("交易已完成");

                ((IViewHolder) holder).order_check_logistics.setVisibility(View.VISIBLE);
                ((IViewHolder) holder).order_buy_again.setVisibility(View.VISIBLE);

                ((IViewHolder) holder).order_check_logistics.setTag(String.format("%d,%d", position, CHAKANWULIU));


                ((IViewHolder) holder).order_apply_after_sale.setTag(String.format("%d,%d", position, SHENQINGSHOUHOU));
                ((IViewHolder) holder).order_appraise.setTag(String.format("%d,%d", position, SHAIDANPINGJIA));
                ((IViewHolder) holder).order_buy_again.setTag(String.format("%d,%d", position, ZAICIGOUMAI));

                ((IViewHolder) holder).order_check_logistics.setOnClickListener(this);
                ((IViewHolder) holder).order_apply_after_sale.setOnClickListener(this);
                ((IViewHolder) holder).order_appraise.setOnClickListener(this);
                ((IViewHolder) holder).order_buy_again.setOnClickListener(this);

            } else if (bean.getStatus() == YIQUXIAO) {


                if (bean.getOrderType() == 0) {
                    ((IViewHolder) holder).orderTyleTextView.setText(Html.fromHtml("<font color=#999999>已取消</font>"));
                } else if (bean.getOrderType() == 1) {
                    ((IViewHolder) holder).orderTyleTextView.setText(Html.fromHtml("<font color=#999999>砍价随意拿-已取消</font>"));
                } else if (bean.getOrderType() == 2) {
                    ((IViewHolder) holder).orderTyleTextView.setText(Html.fromHtml("<font color=#999999>砍价随意拿-已取消</font>"));
                } else if (bean.getOrderType() == 3) {
                    ((IViewHolder) holder).orderTyleTextView.setText(Html.fromHtml("<font color=#999999>砍价随意拿-已取消</font>"));
                } else {
                    ((IViewHolder) holder).orderTyleTextView.setText(Html.fromHtml("<font color=#999999>已取消</font>"));
                }

//                ((IViewHolder) holder).order_del.setVisibility(View.VISIBLE);
                ((IViewHolder) holder).order_buy_again.setVisibility(View.VISIBLE);

                ((IViewHolder) holder).order_del.setTag(String.format("%d,%d", position, SHANCHUDINGDAN));
                ((IViewHolder) holder).order_buy_again.setTag(String.format("%d,%d", position, ZAICIGOUMAI));

                ((IViewHolder) holder).order_del.setOnClickListener(this);
                ((IViewHolder) holder).order_buy_again.setOnClickListener(this);

            } else if (bean.getStatus() == YIJUJUE) {
                // TODO 拒绝

                ((IViewHolder) holder).view.setVisibility(View.GONE);


                if (bean.getOrderType() == 0) {
                    ((IViewHolder) holder).orderTyleTextView.setText("已拒绝");
                } else if (bean.getOrderType() == 1) {
                    ((IViewHolder) holder).orderTyleTextView.setText("折扣订单-已拒绝");
                } else if (bean.getOrderType() == 2) {
                    ((IViewHolder) holder).orderTyleTextView.setText("糖果0元兑-已拒绝");
                } else if (bean.getOrderType() == 3) {
                    ((IViewHolder) holder).orderTyleTextView.setText("砍价随意拿-已拒绝");
                } else {
                    ((IViewHolder) holder).orderTyleTextView.setText("已拒绝");
                }

                ((IViewHolder) holder).order_del.setVisibility(View.VISIBLE);
                ((IViewHolder) holder).order_del.setTag(String.format("%d,%d", position, SHANCHUDINGDAN));
                ((IViewHolder) holder).order_del.setOnClickListener(this);

            } else if (bean.getStatus() == YISHIBAI) {
                // TODO 失败
                ((IViewHolder) holder).view.setVisibility(View.GONE);


                if (bean.getOrderType() == 0) {
                    ((IViewHolder) holder).orderTyleTextView.setText("已失败");
                } else if (bean.getOrderType() == 1) {
                    ((IViewHolder) holder).orderTyleTextView.setText("折扣订单-已失败");
                } else if (bean.getOrderType() == 2) {
                    ((IViewHolder) holder).orderTyleTextView.setText("糖果0元兑-已失败");
                } else if (bean.getOrderType() == 3) {
                    ((IViewHolder) holder).orderTyleTextView.setText("砍价随意拿-已失败");
                } else {
                    ((IViewHolder) holder).orderTyleTextView.setText("已失败");
                }

                ((IViewHolder) holder).order_del.setVisibility(View.VISIBLE);
                ((IViewHolder) holder).order_del.setTag(String.format("%d,%d", position, SHANCHUDINGDAN));
                ((IViewHolder) holder).order_del.setOnClickListener(this);
            }

            try {
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(bean.getLogo()).into(((IViewHolder) holder).imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            if (((IViewHolder) holder).contentLayout.getChildCount() > 0) {
//                ((IViewHolder) holder).contentLayout.removeAllViews();
//            }
            ((IViewHolder) holder).contentLayout.removeAllViews();
            List<OrderBean.DataBean.OrdersListBean.ItemBean> items = bean.getItem();
            for (int i = 0; i < items.size(); i++) {
                OrderBean.DataBean.OrdersListBean.ItemBean itemBean = null;
                View view = null;
                ImageView goods_pic = null;
                TextView goods_name = null;
                TextView goods_color = null;
                TextView goods_price = null;
                TextView goods_count = null;
                StringBuffer specificationItems = null;
                try {
                    itemBean = items.get(i);
                    view = View.inflate(mContext, R.layout.item_goods, null);
                    goods_pic = view.findViewById(R.id.goods_pic);
                    goods_name = view.findViewById(R.id.goods_name);
                    goods_color = view.findViewById(R.id.goods_color);
                    goods_price = view.findViewById(R.id.goods_price);
                    goods_count = view.findViewById(R.id.goods_count);
                    specificationItems = new StringBuffer();
                    for (String content : itemBean.getSpecificationItems()) {
                        specificationItems.append(content).append(" ");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_comment)).load(itemBean.getThumbnail()).into(goods_pic);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    goods_name.setText(itemBean.getItemName());
                    goods_color.setText(specificationItems);
                    if (itemBean.getPaymentModel() == 0) {
                        goods_price.setText(String.format("￥%s", itemBean.getPrice()));
                    } else {
                        goods_price.setText(String.format("￥%s+BC%s", itemBean.getPartPrice(), itemBean.getPartBtAmount()));
                    }

                    goods_count.setText(String.format("X%d", itemBean.getQuantity()));
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                layoutParams.setMargins(0, 5, 0, 5);
                    view.setTag(String.format("%d#%d,%d", position, i, CHAKANXIANGQING));
                    view.setOnClickListener(this);
                    ((IViewHolder) holder).contentLayout.addView(view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    private static final int REAL_ORDER = 0x01;
    private static final int VIRTUAL_ORDER = 0x02;


    @Override
    public int getItemViewType(int position) {
        return list.get(position).getOrderProductType() == 1 ? VIRTUAL_ORDER : REAL_ORDER;
    }

    private void hideView(IViewHolder holder) {
        holder.order_buy_again.setVisibility(View.GONE);
        holder.order_apply_after_sale.setVisibility(View.GONE);
        holder.order_check_logistics.setVisibility(View.GONE);
        holder.order_appraise.setVisibility(View.GONE);
        holder.order_confirm_receipt.setVisibility(View.GONE);
        holder.order_cancel.setVisibility(View.GONE);
        holder.order_go_pay.setVisibility(View.GONE);
        holder.order_del.setVisibility(View.GONE);
        holder.order_check_info.setVisibility(View.GONE);

        holder.order_status_view.setVisibility(View.GONE);
        holder.order_info_view.setVisibility(View.GONE);

    }


    private void hideView(VirtualHolder holder) {
        holder.order_buy_again.setVisibility(View.GONE);
        holder.order_apply_after_sale.setVisibility(View.GONE);
        holder.order_check_logistics.setVisibility(View.GONE);
        holder.order_appraise.setVisibility(View.GONE);
        holder.order_confirm_receipt.setVisibility(View.GONE);
        holder.order_cancel.setVisibility(View.GONE);
        holder.order_go_pay.setVisibility(View.GONE);
        holder.order_del.setVisibility(View.GONE);
        holder.order_check_info.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        String[] types = tag.split(",");
        int type = Integer.parseInt(types[1]);
        switch (type) {
            case SHENQINGSHOUHOU:
                if (onChildClickListener != null) {
                    onChildClickListener.shenQingShouHou(Integer.parseInt(types[0]));
                }
                break;
            case QUXIAO:
                if (onChildClickListener != null) {
                    onChildClickListener.quXiao(Integer.parseInt(types[0]));
                }
                break;
            case QUFUKUAN:
                if (onChildClickListener != null) {
                    onChildClickListener.quFuKuan(Integer.parseInt(types[0]));
                }
                break;
            case CHAKANWULIU:
                if (onChildClickListener != null) {
                    onChildClickListener.chaKanWuLiu(Integer.parseInt(types[0]));
                }
                break;
            case SHAIDANPINGJIA:
                if (onChildClickListener != null) {
                    onChildClickListener.shaiDanPingJia(Integer.parseInt(types[0]));
                }
                break;
            case ZAICIGOUMAI:
                if (onChildClickListener != null) {
                    onChildClickListener.zaiCiGouMai(Integer.parseInt(types[0]));
                }
                break;
            case SHANCHUDINGDAN:
                if (onChildClickListener != null) {
                    onChildClickListener.shanChuDingDan(Integer.parseInt(types[0]));
                }
                break;
            case QUERENSHOUHUO:
                if (onChildClickListener != null) {
                    onChildClickListener.queRenShouHuo(Integer.parseInt(types[0]));
                }
                break;
            case CHAKANXIANGQING:
                if (onChildClickListener != null) {
                    String[] content = types[0].split("#");
                    onChildClickListener.chaKanXiangQing(Integer.parseInt(content[0]), Integer.parseInt(content[1]));
                }
                break;
        }

//        StarActivityUtil.starActivity((Activity) mContext, OrderDetailsActivity.class);

    }


    class VirtualHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.goods_pic)
        ImageView imgview;
        @BindView(R.id.goods_name)
        TextView name;
        @BindView(R.id.goods_price)
        TextView price;
        @BindView(R.id.goods_count)
        TextView count;

        // 申请售后
        @BindView(R.id.order_apply_after_sale)
        Button order_apply_after_sale;
        // 查看物流
        @BindView(R.id.order_check_logistics)
        Button order_check_logistics;
        // 晒单评价
        @BindView(R.id.order_appraise)
        Button order_appraise;
        // 确认收货
        @BindView(R.id.order_confirm_receipt)
        Button order_confirm_receipt;
        // 取消
        @BindView(R.id.order_cancel)
        Button order_cancel;
        // 去付款
        @BindView(R.id.order_go_pay)
        Button order_go_pay;
        // 删除订单
        @BindView(R.id.order_del)
        Button order_del;
        // 再次购买
        @BindView(R.id.order_buy_again)
        Button order_buy_again;
        // 查看详情
        @BindView(R.id.order_check_info)
        Button order_check_info;

        @BindView(R.id.order_footer_view)
        View view;
        @BindView(R.id.order_type)
        TextView order_type;

        @BindView(R.id.goods_view)
        LinearLayout goods_view;


        public VirtualHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
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

        // 申请售后
        @BindView(R.id.order_apply_after_sale)
        Button order_apply_after_sale;
        // 查看物流
        @BindView(R.id.order_check_logistics)
        Button order_check_logistics;
        // 晒单评价
        @BindView(R.id.order_appraise)
        Button order_appraise;
        // 确认收货
        @BindView(R.id.order_confirm_receipt)
        Button order_confirm_receipt;
        // 取消
        @BindView(R.id.order_cancel)
        Button order_cancel;
        // 去付款
        @BindView(R.id.order_go_pay)
        Button order_go_pay;
        // 删除订单
        @BindView(R.id.order_del)
        Button order_del;
        // 再次购买
        @BindView(R.id.order_buy_again)
        Button order_buy_again;
        // 查看详情
        @BindView(R.id.order_check_info)
        Button order_check_info;


        @BindView(R.id.order_info_view)
        LinearLayout order_info_view;

        // 订单状态View
        @BindView(R.id.order_status_view)
        LinearLayout order_status_view;
        // 订单状态
        @BindView(R.id.order_status)
        TextView order_status;
        // 大熊订单号
        @BindView(R.id.order_number)
        TextView order_number;
        // 订单时间
        @BindView(R.id.order_time)
        TextView order_time;

        @BindView(R.id.order_footer_view)
        View view;


        public IViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    private OnChildClickListener onChildClickListener;

    public void setOnChildClickListener(OnChildClickListener listener) {
        onChildClickListener = listener;
    }


    public interface OnChildClickListener {
        void quXiao(int index);

        void quFuKuan(int index);

        void zaiCiGouMai(int index);

        void chaKanWuLiu(int index);

        void queRenShouHuo(int index);

        void shaiDanPingJia(int index);

        void shanChuDingDan(int index);

        void shenQingShouHou(int index);

        void chaKanXiangQing(int index, int childIndex);


    }


    @IntDef({SHENQINGSHOUHOU, QUXIAO, QUFUKUAN, CHAKANWULIU, SHAIDANPINGJIA, ZAICIGOUMAI, SHANCHUDINGDAN, QUERENSHOUHUO, CHAKANXIANGQING})
    public @interface ButtonType {
        int SHENQINGSHOUHOU = 0;
        int QUXIAO = 2;
        int QUFUKUAN = 3;
        int CHAKANWULIU = 4;
        int SHAIDANPINGJIA = 5;
        int ZAICIGOUMAI = 6;
        int SHANCHUDINGDAN = 7;
        int QUERENSHOUHUO = 8;
        int CHAKANXIANGQING = 9;
    }

}

package com.yunqin.bearmall.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newversions.detail.NewProductDetailActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.ActivityMessage;
import com.yunqin.bearmall.bean.BCMessage;
import com.yunqin.bearmall.bean.BaseMessage;
import com.yunqin.bearmall.bean.DealMessage;
import com.yunqin.bearmall.bean.SystemMessage;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.LogisticsActivity;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenchen on 2018/8/13.
 */

public class MessageAdapter<T extends BaseMessage> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<T> datas;
    private Context context;

    private int MessageType = -1;

    private View.OnClickListener iOnClickListener;

    private static final int PRODUCT_MESSAGE_TYPE = 100;

    public MessageAdapter(List<T> datas, Context context, int MessageType) {
        this.datas = datas;
        this.context = context;
        this.MessageType = MessageType;
    }

    public List<T> getData() {
        return datas;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.iOnClickListener = onClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_message_bc, parent, false);

            return new BCViewHolder(view);
        } else if (viewType == 1) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_message_deal, parent, false);

            return new DealViewHolder(view);

        } else if (viewType == 2) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_message_deal_goods, parent, false);

            return new DealViewHolder(view);

        } else if (viewType == 3) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_message_system, parent, false);

            return new SystemViewHolder(view);

        } else if (viewType == PRODUCT_MESSAGE_TYPE) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_product_message_activity, parent, false);
            return new ProductMessageHolder(view);

        } else if (viewType == 4) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_message_activity, parent, false);

            return new ActivityViewHolder(view);
        } else {

            View view = LayoutInflater.from(context).inflate(R.layout.layout_empty_view, parent, false);

            return new EmptyViewHolder(view);

        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        try {

            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(this);

            int type = getItemViewType(position);

            switch (type) {
                case PRODUCT_MESSAGE_TYPE:
                    try {
                        SystemMessage systemMessage = (SystemMessage) datas.get(position);
                        ProductMessageHolder productMessageHolder = (ProductMessageHolder) holder;
                        productMessageHolder.product_message_time.setText(systemMessage.getOperDate());
                        productMessageHolder.product_message_title.setText(systemMessage.getTitle());
                        productMessageHolder.product_message_content.setText(systemMessage.getContent());
                        Glide.with(context).load(systemMessage.getProductImage()).into(productMessageHolder.product_message_img);

                        productMessageHolder.itemView.setOnClickListener(view -> {
                            Intent intent = new Intent(context, NewProductDetailActivity.class);
                            intent.putExtra("productId", "" + systemMessage.getProduct_id());
                            intent.putExtra("sku_id", "");
                            context.startActivity(intent);
                        });
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    break;

                case 0:

                    handleBCHolder(holder, position);

                    break;

                case 1:

                    handleDealHolder(holder, position);

                    break;

                case 2:

                    handleDealGoodsHolder(holder, position);

                    break;


                case 3:

                    handleSystemHolder(holder, position);

                    break;


                case 4:

                    handleActivityHolder(holder, position);

                    break;

                case 5:

                    handleEmptyHolder(holder);

                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void handleEmptyHolder(RecyclerView.ViewHolder holder) {
        EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;
        if (MessageType == 0 || MessageType == 1) {

            if (BearMallAplication.getInstance().getUser() == null) {
                emptyViewHolder.login_btn.setVisibility(View.VISIBLE);
                emptyViewHolder.empty_text.setText("请先登录");
            } else {
                emptyViewHolder.empty_text.setText("暂无消息");
                emptyViewHolder.login_btn.setVisibility(View.GONE);
            }

        } else {
            emptyViewHolder.empty_text.setText("暂无消息");
            emptyViewHolder.login_btn.setVisibility(View.GONE);
        }
        emptyViewHolder.login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.starActivity((Activity) context);
            }
        });
    }

    private void handleBCHolder(RecyclerView.ViewHolder holder, int position) {

        BCViewHolder mHolder = (BCViewHolder) holder;
        BCMessage message = (BCMessage) datas.get(position);
        mHolder.titleView.setText(message.getTitle());
        mHolder.descView.setText(message.getCaption());
        mHolder.bcNumView.setText(message.getValue());
        mHolder.bottomView.setText("挖矿时间：" + message.getCreatedDate());
        mHolder.timeView.setText(message.getOperDate());

    }

//    0：支付失败  1：支付成功
//2：订单取消  3：订单完成
//4：订单发货  5：订单退货
//6: 订单退款

    private void handleDealHolder(RecyclerView.ViewHolder holder, int position) {

        DealViewHolder mHolder = (DealViewHolder) holder;
        DealMessage message = (DealMessage) datas.get(position);

        mHolder.titleView.setText(message.getTitle());
        mHolder.descView.setText("订单编号：" + message.getSn());
        mHolder.preceView.setText(message.getValue());
        switch (message.getOrdersStatus()) {
            case 0:
                mHolder.statusView.setText("交易状态：支付失败");
                break;

            case 1:
                mHolder.statusView.setText("交易状态：支付成功");
                break;

            case 2:
                mHolder.statusView.setText("交易状态：订单取消");
                break;

            case 3:
                mHolder.statusView.setText("交易状态：订单完成");
                break;

            case 4:
                mHolder.statusView.setText("交易状态：订单发货");
                break;

            case 5:
                mHolder.statusView.setText("交易状态：订单退货");
                break;

            case 6:
                mHolder.statusView.setText("交易状态：订单退款");
                break;

        }
        mHolder.timeView.setText(message.getOperDate());
        mHolder.tnView.setText("交易单号：" + message.getOutTradeNo());
        if (message.getOrdersStatus() == 4) {
            mHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogisticsActivity.start(context, message.getOrders_id());
                }
            });
        }

    }

    private void handleDealGoodsHolder(RecyclerView.ViewHolder holder, int position) {

        DealViewHolder mHolder = (DealViewHolder) holder;
        DealMessage message = (DealMessage) datas.get(position);
        mHolder.timeView.setText(message.getOperDate());
        mHolder.titleView.setText(message.getTitle());
        mHolder.descView.setText("订单编号：" + message.getSn());

        if (mHolder.goodsImage != null) {
//            Glide.with(context).load(message.getProductImage().getThumbnail()).into(mHolder.goodsImage);
            Glide.with(context).load(message.getProductImage()).into(mHolder.goodsImage);
        }
        if (mHolder.goodsTitle != null) {
            mHolder.goodsTitle.setText(message.getProductName());
        }
        if (mHolder.goods_count != null) {
            mHolder.goods_count.setText("* " + message.getQuantity());
        }
        if (mHolder.goodsInfo != null) {

            List<String> infos = message.getSpecifications();

            StringBuffer sb = new StringBuffer();
            for (String s :
                    infos) {
                sb.append(s + "  ");
            }

            mHolder.goodsInfo.setText(sb.toString());

        }
        if (mHolder.tnView != null) {

            switch (message.getOrdersStatus()) {

                case 0:
                    mHolder.tnView.setText("支付失败");
                    break;

                case 1:
                    mHolder.tnView.setText("支付成功");
                    break;

                case 2:
                    mHolder.tnView.setText("订单取消");
                    break;

                case 3:
                    mHolder.tnView.setText("订单完成");
                    break;

                case 4:
                    mHolder.tnView.setText("订单发货");
                    break;

                case 5:
                    mHolder.tnView.setText("订单退货");
                    break;

                case 6:
                    mHolder.tnView.setText("订单退款");
                    break;

            }

        }

        if (message.getOrdersStatus() == 4) {
            mHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogisticsActivity.start(context, message.getOrders_id());
                }
            });
        }


    }

    private void handleSystemHolder(RecyclerView.ViewHolder holder, int position) {

        SystemViewHolder mHolder = (SystemViewHolder) holder;
        SystemMessage message = (SystemMessage) datas.get(position);

        mHolder.titleView.setText(message.getTitle());
        mHolder.descView.setText(message.getContent());
        mHolder.timeView.setText(message.getOperDate());


    }

    private void handleActivityHolder(RecyclerView.ViewHolder holder, int position) {

        ActivityViewHolder mHolder = (ActivityViewHolder) holder;

        ActivityMessage message = (ActivityMessage) datas.get(position);

        mHolder.titleView.setText(message.getTitle());

        mHolder.descView.setText(message.getArticleDescription());

        Glide.with(context).load(message.getArticleImage()).into(mHolder.imageView);

        mHolder.timeView.setText(message.getOperDate());


    }


    @Override
    public int getItemCount() {
        return (datas == null || datas.isEmpty()) ? 1 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (datas == null || datas.isEmpty()) {
            return 5;
        }
        BaseMessage message = datas.get(position);
        if (message.type == 5) {
            DealMessage m = (DealMessage) datas.get(position);
            switch (m.getOrdersStatus()) {
                case 0:
                case 1:
                case 3:
                    message.type = 1;
                    break;

                default:
                    message.type = 2;
                    break;

            }
        } else if (message.type == 3) {
            SystemMessage systemMessage = (SystemMessage) datas.get(position);
            if (systemMessage.getModel() == 101) {
                return PRODUCT_MESSAGE_TYPE;
            }
        }
        return message.type;
    }

    @Override
    public void onClick(View v) {
        if (iOnClickListener != null) {
            iOnClickListener.onClick(v);
        }
    }


    class BCViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView titleView;
        @BindView(R.id.desc)
        TextView descView;
        @BindView(R.id.bc_num)
        TextView bcNumView;
        @BindView(R.id.bottom_text)
        TextView bottomView;
        @BindView(R.id.time_text)
        TextView timeView;

        public BCViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class DealViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.time_text)
        TextView timeView;
        @BindView(R.id.title)
        TextView titleView;
        @Nullable
        @BindView(R.id.desc)
        TextView descView;
        @Nullable
        @BindView(R.id.deal_price)
        TextView preceView;
        @Nullable
        @BindView(R.id.status)
        TextView statusView;
        @Nullable
        @BindView(R.id.bottom_text)
        TextView tnView;

        @Nullable
        @BindView(R.id.goods_image)
        ImageView goodsImage;
        @Nullable
        @BindView(R.id.goods_title)
        TextView goodsTitle;
        @Nullable
        @BindView(R.id.goods_count)
        TextView goods_count;
        @Nullable
        @BindView(R.id.goods_info)
        TextView goodsInfo;

//        ConstraintLayout layout_order;

        public DealViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            layout_order = itemView.findViewById(R.id.layout_order);
        }
    }


    class SystemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.time_text)
        TextView timeView;
        @BindView(R.id.title)
        TextView titleView;
        @BindView(R.id.desc)
        TextView descView;

        public SystemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class ActivityViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.time_text)
        TextView timeView;
        @BindView(R.id.title)
        TextView titleView;
        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.desc)
        TextView descView;


        public ActivityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.login_btn)
        HighlightButton login_btn;

        @BindView(R.id.empty_text)
        TextView empty_text;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    /**
     * 跳转到商品详情的ViewHolder
     */
    class ProductMessageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_message_time)
        TextView product_message_time;

        @BindView(R.id.product_message_title)
        TextView product_message_title;

        @BindView(R.id.product_message_content)
        TextView product_message_content;

        @BindView(R.id.product_message_img)
        ImageView product_message_img;


        public ProductMessageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}

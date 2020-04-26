package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.PddBean;
import com.yunqin.bearmall.bean.TaoBaoBeanNew;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.adapter
 * @DATE 2019/11/27
 */
public class PddAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<PddBean.DataBean> list;
    private final int TYPE_NORMAL = 0;
    private final int TYPE_INVALID = 1;
    private RequestOptions mOptions = new RequestOptions()
            .placeholder(R.drawable.default_product)//图片加载出来前，显示的图片
            .fallback(R.drawable.default_product) //url为空的时候,显示的图片
            .error(R.drawable.default_product)//图片加载失败后，显示的图片
            .bitmapTransform(new RoundedCorners(3));
    private int type;

    public PddAdapter(Context context, int type) {
        this.mContext = context;
        list = new ArrayList<>();
        this.type = type;
    }

    @Override
    public int getItemViewType(int position) {
        if (type == 4) {
            return TYPE_INVALID;
        }
        return TYPE_NORMAL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_INVALID:
                View view = LayoutInflater.from(mContext).inflate(R.layout.orderincalidtime_layout, parent, false);
                return new InvalidOrderHolder(view);
            case TYPE_NORMAL:
                View view2 = LayoutInflater.from(mContext).inflate(R.layout.orderitem_layout, parent, false);
                return new OrderHolder(view2);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_INVALID:
                InvalidOrderHolder invalidOrderHolder = (InvalidOrderHolder) holder;
                invalidOrderHolder.mCreat_time.setText("创建时间：" + list.get(position).getCreateTime());
                invalidOrderHolder.state.setText(setText(list.get(position).getOrderStatus()));
                Glide.with(mContext).load(list.get(position).getImage()).apply(mOptions).into(invalidOrderHolder.image);
                invalidOrderHolder.price.setText("¥" + list.get(position).getPayAmount());
                Double aDouble = Double.valueOf(list.get(position).getEffectEstimate());
                BigDecimal bigDecimal = new BigDecimal(aDouble);
                String mon = bigDecimal.setScale(2, RoundingMode.DOWN).toString();
                invalidOrderHolder.commission.setText("预估返佣" + mon + "元");
                invalidOrderHolder.commission.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中划线
                invalidOrderHolder.commission.getPaint().setAntiAlias(true); //去掉锯齿
                invalidOrderHolder.order.setText("订单编号：" + list.get(position).getOrderNo());
                invalidOrderHolder.title.setText(list.get(position).getItemName());
                invalidOrderHolder.copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onChildClickListener != null) {
                            onChildClickListener.onCopyOrderId(list.get(position).getOrderNo());
                        }
                    }
                });

                break;
            case TYPE_NORMAL:
                OrderHolder orderHolder = (OrderHolder) holder;
                orderHolder.mCreat_time.setText("创建时间：" + list.get(position).getCreateTime());
                orderHolder.state.setText(setText(list.get(position).getOrderStatus()));
                Glide.with(mContext).load(list.get(position).getImage()).apply(mOptions).into(orderHolder.image);
                orderHolder.price.setText("¥" + list.get(position).getPayAmount());
                orderHolder.commission.setText("预估返佣" + list.get(position).getEffectEstimate() + "元");
                orderHolder.order.setText("订单编号：" + list.get(position).getOrderNo());
                orderHolder.title.setText(list.get(position).getItemName());
                orderHolder.copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onChildClickListener != null) {
                            onChildClickListener.onCopyOrderId(list.get(position).getOrderNo());
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<PddBean.DataBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.list.clear();
    }


    private class OrderHolder extends RecyclerView.ViewHolder {

        private final TextView mCreat_time, state, title, price, commission, order, copy;
        private final ImageView image;

        public OrderHolder(View itemView) {
            super(itemView);
            mCreat_time = itemView.findViewById(R.id.creat_time);
            state = itemView.findViewById(R.id.state);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            commission = itemView.findViewById(R.id.commission);
            order = itemView.findViewById(R.id.order);
            copy = itemView.findViewById(R.id.copy);
            image = itemView.findViewById(R.id.image);
        }

    }

    private class InvalidOrderHolder extends RecyclerView.ViewHolder {

        private final TextView mCreat_time, state, title, price, commission, order, copy;
        private final ImageView image;

        public InvalidOrderHolder(View itemView) {
            super(itemView);
            mCreat_time = itemView.findViewById(R.id.creat_time);
            state = itemView.findViewById(R.id.state);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            commission = itemView.findViewById(R.id.commission);
            order = itemView.findViewById(R.id.order);
            copy = itemView.findViewById(R.id.copy);
            image = itemView.findViewById(R.id.image);
        }
    }


    private OnChildClickListener onChildClickListener;

    public interface OnChildClickListener {
        void onCopyOrderId(String oderid);

    }

    public void setOnChildClickListener(OnChildClickListener childClickListener) {
        onChildClickListener = childClickListener;
    }


    public String setText(int type) {
        if (type == -1) {
            return "未支付";
        }
        if (type == 2) {
            return "待返佣";
        }
        if (type == 3) {
            return "已到账";
        }
        if (type == 4) {
            return "已失效";
        }
        return "未知";
    }
}

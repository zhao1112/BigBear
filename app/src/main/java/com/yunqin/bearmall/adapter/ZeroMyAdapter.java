package com.yunqin.bearmall.adapter;

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
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.ZeroMyItemBean;
import com.yunqin.bearmall.inter.ChangeCallBack;
import com.yunqin.bearmall.ui.activity.OrderDetailsActivity;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author AYWang
 * @create 2018/8/2
 * @Describe
 */
public class ZeroMyAdapter extends RecyclerView.Adapter {

    private List<ZeroMyItemBean.DataBean.MemberGroupRecordListBean> mlist;
    private final Context mContext;
    private final LayoutInflater mInflater;

    public ZeroMyAdapter(Context mContext, List<ZeroMyItemBean.DataBean.MemberGroupRecordListBean> mlist, ChangeCallBack changeCallBack) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mlist = mlist;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MYViewHolder(mInflater.inflate(R.layout.item_zero_my_get, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            ZeroMyItemBean.DataBean.MemberGroupRecordListBean recordListBean = mlist.get(position);
            if (holder instanceof MYViewHolder) {
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(recordListBean.getProductImages().getThumbnail()).into(((MYViewHolder) holder).goods_img);
                ((MYViewHolder) holder).goods_name.setText(recordListBean.getProductName());

                if (recordListBean.getRewardStatus() == 0 || recordListBean.getRewardStatus() == 1) {
                    ((MYViewHolder) holder).goods_desc.setText("兑换成功，商家正在处理");

                    ((MYViewHolder) holder).look_order.setEnabled(false);
                    ((MYViewHolder) holder).look_order.setTextColor(Color.parseColor("#afafaf"));

                } else if (recordListBean.getRewardStatus() == 2) {

                    ((MYViewHolder) holder).look_order.setEnabled(true);
                    ((MYViewHolder) holder).look_order.setTextColor(Color.parseColor("#23A064"));

                    if (recordListBean.getOrderStatus() == 2) {
                        ((MYViewHolder) holder).goods_desc.setText("兑换成功，等待发货");
                    } else if (recordListBean.getOrderStatus() == 3) {
                        ((MYViewHolder) holder).goods_desc.setText("兑换成功，等待收货");
                    } else if (recordListBean.getOrderStatus() == 4 || recordListBean.getOrderStatus() == 5) {
                        ((MYViewHolder) holder).goods_desc.setText("兑换完成");
                    } else if (recordListBean.getOrderStatus() == 6 || recordListBean.getOrderStatus() == 7 || recordListBean.getOrderStatus() == 8) {
                        ((MYViewHolder) holder).goods_desc.setText("兑换失败");
                    }
                } else if (recordListBean.getRewardStatus() == 3) {
                    ((MYViewHolder) holder).goods_desc.setText("兑换失败");

                    ((MYViewHolder) holder).look_order.setEnabled(false);
                    ((MYViewHolder) holder).look_order.setTextColor(Color.parseColor("#afafaf"));

                }


                ((MYViewHolder) holder).look_order.setTag(recordListBean.getOrders_id());
                ((MYViewHolder) holder).look_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        long orderId = (long) v.getTag();

                        OrderDetailsActivity.start(mContext, (int) orderId, -11, 0);


                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return mlist.size();
    }


    class MYViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.goods_img)
        ImageView goods_img;

        @BindView(R.id.goods_name)
        TextView goods_name;

        @BindView(R.id.goods_desc)
        TextView goods_desc;

        @BindView(R.id.look_order)
        HighlightButton look_order;


        public MYViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}

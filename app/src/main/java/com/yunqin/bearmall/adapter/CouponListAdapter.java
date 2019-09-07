package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.Coupon;
import com.yunqin.bearmall.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class CouponListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Coupon> coupons;
    private OnItemClickListener onItemClickListener;

    public CouponListAdapter(Context context, List<Coupon> coupons, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.coupons = coupons;
        this.onItemClickListener = onItemClickListener;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            Coupon coupon = coupons.get(position);
            if (onItemClickListener != null) {
                onItemClickListener.onClick(position, coupon.getType(), coupon);
            }
        }
    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_coupon, parent, false);

            return new ViewHolder(view);

        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.empty_layout, parent, false);
            return new EmptyViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(onClickListener);
            ViewHolder viewHolder = (ViewHolder) holder;
            Coupon coupon = coupons.get(position);
            String title = coupon.getTitle();
            viewHolder.couponNameView.setText(TextUtils.isEmpty(title) ? "" : title);
            int leftDate = coupon.getExpireDays();
            if (leftDate == -1) {
                viewHolder.couponDateView.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.couponDateView.setVisibility(View.VISIBLE);
                if (!coupon.isUsable()) {
                    viewHolder.couponDateView.setText("当前优惠券不在可用期内！");
                } else {
                    viewHolder.couponDateView.setVisibility(View.INVISIBLE);
                    viewHolder.couponDateView.setText("还剩 " + leftDate + " 天");
                }

            }
            String limitDate = coupon.getLimitDate();
            viewHolder.leftDateView.setText("有效期 " + (TextUtils.isEmpty(limitDate) ? "" : limitDate));
            int status = coupon.getStatus();
            if (status == 0) {
                viewHolder.container.setBackground(context.getResources().getDrawable(R.drawable.voucher_bg_do));
                viewHolder.vLIne.setBackground(context.getResources().getDrawable(R.drawable.voucher_line));
                viewHolder.couponNameView.setTextColor(Color.parseColor("#333333"));
                viewHolder.couponDateView.setTextColor(Color.parseColor("#23A064"));
                viewHolder.statusView.setTextColor(Color.parseColor("#23A064"));
                viewHolder.statusView.setText("立即使用");
            } else if (status == 1 || status == 2) {
                viewHolder.container.setBackground(context.getResources().getDrawable(R.drawable.voucher_bg_done));
                viewHolder.vLIne.setBackground(context.getResources().getDrawable(R.drawable.voucher_line_gray));
                viewHolder.couponNameView.setTextColor(Color.parseColor("#999999"));
                viewHolder.couponDateView.setTextColor(Color.parseColor("#999999"));
                viewHolder.statusView.setTextColor(Color.parseColor("#999999"));
                if (status == 1) {
                    viewHolder.statusView.setText("已使用");
                } else {
                    viewHolder.statusView.setText("已过期");
                }
            }
        }

    }

    //1 没网或者空
    @Override
    public int getItemViewType(int position) {

        if (coupons == null || coupons.isEmpty()) {
            return 1;
        }

        return 0;
    }

    @Override
    public int getItemCount() {
        return (coupons == null || coupons.isEmpty()) ? 1 : coupons.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.container)
        View container;
        @BindView(R.id.coupon_name)
        TextView couponNameView;
        @BindView(R.id.remain_date)
        TextView couponDateView;
        @BindView(R.id.left_date)
        TextView leftDateView;
        @BindView(R.id.status)
        TextView statusView;
        @BindView(R.id.lin_line)
        View vLIne;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface OnItemClickListener {

        void onClick(int position, int clickTicketType, Coupon coupon);

    }

}

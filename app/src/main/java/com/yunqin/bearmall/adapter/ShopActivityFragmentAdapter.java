package com.yunqin.bearmall.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.ShopActivityBean;
import com.yunqin.bearmall.inter.ShopActCallBack;
import com.yunqin.bearmall.ui.activity.BargainFreeDetailActivity;
import com.yunqin.bearmall.ui.activity.ZeroMoneyDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author AYWang
 * @create 2018/8/2
 * @Describe
 */
public class ShopActivityFragmentAdapter extends BaseAdapter {

    private static final int TYPE_PINTUAN = 0;
    private static final int TYPE_KANJIA = 1;

    private List<ShopActivityBean.DataBean.ListBean> mlist;
    private final Context mContext;
    private final LayoutInflater mInflater;

    private ShopActCallBack shopActCallBack;


    public ShopActivityFragmentAdapter(Context mContext, List<ShopActivityBean.DataBean.ListBean> mlist, ShopActCallBack shopActCallBack) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mlist = mlist;
        this.shopActCallBack = shopActCallBack;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return mlist.get(position).getType() == 1 ? TYPE_PINTUAN : TYPE_KANJIA;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_PINTUAN) {
            convertView = getPinTuanView(position, convertView);
        } else if (viewType == TYPE_KANJIA) {
            convertView = getKanJiaView(position, convertView);
        }
        return convertView;
    }

    private View getKanJiaView(int position, View convertView) {
        kanJiaViewHolder kanJiaViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_bargain_free_product, null);
            kanJiaViewHolder = new kanJiaViewHolder(convertView);
            convertView.setTag(kanJiaViewHolder);
        } else {
            kanJiaViewHolder = (kanJiaViewHolder) convertView.getTag();
        }

        try {
            final ShopActivityBean.DataBean.ListBean reviewListBean = mlist.get(position);
            if (reviewListBean.getProductImages() != null && reviewListBean.getProductImages().getThumbnail() != null) {
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_comment)).load(reviewListBean.getProductImages().getThumbnail()).into(kanJiaViewHolder.productImg);
            }
            kanJiaViewHolder.productName.setText(reviewListBean.getProductName());

            kanJiaViewHolder.productPrice.setText("¥" + reviewListBean.getPrice());
            kanJiaViewHolder.productCount.setText("已有" + reviewListBean.getPersonalCount() + "人参加");

            kanJiaViewHolder.bargain_free_product_initiate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BargainFreeDetailActivity.class);
                    intent.putExtra(BargainFreeDetailActivity.BARGAIN_PRODUCT_ID, (long) reviewListBean.getBargainProduct_id());
                    intent.putExtra(BargainFreeDetailActivity.BARGAIN_IS_ONGOING, false);
                    ((Activity) mContext).startActivityForResult(intent, 1);
                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BargainFreeDetailActivity.class);
                    intent.putExtra(BargainFreeDetailActivity.BARGAIN_PRODUCT_ID, (long) reviewListBean.getBargainProduct_id());
                    intent.putExtra(BargainFreeDetailActivity.BARGAIN_IS_ONGOING, false);
                    ((Activity) mContext).startActivityForResult(intent, 1);
                }
            });

            return convertView;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    private View getPinTuanView(int position, View convertView) {
        pinTuanViewHolder pinTuanViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shop_activity_pintuan, null);
            pinTuanViewHolder = new pinTuanViewHolder(convertView);
            convertView.setTag(pinTuanViewHolder);
        } else {
            pinTuanViewHolder = (pinTuanViewHolder) convertView.getTag();
        }
        try {
            final ShopActivityBean.DataBean.ListBean reviewListBean = mlist.get(position);
            //item数据
            pinTuanViewHolder.goods_name.setText(reviewListBean.getProductName());
            pinTuanViewHolder.pintuan_number.setText(String.format("累计兑换%s份", reviewListBean.getTotalCount()));
            pinTuanViewHolder.tv_rmb.setText("¥" + reviewListBean.getPrice());
            pinTuanViewHolder.tv_bt.setText("¥0+BC" + reviewListBean.getCost());
//            changePintuanGuimo(pinTuanViewHolder.pintuan_guimo, reviewListBean.getGroupNumber());
            pinTuanViewHolder.people_number.setText("兑换价");
            Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(reviewListBean.getProductImages().getThumbnail()).into(pinTuanViewHolder.image_goods);
            pinTuanViewHolder.item_layout.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, ZeroMoneyDetailsActivity.class);
                intent.putExtra("groupPurchasing_id", reviewListBean.getGroupPurchasing_id() + "");
                mContext.startActivity(intent);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }


    public void changePintuanGuimo(TextView view, int number) {
        if (number == 10) {
            view.setTextColor(Color.parseColor("#D0021B"));
            view.setBackgroundResource(R.drawable.zero_10_bg);
        } else if (number == 20) {
            view.setTextColor(Color.parseColor("#F5A623"));
            view.setBackgroundResource(R.drawable.zero_20_bg);
        } else if (number == 30) {
            view.setTextColor(Color.parseColor("#4A90E2"));
            view.setBackgroundResource(R.drawable.zero_30_bg);
        } else if (number == 40) {
            view.setTextColor(Color.parseColor("#06e9c4"));
            view.setBackgroundResource(R.drawable.zero_40_bg);
        } else if (number == 50) {
            view.setTextColor(Color.parseColor("#23a064"));
            view.setBackgroundResource(R.drawable.zero_50_bg);
        }
        view.setText(number + "人团");
    }


    class pinTuanViewHolder {

        @BindView(R.id.image_goods)
        ImageView image_goods;

        @BindView(R.id.goods_name)
        TextView goods_name;

        @BindView(R.id.pintuan_number)
        TextView pintuan_number;

        @BindView(R.id.pintuan_guimo)
        TextView pintuan_guimo;

        @BindView(R.id.tv_rmb)
        TextView tv_rmb;

        @BindView(R.id.people_number)
        TextView people_number;

        @BindView(R.id.item_layout)
        LinearLayout item_layout;

        @BindView(R.id.tv_bt)
        TextView tv_bt;

        public pinTuanViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }


    class kanJiaViewHolder {
        @BindView(R.id.bargain_free_product_img)
        ImageView productImg;
        @BindView(R.id.bargain_free_product_name)
        TextView productName;
        @BindView(R.id.bargain_free_product_price)
        TextView productPrice;
        @BindView(R.id.bargain_free_product_count)
        TextView productCount;

        @BindView(R.id.bargain_free_product_initiate)
        TextView bargain_free_product_initiate;

        public kanJiaViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

}

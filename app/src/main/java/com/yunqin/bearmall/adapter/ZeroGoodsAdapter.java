package com.yunqin.bearmall.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newversions.IAdvClick;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.BannerBean;
import com.yunqin.bearmall.bean.ZeroGoodsBean;
import com.yunqin.bearmall.ui.activity.DailyTasksActivity;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.ZeroMoneyDetailsActivity;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.TopBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author AYWang
 * @create 2018/8/2
 * @Describe
 */
public class ZeroGoodsAdapter extends RecyclerView.Adapter {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;

    private List<ZeroGoodsBean.DataBean.GroupPurchasingListBean> mlist;
    List<String> list = new ArrayList<>();
    private final Context mContext;
    private final LayoutInflater mInflater;
    private BannerBean bannerData;
    private BannerBean.DataBean.AdMobileListBean adMobileListBean;

    public ZeroGoodsAdapter(Context mContext, List<ZeroGoodsBean.DataBean.GroupPurchasingListBean> mlist) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mlist = mlist;
    }

    public void setCenterAdUrl(BannerBean.DataBean.AdMobileListBean adMobileListBean) {
        this.adMobileListBean = adMobileListBean;
        notifyDataSetChanged();
    }

    public void setBannerData(BannerBean bannerData) {
        list.clear();
        this.bannerData = bannerData;
        if (bannerData.getData().getAdMobileList() == null || bannerData.getData().getAdMobileList().size() <= 0) {
            return;
        }
        for (int i = 0; i < bannerData.getData().getAdMobileList().size(); i++) {
            list.add(bannerData.getData().getAdMobileList().get(i).getImg());
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new MyTopViewHolder(mInflater.inflate(R.layout.item_zero_top_layout, parent, false));
            case TYPE_NORMAL:
                return new MyContentViewHolder(mInflater.inflate(R.layout.item_zero_goods, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof MyTopViewHolder) {
                try {
                    if (adMobileListBean != null && adMobileListBean.getImg() != null) {
                        Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_ad_one)).load(adMobileListBean.getImg())
                                .into(((MyTopViewHolder) holder).center_ad_img);

                        ((MyTopViewHolder) holder).center_ad_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                adClick(adMobileListBean);
                            }
                        });

                    }

                    if (list.size() > 0) {
                        ((MyTopViewHolder) holder).top_ad_img.setLayout(mContext);
                        ((MyTopViewHolder) holder).top_ad_img.setImagesUrl(list);
                        ((MyTopViewHolder) holder).top_ad_img.setOnItemClickListener(new TopBanner.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                BannerBean.DataBean.AdMobileListBean bean = bannerData.getData().getAdMobileList().get(position);
                                adClick(bean);
                            }
                        });
                    }
                } catch (Exception e) {

                }

                //toplayout数据
                ((MyTopViewHolder) holder).do_task.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (BearMallAplication.getInstance().getUser() == null) {
                            LoginActivity.starActivity((Activity) mContext);
                        } else {
                            StarActivityUtil.starActivity((Activity) mContext, DailyTasksActivity.class);
                        }
                    }
                });

            } else {
                try {
                    //item数据
                    ((MyContentViewHolder) holder).goods_name.setText(mlist.get(position - 1).getProductName());
                    ((MyContentViewHolder) holder).pintuan_number.setText(String.format("累计兑换%s份", mlist.get(position - 1).getTotalCount()));

                    ((MyContentViewHolder) holder).tv_rmb.setText("¥" + mlist.get(position - 1).getMembershipPrice());
                    ((MyContentViewHolder) holder).tv_bt.setText("¥0+BC" + mlist.get(position - 1).getCost());

                    ((MyContentViewHolder) holder).people_number.setText("兑换价");
                    Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(mlist.get(position - 1).getProductImages().getThumbnail()).into(((MyContentViewHolder) holder).image_goods);
                    ((MyContentViewHolder) holder).item_layout.setOnClickListener(v -> {
                        Intent intent = new Intent(mContext, ZeroMoneyDetailsActivity.class);
                        intent.putExtra("groupPurchasing_id", mlist.get(position - 1).getGroupPurchasing_id() + "");
                        mContext.startActivity(intent);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return mlist.size() + 1;
    }


    class MyTopViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.top_ad_img)
        TopBanner top_ad_img;

        @BindView(R.id.center_ad_img)
        ImageView center_ad_img;

        @BindView(R.id.do_task)
        LinearLayout do_task;


        public MyTopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    class MyContentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.goods_name)
        TextView goods_name;

        @BindView(R.id.pintuan_number)
        TextView pintuan_number;

        @BindView(R.id.tv_rmb)
        TextView tv_rmb;

        @BindView(R.id.tv_bt)
        TextView tv_bt;

        @BindView(R.id.people_number)
        TextView people_number;

        @BindView(R.id.image_goods)
        ImageView image_goods;

        @BindView(R.id.item_layout)
        LinearLayout item_layout;

        @BindView(R.id.pintuan_guimo)
        TextView pintuan_guimo;

        public MyContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void adClick(BannerBean.DataBean.AdMobileListBean adMobileListBean) {

        IAdvClick.click(mContext, adMobileListBean.getType(), adMobileListBean.getSkipType(), adMobileListBean.getSource_id(),null);


    }

}

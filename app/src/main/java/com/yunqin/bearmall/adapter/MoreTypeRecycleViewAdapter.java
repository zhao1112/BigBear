package com.yunqin.bearmall.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newversions.detail.NewProductDetailActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.HomeAd;
import com.yunqin.bearmall.bean.HomeBean;
import com.yunqin.bearmall.ui.activity.BargainFreeActivity;
import com.yunqin.bearmall.ui.activity.BargainFreeDetailActivity;
import com.yunqin.bearmall.ui.activity.ZeroMoneyActivity;
import com.yunqin.bearmall.ui.activity.ZeroMoneyDetailsActivity;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.LinearMenu;
import com.yunqin.bearmall.widget.TopBanner2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yunqin.bearmall.adapter.MoreTypeRecycleViewAdapter.FRAGMENT_TYPE.HEADER0;
import static com.yunqin.bearmall.adapter.MoreTypeRecycleViewAdapter.FRAGMENT_TYPE.HEADER1;
import static com.yunqin.bearmall.adapter.MoreTypeRecycleViewAdapter.FRAGMENT_TYPE.HEADER2;
import static com.yunqin.bearmall.adapter.MoreTypeRecycleViewAdapter.FRAGMENT_TYPE.HEADER3;
import static com.yunqin.bearmall.adapter.MoreTypeRecycleViewAdapter.FRAGMENT_TYPE.NORMAL;
import static com.yunqin.bearmall.adapter.MoreTypeRecycleViewAdapter.FRAGMENT_TYPE.NORMAL_;

/**
 * @author Master
 */
public class MoreTypeRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    /**
     * 头布局总数
     */
    private final int HEADER_COUNT = 4;

    private static View headView0;
    private static View headView1;
    private static View headView2;
    private static View headView3;


    @IntDef({HEADER0, HEADER1, HEADER2, HEADER3, NORMAL, NORMAL_})
    public @interface FRAGMENT_TYPE {
        int HEADER0 = 0;
        int HEADER1 = 1;
        int HEADER2 = 2;
        int HEADER3 = 3;
        int NORMAL = 100;
        int NORMAL_ = 200;
    }


    private Context mContext;
    private HomeBean mHomeBean;


    public void setData(HomeBean homeBean) {
        this.mHomeBean = homeBean;
        notifyDataSetChanged();
    }


    public void addData(HomeBean homeBean) {
        mHomeBean.getData().getList().addAll(homeBean.getData().getList());
        notifyDataSetChanged();
    }


    public MoreTypeRecycleViewAdapter(Context context, HomeBean homeBean) {
        this.mContext = context;
        this.mHomeBean = homeBean;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER0:
                headView0 = LayoutInflater.from(mContext).inflate(R.layout.main_banner, null, false);
                return new BannerHolder(headView0);
            case HEADER1:
                headView1 = LayoutInflater.from(mContext).inflate(R.layout.main_menu_4, null, false);
                return new LinearMenuHolder(headView1);
            case HEADER2:
                headView2 = LayoutInflater.from(mContext).inflate(R.layout.main_featured_first3, null, false);
                return new FeaturedFirstHolder3(headView2);
            case HEADER3:
                headView3 = LayoutInflater.from(mContext).inflate(R.layout.main_featured_first1, null, false);
                return new FeaturedFirstHolder1(headView3);
            case NORMAL:
                return new NormalHolder(View.inflate(parent.getContext(), R.layout.item_goods_show, null));
            case NORMAL_:
                return new NormalHolder_(View.inflate(parent.getContext(), R.layout.item_home_subject, null));
        }
        return null;
    }


    // TODO 要改回来
    private String[] linearMenuTitlesO = new String[]{"大熊拼拼拼", "砍价随意拿", "糖果礼遇", "每日任务"};
    private String[] linearMenuTitles = new String[]{"0元拼团", "砍价随意拿", "糖果夺宝", "每日任务"};
    private int[] linearMenuImages = new int[]{R.drawable.icon_pintuan, R.drawable.icon_kanjia, R.drawable.icon_candy, R.drawable.icon_renwu};


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        try {
            int viewType = getItemViewType(position);
            if (viewType == HEADER0) {
                return;
            } else if (viewType == HEADER1) {
                return;
            } else if (viewType == HEADER2) {
                return;
            } else if (viewType == HEADER3) {
                return;
            } else if (viewType == NORMAL) {
                //获取真正的位置
                final int realPostion = position - HEADER_COUNT;
                if (mOnItemClickLitener != null) {
                    holder.itemView.setOnClickListener(v -> {
                        int pos = realPostion;
                        startActivity((long) mHomeBean.getData().getList().get(pos).getProduct_id());
                        mOnItemClickLitener.onItemClick(holder.itemView, pos);
                    });

                    try {
                        Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product)).load(mHomeBean.getData().getList().get(realPostion).getProductImages().getLarge()).into(((NormalHolder) holder).imageView);
                    } catch (Exception e) {

                    }
                    //                Glide.with(mContext).load(mHomeBean.getData().getList().get(realPostion).getProductImages().getLarge()).into(((NormalHolder) holder).imageView);
                    ((NormalHolder) holder).item_good_name.setText(
                            mHomeBean.getData().getList().get(realPostion).getProductName());
                    ((NormalHolder) holder).tv_rmb.setText("¥" +
                            mHomeBean.getData().getList().get(realPostion).getPrice());
                    ((NormalHolder) holder).tv_bt.setText("¥" +
                            mHomeBean.getData().getList().get(realPostion).getPartPrice() +
                            "+BC" +
                            mHomeBean.getData().getList().get(realPostion).getPartBtAmount()
                    );

                }
            } else if (viewType == NORMAL_) {
                //获取真正的位置
                final int realPostion = position - HEADER_COUNT;

                if (mHomeBean.getData().getList().get(realPostion).getType() == 2) {
                    ((NormalHolder_) holder).huodong_title.setText("砍价随意拿专区");
                    ((NormalHolder_) holder).huodong_title_bottom_info.setText("邀请好友砍价，糖果免费送");
                    ((NormalHolder_) holder).huodong_more.setText("更多商品");
                    ((NormalHolder_) holder).bg_group.setBackgroundResource(R.drawable.kanjia_bg);

                    ((NormalHolder_) holder).uuu_tv_l.setText("直接购买");
                    ((NormalHolder_) holder).uuu_tv_r.setText("直接购买");


                } else {

                    ((NormalHolder_) holder).uuu_tv_l.setText("大熊零售价");
                    ((NormalHolder_) holder).uuu_tv_r.setText("大熊零售价");

                    ((NormalHolder_) holder).huodong_title.setText("0元拼团专区");
                    ((NormalHolder_) holder).huodong_title_bottom_info.setText("时时开奖，送糖果");
                    ((NormalHolder_) holder).huodong_more.setText("更多拼团");
                    ((NormalHolder_) holder).bg_group.setBackgroundResource(R.drawable.subject_bg);
                }

                ((NormalHolder_) holder).huodong_more.setTag(realPostion);
                ((NormalHolder_) holder).huodong_more.setOnClickListener(this);


                try {
                    int childCount = mHomeBean.getData().getList().get(realPostion).getList().size();
                    if (childCount >= 1) {

                        try {
                            ((NormalHolder_) holder).click_left.setTag(realPostion);
                            ((NormalHolder_) holder).click_left.setOnClickListener(this);
                            ((NormalHolder_) holder).click_right.setVisibility(View.INVISIBLE);


                            ((NormalHolder_) holder).tv_left_bt.setText("");


                            if (mHomeBean.getData().getList().get(realPostion).getType() == 2) {
                                ((NormalHolder_) holder).tv_left_bt.setText("发起砍价");


                                ((NormalHolder_) holder).tv_left_rmb.setText("¥" + mHomeBean
                                        .getData()
                                        .getList()
                                        .get(realPostion)
                                        .getList()
                                        .get(0)
                                        .getPrice()
                                );


                            } else {
                                ((NormalHolder_) holder).tv_left_bt.setText("发起拼团");


                                ((NormalHolder_) holder).tv_left_rmb.setText("¥" + mHomeBean
                                        .getData()
                                        .getList()
                                        .get(realPostion)
                                        .getList()
                                        .get(0)
                                        .getPartPrice()
                                        + "+BC" +
                                        mHomeBean
                                                .getData()
                                                .getList()
                                                .get(realPostion)
                                                .getList()
                                                .get(0)
                                                .getPartBtAmount());


                            }


                            Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(mHomeBean
                                    .getData()
                                    .getList()
                                    .get(realPostion)
                                    .getList()
                                    .get(0)
                                    .getProductImages()
                                    .getLarge())
                                    .into(((NormalHolder_) holder).img_left);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (childCount >= 2) {

                        try {
                            ((NormalHolder_) holder).click_right.setVisibility(View.VISIBLE);
                            ((NormalHolder_) holder).click_right.setTag(realPostion);
                            ((NormalHolder_) holder).click_right.setOnClickListener(this);

                            if (mHomeBean.getData().getList().get(realPostion).getType() == 2) {
                                ((NormalHolder_) holder).tv_right_bt.setText("发起砍价");


                                ((NormalHolder_) holder).tv_right_rmb.setText("¥" + mHomeBean
                                        .getData()
                                        .getList()
                                        .get(realPostion)
                                        .getList()
                                        .get(1)
                                        .getPrice());


                            } else {
                                ((NormalHolder_) holder).tv_right_bt.setText("发起拼团");

                                ((NormalHolder_) holder).tv_right_rmb.setText("¥" + mHomeBean
                                        .getData()
                                        .getList()
                                        .get(realPostion)
                                        .getList()
                                        .get(1)
                                        .getPartPrice()
                                        + "+BC" +
                                        mHomeBean
                                                .getData()
                                                .getList()
                                                .get(realPostion)
                                                .getList()
                                                .get(1)
                                                .getPartBtAmount());


                            }

//                            ((NormalHolder_) holder).tv_right_rmb.setText("¥" + mHomeBean
//                                    .getData()
//                                    .getList()
//                                    .get(realPostion)
//                                    .getList()
//                                    .get(1)
//                                    .getPartPrice()
//                                    + "+BC" +
//                                    mHomeBean
//                                            .getData()
//                                            .getList()
//                                            .get(realPostion)
//                                            .getList()
//                                            .get(1)
//                                            .getPartBtAmount());

                            Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(mHomeBean
                                    .getData()
                                    .getList()
                                    .get(realPostion)
                                    .getList()
                                    .get(1)
                                    .getProductImages()
                                    .getLarge())
                                    .into(((NormalHolder_) holder).img_right);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int getAdapterType(int position) {
        int type = getItemViewType(position);
        return type == NORMAL ? 1 : 2;
    }


    @Override
    public int getItemCount() {

        if (mHomeBean.getData().getList() != null) {
            return mHomeBean.getData().getList().size() + HEADER_COUNT;
        }

        return HEADER_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER0;
        } else if (position == 1) {
            return HEADER1;
        } else if (position == 2) {
            return HEADER2;
        } else if (position == 3) {
            return HEADER3;
        } else {
            if (mHomeBean.getData().getList().get(position - HEADER_COUNT).getType() == 0) {
                return NORMAL;
            } else {
                return NORMAL_;
            }

        }

    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        int type = mHomeBean.getData().getList().get(position).getType();
        String content = "";
        if (type == 1) {
            content = "拼团活动";
        } else {
            content = "砍价活动";
        }
        switch (v.getId()) {
            case R.id.click_left:
                try {
                    if (type == 1) {
                        Intent intent = new Intent(mContext, ZeroMoneyDetailsActivity.class);
                        int groupPurchasing_id = mHomeBean.getData().getList().get(position).getList().get(0).getGroupPurchasing_id();
                        intent.putExtra("groupPurchasing_id", groupPurchasing_id + "");
                        mContext.startActivity(intent);
                    } else {

                        Intent intent = new Intent(mContext, BargainFreeDetailActivity.class);
                        int product_id = mHomeBean.getData().getList().get(position).getList().get(0).getBargainProduct_id();
                        intent.putExtra(BargainFreeDetailActivity.BARGAIN_PRODUCT_ID, (long) product_id);
                        mContext.startActivity(intent);

//                        String data1 = String.format("第%d件商品的%dItem,跳转到%s专区详情", position, 1, content);
//                        Toast.makeText(BearMallAplication.getInstance().getApplicationContext(), data1, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.click_right:

                try {
                    if (type == 1) {
                        Intent intent = new Intent(mContext, ZeroMoneyDetailsActivity.class);
                        int groupPurchasing_id = mHomeBean.getData().getList().get(position).getList().get(1).getGroupPurchasing_id();
                        intent.putExtra("groupPurchasing_id", groupPurchasing_id + "");
                        mContext.startActivity(intent);
                    } else {

                        Intent intent = new Intent(mContext, BargainFreeDetailActivity.class);
                        int groupPurchasing_id = mHomeBean.getData().getList().get(position).getList().get(1).getBargainProduct_id();
                        intent.putExtra(BargainFreeDetailActivity.BARGAIN_PRODUCT_ID, (long) groupPurchasing_id);
                        mContext.startActivity(intent);


//                        String data2 = String.format("第%d件商品的%dItem,跳转到%s专区详情", position, 2, content);
//                        Toast.makeText(BearMallAplication.getInstance().getApplicationContext(), data2, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.huodong_more:
                try {
                    if (type == 1) {
                        StarActivityUtil.starActivity((Activity) mContext, ZeroMoneyActivity.class);
                    } else {

                        Intent intent = new Intent(mContext, BargainFreeActivity.class);
                        mContext.startActivity(intent);

//                        String data3 = String.format("第%d件商品的跳转到%s", position, content);
//                        Toast.makeText(BearMallAplication.getInstance().getApplicationContext(), data3, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }


    /**
     * 跳转到商品详情
     *
     * @param product_id
     */
    private void startActivity(Long product_id) {
        try {

            Intent intent = new Intent(mContext, NewProductDetailActivity.class);
            intent.putExtra("productId", "" + product_id);
            intent.putExtra("sku_id", "");
            mContext.startActivity(intent);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加顶部Banner
     */
    public void setHeadView0Data(List<HomeAd.DataBean.AdBean> lists) {
        try {
            TopBanner2 topBanner = headView0.findViewById(R.id.banner_top);

            if (lists.size() > 0) {
                topBanner.setImagesUrl(lists);
            }

            topBanner.setOnItemClickListener(new TopBanner2.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    HomeAd.DataBean.AdBean adBean = lists.get(position);


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加四个Menu菜单
     */
    public void setHeaderView1(View.OnClickListener onClickListener) {
        try {
            List<LinearMenu> menusViews = new ArrayList<>();

            LinearMenu spellGroupView = headView1.findViewById(R.id.menu_spell_group);
            LinearMenu freeObtainView = headView1.findViewById(R.id.menu_free_obtain);
            LinearMenu sweetsSnatchView = headView1.findViewById(R.id.menu_sweets_snatch);
            LinearMenu dailyTaskView = headView1.findViewById(R.id.menu_daily_task);

            menusViews.add(spellGroupView);
            menusViews.add(freeObtainView);
            menusViews.add(sweetsSnatchView);
            menusViews.add(dailyTaskView);

            // TODO 判断渠道


//            if ((int) SharedPreferencesHelper.get(mContext, "IsHidePointFunc", 0) == 0) {
//                for (int i = 0; i < menusViews.size(); i++) {
//                    LinearMenu linearMenu = menusViews.get(i);
//                    linearMenu.setAttr(linearMenuImages[i], linearMenuTitles[i]);
//                    linearMenu.setOnClickListener(onClickListener);
//                }
//            } else {
//                for (int i = 0; i < menusViews.size(); i++) {
//                    LinearMenu linearMenu = menusViews.get(i);
//                    linearMenu.setAttr(linearMenuImages[i], linearMenuTitlesO[i]);
//                    linearMenu.setOnClickListener(onClickListener);
//                }
//            }


            Log.e("TEST", BuildConfig.CHANNEL_UPDATE + "");

            if (BuildConfig.CHANNEL_UPDATE && (int) SharedPreferencesHelper.get(mContext, "IsHidePointFunc", 0) != 0) {
                for (int i = 0; i < menusViews.size(); i++) {
                    LinearMenu linearMenu = menusViews.get(i);
                    linearMenu.setAttr(linearMenuImages[i], linearMenuTitlesO[i]);
                    linearMenu.setOnClickListener(onClickListener);

                }
            } else {
                for (int i = 0; i < menusViews.size(); i++) {
                    LinearMenu linearMenu = menusViews.get(i);
                    linearMenu.setAttr(linearMenuImages[i], linearMenuTitles[i]);
                    linearMenu.setOnClickListener(onClickListener);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 添加菜单下面横向推荐位
     */
    public void setHeaderView2(List<HomeAd.DataBean.AdBean> lists, View.OnClickListener onClickListener) {

        try {
            List<ImageView> imageViews = new ArrayList<>();
            ImageView mImageViewLeft = headView2.findViewById(R.id.main_image_left);
            ImageView mImageViewCenter = headView2.findViewById(R.id.main_image_center);
            ImageView mImageViewRight = headView2.findViewById(R.id.main_image_right);

            imageViews.add(mImageViewLeft);
            imageViews.add(mImageViewCenter);
            imageViews.add(mImageViewRight);

            int size = lists.size() > 3 ? 3 : lists.size();

            for (int i = 0; i < size; i++) {
                ImageView imageView = imageViews.get(i);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_home_ad_three)).load(lists.get(i).getImg()).into(imageView);
                imageView.setOnClickListener(onClickListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 添加单个横向推荐位
     */
    public void setHeaderView3(List<HomeAd.DataBean.AdBean> lists, TopBanner2.OnItemClickListener listener) {
        try {
            TopBanner2 topBanner = headView3.findViewById(R.id.banner_top);
            if (lists.size() > 0) {
                topBanner.setImagesUrl(lists);
            }
            topBanner.setOnItemClickListener(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 顶部Banner
     */
    class BannerHolder extends RecyclerView.ViewHolder {

        public BannerHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 四个Menu菜单
     */
    class LinearMenuHolder extends RecyclerView.ViewHolder {

//        LinearMenu spellGroupView;
//        LinearMenu freeObtainView;
//        LinearMenu sweetsSnatchView;
//        LinearMenu dailyTaskView;

        public LinearMenuHolder(View itemView) {
            super(itemView);
//            spellGroupView = itemView.findViewById(R.id.menu_spell_group);
//            freeObtainView = itemView.findViewById(R.id.menu_free_obtain);
//            sweetsSnatchView = itemView.findViewById(R.id.menu_sweets_snatch);
//            dailyTaskView = itemView.findViewById(R.id.menu_daily_task);
        }
    }

    /**
     * 菜单下面横向推荐位
     */
    class FeaturedFirstHolder3 extends RecyclerView.ViewHolder {

//        ImageView mImageViewLeft;
//        ImageView mImageViewCenter;
//        ImageView mImageViewRight;


        public FeaturedFirstHolder3(View itemView) {
            super(itemView);

//            mImageViewLeft = itemView.findViewById(R.id.main_image_left);
//            mImageViewCenter = itemView.findViewById(R.id.main_image_center);
//            mImageViewRight = itemView.findViewById(R.id.main_image_right);


        }
    }

    /**
     * 单个横向推荐位
     */
    class FeaturedFirstHolder1 extends RecyclerView.ViewHolder {

        public FeaturedFirstHolder1(View itemView) {
            super(itemView);
        }
    }

    /**
     * 正常商品
     */
    class NormalHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_good)
        ImageView imageView;
        @BindView(R.id.item_good_name)
        TextView item_good_name;
        @BindView(R.id.tv_rmb)
        TextView tv_rmb;
        @BindView(R.id.tv_bt)
        TextView tv_bt;

        public NormalHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class NormalHolder_ extends RecyclerView.ViewHolder {

        @BindView(R.id.bg_group)
        LinearLayout bg_group;


        @BindView(R.id.img_left)
        ImageView img_left;

        @BindView(R.id.img_right)
        ImageView img_right;

        @BindView(R.id.tv_left_rmb)
        TextView tv_left_rmb;

        @BindView(R.id.tv_left_bt)
        TextView tv_left_bt;

        @BindView(R.id.tv_right_rmb)
        TextView tv_right_rmb;

        @BindView(R.id.tv_right_bt)
        TextView tv_right_bt;

        @BindView(R.id.huodong_title)
        TextView huodong_title;

        @BindView(R.id.huodong_more)
        TextView huodong_more;

        @BindView(R.id.huodong_title_bottom_info)
        TextView huodong_title_bottom_info;

        @BindView(R.id.click_left)
        LinearLayout click_left;

        @BindView(R.id.click_right)
        LinearLayout click_right;

        @BindView(R.id.uuu_tv_l)
        TextView uuu_tv_l;

        @BindView(R.id.uuu_tv_r)
        TextView uuu_tv_r;


        public NormalHolder_(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 自定义点击接口
     */
    public interface OnItemClickListener {
        void onItemClick(View v, int postion);
    }

    private OnItemClickListener mOnItemClickLitener;

    public void setmOnItemClickLitener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


}

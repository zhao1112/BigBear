package com.newversions.tbk.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.GoodsDetailActivity;
import com.newversions.tbk.entity.TBKHomeGoodsEntity;
import com.newversions.tbk.utils.BannerClicker;
import com.newversions.tbk.utils.StringUtils;
import com.newversions.tbk.utils.TopBarClicker;
import com.newversions.util.BannerImageLoader;
import com.newversions.util.ColorInfo;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.NewTBHome;
import com.yunqin.bearmall.ui.activity.SellwellActivity;
import com.yunqin.bearmall.util.CornerTransform;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter2.FRAGMENT_TYPE.VIEWT_TYPE_BANNER;
import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter2.FRAGMENT_TYPE.VIEW_TYPE_ACTIVE_TITLE;
import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter2.FRAGMENT_TYPE.VIEW_TYPE_BANNER_MID;
import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter2.FRAGMENT_TYPE.VIEW_TYPE_DEFAULT;
import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter2.FRAGMENT_TYPE.VIEW_TYPE_GOODS;
import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter2.FRAGMENT_TYPE.VIEW_TYPE_GOODS_TITLE;
import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter2.FRAGMENT_TYPE.VIEW_TYPE_HOT_LIST;
import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter2.FRAGMENT_TYPE.VIEW_TYPE_IMAGE;
import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter2.FRAGMENT_TYPE.VIEW_TYPE_IMAGE_FOUR;
import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter2.FRAGMENT_TYPE.VIEW_TYPE_TOP_BAR;
import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter2.FRAGMENT_TYPE.VIEW_TYPE_ZONE_LIST;

/**
 * Create By Master
 * On 2019/1/3 16:13
 */
public class NewVersionTBKHomeAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Activity context;
    private List<Object> datas;
    private String center_bg;
    private ImageView image;
    private String top_bg;
    private RoundedCorners roundedCorners = new RoundedCorners(5);
    //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
    private RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
    private RoundedCorners roundedCorners2 = new RoundedCorners(10);
    //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
    private RequestOptions options2 = RequestOptions.bitmapTransform(roundedCorners2);
    private NewTBHome.SellWellCopywritingBean mSellWellCopywritingBean = null;
    private NewTBHome.SelectedCopywritingBean mSelectedCopywritingBean = null;
    private NewTBHome.InterCopywritingBean mInterCopywritingBean = null;

    //只是绘制左上角和右上角圆角
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public NewVersionTBKHomeAdapter2(Activity context, ImageView image) {
        this.context = context;
        datas = new LinkedList<>();
        this.image = image;
    }

    public void setData(NewTBHome homeBeanList) {
        Log.e("setData", "setData: " + homeBeanList.toString());
        datas.clear();
        center_bg = homeBeanList.getCenter_bg().getTarget();
        top_bg = homeBeanList.getTop_bg().getTarget();
        mSellWellCopywritingBean = homeBeanList.getSellWellCopywriting();
        mSelectedCopywritingBean = homeBeanList.getSelectedCopywriting();
        mInterCopywritingBean = homeBeanList.getInterCopywriting();
        List<NewTBHome.BannerOneBean> bannerOne = homeBeanList.getBannerOne();
        List<NewTBHome.BannerTwoBean> bannerTwo = homeBeanList.getBannerTwo();
        List<NewTBHome.BannerThreeBean> bannerThree = homeBeanList.getBannerThree();
        datas.add(new BannerOne(bannerOne));
        datas.addAll(homeBeanList.getClassification());
        datas.add(new BannerTwo(bannerTwo));
        datas.addAll(bannerThree);
        datas.add(VIEW_TYPE_GOODS_TITLE);
        datas.add(new CommodityBean(homeBeanList.getCommodity()));
        datas.add(new CommodityOne(homeBeanList.getTianMaoCommodityOne()));
        datas.add(new CommodityTwo(homeBeanList.getTianMaoCommodityTwo()));
        datas.add(VIEW_TYPE_IMAGE_FOUR + "");
        notifyDataSetChanged();
    }


    public void addData(TBKHomeGoodsEntity homeBeanList) {
        // 添加数据
        Log.d("TCP", "addData: " + homeBeanList.getRecommend().size());
        datas.addAll(homeBeanList.getRecommend());//添加商品列表数据
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEWT_TYPE_BANNER:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_banner_top, parent, false);
                return new BannerHolder(view);
            case VIEW_TYPE_TOP_BAR:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_top_bar, parent, false);
                return new TopBarHolder(view);
            case VIEW_TYPE_BANNER_MID:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_banner_mid, parent, false);
                return new BannerHolder2(view);
            case VIEW_TYPE_IMAGE:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_image, parent, false);
                return new ImageHolder(view);
            case VIEW_TYPE_GOODS_TITLE:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_goods_title, parent, false);
                return new GoodTitleHolder(view);
            case VIEW_TYPE_HOT_LIST:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_horizontal_recycler, parent, false);
                return new HotListHolder(view);
            case VIEW_TYPE_ZONE_LIST:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_selected_recycler, parent, false);
                return new SelectedListHolder(view);
            case VIEW_TYPE_ACTIVE_TITLE:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_international_recycler, parent, false);
                return new InternationalListHolder(view);
            case VIEW_TYPE_IMAGE_FOUR:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_goods_title2, parent, false);
                return new GoodTitleHolder(view);
            case VIEW_TYPE_GOODS:
                view = LayoutInflater.from(context).inflate(R.layout.item_priduct_sum, parent, false);
                return new GoodsViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEWT_TYPE_BANNER:
                BannerHolder bannerHolder = (BannerHolder) holder;
                BannerOne bannerOne = (BannerOne) datas.get(position);
                List<String> urlsOne = new ArrayList<>();
                for (int i = 0; i < bannerOne.banners.size(); i++) {
                    urlsOne.add(bannerOne.banners.get(i).getImage());
                    Log.e("onBindViewHolder", bannerOne.banners.get(i).getImage());
                }

                bannerHolder.setDatas(urlsOne, bannerHolder.mImageView);
                bannerHolder.setBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        BannerClicker.bannerClick(context, bannerOne.banners.get(position).getTargetType(),
                                bannerOne.banners.get(position).getTarget(), bannerOne.banners.get(position).getTitle());
                    }
                });
                try {
                    image.setBackgroundColor(Color.parseColor(top_bg));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case VIEW_TYPE_TOP_BAR:
                TopBarHolder topBarHolder = (TopBarHolder) holder;
                NewTBHome.ClassificationBean classificationBean = (NewTBHome.ClassificationBean) datas.get(position);
                Glide.with(context)
                        .setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product))
                        .load(classificationBean.getImg())
                        .into(topBarHolder.imTopBar);
                topBarHolder.tvTopBar.setText(classificationBean.getName());
                topBarHolder.itemView.setOnClickListener(v -> {
                    int sort = classificationBean.getSort();
                    Log.e("topBarClick", "onBindViewHolder: " + sort);
                    TopBarClicker.topBarClick(context, classificationBean);
                });
                topBarHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
                break;
            case VIEW_TYPE_BANNER_MID:
                BannerHolder2 bannerHolder2 = (BannerHolder2) holder;
                BannerTwo bannerTwo = (BannerTwo) datas.get(position);
                List<String> urlsTwo = new ArrayList<>();
                for (int i = 0; i < bannerTwo.banners.size(); i++) {
                    urlsTwo.add(bannerTwo.banners.get(i).getImage());
                    Log.e("onBindViewHolder", bannerTwo.banners.get(i).getImage());
                }
                bannerHolder2.setDatas(urlsTwo);
                bannerHolder2.setBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        BannerClicker.bannerClick(context, bannerTwo.banners.get(position).getTargetType(),
                                bannerTwo.banners.get(position).getTarget(), bannerTwo.banners.get(position).getTitle());
                    }
                });
                break;
            case VIEW_TYPE_IMAGE:
                //  2019/7/15 0015 活动页
                NewTBHome.BannerThreeBean bannerThreeBean = (NewTBHome.BannerThreeBean) datas.get(position);
                ImageHolder imageHolder = (ImageHolder) holder;
                Glide.with(context)
                        .setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small))
                        .asBitmap()
                        .load(bannerThreeBean.getImage())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                imageHolder.imageView.setImageBitmap(resource);
                            }
                        });
                try {
                    imageHolder.view_color.setBackgroundColor(Color.parseColor(center_bg));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                imageHolder.imageView.setOnClickListener(v -> {
                    BannerClicker.bannerClick(context, bannerThreeBean.getTargetType(), bannerThreeBean.getTarget(),
                            bannerThreeBean.getTitle());
                });
                break;
            case VIEW_TYPE_GOODS_TITLE:
                //  2019/7/15 0015 商品title,不需要填充任何数据
                break;
            case VIEW_TYPE_HOT_LIST:
                //  2019/7/15 0015 热卖榜单
                try {
                    HotListHolder hotListHolder = (HotListHolder) holder;
                    CommodityBean hotList = (CommodityBean) datas.get(position);
                    Log.e("CommodityBean", hotList.toString());
                    CornerTransform transformation = new CornerTransform(context, dip2px(context, 15));
                    transformation.setExceptCorner(false, false, true, true);
                    Glide.with(context)
                            .load(mSellWellCopywritingBean.getImage())
                            .apply(new RequestOptions().placeholder(R.drawable.default_product).transform(transformation))
                            .into(hotListHolder.image_hot);
                    hotListHolder.title_hot.setText(mSellWellCopywritingBean.getTitle());
                    hotListHolder.title_hot_2.setText(mSellWellCopywritingBean.getContent());
                    hotListHolder.setData(hotList.data);
                    hotListHolder.r_more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle bundle = new Bundle();
                            bundle.putString("TYPE", "1");
                            SellwellActivity.openSellwellActivity(context, SellwellActivity.class, bundle);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case VIEW_TYPE_ZONE_LIST:
                try {
                    SelectedListHolder selectedListHolder = (SelectedListHolder) holder;
                    CommodityOne commodityOne = (CommodityOne) datas.get(position);
                    CornerTransform transformation2 = new CornerTransform(context, dip2px(context, 15));
                    transformation2.setExceptCorner(false, false, true, true);
                    Glide.with(context)
                            .load(mSelectedCopywritingBean.getImage())
                            .apply(new RequestOptions().placeholder(R.drawable.default_product).transform(transformation2))
                            .into(selectedListHolder.t_image);
                    selectedListHolder.t_title.setText(mSelectedCopywritingBean.getTitle());
                    selectedListHolder.t_title_2.setText(mSelectedCopywritingBean.getContent());
                    selectedListHolder.setData(commodityOne.data);
                    selectedListHolder.t_more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle bundle = new Bundle();
                            bundle.putString("TYPE", "2");
                            SellwellActivity.openSellwellActivity(context, SellwellActivity.class, bundle);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case VIEW_TYPE_ACTIVE_TITLE:
                try {
                    InternationalListHolder internationalListHolder = (InternationalListHolder) holder;
                    CommodityTwo commodityTwo = (CommodityTwo) datas.get(position);
                    CornerTransform transformation3 = new CornerTransform(context, dip2px(context, 15));
                    transformation3.setExceptCorner(false, false, true, true);
                    Glide.with(context)
                            .load(mInterCopywritingBean.getImage())
                            .apply(new RequestOptions().placeholder(R.drawable.default_product).transform(transformation3))
                            .into(internationalListHolder.i_image);
                    internationalListHolder.i_title.setText(mInterCopywritingBean.getTitle());
                    internationalListHolder.i_title_2.setText(mInterCopywritingBean.getContent());
                    internationalListHolder.setData(commodityTwo.data);
                    internationalListHolder.m_more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle bundle = new Bundle();
                            bundle.putString("TYPE", "3");
                            bundle.putString("TITLE", "天猫国际");
                            SellwellActivity.openSellwellActivity(context, SellwellActivity.class, bundle);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case VIEW_TYPE_IMAGE_FOUR:

                break;
            case VIEW_TYPE_GOODS:
                //  2019/7/15 0015 商品
                TBKHomeGoodsEntity.RecommendBean recommendBean = (TBKHomeGoodsEntity.RecommendBean) datas.get(position);
                GoodsViewHolder goodsViewHolder = (GoodsViewHolder) holder;
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(goodsViewHolder.home_bg.getLayoutParams());
                if (position % 2 == 0) {
                    params.setMargins(5, 0, 15, 15);
                } else {
                    params.setMargins(15, 0, 5, 15);
                }
                goodsViewHolder.home_bg.setLayoutParams(params);
                goodsViewHolder.itemHomeProTitle.setText(recommendBean.getName());
                goodsViewHolder.itemHomeXiaoliang.setText("月销" + recommendBean.getSellNum());
                goodsViewHolder.itemHomeProYuanjia.setText("¥" + recommendBean.getPrice());
                goodsViewHolder.itemHomeProYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                try {
                    goodsViewHolder.itemHomeProQuan.setText(recommendBean.getCouponAmount() + "元券");
                    goodsViewHolder.itemHomeProQuanhoujia.setText(recommendBean.getDiscountPrice() + "");
                    goodsViewHolder.tvCommision.setText("预估收益¥" + recommendBean.getCommision());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Exception", e.getMessage());
                }
                CornerTransform transformation4 = new CornerTransform(context, dip2px(context, 5));
                transformation4.setExceptCorner(false, false, true, true);
                Glide.with(context)
                        .load(recommendBean.getOutIcon())
                        .apply(new RequestOptions().placeholder(R.drawable.default_product).transform(transformation4))
                        .into(goodsViewHolder.itemHomeProImage);
                goodsViewHolder.itemSellerName.setText(StringUtils.addImageLabel(context, recommendBean.getTmall() == 1 ?
                        R.mipmap.icon_tmall : R.mipmap.icon_taobao1, recommendBean.getSellerName()));
                goodsViewHolder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(context, GoodsDetailActivity.class);
                    intent.putExtra(Constants.INTENT_KEY_ID, recommendBean.getItemId() + "");
                    intent.putExtra(Constants.INTENT_KEY_TYPE, Constants.GOODS_TYPE_TBK);
                    intent.putExtra(Constants.INTENT_KEY_COMM, recommendBean.getCommision());
                    intent.putExtra(Constants.INTENT_KEY_COMMISSION, Constants.COMMISSION_TYPE_ONE);
                    context.startActivity(intent);
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onClick(View view) {
        //  推荐位处理
       /* if (onItemClickListener != null) {
            onItemClickListener.onHomeChildClick(view);
        }*/
    }

    @IntDef({VIEW_TYPE_DEFAULT, VIEWT_TYPE_BANNER, VIEW_TYPE_TOP_BAR, VIEW_TYPE_IMAGE, VIEW_TYPE_HOT_LIST, VIEW_TYPE_ZONE_LIST,
            VIEW_TYPE_GOODS_TITLE, VIEW_TYPE_GOODS, VIEW_TYPE_BANNER_MID, VIEW_TYPE_ACTIVE_TITLE, VIEW_TYPE_IMAGE_FOUR})
    public @interface FRAGMENT_TYPE {
        int VIEW_TYPE_DEFAULT = 0;
        int VIEWT_TYPE_BANNER = 1;
        int VIEW_TYPE_TOP_BAR = 2;
        int VIEW_TYPE_IMAGE = 3;
        int VIEW_TYPE_HOT_LIST = 4;
        int VIEW_TYPE_ZONE_LIST = 5;
        int VIEW_TYPE_GOODS_TITLE = 6;
        int VIEW_TYPE_GOODS = 7;
        int VIEW_TYPE_BANNER_MID = 8;
        int VIEW_TYPE_ACTIVE_TITLE = 9;
        int VIEW_TYPE_IMAGE_FOUR = 10;
    }

    @Override
    public int getItemViewType(int position) {
        if (datas.get(position) instanceof BannerOne) {
            return VIEWT_TYPE_BANNER;
        }
        if (datas.get(position) instanceof NewTBHome.ClassificationBean) {
            return VIEW_TYPE_TOP_BAR;
        }
        if (datas.get(position) instanceof BannerTwo) {
            return VIEW_TYPE_BANNER_MID;
        }
        if (datas.get(position) instanceof NewTBHome.BannerThreeBean) {
            return VIEW_TYPE_IMAGE;
        }
        if (datas.get(position) instanceof Integer) {
            return VIEW_TYPE_GOODS_TITLE;
        }
        if (datas.get(position) instanceof CommodityBean) {
            return VIEW_TYPE_HOT_LIST;
        }
        if (datas.get(position) instanceof CommodityOne) {
            return VIEW_TYPE_ZONE_LIST;
        }
        if (datas.get(position) instanceof CommodityTwo) {
            return VIEW_TYPE_ACTIVE_TITLE;
        }
        if (datas.get(position) instanceof String) {
            return VIEW_TYPE_IMAGE_FOUR;
        }
        if (datas.get(position) instanceof TBKHomeGoodsEntity.RecommendBean) {
            return VIEW_TYPE_GOODS;
        }
        return 0;
    }

    public int getItemColumnCount(int position) {
        //  2019/7/13 0013 根据itemType返回跨的列数
        switch (getItemViewType(position)) {
            case VIEW_TYPE_GOODS:
                return 5;
            case VIEW_TYPE_GOODS_TITLE:
                return 10;
            case VIEW_TYPE_HOT_LIST:
                return 10;
            case VIEW_TYPE_ZONE_LIST:
                return 10;
            case VIEWT_TYPE_BANNER:
                return 10;
            case VIEW_TYPE_BANNER_MID:
                return 10;
            case VIEW_TYPE_TOP_BAR:
                return 2;
            case VIEW_TYPE_IMAGE:
                return 5;
            case VIEW_TYPE_ACTIVE_TITLE:
                return 10;
            case VIEW_TYPE_IMAGE_FOUR:
                return 10;
            default:
                return 10;
        }
    }


    public class ActiveTitleHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;

        public ActiveTitleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * banner
     */
    private List<ColorInfo> colorList = new ArrayList<>();
    private BannerImageLoader imageLoader;
    private int count;
    private boolean isInit = true;

    public class BannerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.banner)
        Banner banner_list;
        private final ImageView mImageView;

        public BannerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mImageView = itemView.findViewById(R.id.banner_text_2);
        }

        public void setDatas(List<String> imgUrls, ImageView imageView) {
            banner_list.setOnPageChangeListener(new OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (positionOffset > 1) {//会出现极个别大于1的数据
                        return;
                    }
                    //修正position，解决两头颜色错乱，来自Banner控件源码
                    if (position == 0) {
                        position = count;
                    }
                    if (position > count) {
                        position = 1;
                    }
                    int pos = (position + 1) % count;//很关键

                    int vibrantColor = ColorUtils.blendARGB(imageLoader.getVibrantColor(pos), imageLoader.getVibrantColor(pos + 1),
                            positionOffset);
//                    imageView.setBackgroundColor(vibrantColor);
//                    image.setBackgroundColor(vibrantColor);
//                    setStatusBarColor(context, vibrantColor);
                }

                @Override
                public void onPageSelected(int position) {
                    if (isInit) {// 第一次,延时加载才能拿到颜色
                        isInit = false;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int vibrantColor = imageLoader.getVibrantColor(1);
//                                imageView.setBackgroundColor(vibrantColor);
//                                image.setBackgroundColor(vibrantColor);
//                                setStatusBarColor(context, vibrantColor);
                            }
                        }, 200);

                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            count = imgUrls.size();
            colorList.clear();
            for (int i = 0; i <= count + 1; i++) {
                ColorInfo info = new ColorInfo();
                if (i == 0) {
                    info.setImgUrl(imgUrls.get(count - 1));
                } else if (i == count + 1) {
                    info.setImgUrl(imgUrls.get(0));
                } else {
                    info.setImgUrl(imgUrls.get(i - 1));
                }
                colorList.add(info);
            }

            //设置图片集合
            imageLoader = new BannerImageLoader(colorList);
            //设置自动轮播，默认为true
            banner_list.isAutoPlay(true);
            //设置轮播时间
            banner_list.setDelayTime(3000);
            //设置指示器位置（当banner模式中有指示器时）
            banner_list.setIndicatorGravity(BannerConfig.RIGHT);
            banner_list.setImages(imgUrls);
            banner_list.setImageLoader(imageLoader);
            banner_list.start();
        }

        public void setBannerListener(OnBannerListener listener) {
            banner_list.setOnBannerListener(listener);
        }
    }

    public class BannerHolder2 extends RecyclerView.ViewHolder {

        @BindView(R.id.banner)
        Banner banner_list;
        private final ImageView mImageView;

        public BannerHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mImageView = itemView.findViewById(R.id.banner_text_2);
        }

        public void setDatas(List<String> imgUrls) {
            //设置自动轮播，默认为true
            banner_list.isAutoPlay(true);
            //设置轮播时间
            banner_list.setDelayTime(3000);
            //设置指示器位置（当banner模式中有指示器时）
            banner_list.setIndicatorGravity(BannerConfig.RIGHT);
            banner_list.setImages(imgUrls);
            banner_list.setImageLoader(new GlideImageLoader());
            banner_list.start();
        }

        public void setBannerListener(OnBannerListener listener) {
            banner_list.setOnBannerListener(listener);
        }
    }

    public class TopBarHolder extends RecyclerView.ViewHolder {
        //        int id = R.layout.item_home_top_bar;
        @BindView(R.id.im_top_bar)
        ImageView imTopBar;
        @BindView(R.id.tv_top_bar)
        TextView tvTopBar;

        public TopBarHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        //        int id = R.layout.item_home_image;
        @BindView(R.id.image_view_6)
        ImageView imageView;
        @BindView(R.id.image_view_color)
        LinearLayout view_color;

        public ImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HotListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recycle_view_rex)
        RecyclerView recycleView;
        @BindView(R.id.r_more)
        TextView r_more;
        @BindView(R.id.image_hot)
        ImageView image_hot;
        @BindView(R.id.title_hot)
        TextView title_hot;
        @BindView(R.id.title_hot_2)
        TextView title_hot_2;


        ItemHotListAdapter adapter;

        public HotListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            recycleView.setLayoutManager(manager);
            adapter = new ItemHotListAdapter();
            recycleView.setAdapter(adapter);
        }

        public void setData(List<NewTBHome.CommodityBean> data) {
            Log.e("CommodityBean", data.size() + "----" + data.toString());
            adapter.setData(data);
        }
    }

    public class ItemHotListAdapter extends RecyclerView.Adapter<ItemHorizontalViewHolder> {

        List<NewTBHome.CommodityBean> data;

        public void setData(List<NewTBHome.CommodityBean> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ItemHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_home_hot_goods, parent, false);
            return new ItemHorizontalViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHorizontalViewHolder holder, int position) {

            Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(data.get(position).getOutIcon()).apply(options).into(holder.imGoodsImg);
            holder.tvGoodsContent.setText(data.get(position).getName());
            try {
                holder.tvOldPrice.setText("¥" + data.get(position).getDiscountPrice() + "");
                holder.tvQuan.setText(data.get(position).getCouponAmount() + "元券");
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_KEY_ID, data.get(position).getItemId() + "");
                intent.putExtra(Constants.INTENT_KEY_TYPE, Constants.GOODS_TYPE_TBK);
                intent.putExtra(Constants.INTENT_KEY_COMM, "0");
                intent.putExtra(Constants.INTENT_KEY_COMMISSION, Constants.COMMISSION_TYPE_THREE);
                context.startActivity(intent);
            });

        }

        @Override
        public int getItemCount() {
            try {
                return data.size();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

    public class ItemHorizontalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.im_goods_img)
        ImageView imGoodsImg;
        @BindView(R.id.im_live)
        ImageView imLive;
        @BindView(R.id.tv_goods_content)
        TextView tvGoodsContent;
        @BindView(R.id.tv_quan)
        TextView tvQuan;
        @BindView(R.id.tv_msg)
        TextView tvMsg;
        @BindView(R.id.tv_old_price)
        TextView tvOldPrice;
        @BindView(R.id.item_goods_layout)
        LinearLayout itemGoodsLayout;


        public ItemHorizontalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class GoodsViewHolder extends RecyclerView.ViewHolder {
        int id = R.layout.item_priduct_sum;
        @BindView(R.id.item_home_pro_image)
        ImageView itemHomeProImage;
        @BindView(R.id.item_home_pro_title)
        TextView itemHomeProTitle;
        @BindView(R.id.item_home_pro_quan)
        TextView itemHomeProQuan;
        @BindView(R.id.item_home_xiaoliang)
        TextView itemHomeXiaoliang;
        @BindView(R.id.item_home_pro_quanhoujia)
        TextView itemHomeProQuanhoujia;
        @BindView(R.id.item_home_pro_yuanjia)
        TextView itemHomeProYuanjia;
        @BindView(R.id.tv_commision)
        TextView tvCommision;
        @BindView(R.id.item_seller_name)
        TextView itemSellerName;
        @BindView(R.id.rl)
        RelativeLayout rl;
        @BindView(R.id.home_bg)
        RelativeLayout home_bg;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class GoodTitleHolder extends RecyclerView.ViewHolder {

        public GoodTitleHolder(View itemView) {
            super(itemView);
        }
    }

    public class SelectedListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recycle_view_selected)
        RecyclerView recycleView;
        @BindView(R.id.t_more)
        TextView t_more;
        @BindView(R.id.t_image)
        ImageView t_image;
        @BindView(R.id.t_title)
        TextView t_title;
        @BindView(R.id.t_title_2)
        TextView t_title_2;

        ItemSelectedListAdapter adapter;

        public SelectedListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            recycleView.setLayoutManager(manager);
            adapter = new ItemSelectedListAdapter();
            recycleView.setAdapter(adapter);
        }

        public void setData(List<NewTBHome.TianMaoCommodityOneBean> data) {
            try {
                adapter.setData(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class ItemSelectedListAdapter extends RecyclerView.Adapter<ItemSelectedlViewHolder> {

        List<NewTBHome.TianMaoCommodityOneBean> data;

        public void setData(List<NewTBHome.TianMaoCommodityOneBean> data) {
            this.data = data;
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public ItemSelectedlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_home_hot_goods, parent, false);
            return new ItemSelectedlViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemSelectedlViewHolder holder, int position) {
//            if (position == 0) {
//                holder.imLive.setVisibility(View.VISIBLE);
//                holder.imLive.setImageResource(R.mipmap.no_1);
//            } else if (position == 1) {
//                holder.imLive.setVisibility(View.VISIBLE);
//                holder.imLive.setImageResource(R.mipmap.no_2);
//            } else if (position == 2) {
//                holder.imLive.setVisibility(View.VISIBLE);
//                holder.imLive.setImageResource(R.mipmap.no_3);
//            } else {
//                holder.imLive.setVisibility(View.GONE);
//
//            }
            Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(data.get(position).getOutIcon()).apply(options).into(holder.imGoodsImg);
            holder.tvGoodsContent.setText(data.get(position).getName());
            try {
                holder.tvOldPrice.setText("¥" + data.get(position).getDiscountPrice() + "");
                holder.tvQuan.setText(data.get(position).getCouponAmount() + "元券");
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_KEY_ID, data.get(position).getItemId() + "");
                intent.putExtra(Constants.INTENT_KEY_TYPE, Constants.GOODS_TYPE_TBK);
                intent.putExtra(Constants.INTENT_KEY_COMM, data.get(position).getCommision());
                intent.putExtra(Constants.INTENT_KEY_COMMISSION, Constants.COMMISSION_TYPE_ONE);
                context.startActivity(intent);
            });

        }

        @Override
        public int getItemCount() {
            try {
                return data.size();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

    public class ItemSelectedlViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.im_goods_img)
        ImageView imGoodsImg;
        @BindView(R.id.im_live)
        ImageView imLive;
        @BindView(R.id.tv_goods_content)
        TextView tvGoodsContent;
        @BindView(R.id.tv_quan)
        TextView tvQuan;
        @BindView(R.id.tv_msg)
        TextView tvMsg;
        @BindView(R.id.tv_old_price)
        TextView tvOldPrice;
        @BindView(R.id.item_goods_layout)
        LinearLayout itemGoodsLayout;


        public ItemSelectedlViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class InternationalListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recycle_view_international)
        RecyclerView recycleView;
        @BindView(R.id.m_more)
        TextView m_more;
        @BindView(R.id.i_image)
        ImageView i_image;
        @BindView(R.id.i_title)
        TextView i_title;
        @BindView(R.id.i_title_2)
        TextView i_title_2;

        InternationalListAdapter adapter;

        public InternationalListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            recycleView.setLayoutManager(manager);
            adapter = new InternationalListAdapter();
            recycleView.setAdapter(adapter);
        }

        public void setData(List<NewTBHome.TianMaoCommodityTwoBean> data) {
            Log.e("CommodityBean", data.size() + "----" + data.toString());
            adapter.setData(data);
        }
    }

    public class InternationalListAdapter extends RecyclerView.Adapter<ItemInternationalViewHolder> {

        List<NewTBHome.TianMaoCommodityTwoBean> data;

        public void setData(List<NewTBHome.TianMaoCommodityTwoBean> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ItemInternationalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_home_hot_goods, parent, false);
            return new ItemInternationalViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemInternationalViewHolder holder, int position) {

            Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(data.get(position).getOutIcon()).apply(options).into(holder.imGoodsImg);
            holder.tvGoodsContent.setText(data.get(position).getName());
            try {
                holder.tvOldPrice.setText("¥" + data.get(position).getDiscountPrice() + "");
                holder.tvQuan.setText(data.get(position).getCouponAmount() + "元券");
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_KEY_ID, data.get(position).getItemId() + "");
                intent.putExtra(Constants.INTENT_KEY_TYPE, Constants.GOODS_TYPE_TBK);
                intent.putExtra(Constants.INTENT_KEY_COMM, data.get(position).getCommision());
                intent.putExtra(Constants.INTENT_KEY_COMMISSION, Constants.COMMISSION_TYPE_ONE);
                context.startActivity(intent);
            });

        }

        @Override
        public int getItemCount() {
            try {
                return data.size();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

    public class ItemInternationalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.im_goods_img)
        ImageView imGoodsImg;
        @BindView(R.id.im_live)
        ImageView imLive;
        @BindView(R.id.tv_goods_content)
        TextView tvGoodsContent;
        @BindView(R.id.tv_quan)
        TextView tvQuan;
        @BindView(R.id.tv_msg)
        TextView tvMsg;
        @BindView(R.id.tv_old_price)
        TextView tvOldPrice;
        @BindView(R.id.item_goods_layout)
        LinearLayout itemGoodsLayout;


        public ItemInternationalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view);

        void onHomeChildClick(View view);
    }

    public OnItemColor mOnItemColor;

    public interface OnItemColor {
        void setColor(int color);
    }

    public void setOnItemColor(OnItemColor mOnItemColor) {
        this.mOnItemColor = mOnItemColor;
    }


    /**
     * 热卖
     */
    private class CommodityBean {
        List<NewTBHome.CommodityBean> data;

        CommodityBean(List<NewTBHome.CommodityBean> data) {
            this.data = data;
        }
    }

    private class CommodityOne {
        List<NewTBHome.TianMaoCommodityOneBean> data;

        CommodityOne(List<NewTBHome.TianMaoCommodityOneBean> data) {
            this.data = data;
        }
    }

    private class CommodityTwo {
        List<NewTBHome.TianMaoCommodityTwoBean> data;

        CommodityTwo(List<NewTBHome.TianMaoCommodityTwoBean> data) {
            this.data = data;
        }
    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            Glide.with(context)
                    .load(path)
                    .apply(new RequestOptions().placeholder(R.drawable.default_product))
                    .into(imageView);
        }
    }

    private class BannerOne {
        List<NewTBHome.BannerOneBean> banners;

        BannerOne(List<NewTBHome.BannerOneBean> banners) {
            this.banners = banners;
        }
    }

    private class BannerTwo {
        List<NewTBHome.BannerTwoBean> banners;

        BannerTwo(List<NewTBHome.BannerTwoBean> banners) {
            this.banners = banners;
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity
     */
    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            //状态栏改变颜色。
            window.setStatusBarColor(color);
        }
    }

}

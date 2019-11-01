package com.newversions.tbk.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.newversions.home.HomeBean;
import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.GoodsDetailActivity;
import com.newversions.tbk.entity.TBKHomeEntity;
import com.newversions.tbk.entity.TBKHomeGoodsEntity;
import com.newversions.tbk.utils.BannerClicker;
import com.newversions.tbk.utils.GlideImageLoader;
import com.newversions.tbk.utils.StringUtils;
import com.newversions.tbk.utils.TopBarClicker;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.eventbus.ChangeFragment;
import com.yunqin.bearmall.ui.activity.ZeroMoneyDetailsActivity;
import com.yunqin.bearmall.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter.FRAGMENT_TYPE.VIEWT_TYPE_BANNER;
import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter.FRAGMENT_TYPE.VIEW_TYPE_ACTIVE_TITLE;
import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter.FRAGMENT_TYPE.VIEW_TYPE_BANNER_MID;
import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter.FRAGMENT_TYPE.VIEW_TYPE_DEFAULT;
import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter.FRAGMENT_TYPE.VIEW_TYPE_GOODS;
import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter.FRAGMENT_TYPE.VIEW_TYPE_GOODS_TITLE;
import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter.FRAGMENT_TYPE.VIEW_TYPE_HOT_LIST;
import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter.FRAGMENT_TYPE.VIEW_TYPE_IMAGE;
import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter.FRAGMENT_TYPE.VIEW_TYPE_TOP_BAR;
import static com.newversions.tbk.fragment.NewVersionTBKHomeAdapter.FRAGMENT_TYPE.VIEW_TYPE_ZONE_LIST;

/**
 * Create By Master
 * On 2019/1/3 16:13
 */
public class NewVersionTBKHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final int HEADER_COUNT = 3;
    private int DYNAMICALLY_ADD_COUNT = 0;

    private Activity context;
    private HomeBean homeBeanList;

    private List<Object> datas;


    public NewVersionTBKHomeAdapter(Activity context) {
        this.context = context;
        datas = new LinkedList<>();
    }

    public void setData(TBKHomeEntity homeBeanList) {
        datas.clear();
        //  2019/7/13 0013 初始化首页数据
        List<TBKHomeEntity.BannerBean> bannerOne = homeBeanList.getBannerOne();
        List<TBKHomeEntity.BannerBean> bannerTwo = homeBeanList.getBannerTwo();
        List<TBKHomeEntity.BannerTwoBean> bannerThree = homeBeanList.getBannerThree();
        List<TBKHomeEntity.BannerTwoBean> bannerFour = homeBeanList.getBannerFour();
        List<TBKHomeEntity.CommodityBean> commodity = homeBeanList.getCommodity();//热门榜单
        List<TBKHomeEntity.ClassificationBean> classification = homeBeanList.getClassification();//banner下的导航
        List<TBKHomeEntity.GroupPurchasingListBean> groupPurchasingList = homeBeanList.getGroupPurchasingList();//0元兑
        datas.add(new BannersBean(bannerOne, BannersBean.BANNER_TYPE_TOP));//添加顶部Banner，一个list为一个item
        datas.addAll(classification);//添加快速导航
        datas.add(new BannersBean(bannerTwo, BannersBean.BANNER_TYPE_2));//添加导航下的Banner数据
        datas.addAll(bannerThree);//添加第一个四位活动图
        datas.add(new CommodityBean(commodity));//添加热卖榜单为一个item
        datas.add(homeBeanList.getActiveTitle()+"");//添加活动标题
        datas.addAll(bannerFour);//添加第二个四位活动图
        datas.add(new GroupPurchasing(groupPurchasingList));//添加0元兑，为一个item
        datas.add(VIEW_TYPE_GOODS_TITLE);//添加为你推荐的占位item
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
            case VIEW_TYPE_GOODS:
                view = LayoutInflater.from(context).inflate(R.layout.item_priduct_sum, parent, false);
                return new GoodsViewHolder(view);
            case VIEW_TYPE_GOODS_TITLE:
                //  2019/7/13 0013 title页面
                view = LayoutInflater.from(context).inflate(R.layout.item_home_goods_title, parent, false);
                return new GoodTitleHolder(view);
            case VIEW_TYPE_HOT_LIST:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_horizontal_recycler, parent, false);
                return new HotListHolder(view);
            case VIEW_TYPE_IMAGE:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_image, parent, false);
                return new ImageHolder(view);
            case VIEW_TYPE_TOP_BAR:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_top_bar, parent, false);
                return new TopBarHolder(view);
            case VIEW_TYPE_ZONE_LIST:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_horizontal_recycler, parent, false);
                return new ZoneListHolder(view);
            case VIEWT_TYPE_BANNER:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_banner_top, parent, false);
                return new BannerHolder(view);
            case VIEW_TYPE_BANNER_MID:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_banner_mid, parent, false);
                return new BannerHolder(view);
            case VIEW_TYPE_ACTIVE_TITLE:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_active_title,parent,false);
                return new ActiveTitleHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case VIEW_TYPE_BANNER_MID:
            case VIEWT_TYPE_BANNER:
                //  2019/7/15 0015 顶部banner 和中部banner
                BannersBean bannersBean = (BannersBean) datas.get(position);
                List<String> urls = new ArrayList<>();
                for (int i = 0; i < bannersBean.banners.size(); i++) {
                    urls.add(bannersBean.banners.get(i).getImage());
                }
                BannerHolder bannerHolder = (BannerHolder) holder;
                bannerHolder.setDatas(urls);
                bannerHolder.setBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        BannerClicker.bannerClick(context,bannersBean.banners.get(position).getTargetType(),bannersBean.banners.get(position).getTarget(),bannersBean.banners.get(position).getTitle());
                    }
                });
                break;
            case VIEW_TYPE_TOP_BAR:
                //  2019/7/15 0015 快速导航
                TopBarHolder topBarHolder = (TopBarHolder) holder;
                TBKHomeEntity.ClassificationBean classificationBean = (TBKHomeEntity.ClassificationBean) datas.get(position);
                Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product)).load(classificationBean.getImg()).into(topBarHolder.imTopBar);
                topBarHolder.tvTopBar.setText(classificationBean.getName());
                topBarHolder.itemView.setOnClickListener(v -> {
                    TopBarClicker.topBarClick(context,classificationBean);
                });
                topBarHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
                break;
            case VIEW_TYPE_IMAGE:
                //  2019/7/15 0015 活动页
                TBKHomeEntity.BannerTwoBean bannerTwoBean = (TBKHomeEntity.BannerTwoBean) datas.get(position);
                ImageHolder imageHolder = (ImageHolder) holder;

//                Glide.with(context)
//                        .load(bannerTwoBean.getImage())
//                        .apply(new RequestOptions().placeholder(R.drawable.default_product_small))
//                        .into(imageHolder.imageView);
//                Log.i("imageurl", "onBindViewHolder: "+bannerTwoBean.getImage());

                Glide.with(context)
                        .setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small))
                        .asBitmap()
                        .load(bannerTwoBean.getImage())
                        .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageHolder.imageView.setImageBitmap(resource);
                    }
                });

                imageHolder.imageView.setOnClickListener(v ->{
                    BannerClicker.bannerClick(context,bannerTwoBean.getTargetType(),bannerTwoBean.getTarget(),bannerTwoBean.getTitle());
                });
                break;
            case VIEW_TYPE_HOT_LIST:
                //  2019/7/15 0015 热卖榜单
                HotListHolder hotListHolder = (HotListHolder) holder;
                CommodityBean hotList = (CommodityBean) datas.get(position);
                hotListHolder.setData(hotList.data);
                hotListHolder.imageView.setImageResource(R.mipmap.bg_hotsales);
                break;
            case VIEW_TYPE_ZONE_LIST:
                //  2019/7/15 0015 糖果0元兑
                ZoneListHolder zoneListHolder = (ZoneListHolder) holder;
                GroupPurchasing groupPurchasing = (GroupPurchasing) datas.get(position);
                zoneListHolder.setData(groupPurchasing.data);
                zoneListHolder.imageView.setImageResource(R.mipmap.bg_sugar);

                break;
            case VIEW_TYPE_GOODS_TITLE:
                //  2019/7/15 0015 商品title,不需要填充任何数据
                break;
            case VIEW_TYPE_GOODS:
                //  2019/7/15 0015 商品
                TBKHomeGoodsEntity.RecommendBean recommendBean = (TBKHomeGoodsEntity.RecommendBean) datas.get(position);
                GoodsViewHolder goodsViewHolder = (GoodsViewHolder) holder;
                goodsViewHolder.itemHomeProTitle.setText(recommendBean.getName());
                goodsViewHolder.itemHomeProQuan.setText(recommendBean.getCouponAmount() + "元券");
                goodsViewHolder.itemHomeXiaoliang.setText("月销" + recommendBean.getSellNum());
                goodsViewHolder.itemHomeProQuanhoujia.setText("￥" + recommendBean.getDiscountPrice());
                goodsViewHolder.itemHomeProYuanjia.setText("￥" + recommendBean.getPrice());
                goodsViewHolder.itemHomeProYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                goodsViewHolder.tvCommision.setText("预估佣金：" + recommendBean.getCommision());
                Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product)).load(recommendBean.getOutIcon()).into(goodsViewHolder.itemHomeProImage);
                goodsViewHolder.itemSellerName.setText(StringUtils.addImageLabel(context, recommendBean.getTmall() == 1 ? R.mipmap.icon_tmall : R.mipmap.icon_taobao1, recommendBean.getSellerName()));
                goodsViewHolder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(context, GoodsDetailActivity.class);
                    intent.putExtra(Constants.INTENT_KEY_ID, recommendBean.getItemId()+"");
                    intent.putExtra(Constants.INTENT_KEY_TYPE, Constants.GOODS_TYPE_TBK);
                    intent.putExtra(Constants.INTENT_KEY_COMM, recommendBean.getCommision());
                    context.startActivity(intent);
                });
                break;
            case VIEW_TYPE_ACTIVE_TITLE:
                //  2019/7/30 0030 活动标题
                String title = (String) datas.get(position);
                ActiveTitleHolder activeTitleHolder = (ActiveTitleHolder) holder;
                activeTitleHolder.tvTitle.setText(title);
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

    @IntDef({VIEW_TYPE_DEFAULT, VIEWT_TYPE_BANNER, VIEW_TYPE_TOP_BAR, VIEW_TYPE_IMAGE, VIEW_TYPE_HOT_LIST, VIEW_TYPE_ZONE_LIST, VIEW_TYPE_GOODS_TITLE, VIEW_TYPE_GOODS,VIEW_TYPE_BANNER_MID,VIEW_TYPE_ACTIVE_TITLE})
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
    }

    @Override
    public int getItemViewType(int position) {
        //  2019/7/13 0013 根据数据类型返回ViewType
        if (datas.get(position) instanceof BannersBean) {
            return ((BannersBean)datas.get(position)).bannerType == BannersBean.BANNER_TYPE_TOP?VIEWT_TYPE_BANNER:VIEW_TYPE_BANNER_MID;
        }
        if (datas.get(position) instanceof TBKHomeEntity.ClassificationBean) {
            return VIEW_TYPE_TOP_BAR;
        }
        if (datas.get(position) instanceof TBKHomeEntity.BannerTwoBean) {
            return VIEW_TYPE_IMAGE;
        }
        if (datas.get(position) instanceof CommodityBean) {
            return VIEW_TYPE_HOT_LIST;
        }
        if (datas.get(position) instanceof GroupPurchasing) {
            return VIEW_TYPE_ZONE_LIST;
        }
        if (datas.get(position) instanceof TBKHomeGoodsEntity.RecommendBean) {
            return VIEW_TYPE_GOODS;
        }
        if (datas.get(position) instanceof Integer) {
            return VIEW_TYPE_GOODS_TITLE;
        }
        if (datas.get(position) instanceof String){
            return VIEW_TYPE_ACTIVE_TITLE;
        }

        return VIEW_TYPE_DEFAULT;
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
            default:
                return 10;
        }
    }


    class ActiveTitleHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_title)
        TextView tvTitle;
        public ActiveTitleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /**
     * banner
     */
    class BannerHolder extends RecyclerView.ViewHolder {

        //        int id = R.layout.item_home_banner_top;
        @BindView(R.id.banner)
        Banner banner;

        public BannerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //设置自动轮播，默认为true
            banner.isAutoPlay(true);
            //设置轮播时间
            banner.setDelayTime(3000);
            //设置指示器位置（当banner模式中有指示器时）
            banner.setIndicatorGravity(BannerConfig.RIGHT);
        }

        public void setDatas(List<String> imgUrls) {
            banner.setImages(imgUrls);
            banner.setImageLoader(new NewVersionTBKHomeAdapter.GlideImageLoader());
            banner.start();
        }

        public void setBannerListener(OnBannerListener listener) {
            banner.setOnBannerListener(listener);
        }
    }

    class TopBarHolder extends RecyclerView.ViewHolder {
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

    class ImageHolder extends RecyclerView.ViewHolder {
        //        int id = R.layout.item_home_image;
        @BindView(R.id.image_view_6)
        ImageView imageView;

        public ImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HotListHolder extends RecyclerView.ViewHolder {

        //        int id = R.layout.item_home_horizontal_recycler;
        @BindView(R.id.image_view)
        ImageView imageView;
        @BindView(R.id.recycle_view)
        RecyclerView recycleView;
        ItemHotListAdapter adapter;

        public HotListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            recycleView.setLayoutManager(manager);
            adapter = new ItemHotListAdapter();
            recycleView.setAdapter(adapter);
        }

        public void setData(List<TBKHomeEntity.CommodityBean> data) {
            adapter.setData(data);
        }
    }

    class ItemHotListAdapter extends RecyclerView.Adapter<ItemHorizontalViewHolder> {
        List<TBKHomeEntity.CommodityBean> data;

        public void setData(List<TBKHomeEntity.CommodityBean> data) {
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
            if (position == 0) {
                holder.imLive.setVisibility(View.VISIBLE);
                holder.imLive.setImageResource(R.mipmap.no_1);
            } else if (position == 1) {
                holder.imLive.setVisibility(View.VISIBLE);
                holder.imLive.setImageResource(R.mipmap.no_2);
            } else if (position == 2) {
                holder.imLive.setVisibility(View.VISIBLE);
                holder.imLive.setImageResource(R.mipmap.no_3);
            } else {
                holder.imLive.setVisibility(View.GONE);

            }
            Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(data.get(position).getOutIcon()).into(holder.imGoodsImg);
            holder.tvGoodsContent.setText(data.get(position).getName());
            holder.tvPrice.setText(data.get(position).getDiscountPrice() + "");
            holder.tvOldPrice.setText("￥" + data.get(position).getPrice() + "");
            holder.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
            holder.tvQuan.setText("券￥" + data.get(position).getCouponAmount());
            holder.itemView.setOnClickListener(v -> {
                GoodsDetailActivity.startGoodsDetailActivity(context, data.get(position).getItemId());
            });

        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    class ZoneListHolder extends RecyclerView.ViewHolder {

        //int id = R.layout.item_home_horizontal_recycler;
        @BindView(R.id.image_view)
        ImageView imageView;
        @BindView(R.id.recycle_view)
        RecyclerView recycleView;
        ItemZoneListAdapter adapter;

        public ZoneListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            adapter = new ItemZoneListAdapter();
            recycleView.setLayoutManager(manager);
            recycleView.setAdapter(adapter);
            imageView.setOnClickListener(v -> {
                EventBus.getDefault().post(new ChangeFragment(3));
            });
        }

        public void setData(List<TBKHomeEntity.GroupPurchasingListBean> data) {
            adapter.setData(data);
        }
    }

    class ItemZoneListAdapter extends RecyclerView.Adapter<ItemHorizontalViewHolder> {
        List<TBKHomeEntity.GroupPurchasingListBean> data = new ArrayList<>();

        public void setData(List<TBKHomeEntity.GroupPurchasingListBean> data) {
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
            Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(data.get(position).getProductImages().getThumbnail()).into(holder.imGoodsImg);
            holder.tvGoodsContent.setText(data.get(position).getProductName());
            holder.tvPrice.setText("0.0");
            holder.tvOldPrice.setText("￥" + data.get(position).getPrice() + "");
            holder.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
            holder.tvQuan.setText("券￥0.0");
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ZeroMoneyDetailsActivity.class);
                intent.putExtra("groupPurchasing_id", data.get(position).getGroupPurchasing_id() + "");
                context.startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    class ItemHorizontalViewHolder extends RecyclerView.ViewHolder {
        //        int id = R.layout.item_home_hot_goods;
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
        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_old_price)
        TextView tvOldPrice;
        @BindView(R.id.item_goods_layout)
        LinearLayout itemGoodsLayout;


        public ItemHorizontalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class GoodsViewHolder extends RecyclerView.ViewHolder {
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

        public GoodsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class GoodTitleHolder extends RecyclerView.ViewHolder {

        public GoodTitleHolder(View itemView) {
            super(itemView);
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

    /**
     * 0元购
     */
    private class GroupPurchasing {
        List<TBKHomeEntity.GroupPurchasingListBean> data;

        GroupPurchasing(List<TBKHomeEntity.GroupPurchasingListBean> data) {
            this.data = data;
        }
    }

    /**
     * 热卖
     */
    private class CommodityBean {
        List<TBKHomeEntity.CommodityBean> data;

        CommodityBean(List<TBKHomeEntity.CommodityBean> data) {
            this.data = data;
        }
    }

    /**
     * banner
     */
    private class BannersBean {
        static final int BANNER_TYPE_TOP = 1;
        static final int BANNER_TYPE_2 = 2;
        int bannerType;
        List<TBKHomeEntity.BannerBean> banners;

        BannersBean(List<TBKHomeEntity.BannerBean> banners, int bannerType) {
            this.bannerType = bannerType;
            this.banners = banners;
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

}

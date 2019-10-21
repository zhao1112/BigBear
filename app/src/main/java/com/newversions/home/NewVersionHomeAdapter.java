package com.newversions.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newversions.IAdvClick;
import com.newversions.detail.NewProductDetailActivity;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.ui.activity.BargainFreeActivity;
import com.yunqin.bearmall.ui.activity.ZeroMoneyActivity;
import com.yunqin.bearmall.widget.PriceView;
import com.yunqin.bearmall.widget.TopBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.newversions.home.NewVersionHomeAdapter.FRAGMENT_TYPE.HEADER_VIEW_0;
import static com.newversions.home.NewVersionHomeAdapter.FRAGMENT_TYPE.HEADER_VIEW_1;
import static com.newversions.home.NewVersionHomeAdapter.FRAGMENT_TYPE.HEADER_VIEW_2;
import static com.newversions.home.NewVersionHomeAdapter.FRAGMENT_TYPE.HEADER_VIEW_3;
import static com.newversions.home.NewVersionHomeAdapter.FRAGMENT_TYPE.HEADER_VIEW_4;
import static com.newversions.home.NewVersionHomeAdapter.FRAGMENT_TYPE.NORMA_TYPE1;
import static com.newversions.home.NewVersionHomeAdapter.FRAGMENT_TYPE.NORMA_TYPE2;
import static com.newversions.home.NewVersionHomeAdapter.FRAGMENT_TYPE.VIEW_TITLE_TEJIA;
import static com.newversions.home.NewVersionHomeAdapter.FRAGMENT_TYPE.VIEW_TITLE_TUIJIAN;

/**
 * Create By Master
 * On 2019/1/3 16:13
 */
public class NewVersionHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final int HEADER_COUNT = 3;
    private int DYNAMICALLY_ADD_COUNT = 0;

    private Context context;
    private HomeBean homeBeanList;

    List<HomeBean.DataBean.GroupPurchasingRecordBean.GroupPurchasingListBean> list2;// 拼团专区
    List<HomeBean.DataBean.BargainRecordBean.BargainListBean> list3;// 砍价专区
    List<HomeBean.DataBean.SpecialPriceProductListBean> list4;// 特价专区
    List<HomeBean.DataBean.ProductListBean> list5;// 商品列表
    List<Object> list6;

    private View headView0;
    private View headView1;
    private View headView2;


    public NewVersionHomeAdapter(Context context) {
        this.context = context;
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();
        list5 = new ArrayList<>();
        list6 = new ArrayList<>();
    }

    public void setData(HomeBean homeBeanList) {
        this.homeBeanList = homeBeanList;
        if (homeBeanList.getData() != null) {

            list2 = homeBeanList.getData().getGroupPurchasingRecord().getGroupPurchasingList();// 拼团专区
            list3 = homeBeanList.getData().getBargainRecord().getBargainList();// 砍价专区
            list4 = homeBeanList.getData().getSpecialPriceProductList();// 特价专区
            list5 = homeBeanList.getData().getProductList();// 商品列表

            if (list2 == null) {
                list2 = new ArrayList<>();
            }
            if (list3 == null) {
                list3 = new ArrayList<>();
            }

//            list2.clear();
//            list3.clear();
//            list4.clear();

            list6 = new ArrayList<>();

            if (list4 != null) {
                list6.addAll(list4);
            } else {
                list4 = new ArrayList<>();
            }
            if (list5 != null) {
                list6.addAll(list5);
            } else {
                list5 = new ArrayList<>();
            }
        } else {
            list2.clear();
            list3.clear();
            list4.clear();
            list5.clear();
            list6.clear();
        }
        this.notifyDataSetChanged();
    }

    public void addData(HomeBean homeBeanList) {
        // TODO 添加数据
        list5.addAll(homeBeanList.getData().getProductList());
        list6.addAll(homeBeanList.getData().getProductList());
        this.notifyDataSetChanged();
    }


    private void show4grid(List<NewHomeAd.DataBean.AdMobileMidListBean> lists2) {
        if (lists2 != null && lists2.size() > 0) {
            if (lists2.size() >= 4) {
                IImageView view1 = headView1.findViewById(R.id.n_v_home_image_2);
                IImageView view2 = headView1.findViewById(R.id.n_v_home_image_3);
                IImageView view3 = headView1.findViewById(R.id.n_v_home_image_4);
                IImageView view4 = headView1.findViewById(R.id.n_v_home_image_5);

                view1.setITag(lists2.get(0));
                view2.setITag(lists2.get(1));
                view3.setITag(lists2.get(2));
                view4.setITag(lists2.get(3));


                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                view3.setVisibility(View.VISIBLE);
                view4.setVisibility(View.VISIBLE);

                Glide.with(context).load(lists2.get(0).getImg()).into(view1);
                Glide.with(context).load(lists2.get(1).getImg()).into(view2);
                Glide.with(context).load(lists2.get(2).getImg()).into(view3);
                Glide.with(context).load(lists2.get(3).getImg()).into(view4);

            } else if (lists2.size() >= 3) {

                IImageView view1 = headView1.findViewById(R.id.n_v_home_image_2);
                IImageView view2 = headView1.findViewById(R.id.n_v_home_image_3);
                IImageView view3 = headView1.findViewById(R.id.n_v_home_image_4);
                IImageView view4 = headView1.findViewById(R.id.n_v_home_image_5);

                view1.setITag(lists2.get(0));
                view2.setITag(lists2.get(1));
                view3.setITag(lists2.get(2));

                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                view3.setVisibility(View.VISIBLE);
                view4.setVisibility(View.INVISIBLE);

                Glide.with(context).load(lists2.get(0).getImg()).into(view1);
                Glide.with(context).load(lists2.get(1).getImg()).into(view2);
                Glide.with(context).load(lists2.get(2).getImg()).into(view3);


            } else if (lists2.size() >= 2) {

                IImageView view1 = headView1.findViewById(R.id.n_v_home_image_2);
                IImageView view2 = headView1.findViewById(R.id.n_v_home_image_3);
                IImageView view3 = headView1.findViewById(R.id.n_v_home_image_4);
                IImageView view4 = headView1.findViewById(R.id.n_v_home_image_5);

                view1.setITag(lists2.get(0));
                view2.setITag(lists2.get(1));

                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                view3.setVisibility(View.GONE);
                view4.setVisibility(View.GONE);

                Glide.with(context).load(lists2.get(0).getImg()).into(view1);
                Glide.with(context).load(lists2.get(1).getImg()).into(view2);

            } else if (lists2.size() >= 1) {

                IImageView view1 = headView1.findViewById(R.id.n_v_home_image_2);
                IImageView view2 = headView1.findViewById(R.id.n_v_home_image_3);
                IImageView view3 = headView1.findViewById(R.id.n_v_home_image_4);
                IImageView view4 = headView1.findViewById(R.id.n_v_home_image_5);

                view1.setITag(lists2.get(0));


                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.GONE);
                view3.setVisibility(View.GONE);
                view4.setVisibility(View.GONE);

                Glide.with(context).load(lists2.get(0).getImg()).into(view1);
            }

        } else {
            headView1.findViewById(R.id.n_v_tt_r).setVisibility(View.GONE);
        }
    }

    private void showad1(List<NewHomeAd.DataBean.AdMobileCrossList1Bean> lists2) {
        if (lists2 != null && lists2.size() > 0) {
            IImageView view1 = headView1.findViewById(R.id.n_v_home_image_1);
            view1.setITag(lists2.get(0));
            Glide.with(context).load(lists2.get(0).getImg()).into(view1);
        }
    }

    private void showad2(List<NewHomeAd.DataBean.AdMobileCrossList2Bean> lists2) {
        if (lists2 != null && lists2.size() > 0) {
            headView2.setVisibility(View.VISIBLE);
            IImageView view1 = headView2.findViewById(R.id.n_v_tjw_img);
            view1.setITag(lists2.get(0));
            Glide.with(context).load(lists2.get(0).getImg()).into(view1);
        }else{
            headView2.setVisibility(View.GONE);
        }
    }


    /**
     * 设置广告
     */
    public void setBannerData(NewHomeAd homeAd) {
        TopBanner topBanner = headView0.findViewById(R.id.banner_top);
        if (homeAd != null && homeAd.getData() != null) {
            List<NewHomeAd.DataBean.AdMobileTopListBean> lists1 = homeAd.getData().getAdMobileTopList();
            show4grid(homeAd.getData().getAdMobileMidList());
            showad1(homeAd.getData().getAdMobileCrossList1());
            showad2(homeAd.getData().getAdMobileCrossList2());

            if (lists1 != null && lists1.size() > 0) {
                List<String> adList = new ArrayList<>();
                for (int i = 0; i < lists1.size(); i++) {
                    adList.add(lists1.get(i).getImg());
                }
                topBanner.setImagesUrl(adList);
                topBanner.setOnItemClickListener(new TopBanner.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        NewHomeAd.DataBean.AdMobileTopListBean adBean = lists1.get(position);
                        IAdvClick.click(context, adBean.getType(), adBean.getSkipType(), adBean.getSource_id(),null);
                    }
                });
            }
        }

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER_VIEW_0:
                headView0 = LayoutInflater.from(context).inflate(R.layout.new_version_main_banner, parent, false);
                return new BannerHolder(headView0);
            case HEADER_VIEW_1:
                headView1 = LayoutInflater.from(context).inflate(R.layout.new_version_featured_first_layout, parent, false);
                return new FeaturedFirstHolder(headView1);
            case HEADER_VIEW_2:
                View mNormalView = LayoutInflater.from(context).inflate(R.layout.new_version_group_layout, parent, false);
                return new PinTuanHolder(mNormalView);
            case HEADER_VIEW_3:
                View mNormalView1 = LayoutInflater.from(context).inflate(R.layout.new_version_group_layout, parent, false);
                return new KanJiaHolder(mNormalView1);
            case HEADER_VIEW_4:
                headView2 = LayoutInflater.from(context).inflate(R.layout.new_version_img_layout, parent, false);
                return new HoleHolder(headView2);

            case VIEW_TITLE_TEJIA:
                View mNormalView4 = LayoutInflater.from(context).inflate(R.layout.n_v_item_tejia_lauout, parent, false);
                return new TeJiaHolder(mNormalView4);

            case VIEW_TITLE_TUIJIAN:
                View mNormalView5 = LayoutInflater.from(context).inflate(R.layout.n_v_item_tuijian_lauout, parent, false);
                return new TuiJianHolder(mNormalView5);

            case NORMA_TYPE1:
                View mNormalView6 = LayoutInflater.from(context).inflate(R.layout.n_v_item_goods_tejia_lauout, parent, false);
                return new NormalHolder1(mNormalView6);

            case NORMA_TYPE2:
                View mNormalView3 = LayoutInflater.from(context).inflate(R.layout.n_v_item_goods_show2, parent, false);
                return new NormalHolder2(mNormalView3);
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Banner
        if (holder instanceof BannerHolder) {

        }
        // 推荐位
        if (holder instanceof FeaturedFirstHolder) {
            ((FeaturedFirstHolder) holder).n_v_home_1.setOnClickListener(this);
            ((FeaturedFirstHolder) holder).n_v_home_2.setOnClickListener(this);
            ((FeaturedFirstHolder) holder).n_v_home_3.setOnClickListener(this);
            ((FeaturedFirstHolder) holder).n_v_home_4.setOnClickListener(this);
            ((FeaturedFirstHolder) holder).n_v_home_5.setOnClickListener(this);
            ((FeaturedFirstHolder) holder).n_v_home_6.setOnClickListener(this);
            ((FeaturedFirstHolder) holder).n_v_home_7.setOnClickListener(this);
            ((FeaturedFirstHolder) holder).n_v_home_8.setOnClickListener(this);

            ((FeaturedFirstHolder) holder).n_v_home_image_1.setOnClickListener(this);
            ((FeaturedFirstHolder) holder).n_v_home_image_2.setOnClickListener(this);
            ((FeaturedFirstHolder) holder).n_v_home_image_3.setOnClickListener(this);
            ((FeaturedFirstHolder) holder).n_v_home_image_4.setOnClickListener(this);
            ((FeaturedFirstHolder) holder).n_v_home_image_5.setOnClickListener(this);


        }
        // 拼团
        if (holder instanceof PinTuanHolder) {
            ((PinTuanHolder) holder).n_v_bottom_btn.setText("更多0元兑");
            ((PinTuanHolder) holder).n_v_bottom_btn.setBackgroundColor(Color.parseColor("#23A064"));
            ((PinTuanHolder) holder).n_v_top_bg.setBackgroundResource(R.drawable.default_tuijian);

            ((PinTuanHolder) holder).n_v_top_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(context, ZeroMoneyActivity.class);
                    context.startActivity(intent1);
                }
            });

            ((PinTuanHolder) holder).n_v_bottom_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(context, ZeroMoneyActivity.class);
                    context.startActivity(intent1);
                }
            });

            if (homeBeanList != null
                    && homeBeanList.getData() != null
                    && homeBeanList.getData().getGroupPurchasingRecord() != null
                    && homeBeanList.getData().getGroupPurchasingRecord().getGroupPurchasingAdList() != null
                    && homeBeanList.getData().getGroupPurchasingRecord().getGroupPurchasingAdList().size() > 0
                    ) {
                HomeBean.DataBean.GroupPurchasingRecordBean.GroupPurchasingAdListBean groupPurchasingAdListBean = homeBeanList.getData().getGroupPurchasingRecord().getGroupPurchasingAdList().get(0);
                Glide.with(context).load(groupPurchasingAdListBean.getImg()).into(((PinTuanHolder) holder).n_v_top_bg);
            }


            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            ((PinTuanHolder) holder).n_v_child_recycler_view.setLayoutManager(layoutManager);
            NewVersionPinTuanAdapter pinTuanAdapter = new NewVersionPinTuanAdapter(context, list2);
            ((PinTuanHolder) holder).n_v_child_recycler_view.setAdapter(pinTuanAdapter);
        }
        // 砍价
        if (holder instanceof KanJiaHolder) {
            ((KanJiaHolder) holder).n_v_bottom_btn.setText("更多砍价商品");
            ((KanJiaHolder) holder).n_v_bottom_btn.setBackgroundColor(Color.parseColor("#FEB831"));
            ((KanJiaHolder) holder).n_v_top_bg.setBackgroundResource(R.drawable.default_tuijian);

            ((KanJiaHolder) holder).n_v_top_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(context, BargainFreeActivity.class);
                    context.startActivity(intent1);
                }
            });

            ((KanJiaHolder) holder).n_v_bottom_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(context, BargainFreeActivity.class);
                    context.startActivity(intent1);
                }
            });

            if (homeBeanList != null
                    && homeBeanList.getData() != null
                    && homeBeanList.getData().getBargainRecord() != null
                    && homeBeanList.getData().getBargainRecord().getBargainAdList() != null
                    && homeBeanList.getData().getBargainRecord().getBargainAdList().size() > 0) {
                HomeBean.DataBean.BargainRecordBean.BargainAdListBean bargainAdListBean = homeBeanList.getData().getBargainRecord().getBargainAdList().get(0);
                Glide.with(context).load(bargainAdListBean.getImg()).into(((KanJiaHolder) holder).n_v_top_bg);
            }

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            ((KanJiaHolder) holder).n_v_child_recycler_view.setLayoutManager(layoutManager);
            NewVersionKanJiaAdapter kanJiaAdapter = new NewVersionKanJiaAdapter(context, list3);
            ((KanJiaHolder) holder).n_v_child_recycler_view.setAdapter(kanJiaAdapter);

        }
        // 图片推荐
        if (holder instanceof HoleHolder) {
            ((HoleHolder) holder).n_v_tjw_img.setOnClickListener(this);
        }

        if (holder instanceof NormalHolder1) {

            HomeBean.DataBean.SpecialPriceProductListBean specialPriceProductListBean = (HomeBean.DataBean.SpecialPriceProductListBean) list6.get(getTrueLocation(position, holder));


            ((NormalHolder1) holder).price_view.setPrice(specialPriceProductListBean.getMembershipPrice(), specialPriceProductListBean.getPrice(), specialPriceProductListBean.isIsSurportMsp()
                    , specialPriceProductListBean.getIsDiscount(), specialPriceProductListBean.getModel(), specialPriceProductListBean.getSourceMembershipPrice(), specialPriceProductListBean.getSourcePrice());

            ((NormalHolder1) holder).n_v_tj_name.setText(specialPriceProductListBean.getProductName());
            ((NormalHolder1) holder).n_v_tj_maiguo.setText(String.format("%d人付款", specialPriceProductListBean.getSales()));


            ((NormalHolder1) holder).n_v_tj_goumai.setTag(specialPriceProductListBean);
            ((NormalHolder1) holder).n_v_tj_goumai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HomeBean.DataBean.SpecialPriceProductListBean spBean = (HomeBean.DataBean.SpecialPriceProductListBean) view.getTag();

                    Intent intent = new Intent(context, NewProductDetailActivity.class);
                    intent.putExtra("productId", "" + spBean.getProduct_id());
                    intent.putExtra("sku_id", "");
                    context.startActivity(intent);
                }
            });

            ((NormalHolder1) holder).n_v_tj_xiangqing.setTag(specialPriceProductListBean);
            ((NormalHolder1) holder).n_v_tj_xiangqing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HomeBean.DataBean.SpecialPriceProductListBean spBean = (HomeBean.DataBean.SpecialPriceProductListBean) view.getTag();
                    Intent intent = new Intent(context, NewProductDetailActivity.class);
                    intent.putExtra("productId", "" + spBean.getProduct_id());
                    intent.putExtra("sku_id", "");
                    context.startActivity(intent);

                }
            });
            Glide.with(context).load(specialPriceProductListBean.getProductImages()).into(((NormalHolder1) holder).n_v_tj_image);
        }

        if (holder instanceof NormalHolder2) {

            HomeBean.DataBean.ProductListBean productListBean = (HomeBean.DataBean.ProductListBean) list6.get(getTrueLocation(position, holder));

            if (productListBean.isIsCrossBorder()) {
                ((NormalHolder2) holder).n_v_t_img_t.setVisibility(View.VISIBLE);
                ((NormalHolder2) holder).n_v_t_img_t.setImageResource(R.drawable.n_v_qqg);
            } else if (productListBean.isIsNew()) {
                ((NormalHolder2) holder).n_v_t_img_t.setVisibility(View.VISIBLE);
                ((NormalHolder2) holder).n_v_t_img_t.setImageResource(R.drawable.n_v_xp);
            } else {
                ((NormalHolder2) holder).n_v_t_img_t.setVisibility(View.GONE);
            }

            ((NormalHolder2) holder).n_v_t_name.setText(productListBean.getProductName());

            try {


                ((NormalHolder2) holder).price_view.setPrice(productListBean.getMembershipPrice(), productListBean.getPrice(), productListBean.isIsSurportMsp(), productListBean.getIsDiscount(), productListBean.getModel(),
                        productListBean.getSourceMembershipPrice(), productListBean.getSourcePrice());


            } catch (Exception e) {
                e.printStackTrace();
            }


            ((NormalHolder2) holder).n_v_t_linear.setTag(productListBean);
            ((NormalHolder2) holder).n_v_t_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, NewProductDetailActivity.class);
                    intent.putExtra("productId", "" + productListBean.getProduct_id());
                    intent.putExtra("sku_id", "");
                    context.startActivity(intent);
                }
            });
            try {
                Glide.with(context).load(productListBean.getProductImages().getSource()).into(((NormalHolder2) holder).n_v_t_img);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        DYNAMICALLY_ADD_COUNT = 0;
        if (list2 != null && list2.size() > 0) {
            DYNAMICALLY_ADD_COUNT++;
        }
        if (list3 != null && list3.size() > 0) {
            DYNAMICALLY_ADD_COUNT++;
        }
        if (list4 != null && list4.size() > 0) {
            DYNAMICALLY_ADD_COUNT++;
        }
        if (list5 != null && list5.size() > 0) {
            DYNAMICALLY_ADD_COUNT++;
        }

        if (list6 != null) {
            return list6.size() + DYNAMICALLY_ADD_COUNT + HEADER_COUNT;
        }
        return DYNAMICALLY_ADD_COUNT + HEADER_COUNT;
    }

    @Override
    public void onClick(View view) {
        // TODO 推荐位处理
        if (onItemClickListener != null) {
            onItemClickListener.onHomeChildClick(view);
        }
    }

    @IntDef({HEADER_VIEW_0, HEADER_VIEW_1, HEADER_VIEW_2, HEADER_VIEW_3, VIEW_TITLE_TEJIA, VIEW_TITLE_TUIJIAN, HEADER_VIEW_4, NORMA_TYPE1, NORMA_TYPE2})
    public @interface FRAGMENT_TYPE {
        int HEADER_VIEW_0 = 0;
        int HEADER_VIEW_1 = 1;
        int HEADER_VIEW_2 = 2;
        int HEADER_VIEW_3 = 3;
        int HEADER_VIEW_4 = 4;
        int VIEW_TITLE_TEJIA = 5;
        int VIEW_TITLE_TUIJIAN = 6;
        int NORMA_TYPE1 = 7;
        int NORMA_TYPE2 = 8;
    }

    @Override
    public int getItemViewType(int position) {
        if (position <= 1) {
            return position;
        }

        if (position == 2) {
            if (list2 != null && list2.size() > 0) {
                return 2;
            }
        }

        if (position == 3 - ((list2 != null && list2.size() > 0) ? 0 : 1) && list3 != null && list3.size() > 0) {
            return 3;
        }

        if (position == 4 - ((list2 != null && list2.size() > 0) ? 0 : 1) - ((list3 != null && list3.size() > 0) ? 0 : 1)) {
            return 4;
        }


        if (position == 5 - ((list2 != null && list2.size() > 0) ? 0 : 1) - ((list3 != null && list3.size() > 0) ? 0 : 1) && list4 != null && list4.size() > 0) {
            return VIEW_TITLE_TEJIA;
        }

        if (list4 != null && list4.size() > 0 && position < list4.size() + DYNAMICALLY_ADD_COUNT + HEADER_COUNT - 1) {
            return NORMA_TYPE1;
        }

        if (list5 != null && list5.size() > 0 && position == list4.size() + DYNAMICALLY_ADD_COUNT + HEADER_COUNT - 1) {
            return VIEW_TITLE_TUIJIAN;
        }

        return NORMA_TYPE2;
    }

    public int getItemColumnCount(int position) {
        int itemType = getItemViewType(position);
        return itemType == NORMA_TYPE2 ? 1 : 2;
    }


    /**
     * banner
     */
    class BannerHolder extends RecyclerView.ViewHolder {

        public BannerHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 推荐位
     */
    class FeaturedFirstHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.n_v_home_1)
        LinearLayout n_v_home_1;
        @BindView(R.id.n_v_home_2)
        LinearLayout n_v_home_2;
        @BindView(R.id.n_v_home_3)
        LinearLayout n_v_home_3;
        @BindView(R.id.n_v_home_4)
        LinearLayout n_v_home_4;
        @BindView(R.id.n_v_home_5)
        LinearLayout n_v_home_5;
        @BindView(R.id.n_v_home_6)
        LinearLayout n_v_home_6;
        @BindView(R.id.n_v_home_7)
        LinearLayout n_v_home_7;
        @BindView(R.id.n_v_home_8)
        LinearLayout n_v_home_8;
        @BindView(R.id.n_v_home_image_1)
        ImageView n_v_home_image_1;
        @BindView(R.id.n_v_home_image_2)
        ImageView n_v_home_image_2;
        @BindView(R.id.n_v_home_image_3)
        ImageView n_v_home_image_3;
        @BindView(R.id.n_v_home_image_4)
        ImageView n_v_home_image_4;
        @BindView(R.id.n_v_home_image_5)
        ImageView n_v_home_image_5;


        public FeaturedFirstHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class PinTuanHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.n_v_bottom_btn)
        Button n_v_bottom_btn;
        @BindView(R.id.n_v_top_bg)
        ImageView n_v_top_bg;
        @BindView(R.id.n_v_child_recycler_view)
        RecyclerView n_v_child_recycler_view;


        public PinTuanHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class KanJiaHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.n_v_bottom_btn)
        Button n_v_bottom_btn;
        @BindView(R.id.n_v_top_bg)
        ImageView n_v_top_bg;
        @BindView(R.id.n_v_child_recycler_view)
        RecyclerView n_v_child_recycler_view;


        public KanJiaHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class TeJiaHolder extends RecyclerView.ViewHolder {

        public TeJiaHolder(View itemView) {
            super(itemView);
        }
    }

    class TuiJianHolder extends RecyclerView.ViewHolder {

        public TuiJianHolder(View itemView) {
            super(itemView);
        }
    }

    class HoleHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.n_v_tjw_img)
        ImageView n_v_tjw_img;

        public HoleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    /**
     * 商品1
     */

    class NormalHolder1 extends RecyclerView.ViewHolder {

        @BindView(R.id.n_v_tj_xiangqing)
        LinearLayout n_v_tj_xiangqing;
        @BindView(R.id.n_v_tj_goumai)
        Button n_v_tj_goumai;
        @BindView(R.id.n_v_tj_image)
        ImageView n_v_tj_image;
        @BindView(R.id.n_v_tj_maiguo)
        TextView n_v_tj_maiguo;
        @BindView(R.id.n_v_tj_name)
        TextView n_v_tj_name;

        @BindView(R.id.price_view)
        PriceView price_view;


        public NormalHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class FooterHolder extends RecyclerView.ViewHolder {

        public FooterHolder(View itemView) {
            super(itemView);
        }
    }


    class NormalHolder2 extends RecyclerView.ViewHolder {


        @BindView(R.id.price_view)
        PriceView price_view;


        @BindView(R.id.n_v_t_name)
        TextView n_v_t_name;


        @BindView(R.id.n_v_t_img_t)
        ImageView n_v_t_img_t;
        @BindView(R.id.n_v_t_img)
        ImageView n_v_t_img;
        @BindView(R.id.n_v_t_linear)
        LinearLayout n_v_t_linear;

        public NormalHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public int getTrueLocation(int position, RecyclerView.ViewHolder holder) {
        int trueLocation = 3;
        if (holder instanceof NormalHolder1) {
            if (list2 != null && list2.size() > 0) {
                trueLocation++;
            }
            if (list3 != null && list3.size() > 0) {
                trueLocation++;
            }

            if (list4 != null && list4.size() > 0) {
                trueLocation++;
            }


            trueLocation = position - trueLocation;
        }

        if (holder instanceof NormalHolder2) {
            if (list2 != null && list2.size() > 0) {
                trueLocation++;
            }
            if (list3 != null && list3.size() > 0) {
                trueLocation++;
            }

            if (list4 != null && list4.size() > 0) {
                trueLocation++;
            }

            if (list5 != null && list5.size() > 0) {
                trueLocation++;
            }
            trueLocation = position - trueLocation;
        }
        return trueLocation;
    }


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view);

        void onHomeChildClick(View view);
    }


}

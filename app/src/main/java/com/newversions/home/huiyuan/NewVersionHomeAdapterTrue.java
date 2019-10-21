package com.newversions.home.huiyuan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
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
import com.newversions.home.NewVersionHomeAdapter;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.BannerBean;
import com.yunqin.bearmall.widget.PriceView;
import com.yunqin.bearmall.widget.TopBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.newversions.home.huiyuan.NewVersionHomeAdapterTrue.FRAGMENT_TYPE.TYPE_HEADER;
import static com.newversions.home.huiyuan.NewVersionHomeAdapterTrue.FRAGMENT_TYPE.TYPE_TJBK;
import static com.newversions.home.huiyuan.NewVersionHomeAdapterTrue.FRAGMENT_TYPE.TYPE_TJBK_GOODS;
import static com.newversions.home.huiyuan.NewVersionHomeAdapterTrue.FRAGMENT_TYPE.TYPE_WNTJ;
import static com.newversions.home.huiyuan.NewVersionHomeAdapterTrue.FRAGMENT_TYPE.TYPE_WNTJ_GOODS;


/**
 * Create By Master
 * On 2019/1/3 16:13
 */
public class NewVersionHomeAdapterTrue extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private View headView0;
    private HuiYuanHomeBean huiYuanHomeBean;

    private List<HuiYuanHomeBean.DataBean.SpecialPriceProductListBean> list1 = new ArrayList();
    private List<HuiYuanHomeBean.DataBean.ProductListBean> list2 = new ArrayList();
    private List<Object> list3 = new ArrayList();


    public NewVersionHomeAdapterTrue(Context context) {
        this.context = context;
    }

    public void setData(HuiYuanHomeBean homeBeanList) {
        // TODO 设置数据
        list1.clear();
        list2.clear();
        list3.clear();

        huiYuanHomeBean = homeBeanList;
        list1 = huiYuanHomeBean.getData().getSpecialPriceProductList();
        list2 = huiYuanHomeBean.getData().getProductList();

        if (list1 == null) {
            list1 = new ArrayList<>();
        }
        if (list2 == null) {
            list2 = new ArrayList<>();
        }
        if (list1 != null && list1.size() > 0) {
            list3.add("0");
            list3.addAll(list1);
        }
        if (list2 != null && list2.size() > 0) {
            list3.add("1");
            list3.addAll(list2);
        }
        this.notifyDataSetChanged();
    }

    public void addData(HuiYuanHomeBean homeBeanList) {
        // TODO 添加数据
        List<HuiYuanHomeBean.DataBean.ProductListBean> list = homeBeanList.getData().getProductList();
        list3.addAll(list);
        this.notifyDataSetChanged();
    }


    /**
     * 设置广告
     */
    public void setBannerData(BannerBean homeAd) {
        TopBanner topBanner = headView0.findViewById(R.id.banner_top);
        if (homeAd != null && homeAd.getData() != null) {
            List<BannerBean.DataBean.AdMobileListBean> lists1 = homeAd.getData().getAdMobileList();

            if (lists1 != null && lists1.size() > 0) {
                List<String> adList = new ArrayList<>();
                for (int i = 0; i < lists1.size(); i++) {
                    adList.add(lists1.get(i).getImg());
                }
                topBanner.setImagesUrl(adList);
                topBanner.setOnItemClickListener(new TopBanner.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        BannerBean.DataBean.AdMobileListBean adBean = lists1.get(position);
                        int type = adBean.getType();
                        int skipType = adBean.getSkipType();
                        long sourceId = adBean.getSource_id();
                        IAdvClick.click(context, type, skipType, sourceId,null);
                    }
                });
            }
        }

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                headView0 = LayoutInflater.from(context).inflate(R.layout.new_version_main_banner, null, false);
                return new BannerHolder(headView0);
            case TYPE_TJBK:
                View mNormalView4 = LayoutInflater.from(context).inflate(R.layout.n_v_item_tejia_lauout, null, false);
                return new TeJiaHolder(mNormalView4);
            case TYPE_WNTJ:
                View mNormalView5 = LayoutInflater.from(context).inflate(R.layout.n_v_item_tuijian_lauout, null, false);
                return new TuiJianHolder(mNormalView5);
            case TYPE_TJBK_GOODS:
                View mNormalView6 = LayoutInflater.from(context).inflate(R.layout.n_v_item_goods_tejia_lauout, null, false);
                return new NormalHolder1(mNormalView6);
            case TYPE_WNTJ_GOODS:
                View mNormalView3 = LayoutInflater.from(context).inflate(R.layout.n_v_item_goods_show2, null, false);
                return new NormalHolder2(mNormalView3);
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalHolder1) {

            HuiYuanHomeBean.DataBean.SpecialPriceProductListBean specialPriceProductListBean = (HuiYuanHomeBean.DataBean.SpecialPriceProductListBean) list3.get(getTrueLocation(position));
            ((NormalHolder1) holder).price_view.setPrice(specialPriceProductListBean.getMembershipPrice(), specialPriceProductListBean.getPrice(), specialPriceProductListBean.isIsSurportMsp()
                    , specialPriceProductListBean.getIsDiscount(), specialPriceProductListBean.getModel(), specialPriceProductListBean.getSourceMembershipPrice(), specialPriceProductListBean.getSourcePrice());

            ((NormalHolder1) holder).n_v_tj_name.setText(specialPriceProductListBean.getProductName());
            ((NormalHolder1) holder).n_v_tj_maiguo.setText(String.format("%d人付款", specialPriceProductListBean.getSales()));


            ((NormalHolder1) holder).n_v_tj_goumai.setTag(specialPriceProductListBean);
            ((NormalHolder1) holder).n_v_tj_goumai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HuiYuanHomeBean.DataBean.SpecialPriceProductListBean spBean = (HuiYuanHomeBean.DataBean.SpecialPriceProductListBean) view.getTag();

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
                    HuiYuanHomeBean.DataBean.SpecialPriceProductListBean spBean = (HuiYuanHomeBean.DataBean.SpecialPriceProductListBean) view.getTag();
                    Intent intent = new Intent(context, NewProductDetailActivity.class);
                    intent.putExtra("productId", "" + spBean.getProduct_id());
                    intent.putExtra("sku_id", "");
                    context.startActivity(intent);

                }
            });
            Glide.with(context).load(specialPriceProductListBean.getProductImages()).into(((NormalHolder1) holder).n_v_tj_image);
        }

        if (holder instanceof NormalHolder2) {

            HuiYuanHomeBean.DataBean.ProductListBean productListBean = (HuiYuanHomeBean.DataBean.ProductListBean) list3.get(getTrueLocation(position));

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
        return list3.size() + 1;
    }


    @IntDef({TYPE_HEADER, TYPE_TJBK, TYPE_WNTJ, TYPE_TJBK_GOODS, TYPE_WNTJ_GOODS})
    public @interface FRAGMENT_TYPE {
        int TYPE_HEADER = 0;
        int TYPE_TJBK = 1;
        int TYPE_WNTJ = 2;
        int TYPE_TJBK_GOODS = 3;
        int TYPE_WNTJ_GOODS = 4;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }

        if (list3.get(position - 1) instanceof String) {
            if (list3.get(position - 1).equals("0")) {
                return TYPE_TJBK;
            } else {
                return TYPE_WNTJ;
            }
        }

        if (list3.get(position - 1) instanceof HuiYuanHomeBean.DataBean.SpecialPriceProductListBean) {
            return TYPE_TJBK_GOODS;
        }

        return TYPE_WNTJ_GOODS;
    }

    public int getItemColumnCount(int position) {
        int itemType = getItemViewType(position);
        return itemType == TYPE_WNTJ_GOODS ? 1 : 2;
    }


    /**
     * banner
     */
    class BannerHolder extends RecyclerView.ViewHolder {

        public BannerHolder(View itemView) {
            super(itemView);
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


    public int getTrueLocation(int position) {
        return position - 1;
    }

}

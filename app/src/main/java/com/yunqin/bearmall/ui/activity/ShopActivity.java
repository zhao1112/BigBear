package com.yunqin.bearmall.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.bumptech.glide.Glide;
import com.newversions.NewWebViewActivity;
import com.newversions.detail.NewProductDetailActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.ShopTableAdapter;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.ShopBean;
import com.yunqin.bearmall.eventbus.CollectionShopEvent;
import com.yunqin.bearmall.ui.activity.contract.ShopActivtyContract;
import com.yunqin.bearmall.ui.activity.presenter.ShopActivityPresenter;
import com.yunqin.bearmall.widget.CircleImageView;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;
import com.yunqin.bearmall.widget.TopBanner;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2018/7/14
 * @Describe
 */
public class ShopActivity extends BaseActivity implements ShopActivtyContract.UI {

    private ShopActivtyContract.Persenter persenter;

    @BindView(R.id.banner_top)
    TopBanner banner_top;

    @BindView(R.id.xtablelayout)
    XTabLayout xtablelayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.shop_img)
    CircleImageView shop_img;

    @BindView(R.id.shop_name)
    TextView shop_name;

    @BindView(R.id.lable_one)
    TextView lable_one;

    @BindView(R.id.lable_two)
    TextView lable_two;

    @BindView(R.id.goods_text)
    TextView goods_text;

    @BindView(R.id.sale_text)
    TextView sale_text;

    @BindView(R.id.in_shop)
    HighlightButton in_shop;

    @BindView(R.id.back)
    RelativeLayout back;

    ShopTableAdapter adapter;
    private String store_id = "";

    private int isFavorite = 0;
    private int collection_index = -1;

    @Override
    public int layoutId() {
        return R.layout.activity_shop_layout;
    }


    @OnClick({R.id.back, R.id.in_shop})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.in_shop:
                Constans.params.clear();
                Constans.params.put("store_id", store_id);
                if (isFavorite == 0) {
                    Constans.params.put("isFavorite", 1 + "");
                } else {
                    Constans.params.put("isFavorite", 0 + "");
                }
                persenter.collectionShop(Constans.params);
                break;
        }
    }

    @Override
    public void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            store_id = (String) bundle.get("store_id");
            if (bundle.getInt("collection_index", -1) != -1) {
                collection_index = bundle.getInt("collection_index", -1);
            }
        }
        Constans.params.clear();
        Constans.params.put("store_id", store_id);

        persenter = new ShopActivityPresenter(this, this);
        persenter.star();
    }

    @Override
    public Map getParams() {
        return Constans.params;
    }

    @Override
    public void isCollectionShop() {
        if (isFavorite == 0) {
            isFavorite = 1;
            showToast("收藏店铺成功");
            in_shop.setText("已收藏");
            in_shop.setBackground(this.getResources().getDrawable(R.drawable.canle_collection));
        } else {
            isFavorite = 0;
            showToast("已取消收藏店铺");
            in_shop.setText("收藏店铺");
            showToast("已取消收藏");
            in_shop.setBackground(this.getResources().getDrawable(R.drawable.in_shop_look));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFavorite == 0) {
            if (collection_index != -1) {
                EventBus.getDefault().post(new CollectionShopEvent(collection_index));
            }
        }
    }

    @Override
    public void initBinner(List<ShopBean.DataBean.AdListBean> adListBeans) {
        if (adListBeans == null || adListBeans.size() <= 0) {
            return;
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < adListBeans.size(); i++) {
            list.add(adListBeans.get(i).getImage());
        }
        banner_top.setImagesUrl(list);
        banner_top.setOnItemClickListener(new TopBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ShopBean.DataBean.AdListBean bean = adListBeans.get(position);
//                    0：普通商品 1：说明广告 2：导购文章 4：会员往期活动 5：0元拼团 6：砍价免费拿 7 糖果夺宝
                if (bean.getType() == 0) {

                    Intent intent = new Intent(ShopActivity.this, NewProductDetailActivity.class);
                    intent.putExtra("productId", "" + bean.getSource_id());
                    intent.putExtra("sku_id", "");
                    startActivity(intent);
                } else if (bean.getType() == 2) {

                    String guidelUrl = BuildConfig.BASE_URL + "/view/findGuideArticleDetailPage?guideArticle_id=" + bean.getSource_id();
                    VanguardListPageActivity.startH5Activity(ShopActivity.this, guidelUrl, "导购详情");

                }
            }
        });
    }

    @Override
    public void initXtableLayout(List<ShopBean.DataBean.TagListBean> lables) {
        if (lables == null) {
            return;
        }
        for (int i = 0; i < lables.size(); i++) {
            xtablelayout.addTab(xtablelayout.newTab().setText(lables.get(i).getTag_name()));
        }
        xtablelayout.addTab(xtablelayout.newTab().setText("店铺活动"));
        adapter = new ShopTableAdapter(ShopActivity.this, getSupportFragmentManager(), lables, store_id);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        viewpager.setOffscreenPageLimit(3);
        //初始化显示第一个页面
        xtablelayout.setupWithViewPager(viewpager);
        xtablelayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void initTopLayout(ShopBean shopBean) {
        Glide.with(this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(shopBean.getData().getStore().getLogo()).into(shop_img);
        shop_name.setText(shopBean.getData().getStore().getStore_name());
        if (shopBean.getData().getStore().getType() == 1) {
            lable_one.setVisibility(View.VISIBLE);
        } else {
            lable_one.setVisibility(View.INVISIBLE);
        }

        if (shopBean.getData().getStore().isHasQualification()) {
            lable_two.setVisibility(View.VISIBLE);
        } else {
            lable_two.setVisibility(View.GONE);
        }

        lable_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopActivity.this, NewWebViewActivity.class);
                intent.putExtra("store_id", shopBean.getData().getStore().getStore_id() + "");
                startActivity(intent);
            }
        });


        goods_text.setText(Html.fromHtml(getString(R.string.goods_number, shopBean.getData().getStore().getProductNumber() + "")));
        sale_text.setText(Html.fromHtml(getString(R.string.sale_number, shopBean.getData().getStore().getProductSales() + "")));
        in_shop.setText("收藏店铺");

        isFavorite = shopBean.getData().getIsFavorite();

        if (shopBean.getData().getIsFavorite() == 1) {
            in_shop.setText("已收藏");
            in_shop.setBackground(this.getResources().getDrawable(R.drawable.canle_collection));
        } else {
            in_shop.setText("收藏店铺");
            in_shop.setBackground(this.getResources().getDrawable(R.drawable.in_shop_look));
        }
    }
}

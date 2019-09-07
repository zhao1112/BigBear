package com.newversions.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newversions.NewWebViewActivity;
import com.newversions.detail.NewProductBean;
import com.newversions.detail.NewProductDetailActivity;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.ui.activity.ShopActivity;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.CircleImageView;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;
import com.yunqin.bearmall.widget.PriceView;

/**
 * Create By Master
 * On 2019/1/15 11:24
 */
public class NewShopView extends LinearLayout {

    private Context context;
    private CircleImageView shop_img;
    private TextView goods_text;
    private TextView sale_text;
    private HighlightButton in_shop;
    private TextView lable_one;
    private TextView lable_two;
    private ImageView goods_1;
    private ImageView goods_2;
    private PriceView price_left;
    private PriceView price_right;
    private LinearLayout left;
    private LinearLayout right;

    public NewShopView(Context context) {
        this(context, null);
    }

    public NewShopView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewShopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }


    TextView shop_name;

    private void initViews(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.item_menu_shop, this, true);
        shop_img = findViewById(R.id.shop_img);
        shop_name = findViewById(R.id.shop_name);
        goods_text = findViewById(R.id.goods_text);
        sale_text = findViewById(R.id.sale_text);
        in_shop = findViewById(R.id.in_shop);
        lable_one = findViewById(R.id.lable_one);
        lable_two = findViewById(R.id.lable_two);
        goods_1 = findViewById(R.id.goods_1);
        goods_2 = findViewById(R.id.goods_2);
        price_left = findViewById(R.id.price_left);
        price_right = findViewById(R.id.price_right);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
    }

    public void setData(NewProductBean.DataBean.StoreBean storeBean) {

        if (storeBean.getType() == 0) {
            lable_one.setVisibility(View.GONE);
        } else {
            lable_one.setVisibility(View.VISIBLE);
        }

        if (storeBean.isHasQualification()) {
            lable_two.setVisibility(View.VISIBLE);
        } else {
            lable_two.setVisibility(View.GONE);
        }

        lable_two.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, NewWebViewActivity.class);
                intent.putExtra("store_id", storeBean.getStore_id() + "");
                context.startActivity(intent);
            }
        });


        shop_name.setText(storeBean.getStore_name());
        Glide.with(context).load(storeBean.getLogo()).into(shop_img);


        goods_text.setText(Html.fromHtml(context.getString(R.string.goods_number, String.valueOf(storeBean.getProductNumber()))));
        sale_text.setText(Html.fromHtml(context.getString(R.string.sale_number, String.valueOf(storeBean.getProductSales()))));
        in_shop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("store_id", String.valueOf(storeBean.getStore_id()));
                StarActivityUtil.starActivity((Activity) context, ShopActivity.class, bundle);
            }
        });


        if (storeBean.getHotProductList() != null) {
            if (storeBean.getHotProductList().size() > 0) {
                left.setVisibility(VISIBLE);
                Glide.with(context).load(storeBean.getHotProductList().get(0).getProductImages().getSource()).into(goods_1);
                price_left.setPrice(storeBean.getHotProductList().get(0).getMembershipPrice(),
                        storeBean.getHotProductList().get(0).getPrice(),
                        storeBean.getHotProductList().get(0).isIsSurportMsp(),
                        storeBean.getHotProductList().get(0).getIsDiscount(),
                        storeBean.getHotProductList().get(0).getModel(),
                        storeBean.getHotProductList().get(0).getSourceMembershipPrice(),
                        storeBean.getHotProductList().get(0).getSourcePrice());


                left.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, NewProductDetailActivity.class);
                        intent.putExtra("productId", String.valueOf(storeBean.getHotProductList().get(0).getProduct_id()));
                        context.startActivity(intent);
                    }
                });


            } else {
                left.setVisibility(INVISIBLE);
            }
            if (storeBean.getHotProductList().size() > 1) {
                right.setVisibility(VISIBLE);
                Glide.with(context).load(storeBean.getHotProductList().get(1).getProductImages().getSource()).into(goods_2);
                price_right.setPrice(storeBean.getHotProductList().get(1).getMembershipPrice(),
                        storeBean.getHotProductList().get(1).getPrice(),
                        storeBean.getHotProductList().get(1).isIsSurportMsp(),
                        storeBean.getHotProductList().get(1).getIsDiscount(),
                        storeBean.getHotProductList().get(1).getModel(),
                        storeBean.getHotProductList().get(1).getSourceMembershipPrice(),
                        storeBean.getHotProductList().get(1).getSourcePrice());


                right.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, NewProductDetailActivity.class);
                        intent.putExtra("productId", String.valueOf(storeBean.getHotProductList().get(1).getProduct_id()));
                        context.startActivity(intent);
                    }
                });
            } else {
                right.setVisibility(INVISIBLE);
            }
        }
    }


}

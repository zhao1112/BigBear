package com.yunqin.bearmall.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newversions.NewWebViewActivity;
import com.newversions.detail.NewProductDetailActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.MenuShop;
import com.yunqin.bearmall.ui.activity.ShopActivity;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;
import com.yunqin.bearmall.widget.PriceView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author AYWang
 * @create 2018/7/13
 * @Describe
 */
public class MenuShopsAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context mContext;
    private List<MenuShop.DataBean.StoreListBean> storeListBeanList;


    public List<MenuShop.DataBean.StoreListBean> getData() {
        return storeListBeanList;
    }

    public void addData(List<MenuShop.DataBean.StoreListBean> datas) {
        storeListBeanList.addAll(datas);
        notifyDataSetChanged();
    }


    public void setData(List<MenuShop.DataBean.StoreListBean> datas) {
        if (datas == null) {
            storeListBeanList.clear();
            notifyDataSetChanged();
            return;
        }
        this.storeListBeanList = datas;
        notifyDataSetChanged();
    }


    public MenuShopsAdapter(Context mContext, List<MenuShop.DataBean.StoreListBean> storeListBeanList) {
        this.mContext = mContext;
        this.storeListBeanList = storeListBeanList;
    }

    public MenuShopsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_menu_shop, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(storeListBeanList.get(position).getLogo()).into(((ViewHolder) holder).shop_img);
            if (storeListBeanList.get(position).getHotProductList().size() > 0) {
                if (storeListBeanList.get(position).getHotProductList().get(0).getProductImages().getThumbnail() != null
                        || storeListBeanList.get(position).getHotProductList().get(1).getProductImages().getThumbnail() != null) {
                    Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product)).load(storeListBeanList.get(position).getHotProductList().get(0).getProductImages().getThumbnail()).into(((ViewHolder) holder).goods_1);
                    Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product)).load(storeListBeanList.get(position).getHotProductList().get(1).getProductImages().getThumbnail()).into(((ViewHolder) holder).goods_2);

                    ((ViewHolder) holder).left.setTag(position);
                    ((ViewHolder) holder).right.setTag(position);
                    ((ViewHolder) holder).left.setOnClickListener(this);
                    ((ViewHolder) holder).right.setOnClickListener(this);
                }
                MenuShop.DataBean.StoreListBean bean = storeListBeanList.get(position);
                List<MenuShop.DataBean.StoreListBean.HotProductListBean> productListBeans = bean.getHotProductList();
                MenuShop.DataBean.StoreListBean.HotProductListBean leftBean = productListBeans.get(0);
                ((ViewHolder) holder).leftPriceView.setPrice(leftBean.getMembershipPrice(), leftBean.getPrice(), leftBean.isSurportMsp(), leftBean.getIsDiscount(), leftBean.getModel(), leftBean.getSourceMembershipPrice(), leftBean.getSourcePrice());
                MenuShop.DataBean.StoreListBean.HotProductListBean rightBean = productListBeans.get(1);
                ((ViewHolder) holder).rightPriceView.setPrice(rightBean.getMembershipPrice(), rightBean.getPrice(), rightBean.isSurportMsp(), rightBean.getIsDiscount(), rightBean.getModel(), rightBean.getSourceMembershipPrice(), rightBean.getSourcePrice());
            }
        } catch (Exception e) {

        }
        try {
            ((ViewHolder) holder).shop_name.setText(storeListBeanList.get(position).getStore_name());
            ((ViewHolder) holder).shop_introduce.setText(storeListBeanList.get(position).getStoreRankName());

            // TODO: 2018/7/13 少字段  简短介绍字段  type 总共多少类型 未明确
            ((ViewHolder) holder).goods_text.setText(Html.fromHtml(mContext.getString(R.string.goods_number, storeListBeanList.get(position).getProductNumber() + "")));
            ((ViewHolder) holder).sale_text.setText(Html.fromHtml(mContext.getString(R.string.sale_number, storeListBeanList.get(position).getProductSales() + "")));

            if (storeListBeanList.get(position).getType() == 1) {
                ((ViewHolder) holder).lable_one.setVisibility(View.VISIBLE);
            } else {
                ((ViewHolder) holder).lable_one.setVisibility(View.GONE);
            }

            if (storeListBeanList.get(position).isHasQualification()) {
                ((ViewHolder) holder).lable_two.setVisibility(View.VISIBLE);
            } else {
                ((ViewHolder) holder).lable_two.setVisibility(View.GONE);
            }

            ((ViewHolder) holder).lable_two.setTag(position);
            ((ViewHolder) holder).lable_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MenuShop.DataBean.StoreListBean storeListBean = storeListBeanList.get((int) view.getTag());

                    Intent intent = new Intent(mContext, NewWebViewActivity.class);
                    intent.putExtra("store_id", storeListBean.getStore_id() + "");
                    mContext.startActivity(intent);
                }
            });


        } catch (Exception e) {

        }

//        ((ViewHolder) holder).goods_text.setText(Html.fromHtml(mContext.getString(R.string.sale_number, 100+ "")));
//        ((ViewHolder) holder).sale_text.setText(Html.fromHtml(mContext.getString(R.string.sale_number, 200 + "")));

    }

    @Override
    public int getItemCount() {
        // TODO: 2018/7/13 调用接口数据
        return storeListBeanList.size();
//        return 10;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, NewProductDetailActivity.class);
        switch (v.getId()) {
            case R.id.left:
                intent.putExtra("productId", "" + storeListBeanList.get(((int) v.getTag())).getHotProductList().get(0).getProduct_id());
                break;
            case R.id.right:
                intent.putExtra("productId", "" + storeListBeanList.get(((int) v.getTag())).getHotProductList().get(1).getProduct_id());
                break;
            default:
                intent.putExtra("productId", "0");
                break;
        }

        intent.putExtra("sku_id", "");
        mContext.startActivity(intent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shop_img)
        ImageView shop_img;

        @BindView(R.id.shop_name)
        TextView shop_name;

        @BindView(R.id.lable_layout)
        LinearLayout lable_layout;

        @BindView(R.id.shop_introduce)
        TextView shop_introduce;

        @BindView(R.id.goods_text)
        TextView goods_text;

        @BindView(R.id.sale_text)
        TextView sale_text;

        @BindView(R.id.in_shop)
        HighlightButton in_shop;

        @BindView(R.id.goods_1)
        ImageView goods_1;

        @BindView(R.id.goods_2)
        ImageView goods_2;

        @BindView(R.id.price_left)
        PriceView leftPriceView;

        @BindView(R.id.price_right)
        PriceView rightPriceView;

//        @BindView(R.id.price_one)
//        TextView price_one;
//
//        @BindView(R.id.price_one_bt)
//        TextView price_one_bt;
//
//
//        @BindView(R.id.price_two)
//        TextView price_two;
//
//        @BindView(R.id.price_two_bt)
//        TextView price_two_bt;

        @BindView(R.id.lable_one)
        TextView lable_one;

        @BindView(R.id.lable_two)
        TextView lable_two;

        @BindView(R.id.left)
        LinearLayout left;

        @BindView(R.id.right)
        LinearLayout right;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            in_shop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("store_id", storeListBeanList.get(getLayoutPosition()).getStore_id() + "");
                    StarActivityUtil.starActivity((Activity) mContext, ShopActivity.class, bundle);
                }
            });
        }
    }
}

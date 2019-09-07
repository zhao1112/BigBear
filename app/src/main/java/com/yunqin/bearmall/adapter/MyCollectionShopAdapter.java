package com.yunqin.bearmall.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newversions.NewWebViewActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.CollectionShop;
import com.yunqin.bearmall.ui.activity.ShopActivity;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;
import com.yunqin.bearmall.widget.PriceView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author AYWang
 * @create 2018/7/21
 * @Describe
 */
public class MyCollectionShopAdapter extends BaseAdapter {

    private Context mContext;
    private List<CollectionShop.DataBean.StoreFavoriteListBean> listBean;

    public MyCollectionShopAdapter(Context mCOntext) {
        this.mContext = mCOntext;
        listBean = new ArrayList<>();
    }

    public void setData(List<CollectionShop.DataBean.StoreFavoriteListBean> listBean) {
        this.listBean.clear();
        this.listBean.addAll(listBean);
        notifyDataSetChanged();

    }

    public List<CollectionShop.DataBean.StoreFavoriteListBean> listBeanGet() {
        return listBean;
    }

    public void addData(List<CollectionShop.DataBean.StoreFavoriteListBean> listBean) {
        this.listBean.addAll(listBean);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        this.listBean.remove(index);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return listBean.size();
    }

    @Override
    public Object getItem(int position) {
        return listBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        try {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_menu_shop, null);
                viewHolder = new ViewHolder(convertView, position);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (listBean.size() > 0) {
                Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(listBean.get(position).getLogo()).into(viewHolder.shop_img);
                if (listBean.get(position).getHotProductList().size() > 0) {
                    if (listBean.get(position).getHotProductList().get(0).getProductImages().getThumbnail() != null) {
                        Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(listBean.get(position).getHotProductList().get(0).getProductImages().getThumbnail()).into(viewHolder.goods_1);
                    }

                    String memberPrice = listBean.get(position).getHotProductList().get(0).getMembershipPrice();
                    String price = listBean.get(position).getHotProductList().get(0).getPrice();
                    boolean surportMsp = listBean.get(position).getHotProductList().get(0).isSurportMsp();
                    int isDiscount = listBean.get(position).getHotProductList().get(0).getIsDiscount();
                    int model = listBean.get(position).getHotProductList().get(0).getModel();
                    String sourceMemberPrice = listBean.get(position).getHotProductList().get(0).getSourceMembershipPrice();
                    String sourceprice = listBean.get(position).getHotProductList().get(0).getSourcePrice();

                    viewHolder.price_left.setPrice(memberPrice,price,surportMsp,isDiscount,model,sourceMemberPrice,sourceprice);

                }

                if (listBean.get(position).getHotProductList().size() > 1) {
                    if (listBean.get(position).getHotProductList().get(1).getProductImages().getThumbnail() != null) {
                        Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_small)).load(listBean.get(position).getHotProductList().get(1).getProductImages().getThumbnail()).into(viewHolder.goods_2);
                    }
                    String memberPrice = listBean.get(position).getHotProductList().get(1).getMembershipPrice();
                    String price = listBean.get(position).getHotProductList().get(1).getPrice();
                    boolean surportMsp = listBean.get(position).getHotProductList().get(1).isSurportMsp();
                    int isDiscount = listBean.get(position).getHotProductList().get(1).getIsDiscount();
                    int model = listBean.get(position).getHotProductList().get(1).getModel();
                    String sourceMemberPrice = listBean.get(position).getHotProductList().get(1).getSourceMembershipPrice();
                    String sourceprice = listBean.get(position).getHotProductList().get(1).getSourcePrice();

                    viewHolder.price_right.setPrice(memberPrice,price,surportMsp,isDiscount,model,sourceMemberPrice,sourceprice);

                }

                viewHolder.shop_name.setText(listBean.get(position).getStore_name());
                viewHolder.shop_introduce.setText(listBean.get(position).getStoreRankName());

                // TODO: 2018/7/13 少字段  简短介绍字段  type 总共多少类型 未明确
                viewHolder.goods_text.setText(Html.fromHtml(mContext.getString(R.string.goods_number, listBean.get(position).getProductNumber() + "")));
                viewHolder.sale_text.setText(Html.fromHtml(mContext.getString(R.string.sale_number, listBean.get(position).getProductSales() + "")));

                if (listBean.get(position).getType() == 1) {
                    viewHolder.lable_one.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.lable_one.setVisibility(View.GONE);
                }


                if (listBean.get(position).isHasQualification()) {
                    viewHolder.lable_two.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.lable_two.setVisibility(View.GONE);
                }

                viewHolder.lable_two.setTag(position);
                viewHolder.lable_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, NewWebViewActivity.class);
                        intent.putExtra("store_id", listBean.get((int) view.getTag()).getStore_id() + "");
                        mContext.startActivity(intent);
                    }
                });


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.shop_img)
        ImageView shop_img;

        @BindView(R.id.shop_name)
        TextView shop_name;


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
        PriceView price_left;
        @BindView(R.id.price_right)
        PriceView price_right;


        @BindView(R.id.lable_one)
        TextView lable_one;
        @BindView(R.id.lable_two)
        TextView lable_two;

        public ViewHolder(View itemView, int position) {
            ButterKnife.bind(this, itemView);
            in_shop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("store_id", listBean.get(position).getStore_id() + "");
                    bundle.putInt("collection_index", position);
                    StarActivityUtil.starActivity((Activity) mContext, ShopActivity.class, bundle);
                }
            });
        }
    }
}

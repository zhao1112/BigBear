package com.yunqin.bearmall.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.BargainProductListBean;
import com.yunqin.bearmall.ui.activity.BargainFreeDetailActivity;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.dialog.KeepBargainDialog;
import com.yunqin.bearmall.ui.dialog.SpecificationItemsBargainDialog;

import java.util.List;

public class BargainFreeShopAdapter extends BaseAdapter {


    private SpecificationItemsBargainDialog specificationItemsBargainDialog;

    private Context mContext;
    private List<BargainProductListBean.BargainProductList> bargainProductLists;
    private LayoutInflater mInflater;

    public BargainFreeShopAdapter(Context context, List<BargainProductListBean.BargainProductList> bargainProductLists) {
        this.mContext = context;
        this.bargainProductLists = bargainProductLists;
        mInflater = LayoutInflater.from(mContext);

    }

    public int getListSize() {
        return bargainProductLists.size();
    }

    public void setData(List<BargainProductListBean.BargainProductList> bargainProductLists) {
        this.bargainProductLists = bargainProductLists;
        notifyDataSetChanged();
    }

    public void addData(List<BargainProductListBean.BargainProductList> bargainProductLists) {
        this.bargainProductLists.addAll(bargainProductLists);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return bargainProductLists.size();
    }

    @Override
    public Object getItem(int position) {
        return bargainProductLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BargainProductListBean.BargainProductList bargainProductList = bargainProductLists.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_bargain_free_product, null);
            viewHolder.productImg = (ImageView) convertView.findViewById(R.id.bargain_free_product_img);
            viewHolder.productName = (TextView) convertView.findViewById(R.id.bargain_free_product_name);
            viewHolder.productPrice = (TextView) convertView.findViewById(R.id.bargain_free_product_price);
            viewHolder.productCount = (TextView) convertView.findViewById(R.id.bargain_free_product_count);
            viewHolder.initiateFree =  convertView.findViewById(R.id.bargain_free_product_initiate);
            viewHolder.goodsSellOutView = convertView.findViewById(R.id.goods_sell_out_view);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try {
            if (bargainProductList.getProductImages() != null && bargainProductList.getProductImages().getThumbnail() != null) {
                Glide.with(mContext).load(bargainProductList.getProductImages().getThumbnail()).into(viewHolder.productImg);
            }
        } catch (Exception e) {
        }

        try {
            viewHolder.productName.setText(bargainProductList.getProductName());

//            viewHolder.productPrice.setText("¥" + bargainProductList.getPartPrice() + "+BC" + bargainProductList.getPartBtAmount());
            // TODO 砍价改为直接购买  价格是直接购买价
            viewHolder.productPrice.setText("¥" + bargainProductList.getPrice());


            // TODO qqq

            viewHolder.productCount.setText("已有" + bargainProductList.getPersonalCount() + "人参加");

            if (bargainProductList.getRestCount() == 0) {
                viewHolder.goodsSellOutView.setVisibility(View.VISIBLE);
            } else {
                viewHolder.goodsSellOutView.setVisibility(View.GONE);
                //todo 待修改区域
            }

            //发起砍价,显示对话框
            viewHolder.initiateFree.setOnClickListener(v -> {
                if (BearMallAplication.getInstance().getUser() != null) {

                    if (bargainProductList.getRestCount() == 0) {
                        Toast.makeText(mContext, "今日已售罄，明天再来", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (bargainProductList.getIsOngoing() == 0) {
                        if (onBargainProductListener != null) {
                            onBargainProductListener.onBargainProduct(bargainProductList.getBargainProduct_id());//把BargainProduct_id返回给activity
                        }
                        specificationItemsBargainDialog = new SpecificationItemsBargainDialog(mContext, bargainProductList, position,
                                bargainProductList.getStore(), bargainProductList.getProduct_id(), 0, bargainProductList.getSkuList());

                        specificationItemsBargainDialog.setOnBargainSpecificationItemsListener(new SpecificationItemsBargainDialog.OnBargainSpecificationItemsListener() {
                            @Override
                            public void onBargainSpecificationItems(long sku_id) {
                                if (onBargainSpecificationItemsListener != null) {
                                    onBargainSpecificationItemsListener.onBargainSpecificationItems(sku_id);
                                }
                            }
                        });

                        specificationItemsBargainDialog.showDialog();
                    } else {
                        //如果商品已经砍价了，则显示继续砍价弹框
                        KeepBargainDialog keepBargainDialog = new KeepBargainDialog(mContext, bargainProductList);
                        keepBargainDialog.showDialog();
                    }
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                }

            });
        } catch (Exception e) {
        }


        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, BargainFreeDetailActivity.class);
            intent.putExtra(BargainFreeDetailActivity.BARGAIN_PRODUCT_ID, bargainProductList.getBargainProduct_id());
            intent.putExtra(BargainFreeDetailActivity.BARGAIN_IS_ONGOING, false);
            ((Activity) mContext).startActivityForResult(intent, 1);
        });

        return convertView;
    }

    class ViewHolder {
        private ImageView productImg;
        private TextView productName, productPrice, productCount;
        private TextView initiateFree;
        private RelativeLayout goodsSellOutView;// 商品抢光了..
    }

    public OnBargainProductListener onBargainProductListener;

    public interface OnBargainProductListener {
        void onBargainProduct(long bargainProduct_id);
    }

    public void setOnBargainProductListener(OnBargainProductListener onBargainProductListener) {
        this.onBargainProductListener = onBargainProductListener;
    }

    public OnBargainSpecificationItemsListener onBargainSpecificationItemsListener;

    public interface OnBargainSpecificationItemsListener {
        void onBargainSpecificationItems(long sku_id);
    }

    public void setOnBargainSpecificationItemsListener(OnBargainSpecificationItemsListener onBargainSpecificationItemsListener) {
        this.onBargainSpecificationItemsListener = onBargainSpecificationItemsListener;
    }
}

package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.ProductDetail;

import java.util.ArrayList;
import java.util.List;

public class ProductImgViewPagerAdapter extends PagerAdapter{

    private Context mContext;
    private List<ProductDetail.ProductImages> mProductImagesList;
    private List<ImageView> mImageViewList;
    private LayoutInflater mInflater;
    public ProductImgViewPagerAdapter(Context mContext,List<ProductDetail.ProductImages> mProductImagesList) {
        this.mContext = mContext;
        this.mProductImagesList = mProductImagesList;
        mImageViewList = new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return mProductImagesList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_details_top)).load(mProductImagesList.get(position).getSource()).into(imageView);
        mImageViewList.add(imageView);
        ((ViewPager)container).addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager)container).removeView(mImageViewList.get(position));
    }
}

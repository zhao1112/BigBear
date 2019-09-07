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
import com.yunqin.bearmall.bean.BannerBean;
import com.yunqin.bearmall.bean.ProductDetail;

import java.util.ArrayList;
import java.util.List;

public class BannerViewPagerAdapter extends PagerAdapter{

    private Context mContext;
    List<BannerBean.DataBean.AdMobileListBean> adMobileList;
    private List<ImageView> mImageViewList;
    private LayoutInflater mInflater;
    public BannerViewPagerAdapter(Context mContext, List<BannerBean.DataBean.AdMobileListBean> adMobileList) {
        this.mContext = mContext;
        this.adMobileList = adMobileList;
        mImageViewList = new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return adMobileList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_banner)).load(adMobileList.get(position).getImg()).into(imageView);
        mImageViewList.add(imageView);
        ((ViewPager)container).addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager)container).removeView(mImageViewList.get(position));
    }
}

package com.newversions.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.video.CustomVideo;

import java.util.ArrayList;
import java.util.List;

public class NewVersionProductImgViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<NewProductBean.DataBean.ProductBean.ProductImagesBean> mProductImagesList;
    private List<View> mImageViewList;
    private LayoutInflater mInflater;

    private boolean isHasVideo;
    private String videoUrl;
    private String videoBgUrl;


    public NewVersionProductImgViewPagerAdapter(Context mContext, List<NewProductBean.DataBean.ProductBean.ProductImagesBean> mProductImagesList) {
        this(mContext, false, "", "", mProductImagesList);
    }


    public NewVersionProductImgViewPagerAdapter(Context mContext,
                                                boolean isHasVideo,
                                                String videoUrl,
                                                String videoBgUrl,
                                                List<NewProductBean.DataBean.ProductBean.ProductImagesBean> mProductImagesList) {
        this.mContext = mContext;
        this.isHasVideo = isHasVideo;
        this.videoUrl = videoUrl;
        this.videoBgUrl = videoBgUrl;

        this.mProductImagesList = mProductImagesList;

        mImageViewList = new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        if (isHasVideo) {
            return mProductImagesList.size() + 1;
        }
        return mProductImagesList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        int index = 0;
        if(isHasVideo){
            index++;
        }
        if (isHasVideo && position == 0) {
            CustomVideo customVideo = new CustomVideo(mContext);
            customVideo.setUp(videoUrl, "", CustomVideo.SCREEN_WINDOW_NORMAL);
            Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_details_top)).load(videoBgUrl).into(customVideo.thumbImageView);
            mImageViewList.add(customVideo);
            container.addView(customVideo);
            return customVideo;
        }

        ImageView imageView = new ImageView(mContext);
        Glide.with(mContext).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_product_details_top))
                .load(mProductImagesList.get(position - index).getSource())
                .into(imageView);
        mImageViewList.add(imageView);
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        if (position == 0) {
//            Jzvd.releaseAllVideos();
//        }
        container.removeView(mImageViewList.get(position));
    }
}

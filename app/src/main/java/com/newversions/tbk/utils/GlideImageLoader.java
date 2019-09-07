package com.newversions.tbk.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;


public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.default_banner)).load(path).into(imageView);
    }
}

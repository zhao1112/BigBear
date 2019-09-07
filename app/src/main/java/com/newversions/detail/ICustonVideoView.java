package com.newversions.detail;

import android.content.Context;
import android.util.AttributeSet;

import com.yunqin.bearmall.R;

import cn.jzvd.JzvdStd;

/**
 * Create By Master
 * On 2019/1/23 10:35
 */
public class ICustonVideoView extends JzvdStd {

    public ICustonVideoView(Context context) {
        super(context);
    }

    public ICustonVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void init(Context context) {
        super.init(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.product_detail_video_layout;
    }
}

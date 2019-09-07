package com.newversions.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.yunqin.bearmall.util.RudenessScreenHelper;

/**
 * @author AYWang
 * @create 2019/1/21
 * @Describe
 */
public class MyWebView extends WebView {
    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOverScrollMode(int mode) {
        super.setOverScrollMode(mode);
        RudenessScreenHelper.resetDensity(getContext(), 750);
    }


//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }


}

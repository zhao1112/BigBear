package com.yunqin.bearmall.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.widget
 * @DATE 2020/3/20
 */
public class HomeScrollView extends ScrollView {

    private OnScrollistener onScrollistener;

    public OnScrollistener getOnScrollistener() {
        return onScrollistener;
    }

    public void setOnScrollistener(OnScrollistener onScrollistener) {
        this.onScrollistener = onScrollistener;
    }

    public HomeScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HomeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeScrollView(Context context) {
        super(context);
    }

    public interface OnScrollistener {
        void onScroll(int startY, int endY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        onScrollistener.onScroll(oldt, t);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}

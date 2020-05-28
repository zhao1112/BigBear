package com.bbcoupon.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.widget
 * @DATE 2020/5/27
 */
public class MakeScrollView extends ScrollView {

    private OnScrollistener onScrollistener;

    public OnScrollistener getOnScrollistener() {
        return onScrollistener;
    }

    public void setOnScrollistener(OnScrollistener onScrollistener) {
        this.onScrollistener = onScrollistener;
    }

    public MakeScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MakeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MakeScrollView(Context context) {
        super(context);
    }

    public interface OnScrollistener {
        void onScroll(int startY, int endY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        try {
            onScrollistener.onScroll(oldt, t);
            super.onScrollChanged(l, t, oldl, oldt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

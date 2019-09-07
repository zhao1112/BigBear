package com.yunqin.bearmall.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class UpDownAnimView extends ViewGroup {

    private boolean isScrollToTop;

    public UpDownAnimView(Context context) {
        this(context, null);
    }

    public UpDownAnimView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UpDownAnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    private void init() {

    }

    public boolean packPress() {

        return isScrollToTop;
    }

    public void topToBottomAnim() {

        if (getChildCount() < 2) {
            return;
        }
        isScrollToTop = false;
        View chid = getChildAt(0);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(chid, "translationY", 0);
        objectAnimator1.setDuration(500);
        objectAnimator1.start();

        View chid1 = getChildAt(1);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(chid1, "translationY", 0);
        objectAnimator.setDuration(500);
        objectAnimator.start();

    }

    public void bottomToTopAnim() {

        if (getChildCount() < 2) {
            return;
        }
        isScrollToTop = true;
        View chid = getChildAt(0);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(chid, "translationY", -chid.getHeight());
        objectAnimator1.setDuration(500);
        objectAnimator1.start();

        View chid1 = getChildAt(1);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(chid1, "translationY", -chid1.getHeight());
        objectAnimator.setDuration(500);
        objectAnimator.start();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (getChildCount() > 0) {
            heightSize = getChildAt(0).getMeasuredHeight() * getChildCount();
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int bottom = child.getMeasuredHeight() + top;
            child.layout(l, top, r, bottom);
            top += bottom;
        }
    }

}

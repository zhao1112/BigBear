package com.yunqin.bearmall.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.yunqin.bearmall.inter.ScrollViewListener;

public class DeficitScrollView extends ScrollView{
    private ScrollViewListener scrollViewListener = null;

    public DeficitScrollView(Context context) {
        super(context);
    }

    public DeficitScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DeficitScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void computeScroll() {
        // TODO Auto-generated method stub
        super.computeScroll();
    }

    @Override
    public void fling(int velocityY) {

        super.fling(velocityY);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        // TODO Auto-generated method stub
        //super.onScrollChanged(x,y,oldx,oldy);

        if(scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }


}

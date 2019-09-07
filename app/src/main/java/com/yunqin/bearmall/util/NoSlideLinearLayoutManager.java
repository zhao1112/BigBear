package com.yunqin.bearmall.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * @author Master
 */
public class NoSlideLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = false;

    public NoSlideLinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
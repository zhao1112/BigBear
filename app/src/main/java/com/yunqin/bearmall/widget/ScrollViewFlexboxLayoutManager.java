package com.yunqin.bearmall.widget;

import com.google.android.flexbox.FlexboxLayoutManager;

public class ScrollViewFlexboxLayoutManager extends FlexboxLayoutManager{

    public ScrollViewFlexboxLayoutManager() {
        super();
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}

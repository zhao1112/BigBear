package com.yunqin.bearmall.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Master
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;

    public GridSpacingItemDecoration(int spanCount, int spacing) {
        this.spanCount = spanCount;
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.left = spacing;
        outRect.bottom = spacing;

        if (parent.getChildLayoutPosition(view) % spanCount == 0) {
            outRect.left = 30;
        }

        if (parent.getChildLayoutPosition(view) % spanCount == spanCount - 1) {
            outRect.right = 30;
        }
    }
}
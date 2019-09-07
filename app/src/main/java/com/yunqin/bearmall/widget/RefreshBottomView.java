package com.yunqin.bearmall.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.lcodecore.tkrefreshlayout.IBottomView;
import com.yunqin.bearmall.R;

/**
 * @author Master
 */
public class RefreshBottomView extends FrameLayout implements IBottomView {


    public RefreshBottomView(Context context) {
        this(context, null);
    }


    public RefreshBottomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        View rootView = View.inflate(getContext(), R.layout.refresh_bottm_view, null);
        addView(rootView);
    }


    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxBottomHeight, float bottomHeight) {

    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {

    }

    @Override
    public void reset() {

    }

}

package com.yunqin.bearmall.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;

/**
 * @author Master
 */
public class RefreshHeadView extends FrameLayout implements IHeaderView {


    public RefreshHeadView(Context context) {
        this(context,null);
    }


    public RefreshHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        View rootView = View.inflate(getContext(), R.layout.refresh_head, null);
        addView(rootView);
//        View rootView = View.inflate(getContext(), R.layout.view_sinaheader, null);
//        refreshArrow = (ImageView) rootView.findViewById(R.id.iv_arrow);
//        refreshTextView = (TextView) rootView.findViewById(R.id.tv);
//        loadingView = (ImageView) rootView.findViewById(R.id.iv_loading);
    }


    @Override
    public View getView() {
        return this;
    }


    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onFinish(OnAnimEndListener animEndListener) {
        animEndListener.onAnimEnd();
    }

    @Override
    public void reset() {

    }

}

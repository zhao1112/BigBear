package com.bbcoupon.ui.activity;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/4/22
 */
public class MeetingplaceActivity extends BaseActivity {


    @BindView(R.id.me_image)
    ImageView mMeImage;
    @BindView(R.id.me_share)
    TextView mMeShare;

    @Override
    public int layoutId() {
        return R.layout.activity_messting;
    }

    @Override
    public void init() {


        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(60);
        drawable.setColor(getResources().getColor(R.color.jutwo));
        mMeShare.setBackground(drawable);

    }

    @OnClick({R.id.toolbar_back, R.id.me_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.me_share:
                break;
        }
    }
}

package com.bbcoupon.ui.activity;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;

import butterknife.OnClick;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/5/8
 */
public class SharingRulesActivity extends BaseActivity {

    @Override
    public int layoutId() {
        return R.layout.activity_sharingrules;
    }

    @Override
    public void init() {

    }

    @OnClick(R.id.toolbar_back)
    public void onViewClicked() {
        finish();
    }
}

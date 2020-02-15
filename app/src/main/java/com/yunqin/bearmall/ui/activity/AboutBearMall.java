package com.yunqin.bearmall.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.inter.CheckVersionCallBack;
import com.yunqin.bearmall.update.CheckForUpdateHelper;
import com.yunqin.bearmall.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2018/8/21
 * @Describe
 */
public class AboutBearMall extends BaseActivity implements CheckVersionCallBack{
    @BindView(R.id.toolbar_back)
    ImageView toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.chenck_version)
    TextView chenckVersion;

    @Override
    public int layoutId() {
        return R.layout.activity_about_bearmall;
    }

    @Override
    public void init() {
        toolbarTitle.setText("关于大熊酷朋");
        version.setText(CommonUtils.getVersionName(this));
    }

    @OnClick({R.id.toolbar_back,R.id.chenck_version})
    void click(View view){
        switch (view.getId()){
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.chenck_version:
                showLoading();
                new CheckForUpdateHelper(this).checkForUpdate(this,1);
                break;
        }
    }

    @Override
    public void isTheNewVersion() {
        hiddenLoadingView();
        showToast("已是最新版本");
    }

    @Override
    public void success() {
        hiddenLoadingView();
    }

    @Override
    public void onerror() {
        hiddenLoadingView();
    }
}

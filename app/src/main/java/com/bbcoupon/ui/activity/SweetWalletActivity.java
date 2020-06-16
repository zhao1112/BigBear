package com.bbcoupon.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.bbcoupon.ui.adapter.SweetWalletTabAdapter;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/6/16
 */
public class SweetWalletActivity extends BaseActivity {

    @BindView(R.id.sweet_num)
    TextView mSweetNum;
    @BindView(R.id.sweet_tab)
    XTabLayout mSweetTab;
    @BindView(R.id.sweet_pager)
    ViewPager mSweetPager;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.sweet_title)
    TextView mSweetTitle;

    private String[] tabTitle = {"我的糖果", "粉丝推荐糖果"};
    private SweetWalletTabAdapter sweetTabAdapter;

    @Override
    public int layoutId() {
        return R.layout.activity_sweet;
    }

    @Override
    public void init() {

        mTitle.setText("糖果钱包");
        mSweetTitle.setText("糖果余额");

        mSweetTab.addTab(mSweetTab.newTab().setText(tabTitle[0]));
        mSweetTab.addTab(mSweetTab.newTab().setText(tabTitle[1]));

        sweetTabAdapter = new SweetWalletTabAdapter(getSupportFragmentManager(), tabTitle);
        mSweetPager.removeAllViews();
        mSweetPager.setAdapter(sweetTabAdapter);
        mSweetPager.setCurrentItem(0);
        mSweetPager.setOffscreenPageLimit(1);
        //初始化显示第一个页面
        mSweetPager.addOnPageChangeListener(new XTabLayout.TabLayoutOnPageChangeListener(mSweetTab));
        mSweetTab.addOnTabSelectedListener(new XTabLayout.ViewPagerOnTabSelectedListener(mSweetPager));

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

}

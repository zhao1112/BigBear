package com.yunqin.bearmall.ui.fragment;

import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.BackstangeTabAdapter;
import com.yunqin.bearmall.base.BaseFragment;

import butterknife.BindView;

public class BackstangeTabFragment extends BaseFragment {

    @BindView(R.id.bacjstange_XTablayou)
    XTabLayout mXTabLayout;
    @BindView(R.id.bacjstabge_viewpager)
    ViewPager mViewPager;

    private static final String[] tbTabs = new String[]{"全部", "已付款", "已结算", "已失效"};

    @Override
    public int layoutId() {
        return R.layout.fragment_backstange;
    }

    @Override
    public void init() {
        initTabLayout();
    }

    private void initTabLayout() {
        mXTabLayout.removeAllTabs();
        for (int i = 0; i < tbTabs.length; i++) {
            mXTabLayout.addTab(mXTabLayout.newTab().setText(tbTabs[i]));
        }

        BackstangeTabAdapter taoBaoOrderTabAdapter = new BackstangeTabAdapter(getActivity(), getChildFragmentManager());
        mViewPager.setAdapter(taoBaoOrderTabAdapter);
        mViewPager.setOffscreenPageLimit(1);
        mXTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(0);

    }
}

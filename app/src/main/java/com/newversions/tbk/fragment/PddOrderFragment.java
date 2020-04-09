package com.newversions.tbk.fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;

import com.androidkun.xtablayout.XTabLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.PddOrderTabAdapter;
import com.yunqin.bearmall.adapter.TaoBaoOrderTabAdapter;
import com.yunqin.bearmall.base.BaseFragment;

import butterknife.BindView;

public class PddOrderFragment extends BaseFragment {

    @BindView(R.id.x_table_layout)
    XTabLayout mXTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private static final String[] tbTabs = new String[]{"全部", "已付款", "已结算", "已失效"};

    @Override
    public int layoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public void init() {
        initTabLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("#dong", "onResume:3" );
    }

    private void initTabLayout() {
        mXTabLayout.removeAllTabs();

        for (int i = 0; i < tbTabs.length; i++) {
            mXTabLayout.addTab(mXTabLayout.newTab().setText(tbTabs[i]));
        }

        PddOrderTabAdapter taoBaoOrderTabAdapter = new PddOrderTabAdapter(getActivity(), getChildFragmentManager());
        mViewPager.setAdapter(taoBaoOrderTabAdapter);
        mViewPager.setOffscreenPageLimit(1);
        mXTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(0);

    }
}
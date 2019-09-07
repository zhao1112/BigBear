package com.newversions.tbk.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RadioGroup;

import com.androidkun.xtablayout.XTabLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.MineOrderTabAdapter;
import com.yunqin.bearmall.adapter.TaoBaoOrderTabAdapter;
import com.yunqin.bearmall.base.BaseFragment;

import butterknife.BindView;

public class MyOrderFragment extends BaseFragment {
    @BindView(R.id.x_table_layout)
    XTabLayout mXTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    private static final String[] mTabs = new String[]{"全部", "待付款", "待发货", "待收货", "待评价"};
    private int index;
    @Override
    public int layoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("#dong", "onResume: 1" );
    }

    @Override
    public void init() {


        index = getArguments().getInt("index", 0);

        initTabLayout();
    }
    private void initTabLayout() {
        mXTabLayout.removeAllTabs();

            for (int i = 0; i < mTabs.length; i++) {
                mXTabLayout.addTab(mXTabLayout.newTab().setText(mTabs[i]));
            }
            MineOrderTabAdapter mineOrderTabAdapter = new MineOrderTabAdapter(getActivity(), getFragmentManager());
            mViewPager.setAdapter(mineOrderTabAdapter);
            mViewPager.setOffscreenPageLimit(1);
            mXTabLayout.setupWithViewPager(mViewPager);
            mViewPager.setCurrentItem(index);

    }
}

package com.bbcoupon.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.xtablayout.XTabLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.ui.fragment.ZeroActivity.BusinessTableAdapter;

import butterknife.BindView;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.fragment
 * @DATE 2020/4/26
 */
public class ArticleFragment extends BaseFragment {

    @BindView(R.id.business_table)
    XTabLayout mBusinessTable;
    @BindView(R.id.business_viewpager)
    ViewPager mBusinessViewpager;

    @Override
    public int layoutId() {
        return R.layout.fragment_business;
    }

    @Override
    public void init() {

        mBusinessTable.addTab(mBusinessTable.newTab().setText("大熊爆款"));
        mBusinessTable.addTab(mBusinessTable.newTab().setText("宣传素材"));
        mBusinessTable.addTab(mBusinessTable.newTab().setText("商学院"));

        BusinessTableAdapter businessTableAdapter = new BusinessTableAdapter(getActivity(), getChildFragmentManager());

        mBusinessViewpager.setAdapter(businessTableAdapter);
        mBusinessViewpager.setCurrentItem(0);
        //初始化显示第一个页面
        mBusinessTable.setupWithViewPager(mBusinessViewpager);
        mBusinessTable.setTabsFromPagerAdapter(businessTableAdapter);

        mBusinessTable.getTabAt(1).select();
    }
}

package com.yunqin.bearmall.ui.fragment;

import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.PartenrTabAdapter;
import com.yunqin.bearmall.base.BaseFragment;

import butterknife.BindView;

public class PartnerFragmentFans extends BaseFragment {

    @BindView(R.id.partenr_fans_xtab)
    XTabLayout mXTabLayout;
    @BindView(R.id.partenr_fans_viewpage)
    ViewPager mViewPager;

    private static final String[] tbTabs = new String[]{"全部", "大团长", "超级会员"};

    @Override
    public int layoutId() {
        return R.layout.partenr_fragment_fans;
    }

    @Override
    public void init() {
            initTab();
    }

    private void initTab() {

            mXTabLayout.removeAllTabs();
            for (int i = 0; i < tbTabs.length; i++) {
                mXTabLayout.addTab(mXTabLayout.newTab().setText(tbTabs[i]));
            }

        PartenrTabAdapter partenrTabAdapter = new PartenrTabAdapter(getActivity(), getChildFragmentManager());
            mViewPager.setAdapter(partenrTabAdapter);
            mViewPager.setOffscreenPageLimit(1);
            mXTabLayout.setupWithViewPager(mViewPager);
            mViewPager.setCurrentItem(0);

    }
}

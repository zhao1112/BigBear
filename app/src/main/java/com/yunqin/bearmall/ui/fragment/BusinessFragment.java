package com.yunqin.bearmall.ui.fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;

import com.alibaba.wireless.security.open.middletier.fc.IFCActionCallback;
import com.androidkun.xtablayout.XTabLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.ui.activity.HomeActivity;
import com.yunqin.bearmall.ui.fragment.ZeroActivity.BusinessTableAdapter;
import com.yunqin.bearmall.util.SharedPreferencesHelper;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.fragment
 * @DATE 2020/3/30
 */
public class BusinessFragment extends BaseFragment {

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
//        mBusinessTable.addTab(mBusinessTable.newTab().setText("商学院"));

        BusinessTableAdapter businessTableAdapter = new BusinessTableAdapter(getActivity(), getChildFragmentManager());

        mBusinessViewpager.setAdapter(businessTableAdapter);
        mBusinessViewpager.setCurrentItem(0);
        //初始化显示第一个页面
        mBusinessTable.setupWithViewPager(mBusinessViewpager);
        mBusinessTable.setTabsFromPagerAdapter(businessTableAdapter);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            boolean isFirst = (boolean) SharedPreferencesHelper.get(getActivity(), "SELECT_TYPE", false);
            if (isFirst) {
                mBusinessTable.getTabAt(1).select();
            }else {
                mBusinessTable.getTabAt(0).select();
            }
            Log.e("onHiddenChanged", "onHiddenChanged: ");
        }else {
            SharedPreferencesHelper.put(getActivity(), "SELECT_TYPE", false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SharedPreferencesHelper.put(getActivity(), "SELECT_TYPE", false);
    }
}

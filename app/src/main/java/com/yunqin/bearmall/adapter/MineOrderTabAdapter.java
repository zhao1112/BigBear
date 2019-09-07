package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.yunqin.bearmall.ui.fragment.tab.AllFragment;
import com.yunqin.bearmall.ui.fragment.tab.AwaitEvaluateFragment;
import com.yunqin.bearmall.ui.fragment.tab.AwaitPayFragment;
import com.yunqin.bearmall.ui.fragment.tab.AwaitReceiveFragment;
import com.yunqin.bearmall.ui.fragment.tab.AwaitShipmentsFragment;

/**
 * @author Master
 */
public class MineOrderTabAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private static String[] mTabs;
    private static Fragment[] mFragments;


    public MineOrderTabAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mContext = context;
        mTabs = new String[]{"全部", "待付款", "待发货", "待收货", "待评价"};
        mFragments = new Fragment[]{null, null, null, null, null};
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments[position] != null) {
            return mFragments[position];
        }
        Bundle bundle = new Bundle();
        if (position == 0) {
            mFragments[position] = Fragment.instantiate(mContext, AllFragment.class.getName(), bundle);
        } else if (position == 1) {
            mFragments[position] = Fragment.instantiate(mContext, AwaitPayFragment.class.getName(), bundle);
        } else if (position == 2) {
            mFragments[position] = Fragment.instantiate(mContext, AwaitShipmentsFragment.class.getName(), bundle);
        } else if (position == 3) {
            mFragments[position] = Fragment.instantiate(mContext, AwaitReceiveFragment.class.getName(), bundle);
        } else if (position == 4) {
            mFragments[position] = Fragment.instantiate(mContext, AwaitEvaluateFragment.class.getName(), bundle);
        }
        return mFragments[position];
    }


    @Override
    public int getCount() {
        return mTabs.length;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs[position];
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}

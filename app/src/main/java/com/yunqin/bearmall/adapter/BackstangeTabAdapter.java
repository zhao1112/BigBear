package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yunqin.bearmall.ui.fragment.BackstangeFragment;

public class BackstangeTabAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private static String[] mTabs;

    public BackstangeTabAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
        mTabs = new String[]{"全部", "待返佣", "已到账", "已失效"};
    }

    @Override
    public Fragment getItem(int position) {
        BackstangeFragment backstangeFragment = new BackstangeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", mTabs[position]);
        backstangeFragment.setArguments(bundle);
        return backstangeFragment;
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
}

package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yunqin.bearmall.ui.fragment.FragmentFans;

public class PartenrTabAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private static String[] mTabs;

    public PartenrTabAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
        mTabs = new String[]{"全部", "大团长", "超级会员"};
    }

    @Override
    public Fragment getItem(int position) {
        FragmentFans fragmentFans = new FragmentFans();
        Bundle bundle = new Bundle();
        bundle.putString("title", mTabs[position]);
        fragmentFans.setArguments(bundle);
        return fragmentFans;
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

package com.yunqin.bearmall.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yunqin.bearmall.ui.fragment.FansFragment;
import com.yunqin.bearmall.ui.fragment.FansOneFragment;
import com.yunqin.bearmall.ui.fragment.FansTwoFragment;


/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.adapter
 * @DATE 2019/11/28
 */
public class FansAdapter extends FragmentPagerAdapter {

    private static String[] mTabs;

    public FansAdapter(FragmentManager fm) {
        super(fm);
        mTabs = new String[]{"全部", "直邀粉丝", "推荐粉丝"};
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FansFragment();
        }
        if (position == 1) {
            return new FansOneFragment();
        }
        if (position == 2) {
         return new FansTwoFragment();
        }
        return null;
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

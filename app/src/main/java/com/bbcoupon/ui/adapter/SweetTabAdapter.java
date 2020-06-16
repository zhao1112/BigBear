package com.bbcoupon.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bbcoupon.ui.fragment.FansSweetFragment;
import com.bbcoupon.ui.fragment.SweetFragment;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.adapter
 * @DATE 2020/6/16
 */
public class SweetTabAdapter extends FragmentStatePagerAdapter {

    private String[] tabList;

    public SweetTabAdapter(FragmentManager fm, String[] tabList) {
        super(fm);
        this.tabList = tabList;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new SweetFragment();
        } else {
            return new FansSweetFragment();
        }
    }

    @Override
    public int getCount() {
        return tabList.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabList[position];
    }
}

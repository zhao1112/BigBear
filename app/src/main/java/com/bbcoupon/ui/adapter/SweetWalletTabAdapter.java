package com.bbcoupon.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bbcoupon.ui.fragment.FansSweetFragment;
import com.bbcoupon.ui.fragment.FansWalletFragment;
import com.bbcoupon.ui.fragment.SweetFragment;
import com.bbcoupon.ui.fragment.WalletFragment;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.adapter
 * @DATE 2020/6/16
 */
public class SweetWalletTabAdapter extends FragmentStatePagerAdapter {

    private String[] tabList;

    public SweetWalletTabAdapter(FragmentManager fm, String[] tabList) {
        super(fm);
        this.tabList = tabList;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new WalletFragment();
        } else {
            return new FansWalletFragment();
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

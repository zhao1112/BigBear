package com.yunqin.bearmall.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yunqin.bearmall.ui.fragment.GuideListFragment;

import java.util.List;

public class GuidePagerAdapter extends FragmentPagerAdapter {

    private   String title[] = {"全部","视频","文章"};

    private List<GuideListFragment> fragments;

    public GuidePagerAdapter(FragmentManager fm,List<GuideListFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}

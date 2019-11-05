package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author Master
 */
public class ProductViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mListFragment;
    private List<String> mTitleList;
    private Context mContext;

    public ProductViewPagerAdapter(Context context, FragmentManager fragmentManager, List<Fragment> mListFragment, List<String> mTitleList) {
        super(fragmentManager);
        this.mContext = context;
        this.mListFragment = mListFragment;
        this.mTitleList = mTitleList;

    }


    @Override
    public Fragment getItem(int position) {
        return mListFragment.get(position);
    }

    @Override
    public int getCount() {
        return mListFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }

}

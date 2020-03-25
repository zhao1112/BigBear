package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.newversions.tbk.fragment.PddChildFragment;
import com.newversions.tbk.fragment.TaoBaoChildFragment;


/**
 * @author Master
 */
public class PddOrderTabAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private static String[] mTabs;


    public PddOrderTabAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mContext = context;
        mTabs = new String[]{"全部", "已付款", "已结算", "已失效"};

    }

    @Override
    public Fragment getItem(int position) {
        Log.d("TAGaa", "getItem: ___________" + position);
        PddChildFragment testFragment = new PddChildFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", mTabs[position]);
        testFragment.setArguments(bundle);
        return testFragment;
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

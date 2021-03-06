package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.newversions.tbk.fragment.TaoBaoChildFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Master
 */
public class TaoBaoOrderTabAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private static String[] mTabs;
    private List<TaoBaoChildFragment> mTaoBao = new ArrayList<>();

    public TaoBaoOrderTabAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mContext = context;
        mTabs = new String[]{"全部", "待返佣", "已到账", "已失效"};

    }

    @Override
    public Fragment getItem(int position) {
        TaoBaoChildFragment testFragment = new TaoBaoChildFragment();
        mTaoBao.add(testFragment);
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

    public void setOrder(String order) {
        if (mTaoBao != null && mTaoBao.size() > 0) {
            for (int i = 0; i < mTaoBao.size(); i++) {
                TaoBaoChildFragment taoBaoChildFragment = mTaoBao.get(i);
                taoBaoChildFragment.setOrder(order);
            }
        }
    }

}

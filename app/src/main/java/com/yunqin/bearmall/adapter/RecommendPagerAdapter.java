package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yunqin.bearmall.bean.Channel;
import com.yunqin.bearmall.ui.fragment.GuideFragment;

import java.util.List;

/**
 * Created by chenchen on 2018/7/28.
 */

public class RecommendPagerAdapter extends FragmentPagerAdapter {

    private List<Channel.DataBean> mChannellist;
    private Context mContext;

    public RecommendPagerAdapter(Context context, FragmentManager fragmentManager, List<Channel.DataBean> mArrays) {
        super(fragmentManager);
        this.mContext = context;
        this.mChannellist = mArrays;
    }

    @Override
    public Fragment getItem(int position) {
        return GuideFragment.guideFragmentWithID(mChannellist.get(position).getCategory_id()+"");
    }

    @Override
    public int getCount() {
        return mChannellist.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mChannellist.get(position).getName();
    }
}

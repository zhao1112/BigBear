package com.yunqin.bearmall.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yunqin.bearmall.bean.TreasureData;
import com.yunqin.bearmall.ui.fragment.SnatchContentFragment;

import java.util.Calendar;
import java.util.List;

/**
 * Created by chenchen on 2018/8/6.
 */

public class SweetSnatchAdapter extends FragmentPagerAdapter {

    private List<TreasureData.TreasureTag> tags;


    public SweetSnatchAdapter(FragmentManager fm, List<TreasureData.TreasureTag> tags) {
        super(fm);
        this.tags = tags;
    }

    @Override
    public Fragment getItem(int position) {

        TreasureData.TreasureTag tag = tags.get(position);

        return SnatchContentFragment.fragment(tag.getTag(),tag.isToday()?1:0);
    }

    @Override
    public int getCount() {
        return tags==null?0:tags.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return getTitle(position);
    }


    private String getTitle(int postition){

        return postition+"";
    }

}

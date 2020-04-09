package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yunqin.bearmall.ui.fragment.HotFragment;
import com.yunqin.bearmall.ui.fragment.JuhuasuanTimeFragment;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.adapter
 * @DATE 2020/4/8
 */
public class JuhuasuanAdapter extends FragmentStatePagerAdapter {

    private List<String> mChannellist;
    private Context mContext;
    private String type;

    public JuhuasuanAdapter(Context context, FragmentManager fragmentManager, List<String> mArrays, String type) {
        super(fragmentManager);
        this.mContext = context;
        this.mChannellist = mArrays;
        this.type = type;
    }

    @Override
    public Fragment getItem(int position) {
        return JuhuasuanTimeFragment.getInstance(position + "", type);
    }

    @Override
    public int getCount() {
        return mChannellist.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mChannellist.get(position);
    }
}

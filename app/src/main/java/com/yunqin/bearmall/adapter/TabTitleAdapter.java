package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.newversions.home.huiyuan.NewVersionHomeFragmentTrue;
import com.newversions.tbk.fragment.NewVersionTBKHomeFragment;
import com.newversions.tbk.fragment.ProductSumFragment;
import com.yunqin.bearmall.bean.Channel;
import com.yunqin.bearmall.ui.fragment.tab.FragmentHomeTagList;

import java.util.List;

/**
 * @author Master
 */
public class TabTitleAdapter extends FragmentStatePagerAdapter {

    private List<Channel.DataBean> mChannellist;
    private Context mContext;
    private boolean isMember = false;

    public TabTitleAdapter(Context context, FragmentManager fragmentManager, List<Channel.DataBean> mArrays, boolean isMember) {
        super(fragmentManager);
        this.mContext = context;
        this.mChannellist = mArrays;
        this.isMember = isMember;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
//            return new BearRecommendFragment();
            if(isMember){
                return new NewVersionHomeFragmentTrue();
            }
//            return new NewVersionHomeFragment();
            return new NewVersionTBKHomeFragment();
        }
//        Bundle bundle = new Bundle();
//        bundle.putString("category_id", mChannellist.get(position - 1).getCategory_id() + "");
//        bundle.putBoolean("isMember", isMember);
//        Fragment fragment = Fragment.instantiate(mContext, FragmentHomeTagList.class.getName(), bundle);
        return ProductSumFragment.getInstance(mChannellist.get(position - 1).getCategory_id()+"",2);


    }

    @Override
    public int getCount() {
        return mChannellist.size() + 1;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "大熊精选";
        }
        return mChannellist.get(position - 1).getName();
    }

}

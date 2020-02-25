package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.yunqin.bearmall.bean.ShopBean;
import com.yunqin.bearmall.ui.fragment.ShopActivityFragment;
import com.yunqin.bearmall.ui.fragment.ShopGoodsFragment;

import java.util.List;

/**
 * @author Master
 */
public class ShopTableAdapter extends FragmentStatePagerAdapter {

    private List<ShopBean.DataBean.TagListBean> lables;
    private Context mContext;
    private String store_id;
    private String[] title = {"本店推荐", "店铺活动"};

    public ShopTableAdapter(Context context, FragmentManager fragmentManager, String store_id) {
        super(fragmentManager);
        this.mContext = context;
        this.lables = lables;
        this.store_id = store_id;
    }

    Fragment fragment;

    @Override
    public Fragment getItem(int position) {
        Bundle bundle1 = new Bundle();
        bundle1.putString("store_id", store_id);
        if (position == 1) {
            bundle1.putString("tag_id", -1 + "");
            fragment = Fragment.instantiate(mContext, ShopActivityFragment.class.getName(), bundle1);
        } else {
            bundle1.putString("tag_id", "1");
            fragment = Fragment.instantiate(mContext, ShopGoodsFragment.class.getName(), bundle1);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

}

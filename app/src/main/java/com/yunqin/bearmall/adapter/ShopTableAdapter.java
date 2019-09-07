package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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

    public ShopTableAdapter(Context context, FragmentManager fragmentManager, List<ShopBean.DataBean.TagListBean> lables,String store_id) {
        super(fragmentManager);
        this.mContext = context;
        this.lables = lables;
        this.store_id = store_id;
    }

    Fragment fragment;
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("store_id",store_id);
        if(position == lables.size()){
            bundle.putString("tag_id", -1+"");
            fragment = Fragment.instantiate(mContext, ShopActivityFragment.class.getName(), bundle);
        }else {
            bundle.putString("tag_id",lables.get(position).getTag_id()+"");
            fragment = Fragment.instantiate(mContext, ShopGoodsFragment.class.getName(), bundle);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return lables.size()+1;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if(position == lables.size()){
            return "店铺活动";
        }
        return lables.get(position).getTag_name();
    }

}

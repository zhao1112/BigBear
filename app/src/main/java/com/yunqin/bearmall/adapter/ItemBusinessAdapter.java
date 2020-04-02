package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yunqin.bearmall.bean.BusinessTabBean;
import com.yunqin.bearmall.ui.fragment.HotFragment;
import com.yunqin.bearmall.ui.fragment.Item_BusinessItem_Fragment;
import com.yunqin.bearmall.ui.fragment.Item_Propaganda_Fragment;

import java.util.List;

/**
 * @author Master
 */
public class ItemBusinessAdapter extends FragmentStatePagerAdapter {

    private List<BusinessTabBean.DataBean> tabList;
    private Context context;
    private String type;

    public ItemBusinessAdapter(Context context, FragmentManager fragmentManager, List<BusinessTabBean.DataBean> tabList, String type) {
        super(fragmentManager);
        this.context = context;
        this.tabList = tabList;
        this.type = type;
    }

    @Override
    public Fragment getItem(int position) {
        if ("0".equals(type)) {
            return new Item_BusinessItem_Fragment().getInstance(tabList.get(position).getCategoryId() + "");
        }
        if ("1".equals(type)) {
            return new Item_Propaganda_Fragment().getInstance(tabList.get(position).getCategoryId() + "");
        }
        if ("2".equals(type)) {
            return new Item_BusinessItem_Fragment().getInstance(tabList.get(position).getCategoryId() + "");
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position).getCategoryName();
    }

}

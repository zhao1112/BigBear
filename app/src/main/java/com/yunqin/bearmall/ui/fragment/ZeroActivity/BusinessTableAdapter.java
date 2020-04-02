package com.yunqin.bearmall.ui.fragment.ZeroActivity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yunqin.bearmall.ui.fragment.Item_Business_Fragment;


/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.fragment.ZeroActivity
 * @DATE 2020/3/30
 */
public class BusinessTableAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private String[] tableTitle = {"大熊爆款", "宣传素材"};

    public BusinessTableAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return new Item_Business_Fragment().getInstance(position + "");
    }

    @Override
    public int getCount() {
        return tableTitle.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tableTitle[position];
    }
}

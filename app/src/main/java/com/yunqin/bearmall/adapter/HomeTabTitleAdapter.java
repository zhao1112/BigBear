package com.yunqin.bearmall.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.ImageView;

import com.newversions.tbk.fragment.NewVersionTBKHomeAdapter2;
import com.newversions.tbk.fragment.ProductSumFragment;
import com.yunqin.bearmall.bean.Channel;
import com.yunqin.bearmall.ui.fragment.TBKHomeFragmet_2;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.adapter
 * @DATE 2020/3/20
 */
public class HomeTabTitleAdapter extends FragmentStatePagerAdapter {
    private List<Channel.DataBean> mChannellist;
    private Context mContext;
    private ImageView imageView;

    public HomeTabTitleAdapter(Context context, FragmentManager fragmentManager, List<Channel.DataBean> mArrays, ImageView imageView) {
        super(fragmentManager);
        this.mContext = context;
        this.mChannellist = mArrays;
        this.imageView = imageView;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            TBKHomeFragmet_2 tbkHomeFragmet_2 = new TBKHomeFragmet_2();
            tbkHomeFragmet_2.setImage(imageView);
            return tbkHomeFragmet_2;
        }
        return ProductSumFragment.getInstance(mChannellist.get(position - 1).getCategory_id() + "", 2, "");
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

    public NewVersionTBKHomeAdapter2.OnItemColor mOnItemColor;

    public interface OnItemColor {
        void setColor(int color);
    }

    public void setOnItemColor(NewVersionTBKHomeAdapter2.OnItemColor mOnItemColor) {
        this.mOnItemColor = mOnItemColor;
    }
}

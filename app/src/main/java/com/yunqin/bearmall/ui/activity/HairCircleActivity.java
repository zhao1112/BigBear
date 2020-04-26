package com.yunqin.bearmall.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.bbcoupon.ui.fragment.ArticleFragment;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.ui.fragment.BusinessFragment;
import com.yunqin.bearmall.ui.fragment.SellweFragment;

public class HairCircleActivity extends BaseActivity {

    @Override
    public int layoutId() {
        return R.layout.activity_hair_circle;
    }

    @Override
    public void init() {

        ArticleFragment sellweFragment = new ArticleFragment();
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.tag_frame, sellweFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.show(sellweFragment);
        fragmentTransaction.commit();

    }
}

package com.bbcoupon.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.ui.fragment.BusinessFragment;
import com.yunqin.bearmall.ui.fragment.HomeFragment_2;
import com.yunqin.bearmall.ui.fragment.MakeMoneyFragment;
import com.yunqin.bearmall.ui.fragment.MineFragment;
import com.yunqin.bearmall.ui.fragment.ZeorExchangeFragment;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.StatuBarUtils;

/**
 * @author LWP
 * @PACKAGE starrysky.com.ui.fragment
 * @DATE 2019/9/26
 */
public class SwitchFragment2 {

    private HomeFragment_2 mHomeFragment;
    private BusinessFragment mClassifyFragment;
    private MakeMoneyFragment mCartFragment;
    private ZeorExchangeFragment mZeorExchangeFragment;
    private MineFragment mMineFragment;
    private FragmentManager mManager;

    public SwitchFragment2(FragmentManager manager) {
        this.mManager = manager;
    }

    public void hiderFragment(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mClassifyFragment != null) {
            transaction.hide(mClassifyFragment);
        }
        if (mCartFragment != null) {
            transaction.hide(mCartFragment);
        }
        if (mZeorExchangeFragment != null) {
            transaction.hide(mZeorExchangeFragment);
        }
        if (mMineFragment != null) {
            transaction.hide(mMineFragment);
        }
    }

    public void showFragment(int type, int content, Context context) {
        FragmentTransaction fragmentTransaction = mManager.beginTransaction();
        switch (type) {
            case 1:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment_2();
                    fragmentTransaction.add(content, mHomeFragment);
                }
                hiderFragment(fragmentTransaction);
                fragmentTransaction.show(mHomeFragment);
                fragmentTransaction.commitAllowingStateLoss();
                StatuBarUtils.setTranslucentStatus((Activity) context);
                break;
            case 2:
                if (mCartFragment == null) {
                    mCartFragment = new MakeMoneyFragment();
                    fragmentTransaction.add(content, mCartFragment);
                }
                hiderFragment(fragmentTransaction);
                fragmentTransaction.show(mCartFragment);
                fragmentTransaction.commitAllowingStateLoss();
                setStatusBarColor(R.color.white, true, context);
                break;
            case 3:
                if (mClassifyFragment == null) {
                    mClassifyFragment = new BusinessFragment();
                    fragmentTransaction.add(content, mClassifyFragment);
                }
                hiderFragment(fragmentTransaction);
                fragmentTransaction.show(mClassifyFragment);
                fragmentTransaction.commitAllowingStateLoss();
                setStatusBarColor(R.color.white, true, context);
                break;
            case 4:
                if (mZeorExchangeFragment == null) {
                    mZeorExchangeFragment = new ZeorExchangeFragment();
                    fragmentTransaction.add(content, mZeorExchangeFragment);
                }
                hiderFragment(fragmentTransaction);
                fragmentTransaction.show(mZeorExchangeFragment);
                fragmentTransaction.commitAllowingStateLoss();
                StatuBarUtils.setTranslucentStatus((Activity) context);
                //TODO[点击0元兑]
                ConstantScUtil.exchangeClick();
                break;
            case 5:
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    fragmentTransaction.add(content, mMineFragment);
                }
                hiderFragment(fragmentTransaction);
                fragmentTransaction.show(mMineFragment);
                fragmentTransaction.commitAllowingStateLoss();
                StatuBarUtils.setTranslucentStatus((Activity) context);
                break;
        }
    }

    public void setStatusBarColor(int color, boolean dark, Context context) {
        StatuBarUtils.setStatusBarDarkTheme((Activity) context, dark);
        StatuBarUtils.setStatusBarColor((Activity) context, context.getResources().getColor(color));
    }

}

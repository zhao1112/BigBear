package com.yunqin.bearmall.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.ui.fragment.GuideWithVideoFragment;
import com.yunqin.bearmall.ui.fragment.HomeFragment;
import com.yunqin.bearmall.ui.fragment.HomeFragment_2;
import com.yunqin.bearmall.ui.fragment.MakeMoneyFragment;
import com.yunqin.bearmall.ui.fragment.MineFragment;
import com.yunqin.bearmall.ui.fragment.MineNewFragment;
import com.yunqin.bearmall.ui.fragment.ZeorExchangeFragment;
import com.yunqin.bearmall.ui.fragment.ZeroGoodsFragment;

/**
 * @author Master
 */
public class SwitchFragment {

    private int LAST_POSITION = -1;

    @IntDef({FRAGMENT_TYPE.APP_HOME, FRAGMENT_TYPE.APP_RECOMMEND, FRAGMENT_TYPE.APP_INFORMATION, FRAGMENT_TYPE.APP_TROLLEY,
            FRAGMENT_TYPE.APP_MINE})
    public @interface FRAGMENT_TYPE {
        int APP_HOME = 0;
        int APP_INFORMATION = 1;
        int APP_RECOMMEND = 2;
        int APP_TROLLEY = 3;
        int APP_MINE = 4;

    }

    private HomeFragment_2 mHomeFragment;
    private GuideWithVideoFragment mRecommendFragment;
    private MakeMoneyFragment mMakeMoneyFragment;
    // TODO: 2019/7/15 0015 替换成0元兑
    private ZeroGoodsFragment mTrolleyFragment;
    private MineFragment mMineFragment;
    private FragmentManager manager;
    private Activity activity;
    private ZeorExchangeFragment zeorExchangeFragment;

    public SwitchFragment(FragmentManager manager, Activity activity) {
        this.manager = manager;
        this.activity = activity;
    }

    private void hiderFragment(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mRecommendFragment != null) {
            transaction.hide(mRecommendFragment);
        }
        if (zeorExchangeFragment != null) {
            transaction.hide(zeorExchangeFragment);
        }
        if (mMakeMoneyFragment != null) {
            transaction.hide(mMakeMoneyFragment);
        }
        if (mMineFragment != null) {
            transaction.hide(mMineFragment);
        }
    }

    public void chooseFragment(@FRAGMENT_TYPE int type) {
        if (LAST_POSITION == type) {
            return;
        }
        LAST_POSITION = type;
        FragmentTransaction transaction = manager.beginTransaction();
        switch (type) {
            case FRAGMENT_TYPE.APP_HOME:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment_2();
                    transaction.add(R.id.content, mHomeFragment);
                }
                hiderFragment(transaction);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.show(mHomeFragment);
                transaction.commit();
                StatuBarUtils.setTranslucentStatus(activity);
                break;
            case FRAGMENT_TYPE.APP_RECOMMEND:
                if (mRecommendFragment == null) {
                    mRecommendFragment = new GuideWithVideoFragment();
                    transaction.add(R.id.content, mRecommendFragment);
                }
                hiderFragment(transaction);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.show(mRecommendFragment);
                transaction.commit();
                setStatusBarColor(R.color.white, true);
                break;
            case FRAGMENT_TYPE.APP_INFORMATION:
                if (mMakeMoneyFragment == null) {
                    mMakeMoneyFragment = new MakeMoneyFragment();
                    transaction.add(R.id.content, mMakeMoneyFragment);
                }
                hiderFragment(transaction);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.show(mMakeMoneyFragment);
                transaction.commit();
                setStatusBarColor(R.color.white, true);
                break;
            case FRAGMENT_TYPE.APP_TROLLEY:
                if (zeorExchangeFragment == null) {
                    zeorExchangeFragment = new ZeorExchangeFragment();
                    transaction.add(R.id.content, zeorExchangeFragment);
                }
                hiderFragment(transaction);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.show(zeorExchangeFragment);
                transaction.commit();
                StatuBarUtils.setTranslucentStatus(activity);
                //TODO[点击0元兑]
                ConstantScUtil.exchangeClick();
                break;
            case FRAGMENT_TYPE.APP_MINE:
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    transaction.add(R.id.content, mMineFragment);
                }
                hiderFragment(transaction);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.show(mMineFragment);
                transaction.commit();
                StatuBarUtils.setTranslucentStatus(activity);
                break;
        }
    }

    public void setStatusBarColor(int color, boolean dark) {
        StatuBarUtils.setStatusBarDarkTheme(activity, dark);
        StatuBarUtils.setStatusBarColor(activity, activity.getResources().getColor(color));
    }

    public static void setStatusBarColor2(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            //状态栏改变颜色。
            window.setStatusBarColor(color);
        }
    }

}

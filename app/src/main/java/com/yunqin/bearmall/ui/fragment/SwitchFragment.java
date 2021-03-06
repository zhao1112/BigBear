package com.yunqin.bearmall.ui.fragment;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.bbcoupon.ui.fragment.MakeFragment;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.StatuBarUtils;

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
    private BusinessFragment mRecommendFragment;
    private MakeFragment mMakeMoneyFragment;
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
                }
                if (!mHomeFragment.isAdded()) {
                    transaction.add(R.id.content, mHomeFragment);
                }
                hiderFragment(transaction);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.show(mHomeFragment);
                transaction.commitAllowingStateLoss();
                StatuBarUtils.setTranslucentStatus(activity);
                break;
            case FRAGMENT_TYPE.APP_RECOMMEND:
                if (mRecommendFragment == null) {
                    mRecommendFragment = new BusinessFragment();
                }
                if (!mRecommendFragment.isAdded()) {
                    transaction.add(R.id.content, mRecommendFragment);
                }
                hiderFragment(transaction);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.show(mRecommendFragment);
                transaction.commitAllowingStateLoss();
                setStatusBarColor(R.color.white, true);
                break;
            case FRAGMENT_TYPE.APP_INFORMATION:
                if (mMakeMoneyFragment == null) {
                    mMakeMoneyFragment = new MakeFragment();
                }
                if (!mMakeMoneyFragment.isAdded()) {
                    transaction.add(R.id.content, mMakeMoneyFragment);
                }
                hiderFragment(transaction);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.show(mMakeMoneyFragment);
                transaction.commitAllowingStateLoss();
                StatuBarUtils.setTranslucentStatus(activity);
                break;
            case FRAGMENT_TYPE.APP_TROLLEY:
                if (zeorExchangeFragment == null) {
                    zeorExchangeFragment = new ZeorExchangeFragment();
                }
                if (!zeorExchangeFragment.isAdded()) {
                    transaction.add(R.id.content, zeorExchangeFragment);
                }
                hiderFragment(transaction);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.show(zeorExchangeFragment);
                transaction.commitAllowingStateLoss();
                StatuBarUtils.setTranslucentStatus(activity);
                //TODO[点击0元兑]
                ConstantScUtil.exchangeClick();
                break;
            case FRAGMENT_TYPE.APP_MINE:
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                }
                if (!mMineFragment.isAdded()) {
                    transaction.add(R.id.content, mMineFragment);
                }
                hiderFragment(transaction);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.show(mMineFragment);
                transaction.commitAllowingStateLoss();
                StatuBarUtils.setTranslucentStatus(activity);
                break;
        }
    }

    public void setStatusBarColor(int color, boolean dark) {
        StatuBarUtils.setStatusBarDarkTheme(activity, dark);
        StatuBarUtils.setStatusBarColor(activity, activity.getResources().getColor(color));
    }

}

package com.yunqin.bearmall.util;

import android.support.annotation.IntDef;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.ui.fragment.GuideWithVideoFragment;
import com.yunqin.bearmall.ui.fragment.HomeFragment;
import com.yunqin.bearmall.ui.fragment.MakeMoneyFragment;
import com.yunqin.bearmall.ui.fragment.MineNewFragment;
import com.yunqin.bearmall.ui.fragment.ZeroGoodsFragment;

/**
 * @author Master
 */
public class SwitchFragment {

    private int LAST_POSITION = -1;

    @IntDef({FRAGMENT_TYPE.APP_HOME, FRAGMENT_TYPE.APP_RECOMMEND, FRAGMENT_TYPE.APP_INFORMATION, FRAGMENT_TYPE.APP_TROLLEY, FRAGMENT_TYPE.APP_MINE})
    public @interface FRAGMENT_TYPE {
        int APP_HOME = 0;
        int APP_INFORMATION = 1;
        int APP_RECOMMEND = 2;
        int APP_TROLLEY = 3;
        int APP_MINE = 4;

    }


    private  HomeFragment mHomeFragment;
    private  GuideWithVideoFragment mRecommendFragment;
//    private static InformationFragment mInformationFragment;

    private  MakeMoneyFragment mMakeMoneyFragment;

    // TODO: 2019/7/15 0015 替换成0元兑
    private  ZeroGoodsFragment mTrolleyFragment;
//    private static TrolleyFragment mTrolleyFragment;
    private  MineNewFragment mMineFragment;
//    private static MineFragment mMineFragment;

    private FragmentManager manager;

    public SwitchFragment(FragmentManager manager) {
        this.manager = manager;
//        FragmentTransaction transaction = manager.beginTransaction();
//        if (mHomeFragment == null) {
//            mHomeFragment = new HomeFragment();
//            transaction.add(R.id.content, mHomeFragment);
//        }
//        hiderFragment(transaction);
//        transaction.show(mHomeFragment);
//        transaction.commit();
    }


    private void hiderFragment(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mRecommendFragment != null) {
            transaction.hide(mRecommendFragment);
        }
        if (mMakeMoneyFragment != null) {
            transaction.hide(mMakeMoneyFragment);
        }
        if (mTrolleyFragment != null) {
            transaction.hide(mTrolleyFragment);
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
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.content, mHomeFragment);
                }
                hiderFragment(transaction);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.show(mHomeFragment);
                transaction.commit();
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
                break;
            case FRAGMENT_TYPE.APP_TROLLEY:
                if (mTrolleyFragment == null) {
                    mTrolleyFragment = new ZeroGoodsFragment();
                    transaction.add(R.id.content, mTrolleyFragment);
                }
                hiderFragment(transaction);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.show(mTrolleyFragment);
                transaction.commit();
                break;
            case FRAGMENT_TYPE.APP_MINE:
                if (mMineFragment == null) {
                    mMineFragment = new MineNewFragment();
//                    mMineFragment = new MineFragment();
                    transaction.add(R.id.content, mMineFragment);
                }
                hiderFragment(transaction);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.show(mMineFragment);
                transaction.commit();
                break;
        }
    }

}
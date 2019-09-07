package com.yunqin.bearmall.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yunqin.bearmall.base.BaseActivity;

/**
 * Created by chenchen on 2018/7/25.
 */

public abstract class ContainFragmentActivity extends BaseActivity{

    protected FragmentManager mFragmentMannger;


    @Override
    public void init() {
        mFragmentMannger = getSupportFragmentManager();
    }

    /**
     * 添加fragment
     * @param containID
     * @param fragment
     */
    protected void addFragment(int containID, Fragment fragment){

        if (containID <=0 || fragment == null){

            return;
        }

        FragmentTransaction transaction = mFragmentMannger.beginTransaction();

        transaction.add(containID,fragment);

        transaction.commitAllowingStateLoss();

    }


    /**
     * 移除fragment
     * @param fragment
     */
    protected void removeFragment(Fragment fragment){

        if (fragment == null){

            return;
        }

        FragmentTransaction transaction = mFragmentMannger.beginTransaction();

        transaction.remove(fragment);

        transaction.commitAllowingStateLoss();

    }

    /**
     * 显示fragmnet
     * @param fragment
     */
    protected void showFragment(Fragment fragment){

        if (fragment == null){

            return;
        }

        FragmentTransaction transaction = mFragmentMannger.beginTransaction();

        transaction.show(fragment);

        transaction.commitAllowingStateLoss();

    }


    /**
     * 隐藏fragment
     * @param fragment
     */
    protected void hideFragment(Fragment fragment){

        if (fragment == null){

            return;
        }

        FragmentTransaction transaction = mFragmentMannger.beginTransaction();

        transaction.hide(fragment);

        transaction.commitAllowingStateLoss();

    }


    protected void replaceFragment(int containerID,Fragment fragment){

        if (fragment == null){

            return;
        }

        FragmentTransaction transaction = mFragmentMannger.beginTransaction();

        transaction.replace(containerID,fragment);

        transaction.commitAllowingStateLoss();

    }
}

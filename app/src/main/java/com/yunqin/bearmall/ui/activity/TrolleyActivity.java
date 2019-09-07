package com.yunqin.bearmall.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.ui.fragment.TrolleyFragment;

public class TrolleyActivity extends BaseActivity{


    @Override
    public int layoutId() {
        return R.layout.activity_trolley;
    }

    @Override
    public void init() {

        Fragment fragment = null;

        if(fragment == null){
            fragment = TrolleyFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putBoolean("isBack",true);
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, fragment, "TrolleyFragment");
            transaction.commit();
        }else{
            fragment = getSupportFragmentManager().findFragmentByTag("TrolleyFragment");
        }

    }
}

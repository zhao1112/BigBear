package com.newversions.push;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;

public class MyPushActivity extends BaseActivity implements View.OnClickListener {

    private RadioGroup radioGroup;
    private RadioButton new_m1;
    private RadioButton new_m2;

    private FragmentManager fragmentManager;
    private MyOneFragment m1Fragment;
    private MyTwoFragment m2Fragment;

    @Override
    public int layoutId() {
        return R.layout.new_version_activity_my_push;
    }

    @Override
    public void init() {
        findViewById(R.id.new_version_back).setOnClickListener(this);
        radioGroup = findViewById(R.id.new_radio_group);
        new_m1 = findViewById(R.id.new_m1);
        new_m2 = findViewById(R.id.new_m2);
        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            hiddenKeyboard();
            if (i == R.id.new_m1) {
                new_m1.setTextColor(Color.parseColor("#ffffff"));
                new_m2.setTextColor(Color.parseColor("#23A064"));
                chooseFragment(true);
            } else if (i == R.id.new_m2) {
                new_m2.setTextColor(Color.parseColor("#ffffff"));
                new_m1.setTextColor(Color.parseColor("#23A064"));
                chooseFragment(false);
            }
        });

        fragmentManager = getSupportFragmentManager();
        chooseFragment(true);
    }

    @Override
    public void onClick(View view) {
        finish();
    }


    private void chooseFragment(boolean showm1) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (showm1) {
            if (m1Fragment == null) {
                m1Fragment = new MyOneFragment();
                fragmentTransaction.add(R.id.push_container, m1Fragment);
            }
            hideFragment(fragmentTransaction);
            fragmentTransaction.show(m1Fragment);
        } else {
            if (m2Fragment == null) {
                m2Fragment = new MyTwoFragment();
                fragmentTransaction.add(R.id.push_container, m2Fragment);
            }
            fragmentTransaction.show(m2Fragment);
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (m1Fragment != null) {
            fragmentTransaction.hide(m1Fragment);
        }
        if (m2Fragment != null) {
            fragmentTransaction.hide(m2Fragment);
        }
    }

    public void hiddenKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


}

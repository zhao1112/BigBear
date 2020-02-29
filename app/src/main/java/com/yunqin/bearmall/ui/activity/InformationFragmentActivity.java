package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;

public class InformationFragmentActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, InformationFragmentActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_information_fragment;
    }

    @Override
    public void init() {

    }

}

package com.yunqin.bearmall.ui.activity;

import android.os.Handler;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.widget.SlantTextView;

import butterknife.BindView;

public class Testctivity extends BaseActivity {

    @BindView(R.id.test_slant)
    SlantTextView textView;

    @Override
    public int layoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void init() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("测试吃");

            }
        },3000);

    }
}

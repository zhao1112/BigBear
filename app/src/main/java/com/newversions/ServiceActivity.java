package com.newversions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;

/**
 * 在线客服
 */

public class ServiceActivity extends BaseActivity {


    public static void start(Context context) {
        Intent intent = new Intent(context, ServiceActivity.class);
        context.startActivity(intent);
    }


    @Override
    public int layoutId() {
        return R.layout.new_version_activity_service;
    }

    @Override
    public void init() {
        findViewById(R.id.new_version_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}

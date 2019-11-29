package com.newversions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 在线客服
 */

public class ServiceActivity extends BaseActivity {


    @BindView(R.id.visename)
    TextView mVisename;

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
        String versionName = BuildConfig.VERSION_NAME;
        mVisename.setText("当前应用版本：V" + versionName);

        findViewById(R.id.new_version_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}

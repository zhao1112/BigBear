package com.newversions.passwd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;

public class PwdActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public int layoutId() {
        return R.layout.new_version_activity_pwd;
    }

    @Override
    public void init() {
        findViewById(R.id.new_version_back).setOnClickListener(this);
        findViewById(R.id.new_version_update).setOnClickListener(this);
        findViewById(R.id.new_version_reset).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_version_back:
                finish();
                break;
            case R.id.new_version_update:
                startActivity(new Intent(this, ChangePwdActivity.class));
                break;
            case R.id.new_version_reset:
                startActivity(new Intent(this, RetrievePwdActivity.class));
                break;
        }
    }
}

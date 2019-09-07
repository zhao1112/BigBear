package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.content.Intent;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;

/**
 * @author AYWang
 * @create 2018/7/28
 * @Describe
 */
public class DailyTasksActivity extends BaseActivity {


    public static void starActivity(Activity mContext) {
        Intent intent = new Intent(mContext, DailyTasksActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_daily_tasks1;
    }


    @Override
    public void init() {
        //
    }


}

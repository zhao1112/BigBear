package com.yunqin.bearmall.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yunqin.bearmall.R;

public class StarActivityUtil {
    private Context context;
    private Class<Activity> cls;

    public static void starActivity(Activity context, Class cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
        context.overridePendingTransition(0, R.anim.activity_stay);
    }

    public static void starActivity(Activity context, Class cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
        context.overridePendingTransition(0, R.anim.activity_stay);
    }

    public static void startActivityForResult(Activity context, Class cls, int requestCode) {
        Intent intent = new Intent(context, cls);
        context.startActivityForResult(intent, requestCode);
        context.overridePendingTransition(0, R.anim.activity_stay);
    }

    public static void startActivityForResult(Activity context, Class cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
        context.overridePendingTransition(0, R.anim.activity_stay);
    }


}

package com.yunqin.bearmall.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

import static android.content.Context.WINDOW_SERVICE;

public abstract class BaseDialog extends Dialog{

    public BaseDialog(@NonNull Context context) {
        super(context);
        resetDensity(context,750);
//        ButterKnife.bind((Activity) context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        resetDensity(context,750);
//        ButterKnife.bind((Activity) context);
    }

    public static void resetDensity(Context context, float designWidth){
        if(context == null)
            return;

        Point size = new Point();
        ((WindowManager)context.getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(size);

        Resources resources = context.getResources();

        resources.getDisplayMetrics().xdpi = size.x/designWidth*72f;

        DisplayMetrics metrics = getMetricsOnMiui(context.getResources());
        if(metrics != null)
            metrics.xdpi = size.x/designWidth*72f;
    }

    //解决MIUI更改框架导致的MIUI7+Android5.1.1上出现的失效问题(以及极少数基于这部分miui去掉art然后置入xposed的手机)
    private static DisplayMetrics getMetricsOnMiui(Resources resources){
        if("MiuiResources".equals(resources.getClass().getSimpleName()) || "XResources".equals(resources.getClass().getSimpleName())){
            try {
                Field field = Resources.class.getDeclaredField("mTmpMetrics");
                field.setAccessible(true);
                return  (DisplayMetrics) field.get(resources);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

}

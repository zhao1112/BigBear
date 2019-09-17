package com.yunqin.bearmall.util;

import android.content.Context;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.util
 * @DATE 2019/9/16
 */
public class ContextHelper {
    private static Context mApplicationContext;

    private ContextHelper() {
    }

    /**
     * 设置程序上下文。
     *
     * @param context 程序上下文
     */
    public static void init(Context context) {
        if (mApplicationContext == null && context != null) {
            mApplicationContext = context.getApplicationContext();
        }
    }

    /**
     * 获取程序上下文，未设置将返回空。
     */
    public static Context getContext() {
        return mApplicationContext;
    }

    public static int getId(String name) {
        return mApplicationContext.getResources().getIdentifier(name, "id", mApplicationContext.getPackageName());
    }

    public static int getString(String name) {
        return mApplicationContext.getResources().getIdentifier(name, "string", mApplicationContext.getPackageName());
    }

    public static int getColor(String name) {
        return mApplicationContext.getResources().getIdentifier(name, "color", mApplicationContext.getPackageName());
    }

    public static int getDrawable(String name) {
        return mApplicationContext.getResources().getIdentifier(name, "drawable", mApplicationContext.getPackageName());
    }
}

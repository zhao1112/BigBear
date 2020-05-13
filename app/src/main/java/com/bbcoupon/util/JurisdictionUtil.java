package com.bbcoupon.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import permison.PermissonUtil;
import permison.listener.PermissionListener;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.util
 * @DATE 2020/4/28
 */
public class JurisdictionUtil {

    private static boolean isJurisdiction;

    public static void Jurisdiction(Activity activity, String[] strings) {
        PermissonUtil.checkPermission(activity, new PermissionListener() {
            @Override
            public void havePermission() {
                isJurisdiction = true;
            }

            @Override
            public void requestPermissionFail() {
                isJurisdiction = false;
            }
        }, strings);
    }

    public static boolean IsJurisdiction() {
        return isJurisdiction;
    }

    //权限检查
    public static boolean isLacksPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(context, permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    public static boolean lacksPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_DENIED;
    }
}

package com.bbcoupon.util;

import android.app.Activity;

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

}

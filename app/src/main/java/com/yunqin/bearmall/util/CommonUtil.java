package com.yunqin.bearmall.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import com.yunqin.bearmall.BearMallAplication;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.util
 * @DATE 2019/9/16
 */
public class CommonUtil {
    public static String getAid() {
        try {
            return android.provider.Settings.Secure.getString(BearMallAplication.get().getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getNetworkType(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (null != info && info.getType() == ConnectivityManager.TYPE_WIFI) {
                return "WIFI";
            }
            if (null == info || info.getType() != ConnectivityManager.TYPE_MOBILE) {
                return "UNAVAILABLE";
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            int networkType = telephonyManager.getNetworkType();
            switch (networkType) {
                case Config.NETWORK_TYPE_UNAVAILABLE:
                    return "UNAVAILABLE";
                case Config.NETWORK_TYPE_GPRS:
                case Config.NETWORK_TYPE_EDGE:
                case Config.NETWORK_TYPE_CDMA:
                case Config.NETWORK_TYPE_1xRTT:
                case Config.NETWORK_TYPE_IDEN:
                    return "2G";
                case Config.NETWORK_TYPE_UMTS:
                case Config.NETWORK_TYPE_EVDO_0:
                case Config.NETWORK_TYPE_EVDO_A:
                case Config.NETWORK_TYPE_HSDPA:
                case Config.NETWORK_TYPE_HSUPA:
                case Config.NETWORK_TYPE_HSPA:
                case Config.NETWORK_TYPE_EVDO_B:
                case Config.NETWORK_TYPE_EHRPD:
                case Config.NETWORK_TYPE_HSPAP:
                    return "3G";
                case Config.NETWORK_TYPE_LTE:
                    return "4G";
                default:
                    return "UNKNOW";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "UNKNOW";
    }


    public static String getPackageName(Context context) {
        try {
            //当前应用pid
            int pid = android.os.Process.myPid();
            //任务管理类
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            //遍历所有应用
            List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo info : infos) {
                if (info.pid == pid)//得到当前应用
                    return info.processName;//返回包名
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static synchronized <T> void writeParcel(Context context, String fileName, T t) {
        if (context == null) {
            return;
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(context.getFileStreamPath(fileName)));
            oos.writeObject(t);
        } catch (Exception e) {
        } catch (Error e) {
        } finally {
            if (null != oos) {
                try {
                    oos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static void toSelfSetting(Context context) {
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            mIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            mIntent.setAction(Intent.ACTION_VIEW);
            mIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            mIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(mIntent);
    }

}


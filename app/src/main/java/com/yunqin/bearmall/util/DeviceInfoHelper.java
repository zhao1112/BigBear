package com.yunqin.bearmall.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.util
 * @DATE 2019/9/16
 */
public class DeviceInfoHelper {
    private static DeviceInfoHelper _instance;
    private final Context _context;

    public static DeviceInfoHelper defaultHelper() {
        if (_instance == null) {
            _instance = new DeviceInfoHelper(ContextHelper.getContext());
        }
        return _instance;
    }

    private DeviceInfoHelper(Context context) {
        this._context = context.getApplicationContext();
    }



    /**
     * 获取手机厂商 *
     */
    public String getManufacturer() {
        return Build.BRAND.replaceAll(" ", "_");
    }

    /**
     * 获取手机型号 *
     */
    public String getMobileType() {
        return Build.MODEL.replaceAll(" ", "_");
    }



    /**
     * 获取应用的版本号 获取失败时返回-1
     */
    public int getAppVersionCode() {
        int result;
        PackageManager pm = _context.getPackageManager();
        if (pm != null) {
            try {
                PackageInfo info = pm.getPackageInfo(_context.getPackageName(), 0);
                result = info.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                result = -1;
            }
        } else {
            result = -1;
        }
        return result;
    }

    /**
     * 获取应用的版本名 获取失败时返回unknown
     */
    public String getAppVersionName() {
        String result = "unknown";
        PackageManager pm = _context.getPackageManager();
        if (pm != null) {
            try {
                PackageInfo info = pm.getPackageInfo(_context.getPackageName(), 0);
                result = info.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result;
    }



    /**
     * 获取Data分区的总大小 *
     */
    @SuppressWarnings({ "deprecation", "UnusedDeclaration" })
    public long getDataPartitionTotalSize() {
        StatFs fs = new StatFs(Environment.getDataDirectory().getPath());
        return (long) fs.getBlockSize() * fs.getBlockCount();
    }

    /**
     * 获取Data分区的已用空间 *
     */
    @SuppressWarnings({ "deprecation", "UnusedDeclaration" })
    public long getDataPartitionUsedSize() {
        StatFs fs = new StatFs(Environment.getDataDirectory().getPath());
        return (long) (fs.getBlockCount() - fs.getAvailableBlocks()) * fs.getBlockSize();
    }

    /**
     * 获取Data分区的可用空间 *
     */
    @SuppressWarnings({ "deprecation", "UnusedDeclaration" })
    public long getDataPartitionAvailableSize() {
        StatFs fs = new StatFs(Environment.getDataDirectory().getPath());
        return (long) fs.getAvailableBlocks() * fs.getBlockSize();
    }

    private static final String SYSTEM_PATH = "/system";

    /**
     * 获取System分区总大小 *
     */
    @SuppressWarnings({ "deprecation", "UnusedDeclaration" })
    public long getSystemPartitionTotalSize() {
        StatFs fs = new StatFs(SYSTEM_PATH);
        return (long) fs.getBlockSize() * fs.getBlockCount();
    }

}

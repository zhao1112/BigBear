package com.yunqin.bearmall.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.yunqin.bearmall.BearMallAplication;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.util
 * @DATE 2019/9/16
 */
public class DeviceInfoInit {
    private DeviceInfo mDeviceInfo;
    private final IDeviceTaskListener mListener;
    private final Context mContext;

    public interface IDeviceTaskListener {

        void onDeviceInfoLoaded(DeviceInfo deviceInfo);
    }

    public DeviceInfo getmDeviceInfo() {
        return mDeviceInfo;
    }

    public DeviceInfoInit(Context context, IDeviceTaskListener listener) {
        this.mContext = context;
        mListener = listener;
        deviceInfoInitPre();
        try {
            deviceInfoInit();
        } catch (Exception e) {
        }
        if (null != mDeviceInfo) {
            deviceInfoListener(mDeviceInfo);
        }
    }

    private String userAgent = null;

    private void deviceInfoInitPre() {
        mDeviceInfo = new DeviceInfo();
    }

    private void deviceInfoInit() throws Exception {

        String androidId = "";
        String androidAdid = "";
        String mac = "";
        int mcc = -1;
        int mnc = -1;
        int osVersion = -1;
        String osVersionName = "";
        int apkVersion = -1;
        String apkVersionName = "";
        int otaVersion = -1;
        int screenWidth = -1;
        int screenHeight = -1;
        float screenSize = -1.0f;
        String memoryTotal = "";
        String kernelVersion = "";
        String productBrand = "";
        String productName = "";
        String productModel = "";
        String fingerPrint = "";
        String buildDate = "";
        String serialNumber = "";


        //imei
        try {
            //androidId
            androidId = android.provider.Settings.Secure.getString(BearMallAplication.get().getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);

            osVersion = Build.VERSION.SDK_INT;
            TelephonyManager tm = (TelephonyManager) BearMallAplication.get().getSystemService(Context.TELEPHONY_SERVICE);
            //inet_mac
            WifiManager wifi = (WifiManager) BearMallAplication.get().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifi.getConnectionInfo();
            mac = wifiInfo.getMacAddress();


        } catch (Exception ex) {
//            ex.printStackTrace();
        }
        osVersionName = Build.VERSION.RELEASE;
        try {
            PackageInfo packageInfo =
                    BearMallAplication.get().getPackageManager().getPackageInfo(BearMallAplication.get().getPackageName(), 0);
            if (null != packageInfo) {
                apkVersion = packageInfo.versionCode;
                apkVersionName = packageInfo.versionName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) BearMallAplication.get().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;
        screenHeight = metric.heightPixels;
        //screenSize
        float sWidth = metric.widthPixels / metric.xdpi;
        float sHeight = metric.heightPixels / metric.ydpi;
        screenSize = Math.round(Math.sqrt(Math.pow(sWidth, 2) + Math.pow(sHeight, 2)));
        //memory total
        FileInputStream mtFis = null;
        BufferedReader mtReader = null;
        try {
            mtFis = new FileInputStream("/proc/meminfo");
            mtReader = new BufferedReader(new InputStreamReader(mtFis));
            String memoryInfos = mtReader.readLine();
            if (!TextUtils.isEmpty(memoryInfos)) {
                String[] values = memoryInfos.split(":");
                if (values.length > 1 && !TextUtils.isEmpty(values[1])) {
                    memoryTotal = values[1].trim();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mtReader) {
                try {
                    mtReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        productBrand = SystemProperties.get(Config.PRODUCT_BRAND);

        productName = SystemProperties.get(Config.PRODUCT_NAME);

        productModel = SystemProperties.get(Config.PRODUCT_MODEL);

        fingerPrint = SystemProperties.get(Config.FINGER_PRINT);

        buildDate = SystemProperties.get(Config.BUILD_DATE);

        serialNumber = SystemProperties.get(Config.RO_SERIALNO);

        mDeviceInfo.setAndroidId(androidId);
        mDeviceInfo.setAndroidAdid(androidAdid);
        mDeviceInfo.setMac(mac);
        mDeviceInfo.setMcc(mcc);
        mDeviceInfo.setMnc(mnc);
        mDeviceInfo.setOsVersion(osVersion);
        mDeviceInfo.setOsVersionName(osVersionName);
        mDeviceInfo.setApkVersion(apkVersion);
        mDeviceInfo.setApkVersionName(apkVersionName);
        mDeviceInfo.setOtaVersion(otaVersion);
        mDeviceInfo.setScreenWidth(screenWidth);
        mDeviceInfo.setScreenHeight(screenHeight);
        mDeviceInfo.setScreenSize(screenSize);
        mDeviceInfo.setMemoryTotal(memoryTotal);
        mDeviceInfo.setKernelVersion(kernelVersion);
        mDeviceInfo.setProductBrand(productBrand);
        mDeviceInfo.setProductName(productName);
        mDeviceInfo.setProductModel(productModel);
        mDeviceInfo.setFingerPrint(fingerPrint);
        mDeviceInfo.setBuildDate(buildDate);
        mDeviceInfo.setSerialNumber(serialNumber);
        mDeviceInfo.setResolution(screenWidth + "x" + screenHeight);
        CommonUtil.writeParcel(mContext, Config.DEVICE_INFO, mDeviceInfo);
    }

    private void deviceInfoListener(DeviceInfo result) {
        if (null != mListener) {
            mListener.onDeviceInfoLoaded(result);
        }
    }

}

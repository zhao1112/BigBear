package com.yunqin.bearmall.util;

import java.io.Serializable;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.util
 * @DATE 2019/9/16
 */
public class DeviceInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userAgent;
    private String androidId;
    private String androidAdid;
    private String mac;
    private int mcc;
    private int mnc;
    private int osVersion; // 数字版本号
    private String osVersionName;
    private String kernelVersion;
    private int apkVersion;
    private String apkVersionName;
    private int otaVersion;
    private int screenWidth;
    private int screenHeight;
    private float screenSize;
    private String memoryTotal; //所有可用内存
    private String productBrand; // 手机品牌
    private String productName; // 手机正式(官方)名称
    private String productModel; // 手机代号，手机名
    private String fingerPrint; // 机身码
    private String buildDate; // 制作者及时间
    private String serialNumber; // 唯一标识号
    private String resolution;

    @Override
    public String toString() {
        return  "userAgent:" + userAgent + "\n" +
                "androidId:" + androidId + "\n" +
                "androidAdid:" + androidAdid + "\n" +
                "mac:" + mac + "\n" +
                "mcc:" + mcc + "\n" +
                "mnc:" + mnc + "\n" +
                "osVersion:" + osVersion + "\n" +
                "osVersionName:" + osVersionName + "\n" +
                "kernelVersion:" + kernelVersion + "\n" +
                "apkVersion:" + apkVersion + "\n" +
                "apkVersionName:" + apkVersionName + "\n" +
                "otaVersion:" + otaVersion + "\n" +
                "screenWidth:" + screenWidth + "\n" +
                "screenHeight:" + screenHeight + "\n" +
                "screenSize:" + screenSize + "\n" +
                "memoryTotal:" + memoryTotal + "\n" +
                "productBrand:" + productBrand + "\n" +
                "productName:" + productName + "\n" +
                "productModel:" + productModel + "\n" +
                "fingerPrint:" + fingerPrint + "\n" +
                "buildDate:" + buildDate + "\n" +
                "serialNumber:" + serialNumber + "\n";
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getAndroidAdid() {
        return androidAdid;
    }

    public void setAndroidAdid(String androidAdid) {
        this.androidAdid = androidAdid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getMcc() {
        return mcc;
    }

    public void setMcc(int mcc) {
        this.mcc = mcc;
    }

    public int getMnc() {
        return mnc;
    }

    public void setMnc(int mnc) {
        this.mnc = mnc;
    }

    public int getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(int osVersion) {
        this.osVersion = osVersion;
    }

    public String getOsVersionName() {
        return osVersionName;
    }

    public void setOsVersionName(String osVersionName) {
        this.osVersionName = osVersionName;
    }

    public String getKernelVersion() {
        return kernelVersion;
    }

    public void setKernelVersion(String kernelVersion) {
        this.kernelVersion = kernelVersion;
    }

    public int getApkVersion() {
        return apkVersion;
    }

    public void setApkVersion(int apkVersion) {
        this.apkVersion = apkVersion;
    }

    public String getApkVersionName() {
        return apkVersionName;
    }

    public void setApkVersionName(String apkVersionName) {
        this.apkVersionName = apkVersionName;
    }

    public int getOtaVersion() {
        return otaVersion;
    }

    public void setOtaVersion(int otaVersion) {
        this.otaVersion = otaVersion;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public float getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(float screenSize) {
        this.screenSize = screenSize;
    }

    public String getMemoryTotal() {
        return memoryTotal;
    }

    public void setMemoryTotal(String memoryTotal) {
        this.memoryTotal = memoryTotal;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getResolution() {
        return resolution;
    }


    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}


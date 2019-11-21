package com.yunqin.bearmall.util;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;

import com.newversions.passwd.IMD5;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    /**
     * 判断sd卡是否可用
     */
    public static boolean isSDEnable() {
        String en = Environment.getExternalStorageState();
        if (en.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }


    /**
     * 得到当前sdk的版本号.如2.3.5对应的值是10.
     */
    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取应用的版本号
     */
    public static int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取版本名
     */
    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 安装apk
     *
     * @param apkFile apk文件
     */
    public static void installApk(Context context, File apkFile) {
        if (apkFile != null && apkFile.exists()) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setDataAndType(Uri.fromFile(apkFile),
                    "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
    }


    /**
     * judge s is or not a phone number
     */
    public static boolean isPhoneNumber(String s) {
        if (android.text.TextUtils.isEmpty(s))
            return false;
        String pattern = "^(1)\\d{10}$";
        Pattern phone = Pattern.compile(pattern);
        return phone.matcher(s).matches();
    }

//    public static boolean isPhoneNumber(String s) {
//        if (android.text.TextUtils.isEmpty(s))
//            return false;
//        String pattern = "^((13[0-9])|(14[5,7])|(15[^(4,\\D)])|166|(17[0,3,6,7,8])|(18[0-9])|19[8,9])\\d{8}$";
//        Pattern phone = Pattern.compile(pattern);
//        return phone.matcher(s).matches();
//    }

    /**
     * 验证邮箱地址是否正确
     */
    public static boolean checkEmail(String email) {
        boolean flag;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 将bitmap转为base64的字符串
     */
    public static String image2Base64(Bitmap bit, int quality, Bitmap.CompressFormat format) {
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            bit.compress(format, quality, bos);//参数100表示不压缩
            byte[] bytes = bos.toByteArray();
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 注册网络监听广播
     */
    public static void registerNetworkReceiver(Context context, BroadcastReceiver receiver) {
        if (receiver == null)
            return;
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(receiver, filter);
    }

    /**
     * 取消某个广播
     */
    public static void unregisterReceiver(Context context, BroadcastReceiver receiver) {
        if (receiver == null)
            return;
        context.unregisterReceiver(receiver);
    }

    /**
     * 获取清单文件中int类型的元信息
     *
     * @param name 元信息对应的name。如果没有对应的信息，返回0。
     */
    public static int getIntMetaData(String name, Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            ApplicationInfo info = manager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle data = info.metaData;
            return data.getInt(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取清单文件中String类型的元信息
     *
     * @param name 元信息对应的name。如果没有对应的信息，返回""。
     */
    public static String getStringMetaData(String name, Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            ApplicationInfo info = manager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle data = info.metaData;
            return data.getString(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 复制文件
     *
     * @param fromFile 被读取的文件
     * @param outFile  要写入的文件
     */
    public static void copyFile(File fromFile, File outFile) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(fromFile);
            fo = new FileOutputStream(outFile);
            in = fi.getChannel();
            out = fo.getChannel();
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fi != null)
                    fi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fo != null)
                    fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * 判断字符串为不为空
     */
    public static String isEmpty(String value) {
        return value == null ? "" : value;
    }


    /***
     * 超时时间  按钮倒计时
     */
    public static final int waittime = 60000;

    public static void showCountDown(Button button, long millisInFuture, long countDownInterval) {
        new CountDownTimerUtils(button, millisInFuture, countDownInterval).start();
    }

    static class CountDownTimerUtils extends CountDownTimer {
        private Button mTextView;

        /**
         * @param textView          The TextView
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receiver
         *                          {@link #onTick(long)} callbacks.
         */
        public CountDownTimerUtils(Button textView, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.mTextView = textView;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTextView.setClickable(false); //设置不可点击
            mTextView.setTextColor(Color.parseColor("#B3B3B3"));
            mTextView.setBackgroundResource(R.drawable.btn_press_unclick);
            mTextView.setText(millisUntilFinished / 1000 + "秒后重发");  //设置倒计时时间
        }

        @Override
        public void onFinish() {
            mTextView.setText("发送随机码");
            mTextView.setClickable(true);//重新获得点击
            mTextView.setTextColor(Color.parseColor("#169F65"));
            mTextView.setBackgroundResource(R.drawable.btn_other_way_login_bg);
        }
    }


    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }


    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机IMEI
     *
     * @param context
     * @return
     */
    public static final String getIMEI(Context context) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            @SuppressLint("MissingPermission") String imei = telephonyManager.getDeviceId();
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 密码校验
     *
     * @return
     */
    public static final boolean pwdTry(String pwd) {
        //        判断密码是否包含数字：包含返回1，不包含返回0
        int i = pwd.matches(".*\\d+.*") ? 1 : 0;
        //        判断密码是否包含字母：包含返回1，不包含返回0
        int j = pwd.matches(".*[a-zA-Z]+.*") ? 1 : 0;
        //        判断密码是否包含特殊符号(~!@#$%^&*()_+|<>,.?/:;'[]{}\)：包含返回1，不包含返回0
        int k = pwd.matches(".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*") ? 1 : 0;

        if (i + j + k < 2) {
            return false;
        }
        return true;
    }


    /**
     * 版本号比较
     *
     * @param version1
     * @param version2
     * @return 0代表相等，1代表version1大于version2，-1代表version1小于version2
     */
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        Log.d("HomePageActivity", "version1Array==" + version1Array.length);
        Log.d("HomePageActivity", "version2Array==" + version2Array.length);
        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小
        Log.d("HomePageActivity", "verTag2=2222=" + version1Array[index]);
        while (index < minLen
                && (diff = Integer.parseInt(version1Array[index])
                - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

    /**
     * 判断字符串中是否包含字母
     **/
    public static boolean isContainsLetter(String input) {
        if (!TextUtils.isEmpty(input)) {
            Matcher matcher = Pattern.compile(".*[a-zA-Z]+.*").matcher(input);
            return matcher.matches();
        } else {
            return false;
        }
    }

    /**
     * 判断是否开启通知权限
     */
    public static boolean isNotificationEnabled(Context context) {
        boolean isOpened = false;
        try {
            isOpened = NotificationManagerCompat.from(context).areNotificationsEnabled();
        } catch (Exception e) {
            e.printStackTrace();
            isOpened = false;
        }
        return isOpened;
    }

    /**
     * 返回名品有券365加密参数串.
     * @return
     */
    public static String getParam365(String url){
        String machineCode = DeviceUtils.getUniqueId(BearMallAplication.getInstance().getApplicationContext());
        String timestamp = String.valueOf(System.currentTimeMillis());
        ConcurrentHashMap map = new ConcurrentHashMap();
        Map<String, String> secretKeyMap = new LinkedHashMap<>();
        map.put("machineCode", machineCode);
        map.put("agentId", "292");
        map.put("timestamp", timestamp);
        //校验数据
        secretKeyMap.put("secretKey", "RsHfWNYyWMJxhezZQ8QarrNbnYWH7SQh");
        String sign = IMD5.getSign(map, secretKeyMap);
        String result = url;
        if (!StringUtils.isEmpty(result)) {
            if (!result.contains("?")) {
                result += "?1=1";
            }
            if (!result.contains("&") && result.contains("=")) {
                result += "&";
            }
            result += "machineCode=" + machineCode + "&agentId=292&timestamp=" + timestamp + "&sign=" + sign;
        }
        return result;
    }


}

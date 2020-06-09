package com.bbcoupon.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.util.CommonUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.util
 * @DATE 2020/5/6
 */
public class ConstantUtil {

    public static final String first_home = "FIRST_HOME";//第一次打开首页
    public static final String first_search = "FIRST_SEARCH";//第一次打开搜索页
    public static final String first_goodes = "FIRST_GOODES";//第一次打开搜索详情页
    public static final String money = "¥";
    public static final String download = "https://a.app.qq.com/o/simple.jsp?pkgname=com.bbearmall.app";//下载app链接
    public static final String DOUBLING_RULE = "1";//佣金翻倍规则
    public static final String AUTHORIZATION_SUCCESSFUL = "authorization_successful";//授权记录
    public static final String Search_Type = "Search_Type";


    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    /***
     * 超时时间  按钮倒计时
     */
    public static final int waittime = 60000;

    public static void showCountDown(TextView button, long millisInFuture, long countDownInterval, int startId, int endId) {
        new ConstantUtil.CountDownTimerUtils(button, millisInFuture, countDownInterval, startId, endId).start();
    }

    static class CountDownTimerUtils extends CountDownTimer {
        private TextView mTextView;
        private int startId;
        private int endId;

        /**
         * @param textView          The TextView
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receiver
         *                          {@link #onTick(long)} callbacks.
         */
        public CountDownTimerUtils(TextView textView, long millisInFuture, long countDownInterval, int startId, int endId) {
            super(millisInFuture, countDownInterval);
            this.mTextView = textView;
            this.startId = startId;
            this.endId = endId;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTextView.setClickable(false); //设置不可点击
            mTextView.setTextColor(Color.parseColor("#FFFFFF"));
            mTextView.setBackgroundResource(startId);
            mTextView.setText(millisUntilFinished / 1000 + "秒后重发");  //设置倒计时时间
        }

        @Override
        public void onFinish() {
            mTextView.setText("再次获取验证码");
            mTextView.setClickable(true);//重新获得点击
            mTextView.setTextColor(Color.parseColor("#FFFFFF"));
            mTextView.setBackgroundResource(endId);
        }
    }

    /**
     * 通过包名 在应用商店打开应用
     *
     * @param packageName 包名
     */
    public static void openApplicationMarket(String packageName) {
        try {
            String str = "market://details?id=" + packageName;
            Intent localIntent = new Intent(Intent.ACTION_VIEW);
            localIntent.setData(Uri.parse(str));
            startActivity(localIntent);
        } catch (Exception e) {
            // 打开应用商店失败 可能是没有手机没有安装应用市场
            e.printStackTrace();
            // 调用系统浏览器进入商城
            String url = "http://app.mi.com/detail/163525?ref=search";
            openLinkBySystem(url);
        }
    }

    /**
     * 调用系统浏览器打开网页
     *
     * @param url 地址
     */
    public static void openLinkBySystem(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }


    /**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 包括空格判断
     *
     * @param input
     * @return
     */
    public static boolean containSpace(CharSequence input) {
        return Pattern.compile("\\s+").matcher(input).find();
    }

    /**
     * 判断真实姓名
     */
    public static boolean isNmae(String name) {
        if (!TextUtils.isEmpty(name)) {
            Matcher matcher = Pattern.compile("^([\\u4E00-\\u9FA5]+|[a-zA-Z]+)$").matcher(name);
            return matcher.matches();
        } else {
            return false;
        }
    }

    public static String getAsetToken() {
        if (BearMallAplication.getInstance() != null
                && BearMallAplication.getInstance().getUser() != null
                && BearMallAplication.getInstance().getUser().getData() != null
                && BearMallAplication.getInstance().getUser().getData().getToken() != null
                && BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() != null) {
            return BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token();
        }
        return "";
    }


    public static final int NETWORK_CLASS_UNKNOWN = 0;

    /**
     * wifi net work
     */
    public static final int NETWORK_WIFI = 1;

    /**
     * "2G" networks
     */
    public static final int NETWORK_CLASS_2_G = 2;

    /**
     * "3G" networks
     */
    public static final int NETWORK_CLASS_3_G = 3;

    /**
     * "4G" networks
     */
    public static final int NETWORK_CLASS_4_G = 4;


    public static int getNetWorkClass(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORK_CLASS_2_G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NETWORK_CLASS_3_G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NETWORK_CLASS_4_G;
            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }

    public static int getNetWorkStatus(Context context) {
        int netWorkType = NETWORK_CLASS_UNKNOWN;
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();

            if (type == ConnectivityManager.TYPE_WIFI) {
                netWorkType = NETWORK_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = getNetWorkClass(context);
            }
        }
        return netWorkType;
    }

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_SCHOOL_TIME = 500;
    private static long clickTime;

    public static boolean isSchoolClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - clickTime) >= MIN_SCHOOL_TIME) {
            flag = true;
        }
        clickTime = curClickTime;
        return flag;
    }

}

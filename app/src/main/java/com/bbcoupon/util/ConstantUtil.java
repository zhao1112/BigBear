package com.bbcoupon.util;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.util.CommonUtils;

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


}

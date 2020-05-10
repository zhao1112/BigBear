package com.bbcoupon.util;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.yunqin.bearmall.widget.OpenGoodsDetail;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.util
 * @DATE 2020/4/27
 */
public class WindowUtils {

    private static PopupWindow popupWindow;
    private static View viewContent;
    private static PopupWindow popupWindowOnly;


    /**
     * activity
     * view  布局view
     * position popupWindow显示位置 0顶部 1中部 2底部
     * animaton 动画
     */
    public static PopupWindow timeShowOnly(Activity activity, int view, int animaton, int position) {
        if (popupWindowOnly == null) {
            popupWindowOnly = new PopupWindow();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout linearLayout = new LinearLayout(activity);
            viewContent = inflater.inflate(view, linearLayout);
            popupWindowOnly.setContentView(viewContent);
            popupWindowOnly.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindowOnly.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindowOnly.setAnimationStyle(animaton);
        }
        switch (position) {
            case 0:
                popupWindowOnly.showAtLocation(activity.getWindow().getDecorView(), Gravity.TOP, 0, 0);
                break;
            case 1:
                popupWindowOnly.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                break;
            case 2:
                popupWindowOnly.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            default:
                popupWindowOnly.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                break;
        }

        handlerOnly.removeMessages(1);
        handlerOnly.sendEmptyMessageDelayed(1, 4000);

        return popupWindowOnly;
    }


    /**
     * activity
     * view  布局view
     * position popupWindow显示位置 0顶部 1中部 2底部
     * animaton 动画
     */
    public static View timeShow(Activity activity, int view, int animaton, int position) {
        popupWindow = new PopupWindow();
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = new LinearLayout(activity);
        viewContent = inflater.inflate(view, linearLayout);
        popupWindow.setContentView(viewContent);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(animaton);
        switch (position) {
            case 0:
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.TOP, 0, 0);
                break;
            case 1:
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                break;
            case 2:
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            default:
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                break;
        }

        handler.removeMessages(1);
        handler.sendEmptyMessageDelayed(1, 4000);

        return viewContent;
    }


    /**
     * activity
     * view  布局view
     * position popupWindow显示位置 0顶部 1中部 2底部
     */
    public static View ShowBrightness(Activity activity, int view, int position) {
        popupWindow = new PopupWindow();
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = new LinearLayout(activity);
        viewContent = inflater.inflate(view, linearLayout);
        popupWindow.setContentView(viewContent);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        dimBackground(activity, 1.0f, 0.5f);
        switch (position) {
            case 0:
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.TOP, 0, 0);
                break;
            case 1:
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                break;
            case 2:
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            default:
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                break;
        }
        return viewContent;
    }

    /**
     * activity
     * view  布局view
     * position popupWindow显示位置 0顶部 1中部 2底部
     */
    public static View Show(Activity activity, int view, int position) {
        popupWindow = new PopupWindow();
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = new LinearLayout(activity);
        viewContent = inflater.inflate(view, linearLayout);
        popupWindow.setContentView(viewContent);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        switch (position) {
            case 0:
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.TOP, 0, 0);
                break;
            case 1:
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                break;
            case 2:
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            default:
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                break;
        }
        return viewContent;
    }



    /**
     * activity
     * view  布局view
     * position popupWindow显示位置 0顶部 1中部 2底部
     * animaton 动画
     */
    public static View ShowVirtual(Activity activity, int view ,int position) {
        popupWindow = new PopupWindow();
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = new LinearLayout(activity);
        viewContent = inflater.inflate(view, linearLayout);
        popupWindow.setContentView(viewContent);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        dimBackground(activity, 1.0f, 0.1f);
        switch (position) {
            case 0:
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.TOP, 0, 0);
                break;
            case 1:
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                break;
            case 2:
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            default:
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                break;
        }
        return viewContent;
    }

    /**
     * 关闭PopupWindow
     */
    public static void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    /**
     * 关闭PopupWindow
     */
    public static void dismissOnly() {
        if (popupWindowOnly != null && popupWindowOnly.isShowing()) {
            popupWindowOnly.dismiss();
        }
    }


    /**
     * 关闭虚化PopupWindow
     */
    public static void dismissBrightness(Activity activity) {
        if (popupWindow != null && popupWindow.isShowing()) {
            dimBackground(activity, 0.5f, 1.0f);
            popupWindow.dismiss();
        }
    }


    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            popupWindow.dismiss();
        }
    };

    private static Handler handlerOnly = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            popupWindowOnly.dismiss();
        }
    };


    /**
     * 设置屏幕亮度
     */
    public static void dimBackground(Activity activity, final float from, final float to) {
        final Window window = activity.getWindow();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.alpha = (Float) animation.getAnimatedValue();
                window.setAttributes(params);
            }
        });
        valueAnimator.start();
    }



}

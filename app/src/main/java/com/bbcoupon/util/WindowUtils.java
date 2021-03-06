package com.bbcoupon.util;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.newversions.tbk.activity.GoodsDetailContract;
import com.yunqin.bearmall.R;


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
    public static PopupWindow timeShow(Activity activity, int view, int animaton, int position) {
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
        dimBackground(activity, 1.0f, 0.3f);
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

        return popupWindowOnly;
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
        dimBackground(activity, 1.0f, 0.3f);
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
    public static PopupWindow ShowVirtual(Activity activity, int view, int animaton, int position) {
        dimBackground(activity, 1.0f, 0.2f);
        popupWindow = new PopupWindow();
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = new LinearLayout(activity);
        viewContent = inflater.inflate(view, linearLayout);
        popupWindow.setContentView(viewContent);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
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
        return popupWindow;
    }

    /**
     * activity
     * view  布局view
     * position popupWindow显示位置 0顶部 1中部 2底部
     * animaton 动画
     */
    public static PopupWindow ShowSplansh(Activity activity, int view, int animaton, int position) {
        popupWindow = new PopupWindow();
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = new LinearLayout(activity);
        viewContent = inflater.inflate(view, linearLayout);
        popupWindow.setContentView(viewContent);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
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
        return popupWindow;
    }

    /**
     * activity
     * view  布局view
     * position popupWindow显示位置 0顶部 1中部 2底部
     * animaton 动画
     */
    @SuppressLint("WrongConstant")
    public static PopupWindow ShowSex(Activity activity, int view, View position) {
        dimBackground(activity, 1.0f, 0.2f);
        View viewContent = LayoutInflater.from(activity).inflate(view, null);
        popupWindow = new PopupWindow(viewContent,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setFocusable(true);
        // 设置点击其他地方就消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.bottom_animation);
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAtLocation(position, Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimBackground(activity, 0.5f, 1.0f);
            }
        });
        return popupWindow;
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

    /**
     * 关闭虚化PopupWindow
     */
    public static void dismissOnly(Activity activity) {
        if (popupWindowOnly != null && popupWindowOnly.isShowing()) {
            dimBackground(activity, 0.5f, 1.0f);
            popupWindowOnly.dismiss();
        }
    }


    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (popupWindow != null && popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }
    };

    private static Handler handlerOnly = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (popupWindowOnly != null && popupWindowOnly.isShowing()) {
                popupWindowOnly.dismiss();
            }
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

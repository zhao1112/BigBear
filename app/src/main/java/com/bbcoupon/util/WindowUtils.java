package com.bbcoupon.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.ui.activity.BCMessageActivity;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.util
 * @DATE 2020/4/27
 */
public class WindowUtils {

    private static PopupWindow popupWindow;
    private static View viewContent;

    /**
     * activity
     * view  布局view
     * position popupWindow显示位置 0顶部 1中部 2底部
     * animaton 动画
     */
    public static View timeShow(Activity activity, int view, int animaton, int position) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout linearLayout = new LinearLayout(activity);
            viewContent = inflater.inflate(view, linearLayout);
            popupWindow.setContentView(viewContent);
            popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setAnimationStyle(animaton);
        }
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
     * 关闭PopupWindow
     */
    public static void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
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

}

package com.yunqin.bearmall.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.yunqin.bearmall.widget.OpenGoodsDetail;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.util
 * @DATE 2020/3/31
 */
public class PopUtil2 {

    private static PopUtil2 popUtil = null;
    public Context context;
    private PopupWindow popupWindow;

    public static PopUtil2 getInstance() {
        if (popUtil == null) {
            synchronized (PopUtil2.class) {
                if (popUtil == null) {
                    popUtil = new PopUtil2();
                }
            }
        }
        return popUtil;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public void dismissPopupWindow() {
        popupWindow.dismiss();
    }

    public View getPopView2(int viewID, int position) {
        View view = LayoutInflater.from(context).inflate(viewID, null);
        popupWindow = new PopupWindow();
        popupWindow.setContentView(view);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        // 设置背景图片， 必须设置，不然动画没作用
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(false);
        // 设置点击popupwindow外屏幕其它地方消失
        popupWindow.setOutsideTouchable(true);
        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        if (position == 0) {
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        }
        if (position == 1) {
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        return view;
    }
}

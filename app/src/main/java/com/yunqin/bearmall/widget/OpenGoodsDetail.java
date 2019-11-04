package com.yunqin.bearmall.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.yunqin.bearmall.R;


/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.widget
 * @DATE 2019/11/4
 */
public class OpenGoodsDetail {

    public static void showDialog(Context context) {
        lightoff((Activity) context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_first_search, null);
        PopupWindow mPopupWindow = new PopupWindow();
        mPopupWindow.setContentView(view);
        mPopupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置背景图片， 必须设置，不然动画没作用
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        // 设置点击popupwindow外屏幕其它地方消失
        mPopupWindow.setOutsideTouchable(true);
        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        mPopupWindow.showAtLocation(view, Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        view.findViewById(R.id.sc_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lighton((Activity) context);
                mPopupWindow.dismiss();
            }
        });
        view.findViewById(R.id.se_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lighton((Activity) context);
                mPopupWindow.dismiss();
            }
        });
    }

    //设置手机屏幕亮度变暗
    private static void lightoff(Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.2f;
        activity.getWindow().setAttributes(lp);
    }

    //设置手机屏幕亮度显示正常
    private static void lighton(Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 1f;
        activity.getWindow().setAttributes(lp);
    }

}

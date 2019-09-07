package com.yunqin.bearmall.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yunqin.bearmall.R;

public class ToastUtils {

    public static void showToast(Context context, String message){

        // 创建土司
        Toast toast = new Toast(context);
        // 找到toast布局的位置
        View layout = View.inflate(context, R.layout.toast_add_cart,null);
        TextView tv_message = layout.findViewById(R.id.toast_message);
        tv_message.setText(message);
        // 设置toast文本，把设置好的布局传进来
        toast.setView(layout);
        toast.setDuration(3*1000);
        // 设置土司显示在屏幕的位置
        toast.setGravity(Gravity.CENTER,0,0);
        // 显示土司
        toast.show();
    }
}

package com.yunqin.bearmall.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yunqin.bearmall.R;

/**
 * @author AYWang
 * @create 2018/8/9
 * @Describe
 */
public class ToastGetBC {
    private static Toast toast;
    private static Context context;

    public static ToastGetBC  getInstence(){
        return  new ToastGetBC();
    }

    private static Toast getToast(Context mContext){
        context = mContext;
        if(toast == null){
            toast = new Toast(mContext);
        }
        return toast;
    }

    public static void show(Context mContext,String howBc){
        getToast(mContext);
        View view = LayoutInflater.from(context).inflate(R.layout.toast_get_bc,null);
        TextView textView = view.findViewById(R.id.get_how);
        textView.setText(howBc);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

}

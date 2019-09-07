package com.yunqin.bearmall.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunqin.bearmall.R;

public class CustomerServiceDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private String phone_str;
    private TextView phone, wechat_and_qq;
    private Button call_phone;
    private RelativeLayout cancel;

    public CustomerServiceDialog(@NonNull Context context, String phone_str, String qq) {
        super(context, R.style.ProductDialog);

        mContext = context;
        this.phone_str = phone_str;

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_customer_service, null);
        phone = view.findViewById(R.id.dialog_customer_service_phone);
        wechat_and_qq = view.findViewById(R.id.dialog_customer_service_wechat_and_qq);
        call_phone = view.findViewById(R.id.dialog_customer_service_call_phone);
        cancel = view.findViewById(R.id.dialog_cancel_layout);

        call_phone.setOnClickListener(this);
        cancel.setOnClickListener(this);

        if (!phone_str.equals("")) {
            phone.setText("TEL：" + phone_str);
        } else {
            phone.setText("");
        }

        if (!qq.equals("")) {
            wechat_and_qq.setText("QQ：" + qq);
        } else {
            wechat_and_qq.setText("");
        }


        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        WindowManager m = ((Activity) context).getWindowManager();
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
//        dialogWindow.setWindowAnimations(R.style.DialogBottomTranslate); //设置窗口弹出动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        Display d = m.getDefaultDisplay();
        lp.width = (int) (d.getWidth() * 0.8);
        lp.height = (int) (d.getHeight() * 0.25);
        dialogWindow.setAttributes(lp);
        show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_customer_service_call_phone:
                cancel();

                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + phone_str);
                intent.setData(data);
                mContext.startActivity(intent);


                /*Uri uri = Uri.parse("tel:" + phone_str);
                Intent intent = new Intent(Intent.ACTION_CALL, uri);
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mContext.startActivity(intent);*/
                break;

            case R.id.dialog_cancel_layout:
                cancel();

                break;

            default:
                break;
        }
    }

}

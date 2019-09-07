package com.yunqin.bearmall.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.AddressListBean;

public class AffirmAddressDialog extends Dialog implements View.OnClickListener{
    private TextView addressTextView;
    private TextView cancelTextView,okTextView;


    public AffirmAddressDialog(@NonNull Context context,AddressListBean.DataBean receiver) {
        super(context, R.style.ProductDialog);


        View view = LayoutInflater.from(context).inflate(R.layout.dialog_affirm_address,null);
        addressTextView = view.findViewById(R.id.dialog_address_tv);
        cancelTextView = view.findViewById(R.id.dialog_cancel_tv);
        okTextView = view.findViewById(R.id.dialog_ok_tv);

        addressTextView.setText(receiver.getConsignee()+","+receiver.getPhone()+","+receiver.getAreaName()+receiver.getAddress());

        cancelTextView.setOnClickListener(this);
        okTextView.setOnClickListener(this);

        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        WindowManager m = ((Activity)context).getWindowManager();
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
//        dialogWindow.setWindowAnimations(R.style.DialogBottomTranslate); //设置窗口弹出动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        Display d = m.getDefaultDisplay();
        lp.width = (int) (d.getWidth() * 0.8);
        lp.height = (int) (d.getHeight() * 0.3);
        dialogWindow.setAttributes(lp);
        show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_cancel_tv:
                if(onShowAddressDetailListenter != null){
                    onShowAddressDetailListenter.onShowAddressDetail(0);
                }
                cancel();
                break;

            case R.id.dialog_ok_tv:
                if(onShowAddressDetailListenter != null){
                    onShowAddressDetailListenter.onShowAddressDetail(1);
                }
                cancel();
                break;

                default:
                    break;
        }
    }

    OnShowAddressDetailListenter onShowAddressDetailListenter;
    public interface OnShowAddressDetailListenter{
        void onShowAddressDetail(int flag);
    }

    public void setOnShowAddressDetailListenter(OnShowAddressDetailListenter onShowAddressDetailListenter) {
        this.onShowAddressDetailListenter = onShowAddressDetailListenter;
    }
}

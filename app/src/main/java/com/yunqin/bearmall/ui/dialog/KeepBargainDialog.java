package com.yunqin.bearmall.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.yunqin.bearmall.bean.AddressListBean;
import com.yunqin.bearmall.bean.BargainProductListBean;
import com.yunqin.bearmall.ui.activity.BargainFreeActivity;
import com.yunqin.bearmall.ui.activity.BargainFreeShareActivity;

public class KeepBargainDialog extends Dialog implements View.OnClickListener{
    private RelativeLayout cancelLayout;
    private Button okTextView;
    private Context mContext;
    private BargainProductListBean.BargainProductList bargainProductList;


    public KeepBargainDialog(@NonNull Context context, BargainProductListBean.BargainProductList bargainProductList) {
        super(context, R.style.ProductDialog);
        mContext = context;
        this.bargainProductList = bargainProductList;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_keep_bargain,null);
        cancelLayout = view.findViewById(R.id.dialog_cancel_layout);
        okTextView = view.findViewById(R.id.dialog_keep_bargain_bt);


        cancelLayout.setOnClickListener(this);
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
        lp.height = (int) (d.getHeight() * 0.22);
        dialogWindow.setAttributes(lp);

    }

    public void showDialog(){
        show();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.dialog_cancel_layout:
                cancel();
                break;

            case R.id.dialog_keep_bargain_bt:
                if(bargainProductList == null){
                    intent = new Intent();
                    ((Activity)mContext).setResult(1000,intent);
                    ((Activity)mContext).finish();
                }else{
                    intent = new Intent(mContext,BargainFreeShareActivity.class);
                    intent.putExtra("bargainRecord_id",bargainProductList.getBargainRecord_id());
                    mContext.startActivity(intent);
                }

                cancel();
                break;

                default:
                    break;
        }
    }

//    private OnKeepBargainListener onShowAddressDetailListenter;
//    public interface OnKeepBargainListener{
//        void onKeepBargain();
//    }
//
//    public void setOnShowAddressDetailListenter(OnKeepBargainListener onShowAddressDetailListenter) {
//        this.onShowAddressDetailListenter = onShowAddressDetailListenter;
//    }
}

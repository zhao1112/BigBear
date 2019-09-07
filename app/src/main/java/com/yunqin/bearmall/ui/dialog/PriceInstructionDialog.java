package com.yunqin.bearmall.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseDialog;

public class PriceInstructionDialog extends BaseDialog{
    private Context mContext;

    public PriceInstructionDialog(@NonNull Context context) {
        super(context, R.style.ProductDialog);
        mContext = context;

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_price_instruction,null);

        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        WindowManager m = ((Activity)context).getWindowManager();
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.DialogBottomTranslate); //设置窗口弹出动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        Display d = m.getDefaultDisplay();
        lp.width = (int) (d.getWidth() * 1);
        lp.height = (int) (d.getHeight() * 0.54);
        dialogWindow.setAttributes(lp);
        show();
    }
}

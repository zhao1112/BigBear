package com.bbcoupon.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.widget.OpenGoodsDetail;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.util
 * @DATE 2020/4/21
 */
public class DialogUtil {

    private static DialogUtil dialogUtil = null;
    public Context context;
    private Dialog dialog;

    public static DialogUtil getInstance() {
        if (dialogUtil == null) {
            synchronized (DialogUtil.class) {
                if (dialogUtil == null) {
                    dialogUtil = new DialogUtil();
                }
            }
        }
        return dialogUtil;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public View getDialog(int viewId) {
        dialog = new Dialog(context, R.style.ProductDialog);
        dialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(context).inflate(viewId, null);
        dialog.setContentView(view);
        dialog.show();
        return view;
    }

    public void dismissDialog() {
        dialog.dismiss();
    }


}

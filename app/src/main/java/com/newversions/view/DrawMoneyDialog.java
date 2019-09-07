package com.newversions.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.newversions.passwd.IMD5;
import com.yunqin.bearmall.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Master
 * On 2019/1/22 10:40
 */
public class DrawMoneyDialog extends Dialog implements View.OnClickListener {

    private static Context iContext;

    // dialog 动画
    private static int iAnimationResId;

    // Dialog 相对页面显示的位置
    private static int iPosition = 0;

    // 默认点击空白区域消失
    private static boolean iCanceledOnTouchOutside = true;

    // 默认点击返回键消失
    private static boolean iCancelable = true;

    // 回掉自定义接口
    private static OnDialogItemClickListener iOnDialogItemClickListener;


    private static String dialogPayPrice;
    private static String dialogPaytip;


    private TextView tv_pass1;
    private TextView tv_pass2;
    private TextView tv_pass3;
    private TextView tv_pass4;
    private TextView tv_pass5;
    private TextView tv_pass6;
    private List<TextView> pwdTvs;

    public interface OnDialogItemClickListener {
        void onDialogItemClick(DrawMoneyDialog thisDialog, String pwd);
    }


    public DrawMoneyDialog() {
        super(iContext, R.style.ProductDialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (0 == iPosition) {
            // 默认居中
            window.setGravity(Gravity.CENTER);
        } else {
            // 设置要显示的位置
            window.setGravity(iPosition);
        }


        setContentView(R.layout.tixian_dialog_pay_layout);
        setCanceledOnTouchOutside(iCanceledOnTouchOutside);
        setCancelable(iCancelable);

        if (null != dialogPaytip && !"".equals(dialogPaytip)) {
            TextView title = findViewById(R.id.tip);
            title.setText(dialogPaytip);
        }

        if (null != dialogPayPrice && !"".equals(dialogPayPrice)) {
            TextView title = findViewById(R.id.dialog_pay_price);
            title.setText(dialogPayPrice);
        }

        WindowManager windowManager = ((Activity) iContext).getWindowManager();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        Display display = windowManager.getDefaultDisplay();
        layoutParams.width = display.getWidth() * 1;
        window.setAttributes(layoutParams);
        findViews();

    }

    private void findViews() {
        stringBuffer = new StringBuffer();
        pwdTvs = new ArrayList<>();
        tv_pass1 = findViewById(R.id.tv_pass1);
        tv_pass2 = findViewById(R.id.tv_pass2);
        tv_pass3 = findViewById(R.id.tv_pass3);
        tv_pass4 = findViewById(R.id.tv_pass4);
        tv_pass5 = findViewById(R.id.tv_pass5);
        tv_pass6 = findViewById(R.id.tv_pass6);
        pwdTvs.add(tv_pass1);
        pwdTvs.add(tv_pass2);
        pwdTvs.add(tv_pass3);
        pwdTvs.add(tv_pass4);
        pwdTvs.add(tv_pass5);
        pwdTvs.add(tv_pass6);
        findViewById(R.id.keyboard_0).setOnClickListener(this);
        findViewById(R.id.keyboard_1).setOnClickListener(this);
        findViewById(R.id.keyboard_2).setOnClickListener(this);
        findViewById(R.id.keyboard_3).setOnClickListener(this);
        findViewById(R.id.keyboard_4).setOnClickListener(this);
        findViewById(R.id.keyboard_5).setOnClickListener(this);
        findViewById(R.id.keyboard_6).setOnClickListener(this);
        findViewById(R.id.keyboard_7).setOnClickListener(this);
        findViewById(R.id.keyboard_8).setOnClickListener(this);
        findViewById(R.id.keyboard_9).setOnClickListener(this);
        findViewById(R.id.keyboard_del).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.keyboard_0:
                assemblePwd(0);
                break;
            case R.id.keyboard_1:
                assemblePwd(1);
                break;
            case R.id.keyboard_2:
                assemblePwd(2);
                break;
            case R.id.keyboard_3:
                assemblePwd(3);
                break;
            case R.id.keyboard_4:
                assemblePwd(4);
                break;
            case R.id.keyboard_5:
                assemblePwd(5);
                break;
            case R.id.keyboard_6:
                assemblePwd(6);
                break;
            case R.id.keyboard_7:
                assemblePwd(7);
                break;
            case R.id.keyboard_8:
                assemblePwd(8);
                break;
            case R.id.keyboard_9:
                assemblePwd(9);
                break;
            case R.id.keyboard_del:
                assemblePwd(-1);
                break;
            default:
                break;
        }

    }


    private StringBuffer stringBuffer;

    private void assemblePwd(int pwd) {
        if (pwd == -1) {
            if (stringBuffer.length() > 0) {
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            }
            setPwd(stringBuffer);
        } else {

            if (stringBuffer.length() == 6) {
                return;
            }
            stringBuffer.append(pwd);
            setPwd(stringBuffer);

            if (stringBuffer.length() == 6) {
                String data = IMD5.md5(stringBuffer.toString());
                iOnDialogItemClickListener.onDialogItemClick(this, data);
            }
        }
    }


    public void failPwd() {
        resetPwd();
        stringBuffer.setLength(0);
    }

    private void setPwd(StringBuffer stringBuffer) {
        resetPwd();
        for (int i = 0; i < stringBuffer.length(); i++) {
            pwdTvs.get(i).setText("1");
        }
    }

    private void resetPwd() {
        for (int i = 0; i < pwdTvs.size(); i++) {
            pwdTvs.get(i).setText("");
        }
    }

    public static class Builder {

        public Builder(Context context) {
            iContext = context;
        }


        public Builder setAnimationResId(int animationResId) {
            iAnimationResId = animationResId;
            return this;
        }

        public Builder setDialogPosition(int position) {
            iPosition = position;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            iCanceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            iCancelable = cancelable;
            return this;
        }

        public Builder setOnDialogItemClickListener(OnDialogItemClickListener onDialogItemClickListener) {
            iOnDialogItemClickListener = onDialogItemClickListener;
            return this;
        }


        public Builder setOnDialogContent(String dialogContent) {
            dialogPaytip = dialogContent;
            return this;
        }

        public Builder setOnDialogPrice(String dialogPrice) {
            dialogPayPrice = dialogPrice;
            return this;
        }


        public DrawMoneyDialog build() {
            return new DrawMoneyDialog();
        }
    }


}
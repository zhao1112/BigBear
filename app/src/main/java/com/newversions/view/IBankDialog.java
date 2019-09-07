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
import android.widget.LinearLayout;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.BankBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Master
 * On 2019/1/22 10:40
 */
public class IBankDialog extends Dialog implements View.OnClickListener {

    private static Context iContext;
    private static int[] iListenedItems;

    private List<BankCardView> views;

    // Dialog 相对页面显示的位置
    private static int iPosition = 0;

    // 默认点击空白区域消失
    private static boolean iCanceledOnTouchOutside = true;

    // 默认点击返回键消失
    private static boolean iCancelable = true;

    // 回掉自定义接口
    private static OnDialogItemClickListener iOnDialogItemClickListener;


    private static List<BankBean.DataBean.ListBean> iListBeans;
    private static int selectPosition = 0;

    public interface OnDialogItemClickListener {
        void onDialogItemClick(IBankDialog thisDialog, View clickView);
    }


    public IBankDialog() {
        super(iContext, R.style.ProductDialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        views = new ArrayList<>();
        Window window = getWindow();
        if (0 == iPosition) {
            // 默认居中
            window.setGravity(Gravity.CENTER);
        } else {
            // 设置要显示的位置
            window.setGravity(iPosition);
        }


        // 添加默认动画
        window.setWindowAnimations(R.style.DialogBottomTranslate);
        setContentView(R.layout.bank_list_layout);

        setCanceledOnTouchOutside(iCanceledOnTouchOutside);
        setCancelable(iCancelable);

        for (int id : iListenedItems) {
            findViewById(id).setOnClickListener(this);
        }


        LinearLayout linearLayout = findViewById(R.id.bank_container);

        for (int i = 0; i < iListBeans.size(); i++) {
            BankBean.DataBean.ListBean listBean = iListBeans.get(i);
            BankCardView bankCardView = new BankCardView(iContext);
            bankCardView.setTag(i);
            bankCardView.setData(listBean.getIcon(), String.format("%s(%s)", listBean.getBankName(), listBean.getBankCard().substring(listBean.getBankCard().length() - 4)));
            if (i == selectPosition) {
                bankCardView.setChecked(true);
            } else {
                bankCardView.setChecked(false);
            }
            views.add(bankCardView);

            bankCardView.setOnClickListener(this);
            linearLayout.addView(bankCardView);
        }

        WindowManager windowManager = ((Activity) iContext).getWindowManager();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        Display display = windowManager.getDefaultDisplay();
        layoutParams.width = display.getWidth() * 1;
        window.setAttributes(layoutParams);


    }

    @Override
    public void onClick(View view) {
        if (iOnDialogItemClickListener != null) {
            iOnDialogItemClickListener.onDialogItemClick(this, view);
        }
    }


    public static class Builder {

        public Builder(Context context) {
            iContext = context;
            iListenedItems = new int[]{};
            iListBeans = new ArrayList<>();
        }

        public Builder setBankData(List<BankBean.DataBean.ListBean> listBeans) {
            iListBeans = listBeans;
            return this;
        }

        public Builder setSelectPosition(int position) {
            selectPosition = position;
            return this;
        }


        public Builder setListenedItems(int[] listenedItems) {
            iListenedItems = listenedItems;
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


        public IBankDialog build() {
            return new IBankDialog();
        }
    }


}
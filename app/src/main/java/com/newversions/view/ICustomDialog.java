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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Create By Master
 * On 2019/1/22 10:40
 */
public class ICustomDialog extends Dialog implements View.OnClickListener {

    private static Context iContext;
    private static int iLayoutResId;
    private static int[] iListenedItems;
    private static int[] iCustomTextIds;
    private static String[] iCustomTextContents;

    // dialog 动画
    private static int iAnimationResId;

    // Dialog 相对页面显示的位置
    private static int iPosition = 0;

    // 默认点击空白区域消失
    private static boolean iCanceledOnTouchOutside = true;
    private static boolean iWindow = false;

    // 默认点击返回键消失
    private static boolean iCancelable = true;

    private static Map<Integer, String> imgResource;

    // 回掉自定义接口
    private static OnDialogItemClickListener iOnDialogItemClickListener;


    public interface OnDialogItemClickListener {
        void onDialogItemClick(ICustomDialog thisDialog, View clickView);
    }


    public ICustomDialog() {
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

        if (iAnimationResId == 0) {
            // 添加默认动画
            window.setWindowAnimations(R.style.DialogBottomTranslate);
        } else {
            // 添加自定义动画
            window.setWindowAnimations(iAnimationResId);
        }
        setContentView(iLayoutResId);

        setCanceledOnTouchOutside(iCanceledOnTouchOutside);
        setCancelable(iCancelable);

        for (int id : iListenedItems) {
            findViewById(id).setOnClickListener(this);
        }

        for (int i = 0; i < iCustomTextIds.length; i++) {
            TextView textView = findViewById(iCustomTextIds[i]);
            textView.setText(iCustomTextContents[i]);
        }


        for (Map.Entry<Integer, String> entry : imgResource.entrySet()) {
            Glide.with(iContext).load(entry.getValue()).into((ImageView) findViewById(entry.getKey()));
        }


        // 是否全屏宽
        if (iWindow) {
            WindowManager windowManager = ((Activity) iContext).getWindowManager();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            Display display = windowManager.getDefaultDisplay();
            layoutParams.width = display.getWidth() * 1;
            window.setAttributes(layoutParams);
        }


    }

    @Override
    public void onClick(View v) {
        if (iOnDialogItemClickListener != null) {
            iOnDialogItemClickListener.onDialogItemClick(this, v);
        }
    }


    public static class Builder {

        public Builder(Context context) {
            iContext = context;
            iListenedItems = new int[]{};
            iCustomTextIds = new int[]{};
            iCustomTextContents = new String[]{};
            iWindow = false;
            imgResource = new HashMap<>();
        }

        public Builder setLayoutResId(int layoutResId) {
            iLayoutResId = layoutResId;
            return this;
        }

        public Builder setListenedItems(int[] listenedItems) {
            iListenedItems = listenedItems;
            return this;
        }

        public Builder setCustomTextIds(int[] custonTextIds) {
            iCustomTextIds = custonTextIds;
            return this;
        }

        public Builder setCustomTextContents(String[] customContents) {
            iCustomTextContents = customContents;
            return this;
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


        public Builder setWindow(boolean window) {
            iWindow = window;
            return this;
        }

        public Builder setImageViewResource(int id, String url) {
            imgResource.put(id, url);
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

        public ICustomDialog build() {
            return new ICustomDialog();
        }
    }


}
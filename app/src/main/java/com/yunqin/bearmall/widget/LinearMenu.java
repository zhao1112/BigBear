package com.yunqin.bearmall.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunqin.bearmall.R;


public class LinearMenu extends LinearLayout {

    private ImageView iv_menu;
    private TextView tv_menu;

    public LinearMenu(Context context) {
        super(context);
        init(context);
    }

    public LinearMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LinearMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        setGravity(Gravity.CENTER);

        View menu = LayoutInflater.from(context).inflate(R.layout.main_menu, this, true);
        iv_menu = menu.findViewById(R .id.iv_menu);
        tv_menu = menu.findViewById(R.id.tv_menu);
    }

    public void setAttr(int imgRes, String txt) {
        iv_menu.setImageResource(imgRes);
        tv_menu.setText(txt);
    }
}

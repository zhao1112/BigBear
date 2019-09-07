package com.yunqin.bearmall.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunqin.bearmall.R;

public class MineVipItemView extends FrameLayout {

    private int imageSrc;
    private String centerText;

    public MineVipItemView(@NonNull Context context) {
        this(context,null);
    }

    public MineVipItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MineVipItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MineVipItemView);
        imageSrc = typedArray.getResourceId(R.styleable.MineVipItemView_imageSrc,0);
//        imageSrc = R.drawable.icon_fac;
        centerText = typedArray.getString(R.styleable.MineVipItemView_centerText);
        typedArray.recycle();

        addItemView();
    }

    private void addItemView(){

        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_vip_hori_item,this,false);
        TextView textView = view.findViewById(R.id.center_text);
        if (!TextUtils.isEmpty(centerText)){
            textView.setText(centerText);
        }
        ImageView imageView = view.findViewById(R.id.left_icon);
        if (imageSrc > 0){
            imageView.setImageResource(imageSrc);
        }
        addView(view);


    }


}

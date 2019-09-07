package com.yunqin.bearmall.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author AYWang
 * @create 2018/7/21
 * @Describe
 */
public class LinTextView extends android.support.v7.widget.AppCompatTextView {
    public LinTextView(Context context) {
        super(context);
    }

    public LinTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            sb.append(text.charAt(i));
            if (i<text.length()-1){
                sb.append("\n");
            }

        }
        super.setText(sb.toString(), type);
    }
}

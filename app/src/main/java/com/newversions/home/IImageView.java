package com.newversions.home;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Create By Master
 * On 2019/1/8 17:41
 */
public class IImageView extends ImageView {


    private Object object;

    public void setITag(Object object) {
        this.object = object;
    }

    public Object getITag() {
        return object;
    }


    public IImageView(Context context) {
        super(context);
    }

    public IImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}

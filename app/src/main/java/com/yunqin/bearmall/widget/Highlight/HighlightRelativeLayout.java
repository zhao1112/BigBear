package com.yunqin.bearmall.widget.Highlight;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class HighlightRelativeLayout extends RelativeLayout
{
    public HighlightRelativeLayout(final Context context, final AttributeSet attrs, final int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public HighlightRelativeLayout(final Context context, final AttributeSet attrs)
    {
        super(context, attrs);
    }

    public HighlightRelativeLayout(final Context context)
    {
        super(context);
    }
    
    @Override
    public void setBackgroundDrawable(Drawable d)
    {
        if(d == null)
        {
            super.setBackgroundDrawable(d);
        }
        else
        {
        	HighlightDrawable layer = new HighlightDrawable(d);
			layer.pressedFilter = new LightingColorFilter(
					Color.rgb(238, 238, 238), 1);
			super.setBackgroundDrawable(layer);
        }
    }


    @TargetApi(16)
    public void setBackground(Drawable d)
    {
        if(d == null)
        {
            super.setBackground(d);
        }
        else
        {
            HighlightDrawable layer = new HighlightDrawable(d);
            layer.pressedFilter = new LightingColorFilter(
                    Color.rgb(238, 238, 238), 1);
            super.setBackground(layer);
        }
    }
}

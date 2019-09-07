package com.yunqin.bearmall.widget.Highlight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageButton;

@SuppressLint("AppCompatCustomView")
public class HighlightImageButton extends ImageButton
{
    public HighlightImageButton(final Context context, final AttributeSet attrs, final int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public HighlightImageButton(final Context context, final AttributeSet attrs)
    {
        super(context, attrs);
    }

    public HighlightImageButton(final Context context)
    {
        super(context);
    }
    
    @Override
    public void setImageDrawable(Drawable d)
    {
        this.setBackgroundResource(0);
        
        if(d == null)
        {
            super.setImageDrawable(d);
        }
        else
        {
            HighlightDrawable layer = new HighlightDrawable(d);
            super.setImageDrawable(layer);
        }
    }
}

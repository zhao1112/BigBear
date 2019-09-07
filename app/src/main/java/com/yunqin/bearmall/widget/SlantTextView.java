package com.yunqin.bearmall.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.yunqin.bearmall.R;

public class SlantTextView extends View {

    private Paint mBackgroundPaint;
    private Paint mTextPaint;
    private Path bgPath;
    private Path textPath;

    private int backgroundPaintColor ;
    private int textSize;
    private int textColor;
    private int corner;
    private String text;

    public SlantTextView(Context context) {
        this(context,null);
    }

    public SlantTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlantTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.SlantTextView);
        backgroundPaintColor = typedArray.getColor(R.styleable.SlantTextView_backPaintColor,Color.parseColor("#E2A73F"));
        textSize = typedArray.getDimensionPixelSize(R.styleable.SlantTextView_textSize,20);
        textColor = typedArray.getColor(R.styleable.SlantTextView_textColor,Color.BLACK);
        text = typedArray.getString(R.styleable.SlantTextView_text);
        corner = typedArray.getDimensionPixelOffset(R.styleable.SlantTextView_corner,0);
        typedArray.recycle();

        init();
    }

    private void init() {

//        setLayerType(View.LAYER_TYPE_SOFTWARE,null);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(backgroundPaintColor);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        setCommon(mBackgroundPaint);

        mTextPaint = new Paint();
        mTextPaint.setColor(textColor);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        setCommon(mTextPaint);


        bgPath = new Path();
        textPath = new Path();

    }

    private void setCommon(Paint paint){
        paint.setDither(true);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST){
            widthSize = 200;
        }
        if (heightMode == MeasureSpec.AT_MOST){
            heightSize = 200;
        }
        setMeasuredDimension(widthSize,heightSize);
    }

    public void setText(String text){

        this.text = text;
        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        //不带圆角的
//        bgPath.moveTo(0,0);
//        bgPath.lineTo(width,corner);
//        bgPath.lineTo(0,height);
        bgPath.moveTo(0,height);
        bgPath.lineTo(0,corner);
        RectF rectF = new RectF();
        rectF.top = 0;
        rectF.left = 0;
        rectF.bottom = corner;
        rectF.right = corner;
        bgPath.arcTo(rectF,180,90);
        bgPath.lineTo(width,0);
        bgPath.lineTo(0,height);

        bgPath.close();
        canvas.drawPath(bgPath,mBackgroundPaint);
//        canvas.save();

        if (!TextUtils.isEmpty(text)){
            textPath.moveTo(0,height*0.75f);
            textPath.lineTo(width*0.75f,0);
//            canvas.drawPath(textPath,mTextPaint);
            canvas.drawTextOnPath(text,textPath,0,0,mTextPaint);
        }

    }


}

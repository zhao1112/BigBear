package com.yunqin.bearmall.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class ImageCodeUtil {

    private static ImageCodeUtil mCodeUtils;
    private StringBuilder mBuilder = new StringBuilder();
    private Random mRandom = new Random();

    private String content = "";

    //    private static final int DEFAULT_CODE_LENGTH = 6;//验证码的长度  这里是6位
    private static final int DEFAULT_CODE_LENGTH = 4;//验证码的长度  这里是4位
    private static final int DEFAULT_FONT_SIZE = 28;//字体大小
    private static final int DEFAULT_LINE_NUMBER = 5;//多少条干扰线
    private static final int DEFAULT_WIDTH = 168;//默认宽度.图片的总宽
    private static final int DEFAULT_HEIGHT = 64;//默认高度.图片的总高
    private static final int DEFAULT_COLOR_R = 245;//默认背景颜色值
    private static final int DEFAULT_COLOR_B = 246;//默认背景颜色值
    private static final int DEFAULT_COLOR_G = 247;//默认背景颜色值


    private String code;

    public static ImageCodeUtil getInstance() {
        if (mCodeUtils == null) {
            mCodeUtils = new ImageCodeUtil();
        }
        return mCodeUtils;
    }

    public void setImageContent(String content) {
        this.content = content;
    }

    //生成验证码图片  返回类型为bitmap 直接用imageview.setbitmap()即可
    public Bitmap createBitmap(String content) {
        setImageContent(content);

        Bitmap bitmap = Bitmap.createBitmap(DEFAULT_WIDTH, DEFAULT_HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        code = createCode();

        canvas.drawColor(Color.rgb(DEFAULT_COLOR_R, DEFAULT_COLOR_B, DEFAULT_COLOR_G));
        Paint paint = new Paint();
        paint.setTextSize(DEFAULT_FONT_SIZE);
//        paint.setTextAlign(Paint.Align.CENTER);

//        for (int i = 0; i < code.length(); i++) {
        randomTextStyle(paint);
        canvas.drawText(code + "", 40, 40, paint);
//        }

        //干扰线
        for (int i = 0; i < DEFAULT_LINE_NUMBER; i++) {
            drawLine(canvas, paint);
        }

        canvas.save(Canvas.ALL_SAVE_FLAG);//保存
        canvas.restore();
        return bitmap;
    }

    /**
     * 得到图片中的验证码字符串
     *
     * @return
     */
    public String getCode() {
        return code;
    }

    //生成验证码
    public String createCode() {
//        mBuilder.delete(0, mBuilder.length()); //使用之前首先清空内容
//
//        for (int i = 0; i < DEFAULT_CODE_LENGTH; i++) {
//            mBuilder.append(CHARS[mRandom.nextInt(CHARS.length)]);
//        }

        return content;
    }

    //生成干扰线
    private void drawLine(Canvas canvas, Paint paint) {
        int color = randomColor();
        int startX = mRandom.nextInt(DEFAULT_WIDTH);
        int startY = mRandom.nextInt(DEFAULT_HEIGHT);
        int stopX = mRandom.nextInt(DEFAULT_WIDTH);
        int stopY = mRandom.nextInt(DEFAULT_HEIGHT);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    //随机颜色
    private int randomColor() {
        mBuilder.delete(0, mBuilder.length()); //使用之前首先清空内容

        String haxString;
        for (int i = 0; i < 3; i++) {
            haxString = Integer.toHexString(mRandom.nextInt(0xFF));
            if (haxString.length() == 1) {
                haxString = "0" + haxString;
            }

            mBuilder.append(haxString);
        }

        return Color.parseColor("#" + mBuilder.toString());
    }

    //随机文本样式
    private void randomTextStyle(Paint paint) {
        int color = Color.parseColor("#000000");
        paint.setColor(color);
        paint.setFakeBoldText(mRandom.nextBoolean());  //true为粗体，false为非粗体
        float skewX = mRandom.nextInt(11) / 10;
        skewX = mRandom.nextBoolean() ? skewX : -skewX;
        paint.setTextSkewX(skewX); //float类型参数，负数表示右斜，整数左斜
    }

}

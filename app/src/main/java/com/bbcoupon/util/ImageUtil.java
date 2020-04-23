package com.bbcoupon.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.util
 * @DATE 2020/4/22
 */
public class ImageUtil {

    public static Bitmap compoundBitmap(Bitmap BitmapOne, Bitmap BitmapTwo) {
        Bitmap newBitmap = null;
        newBitmap = Bitmap.createBitmap(BitmapOne);
        Canvas canvas = new Canvas(newBitmap);
        Paint paint = new Paint();
        int oneWidth = BitmapOne.getWidth();
        int oneHeight = BitmapOne.getHeight();
        int twoWidth = BitmapTwo.getWidth();
        int twoHeight = BitmapTwo.getHeight();
        BitmapOne.getHeight();
        canvas.drawBitmap(BitmapTwo, (oneWidth / 2) - (twoWidth / 2), (oneHeight / 2) + (twoHeight / 2), paint);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return newBitmap;
    }

    public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

}

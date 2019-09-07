package com.yunqin.bearmall.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

/**
 * Create By Master
 * On 2018/12/14 16:42
 */
public class ImageLoadUt {
    /**
     * 截取指定View为Bitmap
     *
     * @param view
     * @return
     * @throws Throwable
     */
    public static Bitmap captureView(View view) throws Throwable {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }
}

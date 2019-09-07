package com.yunqin.bearmall.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.yunqin.bearmall.R;

import java.text.SimpleDateFormat;

/**
 * Create By Master
 * On 2018/12/17 17:37
 */
public class INotificationUtil {

    /**
     * 显示一个普通的通知
     *
     * @param context
     * @param intent
     */
    public static void showNotification(Context context, Intent intent, String title, String content) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "default");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(getDataId() + "", "default", NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(getDataId() + "");
            notificationManager.createNotificationChannel(channel);
        }
        mBuilder.setContentTitle(title)
                .setContentText(content)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setTicker(content)
                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setContentIntent(PendingIntent.getActivity(context, getDataId(), intent, PendingIntent.FLAG_CANCEL_CURRENT));
        notificationManager.notify(getDataId(), mBuilder.build());
    }


    private static int getDataId() {
        int i = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mmddHHmmss");
            String data = simpleDateFormat.format(System.currentTimeMillis());
            i = Integer.valueOf(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

}

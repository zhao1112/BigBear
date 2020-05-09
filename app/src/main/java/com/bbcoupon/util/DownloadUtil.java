package com.bbcoupon.util;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.util
 * @DATE 2020/5/9
 */
public class DownloadUtil {

    private static final int DOWNLOADCOMPLETE = 0X1;

    public static void onDownLoadImage(String[] imageurl) {
        new DownLoadImage().execute(imageurl);
    }

    public static class DownLoadImage extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                File asking = new File(Environment.getExternalStorageDirectory(), "/SharedCache/");
                if (!asking.exists()) {
                    asking.mkdir();
                }
                for (int i = 0; i < strings.length; i++) {
                    URL url = new URL(strings[i]);
                    //获取连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //循环下载（边读取边存入）
                    BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
                    String fileName = "Wechat_sharing" + i + ".jpg";
                    File file = new File(asking, fileName);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
                    int len = -1;
                    byte[] bytes = new byte[1024];
                    while ((len = bis.read(bytes)) != -1) {
                        bos.write(bytes, 0, len);
                        bos.flush();
                    }
                    bos.close();
                    bis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            publishProgress(DOWNLOADCOMPLETE, 0);
            return "下载完成";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            switch (values[0]) {
                case DOWNLOADCOMPLETE:
                    if (onDownLoadBack != null) {
                        onDownLoadBack.onSuccess();
                    }
                    break;
            }
        }
    }

    public interface OnDownLoadBack {
        void onSuccess();
    }

    public static OnDownLoadBack onDownLoadBack;

    public static void setOnDownLoadBack(OnDownLoadBack downLoadBack) {
        onDownLoadBack = downLoadBack;
    }

}

package com.yunqin.bearmall.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author LWP
 * @PACKAGE com.aha.live.wallpapers.holiday.merry.christmas.santa.myapplication
 * @DATE 2020/3/28
 */
public class DownLoadImage {

    private static DownLoadImage loadImage = null;
    private int contentLen;//声明要下载的文件总长
    private int downloadLen;
    //声明publishProgress的更新标记
    private static final int PROGRESS_MAX = 0X1;
    private static final int UPDATE = 0X2;
    private static final int DOWNLOAD = 0X3;
    private static final int DOWNLOADVALUE = 0X4;
    private static final int UPDATEVIEW = 0X5;
    public Context context;
    public File file = null;

    public static DownLoadImage getInstance() {
        if (loadImage == null) {
            synchronized (DownLoadImage.class) {
                if (loadImage == null) {
                    loadImage = new DownLoadImage();
                }
            }
        }
        return loadImage;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void DownLoadImag(String[] imageUrl) {
        new DownLoad().execute(imageUrl);
    }

    public void DownImageLength(String[] imageUrl) {
        new DownLoadImageLength().execute(imageUrl);
    }

    public class DownLoadImageLength extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                contentLen = 0;
                downloadLen = 0;
                for (int i = 0; i < strings.length; i++) {
                    URL url = new URL(strings[i]);
                    //获取连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //获取下载文件的大小
                    int contentLength = connection.getContentLength();
                    //根据下载文件大小设置进度条最大值（使用标记区别实时进度更新）
                    contentLen = contentLen + contentLength;
                    if (i == strings.length - 1) {
                        publishProgress(PROGRESS_MAX, contentLen);
                        publishProgress(DOWNLOAD, contentLength);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            switch (values[0]) {
                case PROGRESS_MAX:
                    if (mOnDownLoadImage != null) {
                        mOnDownLoadImage.progressMax(values[1]);
                    }
                    break;
                case DOWNLOAD:
                    if (mOnDownLoadImage != null) {
                        mOnDownLoadImage.downLiadImage(values[1]);
                    }
                    break;
            }
        }

    }

    public class DownLoad extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                File appDir = new File(Environment.getExternalStorageDirectory(), "InvitationPoster");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }

                for (int i = 0; i < strings.length; i++) {
                    URL url = new URL(strings[i]);
                    //获取连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //循环下载（边读取边存入）
                    BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
                    String fileName = System.currentTimeMillis() + ".jpg";
                    file = new File(appDir, fileName);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
                    int len = -1;
                    byte[] bytes = new byte[1024];
                    while ((len = bis.read(bytes)) != -1) {
                        bos.write(bytes, 0, len);
                        bos.flush();
                        //实时更新下载进度（使用标记区别最大值）
                        downloadLen = downloadLen + len;
                        publishProgress(UPDATE, downloadLen);
                    }
                    bos.close();
                    bis.close();
                    try {
                        MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri = Uri.fromFile(file);
                    intent.setData(uri);
                    publishProgress(DOWNLOADVALUE, i + 1);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            publishProgress(UPDATEVIEW, 0);
            //删除
            if (file.isFile() && file.exists()) {
                file.delete();
            }
            return "下载完成";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            switch (values[0]) {
                case UPDATE:
                    if (mOnDownLoadImage != null) {
                        mOnDownLoadImage.progressValue(values[1], contentLen);
                    }
                    break;
                case DOWNLOADVALUE:
                    if (mOnDownLoadImage != null) {
                        mOnDownLoadImage.downLoadValue(values[1]);
                    }
                    break;
                case UPDATEVIEW:
                    if (mOnDownLoadImage != null) {
                        mOnDownLoadImage.progerssVisibility();
                    }
                    break;
            }
        }

    }

    public interface onDownLoadImage {
        void progressMax(int value);

        void progressValue(int value, int contentLen);

        void progerssVisibility();

        void downLiadImage(int imageLength);

        void downLoadValue(int value);
    }

    public onDownLoadImage mOnDownLoadImage;

    public void setOnDownLoadImage(onDownLoadImage mOnDownLoadImage) {
        this.mOnDownLoadImage = mOnDownLoadImage;
    }
}

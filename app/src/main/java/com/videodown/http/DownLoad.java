package com.videodown.http;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;


import com.videodown.http.bean.DownBean;
import com.videodown.http.i.OnDownloadListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create by Administrator
 * on 2018/10/22
 */
public class DownLoad {


    private static final int START = 1;                 // 开始下载
    private static final int PUBLISH = 2;               // 更新进度
    private static final int ERROR = 3;                 // 下载错误
    private static final int SUCCESS = 4;               // 下载成功
    private static final int FAIL = 5;                  // 下载失败

    private boolean downIng = true;

    private OnDownloadListener mListener;               // 监听器

    private static ExecutorService mThreadPool;         // 线程池

    static {
        mThreadPool = Executors.newFixedThreadPool(1);  // 默认1个
    }

    private static final String path = Environment.getExternalStorageDirectory().getPath() + "/com.yunqin.app/";


    public DownLoad(OnDownloadListener listener) {
        mListener = listener;
    }


    public void start(DownBean bean) {
        if (bean == null) {
            return;
        }

        if (!new File(path).exists()) {
            new File(path).mkdirs();
        }

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case START:
                        mListener.onStart(msg.obj);
                        break;
                    case PUBLISH:
                        mListener.onPublish(msg.obj, msg.arg1);
                        break;
                    case ERROR:
                        mListener.onError(msg.obj);
                        break;
                    case SUCCESS:
                        mListener.onSuccess(msg.obj);
                        break;
                    case FAIL:
                        mListener.onError(msg.obj);
                        break;
                    default:
                        break;
                }
            }
        };
        start(bean, handler);
    }


    /**
     * 开始下载
     *
     * @param bean
     * @param handler
     */
    private synchronized void start(DownBean bean, Handler handler) {
        mThreadPool.execute(new IRunnable(bean, handler));
    }

    /**
     * 关闭下载线程池
     */
    public void closeDownloadThread() {
        if (null != mThreadPool) {
            downIng = false;
        }
    }

    class IRunnable implements Runnable {

        DownBean bean;
        Handler handler;

        public IRunnable(DownBean bean, Handler handler) {
            this.bean = bean;
            this.handler = handler;
        }

        @Override
        public void run() {
            download(bean, handler);
        }
    }

    /**
     * 下载方法
     *
     * @param bean
     * @param handler
     */
    private void download(final DownBean bean, final Handler handler) {
        Message msg = null;
        HttpURLConnection mHttpURLConnection = null;
        FileOutputStream fos = null;
        try {
            URL mURL = new URL(bean.getUrl());
            mHttpURLConnection = (HttpURLConnection) mURL.openConnection();
            mHttpURLConnection.setRequestMethod("GET");
            mHttpURLConnection.setConnectTimeout(3000);

            int iResponseCode = mHttpURLConnection.getResponseCode();
            if (iResponseCode == 200) {
                long filesize = mHttpURLConnection.getContentLength();

                if (filesize == -1) {
                    bean.setThrowable(new Error("Please check the file length"));
                    msg = handler.obtainMessage();
                    msg.what = ERROR;
                    msg.obj = bean;
                    handler.sendMessage(msg);
                    return;
                }
                msg = handler.obtainMessage();
                bean.setSize(filesize);
                msg.what = START;
                msg.obj = bean;
                handler.sendMessage(msg);

                InputStream in = mHttpURLConnection.getInputStream();
                bean.setPath(path + bean.getName());
                File file = new File(bean.getPath());
                if (file.exists()) {
                    file.delete();
                }
                fos = new FileOutputStream(file);
                byte[] bytes = new byte[1024 * 4];
                int len = -1;
                long current = 0;
                while (-1 != (len = in.read(bytes))) {
                    if(downIng){
                        fos.write(bytes, 0, len);
                        current += len;
                        msg = handler.obtainMessage();
                        msg.what = PUBLISH;
                        msg.obj = bean;
                        msg.arg1 = ((int) (current * 100L / filesize));
                        handler.sendMessage(msg);
                    }else{

                        fos.flush();
                        mHttpURLConnection.disconnect();

                        bean.setThrowable(new Error("Take the initiative to stop"));
                        msg.what = FAIL;
                        msg.obj = bean;
                        handler.sendMessage(msg);
                        return;
                    }
                }
                fos.flush();
                mHttpURLConnection.disconnect();

                msg = handler.obtainMessage();
                msg.what = SUCCESS;
                msg.obj = bean;
                handler.sendMessage(msg);
            } else {
                bean.setThrowable(new Error("Please check the ResponseCode " + iResponseCode));
                msg.what = FAIL;
                msg.obj = bean;
                handler.sendMessage(msg);
            }
        } catch (Exception e) {
            // 发送下载错误的消息
            bean.setThrowable(e);
            msg = handler.obtainMessage();
            msg.what = ERROR;
            msg.obj = bean;
            handler.sendMessage(msg);
        }
    }


}

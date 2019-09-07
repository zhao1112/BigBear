package com.yunqin.bearmall.inter;

/**
 * @author Master
 * @create 2018/8/7 11:06
 */
public interface ApkDownloadListener {
    void onStartDownload();
    void onProgress(int progress);
    void onFinishDownload();
    void onFail(Throwable throwable);
}

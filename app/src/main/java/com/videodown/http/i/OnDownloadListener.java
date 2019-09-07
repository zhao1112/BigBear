package com.videodown.http.i;

/**
 * Create by Administrator
 * on 2018/10/22
 */
public interface OnDownloadListener<T> {

    /**
     * 开始下载
     *
     * @param t        标识值
     */
    void onStart(T t);

    /**
     * 更新进度
     *
     * @param t       标识值
     * @param percent 当前下载量
     */
    void onPublish(T t, int percent);

    /**
     * 下载成功
     *
     * @param t 标识值
     */
    void onSuccess(T t);

    /**
     * 下在失败
     *
     * @param t 标识值
     */
    void onError(T t);
}

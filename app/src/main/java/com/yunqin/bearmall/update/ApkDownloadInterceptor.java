package com.yunqin.bearmall.update;

import com.yunqin.bearmall.inter.ApkDownloadListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author Master
 * @create 2018/8/7 11:11
 */
public class ApkDownloadInterceptor implements Interceptor {

    private ApkDownloadListener apkDownloadListener;

    public ApkDownloadInterceptor(ApkDownloadListener apkDownloadListener) {
        this.apkDownloadListener = apkDownloadListener;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder()
                .body(new ApkResponseBody(response.body(), apkDownloadListener))
                .build();
    }
}

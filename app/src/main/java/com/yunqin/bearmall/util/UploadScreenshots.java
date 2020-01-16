package com.yunqin.bearmall.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.util
 * @DATE 2020/1/13
 */
public class UploadScreenshots {

    public static void okHttpScreenshots(Context context, Uri lists, OnUpLoadCallBack onUpLoadCallBack) {

        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);

        try {
            File file = new File(new URI(lists.toString()));
            if (file != null) {
                RequestBody body = RequestBody.create(MediaType.parse("image/png"), file);
                String filename = String.format("file_%d_%d", 0, 0);
                requestBody.addFormDataPart("file", filename, body);
            }
        } catch (Exception e) {
            Log.e("LoadImage", e.getMessage());
        }


        Request request = new Request.Builder()
                .url(BuildConfig.BASE_URL + "/api/userinfo/uploadWX")
                .post(requestBody.build())
                .tag(context)
                .addHeader("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token())
                .build();

        client.newBuilder()
                .connectTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .build()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                        if (onUpLoadCallBack != null) {
                            onUpLoadCallBack.onFail(e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                        if (onUpLoadCallBack != null) {
                            onUpLoadCallBack.onSuccessID(response.body().string());
                        }
                    }
                });

    }


    public interface OnUpLoadCallBack {
        void onSuccessID(String data);

        void onFail(String fail);
    }

}

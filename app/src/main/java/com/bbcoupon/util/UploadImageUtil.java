package com.bbcoupon.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.util
 * @DATE 2020/5/8
 */
public class UploadImageUtil {

    public static void UploadImage(Context context, Uri uri, String fileName, OnUpLoadmageCallBack callBack) {
        OkHttpClient client = new OkHttpClient();
        String t = String.valueOf(System.currentTimeMillis());
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        try {
            File file = new File(new URI(uri.toString()));
            if (file != null) {
                RequestBody body = RequestBody.create(MediaType.parse("image/png"), file);
                requestBody.addFormDataPart("file", fileName, body);
            }
        } catch (Exception e) {

        }
        Request request = new Request.Builder()
                .url(BuildConfig.BASE_URL + "/api/userinfo/finduUploadImg?type=1")
                .post(requestBody.build())
                .tag(context)
                .addHeader("Authorization", getAuthorization(t))
                .addHeader("Timestamp", t)
                .addHeader("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token())
                .addHeader("refresh_token", BearMallAplication.getInstance().getUser().getData().getToken().getRefresh_token())
                .build();
        client.newBuilder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .build()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                        if (callBack != null) {
                            callBack.onFail(e);
                        }
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                        if (callBack != null) {
                            Log.e("onResponse", "onResponse: ");
                            callBack.onSuccess(response.body().string());
                        }
                    }
                });

    }

    private static String getAuthorization(String ti) {
        String data = "BIGBEARMALL" + ti;
        MessageDigest md5 = null;

        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(data.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public interface OnUpLoadmageCallBack {
        void onSuccess(String data);

        void onFail(Throwable throwable);
    }

}

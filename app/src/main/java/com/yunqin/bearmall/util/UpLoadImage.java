package com.yunqin.bearmall.util;

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
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static java.lang.String.valueOf;

public class UpLoadImage {

    public static void okHttpUpLoadImage(Context context, List<List<Uri>> lists, List<Integer> itemIds, Map<String, Object> map, OnUpLoadCallBack callBack) {
        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < lists.size(); i++) {
            List<Uri> list = lists.get(i);
            for (int j = 0; j < list.size(); j++) {
                try {
                    File file = new File(new URI(list.get(j).toString()));
                    if (file != null) {
                        RequestBody body = RequestBody.create(MediaType.parse("image/png"), file);
                        String filename = String.format("file_%d_%d", itemIds.get(i), j);
                        requestBody.addFormDataPart("file", filename, body);
                    }

                } catch (Exception e) {
                }
            }
        }


        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {
                requestBody.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }

        String t = String.valueOf(System.currentTimeMillis());

        Request request = new Request.Builder()
                .url(BuildConfig.BASE_URL + "/api/review/reviewOrdersItem")
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
                            callBack.onSuccess(response.body().string());
                        }
                    }
                });
    }


    public interface OnUpLoadCallBack {
        void onSuccess(String data);

        void onFail(Throwable throwable);
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

}

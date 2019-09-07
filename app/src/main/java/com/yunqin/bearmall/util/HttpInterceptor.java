package com.yunqin.bearmall.util;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class HttpInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String method = request.method();
        if (method.equals("POST")) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            RequestBody requestBody = request.body();
            if (requestBody instanceof FormBody) {
                for (int i = 0; i < ((FormBody) requestBody).size(); i++) {
                    bodyBuilder.addEncoded(((FormBody) requestBody).encodedName(i), ((FormBody) requestBody).encodedValue(i));
                }
                FormBody formBody = bodyBuilder
                        .addEncoded("imei", "I'm kidding you!")
                        .addEncoded("wang", "111")
                        .addEncoded("zhi", "222")
                        .addEncoded("wei", "333")
                        .build();
                request = request.newBuilder().post(formBody).build();
            } else {
                Log.e("HTTP", "POST 提交方式 不是 FormBody");
                FormBody.Builder bodyBuilders = new FormBody.Builder();


                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                String oldParamsJson = buffer.readUtf8();
                HashMap<String, Object> rootMap = new Gson().fromJson(oldParamsJson, HashMap.class);
                if (rootMap == null) {
                    rootMap = new HashMap<>();
                }
                rootMap.put("imei", "I'm kidding you!");
                rootMap.put("wang", "111");
                rootMap.put("zhi", "222");
                rootMap.put("wei", "333");

                for (String key : rootMap.keySet()) {
                    bodyBuilders.addEncoded(key, rootMap.get(key) + "");
                }
                bodyBuilders.addEncoded("machine_id", "6226522145522485");

                FormBody formBody = bodyBuilders.build();
                request = request.newBuilder().post(formBody).build();
            }
        }

        return chain.proceed(request);
    }
}

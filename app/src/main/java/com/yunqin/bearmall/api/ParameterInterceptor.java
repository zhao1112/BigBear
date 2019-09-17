package com.yunqin.bearmall.api;

import android.util.Log;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.util.DeviceUtils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;


/**
 *
 */
public class ParameterInterceptor implements Interceptor {

    private static final String TOKEN = "access_token";
    private static final String CID = "cid";
    private static final String DEVICES_ID = "machine_id";

    private Map<String, String> mParamsMap = new HashMap<>();


    @Override
    public Response intercept(Chain chain) throws IOException {

        HashMap<String, String> devParams = BearMallAplication.get().getDevParams();

        mParamsMap.clear();
        mParamsMap.put(DEVICES_ID, DeviceUtils.getUniqueId(BearMallAplication.getInstance().getApplicationContext()));

        Request request = chain.request();
        String method = request.method();
        RequestBody requestBody = request.body();


        Request.Builder requestBuilder = request.newBuilder();
        requestBuilder.method(method, requestBody);
        // 添加统一消息头
        String t = String.valueOf(System.currentTimeMillis());
        requestBuilder.addHeader("Authorization", getAuthorization(t));
        requestBuilder.addHeader("Timestamp", t);

        if (checkAdd()) {
            requestBuilder.addHeader(TOKEN, BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
            requestBuilder.addHeader("refresh_token", BearMallAplication.getInstance().getUser().getData().getToken().getRefresh_token());

        }


        if (method.equals("POST")) {

            FormBody.Builder bodyBuilder = new FormBody.Builder();
//            RequestBody requestBody = request.body();

            if (requestBody instanceof FormBody) {
                for (int i = 0; i < ((FormBody) requestBody).size(); i++) {
                    bodyBuilder.addEncoded(((FormBody) requestBody).encodedName(i), ((FormBody) requestBody).encodedValue(i));
                    mParamsMap.remove(((FormBody) requestBody).encodedName(i));
                }

                // TODO 添加公共参数设备ID
                for (String key : mParamsMap.keySet()) {
                    bodyBuilder.addEncoded(key, mParamsMap.get(key));
                }

                if (devParams != null) {
                    for (String key : devParams.keySet()) {
                        bodyBuilder.addEncoded(key, devParams.get(key));
                    }
                }


//                bodyBuilder.addEncoded(DEVICES_ID, DeviceUtils.getUniqueId(BearMallAplication.getInstance().getApplicationContext()));

//                if (checkAdd()) {
//                    bodyBuilder.addEncoded(TOKEN, BearMallAplication.getInstance().getUser().getData().getAccess_token());
//                }
//                bodyBuilder.addEncoded(CID, (String) SharedPreferencesHelper.get(BearMallAplication.getInstance().getApplicationContext
//                (), "clientid", ""));
                FormBody formBody = bodyBuilder.build();
//                request = request.newBuilder().post(formBody).build();
                request = requestBuilder.post(formBody).build();
            } else {
                // POST 提交方式 不是 Form表单
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                String oldParamsJson = buffer.readUtf8();
                HashMap<String, Object> rootMap = new Gson().fromJson(oldParamsJson, HashMap.class);
                if (rootMap == null) {
                    rootMap = new HashMap<>();
                }
                for (String key : rootMap.keySet()) {
                    bodyBuilder.addEncoded(key, rootMap.get(key) + "");
                    mParamsMap.remove(key);
                }

                // TODO 添加公共参数设备ID
                for (String key : mParamsMap.keySet()) {
                    bodyBuilder.addEncoded(key, mParamsMap.get(key));
                }
//
                if (devParams != null) {
                    for (String key : devParams.keySet()) {
                        bodyBuilder.addEncoded(key, devParams.get(key));
                    }
                }


//                bodyBuilder.addEncoded(DEVICES_ID, DeviceUtils.getUniqueId(BearMallAplication.getInstance().getApplicationContext()));

//                if (checkAdd()) {
//                    bodyBuilder.addEncoded(TOKEN, BearMallAplication.getInstance().getUser().getData().getAccess_token());
//                }
//                bodyBuilder.addEncoded(CID, (String) SharedPreferencesHelper.get(BearMallAplication.getInstance().getApplicationContext
//                (), "clientid", ""));

                FormBody formBody = bodyBuilder.build();
//                request = request.newBuilder().post(formBody).build();
                request = requestBuilder.post(formBody).build();
            }
        }

        return chain.proceed(request);
    }


    private boolean checkAdd() {
        if (BearMallAplication.getInstance() != null
                && BearMallAplication.getInstance().getUser() != null
                && BearMallAplication.getInstance().getUser().getData() != null
                && BearMallAplication.getInstance().getUser().getData().getToken() != null
                && BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() != null) {
            Log.e("Token", "=====================" + BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() +
                    "=============================");
            return true;
        }
        return false;
    }


    private String getAuthorization(String ti) {
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

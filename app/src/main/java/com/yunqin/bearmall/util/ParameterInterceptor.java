package com.yunqin.bearmall.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yunqin.bearmall.BearMallAplication;

import java.io.IOException;
import java.util.HashMap;

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

                if (checkAdd()) {
                    bodyBuilder.addEncoded(TOKEN, BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
                }
                FormBody formBody = bodyBuilder.build();
                request = request.newBuilder().post(formBody).build();
            } else {
                // POST 提交方式 不是 Form表单
                try {
                    Buffer buffer = new Buffer();
                    requestBody.writeTo(buffer);
                    String oldParamsJson = buffer.readUtf8();
                    HashMap<String, Object> rootMap = new Gson().fromJson(oldParamsJson, HashMap.class);
                    if (rootMap == null) {
                        rootMap = new HashMap<>();
                    }
                    for (String key : rootMap.keySet()) {
                        bodyBuilder.addEncoded(key, rootMap.get(key) + "");
                    }

                    if (checkAdd()) {
                        bodyBuilder.addEncoded(TOKEN, BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
                    }

                    FormBody formBody = bodyBuilder.build();
                    request = request.newBuilder().post(formBody).build();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }
        }

        return chain.proceed(request);
    }


    private boolean checkAdd() {
        if (BearMallAplication.getInstance() != null
                && BearMallAplication.getInstance().getUser() != null
                && BearMallAplication.getInstance().getUser().getData() != null
                && BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() != null) {
            return true;
        }
        return false;
    }


}

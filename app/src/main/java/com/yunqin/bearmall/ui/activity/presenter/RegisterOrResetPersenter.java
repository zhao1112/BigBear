package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.ui.activity.contract.RegisterOrResetContract;
import com.yunqin.bearmall.util.DeviceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author Master
 */
public class RegisterOrResetPersenter implements RegisterOrResetContract.Presenter {

    RegisterOrResetContract.UI view;

    public RegisterOrResetPersenter(RegisterOrResetContract.UI view) {
        this.view = view;
    }

    @Override
    public void stat() {
        System.out.println("RegisterOrResetPersenter start");
    }

    @Override
    public void performRegister(Context context, Map<String, String> mHashMap) {
        // TODO 注册密码
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).userRegiest(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.optInt("code") == 1) {
                        view.registerSuccess(new Gson().fromJson(data, UserInfo.class));
                    } else {
                        view.registerFail(jsonObject.optString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                Log.e("TAG-register", "失败了 : ", e);
                view.registerFail(e.getMessage());
            }
        });
    }

    @Override
    public void perfromReset(Context context, Map<String, String> mHashMap) {
        // TODO 重置密码
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).resetPwd(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.optInt("code") == 1) {
                        view.resetSuccess();
                    } else {
                        view.resetFail(jsonObject.optString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                Log.e("TAG-reset", "失败了 : ", e);
                view.resetFail(e.getMessage());
            }
        });
    }

    @Override
    public void perfromVerificationCode(Context context,String id) {
        Constans.params.clear();
        Constans.params.put("machine_id", id);

        RetrofitApi.requestImageCode(context, RetrofitApi.createApi(Api.class).getImageCode(Constans.params), new RetrofitApi.ImageCodeResponseListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                try {
                    view.attachVCod(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Throwable e) {
            }
        });

    }

    @Override
    public void sendMsgCode(Context context, Map<String, String> mHashMap) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getMsgCode(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Toast.makeText(context,"随机码发送成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
            }
        });
    }
}

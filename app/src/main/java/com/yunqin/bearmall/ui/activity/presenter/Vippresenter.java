package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.UserPromotion;
import com.yunqin.bearmall.ui.activity.contract.VipContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.activity.contract
 * @DATE 2020/1/10
 */
public class Vippresenter implements VipContract.Presenter {

    private VipContract.UI view;

    public Vippresenter(VipContract.UI view) {
        this.view = view;
    }

    @Override
    public void start(Context context) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getUserPromotion(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                Log.e("onSuccess", data);
                if (data != null) {
                    UserPromotion userPromotion = null;
                    try {
                        userPromotion = new Gson().fromJson(data, UserPromotion.class);
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        Log.e("JsonSyntaxException", e.getMessage() );
                    }
                    if (view != null) {
                        view.resultData(userPromotion);
                    }
                }
            }

            @Override
            public void onNotNetWork() {
                if (view != null) {
                    view.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (view != null) {
                    view.onFail(e);
                }
            }
        });

    }

    @Override
    public void onDeduction(Context context, String type) {
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).gradeDeduction(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                if (data != null) {
                    JSONObject object = new JSONObject(data);
                    view.getDeduction(object.optInt("code"));
                }
            }

            @Override
            public void onNotNetWork() {
                if (view != null) {
                    view.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (view != null) {
                    view.onFail(e);
                }
            }
        });
    }
}

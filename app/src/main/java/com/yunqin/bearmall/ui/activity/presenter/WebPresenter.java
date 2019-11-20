package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.google.gson.Gson;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.Checkzero;
import com.yunqin.bearmall.ui.activity.contract.WebContract;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.activity.presenter
 * @DATE 2019/11/11
 */
public class WebPresenter implements WebContract.presenter {

    private WebContract.UI view;
    private Context mContext;

    public WebPresenter(Context context, WebContract.UI view) {
        this.mContext = context;
        this.view = view;
    }

    @Override
    public void Checkzero() {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getCheckzero(),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        Log.i("onSuccess", data);
                        if (data != null) {
                            Checkzero checkzero = new Gson().fromJson(data, Checkzero.class);
                            if (checkzero.getData().getSuccess() == 1) {
                                view.onCheckzero();
                            } else if (checkzero.getData().getSuccess() == 0) {
                                view.onOneTipe("新人限领1次");
                            }
                        }
                    }

                    @Override
                    public void onNotNetWork() {
                        view.onNotNetWork();
                    }

                    @Override
                    public void onFail(Throwable e) {
                        view.onFail(e);
                    }
                });
    }

    @Override
    public void Clickurl(String access_token,String itemId) {
        Map<String, String> map = new HashMap<>();
        map.put("access_token", access_token);
        map.put("itemId", itemId);
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).clickurl(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws org.json.JSONException {
                Log.i("onSuccess", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.optInt("code") == 1) {
                        view.onClickurl(jsonObject.optString("sendUrl"));
                    }else {
                        view.onFail(new Exception(jsonObject.optString("msg")));
                    }
                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                    view.onFail(e);
                }
            }

            @Override
            public void onNotNetWork() {
                view.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);
            }
        });
    }

    @Override
    public void Checkinvitation() {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getCheckinvitation(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws org.json.JSONException {
                JSONObject object = new JSONObject(data);
                Log.i("onSuccess", data);
                if (object.optInt("code") == 1) {
                    view.onCheckinvitation();
                }else {
                    view.onFail(new Exception(object.optString("msg")));
                }
            }
            @Override
            public void onNotNetWork() {
                view.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);
            }
        });
    }

}

package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.UserPromotion;
import com.yunqin.bearmall.ui.activity.contract.VipContract;

import org.json.JSONException;

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
                if (data != null) {
                    UserPromotion userPromotion = new Gson().fromJson(data, UserPromotion.class);
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
}

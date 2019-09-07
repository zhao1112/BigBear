package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;

import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.BearFAQ;
import com.yunqin.bearmall.ui.activity.contract.BearClassContract;
import com.yunqin.bearmall.ui.activity.model.BearClassModel;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenchen on 2018/7/25.
 */

public class BearClassPresent implements BearClassContract.IPresent {

    private BearClassContract.IUI view;
    private BearClassContract.IModel model;
    private Context context;

    public BearClassPresent(BearClassContract.IUI view) {
        this.view = view;
        model = new BearClassModel();
    }

    @Override
    public void start(Context context) {

        this.context = context;

        RetrofitApi.request(context, model.getQusetion(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {

                BearFAQ result = new Gson().fromJson(data,BearFAQ.class);

                view.onGetInitData(result);

            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }

    @Override
    public void onRequestNextTI(int tag, String answer) {

        Map<String,String> params = new HashMap<>();
        params.put("tag",tag+"");
        params.put("answer",answer);

        RetrofitApi.request(context, model.getNextTi(params), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {

                BearFAQ result = new Gson().fromJson(data,BearFAQ.class);

                view.onGetNextTiData(result);
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

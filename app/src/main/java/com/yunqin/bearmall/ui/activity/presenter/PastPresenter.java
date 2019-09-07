package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.PastSnatchData;
import com.yunqin.bearmall.bean.ZeroPastData;
import com.yunqin.bearmall.ui.activity.contract.PastContract;
import com.yunqin.bearmall.ui.activity.model.PastModel;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenchen on 2018/8/25.
 */

public class PastPresenter implements PastContract.IPresenter {

    private PastContract.IModel model;
    private PastContract.IView view;
    private Context context;

    public PastPresenter(PastContract.IView view) {
        this.view = view;
        model = new PastModel();
    }

    @Override
    public void start(Context context) {

        this.context = context;
    }

    @Override
    public void refreshSnatchPastData(int index) {

        Map<String,String> params = new HashMap<>();
        params.put("page_size","30");
        params.put("page_number",index+"");
        RetrofitApi.request(context, model.getSnatchPastList(params), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                PastSnatchData mData = new Gson().fromJson(data,PastSnatchData.class);
                view.onLoadedSnatchData(mData);
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
    public void refreshZeroPastData(int index) {

        Map<String,String> params = new HashMap<>();
        params.put("page_size","30");
        params.put("page_number",index+"");
        RetrofitApi.request(context, model.getZeroPastList(params), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                ZeroPastData mData = new Gson().fromJson(data,ZeroPastData.class);
                view.onLoadedZeroData(mData);
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

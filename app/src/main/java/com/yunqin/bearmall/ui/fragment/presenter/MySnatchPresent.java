package com.yunqin.bearmall.ui.fragment.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.MySnatch;
import com.yunqin.bearmall.ui.fragment.contract.MySnatchContract;
import com.yunqin.bearmall.ui.fragment.model.MySnatchModel;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenchen on 2018/8/7.
 */

public class MySnatchPresent implements MySnatchContract.IPresent {

    private MySnatchContract.IView view;

    private MySnatchContract.IModel model;

    private Context context;

    public MySnatchPresent(MySnatchContract.IView view) {
        this.view = view;
        model = new MySnatchModel();
    }

    @Override
    public void start(Context context) {
        this.context = context;
    }

    @Override
    public void refreshData(int pageIndex) {

        Map<String,String> params = new HashMap<>();
        params.put("page_size","10");
        params.put("page_number",pageIndex+"");

        RetrofitApi.request(context, model.getMemberTreasureInfo(params), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                MySnatch snatch = new Gson().fromJson(data,MySnatch.class);

                view.onLoadedData(snatch);

            }

            @Override
            public void onNotNetWork() {

                view.onLoadError();

            }

            @Override
            public void onFail(Throwable e) {

                view.onLoadError();
            }
        });

    }
}

package com.yunqin.bearmall.ui.fragment.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.SnatchContent;
import com.yunqin.bearmall.ui.fragment.contract.SnatchContentContract;
import com.yunqin.bearmall.ui.fragment.model.SnatchModel;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenchen on 2018/8/7.
 */

public class SnatchPresenter implements SnatchContentContract.IPresent {

    private Context context;

    private SnatchContentContract.IView view;
    private SnatchContentContract.IModel model;

    public SnatchPresenter( SnatchContentContract.IView view) {
        this.view = view;
        model = new SnatchModel();
    }

    @Override
    public void start(Context context) {
        this.context = context;
    }

    @Override
    public void refreshData(int pageIndex, int tag,int isToday) {

        Map<String,String> params = new HashMap<>();
        params.put("tag",tag+"");
        params.put("page_size","10");
        params.put("page_number",pageIndex+"");
        params.put("isToday",isToday+"");

        RetrofitApi.request(context, model.getTreasureBasicInfo(params), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                SnatchContent content = new Gson().fromJson(data,SnatchContent.class);

               view.onLoadedData(content);

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

package com.yunqin.bearmall.ui.fragment.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.TreasureInfo;
import com.yunqin.bearmall.ui.fragment.contract.SnatchDetailContract;
import com.yunqin.bearmall.ui.fragment.model.SnatchDetailModel;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenchen on 2018/8/9.
 */

public class SnatchDetailPresenter implements SnatchDetailContract.IPresent {

    private SnatchDetailContract.IView view;
    private SnatchDetailContract.IModel model;
    private Context context;

    public SnatchDetailPresenter(SnatchDetailContract.IView view) {
        this.view = view;
        model = new SnatchDetailModel();
    }

    @Override
    public void start(Context context) {

        this.context = context;
    }

    @Override
    public void loadData(String tag, String tid,int isToday) {

        Map<String,String> params = new HashMap<>();
        if (!TextUtils.isEmpty(tag)){
            params.put("tag",tag);
        }
        params.put("treasure_id",tid);
        params.put("isToday",isToday+"");

        RetrofitApi.request(context, model.getTreasureInfo(params), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                if (!TextUtils.isEmpty(data)){

                    TreasureInfo treasureInfo = new Gson().fromJson(data,TreasureInfo.class);

                    view.onLoadedData(treasureInfo);

                }

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

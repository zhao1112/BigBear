package com.yunqin.bearmall.ui.fragment.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.BannerBean;
import com.yunqin.bearmall.bean.TreasureData;
import com.yunqin.bearmall.ui.fragment.contract.SweetSnatchContract;
import com.yunqin.bearmall.ui.fragment.model.SweetSnatchModel;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenchen on 2018/8/14.
 */

public class SweetSnatchPresnet implements SweetSnatchContract.IPresenter {

    private SweetSnatchContract.IView view;
    private SweetSnatchContract.IModel model;

    public SweetSnatchPresnet(SweetSnatchContract.IView view) {
        this.view = view;
        this.model = new SweetSnatchModel();
    }

    @Override
    public void start(Context context) {

        RetrofitApi.request(context, model.getTagData(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                TreasureData mData = new Gson().fromJson(data,TreasureData.class);
                view.onLoadedData(mData.getData());
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
    public void getBannerData(Context context) {
        Map map = new HashMap();
        map.put("positionType","6");
        RetrofitApi.request(context,RetrofitApi.createApi(Api.class).getAdMobileList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                BannerBean bannerBean = new Gson().fromJson(data,BannerBean.class);
                assert view != null;
                view.initBannerTop(bannerBean);
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

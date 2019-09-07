package com.yunqin.bearmall.ui.activity.model;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.activity.contract.BearClassContract;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/7/25.
 */

public class BearClassModel implements BearClassContract.IModel {
    @Override
    public Observable<String> getQusetion() {
        return RetrofitApi.createApi(Api.class).getInitTikuInfo();
    }

    @Override
    public Observable<String> getNextTi(Map params) {
        return RetrofitApi.createApi(Api.class).getNextTi(params);
    }

}

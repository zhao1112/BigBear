package com.yunqin.bearmall.ui.activity.model;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.activity.contract.PastContract;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/8/25.
 */

public class PastModel implements PastContract.IModel {
    @Override
    public Observable<String> getSnatchPastList(Map params) {
        return RetrofitApi.createApi(Api.class).getPastTreasureDetails(params);
    }

    @Override
    public Observable<String> getZeroPastList(Map params) {
        return RetrofitApi.createApi(Api.class).getPastGrouppurchasingDetails(params);
    }
}

package com.yunqin.bearmall.ui.fragment.model;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.fragment.contract.SnatchContentContract;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/8/7.
 */

public class SnatchModel implements SnatchContentContract.IModel {
    @Override
    public Observable<String> getTreasureBasicInfo(Map params) {
        return RetrofitApi.createApi(Api.class).getTreasureBasicInfo(params);
    }
}

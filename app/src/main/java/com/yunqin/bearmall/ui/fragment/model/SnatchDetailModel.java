package com.yunqin.bearmall.ui.fragment.model;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.fragment.contract.SnatchDetailContract;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/8/9.
 */

public class SnatchDetailModel implements SnatchDetailContract.IModel {

    @Override
    public Observable<String> getTreasureInfo(Map params) {
        return RetrofitApi.createApi(Api.class).getTreasureInfo(params);
    }
}

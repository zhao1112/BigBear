package com.yunqin.bearmall.ui.fragment.model;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.fragment.contract.MySnatchContract;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by chenchen on 2018/8/7.
 */

public class MySnatchModel implements MySnatchContract.IModel {

    @Override
    public Observable<String> getMemberTreasureInfo(Map params) {
        return RetrofitApi.createApi(Api.class).getMemberTreasureInfo(params);
    }

}

package com.yunqin.bearmall.ui.activity.model;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.activity.contract.BargainFreeDetailContract;

import java.util.Map;

import io.reactivex.Observable;

public class BargainFreeDetailModel implements BargainFreeDetailContract.Model{

    @Override
    public Observable<String> getBargainProductDetail(Map map) {
        return RetrofitApi.createApi(Api.class).getBargainProductDetail(map);
    }

    @Override
    public Observable<String> setBargainFavorite(Map map) {
        return RetrofitApi.createApi(Api.class).setFavorite(map);
    }
}

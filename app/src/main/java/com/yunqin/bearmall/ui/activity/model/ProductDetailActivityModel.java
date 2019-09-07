package com.yunqin.bearmall.ui.activity.model;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.activity.contract.ProductActivityContract;

import java.util.Map;

import io.reactivex.Observable;

public class ProductDetailActivityModel implements ProductActivityContract.Model{

    @Override
    public Observable<String> getProductData(Map map) {
        return RetrofitApi.createApi(Api.class).getProductData(map);
    }

    @Override
    public Observable<String> setFavorite(Map map) {
        return RetrofitApi.createApi(Api.class).setFavorite(map);
    }

    @Override
    public Observable<String> joinCart(Map map) {
        return RetrofitApi.createApi(Api.class).joinCart(map);
    }

}

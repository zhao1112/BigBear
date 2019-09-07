package com.yunqin.bearmall.ui.fragment.model;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.fragment.contract.TrolleyContract;

import java.util.Map;

import io.reactivex.Observable;

public class TrolleyModel implements TrolleyContract.Model{

    @Override
    public Observable<String> getCartItemList(Map map) {
        return RetrofitApi.createApi(Api.class).getCartItemList(map);
    }

    @Override
    public Observable<String> getCartItemCount(Map map) {
        return RetrofitApi.createApi(Api.class).getCartItemCount(map);
    }

    @Override
    public Observable<String> removeCartItemsForFavorite(Map map) {
        return RetrofitApi.createApi(Api.class).removeCartItemsForFavorite(map);
    }

    @Override
    public Observable<String> removeCartItems(Map map) {
        return RetrofitApi.createApi(Api.class).removeCartItems(map);
    }

    @Override
    public Observable<String> setItemQuantity(Map map) {
        return RetrofitApi.createApi(Api.class).setItemQuantity(map);
    }
}

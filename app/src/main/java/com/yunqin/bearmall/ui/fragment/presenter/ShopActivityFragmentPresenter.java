package com.yunqin.bearmall.ui.fragment.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.BannerBean;
import com.yunqin.bearmall.ui.fragment.contract.ShopActivityFragmentContract;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AYWang
 * @create 2018/8/10
 * @Describe
 */
public class ShopActivityFragmentPresenter implements ShopActivityFragmentContract.presenter {

    private ShopActivityFragmentContract.UI view;
    private Context mContext;

    public ShopActivityFragmentPresenter(Context mContext,ShopActivityFragmentContract.UI view){
        this.view = view;
        this.mContext = mContext;
    }

    @Override
    public void start(Map map, boolean isLoadMore) {
        RetrofitApi.request(mContext,RetrofitApi.createApi(Api.class).getStoreActivityList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                assert view != null;
                view.attachhData(data,isLoadMore);
            }

            @Override
            public void onNotNetWork() {
                view.onError();
            }

            @Override
            public void onFail(Throwable e) {
                view.onError();
            }
        });
    }
}

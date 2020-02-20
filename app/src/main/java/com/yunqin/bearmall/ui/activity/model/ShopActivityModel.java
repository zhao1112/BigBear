package com.yunqin.bearmall.ui.activity.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.Menu;
import com.yunqin.bearmall.bean.ShopBean;
import com.yunqin.bearmall.ui.activity.contract.MenuActivtyContract;
import com.yunqin.bearmall.ui.activity.contract.ShopActivtyContract;

import java.util.Map;

/**
 * @author Master
 */
public class ShopActivityModel implements ShopActivtyContract.Model {


    private ShopBean shopBean = null;
    private Context mContext;
    private  ShopActivtyContract.shopDataCallBack shopDataCallBack;
    private  ShopActivtyContract.collectionShopCallBack collectionShopCallBack;

    public ShopActivityModel(Context mContext, ShopActivtyContract.shopDataCallBack shopDataCallBack,ShopActivtyContract.collectionShopCallBack collectionShopCallBack){
        this.mContext = mContext;
        this.shopDataCallBack = shopDataCallBack;
        this.collectionShopCallBack = collectionShopCallBack;
    }

    @Override
    public void getShopData(Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getShopData(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.d("getShopData", data);
                shopBean = new Gson().fromJson(data,ShopBean.class);
                shopDataCallBack.shopData(shopBean);
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
    public void collectionShop(Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).collectionShop(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                collectionShopCallBack.success();
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

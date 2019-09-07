package com.yunqin.bearmall.ui.fragment.model;

import android.content.Context;

import com.google.gson.Gson;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.MenuShop;
import com.yunqin.bearmall.inter.menuShopCallBack;
import com.yunqin.bearmall.ui.fragment.contract.MenuShopFragmentContract;

import java.util.Map;

/**
 * @author AYWang
 * @create 2018/7/13
 * @Describe
 */
public class MenuShopFragmentModel implements MenuShopFragmentContract.Model {

    private Context mContext;
    private menuShopCallBack menuShopCallBack;
    private  MenuShop menuShop;

    public MenuShopFragmentModel(Context mContext,menuShopCallBack menuShopCallBack){
        this.mContext = mContext;
        this.menuShopCallBack = menuShopCallBack;
    }

    @Override
    public void getMenuShopData(Map<String, String> map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getMenuShopData(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                menuShop = new Gson().fromJson(data,MenuShop.class);
                menuShopCallBack.menuShopCallBack(menuShop,false);
            }

            @Override
            public void onNotNetWork() {
                menuShopCallBack.onFail(new Error("no network"));
            }

            @Override
            public void onFail(Throwable e) {
                // TODO: 2018/7/13 待处理
                menuShopCallBack.onFail(e);
            }
        });
    }

    @Override
    public void loadMore(Map<String, String> map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getMenuShopData(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                menuShop = new Gson().fromJson(data,MenuShop.class);
                menuShopCallBack.menuShopCallBack(menuShop,true);
            }

            @Override
            public void onNotNetWork() {
                menuShopCallBack.onFail(new Error("no network"));
            }

            @Override
            public void onFail(Throwable e) {
                // TODO: 2018/7/13 待处理
                menuShopCallBack.onFail(e);
            }
        });
    }


    @Override
    public void searchData(Map<String, String> map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getMenuShopData(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                menuShop = new Gson().fromJson(data,MenuShop.class);
                menuShopCallBack.menuShopCallBack(menuShop,false);
            }

            @Override
            public void onNotNetWork() {
                menuShopCallBack.onFail(new Error("no network"));
            }

            @Override
            public void onFail(Throwable e) {
                // TODO: 2018/7/13 待处理
                menuShopCallBack.onFail(e);
            }
        });
    }
}

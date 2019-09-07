package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.BargainProductListBean;
import com.yunqin.bearmall.bean.MemberBargainProductListBean;
import com.yunqin.bearmall.ui.activity.contract.BargainFreeContact;

import java.util.Map;

public class BargainFreePresenter implements BargainFreeContact.Presenter {

    private BargainFreeContact.UI view;
    private Context mContext;

    public BargainFreePresenter(Context mContext, BargainFreeContact.UI view) {
        this.mContext = mContext;
        this.view = view;
    }

    @Override
    public void refresh(Map map) {
        map.put("productName", view.getSearchData());
        Log.e("getBargainProductList", map.toString());

        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getBargainProductList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("getBargainProductList", data.substring(0, data.length() / 3));
                Log.e("getBargainProductList", data.substring(data.length() / 3 + 1, 2 * data.length() / 3));
                Log.e("getBargainProductList", data.substring(2 * data.length() / 3 + 1, data.length()));
                BargainProductListBean bargainProductListBean = new Gson().fromJson(data, BargainProductListBean.class);
                view.attachhData(bargainProductListBean);
                view.saveJson(data);
            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);
            }

            @Override
            public void onNotNetWork() {
                view.onFail(new Error("无网络连接"));
            }
        });

    }

    @Override
    public void getMemberBargainProductList(Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getMemberBargainProductList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("MemberBargainProduct", data);
                MemberBargainProductListBean memberBargainProductListBean = new Gson().fromJson(data, MemberBargainProductListBean.class);
                view.attachMemberthData(memberBargainProductListBean);
            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);
            }

            @Override
            public void onNotNetWork() {

            }
        });

    }

    @Override
    public void getReceiverList(Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getReceiverList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("BargainAddressDialog", data);
                view.setReceiverList(data);

            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);

            }

            @Override
            public void onNotNetWork() {

            }
        });
    }

    @Override
    public void partBargain(Map map) {
        Log.e("partBargain", map.toString());
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).partBargain(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("partBargain", data);
                view.setPartBargain(data);
            }

            @Override
            public void onNotNetWork() {
                view.onFail(new Error("onNotNetWork"));
            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);

            }
        });
    }

    @Override
    public void getAdMobileList(Map map) {
        Log.e("getAdMobileList", map.toString());
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getAdMobileList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("getAdMobileList", data);
                view.getAdMobileList(data);
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);

            }
        });
    }

    @Override
    public void getAdMobileListMid(Map map) {
        Log.e("getAdMobileList", map.toString());
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getAdMobileList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("getAdMobileList", data);
                view.getAdMobileListMid(data);
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);

            }
        });
    }


    @Override
    public void refreshListData(Map map) {
        map.put("productName", view.getSearchData());
        Log.e("getBargainProductList", map.toString());

        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getBargainProductList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                BargainProductListBean bargainProductListBean = new Gson().fromJson(data, BargainProductListBean.class);
                view.attachListData(bargainProductListBean);
            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);
            }

            @Override
            public void onNotNetWork() {
                view.onFail(new Error("无网络连接"));
            }
        });
    }
}

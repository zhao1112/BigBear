package com.newversions.home.huiyuan;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.newversions.home.HomeBean;
import com.newversions.home.NewHomeAd;
import com.newversions.home.NewVersionHomeContract;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.BannerBean;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Create By Master
 * On 2019/1/3 17:12
 */
public class NewVersionHomePresenterTrue implements NewVersionHomeContractTrue.Presenter {

    private Context context;
    private NewVersionHomeContractTrue.View view;

    private int PAGE_NUMBER = 1;

    private boolean hasMore = false;

    private Map<String,String> mMap = new HashMap<>();


    public NewVersionHomePresenterTrue(Context context, NewVersionHomeContractTrue.View view) {
        this.context = context;
        this.view = view;
        mMap.put("positionType","5");
    }

    @Override
    public void init() {
        if (view != null) {
            view.showLoad();
            Map<String, String> mHashMap = new HashMap<>();
            mHashMap.put("page_size", "10");
            mHashMap.put("page_number", String.valueOf(PAGE_NUMBER));

            RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getMemberMallIndexData(mHashMap), new RetrofitApi.IResponseListener() {

                @Override
                public void onSuccess(String data) throws JSONException {
                    view.hideLoad();
                    try {
                        Log.e("TCP", data);
                        HuiYuanHomeBean homeBean = new Gson().fromJson(data, HuiYuanHomeBean.class);
                        view.attachData(homeBean);
                        hasMore = homeBean.getData().getHas_more() == 0 ? false : true;
                        view.onHasMore(hasMore);
                    } catch (Exception e) {
                    }

                }

                @Override
                public void onNotNetWork() {
                    view.hideLoad();
                    view.onNotNetWork();
                }

                @Override
                public void onFail(Throwable e) {
                    view.hideLoad();
                    view.onNotNetWork();
                }
            });

            RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getAdMobileList(mMap), new RetrofitApi.IResponseListener() {
                @Override
                public void onSuccess(String data) throws JSONException {
                    BannerBean homeAd = new Gson().fromJson(data, BannerBean.class);
                    view.attachBannerData(homeAd);
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

    @Override
    public void onRefresh() {
        if (view != null) {
            PAGE_NUMBER = 1;

            view.showLoad();
            Map<String, String> mHashMap = new HashMap<>();
            mHashMap.put("page_size", "10");
            mHashMap.put("page_number", String.valueOf(PAGE_NUMBER));

            RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getMemberMallIndexData(mHashMap), new RetrofitApi.IResponseListener() {

                @Override
                public void onSuccess(String data) throws JSONException {
                    view.hideLoad();
                    try {
                        Log.e("TCP", data);
                        HuiYuanHomeBean homeBean = new Gson().fromJson(data, HuiYuanHomeBean.class);
                        view.attachData(homeBean);
                        view.onRefreshFinish();
                        hasMore = homeBean.getData().getHas_more() == 0 ? false : true;
                        view.onHasMore(hasMore);
                    } catch (Exception e) {
                    }

                }

                @Override
                public void onNotNetWork() {
                    view.hideLoad();
                    view.onNotNetWork();
                    view.onRefreshFinish();
                }

                @Override
                public void onFail(Throwable e) {
                    view.hideLoad();
                    view.onNotNetWork();
                    view.onRefreshFinish();
                }
            });




            RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getAdMobileList(mMap), new RetrofitApi.IResponseListener() {
                @Override
                public void onSuccess(String data) throws JSONException {
                    BannerBean homeAd = new Gson().fromJson(data, BannerBean.class);
                    view.attachBannerData(homeAd);
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

    @Override
    public void onLoadMore() {
        if (view != null && hasMore) {
            PAGE_NUMBER++;

            Log.e("RRR","加载更多...");

            view.showLoad();
            Map<String, String> mHashMap = new HashMap<>();
            mHashMap.put("page_size", "10");
            mHashMap.put("page_number", String.valueOf(PAGE_NUMBER));

            RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getMemberMallIndexData(mHashMap), new RetrofitApi.IResponseListener() {

                @Override
                public void onSuccess(String data) throws JSONException {
                    view.hideLoad();
                    try {
                        Log.e("TCP", data);
                        HuiYuanHomeBean homeBean = new Gson().fromJson(data, HuiYuanHomeBean.class);
                        view.attachAddData(homeBean);
                        hasMore = homeBean.getData().getHas_more() == 0 ? false : true;
                        view.onHasMore(hasMore);
                    } catch (Exception e) {
                    }

                }

                @Override
                public void onNotNetWork() {
                    view.hideLoad();
                    view.onNotNetWork();
                    view.onLoadMoreFinish();
                }

                @Override
                public void onFail(Throwable e) {
                    view.hideLoad();
                    view.onNotNetWork();
                    view.onLoadMoreFinish();
                }
            });

        }
    }


}

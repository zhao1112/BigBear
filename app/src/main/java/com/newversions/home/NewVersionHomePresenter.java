package com.newversions.home;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.newversions.util.SharedPreferencesManager;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.util.DeviceUtils;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Create By Master
 * On 2019/1/3 17:12
 */
public class NewVersionHomePresenter implements NewVersionHomeContract.Presenter {

    private Context context;
    private NewVersionHomeContract.View view;

    private int PAGE_NUMBER = 1;

    private boolean hasMore = false;


    public NewVersionHomePresenter(Context context, NewVersionHomeContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void init() {
        if (view != null) {
            view.showLoad();
            Map<String, String> mHashMap = new HashMap<>();
            mHashMap.put("page_size", "10");
            mHashMap.put("page_number", String.valueOf(PAGE_NUMBER));

            RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getHomeListData(mHashMap), new RetrofitApi.IResponseListener() {

                @Override
                public void onSuccess(String data) throws JSONException {
                    view.hideLoad();
                    try {
                        Log.e("TCP", data);
                        HomeBean homeBean = new Gson().fromJson(data, HomeBean.class);
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

            RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getHomeAdData(), new RetrofitApi.IResponseListener() {
                @Override
                public void onSuccess(String data) throws JSONException {
                    NewHomeAd homeAd = new Gson().fromJson(data, NewHomeAd.class);
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

            RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getHomeListData(mHashMap), new RetrofitApi.IResponseListener() {

                @Override
                public void onSuccess(String data) throws JSONException {
                    view.hideLoad();
                    try {
                        Log.e("HomeData",data);
                        HomeBean homeBean = new Gson().fromJson(data, HomeBean.class);
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


            RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getHomeAdData(), new RetrofitApi.IResponseListener() {
                @Override
                public void onSuccess(String data) throws JSONException {
                    NewHomeAd homeAd = new Gson().fromJson(data, NewHomeAd.class);
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

            Log.e("RRR", "加载更多...");

            view.showLoad();
            Map<String, String> mHashMap = new HashMap<>();
            mHashMap.put("page_size", "10");
            mHashMap.put("page_number", String.valueOf(PAGE_NUMBER));

            RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getHomeListData(mHashMap), new RetrofitApi.IResponseListener() {

                @Override
                public void onSuccess(String data) throws JSONException {
                    view.hideLoad();
                    try {
                        Log.e("TCP", data);
                        HomeBean homeBean = new Gson().fromJson(data, HomeBean.class);
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

    @Override
    public void getLoanData() {
        // TODO: 2019/4/18 我要借钱
        if (view != null) {
            view.showLoad();
            HashMap map = new HashMap();
            map.put("deviceType","安卓");
            map.put("device", DeviceUtils.getUniqueId(context));

            RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getLoanSupermarketData(map), new RetrofitApi.IResponseListener() {

                @Override
                public void onSuccess(String data) throws JSONException {
                    view.hideLoad();
                    try {
                        view.loanData(data);
                    } catch (Exception e) {
                    }

                }

                @Override
                public void onNotNetWork() {
                    view.hideLoad();
                    view.loanError();
                }

                @Override
                public void onFail(Throwable e) {
                    view.hideLoad();
                    view.loanError();
                }
            });
        }
    }


}

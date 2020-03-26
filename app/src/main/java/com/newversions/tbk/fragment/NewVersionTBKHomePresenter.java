package com.newversions.tbk.fragment;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.newversions.tbk.entity.TBKHomeEntity;
import com.newversions.tbk.entity.TBKHomeGoodsEntity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.NewTBHome;
import com.yunqin.bearmall.util.DeviceUtils;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取数据
 * Create By Master
 * On 2019/1/3 17:12
 */
public class NewVersionTBKHomePresenter implements NewVersionTBKHomeContract.Presenter {

    private Context context;
    private NewVersionTBKHomeContract.View view;
    private int PAGE_NUMBER = 1;
    private boolean hasMore = false;

    public NewVersionTBKHomePresenter(Context context, NewVersionTBKHomeContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void init() {
        if (view != null) {
            Log.e("attachData2", "init: ");
            getHomeDate2();
        }
    }

    private void getHomeDate() {
        Map<String, String> mHashMap = new HashMap<>();
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getTBKHomeListData(mHashMap), new RetrofitApi.IResponseListener() {

            @Override
            public void onSuccess(String data) throws JSONException {
                view.hideLoad();
                try {
                    Log.e("TCP_DATA", data);
                    TBKHomeEntity tbkHomeEntity = new Gson().fromJson(data, TBKHomeEntity.class);
                    view.attachData(tbkHomeEntity);
                    view.onHasMore(true);
                    getHomeGoods();
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
    }

    private void getHomeDate2() {
        Log.e("attachData2", "getHomeDate2: ");
        Map<String, String> mHashMap = new HashMap<>();
        if (BearMallAplication.getInstance().getUser() != null && BearMallAplication.getInstance().getUser().getData() != null && BearMallAplication.getInstance().getUser().getData().getToken() != null && BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() != null) {
            mHashMap.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
        }
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getNewCommodityCategory(mHashMap),
                new RetrofitApi.IResponseListener() {

                    @Override
                    public void onSuccess(String data) throws JSONException {
                        view.hideLoad();
                        try {
                            NewTBHome newTBHome = new Gson().fromJson(data, NewTBHome.class);
                            view.TBHome(newTBHome);
                            view.onHasMore(true);
                            getHomeGoods();
                        } catch (Exception e) {
                            Log.e("TCP_DATA", e.getMessage());
                        }

                    }

                    @Override
                    public void onNotNetWork() {
                        Log.e("getHomeGoods", "onNotNetWork: ");
                        view.hideLoad();
                        view.onNotNetWork();
                    }

                    @Override
                    public void onFail(Throwable e) {
                        Log.e("getHomeGoods", e.getMessage());
                        view.hideLoad();
                        view.onNotNetWork();
                    }
                });
    }

    private void getHomeGoods() {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page", String.valueOf(PAGE_NUMBER));
        mHashMap.put("deviceNumber", DeviceUtils.getUniqueId(context));
        if (BearMallAplication.getInstance().getUser() != null && BearMallAplication.getInstance().getUser().getData() != null && BearMallAplication.getInstance().getUser().getData().getToken() != null && BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() != null) {
            mHashMap.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
        }
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getTBKHomeGoodsListData(mHashMap),
                new RetrofitApi.IResponseListener() {

                    @Override
                    public void onSuccess(String data) throws JSONException {
                        Log.e("getHomeGoods", data);
                        try {
                            Log.e("TCP", data);
                            TBKHomeGoodsEntity homeBean = new Gson().fromJson(data, TBKHomeGoodsEntity.class);
                            Log.e("TCP", "homeBean.getRecommend().size()" + homeBean.getRecommend().size() + "");
                            view.attachAddData(homeBean);
                            // TODO: 2019/7/13 0013 判断是否有更多
                            hasMore = PAGE_NUMBER < homeBean.getPages();
                            view.onHasMore(hasMore);
                            view.onRefreshFinish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNotNetWork() {
                        view.onNotNetWork();
                        view.onLoadMoreFinish();
                        view.onRefreshFinish();
                    }

                    @Override
                    public void onFail(Throwable e) {
                        Log.i("getHomeGoods", e.getMessage());
                        view.onNotNetWork();
                        view.onLoadMoreFinish();
                        view.onRefreshFinish();
                    }
                });
    }

    @Override
    public void onRefresh() {
        if (view != null) {
            PAGE_NUMBER = 1;
            getHomeDate2();
        }
    }

    @Override
    public void onLoadMore() {
        if (view != null && hasMore) {
            PAGE_NUMBER++;
            getHomeGoods();
        }
    }

}

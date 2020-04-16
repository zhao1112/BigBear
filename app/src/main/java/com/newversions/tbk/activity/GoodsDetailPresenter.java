package com.newversions.tbk.activity;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.newversions.tbk.entity.GoodDetailEntity;
import com.newversions.tbk.entity.LikeGuessEntity;
import com.newversions.tbk.entity.TBKHomeEntity;
import com.newversions.tbk.entity.TBKHomeGoodsEntity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.ContenGoods;
import com.yunqin.bearmall.util.DeviceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GoodsDetailPresenter implements GoodsDetailContract.Presenter {
    private Context context;
    private GoodsDetailContract.View view;
    private int page = 1;
    private boolean hasMore;

    public GoodsDetailPresenter(Context context, GoodsDetailContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void init(String goodsId) {
        view.showLoad();
        page = 1;
        Log.e("TCP_goods_detail", goodsId);
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("id", goodsId + "");
        if (BearMallAplication.getInstance().getUser() != null && BearMallAplication.getInstance().getUser().getData() != null && BearMallAplication.getInstance().getUser().getData().getToken() != null && BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() != null) {
            mHashMap.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
        }
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getGoodsDetails(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                view.hideLoad();
                try {
                    Log.e("TCP_goods_detail", data);
                    GoodDetailEntity tbkHomeEntity = new Gson().fromJson(data, GoodDetailEntity.class);
                    if (tbkHomeEntity.getGoodDetail() != null) {
                        view.attachData(tbkHomeEntity);
                        view.haseMore(true);
                    } else {
                        view.onNotNetWork();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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


    @Override
    public void getMoreLikeGoods(String goodsId) {

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("pageSize", "10");
        mHashMap.put("page", String.valueOf(page));
        mHashMap.put("deviceNumber", DeviceUtils.getUniqueId(context));
        mHashMap.put("itemId", goodsId);
        if (BearMallAplication.getInstance().getUser() != null && BearMallAplication.getInstance().getUser().getData() != null && BearMallAplication.getInstance().getUser().getData().getToken() != null && BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() != null) {
            mHashMap.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
        }
        Log.e("getHomeGoods", goodsId + "");
        Log.e("getHomeGoods", DeviceUtils.getUniqueId(context) + "");
        Log.e("getHomeGoods", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getTBKHomeGoodsListData(mHashMap),
                new RetrofitApi.IResponseListener() {

                    @Override
                    public void onSuccess(String data) throws JSONException {
                        view.hideLoad();
                        try {
                            Log.e("TCP", data);
                            TBKHomeGoodsEntity homeBean = new Gson().fromJson(data, TBKHomeGoodsEntity.class);
                            view.attachAddData(homeBean);
                            // TODO: 2019/7/13 0013 判断是否有更多
                            hasMore = page < homeBean.getPages();
                            page++;
                            view.haseMore(hasMore);
                            view.onLoadMoreFinish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNotNetWork() {
                        view.hideLoad();
                        view.onNotNetWork();
                        view.onLoadMoreFinish();
                        view.onRefreshFinish();
                    }

                    @Override
                    public void onFail(Throwable e) {
                        view.hideLoad();
                        view.onNotNetWork();
                        view.onLoadMoreFinish();
                        view.onRefreshFinish();
                    }
                });
    }

    @Override
    public void getTBKHomeGoodsListData(String goodsId) {
        //       view.showLoad();
        Map<String, String> mHashMap1 = new HashMap<>();
        mHashMap1.put("pageSize", "10");
        mHashMap1.put("page", String.valueOf(page));
        mHashMap1.put("deviceNumber", DeviceUtils.getUniqueId(context));
        mHashMap1.put("itemId", goodsId);
        if (BearMallAplication.getInstance().getUser() != null && BearMallAplication.getInstance().getUser().getData() != null && BearMallAplication.getInstance().getUser().getData().getToken() != null && BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() != null) {
            mHashMap1.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
        }
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getTBKHomeGoodsListData(mHashMap1),
                new RetrofitApi.IResponseListener() {

                    @Override
                    public void onSuccess(String data) throws JSONException {
                        view.hideLoad();
                        try {
                            Log.e("TCP", data);
                            TBKHomeGoodsEntity homeBean = new Gson().fromJson(data, TBKHomeGoodsEntity.class);
                            view.attachAddData(homeBean);
                            // TODO: 2019/7/13 0013 判断是否有更多
                            hasMore = page < homeBean.getPages();
                            page++;
                            view.haseMore(hasMore);
                            view.onRefreshFinish();
                        } catch (Exception e) {
                            e.printStackTrace();
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
    }


    @Override
    public void getContenGoods(String goodsID) {
        Map<String, String> mHashMap1 = new HashMap<>();
        mHashMap1.put("id", goodsID);
        mHashMap1.put("type", "1");
        mHashMap1.put("appKey", "02b0ee88e1c24bfcb5556640f34f16dc");
        RetrofitApi.request5(context, RetrofitApi.contenApi(Api.class,"https://h5api.m.taobao.com/h5/").contenGoods(mHashMap1), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                JSONObject object = new JSONObject(data);
                if (object != null) {
                    ContenGoods contenGoods = new Gson().fromJson(data, ContenGoods.class);
                    view.contenGoods(contenGoods);
                }
            }

            @Override
            public void onNotNetWork() {
                Log.i("getContenGoods", "000");
            }

            @Override
            public void onFail(Throwable e) {
                Log.i("getContenGoods", e.getMessage());
            }
        });
    }
}

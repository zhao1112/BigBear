package com.yunqin.bearmall.ui.fragment.presenter;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.BannerBean;
import com.yunqin.bearmall.bean.ZeroGoodsBean;
import com.yunqin.bearmall.ui.fragment.contract.ZeroGoodsContract;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AYWang
 * @create 2018/8/3
 * @Describe
 */
public class ZeroGoodsPresenter implements ZeroGoodsContract.presenter {

    private Context mContext;
    private ZeroGoodsContract.UI view;
    private Map map = new HashMap();
    private int page_number= 1;

    public ZeroGoodsPresenter(Context mContext, ZeroGoodsContract.UI view){
        this.view = view;
        this.mContext = mContext;
    }

    @Override
    public void start(boolean isLoadMore) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getGrouppurchasingIndex(getMap(isLoadMore)), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    ZeroGoodsBean zeroGoodsBean = new Gson().fromJson(data,ZeroGoodsBean.class);
                    view.attachData(zeroGoodsBean,isLoadMore);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNotNetWork() {
                view.noNet();
            }

            @Override
            public void onFail(Throwable e) {
                view.onError();
            }
        });
    }

    @Override
    public void getBannerData() {
        Map map = new HashMap();
        map.put("positionType","8");
        RetrofitApi.request(mContext,RetrofitApi.createApi(Api.class).getAdMobileList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                BannerBean bannerBean = new Gson().fromJson(data,BannerBean.class);
                assert view != null;
                view.attachBannerData(bannerBean);
            }

            @Override
            public void onNotNetWork() {
                view.noNet();
            }

            @Override
            public void onFail(Throwable e) {
                view.onError();
            }
        });
    }

    @Override
    public void getCenterAdData() {
        Map map = new HashMap();
        map.put("positionType","9");
        RetrofitApi.request(mContext,RetrofitApi.createApi(Api.class).getAdMobileList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                BannerBean bannerBean = new Gson().fromJson(data,BannerBean.class);
                assert view != null;
                view.attachCenterAdData(bannerBean);
            }

            @Override
            public void onNotNetWork() {
                view.noNet();
            }

            @Override
            public void onFail(Throwable e) {
                view.onError();
            }
        });

    }

    private Map getMap(boolean isLoadMore){
        map.clear();
        if(!isLoadMore){
            page_number = 1;
        }else {
            page_number++;
        }
        map.put("page_number",page_number+"");
        return map;
    }
}

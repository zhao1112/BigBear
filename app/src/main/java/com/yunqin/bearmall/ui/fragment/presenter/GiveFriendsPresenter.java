package com.yunqin.bearmall.ui.fragment.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.SweetsBt;
import com.yunqin.bearmall.ui.fragment.contract.GiveFriendsContract;

import java.util.Map;

/**
 * @author AYWang
 * @create 2018/7/20
 * @Describe
 */
public class GiveFriendsPresenter implements GiveFriendsContract.presenter {

    GiveFriendsContract.UI view;
    Context mContext;


    public GiveFriendsPresenter(Context mContext, GiveFriendsContract.UI view){
        this.view = view;
        this.mContext = mContext;
    }

    @Override
    public void refresh(Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getPointToOther(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                SweetsBt sweetsBt = new Gson().fromJson(data,SweetsBt.class);
                view.attachhData(sweetsBt);
            }

            @Override
            public void onNotNetWork() {
                view.onError();
                view.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {
                view.onError();
            }
        });
    }

}

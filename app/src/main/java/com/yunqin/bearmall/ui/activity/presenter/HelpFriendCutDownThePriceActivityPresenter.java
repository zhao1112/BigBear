package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.activity.contract.HelpFriendCutDownThePriceActivityContract;

import java.util.Map;

/**
 * @author AYWang
 * @create 2018/8/30
 * @Describe
 */
public class HelpFriendCutDownThePriceActivityPresenter implements HelpFriendCutDownThePriceActivityContract.Presenter {

    private Context mContext;
    private HelpFriendCutDownThePriceActivityContract.UI view;

    public HelpFriendCutDownThePriceActivityPresenter(Context mContext,HelpFriendCutDownThePriceActivityContract.UI view){
        this.mContext = mContext;
        this.view = view;
    }

    @Override
    public void getListData(Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getPastBargainList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                    view.attachListData(data);
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                view.onError();
                e.printStackTrace();
            }
        });
    }

    @Override
    public void getContentInfo(Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getHelpOthersBargainInfo(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                view.attachHelpOtherBargainInfo(data);
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                view.onError();
                e.printStackTrace();
            }
        });
    }
}

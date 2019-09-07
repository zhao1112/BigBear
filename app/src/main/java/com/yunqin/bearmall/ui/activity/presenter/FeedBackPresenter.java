package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.activity.contract.FeedBackContract;

import java.util.Map;

/**
 * @author AYWang
 * @create 2018/8/21
 * @Describe
 */
public class FeedBackPresenter implements FeedBackContract.Presenter {

    private FeedBackContract.UI view;
    public FeedBackPresenter(FeedBackContract.UI view){
        this.view = view;
    }

    @Override
    public void sendFeed(Context context, Map map) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).feedback(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    view.attachData(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNotNetWork() {

            }
            @Override
            public void onFail(Throwable e) {
                view.onFail(e);
                e.printStackTrace();
            }
        });

    }
}

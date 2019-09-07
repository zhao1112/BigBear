package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;

import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.activity.contract.LoginActivityContract;

import java.util.Map;

import io.reactivex.Scheduler;

/**
 * @author AYWang
 * @create 2018/7/19
 * @Describe
 */
public class LoginPresenter implements LoginActivityContract.presenter {

    Context mContext;

    LoginActivityContract.UI view;

    public LoginPresenter(Context mContext , LoginActivityContract.UI view){
        this.mContext = mContext;
        this.view = view;
    }
    @Override
    public void start(Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getMemberBindStatus(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                view.isBindPhone(data);
            }

            @Override
            public void onNotNetWork() {
                view.onerror();
            }

            @Override
            public void onFail(Throwable e) {
                view.onerror();
            }
        });
    }
}

package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.eventbus.ZeroActivityMessageEvent;
import com.yunqin.bearmall.ui.activity.contract.SettingContract;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AYWang
 * @create 2018/8/20
 * @Describe
 */
public class SettingPresenter implements SettingContract.Presenter {

    private Context mContext;
    private SettingContract.UI view;

    public SettingPresenter(Context mContext, SettingContract.UI view) {
        this.mContext = mContext;
        this.view = view;
    }

    @Override
    public void start(Context context) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getSettingMemberInfo(new HashMap<>()), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    assert view !=null;
                    view.attachData(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNotNetWork() {
                view.onError();
            }

            @Override
            public void onFail(Throwable e) {
                view.onError();
                e.printStackTrace();
            }
        });
    }

    @Override
    public void changeNickName(Context context, Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).updateMemberNickName(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    assert view !=null;
                    view.changeNickNameSussess();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                view.onFail();
                e.printStackTrace();
            }
        });
    }

    @Override
    public void bindWx(Context context, Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).memberThirdPartyBind(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    assert view !=null;
                    view.bindWx();
                } catch (Exception e) {
                    e.printStackTrace();
                }

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
    public void out(Context context) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).memberLoginOut(new HashMap<>()), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    assert view !=null;
                    view.outSussess();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                view.outFail();
                e.printStackTrace();
            }
        });
    }
}

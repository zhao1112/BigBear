package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;
import android.util.Log;

import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.activity.contract.HomeContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class HomePresenter implements HomeContract.Presenter {


    private HomeContract.UI view;

    public HomePresenter(HomeContract.UI view) {
        this.view = view;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void start(Context context) {

    }

    //获取购物车商品项总数量
    public void getCartItemCount(Context context, Map<String, String> map) {

        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getCartItemCount(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("getCartItemCount", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.getInt("code");

                    if (code == 1) {
                        view.getCartItemCount(data);//请求成功，数据返回给activity
                    }
                } catch (JSONException e) {
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


    public void getMessageItemCount(Context context, Map<String, String> map) {
        if (BearMallAplication.getInstance().getUser() == null) {
            return;
        }
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getUnreadMessageCount(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.getInt("code");

                    if (code == 1) {
                        view.getMessageItemCount(data);//请求成功，数据返回给activity
                    }
                } catch (JSONException e) {
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

package com.bbcoupon.ui.model;

import android.content.Context;

import com.bbcoupon.ui.bean.CustomInfor;
import com.bbcoupon.ui.bean.RequestInfor;
import com.bbcoupon.ui.bean.TutorInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.google.gson.Gson;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;

import org.json.JSONException;

import java.util.Map;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.model
 * @DATE 2020/4/22
 */
public class RequestModel implements RequestContract.RequestModel {

    //分享得糖果
    @Override
    public void onCandySharing(Context context, RequestContract.RequestView requestView, Map<String, String> map) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).shareArticleAndProduct(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                RequestInfor requestInfor = new Gson().fromJson(data, RequestInfor.class);
                if (requestView != null) {
                    requestView.onSuccess(requestInfor);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //获取导师微信
    @Override
    public void onTutorWx(Context context, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getTutorInfo(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                TutorInfor requestInfor = new Gson().fromJson(data, TutorInfor.class);
                if (requestView != null) {
                    requestView.onSuccess(requestInfor);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }

    //自定义商品
    @Override
    public void onCustom(Context context, Map<String, String> map, RequestContract.RequestView requestView) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getCustomItemLibraryList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                CustomInfor customInfor = new Gson().fromJson(data, CustomInfor.class);
                if (requestView != null) {
                    requestView.onSuccess(customInfor);
                }
            }

            @Override
            public void onNotNetWork() {
                if (requestView != null) {
                    requestView.onNotNetWork();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (requestView != null) {
                    requestView.onFail(e);
                }
            }
        });
    }
}

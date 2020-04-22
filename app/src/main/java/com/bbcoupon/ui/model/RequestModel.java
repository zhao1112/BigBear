package com.bbcoupon.ui.model;

import android.content.Context;

import com.bbcoupon.ui.bean.RequestInfor;
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
}

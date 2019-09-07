package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.adapter.AddressAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.AddressListBean;
import com.yunqin.bearmall.bean.WaitCommentBean;
import com.yunqin.bearmall.ui.activity.contract.AllCommentContract;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AYWang
 * @create 2018/7/26
 * @Describe
 */
public class AllCommentPresenter implements AllCommentContract.Present {

    private  AllCommentContract.UI view;
    private Context mContext;

    public AllCommentPresenter(Context mContext,AllCommentContract.UI view){
            this.view = view;
            this.mContext =  mContext;
    }

    @Override
    public void start(Context context) {
        Map<String, String> mHashMap = new HashMap<>();
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getReviewBasicInfo(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                WaitCommentBean waitCommentBean = new Gson().fromJson(data,WaitCommentBean.class);
                assert view!=null;
                view.attachData(waitCommentBean);
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }
}

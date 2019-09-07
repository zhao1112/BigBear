package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.ui.activity.contract.VanguardListPageContract;

import java.util.Map;

/**
 * @author AYWang
 * @create 2018/8/24
 * @Describe
 */
public class VanguardListPagePresenter implements VanguardListPageContract.Persenter {

    private Context mContext;
    private VanguardListPageContract.UI view;


    public VanguardListPagePresenter(Context mContext, VanguardListPageContract.UI view) {
        this.mContext = mContext;
        this.view = view;
    }

    @Override
    public void getShareData(Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getShareParams(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    ShareBean shareBean = new Gson().fromJson(data,ShareBean.class);
                    view.attach(shareBean);//请求成功，数据返回给activity
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
}

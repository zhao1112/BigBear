package com.newreward.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.newreward.contract.SpecialRequestContract;
import com.newreward.model.SpecialRequestModel;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.ShareBean;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AYWang
 * @create 2019/1/22
 * @Describe
 */
public class SpecialRequestPresenter implements SpecialRequestContract.Presenter {

    private Context mContext;
    private SpecialRequestContract.UI ui;
    private SpecialRequestContract.Model model;
    public SpecialRequestPresenter(Context mContext,SpecialRequestContract.UI ui){
        this.mContext = mContext;
        this.ui = ui;
        model = new SpecialRequestModel();
        initData();
    }

    private void initData() {
        RetrofitApi.request(mContext, model.getSpecInvitationPageInfo(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                ui.setSpecPageInfo(data);
            }

            @Override
            public void onNotNetWork() {
                ui.onNetFail();
            }

            @Override
            public void onFail(Throwable e) {
                ui.onNetFail();
            }
        });

    }

    @Override
    public void getShareData() {
        Map map = new HashMap();
        map.put("type",7+"");
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getShareParams(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                ShareBean shareBean = new Gson().fromJson(data,ShareBean.class);
                ui.setShareData(shareBean.getData());
            }

            @Override
            public void onNotNetWork() {
                ui.getShareDataFaile();
            }

            @Override
            public void onFail(Throwable e) {
                ui.getShareDataFaile();
            }
        });
    }
}

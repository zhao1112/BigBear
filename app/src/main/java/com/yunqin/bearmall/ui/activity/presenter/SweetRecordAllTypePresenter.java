package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.SweetRecordAllType;
import com.yunqin.bearmall.ui.activity.contract.SweetRecordWithTypeContract;
import com.yunqin.bearmall.ui.activity.model.RecordWithTypeModel;

/**
 * Created by chenchen on 2018/7/23.
 */

public class SweetRecordAllTypePresenter implements SweetRecordWithTypeContract.IPresent {

    private SweetRecordWithTypeContract.IUI view;
    private SweetRecordWithTypeContract.IModel model;

    public SweetRecordAllTypePresenter(SweetRecordWithTypeContract.IUI view) {
        this.view = view;
        this.model = new RecordWithTypeModel();
    }

    @Override
    public void start(Context context) {
        RetrofitApi.request(context, this.model.getMemberIncomeAllType(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.i("onSuccess", data);
                SweetRecordAllType bean = new Gson().fromJson(data,SweetRecordAllType.class);
                if (bean != null && bean.isSuccess()){
                    view.onGetData(bean);
                }
            }

            @Override
            public void onNotNetWork() {
                view.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

}

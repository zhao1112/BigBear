package com.yunqin.bearmall.ui.fragment.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.Voucher;
import com.yunqin.bearmall.ui.fragment.contract.MyTransferVoucherContract;

import java.util.Map;

/**
 * @author AYWang
 * @create 2018/7/21
 * @Describe
 */
public class MyTransferVoucherPersenter implements MyTransferVoucherContract.Persenter {

    MyTransferVoucherContract.UI view;
    Context mContext;

    public MyTransferVoucherPersenter(Context mContext,MyTransferVoucherContract.UI view){
        this.view = view;
        this.mContext = mContext;
    }

    @Override
    public void getData(Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getMemberTicketDetails(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Voucher voucher = new Gson().fromJson(data,Voucher.class);
                view.attach(voucher);
            }

            @Override
            public void onNotNetWork() {
                view.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {
                view.onError();
            }
        });
    }
}

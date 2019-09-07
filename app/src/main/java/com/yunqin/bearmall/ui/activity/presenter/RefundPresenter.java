package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.yunqin.bearmall.adapter.RefundAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.ShouHouBean;
import com.yunqin.bearmall.ui.activity.contract.RefundActivityContract;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Master
 * @create 2018/7/28 11:30
 */
public class RefundPresenter implements RefundActivityContract.Presenter {


    private Context iContext;
    private RefundActivityContract.UI iUI;


    private RefundAdapter refundAdapter;


    public RefundPresenter(Context iContext, RefundActivityContract.UI iUI) {
        this.iContext = iContext;
        this.iUI = iUI;
    }

    @Override
    public void init(Context context) {
        iUI.showLoad();
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "");
        mHashMap.put("page_number", "");
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getAfterSalesList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                ShouHouBean shouHouBean = new Gson().fromJson(data, ShouHouBean.class);
                isMore = shouHouBean.getData().getHas_more() == 0 ? false : true;
                refundAdapter = new RefundAdapter(iContext, shouHouBean.getData());
                iUI.attachAdapter(refundAdapter);
                iUI.hideLoad();
                iUI.jiaZaiGengDuoFinish(isMore);
            }

            @Override
            public void onNotNetWork() {
                iUI.hideLoad();
                iUI.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {
                iUI.attachAdapter(null);
                iUI.hideLoad();
                iUI.jiaZaiGengDuoFinish(isMore);
            }
        });
    }

    @Override
    public void shuaXin(Context context) {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "");
        mHashMap.put("page_number", "");
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getAfterSalesList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                page =  2;
                ShouHouBean shouHouBean = new Gson().fromJson(data, ShouHouBean.class);
                isMore = shouHouBean.getData().getHas_more() == 0 ? false : true;
                refundAdapter.setData(shouHouBean.getData());
                iUI.shuaXinFinish();
                iUI.jiaZaiGengDuoFinish(isMore);
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                iUI.shuaXinFinish();
                iUI.jiaZaiGengDuoFinish(isMore);
            }
        });
    }


    private boolean isMore;
    private int page = 2;

    @Override
    public void jiaZaiGengDuo(Context context) {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "");
        mHashMap.put("page_number", "" + page++);
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getAfterSalesList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                ShouHouBean shouHouBean = new Gson().fromJson(data, ShouHouBean.class);
                isMore = shouHouBean.getData().getHas_more() == 0 ? false : true;
                refundAdapter.addData(shouHouBean.getData());
                iUI.jiaZaiGengDuoFinish(isMore);
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                iUI.jiaZaiGengDuoFinish(isMore);
            }
        });
    }
}

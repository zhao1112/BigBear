package com.yunqin.bearmall.ui.fragment.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.ZeroGoodsBean;
import com.yunqin.bearmall.ui.fragment.contract.ZeroGoodsContract;
import com.yunqin.bearmall.ui.fragment.contract.ZeroMyContract;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AYWang
 * @create 2018/8/3
 * @Describe
 */
public class ZeroMyPresenter implements ZeroMyContract.presenter {

    private Context mContext;
    private ZeroMyContract.UI view;
    private Map map = new HashMap();
    private int page_number= 1;

    public ZeroMyPresenter(Context mContext, ZeroMyContract.UI view){
        this.view = view;
        this.mContext = mContext;
    }

    @Override
    public void start(boolean isLoadMore) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getMemberGrouppurchasingItemList(getMap(isLoadMore)), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    view.attachData(data,isLoadMore);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNotNetWork() {
                view.onNoNet();
            }

            @Override
            public void onFail(Throwable e) {
                view.onError();
            }
        });
    }

    private Map getMap(boolean isLoadMore){
        map.clear();
        if(!isLoadMore){
            page_number = 1;
        }else {
            page_number++;
        }
        map.put("page_number",page_number+"");
        return map;
    }
}

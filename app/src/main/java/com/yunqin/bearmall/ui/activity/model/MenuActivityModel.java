package com.yunqin.bearmall.ui.activity.model;

import android.content.Context;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.Menu;
import com.yunqin.bearmall.ui.activity.contract.MenuActivtyContract;

/**
 * @author Master
 */
public class MenuActivityModel implements MenuActivtyContract.Model {


    private Menu menu = null;
    private Context mContext;
    private  MenuActivtyContract.menuDataCallBack menuDataCallBack;

    public MenuActivityModel(Context mContext,MenuActivtyContract.menuDataCallBack menuDataCallBack){
        this.mContext = mContext;
        this.menuDataCallBack = menuDataCallBack;
    }

    @Override
    public void getMenuData() {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getMenuDataTwo(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                menu = new Gson().fromJson(data,Menu.class);
                menuDataCallBack.menuData(menu);
            }

            @Override
            public void onNotNetWork() {
                menuDataCallBack.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }
}

package com.yunqin.bearmall.ui.fragment.model;

import android.content.Context;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.fragment.contract.MenuGoodsContract;
import com.yunqin.bearmall.ui.fragment.contract.ShopGoodsContract;

import java.util.HashMap;
import java.util.Map;

public class ShopGoodsModel implements ShopGoodsContract.Model {

    ShopGoodsContract.onPresenterModelListener onPresenterModelListener;

    public ShopGoodsModel(ShopGoodsContract.onPresenterModelListener onPresenterModelListener) {
        this.onPresenterModelListener = onPresenterModelListener;
    }

    @Override
    public void getShopProductList(Context context,Map map) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getShopGoodsData(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                if (onPresenterModelListener != null) {
                    onPresenterModelListener.onSuccess(data);
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                if (onPresenterModelListener != null) {
                    onPresenterModelListener.onFail();
                }
            }
        });
    }

}

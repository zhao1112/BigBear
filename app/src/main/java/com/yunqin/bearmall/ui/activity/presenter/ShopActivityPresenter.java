package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;

import com.yunqin.bearmall.adapter.MoreTypeViewAdapter;
import com.yunqin.bearmall.bean.ShopBean;
import com.yunqin.bearmall.ui.activity.contract.ConfirmActivityContract;
import com.yunqin.bearmall.ui.activity.contract.ShopActivtyContract;
import com.yunqin.bearmall.ui.activity.model.ShopActivityModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShopActivityPresenter implements ShopActivtyContract.Persenter,ShopActivtyContract.shopDataCallBack,ShopActivtyContract.collectionShopCallBack {

    Context mContext;
    ShopActivtyContract.UI mView;
    ShopActivtyContract.Model model;
    ShopBean shopBean;

    public ShopActivityPresenter(Context context, ShopActivtyContract.UI view) {
        mContext = context;
        mView = view;
        model = new ShopActivityModel(mContext,this,this);
    }

    @Override
    public void shopData(ShopBean shopBean) {
        mView.initBinner(shopBean.getData().getAdList());
        mView.initXtableLayout(shopBean.getData().getTagList());
        mView.initTopLayout(shopBean);
    }

    @Override
    public void star() {
        model.getShopData(mView.getParams());
    }

    @Override
    public void collectionShop(Map map) {
        model.collectionShop(map);
    }

    //收藏取消收藏  请求成功
    @Override
    public void success() {
        mView.isCollectionShop();
    }
}

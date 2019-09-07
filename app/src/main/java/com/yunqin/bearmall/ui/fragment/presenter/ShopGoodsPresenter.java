package com.yunqin.bearmall.ui.fragment.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yunqin.bearmall.adapter.MenuShopGoodsAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.ShopGoods;
import com.yunqin.bearmall.ui.fragment.contract.ShopGoodsContract;
import com.yunqin.bearmall.ui.fragment.model.ShopGoodsModel;

import java.util.Map;

public class ShopGoodsPresenter implements ShopGoodsContract.Presenter, ShopGoodsContract.onPresenterModelListener {


    private ShopGoodsContract.UI view;
    private ShopGoodsContract.Model model;
    private Context context;
    private MenuShopGoodsAdapter mMenuShopGoodsAdapter;


    public ShopGoodsPresenter(Context context, ShopGoodsContract.UI view) {
        this.context = context;
        this.view = view;
        this.model = new ShopGoodsModel(this);
    }

    @Override
    public void onSuccess(String data) {
        ShopGoods shopGoods;
        try {
            shopGoods = new Gson().fromJson(data, ShopGoods.class);
        } catch (JsonSyntaxException e) {
            shopGoods = null;
        }

        mMenuShopGoodsAdapter = new MenuShopGoodsAdapter(context, shopGoods);
        view.onHasMore(shopGoods.getData().getHas_more() == 1);
        view.attachAdapter(mMenuShopGoodsAdapter, shopGoods);
    }

    @Override
    public void onFail() {
        view.onHasMore(false);
    }

    @Override
    public void star() {
        model.getShopProductList(context, view.getParams());
    }

    @Override
    public void onShuaXin() {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getShopGoodsData(view.getParams()), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                page_number = 2;
                ShopGoods shopGoods = new Gson().fromJson(data, ShopGoods.class);
                mMenuShopGoodsAdapter.setData(shopGoods);
                view.shuaXinFinish();
                view.onHasMore(shopGoods.getData().getHas_more() == 1);
            }

            @Override
            public void onNotNetWork() {
                view.shuaXinFinish();
                view.onHasMore(false);
            }

            @Override
            public void onFail(Throwable e) {
                view.shuaXinFinish();
                view.onHasMore(false);
            }
        });
    }


    private int page_number = 2;

    @Override
    public void onJiaZai() {
        Map<String, String> mHashMap = view.getParams();
        mHashMap.put("page_number", String.valueOf(page_number++));

        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getShopGoodsData(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                ShopGoods shopGoods = new Gson().fromJson(data, ShopGoods.class);
                mMenuShopGoodsAdapter.addData(shopGoods);
                view.jiaZaiFinish();
                view.onHasMore(shopGoods.getData().getHas_more() == 1);
            }

            @Override
            public void onNotNetWork() {
                view.jiaZaiFinish();
                view.onHasMore(false);
            }

            @Override
            public void onFail(Throwable e) {
                view.jiaZaiFinish();
                view.onHasMore(false);
            }
        });
    }
}

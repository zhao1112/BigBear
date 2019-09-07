package com.yunqin.bearmall.ui.fragment.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yunqin.bearmall.adapter.MenuGoodsAdapter;
import com.yunqin.bearmall.bean.MenuGoods;
import com.yunqin.bearmall.ui.fragment.contract.MenuGoodsContract;
import com.yunqin.bearmall.ui.fragment.model.MenuGoodsModel;

public class MenuGoodsPresenter implements MenuGoodsContract.Presenter, MenuGoodsContract.onPresenterModelListener {


    private MenuGoodsContract.UI view;
    private MenuGoodsContract.Model model;
    private Context context;
    private int mRefreshPage = 2;

    public MenuGoodsPresenter(Context context, MenuGoodsContract.UI view) {
        this.context = context;
        this.view = view;
        this.model = new MenuGoodsModel(this);
    }

    @Override
    public void priceRank(int rank) {
        mRefreshPage = 2;
        if (rank == MenuGoodsContract.Rank.UP) {
            model.getMenuProductList(context, "PRICE_ASC", view.getValueType(), view.getCategory_id());
        } else if (rank == MenuGoodsContract.Rank.DOWN) {
            // TODO 降序排列
            model.getMenuProductList(context, "PRICE_DESC", view.getValueType(), view.getCategory_id());
        } else {
            model.getMenuProductList(context, "", view.getValueType(), view.getCategory_id());
        }
    }

    @Override
    public void salesRank() {
        // TODO 销量排序
        model.loadSales(context, view.getValueType(), view.getCategory_id());
    }

    @Override
    public void scoreRank() {
        // TODO 评分排序
        model.loadScore(context, view.getValueType(), view.getCategory_id());
    }

    @Override
    public void startBundle(String data) {
        onSuccess(data, false);
    }

    @Override
    public void loadMore(int  rank) {

        String orderType = "";

        if (rank == MenuGoodsContract.Rank.UP) {
            orderType = "PRICE_ASC";
        } else if (rank == MenuGoodsContract.Rank.DOWN) {
            orderType = "PRICE_DESC";
        } else {
            orderType = "";
        }

        model.loadMore(context, mRefreshPage++, view.getValueType(), view.getCategory_id(),orderType);
    }


    private MenuGoodsAdapter mMenuGoodsAdapter;


    @Override
    public void onSuccess(String data, boolean hasAdd) {
        view.hideLoad();
        MenuGoods menuGoods;
        try {
            menuGoods = new Gson().fromJson(data, MenuGoods.class);
        } catch (JsonSyntaxException e) {
            menuGoods = null;
        }

        if (mMenuGoodsAdapter == null) {
            mMenuGoodsAdapter = new MenuGoodsAdapter(context, menuGoods);
            view.attachAdapter(mMenuGoodsAdapter);
            view.finishRefresh(menuGoods.getData().getHas_more() == 1);

            view.onHasMore(menuGoods.getData().getHas_more() == 1);

            return;
        } else if (hasAdd) {
            mMenuGoodsAdapter.addData(menuGoods);
            view.updateView(mMenuGoodsAdapter);
            view.finishRefresh(menuGoods.getData().getHas_more() == 1);
            view.onHasMore(menuGoods.getData().getHas_more() == 1);
            return;
        } else {
            mMenuGoodsAdapter.setData(menuGoods);
            view.updateView(mMenuGoodsAdapter);
            view.finishRefresh(menuGoods.getData().getHas_more() == 1);
            view.onHasMore(menuGoods.getData().getHas_more() == 1);
        }
    }

    @Override
    public void onFail() {
        view.attachAdapter(null);
        view.hideLoad();
        view.finishRefresh(false);
        view.onHasMore(false);
    }


    @Override
    public void updateSearch(String searchData, String attr_) {
        mRefreshPage = 2;
        view.showLoad();
        model.updateSearch(context, searchData, attr_);
    }


    @Override
    public MenuGoodsAdapter getData() {
        return mMenuGoodsAdapter;
    }
}

package com.yunqin.bearmall.ui.fragment.presenter;

import android.content.Context;

import com.yunqin.bearmall.adapter.MenuShopsAdapter;
import com.yunqin.bearmall.bean.MenuShop;
import com.yunqin.bearmall.inter.menuShopCallBack;
import com.yunqin.bearmall.ui.fragment.contract.MenuShopFragmentContract;
import com.yunqin.bearmall.ui.fragment.model.MenuShopFragmentModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AYWang
 * @create 2018/7/13
 * @Describe
 */
public class MenuShopFragmentPresenter implements MenuShopFragmentContract.Presenter, menuShopCallBack {

    private MenuShopFragmentContract.UI view;
    private MenuShopFragmentContract.Model model;
    private Context mContext;

    public MenuShopFragmentPresenter(MenuShopFragmentContract.UI view, Context mContext) {
        this.mContext = mContext;
        this.view = view;
        this.model = new MenuShopFragmentModel(mContext, this);
    }


    @Override
    public void start(Context context) {

    }

    @Override
    public void start(Context context, String searchValue, String Category_id) {
        index = 1;
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("searchValue", searchValue);
        mHashMap.put("category_id", Category_id);
        model.getMenuShopData(mHashMap);
    }


    private int index = 1;

    @Override
    public void loadMore(Context context, String searchValue, String Category_id) {
        index++;
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("searchValue", searchValue);
        mHashMap.put("category_id", Category_id);
        mHashMap.put("page_number", String.valueOf(index));
        model.loadMore(mHashMap);
    }


    private MenuShopsAdapter menuShopsAdapter;


    private MenuShop mMenuShop;


    @Override
    public void menuShopCallBack(MenuShop menuShop, boolean hasAdd) {
        try {
            mMenuShop = menuShop;
            if (menuShopsAdapter == null) {
                menuShopsAdapter = new MenuShopsAdapter(mContext, menuShop.getData().getStoreList());
                view.attachAdapter(menuShopsAdapter);
                view.hideLoading(menuShop.getData().getHas_more() == 1);
                view.onHasMore(menuShop.getData().getHas_more() == 1);

                return;
            } else if (hasAdd) {
                menuShopsAdapter.addData(menuShop.getData().getStoreList());
                view.updateView(menuShopsAdapter);
                view.hideLoading(menuShop.getData().getHas_more() == 1);
                view.onHasMore(menuShop.getData().getHas_more() == 1);
                return;
            } else {
                menuShopsAdapter.setData(menuShop.getData().getStoreList());
                view.updateView(menuShopsAdapter);
                view.hideLoading(menuShop.getData().getHas_more() == 1);
                view.onHasMore(menuShop.getData().getHas_more() == 1);
            }
        } catch (Exception e) {
            view.hideLoading(false);
            view.onHasMore(false);
        }
    }

    @Override
    public void onFail(Throwable throwable) {
        view.hideLoading(false);
        view.onHasMore(false);
    }


    @Override
    public void updateSearch(String searchData) {
        index = 1;
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("searchValue", searchData);
        model.searchData(mHashMap);
    }

    @Override
    public MenuShop getData() {
        return mMenuShop;
    }
}

package com.yunqin.bearmall.ui.fragment.model;

import android.content.Context;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.fragment.contract.MenuGoodsContract;

import java.util.HashMap;
import java.util.Map;

public class MenuGoodsModel implements MenuGoodsContract.Model {

    MenuGoodsContract.onPresenterModelListener onPresenterModelListener;

    public MenuGoodsModel(MenuGoodsContract.onPresenterModelListener onPresenterModelListener) {
        this.onPresenterModelListener = onPresenterModelListener;
    }

    @Override
    public void getMenuProductList(Context context, String orderType, String value,String category_id) {

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "10");
        mHashMap.put("page_number", "1");
        mHashMap.put("category_id", category_id);
        mHashMap.put("brand_id", "");
        mHashMap.put("orderType", orderType);
        mHashMap.put("searchValue", value);
        mHashMap.put("attr_", "");

        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getMenuProductList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                if (onPresenterModelListener != null) {
                    onPresenterModelListener.onSuccess(data,false);
                }
            }

            @Override
            public void onNotNetWork() {
                if (onPresenterModelListener != null) {
                    onPresenterModelListener.onFail();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (onPresenterModelListener != null) {
                    onPresenterModelListener.onFail();
                }
            }
        });
    }


    @Override
    public void loadMore(Context context, int page, String searchValue, String category_id,String orderType) {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "10");
        mHashMap.put("page_number", "" + page);
        mHashMap.put("category_id", category_id);
        mHashMap.put("brand_id", "");
        mHashMap.put("orderType", orderType);
        mHashMap.put("searchValue", searchValue);
        mHashMap.put("attr_", "");


        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getMenuProductList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                if (onPresenterModelListener != null) {
                    onPresenterModelListener.onSuccess(data,true);
                }
            }

            @Override
            public void onNotNetWork() {
                if (onPresenterModelListener != null) {
                    onPresenterModelListener.onFail();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (onPresenterModelListener != null) {
                    onPresenterModelListener.onFail();
                }
            }
        });
    }

    @Override
    public void loadSales(Context context, String value, String category_id) {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "10");
        mHashMap.put("page_number", "1");
        mHashMap.put("category_id", category_id);
        mHashMap.put("brand_id", "");
        mHashMap.put("orderType", "SALES_DESC");
        mHashMap.put("searchValue", value);
        mHashMap.put("attr_", "");

        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getMenuProductList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                if (onPresenterModelListener != null) {
                    onPresenterModelListener.onSuccess(data,false);
                }
            }

            @Override
            public void onNotNetWork() {
                if (onPresenterModelListener != null) {
                    onPresenterModelListener.onFail();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (onPresenterModelListener != null) {
                    onPresenterModelListener.onFail();
                }
            }
        });
    }

    @Override
    public void loadScore(Context context, String value, String category_id) {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "10");
        mHashMap.put("page_number", "1");
        mHashMap.put("category_id", category_id);
        mHashMap.put("brand_id", "");
        mHashMap.put("orderType", "SCORE_DESC");
        mHashMap.put("searchValue", value);
        mHashMap.put("attr_", "");

        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getMenuProductList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                if (onPresenterModelListener != null) {
                    onPresenterModelListener.onSuccess(data, false);
                }
            }

            @Override
            public void onNotNetWork() {
                if (onPresenterModelListener != null) {
                    onPresenterModelListener.onFail();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (onPresenterModelListener != null) {
                    onPresenterModelListener.onFail();
                }
            }
        });
    }


    @Override
    public void updateSearch(Context context, String searchData,String attr_) {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "10");
        mHashMap.put("page_number", "1");
        mHashMap.put("category_id", "");
        mHashMap.put("brand_id", "");
        mHashMap.put("orderType", "");
        mHashMap.put("searchValue", searchData);
        mHashMap.put("attrList", attr_);

        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getMenuProductList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                if (onPresenterModelListener != null) {
                    onPresenterModelListener.onSuccess(data, false);
                }
            }

            @Override
            public void onNotNetWork() {
                if (onPresenterModelListener != null) {
                    onPresenterModelListener.onFail();
                }
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

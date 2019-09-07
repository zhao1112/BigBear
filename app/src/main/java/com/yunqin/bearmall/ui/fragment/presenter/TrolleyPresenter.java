package com.yunqin.bearmall.ui.fragment.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.CartItemCount;
import com.yunqin.bearmall.ui.fragment.contract.TrolleyContract;
import com.yunqin.bearmall.ui.fragment.model.TrolleyModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class TrolleyPresenter implements TrolleyContract.Presenter {
    private TrolleyContract.Model model;
    private TrolleyContract.UI view;

    public TrolleyPresenter(TrolleyContract.UI view) {
        this.view = view;
        model = new TrolleyModel();
    }

    @Override
    public void start(Context context) {

    }

    //获取购物车列表
    public void getCartItemData(Context context, Map map) {

        RetrofitApi.request(context, model.getCartItemList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("getCartItemData", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.getInt("code");

                    if (code == 1) {
                        view.getCartItemList(data);//请求成功，数据返回给activity
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                view.onShowNotNetView(false);
            }

            @Override
            public void onNotNetWork() {
                view.onShowNotNetView(true);
            }

            @Override
            public void onFail(Throwable e) {
                view.onShowNotNetView(true);
                view.onFail(e);
                e.printStackTrace();

            }
        });

    }

    //购物车商品项移入收藏
    public void removeCartItemsForFavorite(Context context, Map map) {

        RetrofitApi.request(context, model.removeCartItemsForFavorite(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("CartItemsForFavorite", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.getInt("code");

                    if (code == 1) {
                        view.removeCartItemsForFavorite(data);//请求成功，数据返回给fragment
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                view.onShowNotNetView(false);

            }

            @Override
            public void onNotNetWork() {
                view.onShowNotNetView(true);
            }

            @Override
            public void onFail(Throwable e) {
                view.onShowNotNetView(true);
                view.onFail(e);
                e.printStackTrace();
            }
        });

    }

    //购物车商品项移除
    public void removeCartItems(Context context, Map map) {

        RetrofitApi.request(context, model.removeCartItems(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("removeCartItems", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.getInt("code");

                    if (code == 1) {
                        view.removeCartItems(data);//请求成功，数据返回给activity
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                view.onShowNotNetView(false);
            }

            @Override
            public void onNotNetWork() {
                view.onShowNotNetView(true);
            }

            @Override
            public void onFail(Throwable e) {
                view.onShowNotNetView(true);
                view.onFail(e);
                e.printStackTrace();
            }
        });

    }

    //购物车商品项数量调整
    public void setItemQuantity(Context context, Map map) {

        RetrofitApi.request(context, model.setItemQuantity(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("setItemQuantity", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.getInt("code");

                    if (code == 1) {
                        view.setItemQuantity(data);//请求成功，数据返回给activity
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                view.onShowNotNetView(false);
            }

            @Override
            public void onNotNetWork() {
                view.onShowNotNetView(true);
            }

            @Override
            public void onFail(Throwable e) {
                view.onShowNotNetView(true);
                view.onFail(e);
                e.printStackTrace();
            }
        });

    }


    //获取购物车商品项总数量
    public void getCartItemCount(Context context, Map map) {

        RetrofitApi.request(context, model.getCartItemCount(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("getCartItemCount", data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.getInt("code");

                    if (code == 1) {
                        view.getCartItemCount(data);//请求成功，数据返回给activity

                        //如果购物总数大于0，就说明说数据，此时再请求购物车列表接口
                        CartItemCount cartItemCount = new Gson().fromJson(data, CartItemCount.class);
                        if (cartItemCount.getData().getItemCount() > 0) {
                            getCartItemData(context, map);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                view.onShowNotNetView(false);
            }

            @Override
            public void onNotNetWork() {
                view.onShowNotNetView(true);
            }

            @Override
            public void onFail(Throwable e) {
                view.onShowNotNetView(true);
                view.onFail(e);
                e.printStackTrace();
            }
        });

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void onDestroy() {
        assert view != null;
        view = null;
    }
}

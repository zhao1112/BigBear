package com.yunqin.bearmall.ui.activity.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.gson.Gson;
import com.yunqin.bearmall.adapter.MoreTypeViewAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.CartItemCount;
import com.yunqin.bearmall.bean.Channel;
import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.eventbus.ProductDetailCommentMessageEvent;
import com.yunqin.bearmall.eventbus.ProductDetailMessageEvent;
import com.yunqin.bearmall.eventbus.ProductMessageEvent;
import com.yunqin.bearmall.ui.activity.contract.ConfirmActivityContract;
import com.yunqin.bearmall.ui.activity.contract.ProductActivityContract;
import com.yunqin.bearmall.ui.activity.model.ProductDetailActivityModel;
import com.yunqin.bearmall.ui.fragment.ProductCommentFragment;
import com.yunqin.bearmall.ui.fragment.ProductDetailFragment;
import com.yunqin.bearmall.ui.fragment.ProductFragment;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductActivityPresenter implements ProductActivityContract.Presenter {

    private ProductActivityContract.UI view;
    private ProductActivityContract.Model model;

    public ProductActivityPresenter(ProductActivityContract.UI view) {
        this.view = view;
        this.model = new ProductDetailActivityModel();
    }

    @Override
    public void start(Context context) {

        //构建viewpager适配器数据
        List<Fragment> mFragmentList = new ArrayList<Fragment>();
        Fragment fragmentProduct = new ProductFragment();
        Fragment fragmentProductDetail = new ProductDetailFragment();
        Fragment fragmentProductComment = new ProductCommentFragment();

        mFragmentList.add(fragmentProduct);
        mFragmentList.add(fragmentProductDetail);
        mFragmentList.add(fragmentProductComment);


        List<String> mListTitle = new ArrayList<String>();

        mListTitle.add("商品");
        mListTitle.add("详情");
        mListTitle.add("评论");
        view.attachTitle(mFragmentList,mListTitle);
    }

    //获取商品基础数据
    public void getProductData(Context context, Map map){
        start(context);

        RetrofitApi.request(context, model.getProductData(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("getProductData",data.substring(0,data.length()/3));
                Log.e("getProductData",data.substring(data.length()/3+1,2*data.length()/3));
                Log.e("getProductData",data.substring(2*data.length()/3+1,data.length()));
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        view.getProductData(data);//请求成功，数据返回给activity
                        EventBus.getDefault().post(new ProductMessageEvent(data));//向ProductFragment发送请求成功的数据
//                        EventBus.getDefault().post(new ProductDetailMessageEvent(data));//ProductDetailFragment发送请求成功的数据
//                        EventBus.getDefault().post(new ProductDetailCommentMessageEvent(data));//向ProductCommentFragment发送请求成功的数据
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);
                e.printStackTrace();
            }
        });


    }

    //商品收藏/取消收藏
    public void setFavorite(Context context, Map map){

        RetrofitApi.request(context, model.setFavorite(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("setFavorite",data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        view.setFavorite(data);//请求成功，数据返回给activity
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);
                e.printStackTrace();
            }
        });
    }

    //商品加入购物车
    public void joinCart(Context context, Map map){

        RetrofitApi.request(context, model.joinCart(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("setFavorite",data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        view.joinCart(data);//请求成功，数据返回给activity
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);
                e.printStackTrace();
            }
        });


    }

    //获取购物车商品项总数量
    public void getCartItemCount(Context context,Map<String,String> map){

        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getCartItemCount(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("getCartItemCount",data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        view.getCartItemCount(data);//请求成功，数据返回给activity

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);
                e.printStackTrace();
            }
        });

    }

    //分享
    public void getShareData(Context mContext,Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getShareParams(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    ShareBean shareBean = new Gson().fromJson(data,ShareBean.class);
                    view.share(shareBean);//请求成功，数据返回给activity
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
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

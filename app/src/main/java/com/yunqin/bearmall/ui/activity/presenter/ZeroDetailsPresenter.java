package com.yunqin.bearmall.ui.activity.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.eventbus.ProductMessageEvent;
import com.yunqin.bearmall.eventbus.ZeroActivityMessageEvent;
import com.yunqin.bearmall.ui.activity.contract.ProductActivityContract;
import com.yunqin.bearmall.ui.activity.contract.ZeroDetailsContract;
import com.yunqin.bearmall.ui.activity.model.ProductDetailActivityModel;
import com.yunqin.bearmall.ui.fragment.ProductCommentFragment;
import com.yunqin.bearmall.ui.fragment.ProductDetailFragment;
import com.yunqin.bearmall.ui.fragment.ProductFragment;
import com.yunqin.bearmall.ui.fragment.ZeroActivity.FragmentZeroComment;
import com.yunqin.bearmall.ui.fragment.ZeroActivity.FragmentZeroDetails;
import com.yunqin.bearmall.ui.fragment.ZeroActivity.FragmentZeroSecond;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ZeroDetailsPresenter implements ZeroDetailsContract.Presenter {

    private Context mContext;
    private ZeroDetailsContract.UI view;
    public ZeroDetailsPresenter(Context mContext, ZeroDetailsContract.UI view) {
        this.mContext = mContext;
        this.view = view;
    }


    @Override
    public void getShareData(Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getShareParams(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    ShareBean shareBean = new Gson().fromJson(data,ShareBean.class);
                    view.attachSahreBean(shareBean);//请求成功，数据返回给activity
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


    @Override
    public void start(Context context) {
        //构建viewpager适配器数据
        List<Fragment> mFragmentList = new ArrayList<Fragment>();
        //todo  不适用  需要new新的
        Fragment fragmentProduct = new FragmentZeroDetails();
        Fragment fragmentProductDetail = new FragmentZeroSecond();
        Fragment fragmentProductComment = new FragmentZeroComment();

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
    @Override
    public void getZeroDetailstData(Map map){
        start(mContext);

        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getGrouppurchasingItemDetails(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        view.getZeroDetailstData(data);//请求成功，数据返回给activity
                        EventBus.getDefault().post(new ZeroActivityMessageEvent(data));//向ProductFragment发送请求成功的数据
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
    @Override
    public void setFavorite(Map map){

        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).setFavorite(map), new RetrofitApi.IResponseListener() {
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

    @Override
    public void join(Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).partGrouppurchasing(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("setFavorite",data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.getInt("code");
                    if(code == 1){
                        String msg = jsonObject.optString("msg");
                        view.joinZeroActivity(msg);//请求成功，数据返回给activity
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

    @Override
    public void getReceiverList(Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getReceiverList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("BargainAddressDialog",data);
                view.setReceiverList(data);

            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);

            }

            @Override
            public void onNotNetWork() {

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

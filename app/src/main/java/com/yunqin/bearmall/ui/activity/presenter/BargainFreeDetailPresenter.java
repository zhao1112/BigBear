package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.yunqin.bearmall.adapter.BargainAddressAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.AddressListBean;
import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.eventbus.BargainFreeEvent;
import com.yunqin.bearmall.eventbus.ProductMessageEvent;
import com.yunqin.bearmall.ui.activity.BargainFreeShareActivity;
import com.yunqin.bearmall.ui.activity.contract.BargainFreeDetailContract;
import com.yunqin.bearmall.ui.activity.model.BargainFreeDetailModel;
import com.yunqin.bearmall.ui.fragment.BargainFreeCommentFragment;
import com.yunqin.bearmall.ui.fragment.BargainFreeDetailFragment;
import com.yunqin.bearmall.ui.fragment.BargainFreeFragment;
import com.yunqin.bearmall.ui.fragment.ProductCommentFragment;
import com.yunqin.bearmall.ui.fragment.ProductDetailFragment;
import com.yunqin.bearmall.ui.fragment.ProductFragment;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BargainFreeDetailPresenter implements BargainFreeDetailContract.Presenter{

    private BargainFreeDetailContract.UI view;
    private BargainFreeDetailContract.Model model;

    public BargainFreeDetailPresenter(BargainFreeDetailContract.UI view) {
        this.view = view;
        this.model = new BargainFreeDetailModel();
    }

    @Override
    public void start(Context context) {

        //构建viewpager适配器数据
        List<Fragment> mFragmentList = new ArrayList<Fragment>();
        Fragment fragmentBargainFree = new BargainFreeFragment();
        Fragment fragmentBargainFreeDetail = new BargainFreeDetailFragment();
        Fragment fragmentBargainFreeComment = new BargainFreeCommentFragment();

        mFragmentList.add(fragmentBargainFree);
        mFragmentList.add(fragmentBargainFreeDetail);
        mFragmentList.add(fragmentBargainFreeComment);


        List<String> mListTitle = new ArrayList<String>();

        mListTitle.add("商品");
        mListTitle.add("详情");
        mListTitle.add("评论");
        view.attachTitle(mFragmentList,mListTitle);
    }

    //砍价免费拿商品
    public void getBargainProductDetail(Context context, Map map){
        start(context);

        RetrofitApi.request(context, model.getBargainProductDetail(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
//                Log.e("getBargainProductDetail",data);
                Log.e("getBargainProductDetail",data.substring(0,data.length()/3));
                Log.e("getBargainProductDetail",data.substring(data.length()/3+1,2*data.length()/3));
                Log.e("getBargainProductDetail",data.substring(2*data.length()/3+1,data.length()));
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        view.getBargainProductDetail(data);//请求成功，数据返回给activity
                        EventBus.getDefault().post(new BargainFreeEvent(data));//向ProductFragment发送请求成功的数据
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);
                e.printStackTrace();

            }

            @Override
            public void onNotNetWork() {

            }
        });

    }

    //商品收藏/取消收藏
    public void setBargainFavorite(Context context, Map map){

        RetrofitApi.request(context, model.setBargainFavorite(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("setFavorite",data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        view.setBargainFavorite(data);//请求成功，数据返回给activity
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);
                e.printStackTrace();
            }

            @Override
            public void onNotNetWork() {

            }
        });
    }

    public void partBargain(Context context,Map<String, String> map) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).partBargain(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("partBargain",data);
                view.setPartBargain(data);
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);

            }
        });
    }

    public void getReceiverList(Context mContext) {
        Map<String, String> mHashMap = new HashMap<>();
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getReceiverList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("getReceiverList",data);
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

    public void addBargainOrders(Context mContext,Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getReceiverList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("addBargainOrders",data);
                view.addBargainOrders(data);

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

    @Override
    public void onDestroy() {

    }



}

package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.BargainShareDetailBean;
import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.ui.activity.contract.BargainFreeShareContact;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BargainFreeShareFriendPresenter implements BargainFreeShareContact.Presenter{

    private BargainFreeShareContact.UI view;
    private Context mContext;

    public BargainFreeShareFriendPresenter(Context mContext, BargainFreeShareContact.UI view) {
        this.mContext = mContext;
        this.view = view;
    }


    @Override
    public void getBargainDetails(Map map) {

        Log.e("getBargainDetails",map.toString());
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getBargainDetails(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("getBargainDetails",data);
//                BargainProductListBean bargainProductListBean = new Gson().fromJson(data,BargainProductListBean.class);
                view.getBargainDetails(data);
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

    //商品收藏/取消收藏
    public void setBargainFavorite(Context context, Map map){

        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).setFavorite(map), new RetrofitApi.IResponseListener() {
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
            public void onFail(Throwable e) {
                view.onFail(e);
                e.printStackTrace();
            }

            @Override
            public void onNotNetWork() {

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

    public void addBargainReceiver(Context mContext,Map<String, String> mHashMap) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).addBargainReceiver(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("addBargainReceiver",data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if(jsonObject.getInt("code") == 1){
                        view.addBargainReceiver(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    //分享成功
    public void getShareDetails(Context mContext,Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getShareDetails(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("getShareDetails",data);
                try {
                    BargainShareDetailBean shareBean = new Gson().fromJson(data,BargainShareDetailBean.class);
                    view.getShareDetails(shareBean);//请求成功，数据返回给activity

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

    //分享给别人
    public void shareToOthers(Context mContext,Map map) {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).shareToOthers(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("shareToOthers",data);
                try {
                    BargainShareDetailBean shareBean = new Gson().fromJson(data,BargainShareDetailBean.class);
                    view.shareToOthers(data);//请求成功，数据返回给activity

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNotNetWork() {
                view.onFail(new Error(""));
            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);
                e.printStackTrace();
            }
        });
    }




//    public void addBargainOrders(Context mContext,Map map) {
//        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getReceiverList(map), new RetrofitApi.IResponseListener() {
//            @Override
//            public void onSuccess(String data) {
//                Log.e("addBargainOrders",data);
//                view.addBargainOrders(data);
//
//            }
//
//            @Override
//            public void onFail(Throwable e) {
//                view.onFail(e);
//
//            }
//
//            @Override
//            public void onNotNetWork() {
//
//            }
//        });
//    }
}

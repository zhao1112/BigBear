package com.yunqin.bearmall.ui.fragment.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.newversions.util.SharedPreferencesManager;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.MemberInfoResponse;
import com.yunqin.bearmall.bean.MineBannerBean;
import com.yunqin.bearmall.bean.UserBTInfo;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.ui.fragment.contract.MineContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Master
 */
public class MinePresenter implements MineContract.Presenter {

    private MineContract.UI view;

    public MinePresenter(MineContract.UI view) {
        this.view = view;
    }

    @Override
    public void start(Context context, Map map) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getUserBTData(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                UserBTInfo userBTInfo = new Gson().fromJson(data, UserBTInfo.class);
                assert view != null;
                view.attachUserBTInfo(userBTInfo);
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
            }
        });

    }

    @Override
    public void getTaskInfoData(Context context) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getDailyTaskAllReward(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                assert view != null;
                view.initTaskInfo(data);
            }

            @Override
            public void onNotNetWork() {
            }

            @Override
            public void onFail(Throwable e) {
            }
        });

    }

    //订单数量
    @Override
    public void getOrderNumberInfo(Context context) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getOrdersQuantity(new HashMap<>()),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) {
                        assert view != null;
                        view.initOrderNumberInfo(data);
                    }

                    @Override
                    public void onNotNetWork() {
                    }

                    @Override
                    public void onFail(Throwable e) {
                    }
                });
    }

    @Override
    public void updateUserInfo(Context context) {
        if (BearMallAplication.getInstance().getUser() != null) {
            RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getMemberInfo(new HashMap<>()),
                    new RetrofitApi.IResponseListener() {
                        @Override
                        public void onSuccess(String data) {

                            MemberInfoResponse response = new Gson().fromJson(data, MemberInfoResponse.class);
                            if (response.isSuccess()) {
                                if (response.isSuccess()) {
                                    UserInfo.DataBean.MemberBean memberBean = response.getData();
                                    UserInfo.Identity identity = response.getIdentity();
                                    if (memberBean != null && identity != null) {
                                        UserInfo userInfo = BearMallAplication.getInstance().getUser();
                                        userInfo.getData().setMember(memberBean);
                                        userInfo.setIdentity(identity);
                                        BearMallAplication.getInstance().setUser(userInfo);
                                        view.onUpdateUserInfo(userInfo);
                                    }
                                }
                            }

                        }

                        @Override
                        public void onNotNetWork() {

                        }

                        @Override
                        public void onFail(Throwable e) {
                        }
                    });
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void onDestroy() {
        assert view != null;
        view = null;
    }

    @Override
    public void initAdData(Context context) {

        if (BearMallAplication.getInstance().getUser() == null) {
            // 未登录不展示
            return;
        }

        String mobile = BearMallAplication.getInstance().getUser().getData().getMember().getMobile();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String value = simpleDateFormat.format(System.currentTimeMillis());
        String param = (String) SharedPreferencesManager.getParam(context, mobile + "mine", "mine");

        Log.e("TAG-MINE", "value = " + value);
        Log.e("TAG-MINE", "param = " + param);


        if (value.equals(param)) {
            return;
        }

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("adLocation", "1");

        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getPopupAdInfo(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                view.resultAdData(mobile + "mine", value, data);
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }

    /**
     * 我的页面收益
     */
    @Override
    public void onProfit(Context context) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getProfit(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                JSONObject object = new JSONObject(data);
                if (object.optInt("code") == 1) {
                    JSONObject profitdata = object.optJSONObject("data");
                    if (profitdata != null) {
                        double todayprofit = profitdata.optDouble("todayprofit");
                        double cashAmount = profitdata.optDouble("balance");
                        double thismonthprofit = profitdata.optDouble("thismonthprofit");
                        view.onProfit(todayprofit, cashAmount, thismonthprofit);
                    }
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 轮播图
     */
    @Override
    public void onLunboTu(Context context) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getLunboTu(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                if (data != null) {
                    MineBannerBean mineBannerBean = new Gson().fromJson(data, MineBannerBean.class);
                    view.onLunboTu(mineBannerBean);
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }


}

package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.ProfitBean;
import com.yunqin.bearmall.ui.activity.contract.ProfitContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.activity.presenter
 * @DATE 2019/12/2
 */
public class ProfitPresenter implements ProfitContract.Presenter {

    private ProfitContract.UI view;

    public ProfitPresenter(ProfitContract.UI view) {
        this.view = view;
    }

    @Override
    public void getIncomeRecord(Context context) {
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).IncomeRecord(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                if (!TextUtils.isEmpty(data)) {
                    JSONObject object = new JSONObject(data);
                    JSONObject dat = object.optJSONObject("data");
                    Double unsettled = dat.optDouble("unsettled");
                    Double balance = dat.optDouble("balance");
                    Double thisMonthTransaction = dat.optDouble("thisMonthTransaction");
                    Double withdrawals = dat.optDouble("withdrawals");
                    String withdrawFrom = dat.optString("withdrawFrom");
                    view.onIncomeRecord(unsettled, balance, thisMonthTransaction, withdrawals, withdrawFrom);
                } else {
                    view.onNotNetWork();
                }
            }

            @Override
            public void onNotNetWork() {
                view.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);
            }
        });
    }

    @Override
    public void getMonthProfiteDetailed(Context context) {
        Map<String, String> map = new HashMap<>();
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).MonthProfiteDetailed(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                if (!TextUtils.isEmpty(data)) {
                    Log.e("getMonthProfiteDetailed", data);
                    ProfitBean profitBean = new Gson().fromJson(data, ProfitBean.class);
                    view.onMonthProfiteDetailed(profitBean);
                } else {
                    view.onNotNetWork();
                }
            }

            @Override
            public void onNotNetWork() {
                view.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);
            }
        });
    }

    @Override
    public void getDayProfiteDetailed(Context context) {
        Map<String, String> map = new HashMap<>();
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).DayProfiteDetailed(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                if (!TextUtils.isEmpty(data)) {
                    ProfitBean profitBean = new Gson().fromJson(data, ProfitBean.class);
                    view.onDayProfiteDetailed(profitBean);
                } else {
                    view.onNotNetWork();
                }
            }

            @Override
            public void onNotNetWork() {
                view.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);
            }
        });
    }

    @Override
    public void getRecommendedRevenue(Context context) {
        Map<String, String> map = new HashMap<>();
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).recommendedRevenue(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                if (!TextUtils.isEmpty(data)) {
                    ProfitBean profitBean = new Gson().fromJson(data, ProfitBean.class);
                    view.onRecommendedRevenue(profitBean);
                } else {
                    view.onNotNetWork();
                }
            }

            @Override
            public void onNotNetWork() {
                view.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {
                view.onFail(e);
            }
        });
    }
}

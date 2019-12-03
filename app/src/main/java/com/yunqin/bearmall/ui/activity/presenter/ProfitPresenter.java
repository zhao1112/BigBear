package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.activity.contract.ProfitContract;

import org.json.JSONException;
import org.json.JSONObject;

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
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).MonthProfiteDetailed(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                if (!TextUtils.isEmpty(data)) {
                    JSONObject object = new JSONObject(data);
                    JSONObject dat = object.optJSONObject("data");
                    int lastMonthPaymentPens = dat.optInt("lastMonthPaymentPens");
                    Double lastMonthConsumption = dat.optDouble("lastMonthConsumption");
                    int thisMonthPaymentPens = dat.optInt("thisMonthPaymentPens");
                    Double thisMonthConsumption = dat.optDouble("thisMonthConsumption");
                    view.onMonthProfiteDetailed(lastMonthPaymentPens, lastMonthConsumption, thisMonthPaymentPens, thisMonthConsumption);
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
        RetrofitApi.request(context, RetrofitApi.createApi(Api.class).DayProfiteDetailed(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                if (!TextUtils.isEmpty(data)) {
                    JSONObject object = new JSONObject(data);
                    JSONObject dat = object.optJSONObject("data");
                    int todayPaymentPens = dat.optInt("todayPaymentPens");
                    Double todayTransactionRevenue = dat.optDouble("todayTransactionRevenue");
                    int yesterdayPaymentPens = dat.optInt("yesterdayPaymentPens");
                    Double yesterdayTransactionRevenue = dat.optDouble("yesterdayTransactionRevenue");
                    view.onDayProfiteDetailed(todayPaymentPens, todayTransactionRevenue, yesterdayPaymentPens, yesterdayTransactionRevenue);
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

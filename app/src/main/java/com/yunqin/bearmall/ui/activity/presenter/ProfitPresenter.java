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
                    if (object.optInt("code") == 1) {
                        JSONObject dat = object.optJSONObject("data");
                        String todayRecommendEarnings = dat.optString("todayRecommendEarnings");
                        String yesterdayRecommendEarnings = dat.optString("yesterdayRecommendEarnings");
                        String thisMonthRecommendEarnings = dat.optString("thisMonthRecommendEarnings");
                        String lastMonthRecommendEarnings = dat.optString("lastMonthRecommendEarnings");
                        int todayClinchADealNumber = dat.optInt("todayClinchADealNumber");
                        int yesterdayClinchADealNumberens = dat.optInt("yesterdayClinchADealNumber");
                        int thisMonthClinchADealNumber = dat.optInt("thisMonthClinchADealNumber");
                        int lastMonthClinchADealNumber = dat.optInt("lastMonthClinchADealNumber");
                        String thisMonthEstimatedTheRevenue = dat.optString("thisMonthEstimatedTheRevenue");
                        String lastMonthTheTeamReturns = dat.optString("lastMonthTheTeamReturns");
                        int type = object.optInt("type");

                        view.onMonthProfiteDetailed(todayRecommendEarnings, yesterdayRecommendEarnings, thisMonthRecommendEarnings,
                                lastMonthRecommendEarnings, todayClinchADealNumber, yesterdayClinchADealNumberens,
                                thisMonthClinchADealNumber, lastMonthClinchADealNumber, thisMonthEstimatedTheRevenue,
                                lastMonthTheTeamReturns, type);
                    }
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
                    if (object.optInt("code") == 1) {
                        JSONObject dat = object.optJSONObject("data");
                        String todayIndividualPurchased = dat.optString("todayIndividualPurchased");
                        String yesterdayIndividualPurchased = dat.optString("yesterdayIndividualPurchased");
                        String thisMonthIndividualPurchased = dat.optString("thisMonthIndividualPurchased");
                        String lastMonthIndividualPurchased = dat.optString("lastMonthIndividualPurchased");
                        int todayPaymentPens = dat.optInt("todayPaymentPens");
                        int yesterdayPaymentPens = dat.optInt("yesterdayPaymentPens");
                        int thisMonthPaymentPens = dat.optInt("thisMonthPaymentPens");
                        int lastMonthPaymentPens = dat.optInt("lastMonthPaymentPens");
                        view.onDayProfiteDetailed(todayIndividualPurchased, yesterdayIndividualPurchased, thisMonthIndividualPurchased,
                                lastMonthIndividualPurchased, todayPaymentPens, yesterdayPaymentPens, thisMonthPaymentPens,
                                lastMonthPaymentPens);
                    }

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

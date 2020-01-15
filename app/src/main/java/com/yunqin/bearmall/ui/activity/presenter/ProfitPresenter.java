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
                        Double todayRecommendEarnings = dat.optDouble("todayRecommendEarnings");
                        Double yesterdayRecommendEarnings = dat.optDouble("yesterdayRecommendEarnings");
                        Double thisMonthRecommendEarnings = dat.optDouble("thisMonthRecommendEarnings");
                        Double lastMonthRecommendEarnings = dat.optDouble("lastMonthRecommendEarnings");
                        int todayClinchADealNumber = dat.optInt("todayClinchADealNumber");
                        int yesterdayClinchADealNumberens = dat.optInt("yesterdayClinchADealNumber");
                        int thisMonthClinchADealNumber = dat.optInt("thisMonthClinchADealNumber");
                        int lastMonthClinchADealNumber = dat.optInt("lastMonthClinchADealNumber");
                        Double thisMonthEstimatedTheRevenue = dat.optDouble("thisMonthEstimatedTheRevenue");
                        Double lastMonthTheTeamReturns = dat.optDouble("lastMonthTheTeamReturns");
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
                        Double todayIndividualPurchased = dat.optDouble("todayIndividualPurchased");
                        Double yesterdayIndividualPurchased = dat.optDouble("yesterdayIndividualPurchased");
                        Double thisMonthIndividualPurchased = dat.optDouble("thisMonthIndividualPurchased");
                        Double lastMonthIndividualPurchased = dat.optDouble("lastMonthIndividualPurchased");
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

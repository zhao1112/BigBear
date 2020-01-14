package com.yunqin.bearmall.ui.activity.contract;

import android.content.Context;

import com.yunqin.bearmall.ui.fragment.contract.BaseContract;

import mlxy.utils.S;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.activity.contract
 * @DATE 2019/12/2
 */
public interface ProfitContract {

    public interface Presenter {
        void getIncomeRecord(Context context);

        void getMonthProfiteDetailed(Context context);

        void getDayProfiteDetailed(Context context);
    }

    public interface UI extends BaseContract.BaseView {
        void onFail(Throwable e);

        void onNotNetWork();

        void onIncomeRecord(Double unsettled, Double balance, Double thisMonth, Double withdrawals, String withdrawFrom);

        void onMonthProfiteDetailed(String todayRecommendEarnings, String yesterdayRecommendEarnings, String thisMonthRecommendEarnings,
                                    String lastMonthRecommendEarnings, int todayClinchADealNumber, int yesterdayClinchADealNumberens,
                                    int thisMonthClinchADealNumber, int lastMonthClinchADealNumber, String thisMonthEstimatedTheRevenue,
                                    String lastMonthTheTeamReturns,int type);

        void onDayProfiteDetailed(String todayIndividualPurchased, String yesterdayIndividualPurchased, String thisMonthIndividualPurchased,
                                  String lastMonthIndividualPurchased, int todayPaymentPens, int yesterdayPaymentPens,
                                  int thisMonthPaymentPens, int lastMonthPaymentPens);
    }


}

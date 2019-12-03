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

        void onMonthProfiteDetailed(int lastMonthPaymentPens, Double lastMonthConsumption, int thisMonthPaymentPens,
                                    Double thisMonthConsumption);

        void onDayProfiteDetailed(int todayPaymentPens, Double todayTransactionRevenue, int yesterdayPaymentPens,
                                  Double yesterdayTransactionRevenue);
    }


}

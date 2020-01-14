package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.ui.activity.contract.ProfitContract;
import com.yunqin.bearmall.ui.activity.presenter.ProfitPresenter;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

public class MineProfitActivity extends BaseActivity implements ProfitContract.UI {

    @BindView(R.id.cash_price)
    TextView mCashPrice;
    @BindView(R.id.withdrawal_price)
    TextView mWithdrawalPrice;
    @BindView(R.id.not_price)
    TextView mNotPrice;
    @BindView(R.id.cumulative_price)
    TextView mCumulativePrice;
    //分享收益
    @BindView(R.id.thisMonthPaymentPens)
    TextView mThisMonthPaymentPens;
    @BindView(R.id.thisMonthConsumption)
    TextView mThisMonthConsumption;
    @BindView(R.id.lastMonthPaymentPens)
    TextView mLastMonthPaymentPens;
    @BindView(R.id.lastMonthConsumption)
    TextView mLastMonthConsumption;
    @BindView(R.id.todayPaymentPens)
    TextView mTodayPaymentPens;
    @BindView(R.id.todayTransactionRevenue)
    TextView mTodayTransactionRevenue;
    @BindView(R.id.yesterdayPaymentPens)
    TextView mYesterdayPaymentPens;
    @BindView(R.id.yesterdayTransactionRevenue)
    TextView mYesterdayTransactionRevenue;
    //推荐收益
    @BindView(R.id.super_todayTransactionRevenue)
    TextView super_todayTransactionRevenue;
    @BindView(R.id.super_yesterdayTransactionRevenue)
    TextView super_yesterdayTransactionRevenue;
    @BindView(R.id.super_thisMonthConsumption)
    TextView super_thisMonthConsumption;
    @BindView(R.id.super_lastMonthConsumption)
    TextView super_lastMonthConsumption;
    @BindView(R.id.super_todayPaymentPens)
    TextView super_todayPaymentPens;
    @BindView(R.id.super_yesterdayPaymentPens)
    TextView super_yesterdayPaymentPens;
    @BindView(R.id.super_thisMonthPaymentPens)
    TextView super_thisMonthPaymentPens;
    @BindView(R.id.super_lastMonthPaymentPens)
    TextView super_lastMonthPaymentPens;
    @BindView(R.id.vip_super)
    RelativeLayout vip_super;
    //团队收益
    @BindView(R.id.team_yesterdayPaymentPens)
    TextView team_yesterdayPaymentPens;
    @BindView(R.id.team_lastMonthPaymentPens)
    TextView team_lastMonthPaymentPens;
    @BindView(R.id.vip_team)
    RelativeLayout vip_team;


    private ProfitPresenter mProfitPresenter;
    private String withdrawFrom;
    private String balance;

    public static void openMineProfitActivity(Activity activity, Class cla) {
        Intent intent = new Intent(activity, cla);
        activity.startActivity(intent);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_mine_profit;
    }

    @Override
    public void init() {
        setTranslucentStatus();

        mProfitPresenter = new ProfitPresenter(this);
        mProfitPresenter.getIncomeRecord(this);
        mProfitPresenter.getMonthProfiteDetailed(this);
        mProfitPresenter.getDayProfiteDetailed(this);

    }


    @Override
    public void onFail(Throwable e) {

    }

    @Override
    public void onNotNetWork() {

    }

    @Override
    public void onIncomeRecord(Double unsettled, Double balance, Double thisMonth, Double withdrawals, String withdrawFrom) {
        mCashPrice.setText("￥" + doubleToString(balance));
        mWithdrawalPrice.setText(doubleToString(withdrawals));
        mNotPrice.setText(doubleToString(unsettled));
        mCumulativePrice.setText(doubleToString(thisMonth));
        this.withdrawFrom = withdrawFrom;
        this.balance = doubleToString(balance);
    }

    @Override
    public void onMonthProfiteDetailed(String todayRecommendEarnings, String yesterdayRecommendEarnings,
                                       String thisMonthRecommendEarnings, String lastMonthRecommendEarnings, int todayClinchADealNumber,
                                       int yesterdayClinchADealNumberens, int thisMonthClinchADealNumber, int lastMonthClinchADealNumber,
                                       String thisMonthEstimatedTheRevenue, String lastMonthTheTeamReturns, int type) {
        /**
         * type == 1 显示推荐收益
         * type == 2 显示推荐收益和团队收益
         * */
        if (type == 1) {
            vip_super.setVisibility(View.VISIBLE);
        } else if (type == 2) {
            vip_super.setVisibility(View.VISIBLE);
            vip_team.setVisibility(View.VISIBLE);
        }

        //推荐收益
        super_todayTransactionRevenue.setText("￥" + todayRecommendEarnings);
        super_yesterdayTransactionRevenue.setText("￥" + yesterdayRecommendEarnings);
        super_thisMonthConsumption.setText("￥" + thisMonthRecommendEarnings);
        super_lastMonthConsumption.setText("￥" + lastMonthRecommendEarnings);
        super_todayPaymentPens.setText(todayClinchADealNumber + "");
        super_yesterdayPaymentPens.setText(yesterdayClinchADealNumberens + "");
        super_thisMonthPaymentPens.setText(thisMonthClinchADealNumber + "");
        super_lastMonthPaymentPens.setText(lastMonthClinchADealNumber + "");
        //团队收益
        team_yesterdayPaymentPens.setText("￥" + thisMonthEstimatedTheRevenue);
        team_lastMonthPaymentPens.setText("￥" + lastMonthTheTeamReturns);

    }


    @Override
    public void onDayProfiteDetailed(String todayIndividualPurchased, String yesterdayIndividualPurchased,
                                     String thisMonthIndividualPurchased, String lastMonthIndividualPurchased, int todayPaymentPens,
                                     int yesterdayPaymentPens, int thisMonthPaymentPens, int lastMonthPaymentPens) {

        //自购收益
        mTodayPaymentPens.setText(todayPaymentPens + "");
        mYesterdayPaymentPens.setText(yesterdayPaymentPens + "");
        mThisMonthPaymentPens.setText(thisMonthPaymentPens + "");
        mLastMonthPaymentPens.setText(lastMonthPaymentPens + "");
        mTodayTransactionRevenue.setText("￥" + todayIndividualPurchased);
        mYesterdayTransactionRevenue.setText("￥" + yesterdayIndividualPurchased);
        mThisMonthConsumption.setText("￥" + thisMonthIndividualPurchased);
        mLastMonthConsumption.setText("￥" + lastMonthIndividualPurchased);

    }


    public static String doubleToString(double num) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }


    @OnClick({R.id.p_back, R.id.p_detailed, R.id.withdrawal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.p_back:
                finish();
                break;
            case R.id.p_detailed:
                BalanceDetailActivity.startBalanceDetailActivity(this, balance);
                break;
            case R.id.withdrawal:
                BalanceActivity.startBalanceActivity(this, balance, withdrawFrom);
                break;
        }
    }

}

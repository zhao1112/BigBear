package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    @BindView(R.id.twinkling)
    TwinklingRefreshLayout mTwinkling;
    @BindView(R.id.cash_price)
    TextView mCashPrice;
    @BindView(R.id.withdrawal_price)
    TextView mWithdrawalPrice;
    @BindView(R.id.not_price)
    TextView mNotPrice;
    @BindView(R.id.cumulative_price)
    TextView mCumulativePrice;
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

        mTwinkling.setHeaderView(new RefreshHeadView(this));
        mTwinkling.setEnableLoadmore(false);
        mTwinkling.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mProfitPresenter.getIncomeRecord(MineProfitActivity.this);
                mProfitPresenter.getMonthProfiteDetailed(MineProfitActivity.this);
                mProfitPresenter.getDayProfiteDetailed(MineProfitActivity.this);
            }
        });
    }


    @Override
    public void onFail(Throwable e) {
        mTwinkling.finishRefreshing();
    }

    @Override
    public void onNotNetWork() {
        mTwinkling.finishRefreshing();
    }

    @Override
    public void onIncomeRecord(Double unsettled, Double balance, Double thisMonth, Double withdrawals, String withdrawFrom) {
        mCashPrice.setText("￥" + doubleToString(balance));
        mWithdrawalPrice.setText(doubleToString(withdrawals));
        mNotPrice.setText(doubleToString(unsettled));
        mCumulativePrice.setText(doubleToString(thisMonth));
        this.withdrawFrom = withdrawFrom;
        this.balance = doubleToString(balance);
        mTwinkling.finishRefreshing();
    }

    @Override
    public void onMonthProfiteDetailed(int lastMonthPaymentPens, Double lastMonthConsumption, int thisMonthPaymentPens,
                                       Double thisMonthConsumption) {
        mThisMonthPaymentPens.setText(thisMonthPaymentPens + "");
        mThisMonthConsumption.setText("￥" + doubleToString(thisMonthConsumption));
        mLastMonthPaymentPens.setText(lastMonthPaymentPens + "");
        mLastMonthConsumption.setText("￥" + doubleToString(lastMonthConsumption));
        mTwinkling.finishRefreshing();
    }

    @Override
    public void onDayProfiteDetailed(int todayPaymentPens, Double todayTransactionRevenue, int yesterdayPaymentPens,
                                     Double yesterdayTransactionRevenue) {
        mTodayPaymentPens.setText(todayPaymentPens + "");
        mTodayTransactionRevenue.setText("￥" + doubleToString(todayTransactionRevenue));
        mYesterdayPaymentPens.setText(yesterdayPaymentPens + "");
        mYesterdayTransactionRevenue.setText("￥" + doubleToString(yesterdayTransactionRevenue));
        mTwinkling.finishRefreshing();
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

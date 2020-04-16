package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newversions.tbk.activity.WebActivity;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.ProfitBean;
import com.yunqin.bearmall.ui.activity.contract.ProfitContract;
import com.yunqin.bearmall.ui.activity.presenter.ProfitPresenter;

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
    //注册
    @BindView(R.id.lastMonthPaymentPens)
    TextView mLastMonthPaymentPens;
    @BindView(R.id.lastMonthConsumption)
    TextView mLastMonthConsumption;
    @BindView(R.id.questdata)
    TextView mQuestdata;
    @BindView(R.id.PaymentPens)
    TextView mPaymentPens;
    @BindView(R.id.Consumption)
    TextView mConsumption;
    @BindView(R.id.data)
    TextView mData;
    //超级
    @BindView(R.id.PaymentPens_2)
    TextView mPaymentPens2;
    @BindView(R.id.Consumption_2)
    TextView mConsumption2;
    @BindView(R.id.quest_2)
    TextView mQuest2;
    @BindView(R.id.PaymentPens_2_2)
    TextView mPaymentPens22;
    @BindView(R.id.Consumption_2_2)
    TextView mConsumption22;
    @BindView(R.id.quest_2_2)
    TextView mQuest22;
    @BindView(R.id.PaymentPens_month)
    TextView mPaymentPensMonth;
    @BindView(R.id.Consumption_month)
    TextView mConsumptionMonth;
    @BindView(R.id.quest_month)
    TextView mQuestMonth;
    @BindView(R.id.PaymentPens_month_2)
    TextView mPaymentPensMonth2;
    @BindView(R.id.Consumption_month_2)
    TextView mConsumptionMonth2;
    @BindView(R.id.quest_month_2)
    TextView mQuestMonth2;
    //大团长
    @BindView(R.id.PaymentPens_3)
    TextView mPaymentPens3;
    @BindView(R.id.Consumption_3)
    TextView mConsumption3;
    @BindView(R.id.quest_3)
    TextView mQuest3;
    @BindView(R.id.Payment_3)
    TextView mPayment3;
    @BindView(R.id.Consum_3)
    TextView mConsum3;
    @BindView(R.id.quest3)
    TextView mQuest_3;
    @BindView(R.id.PaymentPens_month_3)
    TextView mPaymentPensMonth3;
    @BindView(R.id.Consumption_month_3)
    TextView mConsumptionMonth3;
    @BindView(R.id.quest_month_3)
    TextView mQuestMonth3;
    @BindView(R.id.Payment_month)
    TextView mPaymentMonth;
    @BindView(R.id.Consum_month)
    TextView mConsumMonth;
    @BindView(R.id.quest_month2)
    TextView mQuestMonth_2;
    @BindView(R.id.Payment_31)
    TextView mPayment31;
    @BindView(R.id.Consum_31)
    TextView mConsum31;
    @BindView(R.id.quest31)
    TextView mQuest31;
    @BindView(R.id.PaymentPens_month_4)
    TextView mPaymentPensMonth4;
    @BindView(R.id.Consumption_month_4)
    TextView mConsumptionMonth4;
    @BindView(R.id.quest_month_4)
    TextView mQuestMonth4;
    //
    @BindView(R.id.zhuc)
    LinearLayout mZhuc;
    @BindView(R.id.chaoji)
    LinearLayout mChaoji;
    @BindView(R.id.datuan)
    LinearLayout mDatuan;


    private ProfitPresenter mProfitPresenter;
    private String withdrawFrom;
    private String balance;
    private final String money = "¥";

    public static void openMineProfitActivity(Activity activity, Class cla, Bundle bundle) {
        Intent intent = new Intent(activity, cla);
        intent.putExtras(bundle);
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

        if (getIntent() != null) {
            int upgradeType = getIntent().getIntExtra("upgradeType", 0);
            switch (upgradeType) {
                case 1:
                    mProfitPresenter.getMonthProfiteDetailed(this);
                    mZhuc.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    mProfitPresenter.getMonthProfiteDetailed(this);
                    mProfitPresenter.getDayProfiteDetailed(this);
                    mChaoji.setVisibility(View.VISIBLE);
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                default:
                    mProfitPresenter.getMonthProfiteDetailed(this);
                    mProfitPresenter.getDayProfiteDetailed(this);
                    mProfitPresenter.getRecommendedRevenue(this);
                    mDatuan.setVisibility(View.VISIBLE);
                    break;
            }
        }

    }


    @Override
    public void onFail(Throwable e) {

    }

    @Override
    public void onNotNetWork() {

    }

    @Override
    public void onIncomeRecord(Double unsettled, Double balance, Double thisMonth, Double withdrawals, String withdrawFrom) {
        mCashPrice.setText(money + doubleToString(balance));
        mWithdrawalPrice.setText(doubleToString(withdrawals));
        mNotPrice.setText(doubleToString(unsettled));
        mCumulativePrice.setText(doubleToString(thisMonth));
        this.withdrawFrom = withdrawFrom;
        this.balance = doubleToString(balance);
    }

    //注册会员
    @Override
    public void onMonthProfiteDetailed(ProfitBean profitBean) {
        try {
            //注册
            mLastMonthPaymentPens.setText(profitBean.getData().getTodayPaymentPens() + "");
            mLastMonthConsumption.setText(doubleToString(profitBean.getData().getTodayIndividualPurchased()));
            mQuestdata.setText(doubleToString(profitBean.getData().getTodayConfirmReceipt()));
            mPaymentPens.setText(profitBean.getData().getThisMonthPaymentPens() + "");
            mConsumption.setText(doubleToString(profitBean.getData().getThisMonthIndividualPurchased()));
            mData.setText(doubleToString(profitBean.getData().getThisMonthConfirmReceipt()));
            //超级
            mPaymentPens2.setText(profitBean.getData().getTodayPaymentPens() + "");
            mConsumption2.setText(doubleToString(profitBean.getData().getTodayIndividualPurchased()));
            mQuest2.setText(doubleToString(profitBean.getData().getTodayConfirmReceipt()));
            mPaymentPensMonth.setText(profitBean.getData().getThisMonthPaymentPens() + "");
            mConsumptionMonth.setText(doubleToString(profitBean.getData().getThisMonthIndividualPurchased()));
            mQuestMonth.setText(doubleToString(profitBean.getData().getThisMonthConfirmReceipt()));
            //大团长
            mPaymentPens3.setText(profitBean.getData().getTodayPaymentPens() + "");
            mConsumption3.setText(doubleToString(profitBean.getData().getTodayIndividualPurchased()));
            mQuest3.setText(doubleToString(profitBean.getData().getTodayConfirmReceipt()));
            mPaymentMonth.setText(profitBean.getData().getThisMonthPaymentPens() + "");
            mConsumMonth.setText(doubleToString(profitBean.getData().getThisMonthIndividualPurchased()));
            mQuestMonth_2.setText(doubleToString(profitBean.getData().getThisMonthConfirmReceipt()));
            mQuestMonth4.setText(doubleToString(profitBean.getData().getThisMonthfirmPredictio()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //超级会员
    @Override
    public void onDayProfiteDetailed(ProfitBean profitBean) {
        try {
            Log.e("ProfiteDetailed", doubleToString(profitBean.getData().getThisMonthConfirmReceipt()));
            //超级
            mPaymentPens22.setText(profitBean.getData().getTodayClinchADealNumber() + "");
            mConsumption22.setText(doubleToString(profitBean.getData().getTodayRecommendEarnings()));
            mQuest22.setText(doubleToString(profitBean.getData().getTodayConfirmEarnings()));
            mPaymentPensMonth2.setText(profitBean.getData().getThisMonthClinchADealNumber() + "");
            mConsumptionMonth2.setText(doubleToString(profitBean.getData().getThisMonthRecommendEarnings()));
            mQuestMonth2.setText(doubleToString(profitBean.getData().getThisMonthfirmEarnings()));
            //大团长
            mPayment3.setText(profitBean.getData().getTodayClinchADealNumber() + "");
            mConsum3.setText(doubleToString(profitBean.getData().getTodayRecommendEarnings()));
            mQuest_3.setText(doubleToString(profitBean.getData().getTodayConfirmEarnings()));
            mPaymentPensMonth3.setText(profitBean.getData().getThisMonthClinchADealNumber() + "");
            mConsumptionMonth3.setText(doubleToString(profitBean.getData().getThisMonthRecommendEarnings()));
            mQuestMonth3.setText(doubleToString(profitBean.getData().getThisMonthfirmEarnings()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //大团长
    @Override
    public void onRecommendedRevenue(ProfitBean profitBean) {
        try {
            mPayment31.setText(profitBean.getData().getTodayTeamPrediction() + "");
            mConsum31.setText(doubleToString(profitBean.getData().getTodayPredictioEarnings()));
            mQuest31.setText(doubleToString(profitBean.getData().getTodayConfirmPredictio()));
            mPaymentPensMonth4.setText(profitBean.getData().getThisMonthTeamPrediction() + "");
            mConsumptionMonth4.setText(doubleToString(profitBean.getData().getThisMonthPredictioEarnings()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String doubleToString(double num) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }


    @OnClick({R.id.p_back, R.id.p_detailed, R.id.withdrawal, R.id.item_data, R.id.item_data2, R.id.item_data_2, R.id.item_month
            , R.id.item_2, R.id.item_month2, R.id.title_top})
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
            case R.id.item_data://日
            case R.id.item_data_2:
            case R.id.item_2:
                Intent intent = new Intent(MineProfitActivity.this, DailyIncomeActivity.class);
                startActivity(intent);
                break;
            case R.id.item_data2://月
            case R.id.item_month:
            case R.id.item_month2:
                Intent intent1 = new Intent(MineProfitActivity.this, MonthlyIncomeActivity.class);
                startActivity(intent1);
                break;
            case R.id.title_top:
                WebActivity.startWebActivity(MineProfitActivity.this, 20, "https://testapi.bbcoupon.cn/view/money/list", "收益概况说明");
                break;
        }
    }

}

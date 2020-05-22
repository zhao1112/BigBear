package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bbcoupon.ui.activity.AlipayCashActivity;
import com.bbcoupon.ui.activity.BindingAlipayActivity;
import com.bbcoupon.ui.bean.AlipayInfor;
import com.bbcoupon.ui.bean.BaseInfor;
import com.bbcoupon.ui.bean.RequestInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bbcoupon.util.WindowUtils;
import com.newversions.tbk.activity.WebActivity;
import com.newversions.view.ICustomDialog;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.MemberBeanResponse;
import com.yunqin.bearmall.bean.ProfitBean;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.ui.activity.contract.ProfitContract;
import com.yunqin.bearmall.ui.activity.presenter.ProfitPresenter;
import com.yunqin.bearmall.util.CommonUtils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

public class MineProfitActivity extends BaseActivity implements ProfitContract.UI, View.OnClickListener, PlatformActionListener,
        RequestContract.RequestView {

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
    private RequestPresenter presenter;
    private boolean isBind = false;

    public static void openMineProfitActivity(Activity activity, Class cla, Bundle bundle) {
        Intent intent = new Intent(activity, cla);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public static void openMineProfitActivitys(Activity activity, Class cla, Bundle bundle) {
        Intent intent = new Intent(activity, cla);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
        presenter = new RequestPresenter();
        presenter.setRelation(this);

        if (getIntent() != null) {
            int upgradeType = getIntent().getIntExtra("upgradeType", 0);
            switch (upgradeType) {
                case 1:
                    showLoading();
                    mProfitPresenter.getMonthProfiteDetailed(this);
                    mZhuc.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    showLoading();
                    mProfitPresenter.getMonthProfiteDetailed(this);
                    mProfitPresenter.getDayProfiteDetailed(this);
                    mChaoji.setVisibility(View.VISIBLE);
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                default:
                    showLoading();
                    mProfitPresenter.getMonthProfiteDetailed(this);
                    mProfitPresenter.getDayProfiteDetailed(this);
                    mProfitPresenter.getRecommendedRevenue(this);
                    mDatuan.setVisibility(View.VISIBLE);
                    break;
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.setUntying(this);
    }

    @Override
    public void onFail(Throwable e) {
        showToast("请求失败");
        hiddenLoadingView();
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof String) {
            isBind = true;
            Toast.makeText(MineProfitActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
            presenter.onMemberInfo(MineProfitActivity.this, new HashMap<>());
        }
        if (data instanceof MemberBeanResponse) {
            try {
                MemberBeanResponse memberBeanResponse = (MemberBeanResponse) data;
                UserInfo userInfo = BearMallAplication.getInstance().getUser();
                UserInfo.DataBean dataBean = userInfo.getData();
                UserInfo.DataBean.MemberBean memberBean = memberBeanResponse.getData();
                UserInfo.Identity identity = memberBeanResponse.getIdentity();
                dataBean.setMember(memberBean);
                userInfo.setData(dataBean);
                userInfo.setIdentity(identity);
                BearMallAplication.getInstance().setUser(userInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (data instanceof RequestInfor) {
            RequestInfor requestInfor = (RequestInfor) data;
            if (requestInfor.getIsBindBankCard()) {
                boundBankCard_();
            } else {
                notBoundBankCard_();
            }
        }
        if (data instanceof AlipayInfor) {
            Bundle bundle = new Bundle();
            AlipayInfor baseInfor = (AlipayInfor) data;
            if (baseInfor.getCode() == 1) {
                AlipayCashActivity.openAlipayCashActivity(MineProfitActivity.this, AlipayCashActivity.class);
            } else {
                bundle.putString("TITLE", "绑定支付宝");
                bundle.putInt("TYPE", 0);
                BindingAlipayActivity.openBindingAlipayActivity(MineProfitActivity.this, BindingAlipayActivity.class, bundle);
            }
        }
        hiddenLoadingView();
    }

    @Override
    public void onNotNetWork() {
        showToast("网络错误");
        hiddenLoadingView();
    }

    @Override
    public void onIncomeRecord(Double unsettled, Double balance, Double thisMonth, Double withdrawals, String withdrawFrom) {
        this.balance = doubleToString(balance);
        mCashPrice.setText(money + doubleToString(balance));
        mWithdrawalPrice.setText(doubleToString(withdrawals));
        mNotPrice.setText(doubleToString(unsettled));
        mCumulativePrice.setText(doubleToString(thisMonth));
        this.withdrawFrom = withdrawFrom + "";
        hiddenLoadingView();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        hiddenLoadingView();
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
        hiddenLoadingView();
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
            mQuestMonth4.setText(doubleToString(profitBean.getData().getThisMonthfirmPredictio()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        hiddenLoadingView();
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
            case R.id.withdrawal://提现
                PopupWindow popupWindow = WindowUtils.ShowVirtual(MineProfitActivity.this, R.layout.item_popup_withdrawal_options,
                        R.style.bottom_animation, 2);
                popupWindow.getContentView().findViewById(R.id.wit_cancel).setOnClickListener(this);
                popupWindow.getContentView().findViewById(R.id.wit_zfb).setOnClickListener(this);
                popupWindow.getContentView().findViewById(R.id.wit_wx).setOnClickListener(this);
                popupWindow.getContentView().findViewById(R.id.wit_yhk).setOnClickListener(this);
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
                if (System.currentTimeMillis() - CommonUtils.lastClickTime > CommonUtils.FAST_CLICK_DELAY_TIME) {
                    WebActivity.startWebActivity(MineProfitActivity.this, 20, "https://testapi.bbcoupon.cn/view/money/list", "收益概况说明");
                    CommonUtils.lastClickTime = System.currentTimeMillis();
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.wit_cancel:
                WindowUtils.dismissBrightness(MineProfitActivity.this);
                break;
            case R.id.wit_zfb:
                Map<String, String> map = new HashMap<>();
                presenter.onWithOutAlipay(MineProfitActivity.this, map);
                WindowUtils.dismissBrightness(MineProfitActivity.this);
                break;
            case R.id.wit_wx:
                checkWx();
                WindowUtils.dismissBrightness(MineProfitActivity.this);
                break;
            case R.id.wit_yhk:
                presenter.onisBindBankCard(MineProfitActivity.this);
                WindowUtils.dismissBrightness(MineProfitActivity.this);
                break;
        }
    }

    //微信提现
    private void checkWx() {
        if (BearMallAplication.getInstance().getUser().getData().getMember().isBindWxopenId() || isBind) {
            if (!TextUtils.isEmpty(balance)) {
                BalanceWithdrawalWxActivity.startActivity(this, balance);
            } else {
                BalanceWithdrawalWxActivity.startActivity(this, "0.00");
            }
        } else {
            new ICustomDialog.Builder(this)
                    // 设置布局
                    .setLayoutResId(R.layout.join_member_layout)
                    // 点击空白是否消失
                    .setCanceledOnTouchOutside(true)
                    // 点击返回键是否消失
                    .setCancelable(true)
                    // 设置Dialog的绝对位置
                    .setDialogPosition(Gravity.CENTER)
                    // 设置自定义动画
                    .setAnimationResId(0)
                    // 设置监听ID
                    .setListenedItems(new int[]{R.id.join_member_no, R.id.join_member_ok})
                    .setCustomTextIds(new int[]{R.id.tip, R.id.join_member_no, R.id.join_member_ok})
                    .setCustomTextContents(new String[]{"您还未绑定微信,请先绑定微信再进行提现", "我再想想", "立即绑定"})
                    // 设置回掉
                    .setOnDialogItemClickListener((thisDialog, clickView) -> {
                        thisDialog.dismiss();
                        if (clickView.getId() == R.id.join_member_ok) {
                            showLoading();
                            Platform platform = ShareSDK.getPlatform(Wechat.NAME);
                            platform.SSOSetting(false);
                            platform.setPlatformActionListener(this);
                            platform.authorize();
                        }
                    })
                    .build().show();
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("open_id", platform.getDb().get("unionid"));
        mHashMap.put("wxopen_id", platform.getDb().get("openid"));
        mHashMap.put("bindType", "1");
        presenter.onThirdPartyBind(MineProfitActivity.this, mHashMap);
        hiddenLoadingView();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Toast.makeText(this, "绑定微信取消" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
        hiddenLoadingView();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Toast.makeText(this, "绑定微信取消", Toast.LENGTH_SHORT).show();
        hiddenLoadingView();
    }

    //绑定银行卡
    private void notBoundBankCard_() {
        new ICustomDialog.Builder(this)
                // 设置布局
                .setLayoutResId(R.layout.join_member_layout)
                // 点击空白是否消失
                .setCanceledOnTouchOutside(false)
                // 点击返回键是否消失
                .setCancelable(false)
                // 设置Dialog的绝对位置
                .setDialogPosition(Gravity.CENTER)
                // 设置自定义动画
                .setAnimationResId(0)
                // 设置监听ID
                .setListenedItems(new int[]{R.id.join_member_no, R.id.join_member_ok})
                .setCustomTextIds(new int[]{R.id.tip, R.id.join_member_no, R.id.join_member_ok})
                .setCustomTextContents(new String[]{"您还未绑定银行卡", "取消", "去设置"})
                // 设置回掉
                .setOnDialogItemClickListener((thisDialog, clickView) -> {
                    thisDialog.dismiss();
                    if (clickView.getId() == R.id.join_member_ok) {
                        BankAddVerifyActivity.startActivity(MineProfitActivity.this, BankAddVerifyActivity.BankVerify.BANK_ADD, "0");
                    }
                })
                .build().show();
    }

    private void boundBankCard_() {
        if (!TextUtils.isEmpty(balance)) {
            BalanceWithdrawalActivity.startActivity(this, balance);
        } else {
            BalanceWithdrawalActivity.startActivity(this, "0.00");
        }
    }
}

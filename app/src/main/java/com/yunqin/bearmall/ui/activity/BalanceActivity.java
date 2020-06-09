package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bbcoupon.ui.activity.AlipayCashActivity;
import com.bbcoupon.ui.activity.BindingAlipayActivity;
import com.bbcoupon.ui.bean.AlipayInfor;
import com.bbcoupon.ui.bean.WithdrawalInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bbcoupon.util.WindowUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.newversions.CardListWebActivity;
import com.newversions.view.ICustomDialog;
import com.yunqin.bearmall.AdConstants;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.CustomRotateAnim;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.MemberBeanResponse;
import com.yunqin.bearmall.bean.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

public class BalanceActivity extends BaseActivity implements PlatformActionListener, RequestContract.RequestView, View.OnClickListener {

    @BindView(R.id.banlance_text)
    TextView mongeyView;
    @BindView(R.id.toolbar_right_text)
    TextView rightText;
    @BindView(R.id.toolbar_title)
    TextView titleView;
    @BindView(R.id.ti_xian_ti_shi)
    TextView ti_xian_ti_shi;
    @BindView(R.id.red_package_img)
    ImageView imageView;
    @BindView(R.id.red_package_layout)
    ConstraintLayout constraintLayout;

    private RequestPresenter presenter;
    private boolean isBind = false;
    private String money;
    private String withdrawFrom;

    public static void startBalanceActivity(Context context, String money, String withdrawFrom) {
        Intent intent = new Intent(context, BalanceActivity.class);
        intent.putExtra("MONEY", money);
        intent.putExtra("withdrawFrom", withdrawFrom);
        context.startActivity(intent);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_banlance;
    }

    @Override
    public void init() {
        money = getIntent().getStringExtra("MONEY");
        withdrawFrom = getIntent().getStringExtra("withdrawFrom");
        rightText.setVisibility(View.VISIBLE);

        presenter = new RequestPresenter();
        presenter.setRelation(this);

        showLoading();
        Map<String, String> map = new HashMap<>();
        presenter.onWithdrawal(this, map);

        rightText.setText("余额明细");
        titleView.setText("余额");

        showAnimation();
    }

    /**
     * 设置动画
     */
    private void showAnimation() {
        // 获取自定义动画实例
        CustomRotateAnim rotateAnim = CustomRotateAnim.getCustomRotateAnim();
        // 一次动画执行1秒
        rotateAnim.setDuration(1000);
        // 设置为循环播放
        rotateAnim.setRepeatCount(-1);
        // 设置为匀速
        rotateAnim.setInterpolator(new LinearInterpolator());
        // 开始播放动画
        imageView.startAnimation(rotateAnim);
    }

    @OnClick({R.id.toolbar_back, R.id.toolbar_right_text, R.id.withdraw_deposit, R.id.ti_xian_ji_lu, R.id.ti_xian_gui_ze,
            R.id.red_package_close, R.id.red_package_img})
    public void viewClick(View view) {
        switch (view.getId()) {
            case R.id.red_package_close:
                constraintLayout.setVisibility(View.GONE);
                break;
            case R.id.red_package_img:
                // TODO 待定 URL
                CardListWebActivity.startActivity(BalanceActivity.this, AdConstants.STRING_BALANCE_ACTIVITY, "");
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_right_text:
                BalanceDetailActivity.startBalanceDetailActivity(this, money);
                break;
            case R.id.withdraw_deposit:
//                PopupWindow popupWindow = WindowUtils.ShowVirtual(BalanceActivity.this, R.layout.item_popup_withdrawal_options,
//                        R.style.bottom_animation, 2);
//                popupWindow.getContentView().findViewById(R.id.wit_cancel).setOnClickListener(this);
//                popupWindow.getContentView().findViewById(R.id.wit_zfb).setOnClickListener(this);
//                popupWindow.getContentView().findViewById(R.id.wit_wx).setOnClickListener(this);
//                popupWindow.getContentView().findViewById(R.id.wit_yhk).setOnClickListener(this);
                Map<String, String> map = new HashMap<>();
                presenter.onWithOutAlipay(BalanceActivity.this, map);
                WindowUtils.dismissBrightness(BalanceActivity.this);
                break;
            case R.id.ti_xian_ji_lu:
                WithdrawalRecordActivity.startActivity(this);
                break;
            case R.id.ti_xian_gui_ze:
                String guidelUrl = BuildConfig.BASE_URL + "/view/getWithdrawRules";
                VanguardListPageActivity.startH5Activity(this, guidelUrl, "提现规则");
                break;
            default:
                break;
        }
    }

    /**
     * 提现到微信
     */
    private void checkWx() {
//        if (BearMallAplication.getInstance().getUser().getData().getMember().isMember()) {
        if (BearMallAplication.getInstance().getUser().getData().getMember().isBindWxopenId() || isBind) {
            BalanceWithdrawalWxActivity.startActivity(this, money);
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
//        } else {
//            joinMember_();
//        }
    }

    /**
     * 提现到银行卡
     */
    private void checkBank() {
        showLoading();
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).isBindBankCard(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                hiddenLoadingView();
                JSONObject jsonObject = new JSONObject(data);
                boolean isBindBankCard = jsonObject.optBoolean("isBindBankCard");
                if (isBindBankCard) {
                    boundBankCard_();
                } else {
                    notBoundBankCard_();
                }
            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
            }
        });
    }

    private void boundBankCard_() {
//        if (BearMallAplication.getInstance().getUser().getData().getMember().isMember()) {
        BalanceWithdrawalActivity.startActivity(this, money);
//        } else {
//            joinMember_();
//        }
    }

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
                        BankAddVerifyActivity.startActivity(BalanceActivity.this, BankAddVerifyActivity.BankVerify.BANK_ADD, "0");
                    }
                })
                .build().show();
    }

    private void joinMember_() {
        new ICustomDialog.Builder(this)
                // 设置布局
                .setLayoutResId(R.layout.join_huiyuan_layout)
                // 点击空白是否消失
                .setCanceledOnTouchOutside(false)
                // 点击返回键是否消失
                .setCancelable(false)
                // 设置Dialog的绝对位置
                .setDialogPosition(Gravity.CENTER)
                // 设置自定义动画
                .setAnimationResId(0)
                // 设置监听ID
                .setListenedItems(new int[]{R.id.ok, R.id.close_dialog_})
                // 设置回掉
                .setOnDialogItemClickListener((thisDialog, clickView) -> {
                    thisDialog.dismiss();
                    if (clickView.getId() == R.id.ok) {
                        startActivity(new Intent(BalanceActivity.this, VipCenterActivity.class));
                    }
                })
                .build().show();
    }

    // ----- 以下是微信绑定回调
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        // 绑定微信成功
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("open_id", platform.getDb().get("unionid"));
        mHashMap.put("wxopen_id", platform.getDb().get("openid"));
        mHashMap.put("bindType", "1");
        mHashMap.put("wx_accessToken", platform.getDb().getToken());
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).memberThirdPartyBind(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                isBind = true;
                Toast.makeText(BalanceActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                RetrofitApi.request(BalanceActivity.this, RetrofitApi.createApi(Api.class).getMemberInfo(new HashMap<>()),
                        new RetrofitApi.IResponseListener() {
                            @Override
                            public void onSuccess(String data) {
                                hiddenLoadingView();
                                try {
                                    UserInfo userInfo = BearMallAplication.getInstance().getUser();
                                    UserInfo.DataBean dataBean = userInfo.getData();
                                    MemberBeanResponse response = new Gson().fromJson(data, MemberBeanResponse.class);
                                    UserInfo.DataBean.MemberBean memberBean = response.getData();
                                    UserInfo.Identity identity = response.getIdentity();
                                    dataBean.setMember(memberBean);
                                    userInfo.setData(dataBean);
                                    userInfo.setIdentity(identity);
                                    BearMallAplication.getInstance().setUser(userInfo);
                                } catch (JsonSyntaxException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onNotNetWork() {
                                hiddenLoadingView();
                            }

                            @Override
                            public void onFail(Throwable e) {
                                hiddenLoadingView();
                            }
                        });
            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
            }
        });

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        hiddenLoadingView();
        Toast.makeText(this, "绑定微信取消" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        hiddenLoadingView();
        Toast.makeText(this, "绑定微信取消", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(Object data) {
        hiddenLoadingView();
        if (data instanceof WithdrawalInfor) {
            WithdrawalInfor withdrawalInfor = (WithdrawalInfor) data;
            if (data != null) {
                if (!TextUtils.isEmpty(withdrawalInfor.getData().getBalance())) {
                    money = withdrawalInfor.getData().getBalance();
                    mongeyView.setText("¥" + withdrawalInfor.getData().getBalance());
                } else {
                    mongeyView.setText("¥0.00");
                }
                if (!TextUtils.isEmpty(withdrawalInfor.getData().getWithdrawFrom())) {
                    withdrawFrom = withdrawalInfor.getData().getWithdrawFrom();
                    ti_xian_ti_shi.setText("最小提现金额：¥" + withdrawalInfor.getData().getWithdrawFrom());
                } else {
                    ti_xian_ti_shi.setText("最小提现金额：¥" + 0);
                    withdrawFrom = "0";
                }
            }
        }
        if (data instanceof AlipayInfor) {
            Bundle bundle = new Bundle();
            AlipayInfor baseInfor = (AlipayInfor) data;
            if (baseInfor.getCode() == 1) {
                AlipayCashActivity.openAlipayCashActivity(BalanceActivity.this, AlipayCashActivity.class);
            } else {
                bundle.putString("TITLE", "绑定支付宝");
                bundle.putInt("TYPE", 0);
                BindingAlipayActivity.openBindingAlipayActivity(BalanceActivity.this, BindingAlipayActivity.class, bundle);
            }
        }
    }

    @Override
    public void onNotNetWork() {
        hiddenLoadingView();
    }

    @Override
    public void onFail(Throwable e) {
        hiddenLoadingView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.setUntying(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wit_cancel:
                WindowUtils.dismissBrightness(BalanceActivity.this);
                break;
            case R.id.wit_zfb:
                Map<String, String> map = new HashMap<>();
                presenter.onWithOutAlipay(BalanceActivity.this, map);
                WindowUtils.dismissBrightness(BalanceActivity.this);
                break;
            case R.id.wit_wx:
                checkWx();
                WindowUtils.dismissBrightness(BalanceActivity.this);
                break;
            case R.id.wit_yhk:
                checkBank();
                WindowUtils.dismissBrightness(BalanceActivity.this);
                break;
        }
    }
}

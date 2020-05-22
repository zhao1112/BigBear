package com.yunqin.bearmall.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.newversions.CardListWebActivity;
import com.newversions.passwd.SettingPwdActivity;
import com.newversions.view.ICustomPayDialog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunqin.bearmall.AdConstants;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.PayResult;
import com.yunqin.bearmall.bean.UserBTInfo;
import com.yunqin.bearmall.eventbus.Wx;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.IsInstallWeChatOrAliPay;
import com.yunqin.bearmall.util.StarActivityUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Master
 */
public class PayActivity extends BaseActivity {

    private IWXAPI api;
    @BindView(R.id.bt)
    TextView bt;
    @BindView(R.id.xzf)
    TextView xzf;
    @BindView(R.id.rmb)
    TextView rmb;
    @BindView(R.id.toolbar_title)
    TextView titleTextView;
    @BindView(R.id.yu_e_)
    TextView yu_e_;
    @BindView(R.id.check_box_wx_)
    CheckBox wxCheckBox;
    @BindView(R.id.check_box_ye_)
    CheckBox yuECheckBox;
    @BindView(R.id.check_box_bt_)
    CheckBox btbCheckBox;
    @BindView(R.id.check_box_zfb_)
    CheckBox zfbCheckBox;
    @BindView(R.id.check_box_zfb_ye_)
    CheckBox check_box_zfb_ye_;
    @BindView(R.id.check_box_wx_ye_)
    CheckBox check_box_wx_ye_;
    List<CheckBox> list;
    @BindView(R.id.ye_bt)
    TextView ye_bt;
    @BindView(R.id.yu_e_container)
    LinearLayout yu_e_container;
    @BindView(R.id.hun_he_wx)
    LinearLayout hun_he_wx;
    @BindView(R.id.hun_he_zfb)
    LinearLayout hun_he_zfb;

    private String btAmount;
    private String amount;
    private String outTradeNo;
    private String ordersId;
    private int orderProductType;
    private int isNeedPay = -1;
    @BindView(R.id.pay)
    Button pay;

    private boolean isBanlancePaid = false;
    private boolean member_charge = false;
    private String mType_name;

    @Override
    public int layoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);

        api = WXAPIFactory.createWXAPI(this, "wx10529911212690c7");
        api.registerApp("wx10529911212690c7");
        titleTextView.setText("大熊收银台");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            Toast.makeText(this, "获取参数异常", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        btAmount = bundle.getString("btAmount");
        mType_name = bundle.getString("type_name");
        amount = bundle.getString("amount");
        outTradeNo = bundle.getString("outTradeNo");
        ordersId = bundle.getString("ordersId");
        orderProductType = bundle.getInt("orderProductType");
        isNeedPay = bundle.getInt("isNeedPay", -1);
        isBanlancePaid = bundle.getBoolean("isBanlancePaid", false);

        member_charge = bundle.getBoolean("member_charge", false);

        Log.e("JINGYING", "" + member_charge);

        bt.setText("BC" + btAmount);
        rmb.setText("¥" + amount);
        xzf.setText("需支付BC" + btAmount);

        initBt();

        list = new ArrayList<>();
        list.add(wxCheckBox);
        list.add(btbCheckBox);
        list.add(yuECheckBox);
        list.add(zfbCheckBox);
        list.add(check_box_zfb_ye_);
        list.add(check_box_wx_ye_);

        if (orderProductType != 0) {
            // TODO 虚拟订单隐藏余额支付和混合支付
            yu_e_container.setVisibility(View.GONE);
            hun_he_wx.setVisibility(View.GONE);
            hun_he_zfb.setVisibility(View.GONE);
        }

    }

    @BindView(R.id.go_renwu)
    TextView go_renwu;
    private double balanceOfAccount;
    private boolean checkYe = true;
    private boolean checkHh = true;
    @BindView(R.id.ye_tv)
    TextView ye_tv;
    @BindView(R.id.wx_ye_tv)
    TextView wx_ye_tv;
    @BindView(R.id.zfb_ye_tv)
    TextView zfb_ye_tv;
    private String stringPrice;

    private void initBt() {
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getUserBTData(new HashMap<String, String>()),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) {
                        UserBTInfo userBTInfo = new Gson().fromJson(data, UserBTInfo.class);
                        String banlance = userBTInfo.getData().getBCbanlance();
                        ye_bt.setText("余额BC" + banlance);
                        stringPrice = userBTInfo.getData().getBanlance();
                        balanceOfAccount = Double.valueOf(stringPrice);
                        yu_e_.setText("¥" + stringPrice);
                        // 判断
                        if (balanceOfAccount == 0) {
                            ye_tv.setTextColor(Color.parseColor("#c1c0c0"));
                            wx_ye_tv.setTextColor(Color.parseColor("#c1c0c0"));
                            zfb_ye_tv.setTextColor(Color.parseColor("#c1c0c0"));
                            checkYe = false;
                            checkHh = false;
                        }
                        // 字体颜色     c1c0c0
                        if (balanceOfAccount >= Double.valueOf(amount)) {
                            wx_ye_tv.setTextColor(Color.parseColor("#c1c0c0"));
                            zfb_ye_tv.setTextColor(Color.parseColor("#c1c0c0"));
                            checkYe = true;
                            checkHh = false;
                        }

                        if (isBanlancePaid) {
                            ye_tv.setTextColor(Color.parseColor("#c1c0c0"));
                            wx_ye_tv.setTextColor(Color.parseColor("#c1c0c0"));
                            zfb_ye_tv.setTextColor(Color.parseColor("#c1c0c0"));
                            checkYe = false;
                            checkHh = false;
                        }

                        double banlance1 = Double.valueOf(banlance);
                        double bt = Double.valueOf(btAmount);
                        if (banlance1 >= bt) {
                            go_renwu.setVisibility(View.INVISIBLE);
                        } else {
                            go_renwu.setVisibility(View.VISIBLE);
                            pay.setEnabled(false);
                        }
                    }

                    @Override
                    public void onNotNetWork() {

                    }

                    @Override
                    public void onFail(Throwable e) {
                    }
                });
    }

    @OnClick({R.id.check_box_wx, R.id.check_box_bt, R.id.check_box_zfb, R.id.toolbar_back, R.id.pay, R.id.go_renwu,
            R.id.check_box_ye, R.id.check_box_wx_ye, R.id.check_box_zfb_ye})
    public void onSelect(View view) {
        switch (view.getId()) {
            case R.id.check_box_wx_ye:
                if (checkHh) {
                    yuECheckBox.setChecked(false);
                    wxCheckBox.setChecked(false);
                    zfbCheckBox.setChecked(false);
                    check_box_zfb_ye_.setChecked(false);
                    check_box_wx_ye_.setChecked(true);
                }
                break;
            case R.id.check_box_zfb_ye:
                if (checkHh) {
                    yuECheckBox.setChecked(false);
                    wxCheckBox.setChecked(false);
                    zfbCheckBox.setChecked(false);
                    check_box_zfb_ye_.setChecked(true);
                    check_box_wx_ye_.setChecked(false);
                }
                break;
            case R.id.check_box_ye:
                if (checkYe) {
                    yuECheckBox.setChecked(true);
                    wxCheckBox.setChecked(false);
                    zfbCheckBox.setChecked(false);
                    check_box_zfb_ye_.setChecked(false);
                    check_box_wx_ye_.setChecked(false);
                }
                break;
            case R.id.check_box_wx:
                wxCheckBox.setChecked(true);
                yuECheckBox.setChecked(false);
                zfbCheckBox.setChecked(false);
                check_box_zfb_ye_.setChecked(false);
                check_box_wx_ye_.setChecked(false);
                break;
            case R.id.check_box_bt:
                break;
            case R.id.check_box_zfb:
                yuECheckBox.setChecked(false);
                wxCheckBox.setChecked(false);
                zfbCheckBox.setChecked(true);
                check_box_zfb_ye_.setChecked(false);
                check_box_wx_ye_.setChecked(false);
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.pay:
                go_Pay();
                //TODO[点击立即充值]
                 ConstantScUtil.VIPRechargeSubmit(mType_name, amount + "");
                break;
            case R.id.go_renwu:
                StarActivityUtil.starActivity(this, DailyTasksActivity.class);
                break;
            default:
                break;
        }
    }

    private void go_Pay() {
        showLoading();
        if (isNeedPay == 0) {
            Map<String, String> map = new HashMap<>();
            map.put("outTradeNo", outTradeNo);
            RetrofitApi.request(PayActivity.this, RetrofitApi.createApi(Api.class).ordersBcPay(map), new RetrofitApi.IResponseListener() {
                @Override
                public void onSuccess(String data) throws JSONException {
                    hiddenLoadingView();
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.optInt("code");
                    if (code == 1) {
                        BearMallAplication.getInstance().getActivityStack().finishActivity(MineOrderActivity.class);
                        BearMallAplication.getInstance().getActivityStack().finishActivity(ConfirmActivity.class);
                        BearMallAplication.getInstance().getActivityStack().finishActivity(OpenVipActivity.class);
                        BearMallAplication.getInstance().getActivityStack().finishActivity(VipCenterActivity.class);
                        // TODO 如果是充值会员  就跳转另外...
                        if (member_charge) {
                            PayOverMemberActivity.start(PayActivity.this);
                            PayActivity.this.finish();
                            return;
                        }
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        CardListWebActivity.startActivity(PayActivity.this, AdConstants.STRING_PAY_OVER, "专享福利");
                        PayActivity.this.finish();
                    } else {
                        Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNotNetWork() {
                    hiddenLoadingView();
                    Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFail(Throwable e) {
                    hiddenLoadingView();
                    Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        String payType = "0";
        if (check_box_wx_ye_.isChecked()) {// 微信混合支付
            payType = "3";
            if (!IsInstallWeChatOrAliPay.isWeixinAvilible(this)) {
                hiddenLoadingView();
                Toast.makeText(this, "当前手机未安装微信,请安装后重试!", Toast.LENGTH_SHORT).show();
            } else {
                wxYePay();
            }
            return;
        }

        if (check_box_zfb_ye_.isChecked()) {// 支付宝混合支付
            payType = "4";
            if (!IsInstallWeChatOrAliPay.isWeixinAvilible(this)) {
                hiddenLoadingView();
                Toast.makeText(this, "当前手机未安装支付宝,请安装后重试!", Toast.LENGTH_SHORT).show();
            } else {
                zfbYe();
            }
            return;
        }

        if (wxCheckBox.isChecked()) {
            //TODO[会员支付方式]
            ConstantScUtil.VIPPayType("微信");
            payType = "1";
            if (!IsInstallWeChatOrAliPay.isWeixinAvilible(this)) {
                hiddenLoadingView();
                Toast.makeText(this, "当前手机未安装微信,请安装后重试!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (zfbCheckBox.isChecked()) {
            //TODO[会员支付方式]
            ConstantScUtil.VIPPayType("支付宝");
            payType = "2";
            if (!IsInstallWeChatOrAliPay.checkAliPayInstalled(this)) {
                hiddenLoadingView();
                Toast.makeText(this, "当前手机未安装支付宝,请安装后重试!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (yuECheckBox.isChecked()) {
            RetrofitApi.request(this, RetrofitApi.createApi(Api.class).validatePaymentPwdStatus(), new RetrofitApi.IResponseListener() {
                @Override
                public void onSuccess(String data) throws JSONException {
                    hiddenLoadingView();
                    JSONObject jsonObject = new JSONObject(data);
                    if (1 == jsonObject.optInt("code")) {
                        JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                        if (jsonObject1.optInt("validateResult") == 1) {
                            double iAmount = Double.valueOf(amount);
                            if (balanceOfAccount > iAmount) {
                                new ICustomPayDialog.Builder(PayActivity.this)
                                        .setCanceledOnTouchOutside(false)
                                        .setCancelable(false)
                                        .setDialogPosition(Gravity.CENTER)
                                        .setAnimationResId(0)
                                        .setOnDialogTitle("请输入余额支付密码")
                                        .setOnDialogPrice("¥" + amount)
                                        .setOnDialogItemClickListener(new ICustomPayDialog.OnDialogItemClickListener() {
                                            @Override
                                            public void onDialogItemClick(final ICustomPayDialog thisDialog, String data) {
                                                showLoading();

                                                Map<String, String> mHashMap = new HashMap<>();
                                                mHashMap.put("paymentPwd", data);
                                                mHashMap.put("orderProductType", orderProductType + "");
                                                mHashMap.put("outTradeNo", outTradeNo);
                                                mHashMap.put("amount", amount);
                                                mHashMap.put("ordersId", ordersId);
                                                double bt = Double.valueOf(btAmount);
                                                mHashMap.put("btAmount", ((int) bt) + "");

                                                RetrofitApi.request(PayActivity.this,
                                                        RetrofitApi.createApi(Api.class).balancePayment(mHashMap),
                                                        new RetrofitApi.IResponseListener() {
                                                            @Override
                                                            public void onSuccess(String data) throws JSONException {
                                                                hiddenLoadingView();
                                                                JSONObject jsonObject = new JSONObject(data);
                                                                BearMallAplication.getInstance().getActivityStack().finishActivity(MineOrderActivity.class);
                                                                BearMallAplication.getInstance().getActivityStack().finishActivity(ConfirmActivity.class);
                                                                BearMallAplication.getInstance().getActivityStack().finishActivity(OpenVipActivity.class);
                                                                BearMallAplication.getInstance().getActivityStack().finishActivity(VipCenterActivity.class);
                                                                if (jsonObject.optInt("code") == 1) {
                                                                    thisDialog.dismiss();
                                                                    if (member_charge) {
                                                                        PayOverMemberActivity.start(PayActivity.this);
                                                                        PayActivity.this.finish();
                                                                        return;
                                                                    }
                                                                    Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                                                                    CardListWebActivity.startActivity(PayActivity.this,
                                                                            AdConstants.STRING_PAY_OVER, "专享福利");
                                                                    PayActivity.this.finish();
                                                                }
                                                            }

                                                            @Override
                                                            public void onNotNetWork() {
                                                                hiddenLoadingView();
                                                                thisDialog.failPwd();
                                                            }

                                                            @Override
                                                            public void onFail(Throwable e) {
                                                                hiddenLoadingView();
                                                            }
                                                        });


                                            }
                                        })
                                        .build().show();
                            } else {
                                Toast.makeText(PayActivity.this, "余额不足,请选择其他方式支付", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            startActivity(new Intent(PayActivity.this, SettingPwdActivity.class));
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
            });
            return;
        }

        if (payType.equals("0")) {
            hiddenLoadingView();
            Toast.makeText(this, "请选择支付方式", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("payType", payType);
        mHashMap.put("outTradeNo", outTradeNo);
        mHashMap.put("amount", amount);
        mHashMap.put("ordersId", ordersId);
        double bt = Double.valueOf(btAmount);
        mHashMap.put("btAmount", ((int) bt) + "");
        mHashMap.put("orderProductType", orderProductType + "");

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getPayParams(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                Log.e("outTradeNo", data);
                JSONObject jsonObject = new JSONObject(data);
                JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                if (zfbCheckBox.isChecked()) {
                    zfb(jsonObject1.optString("params"));
                }
                if (wxCheckBox.isChecked()) {
                    wx(jsonObject1.optJSONObject("params"));
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
                String data = e.getMessage();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                Toast.makeText(PayActivity.this, "请求支付参数失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void zfb(String params) {
        Observable.create((ObservableOnSubscribe<Map<String, String>>) e -> {
            PayTask alipay = new PayTask(PayActivity.this);
            Map<String, String> result = alipay.payV2(params, true);
            Thread.sleep(3000);
            e.onNext(result);
            e.onComplete();
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Map<String, String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Map<String, String> stringStringMap) {
                        Log.e("TAG", stringStringMap.toString());

                        PayResult payResult = new PayResult(stringStringMap);
                        if (TextUtils.equals(payResult.getResultStatus(), "9000")) {
                            // TODO 去服务器查询支付状态
                            checkOrderStatus("2", outTradeNo);
                        } else {
                            hiddenLoadingView();

                            BearMallAplication.getInstance().getActivityStack().finishActivity(MineOrderActivity.class);
                            BearMallAplication.getInstance().getActivityStack().finishActivity(ConfirmActivity.class);

                            PayOverActivity.start(PayActivity.this, "支付失败");
                            PayActivity.this.finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void wx(JSONObject params) {
        try {
            PayReq req = new PayReq();
            req.appId = params.getString("appId");
            req.partnerId = params.getString("partnerId");
            req.prepayId = params.getString("prepayId");
            req.nonceStr = params.getString("nonceStr");
            req.timeStamp = params.getString("timeStamp");
            req.packageValue = params.getString("package");
            req.sign = params.getString("sign");

            api.sendReq(req);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static final int WXCODESUCCESS = 706;
    private static final int WXCODECANCEL = 707;
    private static final int WXCODEFAIL = 708;

    @Subscribe
    public void wxc(Wx wx) {
        if (wx.getCode() == WXCODESUCCESS) {
            checkOrderStatus("1", outTradeNo);
        } else if (wx.getCode() == WXCODECANCEL) {
            hiddenLoadingView();
            BearMallAplication.getInstance().getActivityStack().finishActivity(MineOrderActivity.class);
            BearMallAplication.getInstance().getActivityStack().finishActivity(ConfirmActivity.class);
            PayOverActivity.start(PayActivity.this, "支付失败");
            PayActivity.this.finish();
        } else if (wx.getCode() == WXCODEFAIL) {
            hiddenLoadingView();
            BearMallAplication.getInstance().getActivityStack().finishActivity(MineOrderActivity.class);
            BearMallAplication.getInstance().getActivityStack().finishActivity(ConfirmActivity.class);
            PayOverActivity.start(PayActivity.this, "支付失败");
            PayActivity.this.finish();
        } else {

        }
    }

    private void checkOrderStatus(String payType, String outTradeNo) {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("payType", payType);
        mHashMap.put("outTradeNo", outTradeNo);
        mHashMap.put("orderProductType", String.valueOf(orderProductType));
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).queryOrderPayResult(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                JSONObject jsonObject = new JSONObject(data);
                JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                int queryResult = jsonObject1.optInt("queryResult");
                if (queryResult == 0) {
                    // TODO 未查询到结果
                    hiddenLoadingView();
                    BearMallAplication.getInstance().getActivityStack().finishActivity(MineOrderActivity.class);
                    BearMallAplication.getInstance().getActivityStack().finishActivity(ConfirmActivity.class);
                    Intent intent = new Intent(PayActivity.this, PayOverActivity.class);
                    intent.putExtra("status", "订单已提交");
                    startActivity(intent);
                    PayActivity.this.finish();
                } else {
                    // TODO 支付完成
                    hiddenLoadingView();
                    BearMallAplication.getInstance().getActivityStack().finishActivity(MineOrderActivity.class);
                    BearMallAplication.getInstance().getActivityStack().finishActivity(ConfirmActivity.class);
                    if (member_charge) {
                        PayOverMemberActivity.start(PayActivity.this);
                        PayActivity.this.finish();
                        return;
                    }
                    Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    CardListWebActivity.startActivity(PayActivity.this, AdConstants.STRING_PAY_OVER, "专享福利");
                    PayActivity.this.finish();
                }
            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
                Toast.makeText(PayActivity.this, "网络错误,请稍后去订单中查看", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
                Toast.makeText(PayActivity.this, "网络错误,请稍后去订单中查看", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void wxYePay() {
        Log.i("RetrofitApi", "wxYePay: ");
        // TODO 先判断是否设置了支付密码
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).validatePaymentPwdStatus(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                hiddenLoadingView();
                JSONObject jsonObject = new JSONObject(data);
                if (1 == jsonObject.optInt("code")) {
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    if (jsonObject1.optInt("validateResult") == 1) {
                        new ICustomPayDialog.Builder(PayActivity.this)
                                .setCanceledOnTouchOutside(false)
                                .setCancelable(false)
                                .setDialogPosition(Gravity.CENTER)
                                .setAnimationResId(0)
                                .setOnDialogTitle("请输入余额支付密码")
                                .setOnDialogPrice(yu_e_.getText().toString())
                                .setOnDialogItemClickListener(new ICustomPayDialog.OnDialogItemClickListener() {
                                    @Override
                                    public void onDialogItemClick(final ICustomPayDialog thisDialog, String data) {
                                        showLoading();
                                        Map<String, String> mHashMap = new HashMap<>();
                                        mHashMap.put("amount", stringPrice);// 钱
                                        mHashMap.put("ordersId", ordersId);
                                        mHashMap.put("orderProductType", orderProductType + "");
                                        mHashMap.put("outTradeNo", outTradeNo);
                                        mHashMap.put("paymentPwd", data);
                                        mHashMap.put("payType", "3");
                                        mHashMap.put("isWxSmall", "0");
                                        double bt = Double.valueOf(btAmount);
                                        mHashMap.put("btAmount", ((int) bt) + "");
                                        RetrofitApi.request(PayActivity.this,
                                                RetrofitApi.createApi(Api.class).unionPaymentPwdValid(mHashMap),
                                                new RetrofitApi.IResponseListener() {
                                                    @Override
                                                    public void onSuccess(String data) throws JSONException {
                                                        hiddenLoadingView();
                                                        JSONObject jsonObject = new JSONObject(data);
                                                        JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                                                        wx(jsonObject1.optJSONObject("params"));
                                                    }

                                                    @Override
                                                    public void onNotNetWork() {
                                                        hiddenLoadingView();
                                                        thisDialog.failPwd();
                                                    }

                                                    @Override
                                                    public void onFail(Throwable e) {
                                                        hiddenLoadingView();
                                                    }
                                                });


                                    }
                                })
                                .build().show();

                    } else {
                        startActivity(new Intent(PayActivity.this, SettingPwdActivity.class));
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
        });
    }

    private void zfbYe() {
        Log.i("RetrofitApi", "zfbYe: ");
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).validatePaymentPwdStatus(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                hiddenLoadingView();
                JSONObject jsonObject = new JSONObject(data);
                if (1 == jsonObject.optInt("code")) {
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    if (jsonObject1.optInt("validateResult") == 1) {

                        new ICustomPayDialog.Builder(PayActivity.this)
                                .setCanceledOnTouchOutside(false)
                                .setCancelable(false)
                                .setDialogPosition(Gravity.CENTER)
                                .setAnimationResId(0)
                                .setOnDialogTitle("请输入余额支付密码")
                                .setOnDialogPrice(yu_e_.getText().toString())
                                .setOnDialogItemClickListener(new ICustomPayDialog.OnDialogItemClickListener() {
                                    @Override
                                    public void onDialogItemClick(final ICustomPayDialog thisDialog, String data) {
                                        showLoading();
                                        Map<String, String> mHashMap = new HashMap<>();
                                        mHashMap.put("amount", stringPrice);// 钱
                                        mHashMap.put("ordersId", ordersId);
                                        mHashMap.put("orderProductType", orderProductType + "");
                                        mHashMap.put("outTradeNo", outTradeNo);
                                        mHashMap.put("paymentPwd", data);
                                        mHashMap.put("payType", "4");
                                        mHashMap.put("isWxSmall", "0");
                                        double bt = Double.valueOf(btAmount);
                                        mHashMap.put("btAmount", ((int) bt) + "");
                                        RetrofitApi.request(PayActivity.this,
                                                RetrofitApi.createApi(Api.class).unionPaymentPwdValid(mHashMap),
                                                new RetrofitApi.IResponseListener() {
                                                    @Override
                                                    public void onSuccess(String data) throws JSONException {
                                                        hiddenLoadingView();
                                                        JSONObject jsonObject = new JSONObject(data);
                                                        JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                                                        zfb(jsonObject1.optString("params"));
                                                    }

                                                    @Override
                                                    public void onNotNetWork() {
                                                        hiddenLoadingView();
                                                        thisDialog.failPwd();
                                                    }

                                                    @Override
                                                    public void onFail(Throwable e) {
                                                        hiddenLoadingView();
                                                    }
                                                });
                                    }
                                })
                                .build().show();

                    } else {
                        startActivity(new Intent(PayActivity.this, SettingPwdActivity.class));
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
        });

    }

}

package com.bbcoupon.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bbcoupon.ui.bean.AlipayInfor;
import com.bbcoupon.ui.bean.BaseInfor;
import com.bbcoupon.ui.bean.WithdrawalInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bbcoupon.util.WindowUtils;
import com.newversions.passwd.IMD5;
import com.newversions.passwd.RetrievePwdActivity;
import com.newversions.passwd.SettingPwdActivity;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.SettingBean;
import com.yunqin.bearmall.ui.activity.MineProfitActivity;
import com.yunqin.bearmall.util.CashierInputFilter;
import com.yunqin.bearmall.util.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/5/19
 */
public class AlipayCashActivity extends BaseActivity implements View.OnClickListener, RequestContract.RequestView {

    @BindView(R.id.amount)
    EditText mAmount;
    @BindView(R.id.price)
    TextView mPrice;
    @BindView(R.id.all_price)
    LinearLayout mAllPrice;
    @BindView(R.id.tip_price)
    LinearLayout mTipPrice;
    @BindView(R.id.alip_pwd)
    TextView mAlipPwd;
    @BindView(R.id.alip_code)
    TextView mAlipCode;
    @BindView(R.id.alip_name)
    TextView mAlipName;

    private StringBuffer newPwdStringBuffer;
    private List<TextView> pwdTvs;
    private TextView pwd_1, pwd_2, pwd_3, pwd_4, pwd_5, pwd_6;
    private RequestPresenter presenter;
    private String money;
    private String withdrawfrom;
    private String accountNum;
    private String name;
    private String code;
    private boolean isSetPayPwd = false;

    public static void openAlipayCashActivity(Activity activity, Class cla) {
        Intent intent = new Intent(activity, cla);
        activity.startActivity(intent);
    }

    public static void openAlipayCashActivitys(Activity activity, Class cla) {
        Intent intent = new Intent(activity, cla);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_alipay_cash;
    }

    @Override
    public void init() {

        presenter = new RequestPresenter();
        presenter.setRelation(this);

        Map<String, String> map = new HashMap<>();
        presenter.onWithdrawal(this, map);

        CashierInputFilter cashierInputFilter = new CashierInputFilter();
        InputFilter[] inputFilters = new InputFilter[]{cashierInputFilter};
        mAmount.setFilters(inputFilters);
        mAmount.addTextChangedListener(textWatcher);

        mAlipPwd.setClickable(false);

        pwdTvs = new ArrayList<>();
        newPwdStringBuffer = new StringBuffer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, String> map = new HashMap<>();
        presenter.onWithOutAlipay(AlipayCashActivity.this, map);
        presenter.onSettingMemberInfo(this, map);
    }

    @OnClick({R.id.toolbar_back, R.id.updata, R.id.allmoney, R.id.alip_pwd})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.updata://修改支付宝
                bundle.putString("TITLE", "确认支付宝信息");
                bundle.putInt("TYPE", 1);
                bundle.putString("NAME", name);
                bundle.putString("CODE", code);
                BindingAlipayActivity.openBindingAlipayActivity(AlipayCashActivity.this, BindingAlipayActivity.class, bundle);
                break;
            case R.id.allmoney:
                mAmount.setText(money);
                break;
            case R.id.alip_pwd:
                if (!isSetPayPwd) {
                    startActivity(new Intent(AlipayCashActivity.this, SettingPwdActivity.class));
                    return;
                }
                setPwd();
                break;
        }
    }

    public void setPwd() {
        hiddenKeyboard();
        double drawMoney = Double.parseDouble(mAmount.getText().toString());
        double money = Double.parseDouble(this.money);
        double withdrawfroms = Double.parseDouble(this.withdrawfrom);

        if (drawMoney > money) {
            showToast("提现金额高于可提现金额不可提现", Gravity.CENTER);
            return;
        }
        if (drawMoney < withdrawfroms) {
            mAllPrice.setVisibility(View.GONE);
            mTipPrice.setVisibility(View.VISIBLE);
            return;
        }
        //输入密码
        PopupWindow popupWindow = WindowUtils.ShowVirtual(AlipayCashActivity.this, R.layout.item_alip_pwd,
                R.style.bottom_animation, 2);
        pwd_1 = popupWindow.getContentView().findViewById(R.id.pwd_1);
        pwd_2 = popupWindow.getContentView().findViewById(R.id.pwd_2);
        pwd_3 = popupWindow.getContentView().findViewById(R.id.pwd_3);
        pwd_4 = popupWindow.getContentView().findViewById(R.id.pwd_4);
        pwd_5 = popupWindow.getContentView().findViewById(R.id.pwd_5);
        pwd_6 = popupWindow.getContentView().findViewById(R.id.pwd_6);
        TextView al_name = popupWindow.getContentView().findViewById(R.id.al_name);
        al_name.setText("提现到支付宝账户（" + name + ")");
        pwdTvs.add(pwd_1);
        pwdTvs.add(pwd_2);
        pwdTvs.add(pwd_3);
        pwdTvs.add(pwd_4);
        pwdTvs.add(pwd_5);
        pwdTvs.add(pwd_6);
        popupWindow.getContentView().findViewById(R.id.close).setOnClickListener(this);
        popupWindow.getContentView().findViewById(R.id.set_0).setOnClickListener(this);
        popupWindow.getContentView().findViewById(R.id.set_1).setOnClickListener(this);
        popupWindow.getContentView().findViewById(R.id.set_2).setOnClickListener(this);
        popupWindow.getContentView().findViewById(R.id.set_3).setOnClickListener(this);
        popupWindow.getContentView().findViewById(R.id.set_4).setOnClickListener(this);
        popupWindow.getContentView().findViewById(R.id.set_5).setOnClickListener(this);
        popupWindow.getContentView().findViewById(R.id.set_6).setOnClickListener(this);
        popupWindow.getContentView().findViewById(R.id.set_7).setOnClickListener(this);
        popupWindow.getContentView().findViewById(R.id.set_8).setOnClickListener(this);
        popupWindow.getContentView().findViewById(R.id.set_9).setOnClickListener(this);
        popupWindow.getContentView().findViewById(R.id.set_del).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.set_0:
                assemblePwd(0);
                break;
            case R.id.set_1:
                assemblePwd(1);
                break;
            case R.id.set_2:
                assemblePwd(2);
                break;
            case R.id.set_3:
                assemblePwd(3);
                break;
            case R.id.set_4:
                assemblePwd(4);
                break;
            case R.id.set_5:
                assemblePwd(5);
                break;
            case R.id.set_6:
                assemblePwd(6);
                break;
            case R.id.set_7:
                assemblePwd(7);
                break;
            case R.id.set_8:
                assemblePwd(8);
                break;
            case R.id.set_9:
                assemblePwd(9);
                break;
            case R.id.set_del:
                assemblePwd(-1);
                break;
            case R.id.close:
                WindowUtils.dismissBrightness(AlipayCashActivity.this);
                break;
            case R.id.p_close://忘记密码
                WindowUtils.dismissBrightness(AlipayCashActivity.this);
                startActivity(new Intent(this, RetrievePwdActivity.class));
                break;
            case R.id.p_true://重试
                WindowUtils.dismissBrightness(AlipayCashActivity.this);
                setPwd();
                break;
        }
    }

    //设置密码
    private void assemblePwd(int pwd) {
        if (pwd == -1) {
            if (newPwdStringBuffer.length() > 0) {
                newPwdStringBuffer.deleteCharAt(newPwdStringBuffer.length() - 1);
            }
            setPwd(newPwdStringBuffer);
            return;
        }
        if (newPwdStringBuffer.length() == 6) {
            Log.e("assemblePwd", "assemblePwd: ");
            return;
        }
        newPwdStringBuffer.append(pwd);
        setPwd(newPwdStringBuffer);
    }

    //加入密码
    private void setPwd(StringBuffer stringBuffer) {
        Withdrawal(stringBuffer);
        resetPwd();
        try {
            for (int i = 0; i < stringBuffer.length(); i++) {
                pwdTvs.get(i).setText("1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetPwd() {
        for (int i = 0; i < pwdTvs.size(); i++) {
            pwdTvs.get(i).setText("");
        }
    }

    public void Withdrawal(StringBuffer stringBuffer) {
        if (stringBuffer.length() == 6) {
            showLoading();
            String data = IMD5.md5(stringBuffer.toString());
            Map<String, String> map = new HashMap<>();
            map.put("paymentPwd", data);
            presenter.validPayPassword(AlipayCashActivity.this, map);
        }
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof String) {
            hiddenLoadingView();
            String datas = (String) data;
            WindowUtils.dismissBrightness(AlipayCashActivity.this);
            try {
                JSONObject jsonObject = new JSONObject(datas);
                int code = jsonObject.getInt("code");
                if (code == 2) {
                    String msg = jsonObject.getString("msg");
                    PopupWindow popupWindow = WindowUtils.ShowVirtual(AlipayCashActivity.this, R.layout.item_popup_pwd,
                            R.style.bottom_animation, 2);
                    TextView conten = popupWindow.getContentView().findViewById(R.id.conten);
                    conten.setText(msg);
                    popupWindow.getContentView().findViewById(R.id.p_close).setOnClickListener(this);
                    popupWindow.getContentView().findViewById(R.id.p_true).setOnClickListener(this);
                } else {
                    JSONObject jsonData = jsonObject.optJSONObject("data");
                    String resValidCode = jsonData.optString("resValidCode");
                    showLoading();
                    Map<String, String> map = new HashMap<>();
                    map.put("amount", mAmount.getText().toString());
                    map.put("type", "2");
                    map.put("resValidCode", resValidCode + "");
                    presenter.onApplyWithdraw(AlipayCashActivity.this, map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (data instanceof BaseInfor) {
            hiddenLoadingView();
            BaseInfor baseInfor = (BaseInfor) data;
            if (baseInfor.getCode() == 1) {
                startActivity(new Intent(AlipayCashActivity.this, WithdrawalTipsActivity.class));
                finish();
            }
        }
        if (data instanceof AlipayInfor) {
            hiddenLoadingView();
            AlipayInfor alipayInfor = (AlipayInfor) data;
            name = alipayInfor.getData().getAlipayTrueName();
            code = alipayInfor.getData().getAlipayAccountNum();
            mAlipName.setText(name);
            mAlipCode.setText(code);
        }
        if (data instanceof WithdrawalInfor) {
            hiddenLoadingView();
            WithdrawalInfor withdrawalInfor = (WithdrawalInfor) data;
            if (data != null) {
                if (!TextUtils.isEmpty(withdrawalInfor.getData().getBalance())) {
                    money = withdrawalInfor.getData().getBalance();
                    mPrice.setText(withdrawalInfor.getData().getBalance() + "元");
                } else {
                    mPrice.setText("0.00元");
                    money = "0.00";
                }
                if (!TextUtils.isEmpty(withdrawalInfor.getData().getWithdrawFrom())) {
                    withdrawfrom = withdrawalInfor.getData().getWithdrawFrom();
                    mAmount.setHint("最小提现金额" + withdrawalInfor.getData().getWithdrawFrom() + "元");
                } else {
                    mAmount.setHint("最小提现金额" + 0 + "元");
                    withdrawfrom = "0";
                }
            }
        }
        if (data instanceof SettingBean) {
            hiddenLoadingView();
            SettingBean settingBean = (SettingBean) data;
            isSetPayPwd = settingBean.getData().getInfo().isSetPayPwd();
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

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (mAmount.getText().toString().length() > 0) {
                double drawMoney = Double.parseDouble(mAmount.getText().toString());
                double withdrawfroms = Double.parseDouble(withdrawfrom);
                if (drawMoney >= withdrawfroms) {
                    mAlipPwd.setBackground(getResources().getDrawable(R.drawable.bg_vip_up));
                    mAlipPwd.setClickable(true);
                }

            } else {
                mAlipPwd.setBackground(getResources().getDrawable(R.drawable.updata_phone));
                mAlipPwd.setClickable(false);
            }
            if (TextUtils.isEmpty(editable.toString())) {
                mAllPrice.setVisibility(View.VISIBLE);
                mTipPrice.setVisibility(View.GONE);
                return;
            }
            double drawMoney = Double.parseDouble(editable.toString());
            double withdrawfroms = Double.parseDouble(withdrawfrom);
            if (drawMoney < withdrawfroms) {
                mAllPrice.setVisibility(View.GONE);
                mTipPrice.setVisibility(View.VISIBLE);
            } else {
                mAllPrice.setVisibility(View.VISIBLE);
                mTipPrice.setVisibility(View.GONE);
            }
        }
    };

}

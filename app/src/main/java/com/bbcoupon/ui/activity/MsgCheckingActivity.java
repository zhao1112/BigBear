package com.bbcoupon.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bbcoupon.ui.bean.BaseInfor;
import com.bbcoupon.ui.bean.RequestInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bbcoupon.util.ConstantUtil;
import com.bbcoupon.util.WindowUtils;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.ui.activity.MineProfitActivity;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.util.DeviceUtils;
import com.yunqin.bearmall.util.RSAUtil;
import com.yunqin.bearmall.widget.DelEditText2;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/5/19
 */
public class MsgCheckingActivity extends BaseActivity implements RequestContract.RequestView {

    @BindView(R.id.m_phone_new)
    TextView mMPhoneNew;
    @BindView(R.id.code_new)
    DelEditText2 mCodeNew;
    @BindView(R.id.m_next_new)
    TextView mMNextNew;
    @BindView(R.id.m_code_new)
    TextView mMCodeNew;

    private String alip_name, alip_code, alip_type, mobile;
    private RequestPresenter presenter;

    public static void openMsgCheckingActivity(Activity activity, Class cla, Bundle bundle) {
        Intent intent = new Intent(activity, cla);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_msg_checking;
    }

    @Override
    public void init() {

        try {
            mobile = BearMallAplication.getInstance().getUser().getData().getMember().getMobile();
        } catch (Exception e) {
            e.printStackTrace();
        }


        alip_type = getIntent().getStringExtra("ALIP_TYPE");

        if ("1".equals(alip_type)) {
            alip_name = getIntent().getStringExtra("ALIP_NAME");
            alip_code = getIntent().getStringExtra("ALIP_CODE");
        }

        mMPhoneNew.addTextChangedListener(textWatcher);
        mCodeNew.addTextChangedListener(textWatcher);

        String replace = mobile.substring(3, 7);
        String newStr = mobile.replace(replace, "****");
        mMPhoneNew.setText(newStr);


        mMNextNew.setClickable(false);

        presenter = new RequestPresenter();
        presenter.setRelation(this);

        if ("1".equals(alip_type)) {
            mMNextNew.setText("立即绑定");
        } else {
            mMNextNew.setText("下一步");
        }

    }

    @OnClick({R.id.toolbar_back, R.id.m_next_new, R.id.m_code_new})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.m_next_new:
                if (mobile == null || mobile.equals("")) {
                    showToast("请先输入手机号");
                    return;
                }
                if (mobile == null || !CommonUtils.isPhoneNumber(mobile)) {
                    showToast("请输入正确的手机号");
                    return;
                }
                if (mCodeNew.getText() == null || mCodeNew.getText().toString().equals("")) {
                    showToast("请先输入验证码");
                    return;
                }
                if ("2".equals(alip_type)) {
                    bundle.putString("TITLE", "修改绑定支付宝");
                    bundle.putInt("TYPE", 3);
                    bundle.putString("MOBILE", mobile);
                    bundle.putString("SMSCODE", mCodeNew.getText().toString());
                    BindingAlipayActivity.openBindingAlipayActivity(MsgCheckingActivity.this, BindingAlipayActivity.class, bundle);
                    return;
                }
                showLoading();
                presenter.onRsaPublickey(MsgCheckingActivity.this);
                break;
            case R.id.m_code_new:
                if (mobile == null || mobile.equals("")) {
                    showToast("请先输入手机号");
                    return;
                }
                if (mobile == null || !CommonUtils.isPhoneNumber(mobile)) {
                    showToast("请输入正确的手机号");
                    return;
                }
                showLoading();
                if ("1".equals(alip_type)) {
                    Map<String, String> map = new HashMap<>();
                    map.put("mobile", mobile);
                    map.put("validType", "1");
                    map.put("machine_id", DeviceUtils.getUniqueId(this));
                    presenter.onSmsVerification(MsgCheckingActivity.this, map);
                }
                if ("2".equals(alip_type)) {
                    Map<String, String> map = new HashMap<>();
                    map.put("mobile", mobile);
                    map.put("validType", "2");
                    map.put("machine_id", DeviceUtils.getUniqueId(this));
                    presenter.onSmsVerification(MsgCheckingActivity.this, map);
                }
                break;
        }
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
            if (mMPhoneNew.getText().toString().length() > 0 && mCodeNew.getText().toString().length() > 0) {
                mMNextNew.setBackground(getResources().getDrawable(R.drawable.bg_vip_up));
                mMNextNew.setClickable(true);
            } else {
                mMNextNew.setBackground(getResources().getDrawable(R.drawable.updata_phone));
                mMNextNew.setClickable(false);
            }
        }
    };

    @Override
    public void onSuccess(Object data) {
        if (data instanceof String) {
            ConstantUtil.showCountDown(mMCodeNew, CommonUtils.waittime, 1000, R.drawable.updata_phone, R.drawable.bg_vip_up);
            Toast.makeText(MsgCheckingActivity.this, "随机码发送成功", Toast.LENGTH_SHORT).show();
        }
        if (data instanceof BaseInfor) {
            BaseInfor baseInfor = (BaseInfor) data;
            if (baseInfor.getCode() == 2) {
                showToast(baseInfor.getMsg());
                return;
            }
            Toast.makeText(MsgCheckingActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            int upgradeType = BearMallAplication.getInstance().getUser().getIdentity().getUpgradeType();
            bundle.putInt("upgradeType", upgradeType);
            MineProfitActivity.openMineProfitActivitys(MsgCheckingActivity.this, MineProfitActivity.class, bundle);
            finish();
        }
        if (data instanceof RequestInfor) {
            try {
                RequestInfor requestInfor = (RequestInfor) data;
                String rsa = requestInfor.getData();
                String name_rsa = RSAUtil.encrypt(alip_name, RSAUtil.getPublicKey(rsa));
                String code_rsa = RSAUtil.encrypt(alip_code, RSAUtil.getPublicKey(rsa));
                Log.e("BindingAlipayActivity", name_rsa);
                Log.e("BindingAlipayActivity", code_rsa);
                Map<String, String> hasMap = new HashMap<>();
                hasMap.put("alipayTrueName", name_rsa);
                hasMap.put("alipayAccount", code_rsa);
                hasMap.put("mobile", mobile);
                hasMap.put("smsCode", mCodeNew.getText().toString());
                hasMap.put("type", "1");
                presenter.onBindingAlipay(MsgCheckingActivity.this, hasMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        hiddenLoadingView();
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


}

package com.bbcoupon.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.yunqin.bearmall.util.RSAUtil;
import com.yunqin.bearmall.widget.DelEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/5/18
 */
public class BindingAlipayActivity extends BaseActivity implements View.OnClickListener, RequestContract.RequestView {

    @BindView(R.id.name)
    DelEditText mName;
    @BindView(R.id.alip_code)
    DelEditText mAlipCode;
    @BindView(R.id.alip_next)
    TextView mAlipNext;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.name_t)
    TextView mNameT;
    @BindView(R.id.alip_code_t)
    TextView mAlipCodeT;

    private String title, mobile, smscode;
    private int type;
    private RequestPresenter presenter;

    public static void openBindingAlipayActivity(Activity activity, Class cla, Bundle bundle) {
        Intent intent = new Intent(activity, cla);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_binding_alipay;
    }

    @Override
    public void init() {

        title = getIntent().getStringExtra("TITLE");
        type = getIntent().getIntExtra("TYPE", 0);

        mName.addTextChangedListener(textWatcher);
        mAlipCode.addTextChangedListener(textWatcher);

        mTitle.setText(title);

        mAlipNext.setClickable(false);

        if (type == 1) {
            String name = getIntent().getStringExtra("NAME");
            String code = getIntent().getStringExtra("CODE");
            mNameT.setText(name);
            mAlipCodeT.setText(code);
            mName.setVisibility(View.GONE);
            mAlipCode.setVisibility(View.GONE);
            mNameT.setVisibility(View.VISIBLE);
            mAlipCodeT.setVisibility(View.VISIBLE);
            mAlipNext.setText("下一步");
            mAlipNext.setBackground(getResources().getDrawable(R.drawable.bg_vip_up));
            mAlipNext.setClickable(true);
        }

        if (type == 3) {//修改绑定支付宝
            mobile = getIntent().getStringExtra("MOBILE");
            smscode = getIntent().getStringExtra("SMSCODE");
            mAlipNext.setText("提交");
        }

        presenter = new RequestPresenter();
        presenter.setRelation(this);
    }


    @OnClick({R.id.toolbar_back, R.id.alip_next})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.alip_next:
                if (type == 1) {//修改绑定支付宝
                    bundle.putString("ALIP_TYPE", "2");
                    MsgCheckingActivity.openMsgCheckingActivity(BindingAlipayActivity.this, MsgCheckingActivity.class, bundle);
                    return;
                }
                if (mName.getText() == null) {
                    showToast("请先输入支付宝真实姓名");
                    return;
                }
                if (ConstantUtil.isSpecialChar(mName.getText().toString()) || !ConstantUtil.isNmae(mName.getText().toString())) {
                    showToast("请输入支付宝真实姓名");
                    return;
                }
                if (mAlipCode.getText() == null) {
                    showToast("请先输入支付宝账号");
                    return;
                }
                if (CommonUtils.isPhoneNumber(mAlipCode.getText().toString()) || CommonUtils.checkEmail(mAlipCode.getText().toString())) {
                    hiddenKeyboard();
                    if (type == 0) {//绑定支付吧
                        bundle.putString("ALIP_NAME", mName.getText().toString());
                        bundle.putString("ALIP_CODE", mAlipCode.getText().toString());
                        bundle.putString("ALIP_TYPE", "1");
                        MsgCheckingActivity.openMsgCheckingActivity(BindingAlipayActivity.this, MsgCheckingActivity.class, bundle);
                    }
                    if (type == 3) {//修改绑定支付宝
                        PopupWindow popupWindow = WindowUtils.ShowVirtual(BindingAlipayActivity.this, R.layout.item_popup_alip,
                                R.style.bottom_animation, 1);
                        popupWindow.getContentView().findViewById(R.id.alip_close).setOnClickListener(this);
                        popupWindow.getContentView().findViewById(R.id.alip_true).setOnClickListener(this);
                    }
                } else {
                    showToast("请输入正确的支付宝账号");
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
            if (mName.getText().toString().length() > 0 && mAlipCode.getText().toString().length() > 0) {
                mAlipNext.setBackground(getResources().getDrawable(R.drawable.bg_vip_up));
                mAlipNext.setClickable(true);
            } else {
                mAlipNext.setBackground(getResources().getDrawable(R.drawable.updata_phone));
                mAlipNext.setClickable(false);
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alip_close:
                WindowUtils.dismissBrightness(BindingAlipayActivity.this);
                break;
            case R.id.alip_true:
                showLoading();
                presenter.onRsaPublickey(BindingAlipayActivity.this);
                break;
        }
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof BaseInfor) {
            Toast.makeText(BindingAlipayActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
            AlipayCashActivity.openAlipayCashActivitys(BindingAlipayActivity.this, AlipayCashActivity.class);
            finish();
        }
        if (data instanceof RequestInfor) {
            try {
                RequestInfor requestInfor = (RequestInfor) data;
                String rsa = requestInfor.getData();
                String name_rsa = RSAUtil.encrypt(mName.getText().toString(), RSAUtil.getPublicKey(rsa));
                String code_rsa = RSAUtil.encrypt(mAlipCode.getText().toString(), RSAUtil.getPublicKey(rsa));
                Log.e("BindingAlipayActivity", name_rsa);
                Log.e("BindingAlipayActivity", code_rsa);
                Map<String, String> hasMap = new HashMap<>();
                hasMap.put("alipayTrueName", name_rsa);
                hasMap.put("alipayAccount", code_rsa);
                hasMap.put("mobile", mobile);
                hasMap.put("smsCode", smscode);
                hasMap.put("type", "2");
                presenter.onBindingAlipay(BindingAlipayActivity.this, hasMap);
                WindowUtils.dismissBrightness(BindingAlipayActivity.this);
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

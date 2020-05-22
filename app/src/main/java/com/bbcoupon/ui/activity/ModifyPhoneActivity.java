package com.bbcoupon.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bbcoupon.ui.bean.RequestInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bbcoupon.util.ConstantUtil;
import com.bumptech.glide.Glide;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.util.DeviceUtils;
import com.yunqin.bearmall.widget.DelEditText2;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon
 * @DATE 2020/4/21
 */
public class ModifyPhoneActivity extends BaseActivity implements RequestContract.RequestView {

    @BindView(R.id.m_phone_new)
    DelEditText2 mMPhoneNew;
    @BindView(R.id.code_new)
    DelEditText2 mCodeNew;
    @BindView(R.id.m_next_new)
    TextView mMNextNew;
    @BindView(R.id.m_code_new)
    TextView mMCodeNew;

    private RequestPresenter presenter;

    @Override
    public int layoutId() {
        return R.layout.activity_modify;
    }

    @Override
    public void init() {

        presenter = new RequestPresenter();
        presenter.setRelation(this);

        mMPhoneNew.addTextChangedListener(textWatcher);
        mCodeNew.addTextChangedListener(textWatcher);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.setUntying(this);
    }

    @OnClick({R.id.toolbar_back, R.id.m_code_new, R.id.m_next_new})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.m_code_new:
                if (mMPhoneNew.getText() == null || mMPhoneNew.getText().toString().equals("")) {
                    showToast("请先输入手机号");
                    return;
                }
                if (mMPhoneNew.getText() == null || !CommonUtils.isPhoneNumber(mMPhoneNew.getText().toString())) {
                    showToast("请输入正确的手机号");
                    return;
                }
                showLoading();
                Map<String, String> map = new HashMap<>();
                map.put("mobile", mMPhoneNew.getText().toString().trim() + "");
                map.put("validType", 2 + "");
                map.put("machine_id", DeviceUtils.getUniqueId(this));
                Log.e("onViewClicked", DeviceUtils.getUniqueId(this));
                presenter.onVerificationCode(ModifyPhoneActivity.this, map);
                break;
            case R.id.m_next_new:
                if (mMPhoneNew.getText() == null || mMPhoneNew.getText().toString().equals("")) {
                    showToast("请先输入手机号");
                    return;
                }
                if (mMPhoneNew.getText() == null || !CommonUtils.isPhoneNumber(mMPhoneNew.getText().toString())) {
                    showToast("请输入正确的手机号");
                    return;
                }
                if (mCodeNew.getText() == null || mCodeNew.getText().toString().equals("")) {
                    showToast("请先输入验证码");
                    return;
                }
                hiddenKeyboard();
                showLoading();
                Map<String, String> map2 = new HashMap<>();
                map2.put("mobile", mMPhoneNew.getText().toString().trim() + "");
                map2.put("smsVCod", mCodeNew.getText().toString().trim() + "");
                presenter.onMobilePhone(ModifyPhoneActivity.this, map2);
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
        if (data instanceof RequestInfor) {
            RequestInfor requestInfor = (RequestInfor) data;
            if (requestInfor.getCode() == 1) {
                ConstantUtil.showCountDown(mMCodeNew, CommonUtils.waittime, 1000, R.drawable.updata_phone, R.drawable.bg_vip_up);
                Toast.makeText(ModifyPhoneActivity.this, "随机码发送成功", Toast.LENGTH_SHORT).show();
            }
        }
        if (data instanceof String) {
            String datas = (String) data;
            try {
                JSONObject jsonObject = new JSONObject(datas);
                if (jsonObject.getInt("code") == 1) {
                    UserInfo userInfo = BearMallAplication.getInstance().getUser();
                    userInfo.getData().getMember().setMobile(mMPhoneNew.getText().toString());
                    BearMallAplication.getInstance().setUser(userInfo);
                    showToast("手机号修改成功", Gravity.CENTER);
                    startActivity(new Intent(ModifyPhoneActivity.this, SettingsActivity.class));
                    finish();
                }
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

}

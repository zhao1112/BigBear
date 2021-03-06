package com.bbcoupon.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bbcoupon.ui.bean.RequestInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bbcoupon.util.ConstantUtil;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
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
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/5/13
 */
public class ModifyPhoneNumberActivity extends BaseActivity implements RequestContract.RequestView {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.phone_old)
    TextView mPhoneOld;
    @BindView(R.id.m_phone)
    TextView mMPhone;
    @BindView(R.id.code_old)
    DelEditText2 mCodeOld;
    @BindView(R.id.m_code)
    TextView mMCode;
    @BindView(R.id.m_next)
    TextView mMNext;

    private RequestPresenter presenter;
    private String mobile;

    @Override
    public int layoutId() {
        return R.layout.activity_modifyphonenumber;
    }

    @Override
    public void init() {


        mTitle.setText("更换手机号");
        mPhoneOld.setText("已绑号码");
        mMPhone.addTextChangedListener(textWatcher);
        mCodeOld.addTextChangedListener(textWatcher);
        mMPhone.setVisibility(View.VISIBLE);

        mobile = BearMallAplication.getInstance().getUser().getData().getMember().getMobile();

        String replace = mobile.substring(3, 7);
        String newStr = mobile.replace(replace, "****");
        mMPhone.setText(newStr);

        presenter = new RequestPresenter();
        presenter.setRelation(this);

    }

    @OnClick({R.id.m_code, R.id.m_next, R.id.toolbar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_code:
                if (mobile == null || mobile.equals("")) {
                    showToast("请先输入手机号");
                    return;
                }
                if (mobile == null || !CommonUtils.isPhoneNumber(mobile)) {
                    showToast("请输入正确的手机号");
                    return;
                }
                showLoading();
                Map<String, String> map = new HashMap<>();
                map.put("mobile", mobile);
                map.put("validType", 1 + "");
                map.put("machine_id", DeviceUtils.getUniqueId(this));
                Log.e("onViewClicked", DeviceUtils.getUniqueId(this));
                presenter.onVerificationCode(ModifyPhoneNumberActivity.this, map);
                break;
            case R.id.m_next:
                if (mobile == null || mobile.equals("")) {
                    showToast("请先输入手机号");
                    return;
                }
                if (mobile == null || !CommonUtils.isPhoneNumber(mobile)) {
                    showToast("请输入正确的手机号");
                    return;
                }
                if (mCodeOld.getText() == null || mCodeOld.getText().toString().equals("")) {
                    showToast("请先输入验证码");
                    return;
                }
                hiddenKeyboard();
                showLoading();
                Map<String, String> map2 = new HashMap<>();
                map2.put("mobile", mobile);
                map2.put("smsVCod", mCodeOld.getText().toString().trim() + "");
                presenter.onSmsVerificationCode(ModifyPhoneNumberActivity.this, map2);
                break;
            case R.id.toolbar_back:
                finish();
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
            if (mMPhone.getText().toString().length() > 0 && mCodeOld.getText().toString().length() > 0) {
                mMNext.setBackground(getResources().getDrawable(R.drawable.bg_vip_up));
                mMNext.setClickable(true);
            } else {
                mMNext.setBackground(getResources().getDrawable(R.drawable.updata_phone));
                mMNext.setClickable(false);
            }
        }
    };

    @Override
    public void onSuccess(Object data) {
        hiddenLoadingView();
        if (data instanceof RequestInfor) {
            RequestInfor requestInfor = (RequestInfor) data;
            if (requestInfor.getCode() == 1) {
                ConstantUtil.showCountDown(mMCode, CommonUtils.waittime, 1000, R.drawable.updata_phone, R.drawable.bg_vip_up);
                Toast.makeText(ModifyPhoneNumberActivity.this, "随机码发送成功", Toast.LENGTH_SHORT).show();
            }
        }
        if (data instanceof String) {
            String datas = (String) data;
            try {
                JSONObject jsonObject = new JSONObject(datas);
                if (jsonObject.getInt("code") == 1) {
                    startActivity(new Intent(ModifyPhoneNumberActivity.this, ModifyPhoneActivity.class));
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
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

}

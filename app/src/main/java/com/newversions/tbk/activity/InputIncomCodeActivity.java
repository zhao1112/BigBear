package com.newversions.tbk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.eventbus.FinishEvent;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.PhoneLoginActivity;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.util.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InputIncomCodeActivity extends BaseActivity {
    @BindView(R.id.ed_code)
    EditText edCode;

    private String accessToken;
    private String mode;
    private String code;

    public static void startInputIncomCodeActivity(Activity activity, String accessToken, String mode) {
        Intent intent = new Intent(activity, InputIncomCodeActivity.class);
        intent.putExtra("accessToken", accessToken);
        intent.putExtra("mode", mode);
        activity.startActivity(intent);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_input_incomcode;
    }

    @Override
    public void init() {
        accessToken = getIntent().getStringExtra("accessToken");
        mode = getIntent().getStringExtra("mode");
        edCode.setSelection(edCode.getText().toString().length());
    }

    @OnClick({R.id.im_back, R.id.tv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                finish();
                break;
            case R.id.tv_ok:
                code = edCode.getText().toString().trim();
                if (StringUtils.isEmpty(code)) {
                    showToast("请填写邀请码");
                    return;
                }
                if (!(code.length() == 6 || code.length() == 11)) {
                    showToast("请填写正确的邀请码或手机号");
                    return;
                }
                // TODO: 2019/8/1 0001 提交邀请码
                showLoading();
                Map<String, String> map = new HashMap<>();
                map.put("accessToken", accessToken);
                map.put("recomment", code);
                RetrofitApi.request(this, RetrofitApi.createApi(Api.class).fillCode(map), new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        UserInfo userInfo = new Gson().fromJson(data, UserInfo.class);
                        userInfo.getData().setIsFirstLogin(1);
                        if (userInfo.getData().getIsFirstLogin() == 1) {
                            SharedPreferencesHelper.put(InputIncomCodeActivity.this, "firstLoginReward", userInfo.getData().getFirstLoginReward());
                            SharedPreferencesHelper.put(InputIncomCodeActivity.this, "isFirstBind", true);
                        } else {
                            SharedPreferencesHelper.put(InputIncomCodeActivity.this, "isFirstBind", false);
                        }
                        hiddenLoadingView();
                        BearMallAplication.getInstance().setUser(userInfo);
                        showToast("登录成功");
                        EventBus.getDefault().post(new FinishEvent());
                        BearMallAplication.getInstance().getActivityStack().finishActivity(LoginActivity.class);
                        //TODO[登录]
                        ConstantScUtil.sensorsLogin("微信");
                        //TODO[邀请码]
                        ConstantScUtil.sensorsInvitation(mode);
                        finish();
                    }

                    @Override
                    public void onNotNetWork() {
                        hiddenLoadingView();
                    }

                    //17175319997
                    @Override
                    public void onFail(Throwable e) {
                        hiddenLoadingView();
                    }
                });
                break;
        }
    }
}

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
                            SharedPreferencesHelper.put(InputIncomCodeActivity.this, "firstLoginReward",
                                    userInfo.getData().getFirstLoginReward());
                            SharedPreferencesHelper.put(InputIncomCodeActivity.this, "isFirstBind", true);
                        } else {
                            SharedPreferencesHelper.put(InputIncomCodeActivity.this, "isFirstBind", false);
                        }
//{"msg":"成功","code":1,"data":{"member":{"member_id":12251,"isBindWxopenId":true,"nickName":"大熊用户113214308","mobile":"17175319997",
// "isHasSpecInvite":false,"isBindWx":true,"isMember":false,"specInviteUsableCount":0,"bigBearNumber":"113214308","isEnabled":true,
// "isLocked":false,"iconUrl":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/20180816140417.png",
// "isOpendMember":false,"expectSaveAmount":"1000.00"},"token":"eyJhbGciOiJIUzI1NiJ9
// .eyJ1aWQiOiIxMjI1MSIsInN1YiI6IntcInVpZFwiOlwiMTIyNTFcIn0iLCJleHAiOjE1NjUyNTk5ODQsImlhdCI6MTU2NDY1NTE1NCwianRpIjoiYTc4MjczZDhlYjcwNzI2MGZiOTdiMjgxNWFjMzRkOWYifQ.4axh-oiPKN5jqH5SUTRA9wMBKMs2nQQKJ7GvZSrStAs"}}
                        hiddenLoadingView();
                        BearMallAplication.getInstance().setUser(userInfo);
                        showToast("登录成功");
                        InitInvitation();
                        EventBus.getDefault().post(new FinishEvent());
                        BearMallAplication.getInstance().getActivityStack().finishActivity(LoginActivity.class);
                        //TODO[登录]
                        sensorsLogin();
                        //TODO[邀请码]
                        sensorsInvitation();
                        finish();
                    }

                    //
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

    private void InitInvitation() {
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).createManyInviteImage(), null);
    }


    //神策邀请码统计
    public void sensorsInvitation() {
        Map<String, String> map = new HashMap<>();
        map.put("login_method", mode);
        ConstantScUtil.sensorsTrack("invitationCode", map);
    }

    //神策登录统计
    public void sensorsLogin() {
        Map<String, String> map = new HashMap<>();
        map.put("login_method", "微信");
        ConstantScUtil.sensorsTrack("wechatLoginClick", map);
    }

}

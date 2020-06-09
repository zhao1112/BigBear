package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.newversions.tbk.activity.InputIncomCodeActivity;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.eventbus.FinishEvent;
import com.yunqin.bearmall.eventbus.ProductMessageEvent;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.DeviceUtils;
import com.yunqin.bearmall.util.DialogUtils;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.util.StringUtils;
import com.yunqin.bearmall.widget.DelEditText;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

public class PhoneLoginActivity extends BaseActivity implements PlatformActionListener {

    private final int EMSGLOGIN = 1;
    private final int ACOUNTLOGIN = 2;

    @BindView(R.id.emsg_login_layout)
    RelativeLayout emsgLoginLayout;
    @BindView(R.id.acount_login_layout)
    RelativeLayout acountLoginLayout;
    @BindView(R.id.emsg_login)
    TextView emsgLogin;
    @BindView(R.id.acount_login)
    TextView acountLogin;
    @BindView(R.id.emsg_login_line)
    View emsgLoginLine;
    @BindView(R.id.acount_login_line)
    View acountLoginLine;
    @BindView(R.id.pwd_layout)
    LinearLayout pwdLayout;
    @BindView(R.id.input_pwd_layout)
    LinearLayout inputPwdLayout;
    @BindView(R.id.code_layout)
    LinearLayout codeLayout;
    @BindView(R.id.regiedt_forgetpwd_layout)
    LinearLayout bottomLayout;
    @BindView(R.id.img_code_layout)
    LinearLayout imgCodeLayout;
    @BindView(R.id.login_btn)
    HighlightButton loginBtn;
    @BindView(R.id.getcode_btn)
    Button getcodeBtn;
    @BindView(R.id.get_img_code)
    ImageView getImgCode;
    @BindView(R.id.regirst)
    TextView regirst;
    @BindView(R.id.reset_pwd)
    TextView resetPwd;
    @BindView(R.id.toolbar_title)
    TextView titleTextView;
    @BindView(R.id.phone_number)
    DelEditText phone_number;
    @BindView(R.id.psw_login)
    DelEditText psw_login;
    @BindView(R.id.msg_Code)
    DelEditText msg_Code;
    @BindView(R.id.img_code)
    DelEditText img_code;

    private int loginType;
    private UserInfo userInfo;

    public static void starActivity(Activity mContext) {
        Intent intent = new Intent(mContext, PhoneLoginActivity.class);
        mContext.startActivity(intent);
        mContext.overridePendingTransition(R.anim.activity_in, R.anim.activity_stay);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(FinishEvent finishEvent) {
        this.finish();
    }

    @Override
    public int layoutId() {
        return R.layout.activity_phone_login;
    }

    @Override
    public void init() {
        titleTextView.setText("手机快速登陆");
        loginBtn.setText("登录");
        changeLoginWayLayout(EMSGLOGIN);
        getVerificationCode();
    }

    @OnClick({R.id.emsg_login_layout, R.id.acount_login_layout, R.id.login_btn,
            R.id.getcode_btn, R.id.get_img_code, R.id.regirst, R.id.reset_pwd, R.id.toolbar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.emsg_login_layout:
                changeLoginWayLayout(EMSGLOGIN);
                break;
            case R.id.acount_login_layout:
                changeLoginWayLayout(ACOUNTLOGIN);
                break;
            case R.id.login_btn:
                login(loginType);
                break;
            case R.id.getcode_btn:
                sendMsgCode();
                break;
            case R.id.get_img_code:
                //重新设置图片验证码
                getVerificationCode();
                break;
            case R.id.regirst:
                RegiestOrForgetPwdActivity.starActivity(PhoneLoginActivity.this, RegiestOrForgetPwdActivity.FORMREGIEST);
                break;
            case R.id.reset_pwd:
                RegiestOrForgetPwdActivity.starActivity(PhoneLoginActivity.this, RegiestOrForgetPwdActivity.FORMFORGETPWD);
                //TODO[找回密码]
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    //简单逻辑实现 tablelayout效果
    private void changeLoginWayLayout(int whichWay) {
        if (whichWay == EMSGLOGIN) {
            loginType = 2;
            emsgLogin.setTextColor(getResources().getColor(R.color.main_color));
            emsgLoginLine.setVisibility(View.VISIBLE);
            acountLogin.setTextColor(getResources().getColor(R.color.text_color_1));
            acountLoginLine.setVisibility(View.INVISIBLE);
            inputPwdLayout.setVisibility(View.GONE);
            imgCodeLayout.setVisibility(View.VISIBLE);
            pwdLayout.setVisibility(View.GONE);
            codeLayout.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.GONE);
        } else {
            loginType = 1;
            emsgLogin.setTextColor(getResources().getColor(R.color.text_color_1));
            emsgLoginLine.setVisibility(View.INVISIBLE);
            acountLogin.setTextColor(getResources().getColor(R.color.main_color));
            acountLoginLine.setVisibility(View.VISIBLE);
            codeLayout.setVisibility(View.GONE);
            inputPwdLayout.setVisibility(View.GONE);
            imgCodeLayout.setVisibility(View.VISIBLE);
            pwdLayout.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.VISIBLE);
        }
    }

    private void getVerificationCode() {
        Constans.params.clear();
        Constans.params.put("machine_id", DeviceUtils.getUniqueId(this));
        Log.i("ficationCode", DeviceUtils.getUniqueId(this));
        RetrofitApi.requestImageCode(this, RetrofitApi.createApi(Api.class).getImageCode(Constans.params),
                new RetrofitApi.ImageCodeResponseListener() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        try {
                            getImgCode.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                    }
                });
    }

    /**
     * 登录类型 1：手机+密码
     * 2：手机+短信验证码
     *
     * @param type
     */
    private void login(int type) {
        Constans.params.clear();
        if (phone_number.getText() == null || phone_number.getText().toString().equals("")) {
            showToast("请先输入手机号");
            return;
        }
        if (phone_number.getText() == null || !CommonUtils.isPhoneNumber(phone_number.getText().toString())) {
            showToast("请输入正确的手机号");
            return;
        }
        if (type == 1) {
            if (img_code.getText().toString().equals("")) {
                showToast("请输入图片码");
                return;
            }
            if (psw_login.getText().toString().equals("")) {
                showToast("请输入密码");
                return;
            }
            Constans.params.put("password", psw_login.getText().toString());
            Constans.params.put("vCod", img_code.getText().toString());
        } else {
            if (msg_Code.getText().toString().equals("")) {
                showToast("请输入随机码");
                return;
            }
            Constans.params.put("smsVCod", msg_Code.getText().toString());
        }
        Constans.params.put("mobile", phone_number.getText().toString());
        Constans.params.put("type", type + "");
        Constans.params.put("cid", (String) SharedPreferencesHelper.get(BearMallAplication.getInstance().getApplicationContext(),
                "clientid", ""));
        showLoading();
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).userLogin(Constans.params), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    userInfo = new Gson().fromJson(data, UserInfo.class);
                    if (!userInfo.getData().getMember().isBindWx()) {
                        // TODO: 2019/7/31 0031 微信绑定
                        DialogUtils.showBindWXDialog(PhoneLoginActivity.this, () -> {
                            if (!isQQClientAvailable(PhoneLoginActivity.this)) {
                                showToast("未安装微信");
                            } else {
                                hiddenLoadingView();
                                Platform platform = ShareSDK.getPlatform(Wechat.NAME);
                                platform.SSOSetting(false);
                                platform.setPlatformActionListener(PhoneLoginActivity.this);
                                platform.authorize();
                            }
                        });
                    } else if (StringUtils.isEmpty(userInfo.getParentCode())) {
                        // TODO: 2019/8/1 0001 填写邀请码
                        hiddenLoadingView();
                        InputIncomCodeActivity.startInputIncomCodeActivity(PhoneLoginActivity.this,
                                userInfo.getData().getToken().getAccess_token(), "手机");
                        finish();

                    } else {
                        hiddenLoadingView();
                        if (type == 2) {
                            if (userInfo.getData().getIsFirstLogin() == 1) {
                                SharedPreferencesHelper.put(PhoneLoginActivity.this, "firstLoginReward",
                                        userInfo.getData().getFirstLoginReward());
                                SharedPreferencesHelper.put(PhoneLoginActivity.this, "isFirstBind", true);
                            } else {
                                SharedPreferencesHelper.put(PhoneLoginActivity.this, "isFirstBind", false);
                            }
                        }
                        BearMallAplication.getInstance().setUser(userInfo);
                        showToast("登录成功");
                        EventBus.getDefault().post(new FinishEvent());
                        BearMallAplication.getInstance().getActivityStack().finishActivity(LoginActivity.class);
                        //TODO[登录]
                        ConstantScUtil.sensorsLogin("手机");
                        //TODO[获取验证码]
                        ConstantScUtil.sebsorsCode();
                        finish();
                    }
                } catch (Exception e) {
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
                getVerificationCode();
            }
        });
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        hiddenLoadingView();
        userInfo.getData().getMember().setBindWx(true);
        userInfo.getData().getMember().setBindWxopenId(true);
        Map<String, String> map = new HashMap<>();
        map.put("open_id", platform.getDb().get("unionid"));
        map.put("wxopen_id", platform.getDb().get("openid"));
        map.put("accessToken", userInfo.getData().getToken().getAccess_token());
        map.put("bindType", 1 + "");
        map.put("wx_accessToken",platform.getDb().getToken());
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).memberWeixinBind(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                if (StringUtils.isEmpty(userInfo.getParentCode())) {
                    // TODO: 2019/8/1 0001 填写邀请码
                    InputIncomCodeActivity.startInputIncomCodeActivity(PhoneLoginActivity.this,
                            userInfo.getData().getToken().getAccess_token(), "手机");
                    finish();

                } else {
                    if (userInfo.getData().getIsFirstLogin() == 1) {
                        SharedPreferencesHelper.put(PhoneLoginActivity.this, "firstLoginReward", userInfo.getData().getFirstLoginReward());
                        SharedPreferencesHelper.put(PhoneLoginActivity.this, "isFirstBind", true);
                    } else {
                        SharedPreferencesHelper.put(PhoneLoginActivity.this, "isFirstBind", false);
                    }
                    BearMallAplication.getInstance().setUser(userInfo);
                    showToast("登录成功");
                    EventBus.getDefault().post(new FinishEvent());
                    BearMallAplication.getInstance().getActivityStack().finishActivity(LoginActivity.class);
                    //TODO[登录]
                    ConstantScUtil.sensorsLogin("微信");
                    //TODO[获取验证码]
                    ConstantScUtil.sebsorsCode();
                    //TODO[授权]
                    ConstantScUtil.sensorsAuthorized("true", "Success");
                    finish();
                }
            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
                e.printStackTrace();
            }
        });
        SharedPreferencesHelper.put(PhoneLoginActivity.this, "WX_NAME", platform.getDb().getUserName());
        SharedPreferencesHelper.put(PhoneLoginActivity.this, "WX_ICON", platform.getDb().getUserIcon());
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        hiddenLoadingView();
        showToast("绑定错误" + throwable.toString());
        //TODO[授权]
        ConstantScUtil.sensorsAuthorized("false", throwable.getMessage().toString());
    }

    @Override
    public void onCancel(Platform platform, int i) {
        hiddenLoadingView();
        showToast("绑定微信取消");
        //TODO[授权]
        ConstantScUtil.sensorsAuthorized("false", "绑定微信取消");
    }

    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    public void sendMsgCode() {

        if (phone_number.getText() == null || phone_number.getText().toString().equals("")) {
            showToast("请先输入手机号");
            return;
        }
        if (phone_number.getText() == null || !CommonUtils.isPhoneNumber(phone_number.getText().toString())) {
            showToast("请输入正确的手机号");
            return;
        }
        if (img_code.getText() == null || img_code.getText().toString().trim().equals("")) {
            showToast("请输入图形验证码");
            return;
        }

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("mobile", phone_number.getText().toString().trim());
        mHashMap.put("validType", 1 + "");
        mHashMap.put("machine_id", DeviceUtils.getUniqueId(this));
        mHashMap.put("vCod", img_code.getText().toString().trim() + "");

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getMsgCode(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                CommonUtils.showCountDown(getcodeBtn, CommonUtils.waittime, 1000);
                Toast.makeText(PhoneLoginActivity.this, "随机码发送成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                getVerificationCode();
            }
        });
    }

}

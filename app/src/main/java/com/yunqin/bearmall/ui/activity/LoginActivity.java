package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newversions.tbk.activity.InputIncomCodeActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.eventbus.FinishEvent;
import com.yunqin.bearmall.inter.loginWayCallBack;
import com.yunqin.bearmall.ui.activity.contract.LoginActivityContract;
import com.yunqin.bearmall.ui.activity.presenter.LoginPresenter;
import com.yunqin.bearmall.util.DialogUtils;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.util.StringUtils;
import com.yunqin.bearmall.widget.Highlight.HighlightLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends BaseActivity implements loginWayCallBack, PlatformActionListener, LoginActivityContract.UI {

    //微信登陆按钮
    @BindView(R.id.wx_login_btn)
    HighlightLinearLayout wxLoginBtn;

    //其他登陆方式
    @BindView(R.id.other_login_way)
    Button otherLoginWay;
    //用户协议
    @BindView(R.id.user_protocol)
    TextView userProtocol;

    @BindView(R.id.close)
    RelativeLayout close;

    private LoginActivityContract.presenter presenter;
    private int LoginWay;//1 QQ  2 微信
    private Platform platform;


    public static void starActivity(Activity mContext) {
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
        mContext.overridePendingTransition(R.anim.activity_in, R.anim.activity_stay);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        presenter = new LoginPresenter(this, this);
    }

    //15910008841
    @OnClick({R.id.wx_login_btn, R.id.other_login_way, R.id.user_protocol, R.id.close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wx_login_btn:
                WxLogin();
                break;
            case R.id.other_login_way:
//                DialogUtils.changeLoginWay(this, this);
                PhoneLoginActivity.starActivity(this);
                finish();
                break;
            case R.id.user_protocol:

                String guidelUrl = BuildConfig.BASE_URL + "/view/getPrivacyPolicy";
                VanguardListPageActivity.startH5Activity(this, guidelUrl, "用户协议");

                break;
            case R.id.close:
                this.finish();
                break;
        }
    }

    @Override
    public void loginWayResult(int flag) {
        // flag 1 QQ登陆   flag 2 手机号登陆
        if (flag == 2) {
            PhoneLoginActivity.starActivity(this);
        } else {
            QLogin();
//            showToast("QQ登录被点击");
        }
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


    public void QLogin() {
        showLoading();
        LoginWay = 1;
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        platform.SSOSetting(false);
        platform.setPlatformActionListener(this);
        platform.authorize();
    }

    public void WxLogin() {
        if (!isQQClientAvailable(this)) {
            showToast("未安装微信");
        } else {
            showLoading();
            LoginWay = 2;
            Platform platform = ShareSDK.getPlatform(Wechat.NAME);
            platform.SSOSetting(false);
            platform.setPlatformActionListener(this);
            platform.authorize();
        }
    }

    /**
     * 第三方登陆回掉
     *
     * @param platform
     * @param i
     * @param hashMap
     */

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        this.platform = platform;
        hiddenLoadingView();
        Constans.params.clear();
        Log.e("Wang", platform.getDb().getUserId() + "----" + platform.getDb().getUserName());
        if (LoginWay == 1) {
            Constans.params.put("open_id", platform.getDb().getUserId());
            Constans.params.put("loginType", 1 + "");
        } else if (LoginWay == 2) {
            Constans.params.put("open_id", platform.getDb().get("unionid"));
            Constans.params.put("wxopen_id", platform.getDb().get("openid"));
            Constans.params.put("loginType", 2 + "");
        }
        presenter.start(Constans.params);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        hiddenLoadingView();
        showToast("第三方登录错误" + throwable.toString());
    }

    @Override
    public void onCancel(Platform platform, int i) {
        hiddenLoadingView();
        showToast("第三方登录取消");
    }

    @Override
    public void isBindPhone(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            if (jsonObject.getJSONObject("data").optInt("status") == 2) {
                // TODO: 2018/7/19  跳转绑定手机页面
                Bundle bundle = new Bundle();
                bundle.putString("icon", platform.getDb().getUserIcon());
                bundle.putString("name", platform.getDb().getUserName());
                bundle.putString("loginType", LoginWay + "");
                if (LoginWay == 1) {
                    bundle.putString("open_id", platform.getDb().getUserId());
                } else {
                    bundle.putString("open_id", platform.getDb().get("unionid"));
                    bundle.putString("wxopen_id", platform.getDb().get("openid"));
                }
                StarActivityUtil.starActivity(this, LoginBindPhone.class, bundle);
                finish();
            } else if (jsonObject.getJSONObject("data").optInt("status") == 1) {
                UserInfo userInfo = new Gson().fromJson(data, UserInfo.class);
                if (StringUtils.isEmpty(userInfo.getParentCode())) {
                    InputIncomCodeActivity.startInputIncomCodeActivity(LoginActivity.this, userInfo.getData().getToken().getAccess_token());
                    finish();
                } else {
                    BearMallAplication.getInstance().setUser(userInfo);
                    InitInvitation();
                    finish();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onerror() {
        hiddenLoadingView();
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

    private void InitInvitation() {
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).createManyInviteImage(), null);
    }

}

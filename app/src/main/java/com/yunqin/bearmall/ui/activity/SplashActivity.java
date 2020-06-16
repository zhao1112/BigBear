package com.yunqin.bearmall.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bbcoupon.util.WindowUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.MemberBeanResponse;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.update.CheckForUpdateHelper;
import com.yunqin.bearmall.util.ConstantScUtil;

import java.util.HashMap;

import permison.PermissonUtil;
import permison.listener.PermissionListener;

/**
 * @author AYWang
 * @create 2018/7/16
 * @Describe
 */
public class SplashActivity extends BaseActivity {

    private Handler mHandler = new Handler();
    private String mBearmall_url1;
    private String mType1;
    private String mTitle1;

    @Override
    public int layoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void init() {
        immerseStatusBar();

        new CheckForUpdateHelper().checkForUpdate(this, 0);
        if (BearMallAplication.getInstance().getUser() != null) {
            RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getMemberInfo(new HashMap<>()), new RetrofitApi.IResponseListener() {
                @Override
                public void onSuccess(String data) {
                    try {
                        UserInfo userInfo = BearMallAplication.getInstance().getUser();
                        UserInfo.DataBean dataBean = userInfo.getData();
                        MemberBeanResponse response = new Gson().fromJson(data, MemberBeanResponse.class);
                        UserInfo.DataBean.MemberBean memberBean = response.getData();
                        UserInfo.Identity identity = response.getIdentity();
                        dataBean.setMember(memberBean);
                        userInfo.setData(dataBean);
                        userInfo.setIdentity(identity);
                        BearMallAplication.getInstance().setUser(userInfo);
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
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

        Uri data = getIntent().getData();
        if (data != null) {
            mBearmall_url1 = data.getQueryParameter("bearmall_url");
            mType1 = data.getQueryParameter("type");
            mTitle1 = data.getQueryParameter("title");
        }

        getInitMessages();
    }

    private void getInitMessages() {
        PermissonUtil.checkPermission(this, new PermissionListener() {
            @Override
            public void havePermission() {
                openActivity();
                ConstantScUtil.trackInstallation(SplashActivity.this);
            }

            @Override
            public void requestPermissionFail() {
                openActivity();
                ConstantScUtil.trackInstallation(SplashActivity.this);
            }
        }, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    public void SplashImage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PopupWindow popupWindow = WindowUtils.ShowSplansh(SplashActivity.this, R.layout.popup_item_splash, R.style.SplashAnim, 1);
                TextView ad_text = popupWindow.getContentView().findViewById(R.id.ad_text);
                countDown(6 * 1000, ad_text);
                ad_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openActivity();
                    }
                });
            }
        }, 500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 倒计时显示
     */
    private void countDown(int tiem, TextView view) {
        CountDownTimer timer = new CountDownTimer(tiem, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                view.setEnabled(true);
                view.setText(millisUntilFinished / 1000 + "跳过广告");
                if ((millisUntilFinished / 1000) == 1) {
                    openActivity();
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public void openActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                intent.putExtra("bearmall_url", mBearmall_url1);
                intent.putExtra("type", mType1);
                intent.putExtra("title", mTitle1);
                startActivity(intent);
                finish();
            }
        },1000);
    }


}

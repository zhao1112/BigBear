package com.yunqin.bearmall.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

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
    private String mBearmall_url;
    private String mType;
    private String mTitle;
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
                        dataBean.setMember(memberBean);
                        userInfo.setData(dataBean);
                        BearMallAplication.getInstance().setUser(userInfo);
                        //InitInvitation();
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
        }, new String[]{Manifest.permission.READ_PHONE_STATE});
    }

    public void openActivity() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                intent.putExtra("bearmall_url", mBearmall_url1);
                intent.putExtra("type", mType1);
                intent.putExtra("title", mTitle1);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}

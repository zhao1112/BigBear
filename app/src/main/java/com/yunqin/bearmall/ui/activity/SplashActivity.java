package com.yunqin.bearmall.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

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
import com.yunqin.bearmall.util.PermissionsChecker;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.util.StarActivityUtil;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import permison.PermissonUtil;
import permison.listener.PermissionListener;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * @author AYWang
 * @create 2018/7/16
 * @Describe
 */
public class SplashActivity extends BaseActivity {

    private static final String first = "INITIALIZATION";
    private Handler mHandler = new Handler();

    @Override
    public int layoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void init() {

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


//            UserInfo user = BearMallAplication.getInstance().getUser();
//
//            Map<String, String> map = new HashMap<>();
//            map.put("refresh_token", user.getData().getToken().getRefresh_token());
//
//            RetrofitApi.request(getApplicationContext(), RetrofitApi.createApi(Api.class).refreshToken(map), new RetrofitApi
//            .IResponseListener() {
//                @Override
//                public void onSuccess(String data) throws JSONException {
//                    JSONObject jsonObject = new JSONObject(data);
//                    if (jsonObject.optInt("code") == 1) {
//                        String content = jsonObject.optJSONObject("data").getJSONObject("token").toString();
//                        UserInfo.DataBean.TokenBean tokenBean = new Gson().fromJson(content, UserInfo.DataBean.TokenBean.class);
//                        user.getData().setToken(tokenBean);
//                        BearMallAplication.getInstance().setUser(user);
//                    }
//                }
//
//                @Override
//                public void onNotNetWork() {
//                }
//
//                @Override
//                public void onFail(Throwable e) {
//                }
//            });
        }
        immerseStatusBar();
        getInitMessages();
//        Timer timer = new Timer();
//        timer.schedule(new SplashTask(), 2500);
    }

    private void getInitMessages() {
        PermissonUtil.checkPermission(this, new PermissionListener() {
            @Override
            public void havePermission() {
                boolean isFirst = (boolean) SharedPreferencesHelper.get(SplashActivity.this, first, false);
                if (!isFirst) {
                    getInits();
                    Log.i("havePermission", "one1: ");
                }
                SharedPreferencesHelper.put(SplashActivity.this, first, true);
                openActivity();
                Log.i("checkPermission", "havePermission: ");
            }

            @Override
            public void requestPermissionFail() {
                boolean isFirst = (boolean) SharedPreferencesHelper.get(SplashActivity.this, first, false);
                if (!isFirst) {
                    getInits();
                    Log.i("havePermission", "one2: ");
                }
                SharedPreferencesHelper.put(SplashActivity.this, first, true);
                openActivity();
                Log.i("checkPermission", "havePermission:2 ");
            }
        }, new String[]{Manifest.permission.READ_PHONE_STATE});

    }


    private void getInits() {
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getInitMessage(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                Log.i("onFail", "onSuccess: ");
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                Log.i("onFail", "onFail: " + e.getMessage());
            }
        });
    }

    private void InitInvitation() {
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).createManyInviteImage(), null);
    }

    class SplashTask extends TimerTask {

        @Override
        public void run() {
            StarActivityUtil.starActivity(SplashActivity.this, HomeActivity.class);
            finish();
        }
    }

    public void openActivity() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                StarActivityUtil.starActivity(SplashActivity.this, HomeActivity.class);
                finish();
            }
        }, 1000);
    }
}

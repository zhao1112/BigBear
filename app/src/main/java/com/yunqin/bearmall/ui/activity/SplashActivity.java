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
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
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
import org.json.JSONObject;

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

        }
        immerseStatusBar();
        getInitMessages();
    }

    private void getInitMessages() {
        PermissonUtil.checkPermission(this, new PermissionListener() {
            @Override
            public void havePermission() {
                boolean isFirst = (boolean) SharedPreferencesHelper.get(SplashActivity.this, first, false);
                if (!isFirst) {
                    RetrofitApi.request(SplashActivity.this, RetrofitApi.createApi(Api.class).getInitMessage(), null);
                }
                SharedPreferencesHelper.put(SplashActivity.this, first, true);
                openActivity();
                trackInstallation();
            }

            @Override
            public void requestPermissionFail() {
                boolean isFirst = (boolean) SharedPreferencesHelper.get(SplashActivity.this, first, false);
                if (!isFirst) {
                    RetrofitApi.request(SplashActivity.this, RetrofitApi.createApi(Api.class).getInitMessage(), null);
                }
                SharedPreferencesHelper.put(SplashActivity.this, first, true);
                openActivity();
            }
        }, new String[]{Manifest.permission.READ_PHONE_STATE});
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

    /**
     * 神策
     * 记录激活事件
     */
    private void trackInstallation() {
        try {
            String DownloadChannel = null;
            DownloadChannel = SensorsDataUtils.getApplicationMetaData(this, "UMENG_CHANNEL");
            JSONObject properties = new JSONObject();
            properties.put("DownloadChannel", DownloadChannel);//这里的 DownloadChannel 负责记录下载商店的渠道。这里传入具体应用商店包的标记。
            Log.i("trackInstallation", DownloadChannel);
            //记录 AppInstall 激活事件
            SensorsDataAPI.sharedInstance().trackInstallation("AppInstall", properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}

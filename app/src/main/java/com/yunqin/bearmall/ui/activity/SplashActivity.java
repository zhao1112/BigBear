package com.yunqin.bearmall.ui.activity;

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
import com.yunqin.bearmall.util.StarActivityUtil;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author AYWang
 * @create 2018/7/16
 * @Describe
 */
public class SplashActivity extends BaseActivity {
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
//            RetrofitApi.request(getApplicationContext(), RetrofitApi.createApi(Api.class).refreshToken(map), new RetrofitApi.IResponseListener() {
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
        Timer timer = new Timer();
        timer.schedule(new SplashTask(), 2500);
    }

    class SplashTask extends TimerTask {

        @Override
        public void run() {
            StarActivityUtil.starActivity(SplashActivity.this, HomeActivity.class);
            finish();
        }
    }
}

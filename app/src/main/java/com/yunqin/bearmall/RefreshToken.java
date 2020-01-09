package com.yunqin.bearmall;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Create By Master
 * On 2018/12/29 11:34
 */
public class RefreshToken {

    private static int count = 0;

    public static void init(Context mContext, Observable<String> observable, final RetrofitApi.IResponseListener listener) {

        Log.e("TGG", "开始刷新Token : ----->");

        if (count++ == 3) {
            listener.onNotNetWork();
            Toast.makeText(mContext, "网络异常，请稍后重试!", Toast.LENGTH_SHORT).show();
            return;
        }

        UserInfo user = BearMallAplication.getInstance().getUser();

        Map<String, String> map = new HashMap<>();
        Log.e("TGG", "旧Token : ----->" + user.getData().getToken().getAccess_token());
        map.put("refresh_token", user.getData().getToken().getRefresh_token());

        RetrofitApi.request2(BearMallAplication.getInstance(), RetrofitApi.createApi(Api.class).refreshToken(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                JSONObject jsonObject = new JSONObject(data);
                if (jsonObject.optInt("code") == 1) {
                    String content = jsonObject.optJSONObject("data").getJSONObject("token").toString();
                    UserInfo.DataBean.TokenBean tokenBean = new Gson().fromJson(content, UserInfo.DataBean.TokenBean.class);
                    user.getData().setToken(tokenBean);
                    BearMallAplication.getInstance().setUser(user);
                    Log.e("TGG", "新Token : ----->" + tokenBean.getAccess_token());
                    RetrofitApi.request(mContext, observable, listener);
                }
            }

            @Override
            public void onNotNetWork() {
                listener.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {
                listener.onFail(e);
            }
        });
    }


}

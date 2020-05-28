package com.yunqin.bearmall.ui.fragment.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.newversions.util.SharedPreferencesManager;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.Channel;
import com.yunqin.bearmall.ui.fragment.contract.HomeContract;
import com.yunqin.bearmall.ui.fragment.model.HomeFragmentModel;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Master
 */
public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.UI view;
    private HomeContract.Model model;

    public HomePresenter(HomeContract.UI view) {
        this.view = view;
        this.model = new HomeFragmentModel();
    }

    @Override
    public void start(Context context) {
        RetrofitApi.request(context, model.getSubjectTitle(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                Channel channel = new Gson().fromJson(data, Channel.class);
                assert view != null;
                view.attachChannel(channel);
            }

            @Override
            public void onNotNetWork() {
                view.onNotNetWork();
            }

            @Override
            public void onFail(Throwable e) {
                view.attachChannel(null);
            }
        });

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void onDestroy() {
        assert view != null;
        view = null;
    }

    private boolean isStart = true;

    @Override
    public synchronized void initAdData(Context context) {
        if (isStart) {
            isStart = false;
            if (BearMallAplication.getInstance().getUser() == null) {
                // 未登录不展示
                isStart = true;
                return;
            }
            String mobile = BearMallAplication.getInstance().getUser().getData().getMember().getMobile();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String value = simpleDateFormat.format(System.currentTimeMillis());
            String param = (String) SharedPreferencesManager.getParam(context, mobile + "home", "home");
            Log.e("TAG-HOME", "value = " + value);
            Log.e("TAG-HOME", "param = " + param);
            if (value.equals(param)) {
                isStart = true;
                return;
            }
            Map<String, String> mHashMap = new HashMap<>();
            mHashMap.put("adLocation", "0");
            RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getPopupAdInfo(mHashMap), new RetrofitApi.IResponseListener() {
                @Override
                public void onSuccess(String data) throws JSONException {
                    view.resultAdData(mobile + "home", value, data);
                    isStart = true;
                }

                @Override
                public void onNotNetWork() {
                    isStart = true;
                }

                @Override
                public void onFail(Throwable e) {
                    isStart = true;
                }
            });
        }
    }
}

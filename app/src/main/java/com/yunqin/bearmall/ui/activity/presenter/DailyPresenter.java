package com.yunqin.bearmall.ui.activity.presenter;

import android.content.Context;

import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.activity.contract.DailyContract;

/**
 * @author AYWang
 * @create 2018/8/14
 * @Describe
 */
public class DailyPresenter implements DailyContract.Presenter {


    private Context mContext;
    private DailyContract.UI view;

    public DailyPresenter(Context mContext, DailyContract.UI view) {
        this.mContext = mContext;
        this.view = view;
    }

    @Override
    public void initTaskListInfo() {
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getDailyTaskAllReward(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                assert view != null;
                view.getTaskListInfo(data);
            }

            @Override
            public void onNotNetWork() {
                view.onFail();
            }

            @Override
            public void onFail(Throwable e) {
                view.onFail();
            }
        });
    }


}

package com.yunqin.bearmall.ui.activity.contract;

import android.content.Context;

import com.yunqin.bearmall.ui.fragment.contract.BaseContract;

import java.util.Map;

public interface FeedBackContract {

    interface Presenter {
        void sendFeed(Context context, Map map);
    }

    interface UI extends BaseContract.BaseView {
        void attachData(String data);
        void onFail(Throwable e);
    }
}

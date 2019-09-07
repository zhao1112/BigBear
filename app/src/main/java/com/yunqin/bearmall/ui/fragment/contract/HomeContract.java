package com.yunqin.bearmall.ui.fragment.contract;

import android.content.Context;

import com.yunqin.bearmall.bean.Channel;

import io.reactivex.Observable;

public interface HomeContract {

    interface Model {
        Observable<String> getSubjectTitle();
    }

    interface Presenter extends BaseContract.BasePresenter {
         void onDestroy();
        void initAdData(Context context);
    }

    interface UI extends BaseContract.BaseView {
        void attachChannel(Channel channel);
        void onNotNetWork();

        void resultAdData(String key, String value, String data);

    }

}

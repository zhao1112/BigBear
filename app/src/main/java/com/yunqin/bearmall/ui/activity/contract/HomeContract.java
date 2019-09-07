package com.yunqin.bearmall.ui.activity.contract;

import android.support.v4.app.Fragment;

import com.yunqin.bearmall.ui.fragment.contract.BaseContract;

import java.util.List;

public interface HomeContract{

    interface Presenter extends BaseContract.BasePresenter {
        void onDestroy();
    }

    interface UI extends BaseContract.BaseView {
        void getCartItemCount(String data);
        void getMessageItemCount(String data);
        void onFail(Throwable e);
    }
}

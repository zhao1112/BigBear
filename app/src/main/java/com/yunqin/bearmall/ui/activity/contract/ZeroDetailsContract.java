package com.yunqin.bearmall.ui.activity.contract;

import android.support.v4.app.Fragment;

import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.ui.fragment.contract.BaseContract;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public interface ZeroDetailsContract {

    interface Model{
    }

    interface Presenter extends BaseContract.BasePresenter {
        void getZeroDetailstData(Map map);
        void setFavorite(Map map);
        void join(Map map);
        void getReceiverList(Map map);
        void onDestroy();
        void getShareData(Map map);
    }

    interface UI extends BaseContract.BaseView {
        void attachTitle(List<Fragment> listFragment, List<String> listTitle);
        void getZeroDetailstData(String data);
        void setFavorite(String data);
        void joinZeroActivity(String data);
        void setReceiverList(String data);
        void attachSahreBean(ShareBean shareBean);
        void onFail(Throwable e);
    }

}

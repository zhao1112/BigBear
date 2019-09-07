package com.yunqin.bearmall.ui.activity.contract;

import android.support.v4.app.Fragment;

import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.ui.fragment.contract.BaseContract;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public interface BargainFreeDetailContract {

    interface Model{
        Observable<String> getBargainProductDetail(Map map);
        Observable<String> setBargainFavorite(Map map);
    }

    interface Presenter extends BaseContract.BasePresenter {
        void onDestroy();
    }

    interface UI extends BaseContract.BaseView {
        void attachTitle(List<Fragment> listFragment,List<String> listTitle);
        void getBargainProductDetail(String data);
        void setBargainFavorite(String data);
        void setPartBargain(String data);
        void setReceiverList(String data);
        void addBargainOrders(String data);
        void share(ShareBean shareBean);
        void onFail(Throwable e);
    }

}

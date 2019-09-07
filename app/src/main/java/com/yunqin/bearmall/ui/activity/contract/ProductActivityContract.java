package com.yunqin.bearmall.ui.activity.contract;

import android.support.v4.app.Fragment;

import com.yunqin.bearmall.adapter.MoreTypeViewAdapter;
import com.yunqin.bearmall.bean.Channel;
import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.ui.fragment.contract.BaseContract;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public interface ProductActivityContract {

    interface Model{
        Observable<String> getProductData(Map map);
        Observable<String> setFavorite(Map map);
        Observable<String> joinCart(Map map);
    }

    interface Presenter extends BaseContract.BasePresenter {
        void onDestroy();
    }

    interface UI extends BaseContract.BaseView {
        void attachTitle(List<Fragment> listFragment,List<String> listTitle);
        void getProductData(String data);
        void setFavorite(String data);
        void joinCart(String data);
        void getCartItemCount(String data);
        void share(ShareBean shareBean);
        void onFail(Throwable e);
    }

}

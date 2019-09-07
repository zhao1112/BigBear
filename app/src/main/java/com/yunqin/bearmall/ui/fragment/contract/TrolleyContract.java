package com.yunqin.bearmall.ui.fragment.contract;

import java.util.Map;

import io.reactivex.Observable;

public interface TrolleyContract {

    interface Model{
        Observable<String> getCartItemList(Map map);
        Observable<String> getCartItemCount(Map map);
        Observable<String> removeCartItemsForFavorite(Map map);
        Observable<String> removeCartItems(Map map);
        Observable<String> setItemQuantity(Map map);

    }

    interface Presenter extends BaseContract.BasePresenter {
        void onDestroy();
    }

    interface UI extends BaseContract.BaseView {
        void onFail(Throwable e);
        void showXLoading();
        void getCartItemList(String data);
        void getCartItemCount(String data);
        void removeCartItemsForFavorite(String data);
        void removeCartItems(String data);
        void setItemQuantity(String data);

        void onShowNotNetView(boolean onShow);
        void onHideLoading();


    }
}

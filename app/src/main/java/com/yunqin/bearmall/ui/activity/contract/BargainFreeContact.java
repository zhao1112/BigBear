package com.yunqin.bearmall.ui.activity.contract;

import android.support.v4.app.Fragment;

import com.yunqin.bearmall.bean.BargainProductListBean;
import com.yunqin.bearmall.bean.MemberBargainProductListBean;
import com.yunqin.bearmall.bean.ReviewListBean;
import com.yunqin.bearmall.ui.fragment.contract.BaseContract;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public interface BargainFreeContact {

//    interface Model{
//        Observable<String> getBargainProductList(Map map);
//    }

    interface Presenter {
        void refresh(Map map);
        void refreshListData(Map map);
        void getMemberBargainProductList(Map map);
        void getReceiverList(Map map);
        void partBargain(Map map);
        void getAdMobileList(Map map);
        void getAdMobileListMid(Map map);


    }

    interface UI extends BaseContract.BaseView {
        void attachhData(BargainProductListBean bargainProductListBean);
        void attachListData(BargainProductListBean bargainProductListBean);
        void saveJson(String data);
        void attachMemberthData(MemberBargainProductListBean memberBargainProductListBean);
        void setReceiverList(String data);
        void setPartBargain(String data);
        void getAdMobileList(String data);
        void getAdMobileListMid(String data);
        void onFail(Throwable e);
        String getSearchData();
    }
}

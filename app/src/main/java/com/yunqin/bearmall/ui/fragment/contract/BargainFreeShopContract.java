package com.yunqin.bearmall.ui.fragment.contract;

import com.yunqin.bearmall.bean.BargainProductListBean;
import com.yunqin.bearmall.bean.MemberBargainProductListBean;

import java.util.Map;

public interface BargainFreeShopContract {

    interface Presenter {
        void refresh(Map map);
        void getReceiverList(Map map);
        void partBargain(Map map);
        void getAdMobileList(Map map);
        void getAdMobileListMid(Map map);

    }

    interface UI extends BaseContract.BaseView {
        void attachhData(BargainProductListBean bargainProductListBean);
        void saveJson(String data);
        void setReceiverList(String data);
        void setPartBargain(String data);
        void getAdMobileList(String data);
        void getAdMobileListMid(String data);
        void onFail(Throwable e);
    }
}

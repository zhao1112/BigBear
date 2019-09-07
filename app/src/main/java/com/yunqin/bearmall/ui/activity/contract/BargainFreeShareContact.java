package com.yunqin.bearmall.ui.activity.contract;

import com.yunqin.bearmall.bean.BargainProductListBean;
import com.yunqin.bearmall.bean.BargainShareDetailBean;
import com.yunqin.bearmall.bean.MemberBargainProductListBean;
import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.ui.fragment.contract.BaseContract;

import java.util.Map;

public interface BargainFreeShareContact {

//    interface Model{
//        Observable<String> getBargainProductList(Map map);
//    }

    interface Presenter {
        void getBargainDetails(Map map);
    }

    interface UI extends BaseContract.BaseView {
        void getBargainDetails(String data);
        void setFavorite(String data);
        void setReceiverList(String data);
        void addBargainReceiver(String data);
        void share(ShareBean shareBean);
        void getShareDetails(BargainShareDetailBean shareBean);
        void shareToOthers(String data);



//        void addBargainOrders(String data);
        void onFail(Throwable e);
    }
}

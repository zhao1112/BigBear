package com.newversions.tbk.fragment;

import com.newversions.tbk.entity.TBKHomeGoodsEntity;
import com.newversions.tbk.entity.TBKHomeEntity;

/**
 * Create By Master
 * On 2019/1/3 16:56
 */
public interface NewVersionTBKHomeContract {


    interface View {//填充界面
        void showLoad();
        void hideLoad();
        void onNotNetWork();
        void onRefreshFinish();
        void onLoadMoreFinish();
        void onHasMore(boolean hasMore);
        void attachData(TBKHomeEntity TBKHomeEntity);
        void attachAddData(TBKHomeGoodsEntity TBKGoodsEntity);
//        void attachBannerData(NewHomeAd homeAd);

        //我要贷款
//        void loanData(String json);
        void loanError();

    }

    interface Presenter {//获取数据
        void init();
        void onRefresh();
        void onLoadMore();
        //贷款
//        void getLoanData();
    }

}

package com.newversions.tbk.fragment;

import com.newversions.tbk.entity.TBKHomeGoodsEntity;
import com.newversions.tbk.entity.TBKHomeEntity;
import com.yunqin.bearmall.bean.NewTBHome;

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

        void TBHome(NewTBHome newTBHome);

        void attachAddData(TBKHomeGoodsEntity TBKGoodsEntity);

        //我要贷款
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

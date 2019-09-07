package com.newversions.home.huiyuan;

import com.newversions.home.NewHomeAd;
import com.yunqin.bearmall.bean.BannerBean;

/**
 * Create By Master
 * On 2019/1/3 16:56
 */
public interface NewVersionHomeContractTrue {


    interface View {
        void showLoad();
        void hideLoad();
        void onNotNetWork();
        void onRefreshFinish();
        void onLoadMoreFinish();
        void onHasMore(boolean hasMore);
        void attachData(HuiYuanHomeBean homeBean);
        void attachAddData(HuiYuanHomeBean homeBean);
        void attachBannerData(BannerBean homeAd);
    }

    interface Presenter {
        void init();
        void onRefresh();
        void onLoadMore();
    }

}

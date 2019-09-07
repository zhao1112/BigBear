package com.newversions.hd;

import com.newversions.home.HomeBean;
import com.newversions.home.NewHomeAd;

/**
 * Create By Master
 * On 2019/1/3 16:56
 */
public interface HdContract {


    interface View {
        void showLoad();
        void hideLoad();
        void onNotNetWork();
        void onRefreshFinish();
        void onLoadMoreFinish();
        void onHasMore(boolean hasMore);
        void attachData(HomeBean homeBean);
        void attachAddData(HomeBean homeBean);
        void attachBannerData(NewHomeAd homeAd);
    }

    interface Presenter {
        void init();
        void onRefresh();
        void onLoadMore();
    }

}

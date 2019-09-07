package com.newversions.home;

/**
 * Create By Master
 * On 2019/1/3 16:56
 */
public interface NewVersionHomeContract {


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

        //我要贷款
        void loanData(String json);
        void loanError();

    }

    interface Presenter {
        void init();
        void onRefresh();
        void onLoadMore();
        //贷款
        void getLoanData();
    }

}

package com.yunqin.bearmall.ui.fragment.contract;

import java.util.Map;

/**
 * @author AYWang
 * @create 2018/8/10
 * @Describe
 */
public interface ShopActivityFragmentContract {
    interface UI{
        void  attachhData(String data, boolean isLoadMore);
        void onError();
        void onHasMore(boolean hasMore);
    }
    interface presenter{
        void start(Map map,boolean isLoadMore);
    }
}

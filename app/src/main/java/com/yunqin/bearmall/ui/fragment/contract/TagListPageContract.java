package com.yunqin.bearmall.ui.fragment.contract;

import com.yunqin.bearmall.bean.MenuGoods;

/**
 * @author AYWang
 * @create 2018/7/30
 * @Describe
 */
public interface TagListPageContract {
    interface  UI{
        void attachData(MenuGoods menuGoods,boolean isLoadMore);
        void onError();
        void onNotNetWork();
        void onHasMore(boolean hasMore);
    }

    interface  Presenter{
        void start(boolean isLoadMore,String category_id);
    }

}

package com.yunqin.bearmall.ui.fragment.contract;

import android.content.Context;
import android.support.annotation.IntDef;

import com.yunqin.bearmall.adapter.MenuGoodsAdapter;

import static com.yunqin.bearmall.ui.fragment.contract.MenuGoodsContract.Rank.DOWN;
import static com.yunqin.bearmall.ui.fragment.contract.MenuGoodsContract.Rank.NONE;
import static com.yunqin.bearmall.ui.fragment.contract.MenuGoodsContract.Rank.UP;

public interface MenuGoodsContract {

    interface Model {
        void getMenuProductList(Context context, String orderType, String value, String category_id);

        void loadMore(Context context, int page, String searchValue, String category_id, String orderType);

        void loadSales(Context context, String value, String category_id);

        void loadScore(Context context, String value, String category_id);

        // 添加搜索 和 筛选 追加方法
        void updateSearch(Context context, String searchData, String attr_);

    }

    interface Presenter {
        void priceRank(@Rank int rank);

        void salesRank();

        void scoreRank();

        void startBundle(String data);

        void loadMore(@Rank int rank);

        // 添加搜索 和 筛选 追加方法
        void updateSearch(String searchData, String attr_);

        MenuGoodsAdapter getData();


    }

    interface UI {
        void attachAdapter(MenuGoodsAdapter adapter);

        String getValueType();

        String getCategory_id();

        void finishRefresh(boolean isMore);

        void updateView(MenuGoodsAdapter menuShopsAdapter);

        void onHasMore(boolean hasMore);

        void showLoad();
        void hideLoad();


    }

    @IntDef({NONE, UP, DOWN})
    @interface Rank {
        int NONE = -1;
        int UP = 0;
        int DOWN = 1;
    }

    interface onPresenterModelListener {
        void onSuccess(String data ,boolean hasAdd);

        void onFail();
    }


}

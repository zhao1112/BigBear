package com.yunqin.bearmall.ui.fragment.contract;

import android.content.Context;

import com.yunqin.bearmall.adapter.MenuShopsAdapter;
import com.yunqin.bearmall.bean.MenuShop;

import java.util.Map;

/**
 * @author AYWang
 * @create 2018/7/13
 * @Describe
 */
public interface MenuShopFragmentContract {
    interface Model {
        void getMenuShopData(Map<String, String> map);
        void loadMore(Map<String, String> map);
        void searchData(Map<String, String> map);

    }

    interface UI {
        void attachAdapter(MenuShopsAdapter menuShopsAdapter);

        void hideLoading(boolean hasMore);

        void updateView(MenuShopsAdapter menuShopsAdapter);

        void onHasMore(boolean hasMore);


    }

    interface Presenter extends BaseContract.BasePresenter {
        void start(Context context, String searchValue, String Category_id);
        void loadMore(Context context, String searchValue, String Category_id);



        // 添加搜索 和 筛选 追加方法
        void updateSearch(String searchData);

        MenuShop getData();






    }
}

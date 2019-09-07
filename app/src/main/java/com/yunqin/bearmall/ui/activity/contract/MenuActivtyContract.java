package com.yunqin.bearmall.ui.activity.contract;

import com.yunqin.bearmall.adapter.MenuOneAdapter;
import com.yunqin.bearmall.adapter.MenuTwoAdapter;
import com.yunqin.bearmall.bean.Menu;

/**
 * @author AYWang
 * @create 2018/7/12
 * @Describe
 */
public interface MenuActivtyContract {

    interface Model {
        void getMenuData();
    }

    interface UI {
        /**
         * 左侧数据adapter
         *
         * @param menuOneAdapter
         */
        void attachOneAdapter(MenuOneAdapter menuOneAdapter, Menu menu);

        /**
         * 右侧数据adapter
         *
         * @param menuTwoAdapter
         */
        void attachTwoAdapter(MenuTwoAdapter menuTwoAdapter);


        void onNotNetWork();

        void showLoad();
        void hiddLoad();



    }

    interface Persenter {
        void star();
    }

    interface menuDataCallBack {
        void menuData(Menu menu);
        void onNotNetWork();
        void fail(Throwable throwable);
    }
}

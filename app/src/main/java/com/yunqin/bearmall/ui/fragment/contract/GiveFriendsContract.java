package com.yunqin.bearmall.ui.fragment.contract;

import com.yunqin.bearmall.bean.SweetsBt;

import java.util.Map;

/**
 * @author AYWang
 * @create 2018/7/20
 * @Describe
 */
public interface GiveFriendsContract {
    interface UI{
        void  attachhData(SweetsBt sweetsBt);
        void onError();

        void showLoad();
        void hideLoad();

        void onNotNetWork();


    }
    interface presenter{
        void refresh(Map map);
    }
}

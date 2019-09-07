package com.yunqin.bearmall.ui.activity.contract;

import java.util.Map;

/**
 * @author AYWang
 * @create 2018/8/30
 * @Describe
 */
public interface HelpFriendCutDownThePriceActivityContract {
    interface UI {
        void attachListData(String data);
        void attachHelpOtherBargainInfo(String data);
        void onError();
    }

    interface Presenter {
        void getListData(Map map);
        void getContentInfo(Map map);
    }
}

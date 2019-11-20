package com.yunqin.bearmall.ui.activity.contract;

import com.yunqin.bearmall.bean.Checkzero;


/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.activity.contract
 * @DATE 2019/11/11
 */
public interface WebContract {
    interface model {

    }

    interface UI {
        void onNotNetWork();

        void onFail(Throwable e);

        void onCheckzero();

        void onOneTipe(String tipe);

        void onClickurl(String url);

        void onCheckinvitation();
    }

    interface presenter {
        void Checkzero();

        void Clickurl(String access_token, String itemId);

        void Checkinvitation();
    }
}

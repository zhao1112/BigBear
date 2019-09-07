package com.yunqin.bearmall.ui.activity.contract;

import com.newversions.NewOrderBean;
import com.yunqin.bearmall.adapter.MoreTypeViewAdapter;

import java.util.List;

public interface ConfirmActivityContract {

    interface Model {

    }

    interface UI {
        void attachAdapter(MoreTypeViewAdapter adapter);

        void tangGuo(String tangguo);

        void yunFei(String yunfei);

        void money(String rmb);

        void totalMoney(String totalmoney);
        void youhuiMoney(String totalmoney);

        void hideLoad(boolean error, int index);

        void showLoad();


    }

    interface Presenter {
        void start(String orderType, String areaName, String bargainRecord_id, String usersTicketLog_id, List<NewOrderBean> list);
        void refresh(String orderType, String areaName, String bargainRecord_id, String usersTicketLog_id, List<NewOrderBean> list);
    }


}

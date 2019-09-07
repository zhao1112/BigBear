package com.yunqin.bearmall.ui.fragment.contract;

public interface AllFragmentContract {


    interface Model {

    }

    interface Presenter {
        /**
         * 去付款
         */
        void performBuyPay();

        /**
         * 取消订单
         */
        void cnacelOrder();

    }

    interface UI {
        void attachAdapter();

        void showLoad();

        void hideLoad();
    }


}

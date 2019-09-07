package com.yunqin.bearmall.ui.fragment.contract;

import com.yunqin.bearmall.bean.Voucher;

import java.util.Map;

/**
 * @author AYWang
 * @create 2018/7/21
 * @Describe
 */
public interface MyTransferVoucherContract {
    interface UI {
        void attach(Voucher voucher);
        void onError();
        void onNotNetWork();
    }

    interface Persenter {
        void getData(Map map);
    }
}

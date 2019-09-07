package com.yunqin.bearmall.ui.activity.contract;

import com.yunqin.bearmall.bean.Voucher;

import java.util.Map;

/**
 * @author AYWang
 * @create 2018/7/23
 * @Describe
 */
public interface VoucherDialogActivityContract {
    interface UI{
        void  attach(Voucher voucher);
        void onError();
    }
    interface Persenter{
        void getData(Map map);
    }
}

package com.yunqin.bearmall.ui.activity.contract;

import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.bean.Voucher;

import java.util.Map;

/**
 * @author AYWang
 * @create 2018/8/24
 * @Describe
 */
public interface VanguardListPageContract {
    interface UI{
        void  attach(ShareBean shareBean);
        void onError();
    }
    interface Persenter{
        void getShareData(Map map);
    }
}

package com.yunqin.bearmall.inter;

import com.yunqin.bearmall.bean.MenuShop;

public interface menuShopCallBack {
    void menuShopCallBack(MenuShop menuShop,boolean hasAdd);
    void onFail(Throwable throwable);
}

package com.yunqin.bearmall.inter;

/**
 * Created by chenchen on 2018/8/12.
 */

public interface OnPartSnatchListener {

    void onBuySuccess(int postition,int count);

    void onBuyFailer(String msg);
}

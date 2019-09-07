package com.yunqin.bearmall.eventbus;

/**
 * @author AYWang
 * @create 2018/7/18
 * @Describe
 */
public class UpdateBankListEvent {

    private boolean isSetPwd = false;

    public UpdateBankListEvent(boolean is) {
        this.isSetPwd = is;
    }


    public boolean isSetPwd() {
        return isSetPwd;
    }


}

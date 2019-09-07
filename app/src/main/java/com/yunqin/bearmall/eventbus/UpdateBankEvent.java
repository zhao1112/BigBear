package com.yunqin.bearmall.eventbus;

/**
 * @author AYWang
 * @create 2018/7/18
 * @Describe
 */
public class UpdateBankEvent {

    private boolean isSetPwd = false;

    public UpdateBankEvent(boolean is) {
        this.isSetPwd = is;
    }


    public boolean isSetPwd() {
        return isSetPwd;
    }


}

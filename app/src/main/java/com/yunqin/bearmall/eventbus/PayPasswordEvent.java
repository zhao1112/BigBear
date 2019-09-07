package com.yunqin.bearmall.eventbus;

/**
 * @author AYWang
 * @create 2018/7/18
 * @Describe
 */
public class PayPasswordEvent {

    private boolean isSetPwd = false;

    public PayPasswordEvent(boolean is) {
        this.isSetPwd = is;
    }


    public boolean isSetPwd() {
        return isSetPwd;
    }


}

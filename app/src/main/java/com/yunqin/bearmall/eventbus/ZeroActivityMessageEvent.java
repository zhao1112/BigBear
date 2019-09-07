package com.yunqin.bearmall.eventbus;

/**
 * @author AYWang
 * @create 2018/8/7
 * @Describe
 */
public class ZeroActivityMessageEvent {
    private String message;
    public  ZeroActivityMessageEvent(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.yunqin.bearmall.bean;

public class AppointNumberBean {

    /**
     * msg : 此用户已是大团长
     * code : 1
     */

    private String msg;
    private int code;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

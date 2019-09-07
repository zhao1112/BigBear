package com.yunqin.bearmall.bean;

public class BaseResponseBean {

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

    public boolean isSuccess(){

        return code==1;
    }

}

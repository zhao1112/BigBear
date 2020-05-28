package com.bbcoupon.ui.bean;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.bean
 * @DATE 2020/5/20
 */
public class BaseInfor {
    private String msg;
    private int code;
    private String data;
    private int type;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

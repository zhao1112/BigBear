package com.bbcoupon.ui.bean;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.bean
 * @DATE 2020/4/22
 */
public class RequestInfor {

    private String msg;
    private int code;
    private String value;
    private String data;
    private boolean isBindBankCard;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean getIsBindBankCard() {
        return isBindBankCard;
    }

    public void setIsBindBankCard(boolean isBindBankCard) {
        this.isBindBankCard = isBindBankCard;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

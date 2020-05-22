package com.bbcoupon.ui.bean;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.bean
 * @DATE 2020/5/20
 */
public class AlipayInfor {

    private int code;
    private String msg;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String alipayTrueName;
        private String alipayAccountNum;

        public String getAlipayTrueName() {
            return alipayTrueName;
        }

        public void setAlipayTrueName(String alipayTrueName) {
            this.alipayTrueName = alipayTrueName;
        }

        public String getAlipayAccountNum() {
            return alipayAccountNum;
        }

        public void setAlipayAccountNum(String alipayAccountNum) {
            this.alipayAccountNum = alipayAccountNum;
        }
    }
}

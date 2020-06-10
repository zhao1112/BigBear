package com.bbcoupon.ui.bean;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.bean
 * @DATE 2020/6/10
 */
public class MsgInfor {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"alipayTrueName":"小","alipayAccountNum":"150****7550"}
     */

    private String msg;
    private int code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * alipayTrueName : 小
         * alipayAccountNum : 150****7550
         */

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

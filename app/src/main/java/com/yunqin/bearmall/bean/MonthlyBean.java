package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.bean
 * @DATE 2020/4/14
 */
public class MonthlyBean {

    /**
     * msg : 请求成功
     * code : 1
     * data : [{"paymentNum":104,"tradingmoney":24.33,"time":"2020年04月","paymentMoney":3662.26},{"paymentNum":4,"tradingmoney":18,
     * "time":"2020年03月","paymentMoney":18},{"paymentNum":33,"tradingmoney":85.08,"paymentMoney":85.08,"time":"2020年02月"}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * paymentNum : 104
         * tradingmoney : 24.33
         * time : 2020年04月
         * paymentMoney : 3662.26
         */

        private int paymentNum;
        private double tradingmoney;
        private String time;
        private double paymentMoney;

        public int getPaymentNum() {
            return paymentNum;
        }

        public void setPaymentNum(int paymentNum) {
            this.paymentNum = paymentNum;
        }

        public double getTradingmoney() {
            return tradingmoney;
        }

        public void setTradingmoney(double tradingmoney) {
            this.tradingmoney = tradingmoney;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public double getPaymentMoney() {
            return paymentMoney;
        }

        public void setPaymentMoney(double paymentMoney) {
            this.paymentMoney = paymentMoney;
        }
    }
}

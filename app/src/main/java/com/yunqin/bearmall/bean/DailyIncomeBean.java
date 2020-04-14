package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.bean
 * @DATE 2020/4/14
 */
public class DailyIncomeBean {

    /**
     * msg : 请求成功
     * code : 1
     * data : [{"paymentNum":81,"tradingmoney":0.63,"time":"今天","paymentMoney":24.3},{"paymentNum":0,"tradingmoney":0,"time":"昨天",
     * "paymentMoney":0}]
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
         * paymentNum : 81
         * tradingmoney : 0.63
         * time : 今天
         * paymentMoney : 24.3
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

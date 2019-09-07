package com.yunqin.bearmall.bean;

/**
 * @author AYWang
 * @create 2018/7/18
 * @Describe
 */
public class UserBTInfo {


    /**
     * msg : 请求成功
     * code : 1
     * data : {"ticketCount":0,"bCbanlance":"36124.00","banlance":"0.00","todayCreditSum":"897.00"}
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
         * ticketCount : 0
         * bCbanlance : 36124.00
         * banlance : 0.00
         * todayCreditSum : 897.00
         */

        private int ticketCount;
        private String bCbanlance;
        private String banlance;
        private String todayCreditSum;

        private String withdrawFrom;

        public String getWithdrawFrom() {
            return withdrawFrom;
        }

        public void setWithdrawFrom(String withdrawFrom) {
            this.withdrawFrom = withdrawFrom;
        }

        public int getTicketCount() {
            return ticketCount;
        }

        public void setTicketCount(int ticketCount) {
            this.ticketCount = ticketCount;
        }

        public String getBCbanlance() {
            return bCbanlance;
        }

        public void setBCbanlance(String bCbanlance) {
            this.bCbanlance = bCbanlance;
        }

        public String getBanlance() {
            return banlance;
        }

        public void setBanlance(String banlance) {
            this.banlance = banlance;
        }

        public String getTodayCreditSum() {
            return todayCreditSum;
        }

        public void setTodayCreditSum(String todayCreditSum) {
            this.todayCreditSum = todayCreditSum;
        }
    }
}

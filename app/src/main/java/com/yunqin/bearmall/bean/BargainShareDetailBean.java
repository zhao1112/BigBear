package com.yunqin.bearmall.bean;

import java.util.List;


public class BargainShareDetailBean {

    private DataBean data;
    private int code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {


        private int bargainCount;

        private BargainedShareDetails bargainedDetails;

        public int getBargainCount() {
            return bargainCount;
        }

        public void setBargainCount(int bargainCount) {
            this.bargainCount = bargainCount;
        }

        public BargainedShareDetails getBargainedDetails() {
            return bargainedDetails;
        }

        public void setBargainedDetails(BargainedShareDetails bargainedDetails) {
            this.bargainedDetails = bargainedDetails;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "bargainCount=" + bargainCount +
                    ", bargainedDetails=" + bargainedDetails +
                    '}';
        }
    }

    public static class BargainedShareDetails{
        private String bargainedRatio;
        private String bargainedAmount;
        private String bargainMoreAmount;
        private int shareWxCount;
        private int shareWxNeedCount;
        private String criticalRatio;

        public String getBargainedRatio() {
            return bargainedRatio;
        }

        public void setBargainedRatio(String bargainedRatio) {
            this.bargainedRatio = bargainedRatio;
        }

        public String getBargainedAmount() {
            return bargainedAmount;
        }

        public void setBargainedAmount(String bargainedAmount) {
            this.bargainedAmount = bargainedAmount;
        }

        public String getBargainMoreAmount() {
            return bargainMoreAmount;
        }

        public void setBargainMoreAmount(String bargainMoreAmount) {
            this.bargainMoreAmount = bargainMoreAmount;
        }

        public int getShareWxCount() {
            return shareWxCount;
        }

        public void setShareWxCount(int shareWxCount) {
            this.shareWxCount = shareWxCount;
        }

        public int getShareWxNeedCount() {
            return shareWxNeedCount;
        }

        public void setShareWxNeedCount(int shareWxNeedCount) {
            this.shareWxNeedCount = shareWxNeedCount;
        }

        public String getCriticalRatio() {
            return criticalRatio;
        }

        public void setCriticalRatio(String criticalRatio) {
            this.criticalRatio = criticalRatio;
        }

        @Override
        public String toString() {
            return "BargainedShareDetails{" +
                    "bargainedRatio='" + bargainedRatio + '\'' +
                    ", bargainedAmount='" + bargainedAmount + '\'' +
                    ", bargainMoreAmount='" + bargainMoreAmount + '\'' +
                    ", shareWxCount=" + shareWxCount +
                    ", shareWxNeedCount=" + shareWxNeedCount +
                    ", criticalRatio='" + criticalRatio + '\'' +
                    '}';
        }
    }
}

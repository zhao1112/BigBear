package com.newreward.bean;

/**
 * @author AYWang
 * @create 2019/1/22
 * @Describe
 */
public class SpecialRequest {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"specInvitationPageInfo":{"activityStartDate":"2019/01/15","specInviteUsableCount":20,"showPrice":10,"activityEndDate":"2019/02/22","commission":1.1,"rewardPhoneFee":"60","realPrice":1.21}}
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
         * specInvitationPageInfo : {"activityStartDate":"2019/01/15","specInviteUsableCount":20,"showPrice":10,"activityEndDate":"2019/02/22","commission":1.1,"rewardPhoneFee":"60","realPrice":1.21}
         */

        private SpecInvitationPageInfoBean specInvitationPageInfo;

        public SpecInvitationPageInfoBean getSpecInvitationPageInfo() {
            return specInvitationPageInfo;
        }

        public void setSpecInvitationPageInfo(SpecInvitationPageInfoBean specInvitationPageInfo) {
            this.specInvitationPageInfo = specInvitationPageInfo;
        }

        public static class SpecInvitationPageInfoBean {
            /**
             * activityStartDate : 2019/01/15
             * specInviteUsableCount : 20
             * showPrice : 10.0
             * activityEndDate : 2019/02/22
             * commission : 1.1
             * rewardPhoneFee : 60
             * realPrice : 1.21
             */

            private String activityStartDate;
            private int specInviteUsableCount;
            private double showPrice;
            private String activityEndDate;
            private double commission;
            private String rewardPhoneFee;
            private double realPrice;

            public String getActivityStartDate() {
                return activityStartDate;
            }

            public void setActivityStartDate(String activityStartDate) {
                this.activityStartDate = activityStartDate;
            }

            public int getSpecInviteUsableCount() {
                return specInviteUsableCount;
            }

            public void setSpecInviteUsableCount(int specInviteUsableCount) {
                this.specInviteUsableCount = specInviteUsableCount;
            }

            public double getShowPrice() {
                return showPrice;
            }

            public void setShowPrice(double showPrice) {
                this.showPrice = showPrice;
            }

            public String getActivityEndDate() {
                return activityEndDate;
            }

            public void setActivityEndDate(String activityEndDate) {
                this.activityEndDate = activityEndDate;
            }

            public double getCommission() {
                return commission;
            }

            public void setCommission(double commission) {
                this.commission = commission;
            }

            public String getRewardPhoneFee() {
                return rewardPhoneFee;
            }

            public void setRewardPhoneFee(String rewardPhoneFee) {
                this.rewardPhoneFee = rewardPhoneFee;
            }

            public double getRealPrice() {
                return realPrice;
            }

            public void setRealPrice(double realPrice) {
                this.realPrice = realPrice;
            }
        }
    }
}

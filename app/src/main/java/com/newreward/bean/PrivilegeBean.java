package com.newreward.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2019/1/24
 * @Describe
 */
public class PrivilegeBean {

    /**
     * msg : 请求成功
     * code : 1
     * data : [{"groupRecord":{"expireDays":"27","restCount":5,"name":"糖果0元兑","type":2}},{"phoneTicketRecord":{"expireDays":"30","name":"现金优惠券","restCount":1,"type":0}},{"cashTicketRecord":{"expireDays":"","name":"话费充值抵扣券","restCount":0,"type":1}}]
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
         * groupRecord : {"expireDays":"27","restCount":5,"name":"糖果0元兑","type":2}
         * phoneTicketRecord : {"expireDays":"30","name":"现金优惠券","restCount":1,"type":0}
         * cashTicketRecord : {"expireDays":"","name":"话费充值抵扣券","restCount":0,"type":1}
         */

        private GroupRecordBean groupRecord;
        private PhoneTicketRecordBean phoneTicketRecord;
        private CashTicketRecordBean cashTicketRecord;

        public GroupRecordBean getGroupRecord() {
            return groupRecord;
        }

        public void setGroupRecord(GroupRecordBean groupRecord) {
            this.groupRecord = groupRecord;
        }

        public PhoneTicketRecordBean getPhoneTicketRecord() {
            return phoneTicketRecord;
        }

        public void setPhoneTicketRecord(PhoneTicketRecordBean phoneTicketRecord) {
            this.phoneTicketRecord = phoneTicketRecord;
        }

        public CashTicketRecordBean getCashTicketRecord() {
            return cashTicketRecord;
        }

        public void setCashTicketRecord(CashTicketRecordBean cashTicketRecord) {
            this.cashTicketRecord = cashTicketRecord;
        }

        public static class GroupRecordBean {
            /**
             * expireDays : 27
             * restCount : 5
             * name : 糖果0元兑
             * type : 2
             */

            private String expireDays;
            private int restCount;
            private String name;
            private int type;

            public String getExpireDays() {
                return expireDays;
            }

            public void setExpireDays(String expireDays) {
                this.expireDays = expireDays;
            }

            public int getRestCount() {
                return restCount;
            }

            public void setRestCount(int restCount) {
                this.restCount = restCount;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class PhoneTicketRecordBean {
            /**
             * expireDays : 30
             * name : 现金优惠券
             * restCount : 1
             * type : 0
             */

            private String expireDays;
            private String name;
            private int restCount;
            private int type;

            public String getExpireDays() {
                return expireDays;
            }

            public void setExpireDays(String expireDays) {
                this.expireDays = expireDays;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getRestCount() {
                return restCount;
            }

            public void setRestCount(int restCount) {
                this.restCount = restCount;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class CashTicketRecordBean {
            /**
             * expireDays :
             * name : 话费充值抵扣券
             * restCount : 0
             * type : 1
             */

            private String expireDays;
            private String name;
            private int restCount;
            private int type;

            public String getExpireDays() {
                return expireDays;
            }

            public void setExpireDays(String expireDays) {
                this.expireDays = expireDays;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getRestCount() {
                return restCount;
            }

            public void setRestCount(int restCount) {
                this.restCount = restCount;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}

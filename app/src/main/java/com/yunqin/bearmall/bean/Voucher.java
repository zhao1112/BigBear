package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/7/21
 * @Describe
 */
public class Voucher {


    /**
     * data : {"usersTicketList":[{"amount":500,"usersTicket_id":18,"limitDate":"2018-07-30 15:52:58","count":1,"status":0,"type":0,"createdDate":"2018-07-21 15:52:58","expireDays":1}],"has_more":0}
     * code : 1
     * msg : 请求成功
     */

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
        /**
         * usersTicketList : [{"amount":500,"usersTicket_id":18,"limitDate":"2018-07-30 15:52:58","count":1,"status":0,"type":0,"createdDate":"2018-07-21 15:52:58","expireDays":1}]
         * has_more : 0
         */

        private int has_more;
        private List<UsersTicketListBean> usersTicketList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<UsersTicketListBean> getUsersTicketList() {
            return usersTicketList;
        }

        public void setUsersTicketList(List<UsersTicketListBean> usersTicketList) {
            this.usersTicketList = usersTicketList;
        }

        public static class UsersTicketListBean {
            /**
             * amount : 500
             * usersTicket_id : 18
             * limitDate : 2018-07-30 15:52:58
             * count : 1
             * status : 0
             * type : 0
             * createdDate : 2018-07-21 15:52:58
             * expireDays : 1
             */

            private int amount;
            private int usersTicket_id;
            private String limitDate;
            private int count;
            private int status;
            private int type;
            private String createdDate;
            private int expireDays;

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public int getUsersTicket_id() {
                return usersTicket_id;
            }

            public void setUsersTicket_id(int usersTicket_id) {
                this.usersTicket_id = usersTicket_id;
            }

            public String getLimitDate() {
                return limitDate;
            }

            public void setLimitDate(String limitDate) {
                this.limitDate = limitDate;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public int getExpireDays() {
                return expireDays;
            }

            public void setExpireDays(int expireDays) {
                this.expireDays = expireDays;
            }
        }
    }
}

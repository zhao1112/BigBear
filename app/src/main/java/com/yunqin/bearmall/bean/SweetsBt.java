package com.yunqin.bearmall.bean;

import java.util.List;

public class SweetsBt {


    /**
     * data : {"has_more":0,"presentList":[{"bigBearNumber":"253954530","username":"13222222222","nickName":"大熊会员253954530","iconUrl":"https://img5.duitang.com/uploads/item/201508/09/20150809005334_rxVJH.jpeg","dtype":"Member","credit":"200.00","createdDate":"2018-07-12 14:41:02"}]}
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
         * has_more : 0
         * presentList : [{"bigBearNumber":"253954530","username":"13222222222","nickName":"大熊会员253954530","iconUrl":"https://img5.duitang.com/uploads/item/201508/09/20150809005334_rxVJH.jpeg","dtype":"Member","credit":"200.00","createdDate":"2018-07-12 14:41:02"}]
         */

        private int has_more;
        private List<PresentListBean> presentList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<PresentListBean> getPresentList() {
            return presentList;
        }

        public void setPresentList(List<PresentListBean> presentList) {
            this.presentList = presentList;
        }

        public static class PresentListBean {
            /**
             * bigBearNumber : 253954530
             * username : 13222222222
             * nickName : 大熊会员253954530
             * iconUrl : https://img5.duitang.com/uploads/item/201508/09/20150809005334_rxVJH.jpeg
             * dtype : Member
             * credit : 200.00
             * createdDate : 2018-07-12 14:41:02
             * credit
             */

            private String bigBearNumber;
            private String username;
            private String nickName;
            private String iconUrl;
            private String dtype;
            private String credit;
            private String createdDate;
            private String debit;

            public String getDebit() {
                return debit;
            }

            public void setDebit(String debit) {
                this.debit = debit;
            }

            public String getBigBearNumber() {
                return bigBearNumber;
            }

            public void setBigBearNumber(String bigBearNumber) {
                this.bigBearNumber = bigBearNumber;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getDtype() {
                return dtype;
            }

            public void setDtype(String dtype) {
                this.dtype = dtype;
            }

            public String getCredit() {
                return credit;
            }

            public void setCredit(String credit) {
                this.credit = credit;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }
        }
    }
}

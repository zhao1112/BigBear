package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/7/20
 * @Describe
 */
public class BTGive {

    /**
     * data : [{"bigBearNumber":"253954507","nickName":"大熊会员253954507","iconUrl":"https://img5.duitang.com/uploads/item/201508/09/20150809005334_rxVJH.jpeg","member_id":9952}]
     * code : 1
     * msg : 请求成功
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * bigBearNumber : 253954507
         * nickName : 大熊会员253954507
         * iconUrl : https://img5.duitang.com/uploads/item/201508/09/20150809005334_rxVJH.jpeg
         * member_id : 9952
         */

        private String bigBearNumber;
        private String nickName;
        private String iconUrl;
        private int member_id;

        public String getBigBearNumber() {
            return bigBearNumber;
        }

        public void setBigBearNumber(String bigBearNumber) {
            this.bigBearNumber = bigBearNumber;
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

        public int getMember_id() {
            return member_id;
        }

        public void setMember_id(int member_id) {
            this.member_id = member_id;
        }
    }
}

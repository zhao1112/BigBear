package com.yunqin.bearmall.bean;

import java.io.Serializable;
import java.util.List;

public class AddressListBean {


    /**
     * data : [{"area_id":23,"phone":"666","areaName":"天津市 河北区","isDefault":false,"address":"12345","consignee":"哈哈哈","receiver_id":10007},{"area_id":22,"phone":"666","areaName":"天津市 南开区","isDefault":false,"address":"12345","consignee":"哈哈哈","receiver_id":10006},{"area_id":20,"phone":"666","areaName":"天津市 河东区","isDefault":false,"address":"12345","consignee":"哈哈哈","receiver_id":10005},{"area_id":4,"phone":"666","areaName":"北京市 朝阳区","isDefault":false,"address":"12345","consignee":"哈哈哈","receiver_id":10004}]
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

    public static class DataBean implements Serializable{
        /**
         * area_id : 23
         * phone : 666
         * areaName : 天津市 河北区
         * isDefault : false
         * address : 12345
         * consignee : 张三
         * receiver_id : 10007
         */

        private int area_id;
        private String phone;
        private String areaName;
        private boolean isDefault;
        private String address;
        private String consignee;
        private int receiver_id;

        public int getArea_id() {
            return area_id;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public boolean isIsDefault() {
            return isDefault;
        }

        public void setIsDefault(boolean isDefault) {
            this.isDefault = isDefault;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public int getReceiver_id() {
            return receiver_id;
        }

        public void setReceiver_id(int receiver_id) {
            this.receiver_id = receiver_id;
        }
    }
}

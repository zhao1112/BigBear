package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.bean
 * @DATE 2019/11/27
 */
public class PddBean {


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
         * image : http://t00img.yangkeduo.com/goods/images/2019-12-06/2b5c76b347050a14b539233d5029357d.jpeg
         * effectEstimate : 0.0
         * itemName : 【现摘现发】秭归脐橙琳娜新鲜橙子水果应季孕妇水果3/5/9
         * orderNo : 200312-402989056833977
         * payAmount : 10.9
         * createTime : 2020-03-12 18:27:14
         * orderStatus : 4
         */

        private String image;
        private double effectEstimate;
        private String itemName;
        private String orderNo;
        private String payAmount;
        private String createTime;
        private int orderStatus;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public double getEffectEstimate() {
            return effectEstimate;
        }

        public void setEffectEstimate(double effectEstimate) {
            this.effectEstimate = effectEstimate;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(String payAmount) {
            this.payAmount = payAmount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }
    }
}

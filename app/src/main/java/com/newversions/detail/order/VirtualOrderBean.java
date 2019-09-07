package com.newversions.detail.order;

/**
 * Create By Master
 * On 2019/1/23 20:15
 */
public class VirtualOrderBean {


    /**
     * msg : 请求成功
     * code : 1
     * data : {"virtualOrderDetail":{"amount":"19.96","img":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/independent/china_mobile_icon.png","orders_id":25,"quantity":1,"orderSn":"201901231573984","mobile":"15810790252","usersTicketDiscount":"0.00","carrierType":0,"isAllowAfterSales":0,"type":0,"title":"20元","expireTime":"2019-01-24 18:29:46","createdDate":"2019-01-23 18:29:46","price":"19.96","virtualRechargeProudct_id":1,"outTradeNo":"548239385955837","rewardPoint":0,"isExpire":0,"orderProductType":1,"orderRestTime":"22小时28分钟","refundAmount":0,"status":0},"orderProductType":1}
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
         * virtualOrderDetail : {"amount":"19.96","img":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/independent/china_mobile_icon.png","orders_id":25,"quantity":1,"orderSn":"201901231573984","mobile":"15810790252","usersTicketDiscount":"0.00","carrierType":0,"isAllowAfterSales":0,"type":0,"title":"20元","expireTime":"2019-01-24 18:29:46","createdDate":"2019-01-23 18:29:46","price":"19.96","virtualRechargeProudct_id":1,"outTradeNo":"548239385955837","rewardPoint":0,"isExpire":0,"orderProductType":1,"orderRestTime":"22小时28分钟","refundAmount":0,"status":0}
         * orderProductType : 1
         */

        private VirtualOrderDetailBean virtualOrderDetail;
        private int orderProductType;

        public VirtualOrderDetailBean getVirtualOrderDetail() {
            return virtualOrderDetail;
        }

        public void setVirtualOrderDetail(VirtualOrderDetailBean virtualOrderDetail) {
            this.virtualOrderDetail = virtualOrderDetail;
        }

        public int getOrderProductType() {
            return orderProductType;
        }

        public void setOrderProductType(int orderProductType) {
            this.orderProductType = orderProductType;
        }

        public static class VirtualOrderDetailBean {
            /**
             * amount : 19.96
             * img : https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/independent/china_mobile_icon.png
             * orders_id : 25
             * quantity : 1
             * orderSn : 201901231573984
             * mobile : 15810790252
             * usersTicketDiscount : 0.00
             * carrierType : 0
             * isAllowAfterSales : 0
             * type : 0
             * title : 20元
             * expireTime : 2019-01-24 18:29:46
             * createdDate : 2019-01-23 18:29:46
             * price : 19.96
             * virtualRechargeProudct_id : 1
             * outTradeNo : 548239385955837
             * rewardPoint : 0
             * isExpire : 0
             * orderProductType : 1
             * orderRestTime : 22小时28分钟
             * refundAmount : 0.0
             * status : 0
             */

            private int isNeedPay;

            private String amount;
            private String img;
            private int orders_id;
            private int quantity;
            private String orderSn;
            private String mobile;
            private String usersTicketDiscount;
            private int carrierType;
            private int isAllowAfterSales;
            private int type;
            private String title;
            private String expireTime;
            private String createdDate;
            private String price;
            private int virtualRechargeProudct_id;
            private String outTradeNo;
            private int rewardPoint;
            private int isExpire;
            private int orderProductType;
            private String orderRestTime;
            private double refundAmount;
            private int status;


            public int getIsNeedPay() {
                return isNeedPay;
            }

            public void setIsNeedPay(int isNeedPay) {
                this.isNeedPay = isNeedPay;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getOrders_id() {
                return orders_id;
            }

            public void setOrders_id(int orders_id) {
                this.orders_id = orders_id;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getOrderSn() {
                return orderSn;
            }

            public void setOrderSn(String orderSn) {
                this.orderSn = orderSn;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getUsersTicketDiscount() {
                return usersTicketDiscount;
            }

            public void setUsersTicketDiscount(String usersTicketDiscount) {
                this.usersTicketDiscount = usersTicketDiscount;
            }

            public int getCarrierType() {
                return carrierType;
            }

            public void setCarrierType(int carrierType) {
                this.carrierType = carrierType;
            }

            public int getIsAllowAfterSales() {
                return isAllowAfterSales;
            }

            public void setIsAllowAfterSales(int isAllowAfterSales) {
                this.isAllowAfterSales = isAllowAfterSales;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getExpireTime() {
                return expireTime;
            }

            public void setExpireTime(String expireTime) {
                this.expireTime = expireTime;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getVirtualRechargeProudct_id() {
                return virtualRechargeProudct_id;
            }

            public void setVirtualRechargeProudct_id(int virtualRechargeProudct_id) {
                this.virtualRechargeProudct_id = virtualRechargeProudct_id;
            }

            public String getOutTradeNo() {
                return outTradeNo;
            }

            public void setOutTradeNo(String outTradeNo) {
                this.outTradeNo = outTradeNo;
            }

            public int getRewardPoint() {
                return rewardPoint;
            }

            public void setRewardPoint(int rewardPoint) {
                this.rewardPoint = rewardPoint;
            }

            public int getIsExpire() {
                return isExpire;
            }

            public void setIsExpire(int isExpire) {
                this.isExpire = isExpire;
            }

            public int getOrderProductType() {
                return orderProductType;
            }

            public void setOrderProductType(int orderProductType) {
                this.orderProductType = orderProductType;
            }

            public String getOrderRestTime() {
                return orderRestTime;
            }

            public void setOrderRestTime(String orderRestTime) {
                this.orderRestTime = orderRestTime;
            }

            public double getRefundAmount() {
                return refundAmount;
            }

            public void setRefundAmount(double refundAmount) {
                this.refundAmount = refundAmount;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}

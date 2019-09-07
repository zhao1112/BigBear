package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author Master
 * @create 2018/7/30 12:44
 */
public class RefundDetailsBean {

    /**
     * data : {"afterSalesDetails":{"refundDate":null,"returnedQuantity":0,"sn":"201807271012121","refundAmount":"0.00","reason":"买错了","status":1,"afterSalesItem":[{"paymentModel":0,"thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg","price":"9600.00","product_id":29,"partBtAmount":"0.00","partPrice":"0.00","quantity":1,"specifications":["黑色","256GB"],"orderitemName":"苹果(Apple) iPhone X","orders_id":10551},{"paymentModel":0,"thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg","price":"8300.00","product_id":29,"partBtAmount":"0.00","partPrice":"0.00","quantity":1,"specifications":["银色","64GB"],"orderitemName":"苹果(Apple) iPhone X","orders_id":10551}],"lastModifiedDate":"2018-07-27 16:18:12","payType":null,"method":null,"logDetails":[{"type":0,"createdDate":"2018-07-27 19:24:53"}],"applyDate":"2018-07-27 16:10:15"}}
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
         * afterSalesDetails : {"refundDate":null,"returnedQuantity":0,"sn":"201807271012121","refundAmount":"0.00","reason":"买错了","status":1,"afterSalesItem":[{"paymentModel":0,"thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg","price":"9600.00","product_id":29,"partBtAmount":"0.00","partPrice":"0.00","quantity":1,"specifications":["黑色","256GB"],"orderitemName":"苹果(Apple) iPhone X","orders_id":10551},{"paymentModel":0,"thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg","price":"8300.00","product_id":29,"partBtAmount":"0.00","partPrice":"0.00","quantity":1,"specifications":["银色","64GB"],"orderitemName":"苹果(Apple) iPhone X","orders_id":10551}],"lastModifiedDate":"2018-07-27 16:18:12","payType":null,"method":null,"logDetails":[{"type":0,"createdDate":"2018-07-27 19:24:53"}],"applyDate":"2018-07-27 16:10:15"}
         */

        private AfterSalesDetailsBean afterSalesDetails;

        public AfterSalesDetailsBean getAfterSalesDetails() {
            return afterSalesDetails;
        }

        public void setAfterSalesDetails(AfterSalesDetailsBean afterSalesDetails) {
            this.afterSalesDetails = afterSalesDetails;
        }

        public static class AfterSalesDetailsBean {
            /**
             * refundDate : null
             * returnedQuantity : 0
             * sn : 201807271012121
             * refundAmount : 0.00
             * reason : 买错了
             * status : 1
             * afterSalesItem : [{"paymentModel":0,"thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg","price":"9600.00","product_id":29,"partBtAmount":"0.00","partPrice":"0.00","quantity":1,"specifications":["黑色","256GB"],"orderitemName":"苹果(Apple) iPhone X","orders_id":10551},{"paymentModel":0,"thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg","price":"8300.00","product_id":29,"partBtAmount":"0.00","partPrice":"0.00","quantity":1,"specifications":["银色","64GB"],"orderitemName":"苹果(Apple) iPhone X","orders_id":10551}]
             * lastModifiedDate : 2018-07-27 16:18:12
             * payType : null
             * method : null
             * logDetails : [{"type":0,"createdDate":"2018-07-27 19:24:53"}]
             * applyDate : 2018-07-27 16:10:15
             */

            private Object refundDate;
            private int returnedQuantity;
            private String sn;
            private String servicePhone;
            private String refundAmount;
            private String refundableAmount;
            private String reason;
            private int status;
            private String lastModifiedDate;
            private int payType;
            private Object method;
            private String applyDate;
            private List<AfterSalesItemBean> afterSalesItem;
            private List<LogDetailsBean> logDetails;

            public String getRefundableAmount() {
                return refundableAmount;
            }

            public void setRefundableAmount(String refundableAmount) {
                this.refundableAmount = refundableAmount;
            }

            public String getServicePhone() {
                return servicePhone;
            }

            public void setServicePhone(String servicePhone) {
                this.servicePhone = servicePhone;
            }

            public Object getRefundDate() {
                return refundDate;
            }

            public void setRefundDate(Object refundDate) {
                this.refundDate = refundDate;
            }

            public int getReturnedQuantity() {
                return returnedQuantity;
            }

            public void setReturnedQuantity(int returnedQuantity) {
                this.returnedQuantity = returnedQuantity;
            }

            public String getSn() {
                return sn;
            }

            public void setSn(String sn) {
                this.sn = sn;
            }

            public String getRefundAmount() {
                return refundAmount;
            }

            public void setRefundAmount(String refundAmount) {
                this.refundAmount = refundAmount;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getLastModifiedDate() {
                return lastModifiedDate;
            }

            public void setLastModifiedDate(String lastModifiedDate) {
                this.lastModifiedDate = lastModifiedDate;
            }

            public int getPayType() {
                return payType;
            }

            public void setPayType(int payType) {
                this.payType = payType;
            }

            public Object getMethod() {
                return method;
            }

            public void setMethod(Object method) {
                this.method = method;
            }

            public String getApplyDate() {
                return applyDate;
            }

            public void setApplyDate(String applyDate) {
                this.applyDate = applyDate;
            }

            public List<AfterSalesItemBean> getAfterSalesItem() {
                return afterSalesItem;
            }

            public void setAfterSalesItem(List<AfterSalesItemBean> afterSalesItem) {
                this.afterSalesItem = afterSalesItem;
            }

            public List<LogDetailsBean> getLogDetails() {
                return logDetails;
            }

            public void setLogDetails(List<LogDetailsBean> logDetails) {
                this.logDetails = logDetails;
            }

            public static class AfterSalesItemBean {
                /**
                 * paymentModel : 0
                 * thumbnail : http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg
                 * price : 9600.00
                 * product_id : 29
                 * partBtAmount : 0.00
                 * partPrice : 0.00
                 * quantity : 1
                 * specifications : ["黑色","256GB"]
                 * orderitemName : 苹果(Apple) iPhone X
                 * orders_id : 10551
                 */

                private int paymentModel;
                private String thumbnail;
                private String price;
                private int product_id;
                private String partBtAmount;
                private String partPrice;
                private int quantity;
                private String orderitemName;
                private int orders_id;

                private String btAmount;


                public String getBtAmount() {
                    return btAmount;
                }

                public void setBtAmount(String btAmount) {
                    this.btAmount = btAmount;
                }

                private List<String> specifications;

                public int getPaymentModel() {
                    return paymentModel;
                }

                public void setPaymentModel(int paymentModel) {
                    this.paymentModel = paymentModel;
                }

                public String getThumbnail() {
                    return thumbnail;
                }

                public void setThumbnail(String thumbnail) {
                    this.thumbnail = thumbnail;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public int getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(int product_id) {
                    this.product_id = product_id;
                }

                public String getPartBtAmount() {
                    return partBtAmount;
                }

                public void setPartBtAmount(String partBtAmount) {
                    this.partBtAmount = partBtAmount;
                }

                public String getPartPrice() {
                    return partPrice;
                }

                public void setPartPrice(String partPrice) {
                    this.partPrice = partPrice;
                }

                public int getQuantity() {
                    return quantity;
                }

                public void setQuantity(int quantity) {
                    this.quantity = quantity;
                }

                public String getOrderitemName() {
                    return orderitemName;
                }

                public void setOrderitemName(String orderitemName) {
                    this.orderitemName = orderitemName;
                }

                public int getOrders_id() {
                    return orders_id;
                }

                public void setOrders_id(int orders_id) {
                    this.orders_id = orders_id;
                }

                public List<String> getSpecifications() {
                    return specifications;
                }

                public void setSpecifications(List<String> specifications) {
                    this.specifications = specifications;
                }
            }

            public static class LogDetailsBean {
                /**
                 * type : 0
                 * createdDate : 2018-07-27 19:24:53
                 */

                private String type;
                private String createdDate;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
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
}

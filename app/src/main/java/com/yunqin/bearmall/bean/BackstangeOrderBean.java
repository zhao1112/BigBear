package com.yunqin.bearmall.bean;

import java.util.List;

public class BackstangeOrderBean {

/**
 *
 *      合伙人管理后台订单bean
 *
 * */
    /**
     * msg : 请求成功
     * code : 1
     * data : {"orders":[{"image":"http://img.alicdn.com/bao/uploaded/i4/2932851384/O1CN018oWp8I1M5uedMZNw7_!!2932851384.jpg","orderNo":"554074658774735463","customerId":12393,"itemInfo":"安全裤防走光女夏蕾丝可内外穿三分保险裤不卷边短裤大码打底裤","settlementAmount":"0.00"}]}
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
        private List<OrdersBean> orders;

        public List<OrdersBean> getOrders() {
            return orders;
        }

        public void setOrders(List<OrdersBean> orders) {
            this.orders = orders;
        }

        public static class OrdersBean {
            /**
             * image : http://img.alicdn.com/bao/uploaded/i4/2932851384/O1CN018oWp8I1M5uedMZNw7_!!2932851384.jpg
             * orderNo : 554074658774735463
             * customerId : 12393
             * itemInfo : 安全裤防走光女夏蕾丝可内外穿三分保险裤不卷边短裤大码打底裤
             * settlementAmount : 0.00
             */

            private String image;
            private String orderNo;
            private int customerId;
            private String itemInfo;
            private String settlementAmount;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public String getItemInfo() {
                return itemInfo;
            }

            public void setItemInfo(String itemInfo) {
                this.itemInfo = itemInfo;
            }

            public String getSettlementAmount() {
                return settlementAmount;
            }

            public void setSettlementAmount(String settlementAmount) {
                this.settlementAmount = settlementAmount;
            }
        }
    }
}

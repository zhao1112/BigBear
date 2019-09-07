package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author Master
 * @create 2018/8/23 19:18
 */
public class Logistics {

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
        private List<List<LogisticsListBean>> logisticsList;
        private List<OrderShippingListBean> orderShippingList;

        public List<List<LogisticsListBean>> getLogisticsList() {
            return logisticsList;
        }

        public void setLogisticsList(List<List<LogisticsListBean>> logisticsList) {
            this.logisticsList = logisticsList;
        }

        public List<OrderShippingListBean> getOrderShippingList() {
            return orderShippingList;
        }

        public void setOrderShippingList(List<OrderShippingListBean> orderShippingList) {
            this.orderShippingList = orderShippingList;
        }
    }

    public static class LogisticsListBean {
        private String time;
        private String context;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }
    }

    public static class OrderShippingListBean {
        private int orderShipping_id;
        private String trackingNo;
        private int orderItem_id;
        private String deliveryCorpCode;
        private String createdDate;
        private String shippingMethodName;
        private String deliveryCorp;
        private int orders_id;

        public String getDeliveryCorp() {
            return deliveryCorp;
        }

        public void setDeliveryCorp(String deliveryCorp) {
            this.deliveryCorp = deliveryCorp;
        }

        public int getOrderShipping_id() {
            return orderShipping_id;
        }

        public void setOrderShipping_id(int orderShipping_id) {
            this.orderShipping_id = orderShipping_id;
        }

        public String getTrackingNo() {
            return trackingNo;
        }

        public void setTrackingNo(String trackingNo) {
            this.trackingNo = trackingNo;
        }

        public int getOrderItem_id() {
            return orderItem_id;
        }

        public void setOrderItem_id(int orderItem_id) {
            this.orderItem_id = orderItem_id;
        }

        public String getDeliveryCorpCode() {
            return deliveryCorpCode;
        }

        public void setDeliveryCorpCode(String deliveryCorpCode) {
            this.deliveryCorpCode = deliveryCorpCode;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getShippingMethodName() {
            return shippingMethodName;
        }

        public void setShippingMethodName(String shippingMethodName) {
            this.shippingMethodName = shippingMethodName;
        }

        public int getOrders_id() {
            return orders_id;
        }

        public void setOrders_id(int orders_id) {
            this.orders_id = orders_id;
        }
    }
}

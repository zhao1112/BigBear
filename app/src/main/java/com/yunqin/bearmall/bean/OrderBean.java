package com.yunqin.bearmall.bean;

import java.util.List;

public class OrderBean {


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

        private int has_more;
        private List<OrdersListBean> ordersList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<OrdersListBean> getOrdersList() {
            return ordersList;
        }

        public void setOrdersList(List<OrdersListBean> ordersList) {
            this.ordersList = ordersList;
        }

        public static class OrdersListBean {

            private boolean isBanlancePaid;

            public boolean isBanlancePaid() {
                return isBanlancePaid;
            }

            public void setBanlancePaid(boolean banlancePaid) {
                isBanlancePaid = banlancePaid;
            }

            private String usersTicketDiscount;
            private int orderProductType;
            private String expireTime;
            private String btAmount;
            private String amount;
            private String logo;
            private int status;
            private String sn;
            private int isNeedPay;
            private String amountPaid;
            private int store_id;
            private int itemCount;
            private String store_name;
            private String outTradeNo;
            private String createdDate;
            private int isExpire;
            private int orderType;
            private boolean isReviewed;
            private String btAmountPaid;
            private int orders_id;
            private int isAllowAfterSales;
            private String trackingNo;
            private String deliveryTime;
            private String shippingMethod;
            private String deliveryCorp;
            private List<ItemBean> item;
            private VirtualItemBean virtualItem;


            public String getUsersTicketDiscount() {
                return usersTicketDiscount;
            }

            public void setUsersTicketDiscount(String usersTicketDiscount) {
                this.usersTicketDiscount = usersTicketDiscount;
            }

            public int getOrderProductType() {
                return orderProductType;
            }

            public void setOrderProductType(int orderProductType) {
                this.orderProductType = orderProductType;
            }

            public String getExpireTime() {
                return expireTime;
            }

            public void setExpireTime(String expireTime) {
                this.expireTime = expireTime;
            }

            public String getBtAmount() {
                return btAmount;
            }

            public void setBtAmount(String btAmount) {
                this.btAmount = btAmount;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getSn() {
                return sn;
            }

            public void setSn(String sn) {
                this.sn = sn;
            }

            public int getIsNeedPay() {
                return isNeedPay;
            }

            public void setIsNeedPay(int isNeedPay) {
                this.isNeedPay = isNeedPay;
            }

            public String getAmountPaid() {
                return amountPaid;
            }

            public void setAmountPaid(String amountPaid) {
                this.amountPaid = amountPaid;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public int getItemCount() {
                return itemCount;
            }

            public void setItemCount(int itemCount) {
                this.itemCount = itemCount;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public String getOutTradeNo() {
                return outTradeNo;
            }

            public void setOutTradeNo(String outTradeNo) {
                this.outTradeNo = outTradeNo;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public int getIsExpire() {
                return isExpire;
            }

            public void setIsExpire(int isExpire) {
                this.isExpire = isExpire;
            }

            public int getOrderType() {
                return orderType;
            }

            public void setOrderType(int orderType) {
                this.orderType = orderType;
            }

            public boolean isReviewed() {
                return isReviewed;
            }

            public void setReviewed(boolean reviewed) {
                isReviewed = reviewed;
            }

            public String getBtAmountPaid() {
                return btAmountPaid;
            }

            public void setBtAmountPaid(String btAmountPaid) {
                this.btAmountPaid = btAmountPaid;
            }

            public int getOrders_id() {
                return orders_id;
            }

            public void setOrders_id(int orders_id) {
                this.orders_id = orders_id;
            }

            public int getIsAllowAfterSales() {
                return isAllowAfterSales;
            }

            public void setIsAllowAfterSales(int isAllowAfterSales) {
                this.isAllowAfterSales = isAllowAfterSales;
            }

            public String getTrackingNo() {
                return trackingNo;
            }

            public void setTrackingNo(String trackingNo) {
                this.trackingNo = trackingNo;
            }

            public String getDeliveryTime() {
                return deliveryTime;
            }

            public void setDeliveryTime(String deliveryTime) {
                this.deliveryTime = deliveryTime;
            }

            public String getShippingMethod() {
                return shippingMethod;
            }

            public void setShippingMethod(String shippingMethod) {
                this.shippingMethod = shippingMethod;
            }

            public String getDeliveryCorp() {
                return deliveryCorp;
            }

            public void setDeliveryCorp(String deliveryCorp) {
                this.deliveryCorp = deliveryCorp;
            }

            public List<ItemBean> getItem() {
                return item;
            }

            public void setItem(List<ItemBean> item) {
                this.item = item;
            }

            public VirtualItemBean getVirtualItem() {
                return virtualItem;
            }

            public void setVirtualItem(VirtualItemBean virtualItem) {
                this.virtualItem = virtualItem;
            }

            public static class VirtualItemBean {


                private String img;
                private int orders_id;
                private int quantity;
                private String price;
                private int virtualRechargeProudct_id;
                private int carrierType;
                private int isAllowAfterSales;
                private int type;
                private String title;
                private int orderProductType;

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

                public int getOrderProductType() {
                    return orderProductType;
                }

                public void setOrderProductType(int orderProductType) {
                    this.orderProductType = orderProductType;
                }
            }


            public static class ItemBean {

                private int paymentModel;
                private String itemName;
                private Object weight;
                private String thumbnail;
                private String price;
                private int product_id;
                private int orderItem_id;
                private int quantity;
                private int sku_id;
                private int orders_id;
                private List<String> specificationItems;
                // TODO 追加参数
                private String partBtAmount;
                private String partPrice;

                public int getPaymentModel() {
                    return paymentModel;
                }

                public void setPaymentModel(int paymentModel) {
                    this.paymentModel = paymentModel;
                }

                public String getItemName() {
                    return itemName;
                }

                public void setItemName(String itemName) {
                    this.itemName = itemName;
                }

                public Object getWeight() {
                    return weight;
                }

                public void setWeight(Object weight) {
                    this.weight = weight;
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

                public int getOrderItem_id() {
                    return orderItem_id;
                }

                public void setOrderItem_id(int orderItem_id) {
                    this.orderItem_id = orderItem_id;
                }

                public int getQuantity() {
                    return quantity;
                }

                public void setQuantity(int quantity) {
                    this.quantity = quantity;
                }

                public int getSku_id() {
                    return sku_id;
                }

                public void setSku_id(int sku_id) {
                    this.sku_id = sku_id;
                }

                public int getOrders_id() {
                    return orders_id;
                }

                public void setOrders_id(int orders_id) {
                    this.orders_id = orders_id;
                }

                public List<String> getSpecificationItems() {
                    return specificationItems;
                }

                public void setSpecificationItems(List<String> specificationItems) {
                    this.specificationItems = specificationItems;
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
            }
        }
    }
}

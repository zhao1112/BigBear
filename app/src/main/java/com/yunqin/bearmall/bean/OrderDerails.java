package com.yunqin.bearmall.bean;

import java.util.List;

public class OrderDerails {

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

        private boolean isBanlancePaid;
        private int orderProductType;

        public boolean isBanlancePaid() {
            return isBanlancePaid;
        }

        public void setBanlancePaid(boolean banlancePaid) {
            isBanlancePaid = banlancePaid;
        }

        public int getOrderProductType() {
            return orderProductType;
        }

        public void setOrderProductType(int orderProductType) {
            this.orderProductType = orderProductType;
        }

        private StoreBean store;
        private OrderDetailBean orderDetail;

        public StoreBean getStore() {
            return store;
        }

        public void setStore(StoreBean store) {
            this.store = store;
        }

        public OrderDetailBean getOrderDetail() {
            return orderDetail;
        }

        public void setOrderDetail(OrderDetailBean orderDetail) {
            this.orderDetail = orderDetail;
        }

        public static class StoreBean {

            private int productNumber;
            private int productSales;
            private String logo;
            private String storeRankName;
            private double productScore;
            private int store_id;
            private int type;
            private String store_name;
            private List<HotProductListBean> hotProductList;

            public int getProductNumber() {
                return productNumber;
            }

            public void setProductNumber(int productNumber) {
                this.productNumber = productNumber;
            }

            public int getProductSales() {
                return productSales;
            }

            public void setProductSales(int productSales) {
                this.productSales = productSales;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getStoreRankName() {
                return storeRankName;
            }

            public void setStoreRankName(String storeRankName) {
                this.storeRankName = storeRankName;
            }

            public double getProductScore() {
                return productScore;
            }

            public void setProductScore(double productScore) {
                this.productScore = productScore;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public List<HotProductListBean> getHotProductList() {
                return hotProductList;
            }

            public void setHotProductList(List<HotProductListBean> hotProductList) {
                this.hotProductList = hotProductList;
            }

            public static class HotProductListBean {

                private ProductImagesBean productImages;
                private String price;
                private int product_id;
                private String partBtAmount;
                private String partPrice;
                private String productName;
                private String membershipPrice;
                private boolean isSurportMsp;
                private boolean IsNew;
                private int model;
                private String sourceMembershipPrice;
                private String sourcePrice;
                private int isDiscount;

                public int getIsDiscount() {
                    return isDiscount;
                }

                public void setIsDiscount(int isDiscount) {
                    this.isDiscount = isDiscount;
                }

                public String getMembershipPrice() {
                    return membershipPrice;
                }

                public void setMembershipPrice(String membershipPrice) {
                    this.membershipPrice = membershipPrice;
                }

                public boolean isSurportMsp() {
                    return isSurportMsp;
                }

                public void setSurportMsp(boolean surportMsp) {
                    isSurportMsp = surportMsp;
                }

                public boolean isNew() {
                    return IsNew;
                }

                public void setNew(boolean aNew) {
                    IsNew = aNew;
                }

                public int getModel() {
                    return model;
                }

                public void setModel(int model) {
                    this.model = model;
                }

                public String getSourceMembershipPrice() {
                    return sourceMembershipPrice;
                }

                public void setSourceMembershipPrice(String sourceMembershipPrice) {
                    this.sourceMembershipPrice = sourceMembershipPrice;
                }

                public String getSourcePrice() {
                    return sourcePrice;
                }

                public void setSourcePrice(String sourcePrice) {
                    this.sourcePrice = sourcePrice;
                }

                public ProductImagesBean getProductImages() {
                    return productImages;
                }

                public void setProductImages(ProductImagesBean productImages) {
                    this.productImages = productImages;
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

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public static class ProductImagesBean {

                    private String source;
                    private String large;
                    private String medium;
                    private String thumbnail;
                    private int order;

                    public String getSource() {
                        return source;
                    }

                    public void setSource(String source) {
                        this.source = source;
                    }

                    public String getLarge() {
                        return large;
                    }

                    public void setLarge(String large) {
                        this.large = large;
                    }

                    public String getMedium() {
                        return medium;
                    }

                    public void setMedium(String medium) {
                        this.medium = medium;
                    }

                    public String getThumbnail() {
                        return thumbnail;
                    }

                    public void setThumbnail(String thumbnail) {
                        this.thumbnail = thumbnail;
                    }

                    public int getOrder() {
                        return order;
                    }

                    public void setOrder(int order) {
                        this.order = order;
                    }
                }
            }
        }

        public static class OrderDetailBean {

            private String expireTime;
            private String phone;
            private int status;
            private String consignee;
            private String rewardPoint;
            private int store_id;
            private Object outTradeNo;
            private String orderSn;
            private int isExpire;
            private boolean isReviewed;
            private int isAllowAfterSales;
            private String amount;
            private String btAmount;
            private String price;
            private String receiveRestTime;
            private String orderRestTime;
            private String areaName;
            private String address;
            private String servicePhone;
            private String freight;
            private int isNeedPay;
            private String createdDate;
            private List<OrderItemListBean> orderItemList;
            private List<OrderShippingListBean> orderShippingList;


            public int getIsNeedPay() {
                return isNeedPay;
            }

            public void setIsNeedPay(int isNeedPay) {
                this.isNeedPay = isNeedPay;
            }

            public int getIsAllowAfterSales() {
                return isAllowAfterSales;
            }

            public void setIsAllowAfterSales(int isAllowAfterSales) {
                this.isAllowAfterSales = isAllowAfterSales;
            }

            public String getServicePhone() {
                return servicePhone;
            }

            public void setServicePhone(String servicePhone) {
                this.servicePhone = servicePhone;
            }

            public boolean isReviewed() {
                return isReviewed;
            }

            public void setReviewed(boolean reviewed) {
                isReviewed = reviewed;
            }

            public String getExpireTime() {
                return expireTime;
            }

            public void setExpireTime(String expireTime) {
                this.expireTime = expireTime;
            }

            public String getOrderRestTime() {
                return orderRestTime;
            }

            public void setOrderRestTime(String orderRestTime) {
                this.orderRestTime = orderRestTime;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getConsignee() {
                return consignee;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }

            public String getRewardPoint() {
                return rewardPoint;
            }

            public void setRewardPoint(String rewardPoint) {
                this.rewardPoint = rewardPoint;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public Object getOutTradeNo() {
                return outTradeNo;
            }

            public void setOutTradeNo(Object outTradeNo) {
                this.outTradeNo = outTradeNo;
            }

            public String getOrderSn() {
                return orderSn;
            }

            public void setOrderSn(String orderSn) {
                this.orderSn = orderSn;
            }

            public int getIsExpire() {
                return isExpire;
            }

            public void setIsExpire(int isExpire) {
                this.isExpire = isExpire;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getBtAmount() {
                return btAmount;
            }

            public void setBtAmount(String btAmount) {
                this.btAmount = btAmount;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getReceiveRestTime() {
                return receiveRestTime;
            }

            public void setReceiveRestTime(String receiveRestTime) {
                this.receiveRestTime = receiveRestTime;
            }

            public String getAreaName() {
                return areaName;
            }

            public void setAreaName(String areaName) {
                this.areaName = areaName;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getFreight() {
                return freight;
            }

            public void setFreight(String freight) {
                this.freight = freight;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public List<OrderItemListBean> getOrderItemList() {
                return orderItemList;
            }

            public void setOrderItemList(List<OrderItemListBean> orderItemList) {
                this.orderItemList = orderItemList;
            }

            public List<OrderShippingListBean> getOrderShippingList() {
                return orderShippingList;
            }

            public void setOrderShippingList(List<OrderShippingListBean> orderShippingList) {
                this.orderShippingList = orderShippingList;
            }

            public static class OrderItemListBean {

                private int orderitem_id;
                private int paymentModel;
                private String itemName;
                private int weight;
                private String price;
                private String thumbnail;
                private int product_id;
                private String partBtAmount;
                private String partPrice;
                private int quantity;
                private List<String> specifications;

                public int getOrderitem_id() {
                    return orderitem_id;
                }

                public void setOrderitem_id(int orderitem_id) {
                    this.orderitem_id = orderitem_id;
                }

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

                public int getWeight() {
                    return weight;
                }

                public void setWeight(int weight) {
                    this.weight = weight;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getThumbnail() {
                    return thumbnail;
                }

                public void setThumbnail(String thumbnail) {
                    this.thumbnail = thumbnail;
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

                public List<String> getSpecifications() {
                    return specifications;
                }

                public void setSpecifications(List<String> specifications) {
                    this.specifications = specifications;
                }
            }

            public static class OrderShippingListBean {

                private int orderShipping_id;
                private String trackingNo;
                private int orderItem_id;
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
    }
}

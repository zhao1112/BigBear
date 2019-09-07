package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author Master
 * @create 2018/7/28 17:35
 */
public class AfterSaleBean {

    /**
     * data : {"afterSalesMain":{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","phone":"15901153787","storeName":"手机数码旗舰店","areaName":"北京市朝阳区","address":"金辉大厦","consignee":"helen","orderItemList":[{"paymentModel":0,"itemName":"华为(HUAWEI) Mate10","price":"4899.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/fc32821d-c448-4a95-935b-22d7e918c7e5_thumbnail.jpg","product_id":10,"orderItemStatus":9,"partBtAmount":"0.00","partPrice":"0.00","orderItem_id":10554,"store_id":1,"quantity":1,"specifications":["粉红","64GB"],"orders_id":10552},{"paymentModel":0,"itemName":"华为(HUAWEI) Mate10","price":"4899.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/fc32821d-c448-4a95-935b-22d7e918c7e5_thumbnail.jpg","product_id":10,"orderItemStatus":9,"partBtAmount":"0.00","partPrice":"0.00","orderItem_id":10555,"store_id":1,"quantity":1,"specifications":["金色","64GB"],"orders_id":10552}],"store_id":1}}
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
         * afterSalesMain : {"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","phone":"15901153787","storeName":"手机数码旗舰店","areaName":"北京市朝阳区","address":"金辉大厦","consignee":"helen","orderItemList":[{"paymentModel":0,"itemName":"华为(HUAWEI) Mate10","price":"4899.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/fc32821d-c448-4a95-935b-22d7e918c7e5_thumbnail.jpg","product_id":10,"orderItemStatus":9,"partBtAmount":"0.00","partPrice":"0.00","orderItem_id":10554,"store_id":1,"quantity":1,"specifications":["粉红","64GB"],"orders_id":10552},{"paymentModel":0,"itemName":"华为(HUAWEI) Mate10","price":"4899.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/fc32821d-c448-4a95-935b-22d7e918c7e5_thumbnail.jpg","product_id":10,"orderItemStatus":9,"partBtAmount":"0.00","partPrice":"0.00","orderItem_id":10555,"store_id":1,"quantity":1,"specifications":["金色","64GB"],"orders_id":10552}],"store_id":1}
         */

        private AfterSalesMainBean afterSalesMain;

        public AfterSalesMainBean getAfterSalesMain() {
            return afterSalesMain;
        }

        public void setAfterSalesMain(AfterSalesMainBean afterSalesMain) {
            this.afterSalesMain = afterSalesMain;
        }

        public static class AfterSalesMainBean {
            /**
             * logo : http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png
             * phone : 15901153787
             * storeName : 手机数码旗舰店
             * areaName : 北京市朝阳区
             * address : 金辉大厦
             * consignee : helen
             * orderItemList : [{"paymentModel":0,"itemName":"华为(HUAWEI) Mate10","price":"4899.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/fc32821d-c448-4a95-935b-22d7e918c7e5_thumbnail.jpg","product_id":10,"orderItemStatus":9,"partBtAmount":"0.00","partPrice":"0.00","orderItem_id":10554,"store_id":1,"quantity":1,"specifications":["粉红","64GB"],"orders_id":10552},{"paymentModel":0,"itemName":"华为(HUAWEI) Mate10","price":"4899.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/fc32821d-c448-4a95-935b-22d7e918c7e5_thumbnail.jpg","product_id":10,"orderItemStatus":9,"partBtAmount":"0.00","partPrice":"0.00","orderItem_id":10555,"store_id":1,"quantity":1,"specifications":["金色","64GB"],"orders_id":10552}]
             * store_id : 1
             */

            private String logo;
            private String phone;
            private String storeName;
            private String areaName;
            private String address;
            private String consignee;
            private int store_id;
            private List<OrderItemListBean> orderItemList;

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
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

            public String getConsignee() {
                return consignee;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public List<OrderItemListBean> getOrderItemList() {
                return orderItemList;
            }

            public void setOrderItemList(List<OrderItemListBean> orderItemList) {
                this.orderItemList = orderItemList;
            }

            public static class OrderItemListBean {
                /**
                 * paymentModel : 0
                 * itemName : 华为(HUAWEI) Mate10
                 * price : 4899.00
                 * thumbnail : http://image.demo.b2b2c.shopxx.net/6.0/fc32821d-c448-4a95-935b-22d7e918c7e5_thumbnail.jpg
                 * product_id : 10
                 * orderItemStatus : 9
                 * partBtAmount : 0.00
                 * partPrice : 0.00
                 * orderItem_id : 10554
                 * store_id : 1
                 * quantity : 1
                 * specifications : ["粉红","64GB"]
                 * orders_id : 10552
                 */

                private int paymentModel;
                private String itemName;
                private String price;
                private String thumbnail;
                private int product_id;
                private int orderItemStatus;
                private String partBtAmount;
                private String partPrice;
                private int orderItem_id;
                private int store_id;
                private int quantity;
                private int orders_id;
                private List<String> specifications;

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

                public int getOrderItemStatus() {
                    return orderItemStatus;
                }

                public void setOrderItemStatus(int orderItemStatus) {
                    this.orderItemStatus = orderItemStatus;
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

                public int getOrderItem_id() {
                    return orderItem_id;
                }

                public void setOrderItem_id(int orderItem_id) {
                    this.orderItem_id = orderItem_id;
                }

                public int getStore_id() {
                    return store_id;
                }

                public void setStore_id(int store_id) {
                    this.store_id = store_id;
                }

                public int getQuantity() {
                    return quantity;
                }

                public void setQuantity(int quantity) {
                    this.quantity = quantity;
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
        }
    }
}

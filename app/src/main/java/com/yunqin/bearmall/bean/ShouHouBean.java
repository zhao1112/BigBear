package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author Master
 * @create 2018/7/28 13:18
 */
public class ShouHouBean {

    /**
     * data : {"afterSalesList":[{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[{"paymentModel":0,"storeName":"手机数码旗舰店","price":"9600.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg","product_id":29,"partBtAmount":"0.00","orderItemName":"苹果(Apple) iPhone X","partPrice":"0.00","orderItem_id":10553,"quantity":1,"specifications":["银色","256GB"]}],"storeName":"手机数码旗舰店","afterSales_id":10202,"dtype":"AftersalesReturns","afterSalesStatus":0},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[{"paymentModel":0,"storeName":"手机数码旗舰店","price":"9600.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg","product_id":29,"partBtAmount":"0.00","orderItemName":"苹果(Apple) iPhone X","partPrice":"0.00","orderItem_id":10551,"quantity":1,"specifications":["黑色","256GB"]},{"paymentModel":0,"storeName":"手机数码旗舰店","price":"8300.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg","product_id":29,"partBtAmount":"0.00","orderItemName":"苹果(Apple) iPhone X","partPrice":"0.00","orderItem_id":10552,"quantity":1,"specifications":["银色","64GB"]}],"storeName":"手机数码旗舰店","afterSales_id":10201,"dtype":"AftersalesRepair","afterSalesStatus":1},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[{"paymentModel":0,"storeName":"手机数码旗舰店","price":"219.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/776dfe24-2d33-4c9b-ad05-9ffea0d50801_thumbnail.jpg","product_id":147,"partBtAmount":"0.00","orderItemName":"蜘蛛王(SPIDER KING) 商务皮鞋","partPrice":"0.00","orderItem_id":10056,"quantity":1,"specifications":["黑色","40"]}],"storeName":"手机数码旗舰店","afterSales_id":10153,"dtype":"AftersalesReturns","afterSalesStatus":0},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[{"paymentModel":0,"storeName":"手机数码旗舰店","price":"219.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/776dfe24-2d33-4c9b-ad05-9ffea0d50801_thumbnail.jpg","product_id":147,"partBtAmount":"0.00","orderItemName":"蜘蛛王(SPIDER KING) 商务皮鞋","partPrice":"0.00","orderItem_id":10056,"quantity":1,"specifications":["黑色","40"]}],"storeName":"手机数码旗舰店","afterSales_id":10152,"dtype":"AftersalesReturns","afterSalesStatus":0},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[{"paymentModel":0,"storeName":"手机数码旗舰店","price":"1900.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_thumbnail.jpg","product_id":30,"partBtAmount":"0.00","orderItemName":"苹果(Apple) iPhone SE","partPrice":"0.00","orderItem_id":10451,"quantity":1,"specifications":["金色","16GB"]},{"paymentModel":0,"storeName":"手机数码旗舰店","price":"2900.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_thumbnail.jpg","product_id":30,"partBtAmount":"0.00","orderItemName":"苹果(Apple) iPhone SE","partPrice":"0.00","orderItem_id":10452,"quantity":1,"specifications":["银色","64GB"]}],"storeName":"手机数码旗舰店","afterSales_id":10151,"dtype":"AftersalesReturns","afterSalesStatus":3},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[{"paymentModel":0,"storeName":"手机数码旗舰店","price":"219.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/776dfe24-2d33-4c9b-ad05-9ffea0d50801_thumbnail.jpg","product_id":147,"partBtAmount":"0.00","orderItemName":"蜘蛛王(SPIDER KING) 商务皮鞋","partPrice":"0.00","orderItem_id":10056,"quantity":1,"specifications":["黑色","40"]}],"storeName":"手机数码旗舰店","afterSales_id":10105,"dtype":"AftersalesReturns","afterSalesStatus":4},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[],"storeName":"手机数码旗舰店","afterSales_id":10104,"dtype":"AftersalesReturns","afterSalesStatus":3},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[{"paymentModel":0,"storeName":"手机数码旗舰店","price":"1299.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_thumbnail.jpg","product_id":14,"partBtAmount":"0.00","orderItemName":"小米(MI) 小米 5X","partPrice":"0.00","orderItem_id":10103,"quantity":1,"specifications":["黑色","32GB"]}],"storeName":"手机数码旗舰店","afterSales_id":10102,"dtype":"AftersalesReturns","afterSalesStatus":4},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[{"paymentModel":0,"storeName":"手机数码旗舰店","price":"1299.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_thumbnail.jpg","product_id":14,"partBtAmount":"0.00","orderItemName":"小米(MI) 小米 5X","partPrice":"0.00","orderItem_id":10103,"quantity":1,"specifications":["黑色","32GB"]}],"storeName":"手机数码旗舰店","afterSales_id":10101,"dtype":"AftersalesReturns","afterSalesStatus":4},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[{"paymentModel":0,"storeName":"手机数码旗舰店","price":"1299.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_thumbnail.jpg","product_id":14,"partBtAmount":"0.00","orderItemName":"小米(MI) 小米 5X","partPrice":"0.00","orderItem_id":10103,"quantity":1,"specifications":["黑色","32GB"]}],"storeName":"手机数码旗舰店","afterSales_id":10051,"dtype":"AftersalesReturns","afterSalesStatus":4}],"has_more":1}
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
         * afterSalesList : [{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[{"paymentModel":0,"storeName":"手机数码旗舰店","price":"9600.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg","product_id":29,"partBtAmount":"0.00","orderItemName":"苹果(Apple) iPhone X","partPrice":"0.00","orderItem_id":10553,"quantity":1,"specifications":["银色","256GB"]}],"storeName":"手机数码旗舰店","afterSales_id":10202,"dtype":"AftersalesReturns","afterSalesStatus":0},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[{"paymentModel":0,"storeName":"手机数码旗舰店","price":"9600.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg","product_id":29,"partBtAmount":"0.00","orderItemName":"苹果(Apple) iPhone X","partPrice":"0.00","orderItem_id":10551,"quantity":1,"specifications":["黑色","256GB"]},{"paymentModel":0,"storeName":"手机数码旗舰店","price":"8300.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg","product_id":29,"partBtAmount":"0.00","orderItemName":"苹果(Apple) iPhone X","partPrice":"0.00","orderItem_id":10552,"quantity":1,"specifications":["银色","64GB"]}],"storeName":"手机数码旗舰店","afterSales_id":10201,"dtype":"AftersalesRepair","afterSalesStatus":1},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[{"paymentModel":0,"storeName":"手机数码旗舰店","price":"219.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/776dfe24-2d33-4c9b-ad05-9ffea0d50801_thumbnail.jpg","product_id":147,"partBtAmount":"0.00","orderItemName":"蜘蛛王(SPIDER KING) 商务皮鞋","partPrice":"0.00","orderItem_id":10056,"quantity":1,"specifications":["黑色","40"]}],"storeName":"手机数码旗舰店","afterSales_id":10153,"dtype":"AftersalesReturns","afterSalesStatus":0},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[{"paymentModel":0,"storeName":"手机数码旗舰店","price":"219.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/776dfe24-2d33-4c9b-ad05-9ffea0d50801_thumbnail.jpg","product_id":147,"partBtAmount":"0.00","orderItemName":"蜘蛛王(SPIDER KING) 商务皮鞋","partPrice":"0.00","orderItem_id":10056,"quantity":1,"specifications":["黑色","40"]}],"storeName":"手机数码旗舰店","afterSales_id":10152,"dtype":"AftersalesReturns","afterSalesStatus":0},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[{"paymentModel":0,"storeName":"手机数码旗舰店","price":"1900.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_thumbnail.jpg","product_id":30,"partBtAmount":"0.00","orderItemName":"苹果(Apple) iPhone SE","partPrice":"0.00","orderItem_id":10451,"quantity":1,"specifications":["金色","16GB"]},{"paymentModel":0,"storeName":"手机数码旗舰店","price":"2900.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_thumbnail.jpg","product_id":30,"partBtAmount":"0.00","orderItemName":"苹果(Apple) iPhone SE","partPrice":"0.00","orderItem_id":10452,"quantity":1,"specifications":["银色","64GB"]}],"storeName":"手机数码旗舰店","afterSales_id":10151,"dtype":"AftersalesReturns","afterSalesStatus":3},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[{"paymentModel":0,"storeName":"手机数码旗舰店","price":"219.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/776dfe24-2d33-4c9b-ad05-9ffea0d50801_thumbnail.jpg","product_id":147,"partBtAmount":"0.00","orderItemName":"蜘蛛王(SPIDER KING) 商务皮鞋","partPrice":"0.00","orderItem_id":10056,"quantity":1,"specifications":["黑色","40"]}],"storeName":"手机数码旗舰店","afterSales_id":10105,"dtype":"AftersalesReturns","afterSalesStatus":4},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[],"storeName":"手机数码旗舰店","afterSales_id":10104,"dtype":"AftersalesReturns","afterSalesStatus":3},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[{"paymentModel":0,"storeName":"手机数码旗舰店","price":"1299.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_thumbnail.jpg","product_id":14,"partBtAmount":"0.00","orderItemName":"小米(MI) 小米 5X","partPrice":"0.00","orderItem_id":10103,"quantity":1,"specifications":["黑色","32GB"]}],"storeName":"手机数码旗舰店","afterSales_id":10102,"dtype":"AftersalesReturns","afterSalesStatus":4},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[{"paymentModel":0,"storeName":"手机数码旗舰店","price":"1299.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_thumbnail.jpg","product_id":14,"partBtAmount":"0.00","orderItemName":"小米(MI) 小米 5X","partPrice":"0.00","orderItem_id":10103,"quantity":1,"specifications":["黑色","32GB"]}],"storeName":"手机数码旗舰店","afterSales_id":10101,"dtype":"AftersalesReturns","afterSalesStatus":4},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","afterSalesItemList":[{"paymentModel":0,"storeName":"手机数码旗舰店","price":"1299.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/70bf86db-e5cb-4f93-bd3e-177a8358e934_thumbnail.jpg","product_id":14,"partBtAmount":"0.00","orderItemName":"小米(MI) 小米 5X","partPrice":"0.00","orderItem_id":10103,"quantity":1,"specifications":["黑色","32GB"]}],"storeName":"手机数码旗舰店","afterSales_id":10051,"dtype":"AftersalesReturns","afterSalesStatus":4}]
         * has_more : 1
         */

        private int has_more;
        private List<AfterSalesListBean> afterSalesList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<AfterSalesListBean> getAfterSalesList() {
            return afterSalesList;
        }

        public void setAfterSalesList(List<AfterSalesListBean> afterSalesList) {
            this.afterSalesList = afterSalesList;
        }

        public static class AfterSalesListBean {
            /**
             * logo : http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png
             * afterSalesItemList : [{"paymentModel":0,"storeName":"手机数码旗舰店","price":"9600.00","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg","product_id":29,"partBtAmount":"0.00","orderItemName":"苹果(Apple) iPhone X","partPrice":"0.00","orderItem_id":10553,"quantity":1,"specifications":["银色","256GB"]}]
             * storeName : 手机数码旗舰店
             * afterSales_id : 10202
             * dtype : AftersalesReturns
             * afterSalesStatus : 0
             */




            private String logo;
            private String storeName;
            private int afterSales_id;
            private String dtype;
            private int afterSalesStatus;
            private List<AfterSalesItemListBean> afterSalesItemList;

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public int getAfterSales_id() {
                return afterSales_id;
            }

            public void setAfterSales_id(int afterSales_id) {
                this.afterSales_id = afterSales_id;
            }

            public String getDtype() {
                return dtype;
            }

            public void setDtype(String dtype) {
                this.dtype = dtype;
            }

            public int getAfterSalesStatus() {
                return afterSalesStatus;
            }

            public void setAfterSalesStatus(int afterSalesStatus) {
                this.afterSalesStatus = afterSalesStatus;
            }

            public List<AfterSalesItemListBean> getAfterSalesItemList() {
                return afterSalesItemList;
            }

            public void setAfterSalesItemList(List<AfterSalesItemListBean> afterSalesItemList) {
                this.afterSalesItemList = afterSalesItemList;
            }

            public static class AfterSalesItemListBean {
                /**
                 * paymentModel : 0
                 * storeName : 手机数码旗舰店
                 * price : 9600.00
                 * thumbnail : http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg
                 * product_id : 29
                 * partBtAmount : 0.00
                 * orderItemName : 苹果(Apple) iPhone X
                 * partPrice : 0.00
                 * orderItem_id : 10553
                 * quantity : 1
                 * specifications : ["银色","256GB"]
                 */

                private String btAmount;



                private int paymentModel;
                private String storeName;
                private String price;
                private String thumbnail;
                private int product_id;
                private String partBtAmount;
                private String orderItemName;
                private String partPrice;
                private int orderItem_id;
                private int quantity;
                private List<String> specifications;


                public String getBtAmount() {
                    return btAmount;
                }

                public void setBtAmount(String btAmount) {
                    this.btAmount = btAmount;
                }

                public int getPaymentModel() {
                    return paymentModel;
                }

                public void setPaymentModel(int paymentModel) {
                    this.paymentModel = paymentModel;
                }

                public String getStoreName() {
                    return storeName;
                }

                public void setStoreName(String storeName) {
                    this.storeName = storeName;
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

                public String getOrderItemName() {
                    return orderItemName;
                }

                public void setOrderItemName(String orderItemName) {
                    this.orderItemName = orderItemName;
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
        }
    }
}

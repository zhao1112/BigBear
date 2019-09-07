package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/8/31
 * @Describe
 */
public class HelpOtherBarginInfo {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"isHaveBargained":0,"BargainDetails":{"orders_id":null,"specifications":["黑色","16GB"],"bargainProduct_id":552,"productName":"华为mate 10","sourcePartPrice":"3999.00","criticalRatio":"50%","finishedNumber":7,"product_id":10852,"expireDate":"2018-09-01 15:36:55","iconUrl":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/4e1dbcca-bafd-471c-bf2f-6bb96825fc17_medium.jpg","bargainRecord_id":138,"store_id":1,"bargainedBtAmount":"0.00","bargainCount":2,"restTime":73600890,"currentPartPrice":"3159.21","bargainedRatio":"21.00%","nickName":"游戏人生","sku_id":10903,"bargainedPrice":"839.79","productImages":{"source":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/afc3747d-490d-4f28-8cbc-a938d5c51d00_source.jpg","large":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/afc3747d-490d-4f28-8cbc-a938d5c51d00_large.jpg","medium":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/afc3747d-490d-4f28-8cbc-a938d5c51d00_medium.jpg","thumbnail":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/afc3747d-490d-4f28-8cbc-a938d5c51d00_thumbnail.jpg","order":0},"personalCount":52,"bargainMoreAmount":"399.90","currentPartBtAmount":"12.00","sourcePartBtAmount":"12.00","status":1},"store":{"store_id":1,"store_name":"手机数码旗舰店","logo":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/6eac40f8-c1e2-4a47-a008-89aa17554d59.jpg","productSales":81,"storeRankName":"普通自营店铺","productScore":4.458333333333333,"productNumber":44,"type":1},"isFavorite":0}
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
         * isHaveBargained : 0
         * BargainDetails : {"orders_id":null,"specifications":["黑色","16GB"],"bargainProduct_id":552,"productName":"华为mate 10","sourcePartPrice":"3999.00","criticalRatio":"50%","finishedNumber":7,"product_id":10852,"expireDate":"2018-09-01 15:36:55","iconUrl":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/4e1dbcca-bafd-471c-bf2f-6bb96825fc17_medium.jpg","bargainRecord_id":138,"store_id":1,"bargainedBtAmount":"0.00","bargainCount":2,"restTime":73600890,"currentPartPrice":"3159.21","bargainedRatio":"21.00%","nickName":"游戏人生","sku_id":10903,"bargainedPrice":"839.79","productImages":{"source":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/afc3747d-490d-4f28-8cbc-a938d5c51d00_source.jpg","large":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/afc3747d-490d-4f28-8cbc-a938d5c51d00_large.jpg","medium":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/afc3747d-490d-4f28-8cbc-a938d5c51d00_medium.jpg","thumbnail":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/afc3747d-490d-4f28-8cbc-a938d5c51d00_thumbnail.jpg","order":0},"personalCount":52,"bargainMoreAmount":"399.90","currentPartBtAmount":"12.00","sourcePartBtAmount":"12.00","status":1}
         * store : {"store_id":1,"store_name":"手机数码旗舰店","logo":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/6eac40f8-c1e2-4a47-a008-89aa17554d59.jpg","productSales":81,"storeRankName":"普通自营店铺","productScore":4.458333333333333,"productNumber":44,"type":1}
         * isFavorite : 0
         */

        private int isHaveBargained;
        private BargainDetailsBean BargainDetails;
        private StoreBean store;
        private int isFavorite;

        public int getIsHaveBargained() {
            return isHaveBargained;
        }

        public void setIsHaveBargained(int isHaveBargained) {
            this.isHaveBargained = isHaveBargained;
        }

        public BargainDetailsBean getBargainDetails() {
            return BargainDetails;
        }

        public void setBargainDetails(BargainDetailsBean BargainDetails) {
            this.BargainDetails = BargainDetails;
        }

        public StoreBean getStore() {
            return store;
        }

        public void setStore(StoreBean store) {
            this.store = store;
        }

        public int getIsFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(int isFavorite) {
            this.isFavorite = isFavorite;
        }

        public static class BargainDetailsBean {
            /**
             * orders_id : null
             * specifications : ["黑色","16GB"]
             * bargainProduct_id : 552
             * productName : 华为mate 10
             * sourcePartPrice : 3999.00
             * criticalRatio : 50%
             * finishedNumber : 7
             * product_id : 10852
             * expireDate : 2018-09-01 15:36:55
             * iconUrl : https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/4e1dbcca-bafd-471c-bf2f-6bb96825fc17_medium.jpg
             * bargainRecord_id : 138
             * store_id : 1
             * bargainedBtAmount : 0.00
             * bargainCount : 2
             * restTime : 73600890
             * currentPartPrice : 3159.21
             * bargainedRatio : 21.00%
             * nickName : 游戏人生
             * sku_id : 10903
             * bargainedPrice : 839.79
             * productImages : {"source":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/afc3747d-490d-4f28-8cbc-a938d5c51d00_source.jpg","large":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/afc3747d-490d-4f28-8cbc-a938d5c51d00_large.jpg","medium":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/afc3747d-490d-4f28-8cbc-a938d5c51d00_medium.jpg","thumbnail":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/afc3747d-490d-4f28-8cbc-a938d5c51d00_thumbnail.jpg","order":0}
             * personalCount : 52
             * bargainMoreAmount : 399.90
             * currentPartBtAmount : 12.00
             * sourcePartBtAmount : 12.00
             * status : 1
             */

            private String price;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            private Object orders_id;
            private int bargainProduct_id;
            private String productName;
            private String sourcePartPrice;
            private String criticalRatio;
            private int finishedNumber;
            private int product_id;
            private String expireDate;
            private String iconUrl;
            private int bargainRecord_id;
            private int store_id;
            private String bargainedBtAmount;
            private int bargainCount;
            private int restTime;
            private String currentPartPrice;
            private String bargainedRatio;
            private String nickName;
            private int sku_id;
            private String bargainedPrice;
            private ProductImagesBean productImages;
            private int personalCount;
            private String bargainMoreAmount;
            private String currentPartBtAmount;
            private String sourcePartBtAmount;
            private int status;
            private List<String> specifications;

            public Object getOrders_id() {
                return orders_id;
            }

            public void setOrders_id(Object orders_id) {
                this.orders_id = orders_id;
            }

            public int getBargainProduct_id() {
                return bargainProduct_id;
            }

            public void setBargainProduct_id(int bargainProduct_id) {
                this.bargainProduct_id = bargainProduct_id;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getSourcePartPrice() {
                return sourcePartPrice;
            }

            public void setSourcePartPrice(String sourcePartPrice) {
                this.sourcePartPrice = sourcePartPrice;
            }

            public String getCriticalRatio() {
                return criticalRatio;
            }

            public void setCriticalRatio(String criticalRatio) {
                this.criticalRatio = criticalRatio;
            }

            public int getFinishedNumber() {
                return finishedNumber;
            }

            public void setFinishedNumber(int finishedNumber) {
                this.finishedNumber = finishedNumber;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public String getExpireDate() {
                return expireDate;
            }

            public void setExpireDate(String expireDate) {
                this.expireDate = expireDate;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public int getBargainRecord_id() {
                return bargainRecord_id;
            }

            public void setBargainRecord_id(int bargainRecord_id) {
                this.bargainRecord_id = bargainRecord_id;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getBargainedBtAmount() {
                return bargainedBtAmount;
            }

            public void setBargainedBtAmount(String bargainedBtAmount) {
                this.bargainedBtAmount = bargainedBtAmount;
            }

            public int getBargainCount() {
                return bargainCount;
            }

            public void setBargainCount(int bargainCount) {
                this.bargainCount = bargainCount;
            }

            public int getRestTime() {
                return restTime;
            }

            public void setRestTime(int restTime) {
                this.restTime = restTime;
            }

            public String getCurrentPartPrice() {
                return currentPartPrice;
            }

            public void setCurrentPartPrice(String currentPartPrice) {
                this.currentPartPrice = currentPartPrice;
            }

            public String getBargainedRatio() {
                return bargainedRatio;
            }

            public void setBargainedRatio(String bargainedRatio) {
                this.bargainedRatio = bargainedRatio;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getSku_id() {
                return sku_id;
            }

            public void setSku_id(int sku_id) {
                this.sku_id = sku_id;
            }

            public String getBargainedPrice() {
                return bargainedPrice;
            }

            public void setBargainedPrice(String bargainedPrice) {
                this.bargainedPrice = bargainedPrice;
            }

            public ProductImagesBean getProductImages() {
                return productImages;
            }

            public void setProductImages(ProductImagesBean productImages) {
                this.productImages = productImages;
            }

            public int getPersonalCount() {
                return personalCount;
            }

            public void setPersonalCount(int personalCount) {
                this.personalCount = personalCount;
            }

            public String getBargainMoreAmount() {
                return bargainMoreAmount;
            }

            public void setBargainMoreAmount(String bargainMoreAmount) {
                this.bargainMoreAmount = bargainMoreAmount;
            }

            public String getCurrentPartBtAmount() {
                return currentPartBtAmount;
            }

            public void setCurrentPartBtAmount(String currentPartBtAmount) {
                this.currentPartBtAmount = currentPartBtAmount;
            }

            public String getSourcePartBtAmount() {
                return sourcePartBtAmount;
            }

            public void setSourcePartBtAmount(String sourcePartBtAmount) {
                this.sourcePartBtAmount = sourcePartBtAmount;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public List<String> getSpecifications() {
                return specifications;
            }

            public void setSpecifications(List<String> specifications) {
                this.specifications = specifications;
            }

            public static class ProductImagesBean {
                /**
                 * source : https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/afc3747d-490d-4f28-8cbc-a938d5c51d00_source.jpg
                 * large : https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/afc3747d-490d-4f28-8cbc-a938d5c51d00_large.jpg
                 * medium : https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/afc3747d-490d-4f28-8cbc-a938d5c51d00_medium.jpg
                 * thumbnail : https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/afc3747d-490d-4f28-8cbc-a938d5c51d00_thumbnail.jpg
                 * order : 0
                 */

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

        public static class StoreBean {
            /**
             * store_id : 1
             * store_name : 手机数码旗舰店
             * logo : https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/6eac40f8-c1e2-4a47-a008-89aa17554d59.jpg
             * productSales : 81
             * storeRankName : 普通自营店铺
             * productScore : 4.458333333333333
             * productNumber : 44
             * type : 1
             */

            private int store_id;
            private String store_name;
            private String logo;
            private int productSales;
            private String storeRankName;
            private double productScore;
            private int productNumber;
            private int type;

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public int getProductSales() {
                return productSales;
            }

            public void setProductSales(int productSales) {
                this.productSales = productSales;
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

            public int getProductNumber() {
                return productNumber;
            }

            public void setProductNumber(int productNumber) {
                this.productNumber = productNumber;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}

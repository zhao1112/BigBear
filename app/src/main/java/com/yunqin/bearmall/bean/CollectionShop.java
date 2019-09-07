package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/7/24
 * @Describe
 */
public class CollectionShop {

    /**
     * data : {"has_more":0,"storeFavoriteList":[{"productSales":0,"logo":"http://image.demo.b2b2c.shopxx.net/6.0/776352d8-db5b-48cf-a311-f1fb8a42beaf.png","storeRankName":"普通自营店铺","store_id":3,"type":1,"store_name":"精品服饰旗舰店","favorite_id":10002,"hotProductList":[{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_thumbnail.jpg","order":0},"price":"2990.00","product_id":165,"partBtAmount":"0.00","partPrice":"0.00","productName":"托里伯奇(TORY BURCH) 时尚斜挎包"}]}]}
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
         * has_more : 0
         * storeFavoriteList : [{"productSales":0,"logo":"http://image.demo.b2b2c.shopxx.net/6.0/776352d8-db5b-48cf-a311-f1fb8a42beaf.png","storeRankName":"普通自营店铺","store_id":3,"type":1,"store_name":"精品服饰旗舰店","favorite_id":10002,"hotProductList":[{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_thumbnail.jpg","order":0},"price":"2990.00","product_id":165,"partBtAmount":"0.00","partPrice":"0.00","productName":"托里伯奇(TORY BURCH) 时尚斜挎包"}]}]
         */

        private int has_more;
        private List<StoreFavoriteListBean> storeFavoriteList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<StoreFavoriteListBean> getStoreFavoriteList() {
            return storeFavoriteList;
        }

        public void setStoreFavoriteList(List<StoreFavoriteListBean> storeFavoriteList) {
            this.storeFavoriteList = storeFavoriteList;
        }

        public static class StoreFavoriteListBean {
            /**
             * productSales : 0
             * logo : http://image.demo.b2b2c.shopxx.net/6.0/776352d8-db5b-48cf-a311-f1fb8a42beaf.png
             * storeRankName : 普通自营店铺
             * store_id : 3
             * type : 1
             * store_name : 精品服饰旗舰店
             * favorite_id : 10002
             * hotProductList : [{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_thumbnail.jpg","order":0},"price":"2990.00","product_id":165,"partBtAmount":"0.00","partPrice":"0.00","productName":"托里伯奇(TORY BURCH) 时尚斜挎包"}]
             */

            private int productSales;
            private int productNumber;
            private String logo;
            private String storeRankName;
            private int store_id;
            private int type;
            private String store_name;
            private int favorite_id;
            private List<HotProductListBean> hotProductList;
            private boolean isHasQualification;

            public boolean isHasQualification() {
                return isHasQualification;
            }

            public void setHasQualification(boolean hasQualification) {
                isHasQualification = hasQualification;
            }

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

            public int getFavorite_id() {
                return favorite_id;
            }

            public void setFavorite_id(int favorite_id) {
                this.favorite_id = favorite_id;
            }

            public List<HotProductListBean> getHotProductList() {
                return hotProductList;
            }

            public void setHotProductList(List<HotProductListBean> hotProductList) {
                this.hotProductList = hotProductList;
            }

            public static class HotProductListBean {
                /**
                 * productImages : {"source":"http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_thumbnail.jpg","order":0}
                 * price : 2990.00
                 * product_id : 165
                 * partBtAmount : 0.00
                 * partPrice : 0.00
                 * productName : 托里伯奇(TORY BURCH) 时尚斜挎包
                 */

                private ProductImagesBean productImages;
                private String price;
                private int product_id;
                private String partBtAmount;
                private String partPrice;
                private String productName;

                private String membershipPrice;
                private String sourceMembershipPrice;
                private String sourcePrice;
                private boolean IsNew;
                private boolean isSurportMsp;
                private int model;
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

                public boolean isNew() {
                    return IsNew;
                }

                public void setNew(boolean aNew) {
                    IsNew = aNew;
                }

                public boolean isSurportMsp() {
                    return isSurportMsp;
                }

                public void setSurportMsp(boolean surportMsp) {
                    isSurportMsp = surportMsp;
                }

                public int getModel() {
                    return model;
                }

                public void setModel(int model) {
                    this.model = model;
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
                    /**
                     * source : http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_source.jpg
                     * large : http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_large.jpg
                     * medium : http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_medium.jpg
                     * thumbnail : http://image.demo.b2b2c.shopxx.net/6.0/6b93634d-c747-47c1-8af6-c0dfa6923f02_thumbnail.jpg
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
        }
    }
}

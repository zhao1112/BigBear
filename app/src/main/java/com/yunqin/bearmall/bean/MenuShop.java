package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/7/13
 * @Describe
 */
public class MenuShop {

    /**
     * data : {"storeList":[{"productNumber":910,"productSales":0,"logo":"http://image.demo.b2b2c.shopxx.net/6.0/3de84479-78db-4c1b-aadc-af90fac6c5bd.png","storeRankName":"普通店铺","productScore":0,"store_id":2,"type":0,"store_name":"办公家电旗舰店","hotProductList":[{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_thumbnail.jpg","order":0},"price":"3090.00","product_id":55,"partBtAmount":"0.00","partPrice":"0.00","productName":"TCL 家用空调"},{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/ccd533c2-fc7d-46d1-9165-2536efe56aed_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/ccd533c2-fc7d-46d1-9165-2536efe56aed_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/ccd533c2-fc7d-46d1-9165-2536efe56aed_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/ccd533c2-fc7d-46d1-9165-2536efe56aed_thumbnail.jpg","order":0},"price":"4490.00","product_id":54,"partBtAmount":"0.00","partPrice":"0.00","productName":"美的(Midea) 家用空调"},{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/bcd5e380-3f4b-43e7-a15f-f7462f3936bc_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/bcd5e380-3f4b-43e7-a15f-f7462f3936bc_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/bcd5e380-3f4b-43e7-a15f-f7462f3936bc_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/bcd5e380-3f4b-43e7-a15f-f7462f3936bc_thumbnail.jpg","order":0},"price":"6380.00","product_id":53,"partBtAmount":"0.00","partPrice":"0.00","productName":"海信(Hisense) 家用空调"}]}],"has_more":1}
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
         * storeList : [{"productNumber":910,"productSales":0,"logo":"http://image.demo.b2b2c.shopxx.net/6.0/3de84479-78db-4c1b-aadc-af90fac6c5bd.png","storeRankName":"普通店铺","productScore":0,"store_id":2,"type":0,"store_name":"办公家电旗舰店","hotProductList":[{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_thumbnail.jpg","order":0},"price":"3090.00","product_id":55,"partBtAmount":"0.00","partPrice":"0.00","productName":"TCL 家用空调"},{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/ccd533c2-fc7d-46d1-9165-2536efe56aed_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/ccd533c2-fc7d-46d1-9165-2536efe56aed_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/ccd533c2-fc7d-46d1-9165-2536efe56aed_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/ccd533c2-fc7d-46d1-9165-2536efe56aed_thumbnail.jpg","order":0},"price":"4490.00","product_id":54,"partBtAmount":"0.00","partPrice":"0.00","productName":"美的(Midea) 家用空调"},{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/bcd5e380-3f4b-43e7-a15f-f7462f3936bc_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/bcd5e380-3f4b-43e7-a15f-f7462f3936bc_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/bcd5e380-3f4b-43e7-a15f-f7462f3936bc_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/bcd5e380-3f4b-43e7-a15f-f7462f3936bc_thumbnail.jpg","order":0},"price":"6380.00","product_id":53,"partBtAmount":"0.00","partPrice":"0.00","productName":"海信(Hisense) 家用空调"}]}]
         * has_more : 1
         */

        private int has_more;
        private List<StoreListBean> storeList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<StoreListBean> getStoreList() {
            return storeList;
        }

        public void setStoreList(List<StoreListBean> storeList) {
            this.storeList = storeList;
        }

        public static class StoreListBean {
            /**
             * productNumber : 910
             * productSales : 0
             * logo : http://image.demo.b2b2c.shopxx.net/6.0/3de84479-78db-4c1b-aadc-af90fac6c5bd.png
             * storeRankName : 普通店铺
             * productScore : 0
             * store_id : 2
             * type : 0
             * store_name : 办公家电旗舰店
             * hotProductList : [{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_thumbnail.jpg","order":0},"price":"3090.00","product_id":55,"partBtAmount":"0.00","partPrice":"0.00","productName":"TCL 家用空调"},{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/ccd533c2-fc7d-46d1-9165-2536efe56aed_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/ccd533c2-fc7d-46d1-9165-2536efe56aed_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/ccd533c2-fc7d-46d1-9165-2536efe56aed_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/ccd533c2-fc7d-46d1-9165-2536efe56aed_thumbnail.jpg","order":0},"price":"4490.00","product_id":54,"partBtAmount":"0.00","partPrice":"0.00","productName":"美的(Midea) 家用空调"},{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/bcd5e380-3f4b-43e7-a15f-f7462f3936bc_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/bcd5e380-3f4b-43e7-a15f-f7462f3936bc_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/bcd5e380-3f4b-43e7-a15f-f7462f3936bc_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/bcd5e380-3f4b-43e7-a15f-f7462f3936bc_thumbnail.jpg","order":0},"price":"6380.00","product_id":53,"partBtAmount":"0.00","partPrice":"0.00","productName":"海信(Hisense) 家用空调"}]
             */

            private int productNumber;
            private int productSales;
            private String logo;
            private String storeRankName;
            private float productScore;
            private int store_id;
            private int type;
            private String store_name;
            private boolean isHasQualification;

            private List<HotProductListBean> hotProductList;

            public int getProductNumber() {
                return productNumber;
            }

            public void setProductNumber() {
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

            public boolean isHasQualification() {
                return isHasQualification;
            }

            public void setHasQualification(boolean hasQualification) {
                isHasQualification = hasQualification;
            }

            public String getStoreRankName() {
                return storeRankName;
            }

            public void setStoreRankName(String storeRankName) {
                this.storeRankName = storeRankName;
            }

            public float getProductScore() {
                return productScore;
            }

            public void setProductScore(float productScore) {
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
                /**
                 * productImages : {"source":"http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_thumbnail.jpg","order":0}
                 * price : 3090.00
                 * product_id : 55
                 * partBtAmount : 0.00
                 * partPrice : 0.00
                 * productName : TCL 家用空调
                 */

                private ProductImagesBean productImages;
                private String price;
                private int product_id;
                private String partBtAmount;
                private String partPrice;
                private String productName;
                private String membershipPrice;
                private String IsNew;
                private boolean isSurportMsp;
                private int model;
                private String sourceMembershipPrice;
                private String sourcePrice;
                private int isDiscount;

                public String getMembershipPrice() {
                    return membershipPrice;
                }

                public void setMembershipPrice(String membershipPrice) {
                    this.membershipPrice = membershipPrice;
                }

                public String getIsNew() {
                    return IsNew;
                }

                public void setIsNew(String isNew) {
                    IsNew = isNew;
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

                public int getIsDiscount() {
                    return isDiscount;
                }

                public void setIsDiscount(int isDiscount) {
                    this.isDiscount = isDiscount;
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
                     * source : http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_source.jpg
                     * large : http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_large.jpg
                     * medium : http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_medium.jpg
                     * thumbnail : http://image.demo.b2b2c.shopxx.net/6.0/beaf7089-2328-4bd2-9259-c6afc9e80213_thumbnail.jpg
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

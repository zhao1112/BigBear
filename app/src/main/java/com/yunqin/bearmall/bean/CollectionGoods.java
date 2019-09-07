package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/7/23
 * @Describe
 */
public class CollectionGoods {

    /**
     * data : {"productFavoriteList":[{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg","order":0},"price":"8300.00","product_id":29,"storeName":"手机数码旗舰店","store_id":1,"partBtAmount":"0.00","partPrice":"0.00","productName":"苹果(Apple) iPhone X","favorite_id":10002},{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_thumbnail.jpg","order":0},"price":"0.00","product_id":1,"storeName":"手机数码旗舰店","store_id":2,"partBtAmount":"0.00","partPrice":"0.00","productName":"KOOLIFE 畅玩5C钢化膜","favorite_id":10001}],"has_more":0}
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
         * productFavoriteList : [{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg","order":0},"price":"8300.00","product_id":29,"storeName":"手机数码旗舰店","store_id":1,"partBtAmount":"0.00","partPrice":"0.00","productName":"苹果(Apple) iPhone X","favorite_id":10002},{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_thumbnail.jpg","order":0},"price":"0.00","product_id":1,"storeName":"手机数码旗舰店","store_id":2,"partBtAmount":"0.00","partPrice":"0.00","productName":"KOOLIFE 畅玩5C钢化膜","favorite_id":10001}]
         * has_more : 0
         */

        private int has_more;
        private List<ProductFavoriteListBean> productFavoriteList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<ProductFavoriteListBean> getProductFavoriteList() {
            return productFavoriteList;
        }

        public void setProductFavoriteList(List<ProductFavoriteListBean> productFavoriteList) {
            this.productFavoriteList = productFavoriteList;
        }

        public static class ProductFavoriteListBean {
            /**
             * productImages : {"source":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg","order":0}
             * price : 8300.00
             * product_id : 29
             * storeName : 手机数码旗舰店
             * store_id : 1
             * partBtAmount : 0.00
             * partPrice : 0.00
             * productName : 苹果(Apple) iPhone X
             * favorite_id : 10002
             */

            private ProductImagesBean productImages;
            private String price;
            private int product_id;
            private String storeName;
            private int store_id;
            private String partBtAmount;
            private String partPrice;
            private String productName;
            private int favorite_id;
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

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
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

            public int getFavorite_id() {
                return favorite_id;
            }

            public void setFavorite_id(int favorite_id) {
                this.favorite_id = favorite_id;
            }

            public static class ProductImagesBean {
                /**
                 * source : http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_source.jpg
                 * large : http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_large.jpg
                 * medium : http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_medium.jpg
                 * thumbnail : http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg
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

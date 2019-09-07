package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/7/14
 * @Describe
 */
public class ShopBean {


    /**
     * data : {"store":{"productNumber":630,"productSales":15,"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","storeRankName":"普通自营店铺","productScore":4,"store_id":1,"type":1,"store_name":"手机数码旗舰店"},"productList":[{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg","order":0},"price":"8300.00","product_id":29,"partBtAmount":"0.00","partPrice":"0.00","productName":"苹果(Apple) iPhone X"}],"isFavorite":0,"tagList":[{"store_id":1,"orders":1,"tag_name":"本店推荐","tag_id":1},{"store_id":1,"orders":2,"tag_name":"热点推荐","tag_id":2}],"adList":[{"title":"iPhone x广告","ad_id":1,"source_id":21,"image":"http://image.demo.b2b2c.shopxx.net/6.0/d5b8bc69-9883-449e-a4fa-b12f5ebda655.jpg","type":0}]}
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
         * store : {"productNumber":630,"productSales":15,"logo":"http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png","storeRankName":"普通自营店铺","productScore":4,"store_id":1,"type":1,"store_name":"手机数码旗舰店"}
         * productList : [{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg","order":0},"price":"8300.00","product_id":29,"partBtAmount":"0.00","partPrice":"0.00","productName":"苹果(Apple) iPhone X"}]
         * isFavorite : 0
         * tagList : [{"store_id":1,"orders":1,"tag_name":"本店推荐","tag_id":1},{"store_id":1,"orders":2,"tag_name":"热点推荐","tag_id":2}]
         * adList : [{"title":"iPhone x广告","ad_id":1,"source_id":21,"image":"http://image.demo.b2b2c.shopxx.net/6.0/d5b8bc69-9883-449e-a4fa-b12f5ebda655.jpg","type":0}]
         */

        private StoreBean store;
        private int isFavorite;
        private List<ProductListBean> productList;
        private List<TagListBean> tagList;
        private List<AdListBean> adList;

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

        public List<ProductListBean> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductListBean> productList) {
            this.productList = productList;
        }

        public List<TagListBean> getTagList() {
            return tagList;
        }

        public void setTagList(List<TagListBean> tagList) {
            this.tagList = tagList;
        }

        public List<AdListBean> getAdList() {
            return adList;
        }

        public void setAdList(List<AdListBean> adList) {
            this.adList = adList;
        }

        public static class StoreBean {
            /**
             * productNumber : 630
             * productSales : 15
             * logo : http://image.demo.b2b2c.shopxx.net/6.0/fdae92b9-9ad8-4f01-a699-e129ed434fd4.png
             * storeRankName : 普通自营店铺
             * productScore : 4
             * store_id : 1
             * type : 1
             * store_name : 手机数码旗舰店
             */

            private int productNumber;
            private int productSales;
            private String logo;
            private String storeRankName;
            private float productScore;
            private int store_id;
            private int type;
            private boolean isHasQualification;
            private String store_name;

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
        }

        public static class ProductListBean {
            /**
             * productImages : {"source":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/a0808b7e-051b-424c-8b1c-04c0963ea8b6_thumbnail.jpg","order":0}
             * price : 8300.00
             * product_id : 29
             * partBtAmount : 0.00
             * partPrice : 0.00
             * productName : 苹果(Apple) iPhone X
             */

            private ProductImagesBean productImages;
            private String price;
            private int product_id;
            private String partBtAmount;
            private String partPrice;
            private String productName;

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

        public static class TagListBean {
            /**
             * store_id : 1
             * orders : 1
             * tag_name : 本店推荐
             * tag_id : 1
             */

            private int store_id;
            private int orders;
            private String tagName;
            private int tag_id;

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public int getOrders() {
                return orders;
            }

            public void setOrders(int orders) {
                this.orders = orders;
            }

            public String getTag_name() {
                return tagName;
            }

            public void setTag_name(String tag_name) {
                this.tagName = tag_name;
            }

            public int getTag_id() {
                return tag_id;
            }

            public void setTag_id(int tag_id) {
                this.tag_id = tag_id;
            }
        }

        public static class AdListBean {
            /**
             * title : iPhone x广告
             * ad_id : 1
             * source_id : 21
             * image : http://image.demo.b2b2c.shopxx.net/6.0/d5b8bc69-9883-449e-a4fa-b12f5ebda655.jpg
             * type : 0
             */

            private String title;
            private int ad_id;
            private int source_id;
            private String image;
            private int type;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getAd_id() {
                return ad_id;
            }

            public void setAd_id(int ad_id) {
                this.ad_id = ad_id;
            }

            public int getSource_id() {
                return source_id;
            }

            public void setSource_id(int source_id) {
                this.source_id = source_id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
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

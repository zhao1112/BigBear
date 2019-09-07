package com.yunqin.bearmall.bean;

import java.util.List;

import io.reactivex.annotations.Nullable;

public class MenuGoods {

    /**
     * data : {"brandList":[{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/210ca72b-1ae7-4026-b76c-21463bb1722b.png","brand_name":"苹果","brand_id":1},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/cbc4fa6f-3a14-42c7-a554-49c8cce7c446.png","brand_name":"小米","brand_id":2},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/d2f3d6d1-02fd-4f04-9329-f180e12885e7.png","brand_name":"三星","brand_id":3}],"attributeList":[{"attrIndex":0,"attrName":"操作系统","options":"安卓(Android),苹果(IOS),微软(Windows Phone),其它"},{"attrIndex":1,"attrName":"屏幕尺寸","options":"3.1-4.5英寸,4.6-5.0英寸,5.1-5.5英寸,5.6英寸及以上"}],"productList":[{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_thumbnail.jpg","order":0},"storeName":"手机数码旗舰店","product_id":30,"store_id":1,"partPrice":"0.00","attributeValue0":"苹果(IOS)","price":"1900.00","attributeValue1":"3.1-4.5英寸","attributeValue2":"16GB","attributeValue3":"移动4G/联通4G/电信4G","sales":0,"attributeValue4":"双核","partBtAmount":"0.00","createdDate":"2018-06-21 00:14:17","productName":"苹果(Apple) iPhone SE"}],"has_more":0}
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
         * brandList : [{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/210ca72b-1ae7-4026-b76c-21463bb1722b.png","brand_name":"苹果","brand_id":1},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/cbc4fa6f-3a14-42c7-a554-49c8cce7c446.png","brand_name":"小米","brand_id":2},{"logo":"http://image.demo.b2b2c.shopxx.net/6.0/d2f3d6d1-02fd-4f04-9329-f180e12885e7.png","brand_name":"三星","brand_id":3}]
         * attributeList : [{"attrIndex":0,"attrName":"操作系统","options":"安卓(Android),苹果(IOS),微软(Windows Phone),其它"},{"attrIndex":1,"attrName":"屏幕尺寸","options":"3.1-4.5英寸,4.6-5.0英寸,5.1-5.5英寸,5.6英寸及以上"}]
         * productList : [{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_thumbnail.jpg","order":0},"storeName":"手机数码旗舰店","product_id":30,"store_id":1,"partPrice":"0.00","attributeValue0":"苹果(IOS)","price":"1900.00","attributeValue1":"3.1-4.5英寸","attributeValue2":"16GB","attributeValue3":"移动4G/联通4G/电信4G","sales":0,"attributeValue4":"双核","partBtAmount":"0.00","createdDate":"2018-06-21 00:14:17","productName":"苹果(Apple) iPhone SE"}]
         * has_more : 0
         */

        private int has_more;
        private List<BrandListBean> brandList;
        private List<AttributeListBean> attributeList;
        private List<ProductListBean> productList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        @Nullable
        public List<BrandListBean> getBrandList() {
            return brandList;
        }

        public void setBrandList(@Nullable List<BrandListBean> brandList) {
            this.brandList = brandList;
        }

        @Nullable
        public List<AttributeListBean> getAttributeList() {
            return attributeList;
        }

        public void setAttributeList(@Nullable List<AttributeListBean> attributeList) {
            this.attributeList = attributeList;
        }

        public List<ProductListBean> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductListBean> productList) {
            this.productList = productList;
        }

        public static class BrandListBean {
            /**
             * logo : http://image.demo.b2b2c.shopxx.net/6.0/210ca72b-1ae7-4026-b76c-21463bb1722b.png
             * brand_name : 苹果
             * brand_id : 1
             */

            private String logo;
            private String brand_name;
            private int brand_id;

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getBrand_name() {
                return brand_name;
            }

            public void setBrand_name(String brand_name) {
                this.brand_name = brand_name;
            }

            public int getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(int brand_id) {
                this.brand_id = brand_id;
            }
        }

        public static class AttributeListBean {
            /**
             * attrIndex : 0
             * attrName : 操作系统
             * options : 安卓(Android),苹果(IOS),微软(Windows Phone),其它
             */

            private int attrIndex;
            private String attrName;
            private String options;

            public int getAttrIndex() {
                return attrIndex;
            }

            public void setAttrIndex(int attrIndex) {
                this.attrIndex = attrIndex;
            }

            public String getAttrName() {
                return attrName;
            }

            public void setAttrName(String attrName) {
                this.attrName = attrName;
            }

            public String getOptions() {
                return options;
            }

            public void setOptions(String options) {
                this.options = options;
            }
        }

        public static class ProductListBean {
            /**
             * productImages : {"source":"http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_thumbnail.jpg","order":0}
             * storeName : 手机数码旗舰店
             * product_id : 30
             * store_id : 1
             * partPrice : 0.00
             * attributeValue0 : 苹果(IOS)
             * price : 1900.00
             * attributeValue1 : 3.1-4.5英寸
             * attributeValue2 : 16GB
             * attributeValue3 : 移动4G/联通4G/电信4G
             * sales : 0
             * attributeValue4 : 双核
             * partBtAmount : 0.00
             * createdDate : 2018-06-21 00:14:17
             * productName : 苹果(Apple) iPhone SE
             * score : 0.0
             */

            private ProductImagesBean productImages;
            private String storeName;
            private int product_id;
            private int store_id;
            private String partPrice;
            private String attributeValue0;
            private String price;
            private String attributeValue1;
            private String attributeValue2;
            private String attributeValue3;
            private int sales;
            private String attributeValue4;
            private String partBtAmount;
            private String score;
            private String createdDate;
            private String productName;
            private String membershipPrice;
            private boolean IsNew;
            private boolean isCrossBorder;
            private boolean isSurportMsp;
            private int model;
            private String sourceMembershipPrice;
            private String sourcePrice;
            private int isDiscount;

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public boolean isCrossBorder() {
                return isCrossBorder;
            }

            public void setCrossBorder(boolean crossBorder) {
                isCrossBorder = crossBorder;
            }

            public String getMembershipPrice() {
                return membershipPrice;
            }

            public void setMembershipPrice(String membershipPrice) {
                this.membershipPrice = membershipPrice;
            }

            public boolean getIsNew() {
                return IsNew;
            }

            public void setIsNew(boolean isNew) {
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

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getPartPrice() {
                return partPrice;
            }

            public void setPartPrice(String partPrice) {
                this.partPrice = partPrice;
            }

            public String getAttributeValue0() {
                return attributeValue0;
            }

            public void setAttributeValue0(String attributeValue0) {
                this.attributeValue0 = attributeValue0;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getAttributeValue1() {
                return attributeValue1;
            }

            public void setAttributeValue1(String attributeValue1) {
                this.attributeValue1 = attributeValue1;
            }

            public String getAttributeValue2() {
                return attributeValue2;
            }

            public void setAttributeValue2(String attributeValue2) {
                this.attributeValue2 = attributeValue2;
            }

            public String getAttributeValue3() {
                return attributeValue3;
            }

            public void setAttributeValue3(String attributeValue3) {
                this.attributeValue3 = attributeValue3;
            }

            public int getSales() {
                return sales;
            }

            public void setSales(int sales) {
                this.sales = sales;
            }

            public String getAttributeValue4() {
                return attributeValue4;
            }

            public void setAttributeValue4(String attributeValue4) {
                this.attributeValue4 = attributeValue4;
            }

            public String getPartBtAmount() {
                return partBtAmount;
            }

            public void setPartBtAmount(String partBtAmount) {
                this.partBtAmount = partBtAmount;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public static class ProductImagesBean {
                /**
                 * source : http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_source.jpg
                 * large : http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_large.jpg
                 * medium : http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_medium.jpg
                 * thumbnail : http://image.demo.b2b2c.shopxx.net/6.0/cd7715a9-ad05-453c-8efd-2dbcb21171cf_thumbnail.jpg
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

package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/7/14
 * @Describe
 */
public class ShopGoods {

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
        private List<ProductListBean> productList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<ProductListBean> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductListBean> productList) {
            this.productList = productList;
        }

        public static class ProductListBean {
            /**
             * productImages : {"source":"http://image.demo.b2b2c.shopxx.net/6.0/62920974-c86b-4e69-be76-7f641fccfcc9_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/62920974-c86b-4e69-be76-7f641fccfcc9_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/62920974-c86b-4e69-be76-7f641fccfcc9_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/62920974-c86b-4e69-be76-7f641fccfcc9_thumbnail.jpg","order":0}
             * price : 4699.00
             * product_id : 15
             * partBtAmount : 0.00
             * partPrice : 0.00
             * productName : 小米(MI) MIX2
             */

            private ProductImagesBean productImages;
            private String price;
            private int product_id;
            private String partBtAmount;
            private String partPrice;
            private String productName;

            private String membershipPrice;
            private boolean IsNew;
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
                 * source : http://image.demo.b2b2c.shopxx.net/6.0/62920974-c86b-4e69-be76-7f641fccfcc9_source.jpg
                 * large : http://image.demo.b2b2c.shopxx.net/6.0/62920974-c86b-4e69-be76-7f641fccfcc9_large.jpg
                 * medium : http://image.demo.b2b2c.shopxx.net/6.0/62920974-c86b-4e69-be76-7f641fccfcc9_medium.jpg
                 * thumbnail : http://image.demo.b2b2c.shopxx.net/6.0/62920974-c86b-4e69-be76-7f641fccfcc9_thumbnail.jpg
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

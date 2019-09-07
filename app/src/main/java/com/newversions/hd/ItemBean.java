package com.newversions.hd;

import java.util.List;

/**
 * Create By Master
 * On 2019/1/9 17:38
 */
public class ItemBean {


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


        private String name;
        private int has_more;
        private List<DiscountProductListBean> discountProductList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<DiscountProductListBean> getDiscountProductList() {
            return discountProductList;
        }

        public void setDiscountProductList(List<DiscountProductListBean> discountProductList) {
            this.discountProductList = discountProductList;
        }

        public static class DiscountProductListBean {

            private int sales;
            private int store_id;
            private String productVideoImage;
            private String sourceMembershipPrice;
            private int sku_id;
            private boolean isNew;
            private int isDiscount;
            private String productName;
            private ProductImagesBean productImages;
            private boolean isSurportMsp;
            private String price;
            private int product_id;
            private int model;
            private String membershipPrice;
            private String sourcePrice;

            public int getSales() {
                return sales;
            }

            public void setSales(int sales) {
                this.sales = sales;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getProductVideoImage() {
                return productVideoImage;
            }

            public void setProductVideoImage(String productVideoImage) {
                this.productVideoImage = productVideoImage;
            }

            public String getSourceMembershipPrice() {
                return sourceMembershipPrice;
            }

            public void setSourceMembershipPrice(String sourceMembershipPrice) {
                this.sourceMembershipPrice = sourceMembershipPrice;
            }

            public int getSku_id() {
                return sku_id;
            }

            public void setSku_id(int sku_id) {
                this.sku_id = sku_id;
            }

            public boolean isIsNew() {
                return isNew;
            }

            public void setIsNew(boolean isNew) {
                this.isNew = isNew;
            }

            public int getIsDiscount() {
                return isDiscount;
            }

            public void setIsDiscount(int isDiscount) {
                this.isDiscount = isDiscount;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public ProductImagesBean getProductImages() {
                return productImages;
            }

            public void setProductImages(ProductImagesBean productImages) {
                this.productImages = productImages;
            }

            public boolean isIsSurportMsp() {
                return isSurportMsp;
            }

            public void setIsSurportMsp(boolean isSurportMsp) {
                this.isSurportMsp = isSurportMsp;
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

            public int getModel() {
                return model;
            }

            public void setModel(int model) {
                this.model = model;
            }

            public String getMembershipPrice() {
                return membershipPrice;
            }

            public void setMembershipPrice(String membershipPrice) {
                this.membershipPrice = membershipPrice;
            }

            public String getSourcePrice() {
                return sourcePrice;
            }

            public void setSourcePrice(String sourcePrice) {
                this.sourcePrice = sourcePrice;
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
}

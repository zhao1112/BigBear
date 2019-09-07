package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author Master
 * @create 2018/7/31 14:40
 */
public class HomeBean {

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
        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<DataBean.ListBeanX> getList() {
            return list;
        }

        public void setList(List<DataBean.ListBeanX> list) {
            this.list = list;
        }

        private int has_more;
        private List<DataBean.ListBeanX> list;

        public static class ListBeanX {

            private DataBean.ListBeanX.ProductImagesBean productImages;
            private String price;
            private String attributeValue0;
            private String attributeValue1;
            private int product_id;
            private String attributeValue2;
            private String attributeValue3;
            private String attributeValue4;
            private String partBtAmount;
            private String partPrice;
            private int type;
            private String productName;
            private String createdDate;
            private List<DataBean.ListBeanX.ListBean> list;

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

            public String getAttributeValue0() {
                return attributeValue0;
            }

            public void setAttributeValue0(String attributeValue0) {
                this.attributeValue0 = attributeValue0;
            }

            public String getAttributeValue1() {
                return attributeValue1;
            }

            public void setAttributeValue1(String attributeValue1) {
                this.attributeValue1 = attributeValue1;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
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

            public String getPartPrice() {
                return partPrice;
            }

            public void setPartPrice(String partPrice) {
                this.partPrice = partPrice;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {

                private DataBean.ListBeanX.ProductImagesBean productImages;
                private int product_id;
                private int needCount;
                private String partPrice;
                private int groupNumber;
                private int personalCount;
                private String cost;
                private int groupPurchasing_id;
                private int productCount;
                private String price;
                private String partBtAmount;
                private String productName;
                private List<String> specifications;

                private int bargainProduct_id;

                public DataBean.ListBeanX.ProductImagesBean getProductImages() {
                    return productImages;
                }

                public void setProductImages(DataBean.ListBeanX.ProductImagesBean productImages) {
                    this.productImages = productImages;
                }

                public int getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(int product_id) {
                    this.product_id = product_id;
                }

                public int getNeedCount() {
                    return needCount;
                }

                public void setNeedCount(int needCount) {
                    this.needCount = needCount;
                }

                public String getPartPrice() {
                    return partPrice;
                }

                public void setPartPrice(String partPrice) {
                    this.partPrice = partPrice;
                }

                public int getGroupNumber() {
                    return groupNumber;
                }

                public void setGroupNumber(int groupNumber) {
                    this.groupNumber = groupNumber;
                }

                public int getPersonalCount() {
                    return personalCount;
                }

                public void setPersonalCount(int personalCount) {
                    this.personalCount = personalCount;
                }

                public String getCost() {
                    return cost;
                }

                public void setCost(String cost) {
                    this.cost = cost;
                }

                public int getGroupPurchasing_id() {
                    return groupPurchasing_id;
                }

                public void setGroupPurchasing_id(int groupPurchasing_id) {
                    this.groupPurchasing_id = groupPurchasing_id;
                }

                public int getProductCount() {
                    return productCount;
                }

                public void setProductCount(int productCount) {
                    this.productCount = productCount;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getPartBtAmount() {
                    return partBtAmount;
                }

                public void setPartBtAmount(String partBtAmount) {
                    this.partBtAmount = partBtAmount;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public List<String> getSpecifications() {
                    return specifications;
                }

                public void setSpecifications(List<String> specifications) {
                    this.specifications = specifications;
                }

                public int getBargainProduct_id() {
                    return bargainProduct_id;
                }

                public void setBargainProduct_id(int bargainProduct_id) {
                    this.bargainProduct_id = bargainProduct_id;
                }
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

package com.yunqin.bearmall.bean;

import java.util.List;

public class SearchBean {

    /**
     * data : {"productList":[{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_thumbnail.jpg","order":0},"price":"0.00","product_id":1,"partBtAmount":"0.00","partPrice":"0.00","productName":"KOOLIFE 畅玩5C钢化膜"}],"has_more":1}
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
         * productList : [{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_thumbnail.jpg","order":0},"price":"0.00","product_id":1,"partBtAmount":"0.00","partPrice":"0.00","productName":"KOOLIFE 畅玩5C钢化膜"}]
         * has_more : 1
         */

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
             * productImages : {"source":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_thumbnail.jpg","order":0}
             * price : 0.00
             * product_id : 1
             * partBtAmount : 0.00
             * partPrice : 0.00
             * productName : KOOLIFE 畅玩5C钢化膜
             */

            private ProductImagesBean productImages;
            private String price;
            private int product_id;
            private String partBtAmount;
            private String partPrice;
            private String productName;


            private String membershipPrice;
            private String sourcePrice;
            private String sourceMembershipPrice;
            private int model;
            private boolean isSurportMsp;
            private boolean IsNew;
            private boolean isCrossBorder;

            private int isDiscount;

            public int getIsDiscount() {
                return isDiscount;
            }

            public void setIsDiscount(int isDiscount) {
                this.isDiscount = isDiscount;
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

            public String getSourcePrice() {
                return sourcePrice;
            }

            public void setSourcePrice(String sourcePrice) {
                this.sourcePrice = sourcePrice;
            }

            public String getSourceMembershipPrice() {
                return sourceMembershipPrice;
            }

            public void setSourceMembershipPrice(String sourceMembershipPrice) {
                this.sourceMembershipPrice = sourceMembershipPrice;
            }

            public int getModel() {
                return model;
            }

            public void setModel(int model) {
                this.model = model;
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
                 * source : http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_source.jpg
                 * large : http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_large.jpg
                 * medium : http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_medium.jpg
                 * thumbnail : http://image.demo.b2b2c.shopxx.net/6.0/c719c026-b516-4134-b2c5-6aa4b9a162d0_thumbnail.jpg
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

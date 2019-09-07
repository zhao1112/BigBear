package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/8/3
 * @Describe
 */
public class ZeroGoodsBean {

    /**
     * data : {"groupPurchasingList":[{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/ea4dc9c0-3b03-4b68-aa5b-5efecdd4d9eb_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/ea4dc9c0-3b03-4b68-aa5b-5efecdd4d9eb_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/ea4dc9c0-3b03-4b68-aa5b-5efecdd4d9eb_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/ea4dc9c0-3b03-4b68-aa5b-5efecdd4d9eb_thumbnail.jpg","order":0},"tag":1,"needCount":0,"partPrice":"0.00","groupPurchasingName":"机会难得，完美奖品拼团就中","groupNumber":10,"isPart":0,"cost":"20.00","personalCount":10,"specifications":["蓝色","32GB"],"groupPurchasing_id":54,"productCount":1,"price":"1219.00","partBtAmount":"0.00","productName":"华为(HUAWEI) G9"},{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/fc32821d-c448-4a95-935b-22d7e918c7e5_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/fc32821d-c448-4a95-935b-22d7e918c7e5_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/fc32821d-c448-4a95-935b-22d7e918c7e5_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/fc32821d-c448-4a95-935b-22d7e918c7e5_thumbnail.jpg","order":0},"tag":1,"needCount":20,"partPrice":"0.00","groupPurchasingName":"参加零元拼团就有机会中","groupNumber":20,"isPart":0,"cost":"12.00","personalCount":0,"specifications":["金色","128GB"],"groupPurchasing_id":1,"productCount":1,"price":"5399.00","partBtAmount":"0.00","productName":"华为(HUAWEI) Mate10"}],"has_more":0}
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
         * groupPurchasingList : [{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/ea4dc9c0-3b03-4b68-aa5b-5efecdd4d9eb_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/ea4dc9c0-3b03-4b68-aa5b-5efecdd4d9eb_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/ea4dc9c0-3b03-4b68-aa5b-5efecdd4d9eb_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/ea4dc9c0-3b03-4b68-aa5b-5efecdd4d9eb_thumbnail.jpg","order":0},"tag":1,"needCount":0,"partPrice":"0.00","groupPurchasingName":"机会难得，完美奖品拼团就中","groupNumber":10,"isPart":0,"cost":"20.00","personalCount":10,"specifications":["蓝色","32GB"],"groupPurchasing_id":54,"productCount":1,"price":"1219.00","partBtAmount":"0.00","productName":"华为(HUAWEI) G9"},{"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/fc32821d-c448-4a95-935b-22d7e918c7e5_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/fc32821d-c448-4a95-935b-22d7e918c7e5_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/fc32821d-c448-4a95-935b-22d7e918c7e5_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/fc32821d-c448-4a95-935b-22d7e918c7e5_thumbnail.jpg","order":0},"tag":1,"needCount":20,"partPrice":"0.00","groupPurchasingName":"参加零元拼团就有机会中","groupNumber":20,"isPart":0,"cost":"12.00","personalCount":0,"specifications":["金色","128GB"],"groupPurchasing_id":1,"productCount":1,"price":"5399.00","partBtAmount":"0.00","productName":"华为(HUAWEI) Mate10"}]
         * has_more : 0
         */

        private int has_more;
        private List<GroupPurchasingListBean> groupPurchasingList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<GroupPurchasingListBean> getGroupPurchasingList() {
            return groupPurchasingList;
        }

        public void setGroupPurchasingList(List<GroupPurchasingListBean> groupPurchasingList) {
            this.groupPurchasingList = groupPurchasingList;
        }

        public static class GroupPurchasingListBean {
            /**
             * productImages : {"source":"http://image.demo.b2b2c.shopxx.net/6.0/ea4dc9c0-3b03-4b68-aa5b-5efecdd4d9eb_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/ea4dc9c0-3b03-4b68-aa5b-5efecdd4d9eb_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/ea4dc9c0-3b03-4b68-aa5b-5efecdd4d9eb_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/ea4dc9c0-3b03-4b68-aa5b-5efecdd4d9eb_thumbnail.jpg","order":0}
             * tag : 1
             * needCount : 0
             * partPrice : 0.00
             * groupPurchasingName : 机会难得，完美奖品拼团就中
             * groupNumber : 10
             * isPart : 0
             * cost : 20.00
             * personalCount : 10
             * specifications : ["蓝色","32GB"]
             * groupPurchasing_id : 54
             * productCount : 1
             * price : 1219.00
             *
             * partBtAmount : 0.00
             * productName : 华为(HUAWEI) G9
             */

            private int totalCount;
            private String membershipPrice;

            public String getMembershipPrice() {
                return membershipPrice;
            }

            public void setMembershipPrice(String membershipPrice) {
                this.membershipPrice = membershipPrice;
            }

            private ProductImagesBean productImages;
            private int tag;
            private int needCount;
            private String partPrice;
            private String groupPurchasingName;
            private int groupNumber;
            private int isPart;
            private String cost;
            private int personalCount;
            private int groupPurchasing_id;
            private int productCount;
            private String price;
            private String partBtAmount;
            private String productName;
            private List<String> specifications;


            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public ProductImagesBean getProductImages() {
                return productImages;
            }

            public void setProductImages(ProductImagesBean productImages) {
                this.productImages = productImages;
            }

            public int getTag() {
                return tag;
            }

            public void setTag(int tag) {
                this.tag = tag;
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

            public String getGroupPurchasingName() {
                return groupPurchasingName;
            }

            public void setGroupPurchasingName(String groupPurchasingName) {
                this.groupPurchasingName = groupPurchasingName;
            }

            public int getGroupNumber() {
                return groupNumber;
            }

            public void setGroupNumber(int groupNumber) {
                this.groupNumber = groupNumber;
            }

            public int getIsPart() {
                return isPart;
            }

            public void setIsPart(int isPart) {
                this.isPart = isPart;
            }

            public String getCost() {
                return cost;
            }

            public void setCost(String cost) {
                this.cost = cost;
            }

            public int getPersonalCount() {
                return personalCount;
            }

            public void setPersonalCount(int personalCount) {
                this.personalCount = personalCount;
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

            public static class ProductImagesBean {
                /**
                 * source : http://image.demo.b2b2c.shopxx.net/6.0/ea4dc9c0-3b03-4b68-aa5b-5efecdd4d9eb_source.jpg
                 * large : http://image.demo.b2b2c.shopxx.net/6.0/ea4dc9c0-3b03-4b68-aa5b-5efecdd4d9eb_large.jpg
                 * medium : http://image.demo.b2b2c.shopxx.net/6.0/ea4dc9c0-3b03-4b68-aa5b-5efecdd4d9eb_medium.jpg
                 * thumbnail : http://image.demo.b2b2c.shopxx.net/6.0/ea4dc9c0-3b03-4b68-aa5b-5efecdd4d9eb_thumbnail.jpg
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

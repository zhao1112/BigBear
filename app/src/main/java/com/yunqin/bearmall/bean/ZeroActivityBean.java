package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/8/7
 * @Describe
 */
public class ZeroActivityBean {

    /**
     * data : {"groupPurchasingItem":{"productImages":[{"source":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_thumbnail.jpg","order":0},{"source":"http://image.demo.b2b2c.shopxx.net/6.0/7000164f-49fe-4fd3-924d-e84a717c20ca_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/7000164f-49fe-4fd3-924d-e84a717c20ca_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/7000164f-49fe-4fd3-924d-e84a717c20ca_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/7000164f-49fe-4fd3-924d-e84a717c20ca_thumbnail.jpg","order":1},{"source":"http://image.demo.b2b2c.shopxx.net/6.0/e2d09f94-3e7e-49c5-a87d-fc4b548e3de3_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/e2d09f94-3e7e-49c5-a87d-fc4b548e3de3_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/e2d09f94-3e7e-49c5-a87d-fc4b548e3de3_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/e2d09f94-3e7e-49c5-a87d-fc4b548e3de3_thumbnail.jpg","order":2},{"source":"http://image.demo.b2b2c.shopxx.net/6.0/b4a524dd-2db0-4d5c-9321-198135dc4788_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/b4a524dd-2db0-4d5c-9321-198135dc4788_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/b4a524dd-2db0-4d5c-9321-198135dc4788_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/b4a524dd-2db0-4d5c-9321-198135dc4788_thumbnail.jpg","order":3},{"source":"http://image.demo.b2b2c.shopxx.net/6.0/6f6f78de-afa9-4230-9cc9-ab07167335fd_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/6f6f78de-afa9-4230-9cc9-ab07167335fd_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/6f6f78de-afa9-4230-9cc9-ab07167335fd_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/6f6f78de-afa9-4230-9cc9-ab07167335fd_thumbnail.jpg","order":4}],"rewardStatus":0,"product_id":6,"tag":2,"partPrice":"0.00","groupPurchasingName":"赶紧拼","endDate":"2018-08-07 14:42:00","personalCount":0,"groupPurchasing_id":56,"totalCount":0,"isFavorite":0,"restTime":"3:40:2","logo":"http://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/6eac40f8-c1e2-4a47-a008-89aa17554d59.jpg","storeName":"手机数码旗舰店","status":1,"needCount":30,"store_id":1,"groupNumber":30,"isPart":0,"cost":"20.00","specifications":["黑色","64GB"],"member_id":null,"rewardBt":9,"price":"5800.00","productCount":1,"partBtAmount":"0.00","productName":"华为(HUAWEI) 畅享6"},"reviewList":[]}
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
         * groupPurchasingItem : {"productImages":[{"source":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_thumbnail.jpg","order":0},{"source":"http://image.demo.b2b2c.shopxx.net/6.0/7000164f-49fe-4fd3-924d-e84a717c20ca_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/7000164f-49fe-4fd3-924d-e84a717c20ca_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/7000164f-49fe-4fd3-924d-e84a717c20ca_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/7000164f-49fe-4fd3-924d-e84a717c20ca_thumbnail.jpg","order":1},{"source":"http://image.demo.b2b2c.shopxx.net/6.0/e2d09f94-3e7e-49c5-a87d-fc4b548e3de3_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/e2d09f94-3e7e-49c5-a87d-fc4b548e3de3_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/e2d09f94-3e7e-49c5-a87d-fc4b548e3de3_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/e2d09f94-3e7e-49c5-a87d-fc4b548e3de3_thumbnail.jpg","order":2},{"source":"http://image.demo.b2b2c.shopxx.net/6.0/b4a524dd-2db0-4d5c-9321-198135dc4788_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/b4a524dd-2db0-4d5c-9321-198135dc4788_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/b4a524dd-2db0-4d5c-9321-198135dc4788_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/b4a524dd-2db0-4d5c-9321-198135dc4788_thumbnail.jpg","order":3},{"source":"http://image.demo.b2b2c.shopxx.net/6.0/6f6f78de-afa9-4230-9cc9-ab07167335fd_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/6f6f78de-afa9-4230-9cc9-ab07167335fd_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/6f6f78de-afa9-4230-9cc9-ab07167335fd_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/6f6f78de-afa9-4230-9cc9-ab07167335fd_thumbnail.jpg","order":4}],"rewardStatus":0,"product_id":6,"tag":2,"partPrice":"0.00","groupPurchasingName":"赶紧拼","endDate":"2018-08-07 14:42:00","personalCount":0,"groupPurchasing_id":56,"totalCount":0,"isFavorite":0,"restTime":"3:40:2","logo":"http://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/6eac40f8-c1e2-4a47-a008-89aa17554d59.jpg","storeName":"手机数码旗舰店","status":1,"needCount":30,"store_id":1,"groupNumber":30,"isPart":0,"cost":"20.00","specifications":["黑色","64GB"],"member_id":null,"rewardBt":9,"price":"5800.00","productCount":1,"partBtAmount":"0.00","productName":"华为(HUAWEI) 畅享6"}
         * reviewList : []
         */

        private GroupPurchasingItemBean groupPurchasingItem;
        private List<ProductDetail.ReviewList> reviewList;

        private int reviewTotalCount;
        private boolean isMember;

        public boolean isMember() {
            return isMember;
        }

        public void setMember(boolean member) {
            isMember = member;
        }

        public int getReviewTotalCount() {
            return reviewTotalCount;
        }

        public void setReviewTotalCount(int reviewTotalCount) {
            this.reviewTotalCount = reviewTotalCount;
        }

        public GroupPurchasingItemBean getGroupPurchasingItem() {
            return groupPurchasingItem;
        }

        public void setGroupPurchasingItem(GroupPurchasingItemBean groupPurchasingItem) {
            this.groupPurchasingItem = groupPurchasingItem;
        }

        public List<ProductDetail.ReviewList> getReviewList() {
            return reviewList;
        }

        public void setReviewList(List<ProductDetail.ReviewList> reviewList) {
            this.reviewList = reviewList;
        }

        public static class GroupPurchasingItemBean {
            public boolean isCrossBorder() {
                return isCrossBorder;
            }

            public void setCrossBorder(boolean crossBorder) {
                isCrossBorder = crossBorder;
            }

            /**
             * productImages : [{"source":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_thumbnail.jpg","order":0},{"source":"http://image.demo.b2b2c.shopxx.net/6.0/7000164f-49fe-4fd3-924d-e84a717c20ca_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/7000164f-49fe-4fd3-924d-e84a717c20ca_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/7000164f-49fe-4fd3-924d-e84a717c20ca_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/7000164f-49fe-4fd3-924d-e84a717c20ca_thumbnail.jpg","order":1},{"source":"http://image.demo.b2b2c.shopxx.net/6.0/e2d09f94-3e7e-49c5-a87d-fc4b548e3de3_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/e2d09f94-3e7e-49c5-a87d-fc4b548e3de3_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/e2d09f94-3e7e-49c5-a87d-fc4b548e3de3_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/e2d09f94-3e7e-49c5-a87d-fc4b548e3de3_thumbnail.jpg","order":2},{"source":"http://image.demo.b2b2c.shopxx.net/6.0/b4a524dd-2db0-4d5c-9321-198135dc4788_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/b4a524dd-2db0-4d5c-9321-198135dc4788_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/b4a524dd-2db0-4d5c-9321-198135dc4788_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/b4a524dd-2db0-4d5c-9321-198135dc4788_thumbnail.jpg","order":3},{"source":"http://image.demo.b2b2c.shopxx.net/6.0/6f6f78de-afa9-4230-9cc9-ab07167335fd_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/6f6f78de-afa9-4230-9cc9-ab07167335fd_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/6f6f78de-afa9-4230-9cc9-ab07167335fd_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/6f6f78de-afa9-4230-9cc9-ab07167335fd_thumbnail.jpg","order":4}]
             * rewardStatus : 0
             * product_id : 6
             * tag : 2
             * partPrice : 0.00
             * groupPurchasingName : 赶紧拼
             * endDate : 2018-08-07 14:42:00
             * personalCount : 0
             * groupPurchasing_id : 56
             * totalCount : 0
             * isFavorite : 0
             * restTime : 3:40:2
             * logo : http://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/6eac40f8-c1e2-4a47-a008-89aa17554d59.jpg
             * storeName : 手机数码旗舰店
             * status : 1
             * needCount : 30
             * store_id : 1
             * groupNumber : 30
             * isPart : 0
             * cost : 20.00
             * specifications : ["黑色","64GB"]
             * member_id : null
             * rewardBt : 9
             * price : 5800.00
             * productCount : 1
             * partBtAmount : 0.00
             * productName : 华为(HUAWEI) 畅享6
             *
             */


            private btInstructionBean btInstruction;

            private String membershipPrice;

            public String getMembershipPrice() {
                return membershipPrice;
            }

            public void setMembershipPrice(String membershipPrice) {
                this.membershipPrice = membershipPrice;
            }

            public btInstructionBean getBtInstruction() {
                return btInstruction;
            }

            public void setBtInstruction(btInstructionBean btInstruction) {
                this.btInstruction = btInstruction;
            }

            private boolean isCrossBorder;
            private boolean isLimitMs;
            private int sku_id;
            private int rewardStatus;
            private int product_id;
            private int tag;
            private String partPrice;
            private String groupPurchasingName;
            private String endDate;
            private int personalCount;
            private int groupPurchasing_id;
            private int totalCount;
            private int isFavorite;
            private long restTime;
            private String logo;
            private String storeName;
            private int status;
            private int needCount;
            private int store_id;
            private int groupNumber;
            private int isPart;
            private String cost;
            private Object member_id;
            private int rewardBt;
            private String price;
            private int productCount;
            private String partBtAmount;
            private String productName;
            private List<ProductImagesBean> productImages;
            private List<String> specifications;

            public boolean isLimitMs() {
                return isLimitMs;
            }

            public void setLimitMs(boolean limitMsdc) {
                isLimitMs = limitMsdc;
            }

            public int getSku_id() {
                return sku_id;
            }

            public void setSku_id(int sku_id) {
                this.sku_id = sku_id;
            }

            public int getRewardStatus() {
                return rewardStatus;
            }

            public void setRewardStatus(int rewardStatus) {
                this.rewardStatus = rewardStatus;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public int getTag() {
                return tag;
            }

            public void setTag(int tag) {
                this.tag = tag;
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

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
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

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public int getIsFavorite() {
                return isFavorite;
            }

            public void setIsFavorite(int isFavorite) {
                this.isFavorite = isFavorite;
            }

            public long getRestTime() {
                return restTime;
            }

            public void setRestTime(long restTime) {
                this.restTime = restTime;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getNeedCount() {
                return needCount;
            }

            public void setNeedCount(int needCount) {
                this.needCount = needCount;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
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

            public Object getMember_id() {
                return member_id;
            }

            public void setMember_id(Object member_id) {
                this.member_id = member_id;
            }

            public int getRewardBt() {
                return rewardBt;
            }

            public void setRewardBt(int rewardBt) {
                this.rewardBt = rewardBt;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getProductCount() {
                return productCount;
            }

            public void setProductCount(int productCount) {
                this.productCount = productCount;
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

            public List<ProductImagesBean> getProductImages() {
                return productImages;
            }

            public void setProductImages(List<ProductImagesBean> productImages) {
                this.productImages = productImages;
            }

            public List<String> getSpecifications() {
                return specifications;
            }

            public void setSpecifications(List<String> specifications) {
                this.specifications = specifications;
            }


            public static class btInstructionBean{
                private String v0;// 10%
                private String v1;// 分享
                private String v2;// 分销

                public String getV0() {
                    return v0;
                }

                public void setV0(String v0) {
                    this.v0 = v0;
                }

                public String getV1() {
                    return v1;
                }

                public void setV1(String v1) {
                    this.v1 = v1;
                }

                public String getV2() {
                    return v2;
                }

                public void setV2(String v2) {
                    this.v2 = v2;
                }
            }



            public static class ProductImagesBean  {
                /**
                 * source : http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_source.jpg
                 * large : http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_large.jpg
                 * medium : http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_medium.jpg
                 * thumbnail : http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_thumbnail.jpg
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

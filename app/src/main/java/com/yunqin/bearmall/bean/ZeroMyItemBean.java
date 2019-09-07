package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/8/8
 * @Describe
 */
public class ZeroMyItemBean {

    /**
     * data : {"has_more":0,"memberGroupRecordList":[{"isWin":true,"rewardStatus":0,"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_thumbnail.jpg","order":0},"product_id":6,"status":1,"tag":2,"needCount":30,"groupPurchasingName":"快来抢","endDate":"2018-08-08 15:45:00","groupNumber":30,"personalCount":0,"groupPurchasing_id":55,"restTime":"2:18:17","productName":"华为(HUAWEI) 畅享6","orders_id":null},{"isWin":true,"rewardStatus":0,"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_thumbnail.jpg","order":0},"product_id":6,"status":1,"tag":2,"needCount":30,"groupPurchasingName":"c","endDate":"2018-08-08 15:45:00","groupNumber":30,"personalCount":0,"groupPurchasing_id":73,"restTime":"2:18:17","productName":"华为(HUAWEI) 畅享6","orders_id":null},{"isWin":true,"rewardStatus":0,"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_thumbnail.jpg","order":0},"product_id":6,"status":1,"tag":2,"needCount":30,"groupPurchasingName":"赶紧拼","endDate":"2018-08-08 15:45:00","groupNumber":30,"personalCount":0,"groupPurchasing_id":62,"restTime":"2:18:17","productName":"华为(HUAWEI) 畅享6","orders_id":null},{"isWin":true,"rewardStatus":0,"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_thumbnail.jpg","order":0},"product_id":6,"status":1,"tag":2,"needCount":30,"groupPurchasingName":"快来抢","endDate":"2018-08-08 15:45:00","groupNumber":30,"personalCount":0,"groupPurchasing_id":61,"restTime":"2:18:17","productName":"华为(HUAWEI) 畅享6","orders_id":null},{"isWin":true,"rewardStatus":0,"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_thumbnail.jpg","order":0},"product_id":6,"status":1,"tag":2,"needCount":30,"groupPurchasingName":"d","endDate":"2018-08-08 15:45:00","groupNumber":30,"personalCount":0,"groupPurchasing_id":72,"restTime":"2:18:17","productName":"华为(HUAWEI) 畅享6","orders_id":null}]}
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
         * has_more : 0
         * memberGroupRecordList : [{"isWin":true,"rewardStatus":0,"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_thumbnail.jpg","order":0},"product_id":6,"status":1,"tag":2,"needCount":30,"groupPurchasingName":"快来抢","endDate":"2018-08-08 15:45:00","groupNumber":30,"personalCount":0,"groupPurchasing_id":55,"restTime":"2:18:17","productName":"华为(HUAWEI) 畅享6","orders_id":null},{"isWin":true,"rewardStatus":0,"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_thumbnail.jpg","order":0},"product_id":6,"status":1,"tag":2,"needCount":30,"groupPurchasingName":"c","endDate":"2018-08-08 15:45:00","groupNumber":30,"personalCount":0,"groupPurchasing_id":73,"restTime":"2:18:17","productName":"华为(HUAWEI) 畅享6","orders_id":null},{"isWin":true,"rewardStatus":0,"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_thumbnail.jpg","order":0},"product_id":6,"status":1,"tag":2,"needCount":30,"groupPurchasingName":"赶紧拼","endDate":"2018-08-08 15:45:00","groupNumber":30,"personalCount":0,"groupPurchasing_id":62,"restTime":"2:18:17","productName":"华为(HUAWEI) 畅享6","orders_id":null},{"isWin":true,"rewardStatus":0,"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_thumbnail.jpg","order":0},"product_id":6,"status":1,"tag":2,"needCount":30,"groupPurchasingName":"快来抢","endDate":"2018-08-08 15:45:00","groupNumber":30,"personalCount":0,"groupPurchasing_id":61,"restTime":"2:18:17","productName":"华为(HUAWEI) 畅享6","orders_id":null},{"isWin":true,"rewardStatus":0,"productImages":{"source":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_thumbnail.jpg","order":0},"product_id":6,"status":1,"tag":2,"needCount":30,"groupPurchasingName":"d","endDate":"2018-08-08 15:45:00","groupNumber":30,"personalCount":0,"groupPurchasing_id":72,"restTime":"2:18:17","productName":"华为(HUAWEI) 畅享6","orders_id":null}]
         */

        private int has_more;
        private List<MemberGroupRecordListBean> memberGroupRecordList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<MemberGroupRecordListBean> getMemberGroupRecordList() {
            return memberGroupRecordList;
        }

        public void setMemberGroupRecordList(List<MemberGroupRecordListBean> memberGroupRecordList) {
            this.memberGroupRecordList = memberGroupRecordList;
        }

        public static class MemberGroupRecordListBean {
            /**
             * isWin : true
             * rewardStatus : 0
             * productImages : {"source":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_source.jpg","large":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_large.jpg","medium":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_medium.jpg","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/54fd1640-3f76-4910-b959-e288ce04678f_thumbnail.jpg","order":0}
             * product_id : 6
             * status : 1
             * tag : 2
             * needCount : 30
             * groupPurchasingName : 快来抢
             * endDate : 2018-08-08 15:45:00
             * groupNumber : 30
             * personalCount : 0
             * groupPurchasing_id : 55
             * restTime : 2:18:17
             * productName : 华为(HUAWEI) 畅享6
             * orders_id : null
             */

              /*
           根据服务器返回的countdown换算成手机对应的开奖时间 (毫秒)
           [正常情况最好由服务器返回countdown字段，然后客户端再校对成该手机对应的时间，不然误差很大]
         */

              private int orderStatus;

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }

            private boolean isWin;
            private int rewardStatus;
            private ProductImagesBean productImages;
            private int product_id;
            private int status;
            private int tag;
            private int needCount;
            private String groupPurchasingName;
            private String endDate;
            private int groupNumber;
            private int personalCount;
            private int groupPurchasing_id;
            private long restTime;
            private String productName;
            private long orders_id;
            private int groupPurchasingRecord_id;
            private int rewardBt;

            public boolean isWin() {
                return isWin;
            }

            public void setWin(boolean win) {
                isWin = win;
            }

            public int getRewardBt() {
                return rewardBt;
            }

            public void setRewardBt(int rewardBt) {
                this.rewardBt = rewardBt;
            }

            private long endTime;

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }

            public int getGroupPurchasingRecord_id() {
                return groupPurchasingRecord_id;
            }

            public void setGroupPurchasingRecord_id(int groupPurchasingRecord_id) {
                this.groupPurchasingRecord_id = groupPurchasingRecord_id;
            }

            public boolean isIsWin() {
                return isWin;
            }

            public void setIsWin(boolean isWin) {
                this.isWin = isWin;
            }

            public int getRewardStatus() {
                return rewardStatus;
            }

            public void setRewardStatus(int rewardStatus) {
                this.rewardStatus = rewardStatus;
            }

            public ProductImagesBean getProductImages() {
                return productImages;
            }

            public void setProductImages(ProductImagesBean productImages) {
                this.productImages = productImages;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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

            public int getGroupPurchasing_id() {
                return groupPurchasing_id;
            }

            public void setGroupPurchasing_id(int groupPurchasing_id) {
                this.groupPurchasing_id = groupPurchasing_id;
            }

            public long getRestTime() {
                return restTime;
            }

            public void setRestTime(long restTime) {
                this.restTime = restTime;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public long getOrders_id() {
                return orders_id;
            }

            public void setOrders_id(long orders_id) {
                this.orders_id = orders_id;
            }

            public static class ProductImagesBean {
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

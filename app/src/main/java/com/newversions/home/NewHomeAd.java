package com.newversions.home;

import java.util.List;

/**
 * Create By Master
 * On 2019/1/8 14:33
 */
public class NewHomeAd {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"adMobileTopList":[{"img":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201901/7c68657a-79d3-43fc-acaa-aedd1a42ee4e.jpg","skipType":0,"orders":0,"source_id":15,"type":0},{"img":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201901/bf2d904b-2e9d-433c-abc9-5626b4060112.jpg","skipType":0,"orders":1,"source_id":null,"type":2},{"img":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201901/15e277d1-e6c7-408b-99f8-5f7783e9a7a7.jpg","skipType":0,"orders":2,"source_id":null,"type":8}],"adMobileMidList":[{"img":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201901/cf078bf4-25a9-4df7-b6fe-c3cf466f3df7.jpg","skipType":0,"orders":1,"source_id":15,"type":4},{"img":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201901/4dddd9bb-9601-48c3-b4c9-863a7535e310.jpg","skipType":0,"orders":2,"source_id":7,"type":3}],"adMobileCrossList1":[{"img":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201901/51f7aaa3-6d2a-4d5e-bab8-f9889ebaf618.jpg","skipType":0,"orders":0,"source_id":1202,"type":6}],"adMobileCrossList2":[{"img":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201901/1d1a819c-3813-4e2d-867d-c5e3e8bac651.jpg","skipType":0,"orders":0,"source_id":7,"type":0}]}
     */

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
        private List<AdMobileTopListBean> adMobileTopList;
        private List<AdMobileMidListBean> adMobileMidList;
        private List<AdMobileCrossList1Bean> adMobileCrossList1;
        private List<AdMobileCrossList2Bean> adMobileCrossList2;

        public List<AdMobileTopListBean> getAdMobileTopList() {
            return adMobileTopList;
        }

        public void setAdMobileTopList(List<AdMobileTopListBean> adMobileTopList) {
            this.adMobileTopList = adMobileTopList;
        }

        public List<AdMobileMidListBean> getAdMobileMidList() {
            return adMobileMidList;
        }

        public void setAdMobileMidList(List<AdMobileMidListBean> adMobileMidList) {
            this.adMobileMidList = adMobileMidList;
        }

        public List<AdMobileCrossList1Bean> getAdMobileCrossList1() {
            return adMobileCrossList1;
        }

        public void setAdMobileCrossList1(List<AdMobileCrossList1Bean> adMobileCrossList1) {
            this.adMobileCrossList1 = adMobileCrossList1;
        }

        public List<AdMobileCrossList2Bean> getAdMobileCrossList2() {
            return adMobileCrossList2;
        }

        public void setAdMobileCrossList2(List<AdMobileCrossList2Bean> adMobileCrossList2) {
            this.adMobileCrossList2 = adMobileCrossList2;
        }

        public static class AdMobileTopListBean {
            /**
             * img : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201901/7c68657a-79d3-43fc-acaa-aedd1a42ee4e.jpg
             * skipType : 0
             * orders : 0
             * source_id : 15
             * type : 0
             */

            private String img;
            private int skipType;
            private int orders;
            private int source_id;
            private int type;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getSkipType() {
                return skipType;
            }

            public void setSkipType(int skipType) {
                this.skipType = skipType;
            }

            public int getOrders() {
                return orders;
            }

            public void setOrders(int orders) {
                this.orders = orders;
            }

            public int getSource_id() {
                return source_id;
            }

            public void setSource_id(int source_id) {
                this.source_id = source_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class AdMobileMidListBean {
            /**
             * img : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201901/cf078bf4-25a9-4df7-b6fe-c3cf466f3df7.jpg
             * skipType : 0
             * orders : 1
             * source_id : 15
             * type : 4
             */

            private String img;
            private int skipType;
            private int orders;
            private int source_id;
            private int type;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getSkipType() {
                return skipType;
            }

            public void setSkipType(int skipType) {
                this.skipType = skipType;
            }

            public int getOrders() {
                return orders;
            }

            public void setOrders(int orders) {
                this.orders = orders;
            }

            public int getSource_id() {
                return source_id;
            }

            public void setSource_id(int source_id) {
                this.source_id = source_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class AdMobileCrossList1Bean {
            /**
             * img : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201901/51f7aaa3-6d2a-4d5e-bab8-f9889ebaf618.jpg
             * skipType : 0
             * orders : 0
             * source_id : 1202
             * type : 6
             */

            private String img;
            private int skipType;
            private int orders;
            private int source_id;
            private int type;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getSkipType() {
                return skipType;
            }

            public void setSkipType(int skipType) {
                this.skipType = skipType;
            }

            public int getOrders() {
                return orders;
            }

            public void setOrders(int orders) {
                this.orders = orders;
            }

            public int getSource_id() {
                return source_id;
            }

            public void setSource_id(int source_id) {
                this.source_id = source_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class AdMobileCrossList2Bean {
            /**
             * img : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201901/1d1a819c-3813-4e2d-867d-c5e3e8bac651.jpg
             * skipType : 0
             * orders : 0
             * source_id : 7
             * type : 0
             */

            private String img;
            private int skipType;
            private int orders;
            private int source_id;
            private int type;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getSkipType() {
                return skipType;
            }

            public void setSkipType(int skipType) {
                this.skipType = skipType;
            }

            public int getOrders() {
                return orders;
            }

            public void setOrders(int orders) {
                this.orders = orders;
            }

            public int getSource_id() {
                return source_id;
            }

            public void setSource_id(int source_id) {
                this.source_id = source_id;
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

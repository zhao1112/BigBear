package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author Master
 * @create 2018/8/1 14:33
 */
public class HomeAd {

    /**
     * data : {"adMobileTopList":[{"source_id":43,"img":"http://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/57179ba8-7676-42b1-be3b-bff91016ca99.jpg","orders":0,"type":0},{"source_id":43,"img":"http://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/57179ba8-7676-42b1-be3b-bff91016ca99.jpg","orders":1,"type":0},{"source_id":null,"img":"http://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/f6a37d36-20ea-4631-962b-b96496d9593c.jpg","orders":2,"type":1}],"adMobileCrossList":[{"source_id":11,"img":"http://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/fb082225-5dbe-43a6-9edd-bc53f92e7531.jpg","orders":0,"type":0}],"adMobileMidList":[{"source_id":8,"img":"http://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/fb082225-5dbe-43a6-9edd-bc53f92e7531.jpg","orders":0,"type":0},{"source_id":10,"img":"http://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/fb082225-5dbe-43a6-9edd-bc53f92e7531.jpg","orders":1,"type":0}]}
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
        private List<AdBean> adMobileTopList;
        private List<AdBean> adMobileCrossList;
        private List<AdBean> adMobileMidList;

        public List<AdBean> getAdMobileTopList() {
            return adMobileTopList;
        }

        public void setAdMobileTopList(List<AdBean> adMobileTopList) {
            this.adMobileTopList = adMobileTopList;
        }

        public List<AdBean> getAdMobileCrossList() {
            return adMobileCrossList;
        }

        public void setAdMobileCrossList(List<AdBean> adMobileCrossList) {
            this.adMobileCrossList = adMobileCrossList;
        }

        public List<AdBean> getAdMobileMidList() {
            return adMobileMidList;
        }

        public void setAdMobileMidList(List<AdBean> adMobileMidList) {
            this.adMobileMidList = adMobileMidList;
        }

        public static class AdBean {
            /**
             * source_id : 11
             * img : http://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/fb082225-5dbe-43a6-9edd-bc53f92e7531.jpg
             * orders : 0
             * type : 0
             */

            private int source_id;
            private String img;
            private int orders;
            private int type;

            public int getSource_id() {
                return source_id;
            }

            public void setSource_id(int source_id) {
                this.source_id = source_id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getOrders() {
                return orders;
            }

            public void setOrders(int orders) {
                this.orders = orders;
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

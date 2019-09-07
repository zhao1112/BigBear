package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/7/26
 * @Describe
 */
public class BannerBean {

    /**
     * data : {"adMobileList":[{"source_id":null,"img":"http://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/f6a37d36-20ea-4631-962b-b96496d9593c.jpg","orders":2,"type":1}]}
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
        private List<AdMobileListBean> adMobileList;

        public List<AdMobileListBean> getAdMobileList() {
            return adMobileList;
        }

        public void setAdMobileList(List<AdMobileListBean> adMobileList) {
            this.adMobileList = adMobileList;
        }

        public static class AdMobileListBean {
            /**
             * source_id : null
             * img : http://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/f6a37d36-20ea-4631-962b-b96496d9593c.jpg
             * orders : 2
             * type : 1
             */

            private int source_id;
            private String img;
            private int orders;
            private int type;
            private int skipType;

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

            public int getSkipType() {
                return skipType;
            }

            public void setSkipType(int skipType) {
                this.skipType = skipType;
            }
        }
    }
}

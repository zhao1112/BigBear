package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * Created by chenchen on 2018/8/13.
 */

public class BCMessageData {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"has_more":0,"list":[{"createdDate":"2020-02-19 14:27:52","caption":"导购文章分享成功,获得糖果数量35","title":"资讯分享","value":"+35"}]}
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
        /**
         * has_more : 0
         * list : [{"createdDate":"2020-02-19 14:27:52","caption":"导购文章分享成功,获得糖果数量35","title":"资讯分享","value":"+35"}]
         */

        private int has_more;
        private List<ListBean> list;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * createdDate : 2020-02-19 14:27:52
             * caption : 导购文章分享成功,获得糖果数量35
             * title : 资讯分享
             * value : +35
             */

            private String createdDate;
            private String caption;
            private String title;
            private String value;

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public String getCaption() {
                return caption;
            }

            public void setCaption(String caption) {
                this.caption = caption;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}

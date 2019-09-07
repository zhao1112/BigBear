package com.newversions;

import java.util.List;

/**
 * Create By Master
 * On 2019/2/19 16:12
 */
public class MoneyRewardBean {


    /**
     * msg : 请求成功
     * code : 1
     * data : {"has_more":0,"list":[{"operDate":"2月19日","createdDate":"2019-02-19 15:34:00","title":"好友邀请","value":"+4.00","content":"好厉害"}]}
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
         * list : [{"operDate":"2月19日","createdDate":"2019-02-19 15:34:00","title":"好友邀请","value":"+4.00","content":"好厉害"}]
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
             * operDate : 2月19日
             * createdDate : 2019-02-19 15:34:00
             * title : 好友邀请
             * value : +4.00
             * content : 好厉害
             */

            private String operDate;
            private String createdDate;
            private String title;
            private String value;
            private String content;

            public String getOperDate() {
                return operDate;
            }

            public void setOperDate(String operDate) {
                this.operDate = operDate;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}

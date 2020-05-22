package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * Created by chenchen on 2018/8/13.
 */

public class SystemMessageData {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"has_more":0,"list":[{"article_id":null,"articleImage":null,"createdDate":"06:42:06","articleDescription":null,"title":"双十一订单太多，订单同步会有延迟","content":null},{"article_id":null,"articleImage":null,"createdDate":"昨天 06:42:54","articleDescription":null,"title":"测试","content":null}]}
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
         * list : [{"article_id":null,"articleImage":null,"createdDate":"06:42:06","articleDescription":null,"title":"双十一订单太多，订单同步会有延迟","content":null},{"article_id":null,"articleImage":null,"createdDate":"昨天 06:42:54","articleDescription":null,"title":"测试","content":null}]
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
             * article_id : null
             * articleImage : null
             * createdDate : 06:42:06
             * articleDescription : null
             * title : 双十一订单太多，订单同步会有延迟
             * content : null
             */

            private int article_id;
            private Object articleImage;
            private String createdDate;
            private Object articleDescription;
            private String title;
            private String content;

            public int getArticle_id() {
                return article_id;
            }

            public void setArticle_id(int article_id) {
                this.article_id = article_id;
            }

            public Object getArticleImage() {
                return articleImage;
            }

            public void setArticleImage(Object articleImage) {
                this.articleImage = articleImage;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public Object getArticleDescription() {
                return articleDescription;
            }

            public void setArticleDescription(Object articleDescription) {
                this.articleDescription = articleDescription;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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

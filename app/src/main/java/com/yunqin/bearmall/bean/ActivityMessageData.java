package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * Created by chenchen on 2018/8/13.
 */

public class ActivityMessageData {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"has_more":0,"list":[{"createdDate":"06:40:24","productImage":null,"product_id":null,"model":30,"title":"拍三件可享受半折优惠哦","content":null,"productName":null}]}
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
         * list : [{"createdDate":"06:40:24","productImage":null,"product_id":null,"model":30,"title":"拍三件可享受半折优惠哦","content":null,"productName":null}]
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
             * createdDate : 06:40:24
             * productImage : null
             * product_id : null
             * model : 30
             * title : 拍三件可享受半折优惠哦
             * content : null
             * productName : null
             */

            private String createdDate;
            private Object productImage;
            private Object product_id;
            private int model;
            private String title;
            private String content;
            private Object productName;
            private String article_id;
            private String articleImage;
            private int targetType;

            public String getArticle_id() {
                return article_id;
            }

            public void setArticle_id(String article_id) {
                this.article_id = article_id;
            }

            public String getArticleImage() {
                return articleImage;
            }

            public void setArticleImage(String articleImage) {
                this.articleImage = articleImage;
            }

            public int getTargetType() {
                return targetType;
            }

            public void setTargetType(int targetType) {
                this.targetType = targetType;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public Object getProductImage() {
                return productImage;
            }

            public void setProductImage(Object productImage) {
                this.productImage = productImage;
            }

            public Object getProduct_id() {
                return product_id;
            }

            public void setProduct_id(Object product_id) {
                this.product_id = product_id;
            }

            public int getModel() {
                return model;
            }

            public void setModel(int model) {
                this.model = model;
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

            public Object getProductName() {
                return productName;
            }

            public void setProductName(Object productName) {
                this.productName = productName;
            }
        }
    }
}

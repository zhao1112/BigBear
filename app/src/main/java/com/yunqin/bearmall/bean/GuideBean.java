package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * Created by chenchen on 2018/7/27.
 */

public class GuideBean {

    private int code;

    private String msg;

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{

        private int has_more;

        private List<Article> guideArticleList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<Article> getGuideArticleList() {
            return guideArticleList;
        }

        public void setGuideArticleList(List<Article> guideArticleList) {
            this.guideArticleList = guideArticleList;
        }
    }


    public static class Article{

        private String logo;

        private int hits;

        private String title;

        private List<Hot> hotList;

        private String storeName;

        private int productCategory_id;

        private int store_id;

        private String image;

        private String caption;

        private int guideArticle_id;

        private String createdDate;

        private boolean isExpand;

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public int getHits() {
            return hits;
        }

        public void setHits(int hits) {
            this.hits = hits;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<Hot> getHotList() {
            return hotList;
        }

        public void setHotList(List<Hot> hotList) {
            this.hotList = hotList;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public int getProductCategory_id() {
            return productCategory_id;
        }

        public void setProductCategory_id(int productCategory_id) {
            this.productCategory_id = productCategory_id;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public int getGuideArticle_id() {
            return guideArticle_id;
        }

        public void setGuideArticle_id(int guideArticle_id) {
            this.guideArticle_id = guideArticle_id;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public boolean isExpand() {
            return isExpand;
        }

        public void setExpand(boolean expand) {
            isExpand = expand;
        }
    }


    public static class Hot{

        private String title;

        private String image;

        private String caption;

        private String guideArticle_id;

        private String createdDate;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getGuideArticle_id() {
            return guideArticle_id;
        }

        public void setGuideArticle_id(String guideArticle_id) {
            this.guideArticle_id = guideArticle_id;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }
    }

}

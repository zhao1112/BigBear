package com.bbcoupon.ui.bean;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.bean
 * @DATE 2020/6/5
 */
public class ArticeleListInfor {

    /**
     * msg : 请求成功
     * code : 1
     * data : [{"coverimage":null,"releaseTime":"1小时前","isOverhead":0,"id":854,"views_num":"3.2k","title":"测试测试","likes_num":"0",
     * "type":0,"url":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202006/6485abf0-2c9c-4cef-ae8d-06229ec88d1c.jpg"}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * coverimage : null
         * releaseTime : 1小时前
         * isOverhead : 0
         * id : 854
         * views_num : 3.2k
         * title : 测试测试
         * likes_num : 0
         * type : 0
         * url : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202006/6485abf0-2c9c-4cef-ae8d-06229ec88d1c.jpg
         */

        private String coverimage;
        private String releaseTime;
        private int isOverhead;
        private int id;
        private String views_num;
        private String title;
        private String likes_num;
        private int type;
        private String url;

        public String getCoverimage() {
            return coverimage;
        }

        public void setCoverimage(String coverimage) {
            this.coverimage = coverimage;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }

        public int getIsOverhead() {
            return isOverhead;
        }

        public void setIsOverhead(int isOverhead) {
            this.isOverhead = isOverhead;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getViews_num() {
            return views_num;
        }

        public void setViews_num(String views_num) {
            this.views_num = views_num;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLikes_num() {
            return likes_num;
        }

        public void setLikes_num(String likes_num) {
            this.likes_num = likes_num;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

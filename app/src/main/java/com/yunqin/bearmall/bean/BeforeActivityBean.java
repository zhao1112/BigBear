package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/8/9
 * @Describe
 * 往期活动
 */
public class BeforeActivityBean {

    /**
     * data : {"has_more":0,"activityList":[{"title":"往期活动aa","article_id":10000,"img":"http://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/d122498f-4dc2-4fd0-aba4-bef99c90df2f.jpg"}]}
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
         * activityList : [{"title":"往期活动aa","article_id":10000,"img":"http://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/d122498f-4dc2-4fd0-aba4-bef99c90df2f.jpg"}]
         */

        private int has_more;
        private List<ActivityListBean> activityList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<ActivityListBean> getActivityList() {
            return activityList;
        }

        public void setActivityList(List<ActivityListBean> activityList) {
            this.activityList = activityList;
        }

        public static class ActivityListBean {
            /**
             * title : 往期活动aa
             * article_id : 10000
             * img : http://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/d122498f-4dc2-4fd0-aba4-bef99c90df2f.jpg
             */

            private String title;
            private int article_id;
            private String img;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getArticle_id() {
                return article_id;
            }

            public void setArticle_id(int article_id) {
                this.article_id = article_id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}

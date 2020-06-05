package com.bbcoupon.ui.bean;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.bean
 * @DATE 2020/6/2
 */
public class ArticleInfor {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"comment_num":0,"likes_num":1,"isGiveTheThumbsUp":0}
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
         * comment_num : 0
         * likes_num : 1
         * isGiveTheThumbsUp : 0
         */

        private String comment_num;
        private String likes_num;
        private int isGiveTheThumbsUp;

        public String getComment_num() {
            return comment_num;
        }

        public void setComment_num(String comment_num) {
            this.comment_num = comment_num;
        }

        public String getLikes_num() {
            return likes_num;
        }

        public void setLikes_num(String likes_num) {
            this.likes_num = likes_num;
        }

        public int getIsGiveTheThumbsUp() {
            return isGiveTheThumbsUp;
        }

        public void setIsGiveTheThumbsUp(int isGiveTheThumbsUp) {
            this.isGiveTheThumbsUp = isGiveTheThumbsUp;
        }
    }
}
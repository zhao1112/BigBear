package com.bbcoupon.ui.bean;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.bean
 * @DATE 2020/6/5
 */
public class CommentInfor {

    /**
     * msg : 请求成功
     * code : 1
     * data : [{"comments":"1211212","nickName":"2121212","iconUrl":"2112","commentTime":"2020-06-03"},{"comments":"1331",
     * "nickName":"21231312","iconUrl":"2123132","commentTime":"2020-06-03"},{"comments":"2131311","nickName":"hhh","iconUrl":"2311",
     * "commentTime":"2020-06-03"},{"comments":"侧手动结算辅导教师得劲开局打扫房间","nickName":"哈哈哈","iconUrl":"https://thirdwx.qlogo
     * .cn/mmopen/vi_32/DYAIOgq83epia6UhwKbdibBzZG4JdQZAVic2WXfLfGzcudWY0rGIdF4hibFgeQ2iav27PvomHicpp1FVb8Nm46qfvIicA/132",
     * "commentTime":"2020-06-03"}]
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
         * comments : 1211212
         * nickName : 2121212
         * iconUrl : 2112
         * commentTime : 2020-06-03
         */

        private String comments;
        private String nickName;
        private String iconUrl;
        private String commentTime;

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(String commentTime) {
            this.commentTime = commentTime;
        }
    }
}

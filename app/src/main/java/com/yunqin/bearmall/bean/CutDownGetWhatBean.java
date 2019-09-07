package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/8/28
 * @Describe
 */
public class CutDownGetWhatBean {

    /**
     * data : {"bargainRecordList":[{"nickName":"444","iconUrl":"123","content":"123","time":"52分前"}],"code":1,"msg":"请求成功"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * bargainRecordList : [{"nickName":"444","iconUrl":"123","content":"123","time":"52分前"}]
         * code : 1
         * msg : 请求成功
         */

        private int code;
        private String msg;
        private List<BargainRecordListBean> bargainRecordList;

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

        public List<BargainRecordListBean> getBargainRecordList() {
            return bargainRecordList;
        }

        public void setBargainRecordList(List<BargainRecordListBean> bargainRecordList) {
            this.bargainRecordList = bargainRecordList;
        }

        public static class BargainRecordListBean {
            /**
             * nickName : 444
             * iconUrl : 123
             * content : 123
             * time : 52分前
             */

            private String nickName;
            private String iconUrl;
            private String content;
            private String time;

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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}

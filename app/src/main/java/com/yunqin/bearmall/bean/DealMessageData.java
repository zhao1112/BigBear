package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * Created by chenchen on 2018/8/13.
 */

public class DealMessageData {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"has_more":0,"list":[{"createdDate":"2020-02-27 18:08:35","caption":"邀请成功!","title":"2018-09-01 14:44:04【张三】已经成为你的粉丝","content":"恭喜你成功邀请【张三】加入大熊酷朋"},{"createdDate":"2020-02-27 14:44:11","caption":"邀请粉丝成功","title":"2020-02-26 17:25:50【12212121】已经成为你的粉丝","content":null},{"createdDate":"2020-02-26 18:43:29","caption":"邀请粉丝成功","title":"2020-02-26 17:25:50【12212121】已经成为你的粉丝","content":null},{"createdDate":"2020-02-26 18:39:08","caption":"邀请粉丝成功","title":"2020-02-26 17:25:50【呵呵呵】已经成为你的粉丝","content":null},{"createdDate":"2020-02-26 17:25:50","caption":"邀请粉丝成功","title":"邀请好友","content":"2019-09-26 09:48:35【1212】已经成为你的粉丝"},{"createdDate":"2020-02-26 17:17:24","caption":"邀请粉丝成功","title":"邀请消息","content":"2019-09-26 09:48:35【1212】已经成为你的粉丝"},{"createdDate":"2020-02-26 17:04:49","caption":"邀请粉丝成功","title":"邀请消息","content":"您于元111"},{"createdDate":"2020-02-26 16:55:08","caption":null,"title":"邀请消息","content":"您于元111"},{"createdDate":"2020-02-26 16:30:32","caption":null,"title":"佣金消息","content":"12211121212"}]}
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
         * list : [{"createdDate":"2020-02-27 18:08:35","caption":"邀请成功!","title":"2018-09-01 14:44:04【张三】已经成为你的粉丝","content":"恭喜你成功邀请【张三】加入大熊酷朋"},{"createdDate":"2020-02-27 14:44:11","caption":"邀请粉丝成功","title":"2020-02-26 17:25:50【12212121】已经成为你的粉丝","content":null},{"createdDate":"2020-02-26 18:43:29","caption":"邀请粉丝成功","title":"2020-02-26 17:25:50【12212121】已经成为你的粉丝","content":null},{"createdDate":"2020-02-26 18:39:08","caption":"邀请粉丝成功","title":"2020-02-26 17:25:50【呵呵呵】已经成为你的粉丝","content":null},{"createdDate":"2020-02-26 17:25:50","caption":"邀请粉丝成功","title":"邀请好友","content":"2019-09-26 09:48:35【1212】已经成为你的粉丝"},{"createdDate":"2020-02-26 17:17:24","caption":"邀请粉丝成功","title":"邀请消息","content":"2019-09-26 09:48:35【1212】已经成为你的粉丝"},{"createdDate":"2020-02-26 17:04:49","caption":"邀请粉丝成功","title":"邀请消息","content":"您于元111"},{"createdDate":"2020-02-26 16:55:08","caption":null,"title":"邀请消息","content":"您于元111"},{"createdDate":"2020-02-26 16:30:32","caption":null,"title":"佣金消息","content":"12211121212"}]
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
             * createdDate : 2020-02-27 18:08:35
             * caption : 邀请成功!
             * title : 2018-09-01 14:44:04【张三】已经成为你的粉丝
             * content : 恭喜你成功邀请【张三】加入大熊酷朋
             */

            private String createdDate;
            private String caption;
            private String title;
            private String content;

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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}

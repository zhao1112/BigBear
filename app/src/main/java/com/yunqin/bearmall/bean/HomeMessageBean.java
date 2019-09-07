package com.yunqin.bearmall.bean;

/**
 * @author AYWang
 * @create 2018/8/15
 * @Describe
 */
public class HomeMessageBean {

    /**
     * data : {"message_3":{"title":"双十一","createdDate":"2018-08-14"},"message_2":{"title":"大促销222","createdDate":"2018-08-14"}}
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
         * message_3 : {"title":"双十一","createdDate":"2018-08-14"}
         * message_2 : {"title":"大促销222","createdDate":"2018-08-14"}
         */

        private Message1Bean message_1;
        private Message0Bean message_0;
        private Message3Bean message_3;
        private Message2Bean message_2;










        private Message2Bean message_4;


        public Message2Bean getMessage_4() {
            return message_4;
        }

        public void setMessage_4(Message2Bean message_4) {
            this.message_4 = message_4;
        }

        public Message1Bean getMessage_1() {
            return message_1;
        }

        public void setMessage_1(Message1Bean message_1) {
            this.message_1 = message_1;
        }

        public Message0Bean getMessage_0() {
            return message_0;
        }

        public void setMessage_0(Message0Bean message_0) {
            this.message_0 = message_0;
        }

        public Message3Bean getMessage_3() {
            return message_3;
        }

        public void setMessage_3(Message3Bean message_3) {
            this.message_3 = message_3;
        }

        public Message2Bean getMessage_2() {
            return message_2;
        }

        public void setMessage_2(Message2Bean message_2) {
            this.message_2 = message_2;
        }

        public static class Message3Bean {
            /**
             * title : 双十一
             * createdDate : 2018-08-14
             */

            private String title;
            private String createdDate;
            private int unreadMessageCount;

            public int getUnreadMessageCount() {
                return unreadMessageCount;
            }

            public void setUnreadMessageCount(int unreadMessageCount) {
                this.unreadMessageCount = unreadMessageCount;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }
        }

        public static class Message1Bean {
            /**
             * title : 双十一
             * createdDate : 2018-08-14
             */

            private String title;
            private String createdDate;

            private int unreadMessageCount;

            public int getUnreadMessageCount() {
                return unreadMessageCount;
            }

            public void setUnreadMessageCount(int unreadMessageCount) {
                this.unreadMessageCount = unreadMessageCount;
            }


            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }
        }

        public static class Message0Bean {
            /**
             * title : 双十一
             * createdDate : 2018-08-14
             */

            private String title;
            private String createdDate;

            private int unreadMessageCount;

            public int getUnreadMessageCount() {
                return unreadMessageCount;
            }

            public void setUnreadMessageCount(int unreadMessageCount) {
                this.unreadMessageCount = unreadMessageCount;
            }


            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }
        }

        public static class Message2Bean {
            /**
             * title : 大促销222
             * createdDate : 2018-08-14
             */

            private String title;
            private String createdDate;

            private int unreadMessageCount;

            public int getUnreadMessageCount() {
                return unreadMessageCount;
            }

            public void setUnreadMessageCount(int unreadMessageCount) {
                this.unreadMessageCount = unreadMessageCount;
            }


            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }
        }
    }
}

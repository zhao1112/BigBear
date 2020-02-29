package com.yunqin.bearmall.bean;

/**
 * @author AYWang
 * @create 2018/8/15
 * @Describe
 */
public class HomeMessageBean {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"message_3":{"title":"双十一订单太多，订单同步会有延迟","unreadMessageCount":2},"message_2":{"title":"拍三件可享受半折优惠哦","unreadMessageCount":1},"message_1":{"title":"您在2018-09-15 10:00:29下单成功，预估返佣25元,预计结算时间:收货后次月25日结算","unreadMessageCount":5},"message_0":{"title":"导购文章分享成功,获得糖果数量35","unreadMessageCount":1},"message_4":{"title":"2020-02-26 17:25:50【12212121】已经成为你的粉丝","unreadMessageCount":0}}
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
         * message_3 : {"title":"双十一订单太多，订单同步会有延迟","unreadMessageCount":2}
         * message_2 : {"title":"拍三件可享受半折优惠哦","unreadMessageCount":1}
         * message_1 : {"title":"您在2018-09-15 10:00:29下单成功，预估返佣25元,预计结算时间:收货后次月25日结算","unreadMessageCount":5}
         * message_0 : {"title":"导购文章分享成功,获得糖果数量35","unreadMessageCount":1}
         * message_4 : {"title":"2020-02-26 17:25:50【12212121】已经成为你的粉丝","unreadMessageCount":0}
         */

        private Message3Bean message_3;
        private Message2Bean message_2;
        private Message1Bean message_1;
        private Message0Bean message_0;
        private Message4Bean message_4;

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

        public Message4Bean getMessage_4() {
            return message_4;
        }

        public void setMessage_4(Message4Bean message_4) {
            this.message_4 = message_4;
        }

        public static class Message3Bean {
            /**
             * title : 双十一订单太多，订单同步会有延迟
             * unreadMessageCount : 2
             */

            private String title;
            private int unreadMessageCount;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getUnreadMessageCount() {
                return unreadMessageCount;
            }

            public void setUnreadMessageCount(int unreadMessageCount) {
                this.unreadMessageCount = unreadMessageCount;
            }
        }

        public static class Message2Bean {
            /**
             * title : 拍三件可享受半折优惠哦
             * unreadMessageCount : 1
             */

            private String title;
            private int unreadMessageCount;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getUnreadMessageCount() {
                return unreadMessageCount;
            }

            public void setUnreadMessageCount(int unreadMessageCount) {
                this.unreadMessageCount = unreadMessageCount;
            }
        }

        public static class Message1Bean {
            /**
             * title : 您在2018-09-15 10:00:29下单成功，预估返佣25元,预计结算时间:收货后次月25日结算
             * unreadMessageCount : 5
             */

            private String title;
            private int unreadMessageCount;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getUnreadMessageCount() {
                return unreadMessageCount;
            }

            public void setUnreadMessageCount(int unreadMessageCount) {
                this.unreadMessageCount = unreadMessageCount;
            }
        }

        public static class Message0Bean {
            /**
             * title : 导购文章分享成功,获得糖果数量35
             * unreadMessageCount : 1
             */

            private String title;
            private int unreadMessageCount;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getUnreadMessageCount() {
                return unreadMessageCount;
            }

            public void setUnreadMessageCount(int unreadMessageCount) {
                this.unreadMessageCount = unreadMessageCount;
            }
        }

        public static class Message4Bean {
            /**
             * title : 2020-02-26 17:25:50【12212121】已经成为你的粉丝
             * unreadMessageCount : 0
             */

            private String title;
            private int unreadMessageCount;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getUnreadMessageCount() {
                return unreadMessageCount;
            }

            public void setUnreadMessageCount(int unreadMessageCount) {
                this.unreadMessageCount = unreadMessageCount;
            }
        }
    }
}

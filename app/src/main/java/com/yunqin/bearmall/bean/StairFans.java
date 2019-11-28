package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.bean
 * @DATE 2019/11/28
 */
public class StairFans {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"oneList":[{"fans_count":4,"createdDate":"2019-10-23 14:40:22","bigBearNumber":"296326397","nickName":"缘、",
     * "customerId":11135,"mobile":"15236813252","iconUrl":"http://thirdwx.qlogo
     * .cn/mmopen/vi_32/DYAIOgq83epia6UhwKbdibBzZG4JdQZAVic2WXfLfGzcudWY0rGIdF4hibFgeQ2iav27PvomHicpp1FVb8Nm46qfvIicA/132",
     * "lastLoginDate":"2019-10-23 14:40:22","memberRank_id":0},{"fans_count":0,"createdDate":"2019-11-22 18:54:02",
     * "bigBearNumber":"205621890","nickName":"枫叶的花雨","customerId":12359,"mobile":"15515736182","iconUrl":"http://thirdwx.qlogo
     * .cn/mmopen/vi_32/C1JtdNJ2lKP1FOZF15fdBFa2YXfs6F35SqKskia3WEiar0prgsm3NyTiaT3X1yVibwVuOTkV6tichnre31Rzv6SZfJA/132",
     * "lastLoginDate":"2019-11-22 18:54:02","memberRank_id":0},{"fans_count":0,"createdDate":"2019-11-22 19:52:45",
     * "bigBearNumber":"779873070","nickName":"大熊用户779873070","customerId":12360,"mobile":"18576684975","iconUrl":"https://shopxxbbc
     * .oss-cn-beijing.aliyuncs.com/upload/image/201808/20180816140417.png","lastLoginDate":"2019-11-22 19:52:45","memberRank_id":0},{
     * "fans_count":0,"createdDate":"2019-11-22 20:52:18","bigBearNumber":"741080031","nickName":"leo","customerId":12361,
     * "mobile":"18892136721","iconUrl":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/201911/e95e06e6-2120-4197-9a33-28cffd0120f7_medium.jpg","lastLoginDate":"2019-11-22 20:52:18","memberRank_id":0}
     * ,{"fans_count":0,"createdDate":"2019-02-22 11:39:10","bigBearNumber":"9139793112","nickName":"Yan","customerId":13000,
     * "mobile":"15701301800","iconUrl":"http://thirdwx.qlogo
     * .cn/mmopen/vi_32/Q0j4TwGTfTLQqb3ScBU4MVibXNVhWxfmzWfsUzbMhGicnWxyqSgwvkZKS1VKrhWTe5T1cvd8wnZadLm8R0a9ssoA/132",
     * "lastLoginDate":"2019-02-22 11:39:10","memberRank_id":0},{"fans_count":2,"createdDate":"2019-11-22 17:36:15",
     * "bigBearNumber":"749520357","nickName":"宇宙无敌","customerId":12358,"mobile":"18568200206","iconUrl":"http://thirdwx.qlogo
     * .cn/mmopen/vi_32/NYLvH4BuCibpiaq5h3clKbQhBcMGMVrwrvkZHM2ibiadXIzvPnQrXQr3VQVFibjw2OeLoHHOyLhEDrR31J9weLYuoFw/132",
     * "lastLoginDate":"2019-11-22 17:36:15","memberRank_id":0}],"oneSize":6,"twoList":[{"fans_count":0,"createdDate":"2019-10-30
     * 09:49:33","bigBearNumber":"907712662","nickName":"枫叶的花雨","customerId":12290,"mobile":"15515736180",
     * "iconUrl":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201911/a9629d5b-3114-4c24-8498-348127ae5b9a_medium
     * .jpg","lastLoginDate":"2019-10-30 09:49:33","memberRank_id":0},{"fans_count":0,"createdDate":"2019-11-18 19:43:21",
     * "bigBearNumber":"626074679","nickName":"RitualYang","customerId":12306,"mobile":"15537310583","iconUrl":"http://thirdwx.qlogo
     * .cn/mmopen/vi_32/DYAIOgq83ep0ox8v8f6wh1icmWzl15pp59raqc0GvibL5iabGcE1MTBGHD9gib85U0kbjgD16icuS8ydt4a85Un6kaA/132",
     * "lastLoginDate":"2019-11-18 19:43:21","memberRank_id":0},{"fans_count":2,"createdDate":"2019-11-20 14:29:06",
     * "bigBearNumber":"680417735","nickName":"5555","customerId":12320,"mobile":"1551573618211","iconUrl":"http://thirdwx.qlogo
     * .cn/mmopen/vi_32/C1JtdNJ2lKP1FOZF15fdBFa2YXfs6F35SqKskia3WEiar0prgsm3NyTiaT3X1yVibwVuOTkV6tichnre31Rzv6SZfJA/132",
     * "lastLoginDate":"2019-11-20 14:29:06","memberRank_id":0},{"fans_count":0,"createdDate":"2019-11-22 18:54:02",
     * "bigBearNumber":"205621890","nickName":"枫叶的花雨","customerId":12359,"mobile":"15515736182","iconUrl":"http://thirdwx.qlogo
     * .cn/mmopen/vi_32/C1JtdNJ2lKP1FOZF15fdBFa2YXfs6F35SqKskia3WEiar0prgsm3NyTiaT3X1yVibwVuOTkV6tichnre31Rzv6SZfJA/132",
     * "lastLoginDate":"2019-11-22 18:54:02","memberRank_id":0}],"twoSize":4}
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
         * oneList : [{"fans_count":4,"createdDate":"2019-10-23 14:40:22","bigBearNumber":"296326397","nickName":"缘、","customerId":11135,
         * "mobile":"15236813252","iconUrl":"http://thirdwx.qlogo
         * .cn/mmopen/vi_32/DYAIOgq83epia6UhwKbdibBzZG4JdQZAVic2WXfLfGzcudWY0rGIdF4hibFgeQ2iav27PvomHicpp1FVb8Nm46qfvIicA/132",
         * "lastLoginDate":"2019-10-23 14:40:22","memberRank_id":0},{"fans_count":0,"createdDate":"2019-11-22 18:54:02",
         * "bigBearNumber":"205621890","nickName":"枫叶的花雨","customerId":12359,"mobile":"15515736182","iconUrl":"http://thirdwx.qlogo
         * .cn/mmopen/vi_32/C1JtdNJ2lKP1FOZF15fdBFa2YXfs6F35SqKskia3WEiar0prgsm3NyTiaT3X1yVibwVuOTkV6tichnre31Rzv6SZfJA/132",
         * "lastLoginDate":"2019-11-22 18:54:02","memberRank_id":0},{"fans_count":0,"createdDate":"2019-11-22 19:52:45",
         * "bigBearNumber":"779873070","nickName":"大熊用户779873070","customerId":12360,"mobile":"18576684975","iconUrl":"https://shopxxbbc
         * .oss-cn-beijing.aliyuncs.com/upload/image/201808/20180816140417.png","lastLoginDate":"2019-11-22 19:52:45","memberRank_id":0},
         * {"fans_count":0,"createdDate":"2019-11-22 20:52:18","bigBearNumber":"741080031","nickName":"leo","customerId":12361,
         * "mobile":"18892136721","iconUrl":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
         * .com/upload/image/201911/e95e06e6-2120-4197-9a33-28cffd0120f7_medium.jpg","lastLoginDate":"2019-11-22 20:52:18",
         * "memberRank_id":0},{"fans_count":0,"createdDate":"2019-02-22 11:39:10","bigBearNumber":"9139793112","nickName":"Yan",
         * "customerId":13000,"mobile":"15701301800","iconUrl":"http://thirdwx.qlogo
         * .cn/mmopen/vi_32/Q0j4TwGTfTLQqb3ScBU4MVibXNVhWxfmzWfsUzbMhGicnWxyqSgwvkZKS1VKrhWTe5T1cvd8wnZadLm8R0a9ssoA/132",
         * "lastLoginDate":"2019-02-22 11:39:10","memberRank_id":0},{"fans_count":2,"createdDate":"2019-11-22 17:36:15",
         * "bigBearNumber":"749520357","nickName":"宇宙无敌","customerId":12358,"mobile":"18568200206","iconUrl":"http://thirdwx.qlogo
         * .cn/mmopen/vi_32/NYLvH4BuCibpiaq5h3clKbQhBcMGMVrwrvkZHM2ibiadXIzvPnQrXQr3VQVFibjw2OeLoHHOyLhEDrR31J9weLYuoFw/132",
         * "lastLoginDate":"2019-11-22 17:36:15","memberRank_id":0}]
         * oneSize : 6
         * twoList : [{"fans_count":0,"createdDate":"2019-10-30 09:49:33","bigBearNumber":"907712662","nickName":"枫叶的花雨",
         * "customerId":12290,"mobile":"15515736180","iconUrl":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
         * .com/upload/image/201911/a9629d5b-3114-4c24-8498-348127ae5b9a_medium.jpg","lastLoginDate":"2019-10-30 09:49:33",
         * "memberRank_id":0},{"fans_count":0,"createdDate":"2019-11-18 19:43:21","bigBearNumber":"626074679","nickName":"RitualYang",
         * "customerId":12306,"mobile":"15537310583","iconUrl":"http://thirdwx.qlogo
         * .cn/mmopen/vi_32/DYAIOgq83ep0ox8v8f6wh1icmWzl15pp59raqc0GvibL5iabGcE1MTBGHD9gib85U0kbjgD16icuS8ydt4a85Un6kaA/132",
         * "lastLoginDate":"2019-11-18 19:43:21","memberRank_id":0},{"fans_count":2,"createdDate":"2019-11-20 14:29:06",
         * "bigBearNumber":"680417735","nickName":"5555","customerId":12320,"mobile":"1551573618211","iconUrl":"http://thirdwx.qlogo
         * .cn/mmopen/vi_32/C1JtdNJ2lKP1FOZF15fdBFa2YXfs6F35SqKskia3WEiar0prgsm3NyTiaT3X1yVibwVuOTkV6tichnre31Rzv6SZfJA/132",
         * "lastLoginDate":"2019-11-20 14:29:06","memberRank_id":0},{"fans_count":0,"createdDate":"2019-11-22 18:54:02",
         * "bigBearNumber":"205621890","nickName":"枫叶的花雨","customerId":12359,"mobile":"15515736182","iconUrl":"http://thirdwx.qlogo
         * .cn/mmopen/vi_32/C1JtdNJ2lKP1FOZF15fdBFa2YXfs6F35SqKskia3WEiar0prgsm3NyTiaT3X1yVibwVuOTkV6tichnre31Rzv6SZfJA/132",
         * "lastLoginDate":"2019-11-22 18:54:02","memberRank_id":0}]
         * twoSize : 4
         */

        private int oneSize;
        private int twoSize;
        private List<OneListBean> oneList;
        private List<TwoListBean> twoList;

        public int getOneSize() {
            return oneSize;
        }

        public void setOneSize(int oneSize) {
            this.oneSize = oneSize;
        }

        public int getTwoSize() {
            return twoSize;
        }

        public void setTwoSize(int twoSize) {
            this.twoSize = twoSize;
        }

        public List<OneListBean> getOneList() {
            return oneList;
        }

        public void setOneList(List<OneListBean> oneList) {
            this.oneList = oneList;
        }

        public List<TwoListBean> getTwoList() {
            return twoList;
        }

        public void setTwoList(List<TwoListBean> twoList) {
            this.twoList = twoList;
        }

        public static class OneListBean {
            /**
             * fans_count : 4
             * createdDate : 2019-10-23 14:40:22
             * bigBearNumber : 296326397
             * nickName : 缘、
             * customerId : 11135
             * mobile : 15236813252
             * iconUrl : http://thirdwx.qlogo
             * .cn/mmopen/vi_32/DYAIOgq83epia6UhwKbdibBzZG4JdQZAVic2WXfLfGzcudWY0rGIdF4hibFgeQ2iav27PvomHicpp1FVb8Nm46qfvIicA/132
             * lastLoginDate : 2019-10-23 14:40:22
             * memberRank_id : 0
             */

            private int fans_count;
            private String createdDate;
            private String bigBearNumber;
            private String nickName;
            private int customerId;
            private String mobile;
            private String iconUrl;
            private String lastLoginDate;
            private int memberRank_id;

            public int getFans_count() {
                return fans_count;
            }

            public void setFans_count(int fans_count) {
                this.fans_count = fans_count;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public String getBigBearNumber() {
                return bigBearNumber;
            }

            public void setBigBearNumber(String bigBearNumber) {
                this.bigBearNumber = bigBearNumber;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getLastLoginDate() {
                return lastLoginDate;
            }

            public void setLastLoginDate(String lastLoginDate) {
                this.lastLoginDate = lastLoginDate;
            }

            public int getMemberRank_id() {
                return memberRank_id;
            }

            public void setMemberRank_id(int memberRank_id) {
                this.memberRank_id = memberRank_id;
            }
        }

        public static class TwoListBean {
            /**
             * fans_count : 0
             * createdDate : 2019-10-30 09:49:33
             * bigBearNumber : 907712662
             * nickName : 枫叶的花雨
             * customerId : 12290
             * mobile : 15515736180
             * iconUrl : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201911/a9629d5b-3114-4c24-8498-348127ae5b9a_medium.jpg
             * lastLoginDate : 2019-10-30 09:49:33
             * memberRank_id : 0
             */

            private int fans_count;
            private String createdDate;
            private String bigBearNumber;
            private String nickName;
            private int customerId;
            private String mobile;
            private String iconUrl;
            private String lastLoginDate;
            private int memberRank_id;

            public int getFans_count() {
                return fans_count;
            }

            public void setFans_count(int fans_count) {
                this.fans_count = fans_count;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public String getBigBearNumber() {
                return bigBearNumber;
            }

            public void setBigBearNumber(String bigBearNumber) {
                this.bigBearNumber = bigBearNumber;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getLastLoginDate() {
                return lastLoginDate;
            }

            public void setLastLoginDate(String lastLoginDate) {
                this.lastLoginDate = lastLoginDate;
            }

            public int getMemberRank_id() {
                return memberRank_id;
            }

            public void setMemberRank_id(int memberRank_id) {
                this.memberRank_id = memberRank_id;
            }
        }
    }
}

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
     * data : {"size":3,"list":[{"fans_count":2,"createdDate":"2019-08-17 09:40:49","bigBearNumber":"617801472","nickName":"幸  好",
     * "customerId":12268,"mobile":"17865202474","iconUrl":"http://thirdwx.qlogo
     * .cn/mmopen/vi_32/QBRibaicGLY6scAmHricZhIR5tYCbkhW91OPa5mgXwW7IRdfGs8J61Tl2jLjdt8nucnck1ibeObYcFp6DGR2arAPng/132",
     * "lastLoginDate":"2019-08-17 09:40:49","memberRank_id":0},{"fans_count":0,"createdDate":"2019-08-17 14:09:48",
     * "bigBearNumber":"554804755","nickName":"宇宙最可爱v","customerId":12270,"mobile":"17691214223","iconUrl":"http://thirdwx.qlogo
     * .cn/mmopen/vi_32/DYAIOgq83ept6UvMIWKZIp0T9IGO7vicyvhiauqJEPt9qQmIq8zMI18GLtIBwQPN7I2uiag9WD9ymLHKu0Bp3zib9w/132",
     * "lastLoginDate":"2019-08-17 14:09:48","memberRank_id":0},{"fans_count":1,"createdDate":"2019-12-02 09:38:11",
     * "bigBearNumber":"997921461","nickName":"大熊用户997921461","customerId":12379,"mobile":"1856820020655","iconUrl":"https://shopxxbbc
     * .oss-cn-beijing.aliyuncs.com/upload/image/201808/20180816140417.png","lastLoginDate":"2019-12-02 09:38:11","memberRank_id":0}]}
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
         * size : 3
         * list : [{"fans_count":2,"createdDate":"2019-08-17 09:40:49","bigBearNumber":"617801472","nickName":"幸  好","customerId":12268,
         * "mobile":"17865202474","iconUrl":"http://thirdwx.qlogo
         * .cn/mmopen/vi_32/QBRibaicGLY6scAmHricZhIR5tYCbkhW91OPa5mgXwW7IRdfGs8J61Tl2jLjdt8nucnck1ibeObYcFp6DGR2arAPng/132",
         * "lastLoginDate":"2019-08-17 09:40:49","memberRank_id":0},{"fans_count":0,"createdDate":"2019-08-17 14:09:48",
         * "bigBearNumber":"554804755","nickName":"宇宙最可爱v","customerId":12270,"mobile":"17691214223","iconUrl":"http://thirdwx.qlogo
         * .cn/mmopen/vi_32/DYAIOgq83ept6UvMIWKZIp0T9IGO7vicyvhiauqJEPt9qQmIq8zMI18GLtIBwQPN7I2uiag9WD9ymLHKu0Bp3zib9w/132",
         * "lastLoginDate":"2019-08-17 14:09:48","memberRank_id":0},{"fans_count":1,"createdDate":"2019-12-02 09:38:11",
         * "bigBearNumber":"997921461","nickName":"大熊用户997921461","customerId":12379,"mobile":"1856820020655",
         * "iconUrl":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/20180816140417.png","lastLoginDate":"2019-12-02
         * 09:38:11","memberRank_id":0}]
         */

        private int size;
        private List<ListBean> list;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * fans_count : 2
             * createdDate : 2019-08-17 09:40:49
             * bigBearNumber : 617801472
             * nickName : 幸  好
             * customerId : 12268
             * mobile : 17865202474
             * iconUrl : http://thirdwx.qlogo
             * .cn/mmopen/vi_32/QBRibaicGLY6scAmHricZhIR5tYCbkhW91OPa5mgXwW7IRdfGs8J61Tl2jLjdt8nucnck1ibeObYcFp6DGR2arAPng/132
             * lastLoginDate : 2019-08-17 09:40:49
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

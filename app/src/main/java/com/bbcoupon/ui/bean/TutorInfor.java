package com.bbcoupon.ui.bean;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.bean
 * @DATE 2020/4/23
 */
public class TutorInfor {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"weixin":"sdf3321","wxQrcode":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202003/48faeaa0-27ba-4eb0-8029-586c97b16f5a_medium.jpg"}
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
         * weixin : sdf3321
         * wxQrcode : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202003/48faeaa0-27ba-4eb0-8029-586c97b16f5a_medium.jpg
         */

        private String weixin;
        private String wxQrcode;

        public String getWeixin() {
            return weixin;
        }

        public void setWeixin(String weixin) {
            this.weixin = weixin;
        }

        public String getWxQrcode() {
            return wxQrcode;
        }

        public void setWxQrcode(String wxQrcode) {
            this.wxQrcode = wxQrcode;
        }
    }
}

package com.bbcoupon.ui.bean;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.bean
 * @DATE 2020/5/18
 */
public class WXInfor {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"weixin":"weqweqw123","wxQrcode":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202005/a63b1475-f62f-4c86-8c58-78719cb9feb5_medium.jpg"}
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
         * weixin : weqweqw123
         * wxQrcode : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202005/a63b1475-f62f-4c86-8c58-78719cb9feb5_medium.jpg
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

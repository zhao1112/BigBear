package com.newversions.tbk.entity;

import java.io.Serializable;

public class ShareGoodsEntity implements Serializable {

    /**
     * msg : 请求成功
     * taoQcodeUrl : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/shareproductimage/2019-07-24.jpg
     * code : 1
     * taoToken : https://s.click.taobao.com/ZXs7t4w
     */

    private String msg;
    private String taoQcodeUrl;
    private int code;
    private String taoToken;
    private String shareReason;

    public String getShareReason() {
        return shareReason;
    }

    public void setShareReason(String shareReason) {
        this.shareReason = shareReason;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTaoQcodeUrl() {
        return taoQcodeUrl;
    }

    public void setTaoQcodeUrl(String taoQcodeUrl) {
        this.taoQcodeUrl = taoQcodeUrl;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTaoToken() {
        return taoToken;
    }

    public void setTaoToken(String taoToken) {
        this.taoToken = taoToken;
    }
}

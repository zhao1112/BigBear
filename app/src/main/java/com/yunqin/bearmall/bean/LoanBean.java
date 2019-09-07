package com.yunqin.bearmall.bean;

/**
 * @author AYWang
 * @create 2019/4/18
 * @Describe
 */
public class LoanBean {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"linkSite":"http://39.105.212.10:91/?channel=dxsc&MemberId=15"}
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
         * linkSite : http://39.105.212.10:91/?channel=dxsc&MemberId=15
         */

        private String linkSite;

        public String getLinkSite() {
            return linkSite;
        }

        public void setLinkSite(String linkSite) {
            this.linkSite = linkSite;
        }
    }
}

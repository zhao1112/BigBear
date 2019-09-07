package com.yunqin.bearmall.bean;

public class ImageCode {

    /**
     * data : {"vCod":"V8SD"}
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
         * vCod : V8SD
         */

        private String vCod;

        public String getVCod() {
            return vCod;
        }

        public void setVCod(String vCod) {
            this.vCod = vCod;
        }
    }
}

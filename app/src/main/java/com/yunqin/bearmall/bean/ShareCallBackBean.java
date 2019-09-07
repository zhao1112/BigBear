package com.yunqin.bearmall.bean;

/**
 * @author AYWang
 * @create 2018/8/25
 * @Describe
 */
public class ShareCallBackBean {

    /**
     * data : {"value":10,"isReward":1}
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
         * value : 10
         * isReward : 1
         */

        private int value;
        private int isReward;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getIsReward() {
            return isReward;
        }

        public void setIsReward(int isReward) {
            this.isReward = isReward;
        }
    }
}

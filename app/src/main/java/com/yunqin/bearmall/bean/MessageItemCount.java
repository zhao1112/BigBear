package com.yunqin.bearmall.bean;

/**
 * @author AYWang
 * @create 2018/8/20
 * @Describe
 */
public class MessageItemCount {

    /**
     * data : {"unreadMessageCount":9}
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
         * unreadMessageCount : 9
         */

        private int unreadMessageCount;

        public int getUnreadMessageCount() {
            return unreadMessageCount;
        }

        public void setUnreadMessageCount(int unreadMessageCount) {
            this.unreadMessageCount = unreadMessageCount;
        }
    }
}

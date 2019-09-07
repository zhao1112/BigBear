package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * Created by chenchen on 2018/8/13.
 */

public class BCMessageData {

    private int code;
    private String msg;
    private BCMessageDataBean data;

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

    public BCMessageDataBean getData() {
        return data;
    }

    public void setData(BCMessageDataBean data) {
        this.data = data;
    }

    public static class BCMessageDataBean{

        private int has_more;

        private List<BCMessage> list;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<BCMessage> getList() {
            return list;
        }

        public void setList(List<BCMessage> list) {
            this.list = list;
        }
    }


}

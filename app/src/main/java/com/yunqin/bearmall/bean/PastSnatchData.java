package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * Created by chenchen on 2018/8/25.
 */

public class PastSnatchData {

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{

        private int has_more;
        private List<Treasure> pastRewardList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<Treasure> getPastRewardList() {
            return pastRewardList;
        }

        public void setPastRewardList(List<Treasure> pastRewardList) {
            this.pastRewardList = pastRewardList;
        }
    }

}

package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * Created by chenchen on 2018/8/14.
 */

public class TreasureData {


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

        private List<TreasureTag> treasureTagInfo;

        public List<TreasureTag> getTreasureList() {
            return treasureTagInfo;
        }

        public void setTreasureList(List<TreasureTag> treasureTagInfo) {
            this.treasureTagInfo = treasureTagInfo;
        }
    }

    public static class TreasureTag{

        private String time;
        private int tag;
        private int orders;
        private int tagStatus;
        private boolean isToday;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public int getOrders() {
            return orders;
        }

        public void setOrders(int orders) {
            this.orders = orders;
        }

        public int getTagStatus() {
            return tagStatus;
        }

        public void setTagStatus(int tagStatus) {
            this.tagStatus = tagStatus;
        }

        public boolean isToday() {
            return isToday;
        }

        public void setToday(boolean today) {
            isToday = today;
        }
    }

}

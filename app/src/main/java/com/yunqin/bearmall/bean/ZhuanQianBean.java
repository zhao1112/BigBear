package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * Create By Master
 * On 2019/5/28 16:19
 */
public class ZhuanQianBean {

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
        private List<DrawActivityInfoBean> drawActivityInfo;

        public List<DrawActivityInfoBean> getDrawActivityInfo() {
            return drawActivityInfo;
        }

        public void setDrawActivityInfo(List<DrawActivityInfoBean> drawActivityInfo) {
            this.drawActivityInfo = drawActivityInfo;
        }

        public static class DrawActivityInfoBean {

            private int times;
            private String img;
            private int isReward;
            private String name;
            private int count;
            private int isFinish;
            private int raffleConfig_id;
            private int btAmount;

            public int getTimes() {
                return times;
            }

            public void setTimes(int times) {
                this.times = times;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getIsReward() {
                return isReward;
            }

            public void setIsReward(int isReward) {
                this.isReward = isReward;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getIsFinish() {
                return isFinish;
            }

            public void setIsFinish(int isFinish) {
                this.isFinish = isFinish;
            }

            public int getRaffleConfig_id() {
                return raffleConfig_id;
            }

            public void setRaffleConfig_id(int raffleConfig_id) {
                this.raffleConfig_id = raffleConfig_id;
            }

            public int getBtAmount() {
                return btAmount;
            }

            public void setBtAmount(int btAmount) {
                this.btAmount = btAmount;
            }
        }
    }
}

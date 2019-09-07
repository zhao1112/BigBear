package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * Created by chenchen on 2018/8/9.
 */

public class TreasureInfo {


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

        private Treasure treasure;

        private List<RewardMember> rewardMember;

        public Treasure getTreasure() {
            return treasure;
        }

        public void setTreasure(Treasure treasure) {
            this.treasure = treasure;
        }

        public List<RewardMember> getRewardMember() {
            return rewardMember;
        }

        public void setRewardMember(List<RewardMember> rewardMember) {
            this.rewardMember = rewardMember;
        }
    }



    public static class RewardMember{

        private String reward;
        private String nickName;
        private String treasureName;

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getTreasureName() {
            return treasureName;
        }

        public void setTreasureName(String treasureName) {
            this.treasureName = treasureName;
        }

    }

}

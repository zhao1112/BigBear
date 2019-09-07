package com.yunqin.bearmall.bean;

/**
 * @author AYWang
 * @create 2018/7/25
 * @Describe
 */
public class InvitationBean {

    /**
     * data : {"M1Count":0,"inviteTotalReward":"0.00","M2RewardRatio":0.2,"M2Count":0,"M1RewardValue":20}
     * inviteUrl : http://localhost:8080/api/member/10652
     * code : 1
     * msg : 请求成功
     */

    private DataBean data;
    private String inviteUrl;
    private int code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getInviteUrl() {
        return inviteUrl;
    }

    public void setInviteUrl(String inviteUrl) {
        this.inviteUrl = inviteUrl;
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
         * M1Count : 0
         * inviteTotalReward : 0.00
         * M2RewardRatio : 0.2
         * M2Count : 0
         * M1RewardValue : 20
         */

        private int M1Count;
        private String inviteTotalReward;
        private double M2RewardRatio;
        private int M2Count;
        private int M1RewardValue;

        public int getM1Count() {
            return M1Count;
        }

        public void setM1Count(int M1Count) {
            this.M1Count = M1Count;
        }

        public String getInviteTotalReward() {
            return inviteTotalReward;
        }

        public void setInviteTotalReward(String inviteTotalReward) {
            this.inviteTotalReward = inviteTotalReward;
        }

        public double getM2RewardRatio() {
            return M2RewardRatio;
        }

        public void setM2RewardRatio(double M2RewardRatio) {
            this.M2RewardRatio = M2RewardRatio;
        }

        public int getM2Count() {
            return M2Count;
        }

        public void setM2Count(int M2Count) {
            this.M2Count = M2Count;
        }

        public int getM1RewardValue() {
            return M1RewardValue;
        }

        public void setM1RewardValue(int M1RewardValue) {
            this.M1RewardValue = M1RewardValue;
        }
    }
}

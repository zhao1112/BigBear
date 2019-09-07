package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/7/25
 * @Describe
 */
public class SignInPage {

    /**
     * data : {"rewardList":[{"isSign":0,"ticketId":"2","name":"usersRegisterReward0","isRewardTicket":"1","rewardCount":"10"},{"isSign":0,"ticketId":"2","name":"usersRegisterReward1","isRewardTicket":"0","rewardCount":"20"},{"isSign":0,"ticketId":"2","name":"usersRegisterReward2","isRewardTicket":"0","rewardCount":"30"},{"isSign":0,"ticketId":"3","name":"usersRegisterReward3","isRewardTicket":"0","rewardCount":"40"},{"isSign":0,"ticketId":"3","name":"usersRegisterReward4","isRewardTicket":"0","rewardCount":"50"},{"isSign":0,"ticketId":"2","name":"usersRegisterReward5","isRewardTicket":"0","rewardCount":"60"},{"isSign":0,"ticketId":"2","name":"usersRegisterReward6","isRewardTicket":"0","rewardCount":"70"}],"isSignToday":0}
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
         * rewardList : [{"isSign":0,"ticketId":"2","name":"usersRegisterReward0","isRewardTicket":"1","rewardCount":"10"},{"isSign":0,"ticketId":"2","name":"usersRegisterReward1","isRewardTicket":"0","rewardCount":"20"},{"isSign":0,"ticketId":"2","name":"usersRegisterReward2","isRewardTicket":"0","rewardCount":"30"},{"isSign":0,"ticketId":"3","name":"usersRegisterReward3","isRewardTicket":"0","rewardCount":"40"},{"isSign":0,"ticketId":"3","name":"usersRegisterReward4","isRewardTicket":"0","rewardCount":"50"},{"isSign":0,"ticketId":"2","name":"usersRegisterReward5","isRewardTicket":"0","rewardCount":"60"},{"isSign":0,"ticketId":"2","name":"usersRegisterReward6","isRewardTicket":"0","rewardCount":"70"}]
         * isSignToday : 0
         */

        private int isSignToday;
        private List<RewardListBean> rewardList;

        public int getIsSignToday() {
            return isSignToday;
        }

        public void setIsSignToday(int isSignToday) {
            this.isSignToday = isSignToday;
        }

        public List<RewardListBean> getRewardList() {
            return rewardList;
        }

        public void setRewardList(List<RewardListBean> rewardList) {
            this.rewardList = rewardList;
        }

        public static class RewardListBean {
            /**
             * isSign : 0
             * ticketId : 2
             * name : usersRegisterReward0
             * isRewardTicket : 1
             * rewardCount : 10
             */

            private int isSign;
            private String ticketId;
            private String name;
            private String isRewardTicket;
            private String rewardCount;

            public int getIsSign() {
                return isSign;
            }

            public void setIsSign(int isSign) {
                this.isSign = isSign;
            }

            public String getTicketId() {
                return ticketId;
            }

            public void setTicketId(String ticketId) {
                this.ticketId = ticketId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIsRewardTicket() {
                return isRewardTicket;
            }

            public void setIsRewardTicket(String isRewardTicket) {
                this.isRewardTicket = isRewardTicket;
            }

            public String getRewardCount() {
                return rewardCount;
            }

            public void setRewardCount(String rewardCount) {
                this.rewardCount = rewardCount;
            }
        }
    }
}

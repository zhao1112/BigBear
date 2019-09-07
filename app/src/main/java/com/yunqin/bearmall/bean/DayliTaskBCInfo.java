package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/8/14
 * @Describe
 */
public class DayliTaskBCInfo {



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

        private RewardDetailsBean rewardDetails;
        private AdRecordBean adRecord;
        private int isSignToday;

        public RewardDetailsBean getRewardDetails() {
            return rewardDetails;
        }

        public void setRewardDetails(RewardDetailsBean rewardDetails) {
            this.rewardDetails = rewardDetails;
        }

        public AdRecordBean getAdRecord() {
            return adRecord;
        }

        public void setAdRecord(AdRecordBean adRecord) {
            this.adRecord = adRecord;
        }

        public int getIsSignToday() {
            return isSignToday;
        }

        public void setIsSignToday(int isSignToday) {
            this.isSignToday = isSignToday;
        }

        public static class RewardDetailsBean {

            private String usersRegisterRewardMax;
            private String todayRewardMoney;
            private String balance;
            private String bCbanlance;
            private String todayCreditSum;
            private String memberShareInfoReward;


            private String friendInvitReward;
            private String memberShareProduct;

            public String getMemberShareProduct() {
                return memberShareProduct;
            }

            public void setMemberShareProduct(String memberShareProduct) {
                this.memberShareProduct = memberShareProduct;
            }

            public String getFriendInvitReward() {
                return friendInvitReward;
            }

            public void setFriendInvitReward(String friendInvitReward) {
                this.friendInvitReward = friendInvitReward;
            }

            private String restReward;


            public String getRestReward() {
                return restReward;
            }

            public void setRestReward(String restReward) {
                this.restReward = restReward;
            }

            public String getUsersRegisterRewardMax() {
                return usersRegisterRewardMax;
            }

            public void setUsersRegisterRewardMax(String usersRegisterRewardMax) {
                this.usersRegisterRewardMax = usersRegisterRewardMax;
            }

            public String getTodayRewardMoney() {
                return todayRewardMoney;
            }

            public void setTodayRewardMoney(String todayRewardMoney) {
                this.todayRewardMoney = todayRewardMoney;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getBCbanlance() {
                return bCbanlance;
            }

            public void setBCbanlance(String bCbanlance) {
                this.bCbanlance = bCbanlance;
            }

            public String getTodayCreditSum() {
                return todayCreditSum;
            }

            public void setTodayCreditSum(String todayCreditSum) {
                this.todayCreditSum = todayCreditSum;
            }

            public String getMemberShareInfoReward() {
                return memberShareInfoReward;
            }

            public void setMemberShareInfoReward(String memberShareInfoReward) {
                this.memberShareInfoReward = memberShareInfoReward;
            }
        }

        public static class AdRecordBean {
            private List<AdEarnMoneyListBean> adEarnMoneyList1;
            private List<AdEarnMoneyListBean> adEarnMoneyList2;
            private List<AdEarnMoneyListBean> adEarnMoneyList3;
            private List<AdEarnMoneyListBean> adEarnMoneyList4;
            private List<AdEarnMoneyListBean> adEarnMoneyList5;

            public List<AdEarnMoneyListBean> getAdEarnMoneyList1() {
                return adEarnMoneyList1;
            }

            public void setAdEarnMoneyList1(List<AdEarnMoneyListBean> adEarnMoneyList1) {
                this.adEarnMoneyList1 = adEarnMoneyList1;
            }

            public List<AdEarnMoneyListBean> getAdEarnMoneyList2() {
                return adEarnMoneyList2;
            }

            public void setAdEarnMoneyList2(List<AdEarnMoneyListBean> adEarnMoneyList2) {
                this.adEarnMoneyList2 = adEarnMoneyList2;
            }

            public List<AdEarnMoneyListBean> getAdEarnMoneyList3() {
                return adEarnMoneyList3;
            }

            public void setAdEarnMoneyList3(List<AdEarnMoneyListBean> adEarnMoneyList3) {
                this.adEarnMoneyList3 = adEarnMoneyList3;
            }

            public List<AdEarnMoneyListBean> getAdEarnMoneyList4() {
                return adEarnMoneyList4;
            }

            public void setAdEarnMoneyList4(List<AdEarnMoneyListBean> adEarnMoneyList4) {
                this.adEarnMoneyList4 = adEarnMoneyList4;
            }

            public List<AdEarnMoneyListBean> getAdEarnMoneyList5() {
                return adEarnMoneyList5;
            }

            public void setAdEarnMoneyList5(List<AdEarnMoneyListBean> adEarnMoneyList5) {
                this.adEarnMoneyList5 = adEarnMoneyList5;
            }

            public static class AdEarnMoneyListBean {

                private int type;
                private String img;
                private int orders;
                private long source_id;
                private int skipType;
                private String name;

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public int getOrders() {
                    return orders;
                }

                public void setOrders(int orders) {
                    this.orders = orders;
                }

                public long getSource_id() {
                    return source_id;
                }

                public void setSource_id(long source_id) {
                    this.source_id = source_id;
                }

                public int getSkipType() {
                    return skipType;
                }

                public void setSkipType(int skipType) {
                    this.skipType = skipType;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

        }
    }
}

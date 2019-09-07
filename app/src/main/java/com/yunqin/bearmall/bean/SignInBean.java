package com.yunqin.bearmall.bean;

/**
 * @author AYWang
 * @create 2018/7/25
 * @Describe
 */
public class SignInBean {

    /**
     * data : {"registerRanking":3,"registerStatus":2,"usersRegisterInfo":{"userRegister_id":21,"reward":10,"registerDate":"2018-07-25 15:10:45","serialNumber":1,"isRewardTicket":true},"registerTotalCount":1,"virtualCoinPicture":"http://image.demo.b2b2c.shopxx.net/6.0/3de84479-78db-4c1b-aadc-af90fac6c5bd.png","virtualCoinName":"虚拟币名称11"}
     * code : 1
     * msg : 今日已签到
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
         * registerRanking : 3
         * registerStatus : 2
         * usersRegisterInfo : {"userRegister_id":21,"reward":10,"registerDate":"2018-07-25 15:10:45","serialNumber":1,"isRewardTicket":true}
         * registerTotalCount : 1
         * virtualCoinPicture : http://image.demo.b2b2c.shopxx.net/6.0/3de84479-78db-4c1b-aadc-af90fac6c5bd.png
         * virtualCoinName : 虚拟币名称11
         */

        private int registerRanking;
        private int registerStatus;
        private UsersRegisterInfoBean usersRegisterInfo;
        private int registerTotalCount;
        private String virtualCoinPicture;
        private String virtualCoinName;

        public int getRegisterRanking() {
            return registerRanking;
        }

        public void setRegisterRanking(int registerRanking) {
            this.registerRanking = registerRanking;
        }

        public int getRegisterStatus() {
            return registerStatus;
        }

        public void setRegisterStatus(int registerStatus) {
            this.registerStatus = registerStatus;
        }

        public UsersRegisterInfoBean getUsersRegisterInfo() {
            return usersRegisterInfo;
        }

        public void setUsersRegisterInfo(UsersRegisterInfoBean usersRegisterInfo) {
            this.usersRegisterInfo = usersRegisterInfo;
        }

        public int getRegisterTotalCount() {
            return registerTotalCount;
        }

        public void setRegisterTotalCount(int registerTotalCount) {
            this.registerTotalCount = registerTotalCount;
        }

        public String getVirtualCoinPicture() {
            return virtualCoinPicture;
        }

        public void setVirtualCoinPicture(String virtualCoinPicture) {
            this.virtualCoinPicture = virtualCoinPicture;
        }

        public String getVirtualCoinName() {
            return virtualCoinName;
        }

        public void setVirtualCoinName(String virtualCoinName) {
            this.virtualCoinName = virtualCoinName;
        }

        public static class UsersRegisterInfoBean {
            /**
             * userRegister_id : 21
             * reward : 10
             * registerDate : 2018-07-25 15:10:45
             * serialNumber : 1
             * isRewardTicket : true
             */

            private int userRegister_id;
            private int reward;
            private String registerDate;
            private int serialNumber;
            private boolean isRewardTicket;

            public int getUserRegister_id() {
                return userRegister_id;
            }

            public void setUserRegister_id(int userRegister_id) {
                this.userRegister_id = userRegister_id;
            }

            public int getReward() {
                return reward;
            }

            public void setReward(int reward) {
                this.reward = reward;
            }

            public String getRegisterDate() {
                return registerDate;
            }

            public void setRegisterDate(String registerDate) {
                this.registerDate = registerDate;
            }

            public int getSerialNumber() {
                return serialNumber;
            }

            public void setSerialNumber(int serialNumber) {
                this.serialNumber = serialNumber;
            }

            public boolean isIsRewardTicket() {
                return isRewardTicket;
            }

            public void setIsRewardTicket(boolean isRewardTicket) {
                this.isRewardTicket = isRewardTicket;
            }
        }
    }
}

package com.yunqin.bearmall.bean;


/**
 * @author AYWang
 * @create 2018/7/18
 * @Describe
 */
public class UserInfo {

    /**
     * data : {"member":{"bigBearNumber":"532711651","nickName":"大熊用户532711651","iconUrl":"","rankTitle":"大熊先锋"},"access_token":"4f69216bd28f8b0cb04f9ebaafc9d7cd"}
     * code : 1
     * msg : 登录成功
     */

    private DataBean data;
    private int code;
    private String msg;
    private String recommendCode;//自己的邀请码
    private String parentCode;//上级的邀请码，自己填的邀请码

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

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
         * member : {"bigBearNumber":"532711651","nickName":"大熊用户532711651","iconUrl":"","rankTitle":"大熊先锋"}
         * access_token : 4f69216bd28f8b0cb04f9ebaafc9d7cd
         */

        private MemberBean member;
        private TokenBean token;
        private int isFirstLogin;
        private String firstLoginReward;

        public String getFirstLoginReward() {
            return firstLoginReward;
        }

        public void setFirstLoginReward(String firstLoginReward) {
            this.firstLoginReward = firstLoginReward;
        }

        public int getIsFirstLogin() {
            return isFirstLogin;
        }

        public void setIsFirstLogin(int isFirstLogin) {
            this.isFirstLogin = isFirstLogin;
        }

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public TokenBean getToken() {
            return token;
        }

        public void setToken(TokenBean token) {
            this.token = token;
        }

        public static class MemberBean {
            /**
             * bigBearNumber : 532711651
             * nickName : 大熊用户532711651
             * iconUrl :
             * rankTitle : 大熊先锋
             */

            private String bigBearNumber;
            private String nickName;
            private String iconUrl;
            private String rankTitle;

            private String mobile;
            private boolean isMember;
            private boolean isOpendMember;
            private int restPrivilegeCount;
            private int restDays;
            private String endDate;
            private boolean  isHasSpecInvite;
            private int specInviteUsableCount;

            private boolean isBindWxopenId;
            private boolean isBindWx;



            public boolean isBindWxopenId() {
                return isBindWxopenId;
            }

            public void setBindWxopenId(boolean bindWxopenId) {
                isBindWxopenId = bindWxopenId;
            }

            public boolean isBindWx() {
                return isBindWx;
            }

            public void setBindWx(boolean bindWx) {
                isBindWx = bindWx;
            }

            public boolean isMember() {
                return isMember;
            }

            public void setMember(boolean member) {
                isMember = member;
            }

            public boolean isOpendMember() {
                return isOpendMember;
            }

            public void setOpendMember(boolean opendMember) {
                isOpendMember = opendMember;
            }

            public int getRestPrivilegeCount() {
                return restPrivilegeCount;
            }

            public void setRestPrivilegeCount(int restPrivilegeCount) {
                this.restPrivilegeCount = restPrivilegeCount;
            }

            public int getRestDays() {
                return restDays;
            }

            public void setRestDays(int restDays) {
                this.restDays = restDays;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public boolean isHasSpecInvite() {
                return isHasSpecInvite;
            }

            public void setHasSpecInvite(boolean hasSpecInvite) {
                isHasSpecInvite = hasSpecInvite;
            }

            public int getSpecInviteUsableCount() {
                return specInviteUsableCount;
            }

            public void setSpecInviteUsableCount(int specInviteUsableCount) {
                this.specInviteUsableCount = specInviteUsableCount;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getBigBearNumber() {
                return bigBearNumber;
            }

            public void setBigBearNumber(String bigBearNumber) {
                this.bigBearNumber = bigBearNumber;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getRankTitle() {
                return rankTitle;
            }

            public void setRankTitle(String rankTitle) {
                this.rankTitle = rankTitle;
            }
        }

        public static class TokenBean{

            /**
             * access_token : eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMTg3NyIsImV4cCI6MTU0NTk3MzczMSwic3ViIjoie1widWlkXCI6XCIxMTg3N1wifSIsImp0aSI6ImM4N2IyMmFkYzY4OGUyMTFmNzQ2YmVlYjUwZDA3NTI3IiwiaWF0IjoxNTQ1OTY2NTMxfQ.j9Sc_Z4P63nAW0aZx5iG3k08WgGpd0BRFIbH3R8Rgf4
             * timestamp : 1545966531852
             * expire : 7200
             * refresh_token : c87b22adc688e211f746beeb50d07527
             */

            private String access_token;
            private long timestamp;
            private int expire;
            private String refresh_token;

            public String getAccess_token() {
                return access_token;
            }

            public void setAccess_token(String access_token) {
                this.access_token = access_token;
            }

            public long getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(long timestamp) {
                this.timestamp = timestamp;
            }

            public int getExpire() {
                return expire;
            }

            public void setExpire(int expire) {
                this.expire = expire;
            }

            public String getRefresh_token() {
                return refresh_token;
            }

            public void setRefresh_token(String refresh_token) {
                this.refresh_token = refresh_token;
            }
        }

    }
}

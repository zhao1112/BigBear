package com.yunqin.bearmall.bean;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.bean
 * @DATE 2020/1/10
 */
public class UserPromotion {

    private String msg;
    private int code;
    private int type;
    private Data data;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        //通用
        private String iconUrl;
        private String nickName;
        private String parentIconUrl;
        private String parentNickName;
        private String parentInviteCode;
        private String parentRole;
        private String parentWXCode;
        private String parentWXId;
        //注册会员
        private int totalNumber;
        private int fansNum;
        private int orderNumber;
        private int orderIsDisplay;
        private int orderTotalNumber;
        private int checkWXState;
        private int weChatGroupNumber;
        private int isDisplay1;
        private int isDisplay2;
        //超级会员
        private int twoLevelFans;
        private int twoLevelTotalNumber;
        private int invitationIsDisplay;
        private int activityLevel;
        private int activityLevelTotalNumber;
        private int activityIsDisplay;
        private int isDisplay;
        //大团长v1
        private int superMemNumber;
        private int teamSuperMemberTotal;
        //大团长v2
        //大团长v3
        //大团长v4
        private int ClassAVipNum;

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getParentIconUrl() {
            return parentIconUrl;
        }

        public void setParentIconUrl(String parentIconUrl) {
            this.parentIconUrl = parentIconUrl;
        }

        public String getParentNickName() {
            return parentNickName;
        }

        public void setParentNickName(String parentNickName) {
            this.parentNickName = parentNickName;
        }

        public String getParentInviteCode() {
            return parentInviteCode;
        }

        public void setParentInviteCode(String parentInviteCode) {
            this.parentInviteCode = parentInviteCode;
        }

        public String getParentRole() {
            return parentRole;
        }

        public void setParentRole(String parentRole) {
            this.parentRole = parentRole;
        }

        public String getParentWXCode() {
            return parentWXCode;
        }

        public void setParentWXCode(String parentWXCode) {
            this.parentWXCode = parentWXCode;
        }

        public String getParentWXId() {
            return parentWXId;
        }

        public void setParentWXId(String parentWXId) {
            this.parentWXId = parentWXId;
        }

        public int getTotalNumber() {
            return totalNumber;
        }

        public void setTotalNumber(int totalNumber) {
            this.totalNumber = totalNumber;
        }

        public int getFansNum() {
            return fansNum;
        }

        public void setFansNum(int fansNum) {
            this.fansNum = fansNum;
        }

        public int getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(int orderNumber) {
            this.orderNumber = orderNumber;
        }

        public int getOrderIsDisplay() {
            return orderIsDisplay;
        }

        public void setOrderIsDisplay(int orderIsDisplay) {
            this.orderIsDisplay = orderIsDisplay;
        }

        public int getOrderTotalNumber() {
            return orderTotalNumber;
        }

        public void setOrderTotalNumber(int orderTotalNumber) {
            this.orderTotalNumber = orderTotalNumber;
        }

        public int getCheckWXState() {
            return checkWXState;
        }

        public void setCheckWXState(int checkWXState) {
            this.checkWXState = checkWXState;
        }

        public int getWeChatGroupNumber() {
            return weChatGroupNumber;
        }

        public void setWeChatGroupNumber(int weChatGroupNumber) {
            this.weChatGroupNumber = weChatGroupNumber;
        }

        public int getIsDisplay1() {
            return isDisplay1;
        }

        public void setIsDisplay1(int isDisplay1) {
            this.isDisplay1 = isDisplay1;
        }

        public int getIsDisplay2() {
            return isDisplay2;
        }

        public void setIsDisplay2(int isDisplay2) {
            this.isDisplay2 = isDisplay2;
        }

        public int getTwoLevelFans() {
            return twoLevelFans;
        }

        public void setTwoLevelFans(int twoLevelFans) {
            this.twoLevelFans = twoLevelFans;
        }

        public int getTwoLevelTotalNumber() {
            return twoLevelTotalNumber;
        }

        public void setTwoLevelTotalNumber(int twoLevelTotalNumber) {
            this.twoLevelTotalNumber = twoLevelTotalNumber;
        }

        public int getInvitationIsDisplay() {
            return invitationIsDisplay;
        }

        public void setInvitationIsDisplay(int invitationIsDisplay) {
            this.invitationIsDisplay = invitationIsDisplay;
        }

        public int getActivityLevel() {
            return activityLevel;
        }

        public void setActivityLevel(int activityLevel) {
            this.activityLevel = activityLevel;
        }

        public int getActivityLevelTotalNumber() {
            return activityLevelTotalNumber;
        }

        public void setActivityLevelTotalNumber(int activityLevelTotalNumber) {
            this.activityLevelTotalNumber = activityLevelTotalNumber;
        }

        public int getActivityIsDisplay() {
            return activityIsDisplay;
        }

        public void setActivityIsDisplay(int activityIsDisplay) {
            this.activityIsDisplay = activityIsDisplay;
        }

        public int getIsDisplay() {
            return isDisplay;
        }

        public void setIsDisplay(int isDisplay) {
            this.isDisplay = isDisplay;
        }

        public int getSuperMemNumber() {
            return superMemNumber;
        }

        public void setSuperMemNumber(int superMemNumber) {
            this.superMemNumber = superMemNumber;
        }

        public int getTeamSuperMemberTotal() {
            return teamSuperMemberTotal;
        }

        public void setTeamSuperMemberTotal(int teamSuperMemberTotal) {
            this.teamSuperMemberTotal = teamSuperMemberTotal;
        }

        public int getClassAVipNum() {
            return ClassAVipNum;
        }

        public void setClassAVipNum(int classAVipNum) {
            ClassAVipNum = classAVipNum;
        }
    }
}


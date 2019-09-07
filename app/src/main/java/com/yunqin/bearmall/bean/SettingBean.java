package com.yunqin.bearmall.bean;

/**
 * @author AYWang
 * @create 2018/8/20
 * @Describe
 */
public class SettingBean {
    /**
     * data : {"info":{"bigBearNumber":"167385278","rankName":"V1","nickName":"大熊用户167385278","iconUrl":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/ef3da5bf-e3ed-4a00-99ae-9da85c0feeb0.jpg","wxPublicNumber":"bbbearmall","isBindWx":1,"rankTitle":"大熊先锋","isBindQq":0,"mobile":"17801118515"}}
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
         * info : {"bigBearNumber":"167385278","rankName":"V1","nickName":"大熊用户167385278","iconUrl":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/ef3da5bf-e3ed-4a00-99ae-9da85c0feeb0.jpg","wxPublicNumber":"bbbearmall","isBindWx":1,"rankTitle":"大熊先锋","isBindQq":0,"mobile":"17801118515"}
         */

        private InfoBean info;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * bigBearNumber : 167385278
             * rankName : V1
             * nickName : 大熊用户167385278
             * iconUrl : https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201807/ef3da5bf-e3ed-4a00-99ae-9da85c0feeb0.jpg
             * wxPublicNumber : bbbearmall
             * isBindWx : 1
             * rankTitle : 大熊先锋
             * isBindQq : 0
             * mobile : 17801118515
             */

            private boolean IsMember;
            private boolean isSetPayPwd;
            private String bigBearNumber;
            private String rankName;
            private String nickName;
            private String iconUrl;
            private String wxPublicNumber;
            private int isBindWx;
            private String rankTitle;
            private int isBindQq;
            private String mobile;


            public boolean isMember() {
                return IsMember;
            }

            public void setMember(boolean member) {
                IsMember = member;
            }

            public boolean isSetPayPwd() {
                return isSetPayPwd;
            }

            public void setSetPayPwd(boolean setPayPwd) {
                isSetPayPwd = setPayPwd;
            }

            public String getBigBearNumber() {
                return bigBearNumber;
            }

            public void setBigBearNumber(String bigBearNumber) {
                this.bigBearNumber = bigBearNumber;
            }

            public String getRankName() {
                return rankName;
            }

            public void setRankName(String rankName) {
                this.rankName = rankName;
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

            public String getWxPublicNumber() {
                return wxPublicNumber;
            }

            public void setWxPublicNumber(String wxPublicNumber) {
                this.wxPublicNumber = wxPublicNumber;
            }

            public int getIsBindWx() {
                return isBindWx;
            }

            public void setIsBindWx(int isBindWx) {
                this.isBindWx = isBindWx;
            }

            public String getRankTitle() {
                return rankTitle;
            }

            public void setRankTitle(String rankTitle) {
                this.rankTitle = rankTitle;
            }

            public int getIsBindQq() {
                return isBindQq;
            }

            public void setIsBindQq(int isBindQq) {
                this.isBindQq = isBindQq;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }
        }
    }
}

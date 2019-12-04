package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.bean
 * @DATE 2019/12/3
 */
public class SecondFans {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"list":[{"fans_count":0,"createdDate":"2019-07-29 11:15:43","bigBearNumber":"313871770",
     * "wxOpen_id":"oKPywszlPtTO4htUev6sx--s6ZU0","nickName":"周周","customerId":12254,"mobile":"17857690207","iconUrl":"http://thirdwx
     * .qlogo.cn/mmopen/vi_32/YtictVvlT9o3lzkcrKZHsMr1nkyr9IGge0lnAVbrp56rQZw9Pf6X31nhRlVr24vnOSMXgqTqIibbKBRuCQxsACRw/132",
     * "lastLoginDate":"2019-07-29 11:15:43","memberRank_id":0}]}
     */

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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * fans_count : 0
             * createdDate : 2019-07-29 11:15:43
             * bigBearNumber : 313871770
             * wxOpen_id : oKPywszlPtTO4htUev6sx--s6ZU0
             * nickName : 周周
             * customerId : 12254
             * mobile : 17857690207
             * iconUrl : http://thirdwx.qlogo
             * .cn/mmopen/vi_32/YtictVvlT9o3lzkcrKZHsMr1nkyr9IGge0lnAVbrp56rQZw9Pf6X31nhRlVr24vnOSMXgqTqIibbKBRuCQxsACRw/132
             * lastLoginDate : 2019-07-29 11:15:43
             * memberRank_id : 0
             */

            private int fans_count;
            private String createdDate;
            private String bigBearNumber;
            private String wxOpen_id;
            private String nickName;
            private int customerId;
            private String mobile;
            private String iconUrl;
            private String lastLoginDate;
            private int memberRank_id;

            public int getFans_count() {
                return fans_count;
            }

            public void setFans_count(int fans_count) {
                this.fans_count = fans_count;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public String getBigBearNumber() {
                return bigBearNumber;
            }

            public void setBigBearNumber(String bigBearNumber) {
                this.bigBearNumber = bigBearNumber;
            }

            public String getWxOpen_id() {
                return wxOpen_id;
            }

            public void setWxOpen_id(String wxOpen_id) {
                this.wxOpen_id = wxOpen_id;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getLastLoginDate() {
                return lastLoginDate;
            }

            public void setLastLoginDate(String lastLoginDate) {
                this.lastLoginDate = lastLoginDate;
            }

            public int getMemberRank_id() {
                return memberRank_id;
            }

            public void setMemberRank_id(int memberRank_id) {
                this.memberRank_id = memberRank_id;
            }
        }
    }
}

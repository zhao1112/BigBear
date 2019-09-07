package com.yunqin.bearmall.bean;

import java.util.List;

public class RenewVipResponse extends BaseResponseBean {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{

        private UserInfo.DataBean.MemberBean memberInfo;

        private List<VipItem> list;

        public UserInfo.DataBean.MemberBean getMemberInfo() {
            return memberInfo;
        }

        public void setMemberInfo(UserInfo.DataBean.MemberBean memberInfo) {
            this.memberInfo = memberInfo;
        }

        public List<VipItem> getList() {
            return list;
        }

        public void setList(List<VipItem> list) {
            this.list = list;
        }
    }

}

package com.yunqin.bearmall.bean;

public class MemberInfoResponse extends BaseResponseBean {

    private UserInfo.DataBean.MemberBean data;

    public UserInfo.DataBean.MemberBean getData() {
        return data;
    }

    public void setData(UserInfo.DataBean.MemberBean data) {
        this.data = data;
    }
}
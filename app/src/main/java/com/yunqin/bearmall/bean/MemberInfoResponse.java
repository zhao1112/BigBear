package com.yunqin.bearmall.bean;

public class MemberInfoResponse extends BaseResponseBean {

    private UserInfo.DataBean.MemberBean data;
    private UserInfo.Identity identity;

    public UserInfo.DataBean.MemberBean getData() {
        return data;
    }

    public void setData(UserInfo.DataBean.MemberBean data) {
        this.data = data;
    }

    public UserInfo.Identity getIdentity(){
        return identity;
    }

    public void setIdentity(UserInfo.Identity identity){
        this.identity = identity;
    }
}

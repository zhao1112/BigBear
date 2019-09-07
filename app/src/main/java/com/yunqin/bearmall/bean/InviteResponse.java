package com.yunqin.bearmall.bean;

public class InviteResponse extends BaseResponseBean {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{

        private InviteBean openMemberInfo;

        public InviteBean getOpenMemberInfo() {
            return openMemberInfo;
        }

        public void setOpenMemberInfo(InviteBean openMemberInfo) {
            this.openMemberInfo = openMemberInfo;
        }

    }

}

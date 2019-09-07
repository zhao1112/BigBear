package com.yunqin.bearmall.bean;

import java.util.List;

public class VipResponse extends BaseResponseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{

        private List<VipItem> list;

        public List<VipItem> getList() {
            return list;
        }

        public void setList(List<VipItem> list) {
            this.list = list;
        }
    }

}

package com.yunqin.bearmall.bean;

import java.util.List;

public class BalanceDetailResponse extends BaseResponseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private String balance;
        private int  has_more;
        private List<Balance> list;

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<Balance> getList() {
            return list;
        }

        public void setList(List<Balance> list) {
            this.list = list;
        }
    }

}

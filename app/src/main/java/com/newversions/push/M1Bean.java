package com.newversions.push;

import java.util.List;

/**
 * Create By Master
 * On 2019/1/19 13:52
 */
public class M1Bean {

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
        private List<M1ListBean> m1List;

        public List<M1ListBean> getM1List() {
            return m1List;
        }

        public void setM1List(List<M1ListBean> m1List) {
            this.m1List = m1List;
        }

        public static class M1ListBean {

            private String buyingTime;
            private String m1Mobile;
            private int m2Count;
            private String cashBack;

            public String getBuyingTime() {
                return buyingTime;
            }

            public void setBuyingTime(String buyingTime) {
                this.buyingTime = buyingTime;
            }

            public String getM1Mobile() {
                return m1Mobile;
            }

            public void setM1Mobile(String m1Mobile) {
                this.m1Mobile = m1Mobile;
            }

            public int getM2Count() {
                return m2Count;
            }

            public void setM2Count(int m2Count) {
                this.m2Count = m2Count;
            }

            public String getCashBack() {
                return cashBack;
            }

            public void setCashBack(String cashBack) {
                this.cashBack = cashBack;
            }
        }
    }








}

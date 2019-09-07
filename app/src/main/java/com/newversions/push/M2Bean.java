package com.newversions.push;

import java.util.List;

/**
 * Create By Master
 * On 2019/1/19 13:49
 */
public class M2Bean {

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
        private List<M2ListBean> m2List;

        public List<M2ListBean> getM2List() {
            return m2List;
        }

        public void setM2List(List<M2ListBean> m2List) {
            this.m2List = m2List;
        }

        public static class M2ListBean {

            private String m2Mobile;
            private String secCashBack;
            private String m1Mobile;

            public String getM2Mobile() {
                return m2Mobile;
            }

            public void setM2Mobile(String m2Mobile) {
                this.m2Mobile = m2Mobile;
            }

            public String getSecCashBack() {
                return secCashBack;
            }

            public void setSecCashBack(String secCashBack) {
                this.secCashBack = secCashBack;
            }

            public String getM1Mobile() {
                return m1Mobile;
            }

            public void setM1Mobile(String m1Mobile) {
                this.m1Mobile = m1Mobile;
            }
        }
    }
}

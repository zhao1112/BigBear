package com.yunqin.bearmall.bean;

public class FansPeopleAlwaysBean  {


    /**
     * msg : 请求成功
     * code : 1
     * data : {"bigLeader":1,"count":4,"superMem":3}
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
        /**
         * bigLeader : 1
         * count : 4
         * superMem : 3
         */

        private int bigLeader;
        private int count;
        private int superMem;

        public int getBigLeader() {
            return bigLeader;
        }

        public void setBigLeader(int bigLeader) {
            this.bigLeader = bigLeader;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getSuperMem() {
            return superMem;
        }

        public void setSuperMem(int superMem) {
            this.superMem = superMem;
        }
    }
}

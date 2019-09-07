package com.yunqin.bearmall.bean;

import java.util.List;

public class Channel {

    /**
     * data : [{"name":"手机数码","grade":0,"category_id":1},{"name":"办公家电","grade":0,"category_id":2},{"name":"精品服饰","grade":0,"category_id":3},{"name":"珠宝饰品","grade":0,"category_id":4},{"name":"美妆护肤","grade":0,"category_id":5},{"name":"生鲜绿植","grade":0,"category_id":6}]
     * code : 1
     * msg : 请求成功
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 手机数码
         * grade : 0
         * category_id : 1
         */

        private String name;
        private int grade;
        private int category_id;
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public int getCategory_id() {
            return category_id==0?getId():category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }
    }
}

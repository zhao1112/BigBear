package com.yunqin.bearmall.bean;

import java.util.List;

public class Address {

    private DataBean data;
    private int code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        private List<AreaListBean> areaList_0;
        private List<AreaListBean> areaList_1;
        private List<AreaListBean> areaList_2;

        public List<AreaListBean> getAreaList_0() {
            return areaList_0;
        }

        public void setAreaList_0(List<AreaListBean> areaList_0) {
            this.areaList_0 = areaList_0;
        }

        public List<AreaListBean> getAreaList_1() {
            return areaList_1;
        }

        public void setAreaList_1(List<AreaListBean> areaList_1) {
            this.areaList_1 = areaList_1;
        }

        public List<AreaListBean> getAreaList_2() {
            return areaList_2;
        }

        public void setAreaList_2(List<AreaListBean> areaList_2) {
            this.areaList_2 = areaList_2;
        }

        public static class AreaListBean {
            /**
             * area_id : 1346
             * areaName : 山东省
             * grade : 0
             * fullName : 山东省
             * parent_id : null
             */

            private int area_id;
            private String areaName;
            private int grade;
            private String fullName;
            private Long parent_id;

            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public String getAreaName() {
                return areaName;
            }

            public void setAreaName(String areaName) {
                this.areaName = areaName;
            }

            public int getGrade() {
                return grade;
            }

            public void setGrade(int grade) {
                this.grade = grade;
            }

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
            }

            public Long getParent_id() {
                return parent_id;
            }

            public void setParent_id(Long parent_id) {
                this.parent_id = parent_id;
            }
        }

    }
}

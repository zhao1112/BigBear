package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/7/12
 * @Describe
 */
public class Menu {

    /**
     * data : [{"subCategory":[{"subCategory":[{"name":"智能手机","grade":2,"category_id":55,"img":"http://xxxx","subCategory":null},{"name":"老人手机","grade":2,"img":"http://xxxx","category_id":56,"subCategory":null},{"name":"对讲机","grade":2,"category_id":57,"img":"http://xxxx","subCategory":null}],"name":"手机通讯","grade":1,"img":"http://xxxx","category_id":7}],"name":"手机数码","grade":0,"category_id":1}]
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
         * subCategory : [{"subCategory":[{"name":"智能手机","grade":2,"category_id":55,"img":"http://xxxx","subCategory":null},{"name":"老人手机","grade":2,"img":"http://xxxx","category_id":56,"subCategory":null},{"name":"对讲机","grade":2,"category_id":57,"img":"http://xxxx","subCategory":null}],"name":"手机通讯","grade":1,"img":"http://xxxx","category_id":7}]
         * name : 手机数码
         * grade : 0
         * category_id : 1
         */

        private String name;
        private int grade;
        private int category_id;
        private List<SubCategoryBeanX> subCategory;

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
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public List<SubCategoryBeanX> getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(List<SubCategoryBeanX> subCategory) {
            this.subCategory = subCategory;
        }

        public static class SubCategoryBeanX {
            /**
             * subCategory : [{"name":"智能手机","grade":2,"category_id":55,"img":"http://xxxx","subCategory":null},{"name":"老人手机","grade":2,"img":"http://xxxx","category_id":56,"subCategory":null},{"name":"对讲机","grade":2,"category_id":57,"img":"http://xxxx","subCategory":null}]
             * name : 手机通讯
             * grade : 1
             * img : http://xxxx
             * category_id : 7
             */

            private String name;
            private int grade;
            private String img;
            private int category_id;
            private List<SubCategoryBean> subCategory;

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

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public List<SubCategoryBean> getSubCategory() {
                return subCategory;
            }

            public void setSubCategory(List<SubCategoryBean> subCategory) {
                this.subCategory = subCategory;
            }

            public static class SubCategoryBean {
                /**
                 * name : 智能手机
                 * grade : 2
                 * category_id : 55
                 * img : http://xxxx
                 * subCategory : null
                 */

                private String name;
                private int grade;
                private int category_id;
                private String img;
                private Object subCategory;

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
                    return category_id;
                }

                public void setCategory_id(int category_id) {
                    this.category_id = category_id;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public Object getSubCategory() {
                    return subCategory;
                }

                public void setSubCategory(Object subCategory) {
                    this.subCategory = subCategory;
                }
            }
        }
    }
}

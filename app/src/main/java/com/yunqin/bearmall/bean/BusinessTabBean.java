package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.bean
 * @DATE 2020/3/30
 */
public class BusinessTabBean {

    /**
     * msg : 请求成功
     * code : 1
     * data : [{"categoryName":"精选","categoryId":1},{"categoryName":"猜你喜欢","categoryId":2},{"categoryName":"居家百货","categoryId":3},{
     * "categoryName":"母婴","categoryId":4},{"categoryName":"美食","categoryId":5},{"categoryName":"女装","categoryId":6},{"categoryName
     * ":"美妆","categoryId":7}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * categoryName : 精选
         * categoryId : 1
         */

        private String categoryName;
        private int categoryId;

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }
    }
}

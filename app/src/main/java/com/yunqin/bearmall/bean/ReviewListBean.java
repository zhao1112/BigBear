package com.yunqin.bearmall.bean;

import java.util.List;

public class ReviewListBean {

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

    public static class  DataBean{
        private int has_more;
        private List<ProductDetail.ReviewList> reviewList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<ProductDetail.ReviewList> getReviewList() {
            return reviewList;
        }

        public void setReviewList(List<ProductDetail.ReviewList> reviewList) {
            this.reviewList = reviewList;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "has_more=" + has_more +
                    ", reviewList=" + reviewList +
                    '}';
        }
    }
}

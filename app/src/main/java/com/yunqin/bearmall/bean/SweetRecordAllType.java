package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * Created by chenchen on 2018/7/23.
 */

public class SweetRecordAllType {

    private int code;

    private String message;

    private List<DataBean> data;

    public boolean isSuccess(){

        return code == 1;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean{

        private String creditSum;

        private int type;

        public String getCreditSum() {
            return creditSum;
        }

        public void setCreditSum(String creditSum) {
            this.creditSum = creditSum;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

}

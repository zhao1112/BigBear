package com.yunqin.bearmall.bean;

import java.io.Serializable;
import java.util.List;

public class MemberBargainProductListBean {

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

    public static class DataBean{
        private List<BargainRecordList> bargainRecordList;
        private int has_more;

        public List<BargainRecordList> getBargainRecordList() {
            return bargainRecordList;
        }

        public void setBargainRecordList(List<BargainRecordList> bargainRecordList) {
            this.bargainRecordList = bargainRecordList;
        }

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "bargainRecordList=" + bargainRecordList +
                    ", has_more=" + has_more +
                    '}';
        }
    }

    public static class BargainRecordList implements Serializable{

        private String price;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        private long bargainProduct_id;
        private long bargainRecord_id;
        private String productName;
        private ProductDetail.ProductImages productImages;
        private String currentPartPrice;
        private long currentPartBtAmount;
        private long expireDate;
        private List<String> specifications;
        private int status;//状态 0 已过期 1 进行中 2 已完成
        private String sourcePartPrice;
        private String sourcePartBtAmount;
        private int finishedNumber;
        private int personalCount;

        public long getBargainProduct_id() {
            return bargainProduct_id;
        }

        public void setBargainProduct_id(long bargainProduct_id) {
            this.bargainProduct_id = bargainProduct_id;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public ProductDetail.ProductImages getProductImages() {
            return productImages;
        }

        public void setProductImages(ProductDetail.ProductImages productImages) {
            this.productImages = productImages;
        }

        public String getCurrentPartPrice() {
            return currentPartPrice;
        }

        public void setCurrentPartPrice(String currentPartPrice) {
            this.currentPartPrice = currentPartPrice;
        }

        public long getCurrentPartBtAmount() {
            return currentPartBtAmount;
        }

        public void setCurrentPartBtAmount(long currentPartBtAmount) {
            this.currentPartBtAmount = currentPartBtAmount;
        }

        public long getExpireDate() {
            return expireDate;
        }

        public void setExpireDate(long expireDate) {
            this.expireDate = expireDate;
        }

        public List<String> getSpecifications() {
            return specifications;
        }

        public void setSpecifications(List<String> specifications) {
            this.specifications = specifications;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSourcePartPrice() {
            return sourcePartPrice;
        }

        public void setSourcePartPrice(String sourcePartPrice) {
            this.sourcePartPrice = sourcePartPrice;
        }

        public String getSourcePartBtAmount() {
            return sourcePartBtAmount;
        }

        public void setSourcePartBtAmount(String sourcePartBtAmount) {
            this.sourcePartBtAmount = sourcePartBtAmount;
        }

        public long getBargainRecord_id() {
            return bargainRecord_id;
        }

        public void setBargainRecord_id(long bargainRecord_id) {
            this.bargainRecord_id = bargainRecord_id;
        }

        public int getFinishedNumber() {
            return finishedNumber;
        }

        public void setFinishedNumber(int finishedNumber) {
            this.finishedNumber = finishedNumber;
        }

        public int getPersonalCount() {
            return personalCount;
        }

        public void setPersonalCount(int personalCount) {
            this.personalCount = personalCount;
        }

        @Override
        public String toString() {
            return "BargainRecordList{" +
                    "bargainProduct_id=" + bargainProduct_id +
                    ", bargainRecord_id=" + bargainRecord_id +
                    ", productName='" + productName + '\'' +
                    ", productImages=" + productImages +
                    ", currentPartPrice='" + currentPartPrice + '\'' +
                    ", currentPartBtAmount=" + currentPartBtAmount +
                    ", expireDate=" + expireDate +
                    ", specifications=" + specifications +
                    ", status=" + status +
                    ", sourcePartPrice='" + sourcePartPrice + '\'' +
                    ", sourcePartBtAmount='" + sourcePartBtAmount + '\'' +
                    ", finishedNumber=" + finishedNumber +
                    ", personalCount=" + personalCount +
                    '}';
        }
    }
}

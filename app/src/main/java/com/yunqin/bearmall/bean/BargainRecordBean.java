package com.yunqin.bearmall.bean;

import java.util.List;

import mlxy.utils.F;

public class BargainRecordBean {

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

        private List<BargainJoinRecordList> BargainJoinRecordList;
        private BargainDetails BargainDetails;
        private ProductDetail.Store store;
        private boolean isMember;

        public boolean isMember() {
            return isMember;
        }

        public void setMember(boolean member) {
            isMember = member;
        }

        public List<BargainRecordBean.BargainJoinRecordList> getBargainJoinRecordList() {
            return BargainJoinRecordList;
        }

        public void setBargainJoinRecordList(List<BargainRecordBean.BargainJoinRecordList> bargainJoinRecordList) {
            BargainJoinRecordList = bargainJoinRecordList;
        }

        public BargainRecordBean.BargainDetails getBargainDetails() {
            return BargainDetails;
        }

        public void setBargainDetails(BargainRecordBean.BargainDetails bargainDetails) {
            BargainDetails = bargainDetails;
        }

        public ProductDetail.Store getStore() {
            return store;
        }

        public void setStore(ProductDetail.Store store) {
            this.store = store;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "BargainJoinRecordList=" + BargainJoinRecordList +
                    ", BargainDetails=" + BargainDetails +
                    ", store=" + store +
                    '}';
        }
    }

    public static class BargainJoinRecordList{
        private String bargainAmount;
        private String nickName;
        private String description;
        private String iconUrl;
        private boolean isSelf;

        public String getBargainAmount() {
            return bargainAmount;
        }

        public void setBargainAmount(String bargainAmount) {
            this.bargainAmount = bargainAmount;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public boolean isSelf() {
            return isSelf;
        }

        public void setSelf(boolean self) {
            isSelf = self;
        }

        @Override
        public String toString() {
            return "BargainJoinRecordList{" +
                    "bargainAmount='" + bargainAmount + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", description='" + description + '\'' +
                    ", iconUrl='" + iconUrl + '\'' +
                    ", isSelf=" + isSelf +
                    '}';
        }
    }

    public static class BargainDetails{
        private ProductDetail.ProductImages productImages;
        private long bargainProduct_id;
        private long product_id;
        private String criticalRatio;
        private int status;
        private String bargainMoreAmount;
        private String bargainedRatio;
        private int finishedNumber;
        private long store_id;
        private int personalCount;
        private String bargainedBtAmount;
        private List<String> specifications;
        private int bargainCount;
        private String sourcePartBtAmount;
        private String bargainedPrice;
        private String sourcePartPrice;
        private String price;
        private boolean isLimitMs;
        private int restBargainCount;
        private String memberShipPrice;

        public int getRestBargainCount() {
            return restBargainCount;
        }

        public void setRestBargainCount(int restBargainCount) {
            this.restBargainCount = restBargainCount;
        }

        public String getMemberShipPrice() {
            return memberShipPrice;
        }

        public void setMemberShipPrice(String memberShipPrice) {
            this.memberShipPrice = memberShipPrice;
        }

        public boolean isLimitMs() {
            return isLimitMs;
        }

        public void setLimitMs(boolean limitMs) {
            isLimitMs = limitMs;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        private int isFavorite;
        private String expireDate;
        private long restTime;
        private long sku_id;
        private String productName;
        private String currentPartPrice;
        private String currentPartBtAmount;
        private long receiver_id;
        private long orders_id;
        private String address;
        private String area;
        private String consignee;
        private String phone;
        private long bargainRecord_id;

        public long getBargainRecord_id() {
            return bargainRecord_id;
        }

        public void setBargainRecord_id(long bargainRecord_id) {
            this.bargainRecord_id = bargainRecord_id;
        }

        public ProductDetail.ProductImages getProductImages() {
            return productImages;
        }

        public void setProductImages(ProductDetail.ProductImages productImages) {
            this.productImages = productImages;
        }

        public long getBargainProduct_id() {
            return bargainProduct_id;
        }

        public void setBargainProduct_id(long bargainProduct_id) {
            this.bargainProduct_id = bargainProduct_id;
        }

        public long getProduct_id() {
            return product_id;
        }

        public void setProduct_id(long product_id) {
            this.product_id = product_id;
        }

        public String getCriticalRatio() {
            return criticalRatio;
        }

        public void setCriticalRatio(String criticalRatio) {
            this.criticalRatio = criticalRatio;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getBargainMoreAmount() {
            return bargainMoreAmount;
        }

        public void setBargainMoreAmount(String bargainMoreAmount) {
            this.bargainMoreAmount = bargainMoreAmount;
        }

        public String getBargainedRatio() {
            return bargainedRatio;
        }

        public void setBargainedRatio(String bargainedRatio) {
            this.bargainedRatio = bargainedRatio;
        }

        public int getFinishedNumber() {
            return finishedNumber;
        }

        public void setFinishedNumber(int finishedNumber) {
            this.finishedNumber = finishedNumber;
        }

        public long getStore_id() {
            return store_id;
        }

        public void setStore_id(long store_id) {
            this.store_id = store_id;
        }

        public int getPersonalCount() {
            return personalCount;
        }

        public void setPersonalCount(int personalCount) {
            this.personalCount = personalCount;
        }

        public String getBargainedBtAmount() {
            return bargainedBtAmount;
        }

        public void setBargainedBtAmount(String bargainedBtAmount) {
            this.bargainedBtAmount = bargainedBtAmount;
        }

        public List<String> getSpecifications() {
            return specifications;
        }

        public void setSpecifications(List<String> specifications) {
            this.specifications = specifications;
        }

        public int getBargainCount() {
            return bargainCount;
        }

        public void setBargainCount(int bargainCount) {
            this.bargainCount = bargainCount;
        }

        public String getSourcePartBtAmount() {
            return sourcePartBtAmount;
        }

        public void setSourcePartBtAmount(String sourcePartBtAmount) {
            this.sourcePartBtAmount = sourcePartBtAmount;
        }

        public String getBargainedPrice() {
            return bargainedPrice;
        }

        public void setBargainedPrice(String bargainedPrice) {
            this.bargainedPrice = bargainedPrice;
        }

        public String getSourcePartPrice() {
            return sourcePartPrice;
        }

        public void setSourcePartPrice(String sourcePartPrice) {
            this.sourcePartPrice = sourcePartPrice;
        }

        public int getIsFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(int isFavorite) {
            this.isFavorite = isFavorite;
        }

        public String getExpireDate() {
            return expireDate;
        }

        public void setExpireDate(String expireDate) {
            this.expireDate = expireDate;
        }

        public long getRestTime() {
            return restTime;
        }

        public void setRestTime(long restTime) {
            this.restTime = restTime;
        }

        public long getSku_id() {
            return sku_id;
        }

        public void setSku_id(long sku_id) {
            this.sku_id = sku_id;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getCurrentPartPrice() {
            return currentPartPrice;
        }

        public void setCurrentPartPrice(String currentPartPrice) {
            this.currentPartPrice = currentPartPrice;
        }

        public String getCurrentPartBtAmount() {
            return currentPartBtAmount;
        }

        public void setCurrentPartBtAmount(String currentPartBtAmount) {
            this.currentPartBtAmount = currentPartBtAmount;
        }

        public long getReceiver_id() {
            return receiver_id;
        }

        public void setReceiver_id(long receiver_id) {
            this.receiver_id = receiver_id;
        }

        public long getOrders_id() {
            return orders_id;
        }

        public void setOrders_id(long orders_id) {
            this.orders_id = orders_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "BargainDetails{" +
                    "productImages=" + productImages +
                    ", bargainProduct_id=" + bargainProduct_id +
                    ", product_id=" + product_id +
                    ", criticalRatio='" + criticalRatio + '\'' +
                    ", status=" + status +
                    ", bargainMoreAmount='" + bargainMoreAmount + '\'' +
                    ", bargainedRatio='" + bargainedRatio + '\'' +
                    ", finishedNumber=" + finishedNumber +
                    ", store_id=" + store_id +
                    ", personalCount=" + personalCount +
                    ", bargainedBtAmount='" + bargainedBtAmount + '\'' +
                    ", specifications=" + specifications +
                    ", bargainCount=" + bargainCount +
                    ", sourcePartBtAmount='" + sourcePartBtAmount + '\'' +
                    ", bargainedPrice='" + bargainedPrice + '\'' +
                    ", sourcePartPrice='" + sourcePartPrice + '\'' +
                    ", isFavorite=" + isFavorite +
                    ", expireDate='" + expireDate + '\'' +
                    ", restTime=" + restTime +
                    ", sku_id=" + sku_id +
                    ", productName='" + productName + '\'' +
                    ", currentPartPrice='" + currentPartPrice + '\'' +
                    ", currentPartBtAmount='" + currentPartBtAmount + '\'' +
                    ", receiver_id=" + receiver_id +
                    ", orders_id=" + orders_id +
                    ", address='" + address + '\'' +
                    ", area='" + area + '\'' +
                    ", consignee='" + consignee + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }


}

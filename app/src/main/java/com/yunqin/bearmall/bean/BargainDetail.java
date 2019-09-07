package com.yunqin.bearmall.bean;

import java.util.List;

public class BargainDetail {


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
        private ProductDetail.Store store;
        private List<ProductDetail.ReviewList> reviewList;
        private int isFavorite;
        private boolean isMember;
        private int reviewTotalCount;
        private BargainProduct bargainProduct;

        public ProductDetail.Store getStore() {
            return store;
        }

        public void setStore(ProductDetail.Store store) {
            this.store = store;
        }

        public List<ProductDetail.ReviewList> getReviewList() {
            return reviewList;
        }

        public boolean isMember() {
            return isMember;
        }

        public void setMember(boolean member) {
            isMember = member;
        }

        public void setReviewList(List<ProductDetail.ReviewList> reviewList) {
            this.reviewList = reviewList;
        }

        public int getIsFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(int isFavorite) {
            this.isFavorite = isFavorite;
        }

        public int getReviewTotalCount() {
            return reviewTotalCount;
        }

        public void setReviewTotalCount(int reviewTotalCount) {
            this.reviewTotalCount = reviewTotalCount;
        }

        public BargainProduct getBargainProduct() {
            return bargainProduct;
        }

        public void setBargainProduct(BargainProduct bargainProduct) {
            this.bargainProduct = bargainProduct;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "store=" + store +
                    ", reviewList=" + reviewList +
                    ", isFavorite=" + isFavorite +
                    ", reviewCount=" + reviewTotalCount +
                    ", bargainProduct=" + bargainProduct +
                    '}';
        }
    }


    public static class BargainProduct {
        private List<ProductDetail.ProductImages> productImages;
        private long bargainProduct_id;
        private long bargainRecord_id;
        private long product_id;
        private int restCount;
        private boolean isLimitMs;

        public boolean isLimitMs() {
            return isLimitMs;
        }

        public void setLimitMs(boolean limitMsdc) {
            isLimitMs = limitMsdc;
        }

        public int getRestCount() {
            return restCount;
        }

        public void setRestCount(int restCount) {
            this.restCount = restCount;
        }

        private int finishedNumber;
        private long expireDate;//过期时间
        private String partBtAmount;
        private String partPrice;
        private int isOngoing;//是否正在砍价中 0 否 1 是
        private long sku_id;
        private String productName;
        private String currentPartPrice;
        private String currentPartBtAmount;
        private float price;
        private List<ProductDetail.SpecificationItems> specificationItems;
        private List<ProductDetail.SkuList> skuList;
        private String address;
        private String area;
        private String consignee;
        private String phone;

        public List<ProductDetail.ProductImages> getProductImages() {
            return productImages;
        }

        public void setProductImages(List<ProductDetail.ProductImages> productImages) {
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

        public int getFinishedNumber() {
            return finishedNumber;
        }

        public void setFinishedNumber(int finishedNumber) {
            this.finishedNumber = finishedNumber;
        }

        public long getExpireDate() {
            return expireDate;
        }

        public void setExpireDate(long expireDate) {
            this.expireDate = expireDate;
        }

        public String getPartBtAmount() {
            return partBtAmount;
        }

        public void setPartBtAmount(String partBtAmount) {
            this.partBtAmount = partBtAmount;
        }

        public String getPartPrice() {
            return partPrice;
        }

        public void setPartPrice(String partPrice) {
            this.partPrice = partPrice;
        }

        public int getIsOngoing() {
            return isOngoing;
        }

        public void setIsOngoing(int isOngoing) {
            this.isOngoing = isOngoing;
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

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public List<ProductDetail.SpecificationItems> getSpecificationItems() {
            return specificationItems;
        }

        public void setSpecificationItems(List<ProductDetail.SpecificationItems> specificationItems) {
            this.specificationItems = specificationItems;
        }

        public List<ProductDetail.SkuList> getSkuList() {
            return skuList;
        }

        public void setSkuList(List<ProductDetail.SkuList> skuList) {
            this.skuList = skuList;
        }

        public long getSku_id() {
            return sku_id;
        }

        public void setSku_id(long sku_id) {
            this.sku_id = sku_id;
        }

        public long getBargainRecord_id() {
            return bargainRecord_id;
        }

        public void setBargainRecord_id(long bargainRecord_id) {
            this.bargainRecord_id = bargainRecord_id;
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
            return "BargainProduct{" +
                    "productImages=" + productImages +
                    ", bargainProduct_id=" + bargainProduct_id +
                    ", bargainRecord_id=" + bargainRecord_id +
                    ", product_id=" + product_id +
                    ", finishedNumber=" + finishedNumber +
                    ", expireDate=" + expireDate +
                    ", partBtAmount='" + partBtAmount + '\'' +
                    ", partPrice='" + partPrice + '\'' +
                    ", isOngoing=" + isOngoing +
                    ", sku_id=" + sku_id +
                    ", productName='" + productName + '\'' +
                    ", currentPartPrice='" + currentPartPrice + '\'' +
                    ", currentPartBtAmount='" + currentPartBtAmount + '\'' +
                    ", price=" + price +
                    ", specificationItems=" + specificationItems +
                    ", skuList=" + skuList +
                    ", address='" + address + '\'' +
                    ", area='" + area + '\'' +
                    ", consignee='" + consignee + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }
}

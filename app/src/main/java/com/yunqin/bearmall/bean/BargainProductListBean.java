package com.yunqin.bearmall.bean;

import java.util.List;

public class BargainProductListBean {

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
        private List<BargainProductList> bargainProductList;
        private int has_more;

        public List<BargainProductList> getBargainProductList() {
            return bargainProductList;
        }

        public void setBargainProductList(List<BargainProductList> bargainProductList) {
            this.bargainProductList = bargainProductList;
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
                    "bargainProductList=" + bargainProductList +
                    ", has_more=" + has_more +
                    '}';
        }
    }

    public static class BargainProductList{
        private ProductDetail.ProductImages productImages;
        private long bargainProduct_id;
        private ProductDetail.Store store;
        private String price;
        private long product_id;
        private int restCount;

        public int getRestCount() {
            return restCount;
        }

        public void setRestCount(int restCount) {
            this.restCount = restCount;
        }


        private List<ProductDetail.SkuList> skuList;
        private List<ProductDetail.SpecificationItems> specificationItems;
        private int isOngoing;
        private long bargainRecord_id;
        private int personalCount;
        private String productName;
        private String createdDate;

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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public long getProduct_id() {
            return product_id;
        }

        public void setProduct_id(long product_id) {
            this.product_id = product_id;
        }



        public List<ProductDetail.SkuList> getSkuList() {
            return skuList;
        }

        public void setSkuList(List<ProductDetail.SkuList> skuList) {
            this.skuList = skuList;
        }

        public List<ProductDetail.SpecificationItems> getSpecificationItems() {
            return specificationItems;
        }

        public void setSpecificationItems(List<ProductDetail.SpecificationItems> specificationItems) {
            this.specificationItems = specificationItems;
        }

        public int getIsOngoing() {
            return isOngoing;
        }

        public void setIsOngoing(int isOngoing) {
            this.isOngoing = isOngoing;
        }

        public int getPersonalCount() {
            return personalCount;
        }

        public void setPersonalCount(int personalCount) {
            this.personalCount = personalCount;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public ProductDetail.Store getStore() {
            return store;
        }

        public void setStore(ProductDetail.Store store) {
            this.store = store;
        }

        public long getBargainRecord_id() {
            return bargainRecord_id;
        }

        public void setBargainRecord_id(long bargainRecord_id) {
            this.bargainRecord_id = bargainRecord_id;
        }

        @Override
        public String toString() {
            return "BargainProductList{" +
                    "productImages=" + productImages +
                    ", bargainProduct_id=" + bargainProduct_id +
                    ", store=" + store +
                    ", price='" + price + '\'' +
                    ", product_id=" + product_id +
                    ", skuList=" + skuList +
                    ", specificationItems=" + specificationItems +
                    ", isOngoing=" + isOngoing +
                    ", personalCount=" + personalCount +
                    ", productName='" + productName + '\'' +
                    ", bargainRecord_id='" + bargainRecord_id + '\'' +
                    ", createdDate='" + createdDate + '\'' +
                    '}';
        }
    }
}

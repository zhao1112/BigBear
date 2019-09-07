package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * Created by chenchen on 2018/8/25.
 */

public class ZeroPastData {



    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{

        private int has_more;

        private List<ZeroPast> pastGroupRecordList;

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public List<ZeroPast> getPastGroupRecordList() {
            return pastGroupRecordList;
        }

        public void setPastGroupRecordList(List<ZeroPast> pastGroupRecordList) {
            this.pastGroupRecordList = pastGroupRecordList;
        }
    }


    public static class ZeroPast{

        private String bigBearNumber;

        private ZeroGoodsBean.DataBean.GroupPurchasingListBean.ProductImagesBean productImages;

        private String product_id;

        private String nickName;

        private String productName;

        private String iconUrl;

        private String store_id;

        private String endDate;

        private String sku_id;

        private List<String> specifications;

        public String getBigBearNumber() {
            return bigBearNumber;
        }

        public void setBigBearNumber(String bigBearNumber) {
            this.bigBearNumber = bigBearNumber;
        }

        public ZeroGoodsBean.DataBean.GroupPurchasingListBean.ProductImagesBean getProductImages() {
            return productImages;
        }

        public void setProductImages(ZeroGoodsBean.DataBean.GroupPurchasingListBean.ProductImagesBean productImages) {
            this.productImages = productImages;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getSku_id() {
            return sku_id;
        }

        public void setSku_id(String sku_id) {
            this.sku_id = sku_id;
        }

        public List<String> getSpecifications() {
            return specifications;
        }

        public void setSpecifications(List<String> specifications) {
            this.specifications = specifications;
        }
    }


}

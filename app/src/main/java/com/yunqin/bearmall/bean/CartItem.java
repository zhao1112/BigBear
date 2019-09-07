package com.yunqin.bearmall.bean;

import java.io.Serializable;
import java.util.List;

public class CartItem {

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
        private List<CartItemList> cartItemList;
        private int itemCount;

        public List<CartItemList> getCartItemList() {
            return cartItemList;
        }

        public void setCartItemList(List<CartItemList> cartItemList) {
            this.cartItemList = cartItemList;
        }

        public int getItemCount() {
            return itemCount;
        }

        public void setItemCount(int itemCount) {
            this.itemCount = itemCount;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "cartItemList=" + cartItemList +
                    ", itemCount=" + itemCount +
                    '}';
        }
    }

    public static class CartItemList implements Serializable {
        private String logo;
        private List<ItemList> itemList;
        private long store_id;
        private String store_name;
        private boolean isStoreEnabled;

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public List<ItemList> getItemList() {
            return itemList;
        }

        public void setItemList(List<ItemList> itemList) {
            this.itemList = itemList;
        }

        public long getStore_id() {
            return store_id;
        }

        public void setStore_id(long store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public boolean isStoreEnabled() {
            return isStoreEnabled;
        }

        public void setStoreEnabled(boolean storeEnabled) {
            isStoreEnabled = storeEnabled;
        }

        @Override
        public String toString() {
            return "CartItemList{" +
                    "logo='" + logo + '\'' +
                    ", itemList=" + itemList +
                    ", store_id=" + store_id +
                    ", store_name='" + store_name + '\'' +
                    ", isStoreEnabled=" + isStoreEnabled +
                    '}';
        }
    }

    public static class ItemList implements Serializable {
        private List<ProductDetail.SpecificationValues> specificationValues;
        private ProductDetail.ProductImages productImages;
        private long product_id;
        private String itemCreatedDate;
        private long store_id;
        private float partPrice;
        private long cart_id;
        private String price;
        private int stock;
        private boolean isProductList;
        private boolean isProductActive;
        private long item_id;
        private float partBtAmount;
        private int quantity;
        private String caption;
        private String productName;
        private long sku_id;
        private boolean isProductMarketable;
        private boolean isCheck;
        private boolean isEditCheck;
        private boolean isBTPrice;

        private boolean isLimitMs;//是否是会员专属商品
        private String membershipPrice;// 会员价格
        private boolean isSurportMsp; // 是否支持会员价

        public boolean isLimitMs() {
            return isLimitMs;
        }

        public void setLimitMs(boolean limitMs) {
            isLimitMs = limitMs;
        }

        public String getMembershipPrice() {
            return membershipPrice;
        }

        public void setMembershipPrice(String membershipPrice) {
            this.membershipPrice = membershipPrice;
        }

        public boolean isSurportMsp() {
            return isSurportMsp;
        }

        public void setSurportMsp(boolean surportMsp) {
            isSurportMsp = surportMsp;
        }

        public List<ProductDetail.SpecificationValues> getSpecificationValues() {
            return specificationValues;
        }

        public void setSpecificationValues(List<ProductDetail.SpecificationValues> specificationValues) {
            this.specificationValues = specificationValues;
        }

        public long getProduct_id() {
            return product_id;
        }

        public void setProduct_id(long product_id) {
            this.product_id = product_id;
        }

        public String getItemCreatedDate() {
            return itemCreatedDate;
        }

        public void setItemCreatedDate(String itemCreatedDate) {
            this.itemCreatedDate = itemCreatedDate;
        }

        public ProductDetail.ProductImages getProductImages() {
            return productImages;
        }

        public void setProductImages(ProductDetail.ProductImages productImages) {
            this.productImages = productImages;
        }

        public long getStore_id() {
            return store_id;
        }

        public void setStore_id(long store_id) {
            this.store_id = store_id;
        }

        public float getPartPrice() {
            return partPrice;
        }

        public void setPartPrice(float partPrice) {
            this.partPrice = partPrice;
        }

        public long getCart_id() {
            return cart_id;
        }

        public void setCart_id(long cart_id) {
            this.cart_id = cart_id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public boolean isProductList() {
            return isProductList;
        }

        public void setProductList(boolean productList) {
            isProductList = productList;
        }

        public boolean isProductActive() {
            return isProductActive;
        }

        public void setProductActive(boolean productActive) {
            isProductActive = productActive;
        }

        public long getItem_id() {
            return item_id;
        }

        public void setItem_id(long item_id) {
            this.item_id = item_id;
        }

        public float getPartBtAmount() {
            return partBtAmount;
        }

        public void setPartBtAmount(float partBtAmount) {
            this.partBtAmount = partBtAmount;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public long getSku_id() {
            return sku_id;
        }

        public void setSku_id(long sku_id) {
            this.sku_id = sku_id;
        }

        public boolean isProductMarketable() {
            return isProductMarketable;
        }

        public void setProductMarketable(boolean productMarketable) {
            isProductMarketable = productMarketable;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public boolean isBTPrice() {
            return isBTPrice;
        }

        public void setBTPrice(boolean BTPrice) {
            isBTPrice = BTPrice;
        }

        public boolean isEditCheck() {
            return isEditCheck;
        }

        public void setEditCheck(boolean editCheck) {
            isEditCheck = editCheck;
        }

        @Override
        public String toString() {
            return "ItemList{" +
                    "specificationValues=" + specificationValues +
                    ", productImages=" + productImages +
                    ", product_id=" + product_id +
                    ", itemCreatedDate='" + itemCreatedDate + '\'' +
                    ", store_id=" + store_id +
                    ", partPrice=" + partPrice +
                    ", cart_id=" + cart_id +
                    ", price=" + price +
                    ", stock=" + stock +
                    ", isProductList=" + isProductList +
                    ", isProductActive=" + isProductActive +
                    ", item_id=" + item_id +
                    ", partBtAmount=" + partBtAmount +
                    ", quantity=" + quantity +
                    ", caption='" + caption + '\'' +
                    ", productName='" + productName + '\'' +
                    ", sku_id=" + sku_id +
                    ", isProductMarketable=" + isProductMarketable +
                    ", isCheck=" + isCheck +
                    ", isEditCheck=" + isEditCheck +
                    ", isBTPrice=" + isBTPrice +
                    '}';
        }
    }

    public static class CartBrand implements Serializable {
        private String logo;
        private long store_id;
        private boolean isStoreEnabled;
        private String store_name;
        private boolean isCheck;
        private boolean isEditCheck;

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public long getStore_id() {
            return store_id;
        }

        public void setStore_id(long store_id) {
            this.store_id = store_id;
        }

        public boolean isStoreEnabled() {
            return isStoreEnabled;
        }

        public void setStoreEnabled(boolean storeEnabled) {
            isStoreEnabled = storeEnabled;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public boolean isEditCheck() {
            return isEditCheck;
        }

        public void setEditCheck(boolean editCheck) {
            isEditCheck = editCheck;
        }

        @Override
        public String toString() {
            return "CartBrand{" +
                    "logo='" + logo + '\'' +
                    ", store_id=" + store_id +
                    ", isStoreEnabled=" + isStoreEnabled +
                    ", store_name='" + store_name + '\'' +
                    ", isCheck=" + isCheck +
                    ", isEditCheck=" + isEditCheck +
                    '}';
        }
    }
}

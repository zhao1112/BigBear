package com.yunqin.bearmall.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ProductDetail {


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
        private String priceInstruction;//价格说明内容
        private Product product;
//        private String btInstruction;//糖果说明内容
        private BtInstruction btInstruction;//糖果说明内容
        private Store store;
        private List<ReviewList> reviewList;
        private List<GuaranteeInstruction> guaranteeInstruction;//保障说明内容
        private int isFavorite;
        private List<SkuList> skuList;
        private long sku_id;
        private int reviewTotalCount;
        private List<InstantmessageList> instantmessageList;

        public int getReviewTotalCount() {
            return reviewTotalCount;
        }

        public void setReviewTotalCount(int reviewTotalCount) {
            this.reviewTotalCount = reviewTotalCount;
        }

        public List<GuaranteeInstruction> getGuaranteeInstruction() {
            return guaranteeInstruction;
        }

        public void setGuaranteeInstruction(List<GuaranteeInstruction> guaranteeInstruction) {
            this.guaranteeInstruction = guaranteeInstruction;
        }

        public String getPriceInstruction() {
            return priceInstruction;
        }

        public void setPriceInstruction(String priceInstruction) {
            this.priceInstruction = priceInstruction;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public BtInstruction getBtInstruction() {
            return btInstruction;
        }

        public void setBtInstruction(BtInstruction btInstruction) {
            this.btInstruction = btInstruction;
        }

        public Store getStore() {
            return store;
        }

        public void setStore(Store store) {
            this.store = store;
        }

        public List<ReviewList> getReviewList() {
            return reviewList;
        }

        public void setReviewList(List<ReviewList> reviewList) {
            this.reviewList = reviewList;
        }



        public int getIsFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(int isFavorite) {
            this.isFavorite = isFavorite;
        }

        public List<SkuList> getSkuList() {
            return skuList;
        }

        public void setSkuList(List<SkuList> skuList) {
            this.skuList = skuList;
        }

        public long getSku_id() {
            return sku_id;
        }

        public void setSku_id(long sku_id) {
            this.sku_id = sku_id;
        }

        public List<InstantmessageList> getInstantmessageList() {
            return instantmessageList;
        }

        public void setInstantmessageList(List<InstantmessageList> instantmessageList) {
            this.instantmessageList = instantmessageList;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "priceInstruction='" + priceInstruction + '\'' +
                    ", product=" + product +
                    ", btInstruction=" + btInstruction +
                    ", store=" + store +
                    ", reviewList=" + reviewList +
                    ", guaranteeInstruction='" + guaranteeInstruction + '\'' +
                    ", isFavorite=" + isFavorite +
                    ", skuList=" + skuList +
                    ", sku_id=" + sku_id +
                    ", instantmessageList=" + instantmessageList +
                    '}';
        }
    }

    public static class GuaranteeInstruction implements Serializable{
        private String description;
        private String name;
        private String img;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        @Override
        public String toString() {
            return "GuaranteeInstruction{" +
                    "description='" + description + '\'' +
                    ", name='" + name + '\'' +
                    ", img='" + img + '\'' +
                    '}';
        }
    }

    public static class BtInstruction{
        private String v0;
        private String v1;
        private String v2;

        public String getV0() {
            return v0;
        }

        public void setV0(String v0) {
            this.v0 = v0;
        }

        public String getV1() {
            return v1;
        }

        public void setV1(String v1) {
            this.v1 = v1;
        }

        public String getV2() {
            return v2;
        }

        public void setV2(String v2) {
            this.v2 = v2;
        }

        @Override
        public String toString() {
            return "BtInstruction{" +
                    "v0='" + v0 + '\'' +
                    ", v1='" + v1 + '\'' +
                    ", v2='" + v2 + '\'' +
                    '}';
        }
    }

    public static class Product {
        private List<ProductImages> productImages;
        private String price;
        private long product_id;
        private List<SpecificationItems> specificationItems;//规格
        private int sales;
        private String partBtAmount;
        private String partPrice;
        private long store_id;
        private String caption;//商品副标题
        private String productName;//商品名称

        public List<ProductImages> getProductImages() {
            return productImages;
        }

        public void setProductImages(List<ProductImages> productImages) {
            this.productImages = productImages;
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

        public List<SpecificationItems> getSpecificationItems() {
            return specificationItems;
        }

        public void setSpecificationItems(List<SpecificationItems> specificationItems) {
            this.specificationItems = specificationItems;
        }

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
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

        public long getStore_id() {
            return store_id;
        }

        public void setStore_id(long store_id) {
            this.store_id = store_id;
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

        @Override
        public String toString() {
            return "Product{" +
                    "productImages=" + productImages +
                    ", price='" + price + '\'' +
                    ", product_id=" + product_id +
                    ", specificationItems=" + specificationItems +
                    ", sales=" + sales +
                    ", partBtAmount='" + partBtAmount + '\'' +
                    ", partPrice='" + partPrice + '\'' +
                    ", store_id=" + store_id +
                    ", caption='" + caption + '\'' +
                    ", productName='" + productName + '\'' +
                    '}';
        }
    }

    public static class ProductImages implements Serializable{
        private String source;
        private String large;
        private String medium;
        private String thumbnail;
        private int order;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        @Override
        public String toString() {
            return "ProductImages{" +
                    "source='" + source + '\'' +
                    ", large='" + large + '\'' +
                    ", medium='" + medium + '\'' +
                    ", thumbnail='" + thumbnail + '\'' +
                    ", order=" + order +
                    '}';
        }
    }

    public static class SpecificationItems {
        private String name;
        private List<Entries> entries;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Entries> getEntries() {
            return entries;
        }

        public void setEntries(List<Entries> entries) {
            this.entries = entries;
        }

        @Override
        public String toString() {
            return "SpecificationItems{" +
                    "name='" + name + '\'' +
                    ", entries=" + entries +
                    '}';
        }
    }

    public static class Entries {
        private long id;
        private String value;
        private boolean isSelected;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        @Override
        public String toString() {
            return "Entries{" +
                    "id=" + id +
                    ", value='" + value + '\'' +
                    ", isSelected=" + isSelected +
                    '}';
        }
    }

    public static class Store {
        private int productNumber;//商品数量
        private int productSales;//销售量
        private String logo;
        private String storeRankName;//店铺等级名称
        private float productScore;//店铺的商品合计评分
        private long store_id;
        private int type;//店铺类型 0非自营 1自营
        private String store_name;//店铺名称
        private List<HotProductList> hotProductList;//店铺热销商品

        public int getProductNumber() {
            return productNumber;
        }

        public void setProductNumber(int productNumber) {
            this.productNumber = productNumber;
        }

        public int getProductSales() {
            return productSales;
        }

        public void setProductSales(int productSales) {
            this.productSales = productSales;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getStoreRankName() {
            return storeRankName;
        }

        public void setStoreRankName(String storeRankName) {
            this.storeRankName = storeRankName;
        }

        public float getProductScore() {
            return productScore;
        }

        public void setProductScore(float productScore) {
            this.productScore = productScore;
        }

        public long getStore_id() {
            return store_id;
        }

        public void setStore_id(long store_id) {
            this.store_id = store_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public List<HotProductList> getHotProductList() {
            return hotProductList;
        }

        public void setHotProductList(List<HotProductList> hotProductList) {
            this.hotProductList = hotProductList;
        }

        @Override
        public String toString() {
            return "Store{" +
                    "productNumber=" + productNumber +
                    ", productSales=" + productSales +
                    ", logo='" + logo + '\'' +
                    ", storeRankName='" + storeRankName + '\'' +
                    ", productScore=" + productScore +
                    ", store_id=" + store_id +
                    ", type=" + type +
                    ", store_name='" + store_name + '\'' +
                    ", hotProductList=" + hotProductList +
                    '}';
        }
    }

    public static class HotProductList {
        private ProductImages productImages;
        private String price;
        private long product_id;
        private String partBtAmount;
        private String partPrice;

        public ProductImages getProductImages() {
            return productImages;
        }

        public void setProductImages(ProductImages productImages) {
            this.productImages = productImages;
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

        @Override
        public String toString() {
            return "HotProductList{" +
                    "productImages=" + productImages +
                    ", price='" + price + '\'' +
                    ", product_id=" + product_id +
                    ", partBtAmount='" + partBtAmount + '\'' +
                    ", partPrice='" + partPrice + '\'' +
                    '}';
        }
    }

    public static class ReviewList {
        private String content;
        private List<ReviewImages> reviewImages;
        private long review_id;
        private String nickName;
        private String iconUrl;
        private float score;
        private String createdDate;
        private List<String> specifications;//规格

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<ProductDetail.ReviewImages> getReviewImages() {
            return reviewImages;
        }

        public void setReviewImages(List<ProductDetail.ReviewImages> reviewImages) {
            this.reviewImages = reviewImages;
        }

        public long getReview_id() {
            return review_id;
        }

        public void setReview_id(long review_id) {
            this.review_id = review_id;
        }

        public String getNickName() {
            return nickName;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public List<String> getSpecifications() {
            return specifications;
        }

        public void setSpecifications(List<String> specifications) {
            this.specifications = specifications;
        }

        @Override
        public String toString() {
            return "ReviewList{" +
                    "content='" + content + '\'' +
                    ", reviewImages=" + reviewImages +
                    ", review_id=" + review_id +
                    ", nickName='" + nickName + '\'' +
                    ", iconUrl='" + iconUrl + '\'' +
                    ", score=" + score +
                    ", specifications=" + specifications +
                    '}';
        }
    }

    public static class ReviewImages {
        private String source;
        private String large;
        private String medium;
        private String thumbnail;
        private int order;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        @Override
        public String toString() {
            return "reviewImages{" +
                    "source='" + source + '\'' +
                    ", large='" + large + '\'' +
                    ", medium='" + medium + '\'' +
                    ", thumbnail='" + thumbnail + '\'' +
                    ", order=" + order +
                    '}';
        }
    }

    public static class SkuList {
        private int stock;//库存
        private List<SpecificationValues> specificationValues;
        private String price;
        private boolean isDefault;
        private long sku_id;

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public List<SpecificationValues> getSpecificationValues() {
            return specificationValues;
        }

        public void setSpecificationValues(List<SpecificationValues> specificationValues) {
            this.specificationValues = specificationValues;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public boolean isDefault() {
            return isDefault;
        }

        public void setDefault(boolean aDefault) {
            isDefault = aDefault;
        }



        public long getSku_id() {
            return sku_id;
        }

        public void setSku_id(long sku_id) {
            this.sku_id = sku_id;
        }

        @Override
        public String toString() {
            return "SkuList{" +
                    "stock=" + stock +
                    ", specificationValues=" + specificationValues +
                    ", price='" + price + '\'' +
                    ", isDefault=" + isDefault +
                    ", sku_id=" + sku_id +
                    '}';
        }
    }

    public static class SpecificationValues implements Serializable{
        private long id;
        private String value;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "SpecificationValues{" +
                    "id=" + id +
                    ", value='" + value + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            SpecificationValues other = (SpecificationValues) obj;
            if (id != other.id)
                return false;
            if (value == null) {
                if (other.value != null)
                    return false;
            } else if (!value.equals(other.value))
                return false;
            return true;
        }
    }

    public static class InstantmessageList {
        private long record_id;
        private String name;
        private String account;
        private int type;//类型 0：QQ  1：电话

        public long getRecord_id() {
            return record_id;
        }

        public void setRecord_id(long record_id) {
            this.record_id = record_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "InstantmessageList{" +
                    "record_id=" + record_id +
                    ", name='" + name + '\'' +
                    ", account='" + account + '\'' +
                    ", type=" + type +
                    '}';
        }
    }

}

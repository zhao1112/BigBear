package com.newversions.tbk.entity;

import java.io.Serializable;
import java.util.List;

public class GoodDetailEntity implements Serializable{

    /**
     * msg : 请求成功
     * code : 1
     * goodDetail : {"freeShip":true,"couponEndDate":"2019-07-04 00:00:00","outerImages":"https://t00img.yangkeduo.com/goods/images/2019-06-12/f22073b1581c43225c147484e4b687f0.jpeg","type":true,"commision":3.99,"couponAmount":1,"sellerId":null,"price":"8.90","handleStatus":true,"id":681648,"tmall":0,"area":null,"images":null,"timeId":null,"module":false,"couponUrl":null,"subCategoryId":null,"sellerLogo":null,"version":null,"brandFlash":false,"itemId":"838296102","dse":4.7,"name":"【8.9元抢1000件，抢完恢复16.9元】童装男童夏装套装2019新款男女宝宝夏季婴幼儿童帅气短袖两件套潮","detailUrl":null,"rushBuy":false,"sellNum":29850,"needBuyed":false,"maternalBaby":0,"dsr":4.6,"todayRecommended":false,"flag":true,"specialCategoryId":0,"dss":4.6,"brandFlashName":null,"icon":null,"discountPrice":"7.90","sellerName":"超级宝宝母婴馆","moduleCategoryId":null,"content":"<p>\n<img src=\"https://t00img.yangkeduo.com/goods/images/2019-06-30/183f9e77-97cc-4e73-b694-c35798efa081.jpg\"><\/img>\n<img src=\"https://t00img.yangkeduo.com/goods/images/2019-05-22/21e4794a-3147-4601-b5d2-609c13e4a808.jpg\"><\/img>\n<img src=\"https://t00img.yangkeduo.com/goods/images/2019-05-22/bf9811ce-ff78-492a-ac5d-ff8968b535b1.jpg\"><\/img>\n<img src=\"https://t00img.yangkeduo.com/goods/images/2019-05-22/deb50eec-aceb-457a-9735-377471496862.jpg\"><\/img>\n<img src=\"https://t00img.yangkeduo.com/goods/images/2019-05-22/cf4d1fbb-4c8e-4113-8890-c044680db105.jpg\"><\/img>\n<img src=\"https://t00img.yangkeduo.com/goods/images/2019-05-22/2cd835f9-af5c-45d7-9bc1-22429db29468.jpg\"><\/img>\n<img src=\"https://t00img.yangkeduo.com/goods/images/2019-05-22/0efa9143-9b07-446e-b0ae-1271327d8c73.jpg\"><\/img>\n<\/p>","enabled":true,"virtualSales":null,"brandSellerId":null,"summary":null,"lastModifiedDate":null,"outIcon":"https://t00img.yangkeduo.com/goods/images/2019-06-12/f22073b1581c43225c147484e4b687f0.jpeg","brandFlashId":0,"createdDate":null,"createTime":null,"taoToken":null,"couponStartDate":"2019-07-03 00:00:00","categoryId":158}
     */

    private String msg;
    private int code;
    private GoodDetailBean goodDetail;

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

    public GoodDetailBean getGoodDetail() {
        return goodDetail;
    }

    public void setGoodDetail(GoodDetailBean goodDetail) {
        this.goodDetail = goodDetail;
    }

    public static class GoodDetailBean implements Serializable{
        /**
         * freeShip : true
         * couponEndDate : 2019-07-04 00:00:00
         * outerImages : https://t00img.yangkeduo.com/goods/images/2019-06-12/f22073b1581c43225c147484e4b687f0.jpeg
         * type : true
         * commision : 3.99
         * couponAmount : 1
         * sellerId : null
         * price : 8.90
         * handleStatus : true
         * id : 681648
         * tmall : 0
         * area : null
         * images : null
         * timeId : null
         * module : false
         * couponUrl : null
         * subCategoryId : null
         * sellerLogo : null
         * version : null
         * brandFlash : false
         * itemId : 838296102
         * dse : 4.7
         * name : 【8.9元抢1000件，抢完恢复16.9元】童装男童夏装套装2019新款男女宝宝夏季婴幼儿童帅气短袖两件套潮
         * detailUrl : null
         * rushBuy : false
         * sellNum : 29850
         * needBuyed : false
         * maternalBaby : 0
         * dsr : 4.6
         * todayRecommended : false
         * flag : true
         * specialCategoryId : 0
         * dss : 4.6
         * brandFlashName : null
         * icon : null
         * discountPrice : 7.90
         * sellerName : 超级宝宝母婴馆
         * moduleCategoryId : null
         * content : <p>
         <img src="https://t00img.yangkeduo.com/goods/images/2019-06-30/183f9e77-97cc-4e73-b694-c35798efa081.jpg"></img>
         <img src="https://t00img.yangkeduo.com/goods/images/2019-05-22/21e4794a-3147-4601-b5d2-609c13e4a808.jpg"></img>
         <img src="https://t00img.yangkeduo.com/goods/images/2019-05-22/bf9811ce-ff78-492a-ac5d-ff8968b535b1.jpg"></img>
         <img src="https://t00img.yangkeduo.com/goods/images/2019-05-22/deb50eec-aceb-457a-9735-377471496862.jpg"></img>
         <img src="https://t00img.yangkeduo.com/goods/images/2019-05-22/cf4d1fbb-4c8e-4113-8890-c044680db105.jpg"></img>
         <img src="https://t00img.yangkeduo.com/goods/images/2019-05-22/2cd835f9-af5c-45d7-9bc1-22429db29468.jpg"></img>
         <img src="https://t00img.yangkeduo.com/goods/images/2019-05-22/0efa9143-9b07-446e-b0ae-1271327d8c73.jpg"></img>
         </p>
         * enabled : true
         * virtualSales : null
         * brandSellerId : null
         * summary : null
         * lastModifiedDate : null
         * outIcon : https://t00img.yangkeduo.com/goods/images/2019-06-12/f22073b1581c43225c147484e4b687f0.jpeg
         * brandFlashId : 0
         * createdDate : null
         * createTime : null
         * taoToken : null
         * couponStartDate : 2019-07-03 00:00:00
         * categoryId : 158
         */

        private boolean freeShip;
        private String couponEndDate;
        private String outerImages;
        private boolean type;
        private double commision;
        private int couponAmount;
        private Object sellerId;
        private String price;
        private boolean handleStatus;
        private int id;
        private int tmall;
        private String area;
        private List<String> images;
        private Object timeId;
        private boolean module;
        private Object couponUrl;
        private Object subCategoryId;
        private Object sellerLogo;
        private Object version;
        private boolean brandFlash;
        private String itemId;
        private double dse;
        private String name;
        private Object detailUrl;
        private boolean rushBuy;
        private int sellNum;
        private boolean needBuyed;
        private int maternalBaby;
        private double dsr;
        private boolean todayRecommended;
        private boolean flag;
        private int specialCategoryId;
        private double dss;
        private Object brandFlashName;
        private Object icon;
        private String discountPrice;
        private String sellerName;
        private Object moduleCategoryId;
        private String content;
        private boolean enabled;
        private Object virtualSales;
        private Object brandSellerId;
        private Object summary;
        private Object lastModifiedDate;
        private String outIcon;
        private int brandFlashId;
        private Object createdDate;
        private Object createTime;
        private Object taoToken;
        private String couponStartDate;
        private int categoryId;
        private  boolean collected;
        private String maxCommision;

        public String getMaxCommision() {
            return maxCommision;
        }

        public void setMaxCommision(String maxCommision) {
            this.maxCommision = maxCommision;
        }

        public boolean isCollected() {
            return collected;
        }

        public void setCollected(boolean collected) {
            this.collected = collected;
        }

        public boolean isFreeShip() {
            return freeShip;
        }

        public void setFreeShip(boolean freeShip) {
            this.freeShip = freeShip;
        }

        public String getCouponEndDate() {
            return couponEndDate;
        }

        public void setCouponEndDate(String couponEndDate) {
            this.couponEndDate = couponEndDate;
        }

        public String getOuterImages() {
            return outerImages;
        }

        public void setOuterImages(String outerImages) {
            this.outerImages = outerImages;
        }

        public boolean isType() {
            return type;
        }

        public void setType(boolean type) {
            this.type = type;
        }

        public double getCommision() {
            return commision;
        }

        public void setCommision(double commision) {
            this.commision = commision;
        }

        public int getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(int couponAmount) {
            this.couponAmount = couponAmount;
        }

        public Object getSellerId() {
            return sellerId;
        }

        public void setSellerId(Object sellerId) {
            this.sellerId = sellerId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public boolean isHandleStatus() {
            return handleStatus;
        }

        public void setHandleStatus(boolean handleStatus) {
            this.handleStatus = handleStatus;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTmall() {
            return tmall;
        }

        public void setTmall(int tmall) {
            this.tmall = tmall;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public Object getTimeId() {
            return timeId;
        }

        public void setTimeId(Object timeId) {
            this.timeId = timeId;
        }

        public boolean isModule() {
            return module;
        }

        public void setModule(boolean module) {
            this.module = module;
        }

        public Object getCouponUrl() {
            return couponUrl;
        }

        public void setCouponUrl(Object couponUrl) {
            this.couponUrl = couponUrl;
        }

        public Object getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(Object subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public Object getSellerLogo() {
            return sellerLogo;
        }

        public void setSellerLogo(Object sellerLogo) {
            this.sellerLogo = sellerLogo;
        }

        public Object getVersion() {
            return version;
        }

        public void setVersion(Object version) {
            this.version = version;
        }

        public boolean isBrandFlash() {
            return brandFlash;
        }

        public void setBrandFlash(boolean brandFlash) {
            this.brandFlash = brandFlash;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public double getDse() {
            return dse;
        }

        public void setDse(double dse) {
            this.dse = dse;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getDetailUrl() {
            return detailUrl;
        }

        public void setDetailUrl(Object detailUrl) {
            this.detailUrl = detailUrl;
        }

        public boolean isRushBuy() {
            return rushBuy;
        }

        public void setRushBuy(boolean rushBuy) {
            this.rushBuy = rushBuy;
        }

        public int getSellNum() {
            return sellNum;
        }

        public void setSellNum(int sellNum) {
            this.sellNum = sellNum;
        }

        public boolean isNeedBuyed() {
            return needBuyed;
        }

        public void setNeedBuyed(boolean needBuyed) {
            this.needBuyed = needBuyed;
        }

        public int getMaternalBaby() {
            return maternalBaby;
        }

        public void setMaternalBaby(int maternalBaby) {
            this.maternalBaby = maternalBaby;
        }

        public double getDsr() {
            return dsr;
        }

        public void setDsr(double dsr) {
            this.dsr = dsr;
        }

        public boolean isTodayRecommended() {
            return todayRecommended;
        }

        public void setTodayRecommended(boolean todayRecommended) {
            this.todayRecommended = todayRecommended;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public int getSpecialCategoryId() {
            return specialCategoryId;
        }

        public void setSpecialCategoryId(int specialCategoryId) {
            this.specialCategoryId = specialCategoryId;
        }

        public double getDss() {
            return dss;
        }

        public void setDss(double dss) {
            this.dss = dss;
        }

        public Object getBrandFlashName() {
            return brandFlashName;
        }

        public void setBrandFlashName(Object brandFlashName) {
            this.brandFlashName = brandFlashName;
        }

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
            this.icon = icon;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getSellerName() {
            return sellerName;
        }

        public void setSellerName(String sellerName) {
            this.sellerName = sellerName;
        }

        public Object getModuleCategoryId() {
            return moduleCategoryId;
        }

        public void setModuleCategoryId(Object moduleCategoryId) {
            this.moduleCategoryId = moduleCategoryId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public Object getVirtualSales() {
            return virtualSales;
        }

        public void setVirtualSales(Object virtualSales) {
            this.virtualSales = virtualSales;
        }

        public Object getBrandSellerId() {
            return brandSellerId;
        }

        public void setBrandSellerId(Object brandSellerId) {
            this.brandSellerId = brandSellerId;
        }

        public Object getSummary() {
            return summary;
        }

        public void setSummary(Object summary) {
            this.summary = summary;
        }

        public Object getLastModifiedDate() {
            return lastModifiedDate;
        }

        public void setLastModifiedDate(Object lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
        }

        public String getOutIcon() {
            return outIcon;
        }

        public void setOutIcon(String outIcon) {
            this.outIcon = outIcon;
        }

        public int getBrandFlashId() {
            return brandFlashId;
        }

        public void setBrandFlashId(int brandFlashId) {
            this.brandFlashId = brandFlashId;
        }

        public Object getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(Object createdDate) {
            this.createdDate = createdDate;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getTaoToken() {
            return taoToken;
        }

        public void setTaoToken(Object taoToken) {
            this.taoToken = taoToken;
        }

        public String getCouponStartDate() {
            return couponStartDate;
        }

        public void setCouponStartDate(String couponStartDate) {
            this.couponStartDate = couponStartDate;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }
    }
}

package com.newversions.tbk.entity;

import java.util.List;

public class TBKOrder {

    /**
     * msg : 请求成功
     * code : 1
     * taokeOrder : [{"orderType":"1","commissionRate":"6.03","icon":"","shopName":"可娇旗舰店","orderStatus":"4","itemInfo":"牛仔短裤女2019年夏天新款外穿粗腿显瘦网红高腰a字泫雅同款热裤","incomeRate":"6.03","mediaId":"258200457","categoryName":"女装","subsidyType":"--","thirdSource":"2","itemNum":1,"payAmount":"0.00","price":"219.00","handleStatus":2,"id":15947,"mediaName":"数一科技","image":"http://img.alicdn.com/bao/uploaded/i2/2200806333755/O1CN01WPvbqQ1dbplgz7gu2_!!2200806333755.jpg","subsidyRate":"0.00","adName":"syit_176","orderNo":"518578272159222743","lastModifiedDate":"","address_id":9,"clickTime":"2019-07-06 16:59:17","sellerWangwang":"可娇旗舰店","divideRate":"6.03","subsidyAmount":"0.00","version":1,"transactionPlatform":"无线","itemId":"594757654381","effectEstimate":"0.00","adId":"72478400496","createdDate":"","createTime":"2019-07-06 16:59:45","settlementTime":"","estimateIncome":"0.00","settlementAmount":"0.00","tradeId":"518578272159222743","importTime":"2019-07-06 18:05:55","commissionAmount":"0.00"}]
     */

    private String msg;
    private int code;
    private int pages;
    private List<TaokeOrderBean> taokeOrder;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

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

    public List<TaokeOrderBean> getTaokeOrder() {
        return taokeOrder;
    }

    public void setTaokeOrder(List<TaokeOrderBean> taokeOrder) {
        this.taokeOrder = taokeOrder;
    }

    public static class TaokeOrderBean {
        /**
         * orderType : 1
         * commissionRate : 6.03
         * icon :
         * shopName : 可娇旗舰店
         * orderStatus : 4
         * itemInfo : 牛仔短裤女2019年夏天新款外穿粗腿显瘦网红高腰a字泫雅同款热裤
         * incomeRate : 6.03
         * mediaId : 258200457
         * categoryName : 女装
         * subsidyType : --
         * thirdSource : 2
         * itemNum : 1
         * payAmount : 0.00
         * price : 219.00
         * handleStatus : 2
         * id : 15947
         * mediaName : 数一科技
         * image : http://img.alicdn.com/bao/uploaded/i2/2200806333755/O1CN01WPvbqQ1dbplgz7gu2_!!2200806333755.jpg
         * subsidyRate : 0.00
         * adName : syit_176
         * orderNo : 518578272159222743
         * lastModifiedDate :
         * address_id : 9
         * clickTime : 2019-07-06 16:59:17
         * sellerWangwang : 可娇旗舰店
         * divideRate : 6.03
         * subsidyAmount : 0.00
         * version : 1
         * transactionPlatform : 无线
         * itemId : 594757654381
         * effectEstimate : 0.00
         * adId : 72478400496
         * createdDate :
         * createTime : 2019-07-06 16:59:45
         * settlementTime :
         * estimateIncome : 0.00
         * settlementAmount : 0.00
         * tradeId : 518578272159222743
         * importTime : 2019-07-06 18:05:55
         * commissionAmount : 0.00
         */

        private String orderType;
        private String commissionRate;
        private String icon;
        private String shopName;
        private String orderStatus;
        private String itemInfo;
        private String incomeRate;
        private String mediaId;
        private String categoryName;
        private String subsidyType;
        private String thirdSource;
        private int itemNum;
        private String payAmount;
        private String price;
        private int handleStatus;
        private int id;
        private String mediaName;
        private String image;
        private String subsidyRate;
        private String adName;
        private String orderNo;
        private String lastModifiedDate;
        private int address_id;
        private String clickTime;
        private String sellerWangwang;
        private String divideRate;
        private String subsidyAmount;
        private int version;
        private String transactionPlatform;
        private String itemId;
        private String effectEstimate;
        private String adId;
        private String createdDate;
        private String createTime;
        private String settlementTime;
        private String estimateIncome;
        private String settlementAmount;
        private String tradeId;
        private String importTime;
        private String commissionAmount;

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getCommissionRate() {
            return commissionRate;
        }

        public void setCommissionRate(String commissionRate) {
            this.commissionRate = commissionRate;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getItemInfo() {
            return itemInfo;
        }

        public void setItemInfo(String itemInfo) {
            this.itemInfo = itemInfo;
        }

        public String getIncomeRate() {
            return incomeRate;
        }

        public void setIncomeRate(String incomeRate) {
            this.incomeRate = incomeRate;
        }

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getSubsidyType() {
            return subsidyType;
        }

        public void setSubsidyType(String subsidyType) {
            this.subsidyType = subsidyType;
        }

        public String getThirdSource() {
            return thirdSource;
        }

        public void setThirdSource(String thirdSource) {
            this.thirdSource = thirdSource;
        }

        public int getItemNum() {
            return itemNum;
        }

        public void setItemNum(int itemNum) {
            this.itemNum = itemNum;
        }

        public String getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(String payAmount) {
            this.payAmount = payAmount;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getHandleStatus() {
            return handleStatus;
        }

        public void setHandleStatus(int handleStatus) {
            this.handleStatus = handleStatus;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMediaName() {
            return mediaName;
        }

        public void setMediaName(String mediaName) {
            this.mediaName = mediaName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getSubsidyRate() {
            return subsidyRate;
        }

        public void setSubsidyRate(String subsidyRate) {
            this.subsidyRate = subsidyRate;
        }

        public String getAdName() {
            return adName;
        }

        public void setAdName(String adName) {
            this.adName = adName;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getLastModifiedDate() {
            return lastModifiedDate;
        }

        public void setLastModifiedDate(String lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
        }

        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }

        public String getClickTime() {
            return clickTime;
        }

        public void setClickTime(String clickTime) {
            this.clickTime = clickTime;
        }

        public String getSellerWangwang() {
            return sellerWangwang;
        }

        public void setSellerWangwang(String sellerWangwang) {
            this.sellerWangwang = sellerWangwang;
        }

        public String getDivideRate() {
            return divideRate;
        }

        public void setDivideRate(String divideRate) {
            this.divideRate = divideRate;
        }

        public String getSubsidyAmount() {
            return subsidyAmount;
        }

        public void setSubsidyAmount(String subsidyAmount) {
            this.subsidyAmount = subsidyAmount;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getTransactionPlatform() {
            return transactionPlatform;
        }

        public void setTransactionPlatform(String transactionPlatform) {
            this.transactionPlatform = transactionPlatform;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getEffectEstimate() {
            return effectEstimate;
        }

        public void setEffectEstimate(String effectEstimate) {
            this.effectEstimate = effectEstimate;
        }

        public String getAdId() {
            return adId;
        }

        public void setAdId(String adId) {
            this.adId = adId;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getSettlementTime() {
            return settlementTime;
        }

        public void setSettlementTime(String settlementTime) {
            this.settlementTime = settlementTime;
        }

        public String getEstimateIncome() {
            return estimateIncome;
        }

        public void setEstimateIncome(String estimateIncome) {
            this.estimateIncome = estimateIncome;
        }

        public String getSettlementAmount() {
            return settlementAmount;
        }

        public void setSettlementAmount(String settlementAmount) {
            this.settlementAmount = settlementAmount;
        }

        public String getTradeId() {
            return tradeId;
        }

        public void setTradeId(String tradeId) {
            this.tradeId = tradeId;
        }

        public String getImportTime() {
            return importTime;
        }

        public void setImportTime(String importTime) {
            this.importTime = importTime;
        }

        public String getCommissionAmount() {
            return commissionAmount;
        }

        public void setCommissionAmount(String commissionAmount) {
            this.commissionAmount = commissionAmount;
        }
    }
}

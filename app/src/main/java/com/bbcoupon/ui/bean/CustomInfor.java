package com.bbcoupon.ui.bean;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.bean
 * @DATE 2020/4/26
 */
public class CustomInfor {

    /**
     * msg : 请求成功
     * code : 1
     * platformList : {"title":"选品库商品","image":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1616134753,360647826&fm=26&gp=0
     * .jpg","content":"#db3428"}
     * commodityList : [{"itemId":547080273206,"couponInfo":"满22元减10元","price":"22.8","outIcon":"http:https://img.alicdn
     * .com/i2/3206032358/O1CN016dIyES1TI0VYJR5t9_!!0-item_pic.jpg","name":"麻辣烫底料四川成都特产臻鲜微辣牛油500g火锅料麻辣香锅底料包邮","sellerName":"臻鲜旗舰店",
     * "commision":12.08,"tmall":"1","sellNum":"3447"}]
     */

    private String msg;
    private int code;
    private PlatformListBean platformList;
    private List<CommodityListBean> commodityList;

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

    public PlatformListBean getPlatformList() {
        return platformList;
    }

    public void setPlatformList(PlatformListBean platformList) {
        this.platformList = platformList;
    }

    public List<CommodityListBean> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(List<CommodityListBean> commodityList) {
        this.commodityList = commodityList;
    }

    public static class PlatformListBean {
        /**
         * title : 选品库商品
         * image : https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1616134753,360647826&fm=26&gp=0.jpg
         * content : #db3428
         */

        private String title;
        private String image;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class CommodityListBean {
        /**
         * itemId : 547080273206
         * couponInfo : 满22元减10元
         * price : 22.8
         * outIcon : http:https://img.alicdn.com/i2/3206032358/O1CN016dIyES1TI0VYJR5t9_!!0-item_pic.jpg
         * name : 麻辣烫底料四川成都特产臻鲜微辣牛油500g火锅料麻辣香锅底料包邮
         * sellerName : 臻鲜旗舰店
         * commision : 12.08
         * tmall : 1
         * sellNum : 3447
         */

        private long itemId;
        private String couponInfo;
        private String price;
        private String outIcon;
        private String name;
        private String sellerName;
        private double commision;
        private String tmall;
        private String sellNum;

        public long getItemId() {
            return itemId;
        }

        public void setItemId(long itemId) {
            this.itemId = itemId;
        }

        public String getCouponInfo() {
            return couponInfo;
        }

        public void setCouponInfo(String couponInfo) {
            this.couponInfo = couponInfo;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOutIcon() {
            return outIcon;
        }

        public void setOutIcon(String outIcon) {
            this.outIcon = outIcon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSellerName() {
            return sellerName;
        }

        public void setSellerName(String sellerName) {
            this.sellerName = sellerName;
        }

        public double getCommision() {
            return commision;
        }

        public void setCommision(double commision) {
            this.commision = commision;
        }

        public String getTmall() {
            return tmall;
        }

        public void setTmall(String tmall) {
            this.tmall = tmall;
        }

        public String getSellNum() {
            return sellNum;
        }

        public void setSellNum(String sellNum) {
            this.sellNum = sellNum;
        }
    }
}

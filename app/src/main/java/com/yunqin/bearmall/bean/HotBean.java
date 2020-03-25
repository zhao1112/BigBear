package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.bean
 * @DATE 2020/3/21
 */
public class HotBean {




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
         * image : http://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202003/9.9.jpg
         * title : 9.9
         * content : #db3428
         */

        private String image;
        private String title;
        private String content;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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
         * itemId : 602326406173
         * couponAmount : 5.00
         * price : 14.80
         * outIcon : https://img.alicdn.com/bao/uploaded/i3/2204935314738/O1CN014blTJR1ks38DxXotC_!!0-item_pic.jpg
         * discountPrice : 9.80
         * name : Marquis网红款磨砂哑光可撕拉指甲油
         * sellerName : marquis旗舰店
         * commision : 1.0388
         * tmall : 1
         * sellNum : 6607
         */

        private String itemId;
        private String couponAmount;
        private String price;
        private String outIcon;
        private String discountPrice;
        private String name;
        private String sellerName;
        private double commision;
        private String tmall;
        private String sellNum;

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(String couponAmount) {
            this.couponAmount = couponAmount;
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

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
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

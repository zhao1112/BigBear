package com.yunqin.bearmall.bean;

import java.util.List;

public class SearchData {

    /**
     * msg : 请求成功
     * code : 1
     * data : [{"itemId":"596001049561","couponAmount":"15.00","price":"34.80","outIcon":"https://img.alicdn
     * .com/bao/uploaded/i4/3465172346/O1CN01jV3j9D1TCVl3rVb1x_!!0-item_pic.jpg","name":"纯棉牛油果绿t恤女宽松抹茶绿圆领","discountPrice":"19.80",
     * "sellerName":"偶约旗舰店","commision":2.0988,"tmall":"1","sellNum":"3053"},{"itemId":"586847456941","couponAmount":"20.00","price":"59
     * .00","outIcon":"https://img.alicdn.com/bao/uploaded/i4/3157541895/O1CN016XF7AI1Prx4j5UeGF_!!0-item_pic.jpg",
     * "name":"【牧都兰】纯棉ins潮圆领短袖两件装","discountPrice":"39.00","sellerName":"牧都兰服饰旗舰店","commision":6.201,"tmall":"1","sellNum":"65034"},{
     * "itemId":"587146422162","couponAmount":"5.00","price":"19.90","outIcon":"https://img.alicdn
     * .com/bao/uploaded/i1/3497339300/O1CN01Fobc2p2IZRzbiOO1b_!!0-item_pic.jpg","name":"爆款！10W月销！百搭简约T恤","discountPrice":"14.90",
     * "sellerName":"云稍旗舰店","commision":2.3691,"tmall":"1","sellNum":"48058"}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;
    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * itemId : 596001049561
         * couponAmount : 15.00
         * price : 34.80
         * outIcon : https://img.alicdn.com/bao/uploaded/i4/3465172346/O1CN01jV3j9D1TCVl3rVb1x_!!0-item_pic.jpg
         * name : 纯棉牛油果绿t恤女宽松抹茶绿圆领
         * discountPrice : 19.80
         * sellerName : 偶约旗舰店
         * commision : 2.0988
         * tmall : 1
         * sellNum : 3053
         */

        private String itemId;
        private String couponAmount;
        private String price;
        private String outIcon;
        private String name;
        private String discountPrice;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

    public static class ListBean {
        private String itemId;
        private String couponAmount;
        private String price;
        private String outIcon;
        private String name;
        private String discountPrice;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

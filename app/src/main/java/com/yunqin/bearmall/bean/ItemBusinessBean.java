package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.bean
 * @DATE 2020/3/30
 */
public class ItemBusinessBean {

    /**
     * msg : 请求成功
     * code : 1
     * data : [{"itemId":"595128095770","itemName":"3双 硅胶前脚掌垫袜加厚鞋垫女半码垫防滑前掌垫高跟鞋防磨脚神器11111111","images":"https://img.alicdn
     * .com/i3/3894739126/O1CN01S6izgd2HHl5IkLAKv_!!3894739126.jpg%7Chttps://img.alicdn
     * .com/i3/3894739126/O1CN01oM42R72HHl5IkOFg3_!!3894739126.jpg%7Chttps://img.alicdn
     * .com/i3/3894739126/O1CN01eftgSQ2HHl5KEJtr3_!!3894739126.jpg%7Chttps://img.alicdn
     * .com/i2/3894739126/O1CN01yV3NT82HHl5Htx7fo_!!3894739126.jpg,https://img.alicdn
     * .com/bao/uploaded/i1/3894739126/O1CN01paHRmx2HHl5LHt60I_!!0-item_pic.jpg","couponAmount":5,"taoMsg":"https://api.zhetaoke
     * .com:10001/api/open_sc_publisher_save.ashx?inviter_code=XPY3AP&backurl=https://testapi.bbcoupon
     * .cn/api/commodity/taobaoCallback&s_note=12506&type=0","createTime":"2020-03-28 15:04:48","price":"15.90","itemIcon":"https://img
     * .alicdn.com/bao/uploaded/i1/3894739126/O1CN01paHRmx2HHl5LHt60I_!!0-item_pic.jpg","id":5490896,
     * "itemDesc":"可以干吃的网红硅胶前脚掌垫，安卓吃一片，ios吃两片。早餐代餐选择，越吃越香。","commision":0.848,"shareNum":101}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

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
         * itemId : 595128095770
         * itemName : 3双 硅胶前脚掌垫袜加厚鞋垫女半码垫防滑前掌垫高跟鞋防磨脚神器11111111
         * images : https://img.alicdn.com/i3/3894739126/O1CN01S6izgd2HHl5IkLAKv_!!3894739126.jpg%7Chttps://img.alicdn
         * .com/i3/3894739126/O1CN01oM42R72HHl5IkOFg3_!!3894739126.jpg%7Chttps://img.alicdn
         * .com/i3/3894739126/O1CN01eftgSQ2HHl5KEJtr3_!!3894739126.jpg%7Chttps://img.alicdn
         * .com/i2/3894739126/O1CN01yV3NT82HHl5Htx7fo_!!3894739126.jpg,https://img.alicdn
         * .com/bao/uploaded/i1/3894739126/O1CN01paHRmx2HHl5LHt60I_!!0-item_pic.jpg
         * couponAmount : 5.0
         * taoMsg : https://api.zhetaoke.com:10001/api/open_sc_publisher_save.ashx?inviter_code=XPY3AP&backurl=https://testapi.bbcoupon
         * .cn/api/commodity/taobaoCallback&s_note=12506&type=0
         * createTime : 2020-03-28 15:04:48
         * price : 15.90
         * itemIcon : https://img.alicdn.com/bao/uploaded/i1/3894739126/O1CN01paHRmx2HHl5LHt60I_!!0-item_pic.jpg
         * id : 5490896
         * itemDesc : 可以干吃的网红硅胶前脚掌垫，安卓吃一片，ios吃两片。早餐代餐选择，越吃越香。
         * commision : 0.848
         * shareNum : 101
         */

        private String itemId;
        private String itemName;
        private String images;
        private double couponAmount;
        private String taoMsg;
        private String createTime;
        private String price;
        private String itemIcon;
        private int id;
        private String itemDesc;
        private double commision;
        private int shareNum;

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public double getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(double couponAmount) {
            this.couponAmount = couponAmount;
        }

        public String getTaoMsg() {
            return taoMsg;
        }

        public void setTaoMsg(String taoMsg) {
            this.taoMsg = taoMsg;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getItemIcon() {
            return itemIcon;
        }

        public void setItemIcon(String itemIcon) {
            this.itemIcon = itemIcon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getItemDesc() {
            return itemDesc;
        }

        public void setItemDesc(String itemDesc) {
            this.itemDesc = itemDesc;
        }

        public double getCommision() {
            return commision;
        }

        public void setCommision(double commision) {
            this.commision = commision;
        }

        public int getShareNum() {
            return shareNum;
        }

        public void setShareNum(int shareNum) {
            this.shareNum = shareNum;
        }
    }
}

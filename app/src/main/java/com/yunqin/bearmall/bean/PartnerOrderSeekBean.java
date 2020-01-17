package com.yunqin.bearmall.bean;

import java.util.List;

public class PartnerOrderSeekBean {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"orders":[{"image":"http://img.alicdn.com/bao/uploaded/i4/2932851384/O1CN018oWp8I1M5uedMZNw7_!!2932851384.jpg","orderNo":"554074658774735463","customerId":12393,"itemInfo":"安全裤防走光女夏蕾丝可内外穿三分保险裤不卷边短裤大码打底裤","settlementAmount":"0.00"},{"image":"http://img.alicdn.com/bao/uploaded/i3/2932851384/O1CN01CbYHb71M5uebkMM0G_!!2932851384.jpg","orderNo":"554074658774735463","customerId":12393,"itemInfo":"安全裤防走光女夏蕾丝可内外穿三分保险裤不卷边短裤大码打底裤","settlementAmount":"0.00"},{"image":"http://img.alicdn.com/bao/uploaded/i1/2932851384/O1CN01hLxXsI1M5ueaS6jSG_!!2932851384.jpg","orderNo":"554074658774735463","customerId":12393,"itemInfo":"安全裤防走光女夏蕾丝可内外穿三分保险裤不卷边短裤大码打底裤","settlementAmount":"0.00"},{"image":"http://img.alicdn.com/bao/uploaded/i4/710024899/O1CN01tSRDP81m3mrOD0fHP_!!710024899.jpg","orderNo":"554094464954710255","customerId":12393,"itemInfo":"孕妇内裤怀孕期高腰托腹内衣纯棉裤头初期孕晚期早期中期夏季薄款","settlementAmount":"0.00"},{"image":"http://img.alicdn.com/bao/uploaded/i2/4006792301/TB29IoSu98YBeNkSnb4XXaevFXa_!!4006792301-0-item_pic.jpg","orderNo":"554139072266735463","customerId":12393,"itemInfo":"光友酸辣粉105g*10袋 袋装红薯粉丝四川特产网红泡面速食方便面","settlementAmount":"0.00"},{"image":"http://img.alicdn.com/bao/uploaded/i4/2456067781/O1CN01FoIfXH27LkLI3Y036_!!2456067781.jpg","orderNo":"554618126420023710","customerId":12393,"itemInfo":"纸手表德国Papr Watch纸质黑科技防水智能手表新型创意 男学生女情侣cajiso新概念耐撕拉纸表","settlementAmount":"0.00"}]}
     */

    private String msg;
    private int code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<OrdersBean> orders;

        public List<OrdersBean> getOrders() {
            return orders;
        }

        public void setOrders(List<OrdersBean> orders) {
            this.orders = orders;
        }

        public static class OrdersBean {
            /**
             * image : http://img.alicdn.com/bao/uploaded/i4/2932851384/O1CN018oWp8I1M5uedMZNw7_!!2932851384.jpg
             * orderNo : 554074658774735463
             * customerId : 12393
             * itemInfo : 安全裤防走光女夏蕾丝可内外穿三分保险裤不卷边短裤大码打底裤
             * settlementAmount : 0.00
             */

            private String image;
            private String orderNo;
            private int customerId;
            private String itemInfo;
            private String settlementAmount;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public String getItemInfo() {
                return itemInfo;
            }

            public void setItemInfo(String itemInfo) {
                this.itemInfo = itemInfo;
            }

            public String getSettlementAmount() {
                return settlementAmount;
            }

            public void setSettlementAmount(String settlementAmount) {
                this.settlementAmount = settlementAmount;
            }
        }
    }
}

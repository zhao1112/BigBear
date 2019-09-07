package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author AYWang
 * @create 2018/7/26
 * @Describe
 */
public class WaitCommentBean {

    /**
     * data : {" itemList":[{"orderitem_id":10101,"itemName":"苹果(Apple) iPhone 8 Plus","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/92aac5d6-a59f-49e9-8920-472c674abff4_thumbnail.jpg","orders_id":10101},{"orderitem_id":10104,"itemName":"小米(MI) 5C","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/1c6d0aa6-4d8c-433f-84cc-9319565b504f_thumbnail.jpg","orders_id":10104}],"has_more":0}
     * code : 1
     * msg : 请求成功
     */

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
        /**
         *  itemList : [{"orderitem_id":10101,"itemName":"苹果(Apple) iPhone 8 Plus","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/92aac5d6-a59f-49e9-8920-472c674abff4_thumbnail.jpg","orders_id":10101},{"orderitem_id":10104,"itemName":"小米(MI) 5C","thumbnail":"http://image.demo.b2b2c.shopxx.net/6.0/1c6d0aa6-4d8c-433f-84cc-9319565b504f_thumbnail.jpg","orders_id":10104}]
         * has_more : 0
         */

        private int has_more;
        private List<itemListBean> itemList;

        public List<itemListBean> getItemList() {
            return itemList;
        }

        public void setItemList(List<itemListBean> itemList) {
            this.itemList = itemList;
        }

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public static class itemListBean {
            /**
             * orderitem_id : 10101
             * itemName : 苹果(Apple) iPhone 8 Plus
             * thumbnail : http://image.demo.b2b2c.shopxx.net/6.0/92aac5d6-a59f-49e9-8920-472c674abff4_thumbnail.jpg
             * orders_id : 10101
             */

            private int orderitem_id;
            private String itemName;
            private String thumbnail;
            private int orders_id;

            public int getOrderitem_id() {
                return orderitem_id;
            }

            public void setOrderitem_id(int orderitem_id) {
                this.orderitem_id = orderitem_id;
            }

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public int getOrders_id() {
                return orders_id;
            }

            public void setOrders_id(int orders_id) {
                this.orders_id = orders_id;
            }
        }
    }
}

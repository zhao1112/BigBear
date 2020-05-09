package com.yunqin.bearmall.bean;

import android.widget.TextView;

import com.newversions.tbk.entity.GoodDetailEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.bean
 * @DATE 2019/11/27
 */
public class TaoBaoBeanNew {

    /**
     * msg : 请求成功
     * code : 1
     * data : [{"effectEstimate":0.15,"payAmount":"2.00","taoOrders_id":"552710050489498045","createTime":"2019-09-01 19:23:03",
     * "imageUrl":"http://img.alicdn.com/bao/uploaded/i3/3985160624/O1CN011GTpcRwIgQ5v8mu_!!0-item_pic.jpg","orderStatus":"1",
     * "itemInfo":"定做广告笔中性笔签字笔定制订制碳素水笔可定制印刷LOGO刻字"},{"effectEstimate":0.21,"payAmount":"1.90","taoOrders_id":"552630753339498045",
     * "createTime":"2019-09-01 17:50:55","imageUrl":"http://img.alicdn
     * .com/bao/uploaded/i3/3320545807/O1CN01VDg4d21sleV56x9R9_!!3320545807.jpg","orderStatus":"1",
     * "itemInfo":"得力油画棒36色儿童彩色蜡笔幼儿安全无毒蜡笔画画笔油彩笔包邮24色蜡笔儿童安全无毒可水洗宝宝画画笔48色"},{"effectEstimate":11.22,"payAmount":"89.00",
     * "taoOrders_id":"552767277233023710","createTime":"2019-08-16 16:17:02","imageUrl":"http://img.alicdn
     * .com/bao/uploaded/i4/4139376508/O1CN011xwi3oyT5C8MV3V_!!4139376508.png","orderStatus":"1",
     * "itemInfo":"小唱唱吧麦克风全民K歌手机麦克风话筒家用手机K歌神器生日礼物"}]
     */

    private String msg;
    private int code;
    private int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

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
         * effectEstimate : 0.15
         * payAmount : 2.00
         * taoOrders_id : 552710050489498045
         * createTime : 2019-09-01 19:23:03
         * imageUrl : http://img.alicdn.com/bao/uploaded/i3/3985160624/O1CN011GTpcRwIgQ5v8mu_!!0-item_pic.jpg
         * orderStatus : 1
         * itemInfo : 定做广告笔中性笔签字笔定制订制碳素水笔可定制印刷LOGO刻字
         */

        private String effectEstimate;
        private String payAmount;
        private String taoOrders_id;
        private String createTime;
        private String imageUrl;
        private String orderStatus;
        private String itemInfo;
        private Active active;

        public Active getActive() {
            return active;
        }

        public void setActive(Active active) {
            this.active = active;
        }

        public String getEffectEstimate() {
            return effectEstimate;
        }

        public void setEffectEstimate(String effectEstimate) {
            this.effectEstimate = effectEstimate;
        }

        public String getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(String payAmount) {
            this.payAmount = payAmount;
        }

        public String getTaoOrders_id() {
            return taoOrders_id;
        }

        public void setTaoOrders_id(String taoOrders_id) {
            this.taoOrders_id = taoOrders_id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
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

        public static class Active {
            private int power;
            private int isActive;
            private String activeCommission;

            public String getActiveCommission() {
                return activeCommission;
            }

            public void setActiveCommission(String activeCommission) {
                this.activeCommission = activeCommission;
            }

            public int getPower() {
                return power;
            }

            public void setPower(int power) {
                this.power = power;
            }

            public int getIsActive() {
                return isActive;
            }

            public void setIsActive(int isActive) {
                this.isActive = isActive;
            }
        }
    }
}

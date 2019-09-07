package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author Master
 * @create 2018/8/2 19:57
 */
public class KdBean {

    /**
     * data : {"shippingMethodList":[{"deliveryCorp_name":"圆通","shippingMethod_id":1,"deliveryCorp_id":2,"description":"系统将根据您的收货地址自动匹配快递公司进行配送，享受免运费服务","shippingMethod_name":"普通快递"},{"deliveryCorp_name":"顺丰","shippingMethod_id":2,"deliveryCorp_id":1,"description":null,"shippingMethod_name":"顺丰快递"}]}
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
        private List<ShippingMethodListBean> shippingMethodList;

        public List<ShippingMethodListBean> getShippingMethodList() {
            return shippingMethodList;
        }

        public void setShippingMethodList(List<ShippingMethodListBean> shippingMethodList) {
            this.shippingMethodList = shippingMethodList;
        }

        public static class ShippingMethodListBean {
            /**
             * deliveryCorp_name : 圆通
             * shippingMethod_id : 1
             * deliveryCorp_id : 2
             * description : 系统将根据您的收货地址自动匹配快递公司进行配送，享受免运费服务
             * shippingMethod_name : 普通快递
             */

            private String deliveryCorp_name;
            private int shippingMethod_id;
            private int deliveryCorp_id;
            private String description;
            private String shippingMethod_name;

            public String getDeliveryCorp_name() {
                return deliveryCorp_name;
            }

            public void setDeliveryCorp_name(String deliveryCorp_name) {
                this.deliveryCorp_name = deliveryCorp_name;
            }

            public int getShippingMethod_id() {
                return shippingMethod_id;
            }

            public void setShippingMethod_id(int shippingMethod_id) {
                this.shippingMethod_id = shippingMethod_id;
            }

            public int getDeliveryCorp_id() {
                return deliveryCorp_id;
            }

            public void setDeliveryCorp_id(int deliveryCorp_id) {
                this.deliveryCorp_id = deliveryCorp_id;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getShippingMethod_name() {
                return shippingMethod_name;
            }

            public void setShippingMethod_name(String shippingMethod_name) {
                this.shippingMethod_name = shippingMethod_name;
            }
        }
    }
}

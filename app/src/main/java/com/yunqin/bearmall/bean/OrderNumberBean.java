package com.yunqin.bearmall.bean;

/**
 * @author AYWang
 * @create 2018/9/19
 * @Describe
 */
public class OrderNumberBean {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"ordersNumRecord":{"waitPayNum":0,"waitSendNum":0,"afterSalesNum":0,"waitReceiveNum":0,"waitAssNum":0}}
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
        /**
         * ordersNumRecord : {"waitPayNum":0,"waitSendNum":0,"afterSalesNum":0,"waitReceiveNum":0,"waitAssNum":0}
         */

        private OrdersNumRecordBean ordersNumRecord;

        public OrdersNumRecordBean getOrdersNumRecord() {
            return ordersNumRecord;
        }

        public void setOrdersNumRecord(OrdersNumRecordBean ordersNumRecord) {
            this.ordersNumRecord = ordersNumRecord;
        }

        public static class OrdersNumRecordBean {
            /**
             * waitPayNum : 0
             * waitSendNum : 0
             * afterSalesNum : 0
             * waitReceiveNum : 0
             * waitAssNum : 0
             */

            private int waitPayNum;
            private int waitSendNum;
            private int afterSalesNum;
            private int waitReceiveNum;
            private int waitAssNum;

            public int getWaitPayNum() {
                return waitPayNum;
            }

            public void setWaitPayNum(int waitPayNum) {
                this.waitPayNum = waitPayNum;
            }

            public int getWaitSendNum() {
                return waitSendNum;
            }

            public void setWaitSendNum(int waitSendNum) {
                this.waitSendNum = waitSendNum;
            }

            public int getAfterSalesNum() {
                return afterSalesNum;
            }

            public void setAfterSalesNum(int afterSalesNum) {
                this.afterSalesNum = afterSalesNum;
            }

            public int getWaitReceiveNum() {
                return waitReceiveNum;
            }

            public void setWaitReceiveNum(int waitReceiveNum) {
                this.waitReceiveNum = waitReceiveNum;
            }

            public int getWaitAssNum() {
                return waitAssNum;
            }

            public void setWaitAssNum(int waitAssNum) {
                this.waitAssNum = waitAssNum;
            }
        }
    }
}

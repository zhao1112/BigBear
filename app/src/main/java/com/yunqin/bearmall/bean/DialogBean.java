package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author Master
 * @create 2018/7/30 12:30
 */
public class DialogBean {

    /**
     * data : {"reason":[{"reasonCode":0,"reasonValue":"不喜欢/拍错"},{"reasonCode":1,"reasonValue":"质量问题"},{"reasonCode":2,"reasonValue":"退运费"},{"reasonCode":3,"reasonValue":"卖家发错货、漏发"},{"reasonCode":4,"reasonValue":"假冒品牌"},{"reasonCode":5,"reasonValue":"其他"}]}
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
        private List<ReasonBean> reason;

        public List<ReasonBean> getReason() {
            return reason;
        }

        public void setReason(List<ReasonBean> reason) {
            this.reason = reason;
        }

        public static class ReasonBean {
            /**
             * reasonCode : 0
             * reasonValue : 不喜欢/拍错
             */

            private int reasonCode;
            private String reasonValue;

            public int getReasonCode() {
                return reasonCode;
            }

            public void setReasonCode(int reasonCode) {
                this.reasonCode = reasonCode;
            }

            public String getReasonValue() {
                return reasonValue;
            }

            public void setReasonValue(String reasonValue) {
                this.reasonValue = reasonValue;
            }
        }
    }
}

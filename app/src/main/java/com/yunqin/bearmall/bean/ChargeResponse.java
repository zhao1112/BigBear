package com.yunqin.bearmall.bean;

import java.util.List;

public class ChargeResponse extends BaseResponseBean {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{

        private String mobile;
        private int isMs;
        private int usableTicketCount;
        private int carrierType;
        private List<Charge> list;

        public List<Charge> getList() {
            return list;
        }

        public void setList(List<Charge> list) {
            this.list = list;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getIsMs() {
            return isMs;
        }

        public void setIsMs(int isMs) {
            this.isMs = isMs;
        }

        public int getUsableTicketCount() {
            return usableTicketCount;
        }

        public void setUsableTicketCount(int usableTicketCount) {
            this.usableTicketCount = usableTicketCount;
        }

        public int getCarrierType() {
            return carrierType;
        }

        public void setCarrierType(int carrierType) {
            this.carrierType = carrierType;
        }
    }


}

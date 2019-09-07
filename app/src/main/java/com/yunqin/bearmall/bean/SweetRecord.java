package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * Created by chenchen on 2018/7/21.
 */

public class SweetRecord {

    private int code;

    private String message;

    private DataBean data;

    public boolean isSuccese(){

        return code == 1;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{

        private List<RecordBean> incomeDetail;

        private List<RecordBean> purchaseDetail;

        private int has_more;

        private int incomeCount;

        private int purchaseCount;

        public int getPurchaseCount() {
            return purchaseCount;
        }

        public void setPurchaseCount(int purchaseCount) {
            this.purchaseCount = purchaseCount;
        }

        public List<RecordBean> getIncomeDetail() {
            return incomeDetail;
        }

        public boolean hasMore(){

            return has_more == 1;
        }

        public void setIncomeDetail(List<RecordBean> incomeDetail) {
            this.incomeDetail = incomeDetail;
        }

        public List<RecordBean> getPurchaseDetail() {
            return purchaseDetail;
        }

        public void setPurchaseDetail(List<RecordBean> purchaseDetail) {
            this.purchaseDetail = purchaseDetail;
        }

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public int getIncomeCount() {
            return incomeCount;
        }

        public void setIncomeCount(int incomeCount) {
            this.incomeCount = incomeCount;
        }
    }



    public static class RecordBean{

        private String memo;

        private String credit;

        private String createdDate;

        private String debit;

        public String getDebit() {
            return debit;
        }

        public void setDebit(String debit) {
            this.debit = debit;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }
    }


}

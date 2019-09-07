package com.yunqin.bearmall.bean;

public class Balance {


    private String createdDate;
    private String balanceChange;
    private String detail;
    private long source_id;
    private int type;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getBalanceChange() {
        return balanceChange;
    }

    public void setBalanceChange(String balanceChange) {
        this.balanceChange = balanceChange;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getSource_id() {
        return source_id;
    }

    public void setSource_id(long source_id) {
        this.source_id = source_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

package com.yunqin.bearmall.bean;

import java.util.List;

public class VipItem {

    private boolean isAllowfirstCharge;
    private String showPrice;
    private String name;
    private String firstChargePrice;
    private int memberGrade_id;
    private String showDiscount;
    private String realPrice;
    private List<RightDesc> rightsDec;
    private List<RightDesc> renewRules;


    public boolean isAllowfirstCharge() {
        return isAllowfirstCharge;
    }

    public void setAllowfirstCharge(boolean allowfirstCharge) {
        isAllowfirstCharge = allowfirstCharge;
    }

    public String getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(String showPrice) {
        this.showPrice = showPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstChargePrice() {
        return firstChargePrice;
    }

    public void setFirstChargePrice(String firstChargePrice) {
        this.firstChargePrice = firstChargePrice;
    }

    public int getMemberGrade_id() {
        return memberGrade_id;
    }

    public void setMemberGrade_id(int memberGrade_id) {
        this.memberGrade_id = memberGrade_id;
    }

    public String getShowDiscount() {
        return showDiscount;
    }

    public void setShowDiscount(String showDiscount) {
        this.showDiscount = showDiscount;
    }

    public List<RightDesc> getRightDesc() {
        return rightsDec;
    }

    public void setRightDesc(List<RightDesc> rightDesc) {
        this.rightsDec = rightDesc;
    }

    public List<RightDesc> getRenewRules() {
        return renewRules;
    }

    public void setRenewRules(List<RightDesc> renewRules) {
        this.renewRules = renewRules;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }


}

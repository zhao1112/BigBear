package com.yunqin.bearmall.bean;

import java.util.List;

public class InviteBean {

    private int specInviteUsableCount;
    private String showPrice;
    private String name;
    private long memberGrade_id;
    private String realPrice;
    private List<RightDesc> rightsDec;

    public int getSpecInviteUsableCount() {
        return specInviteUsableCount;
    }

    public void setSpecInviteUsableCount(int specInviteUsableCount) {
        this.specInviteUsableCount = specInviteUsableCount;
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

    public long getMemberGrade_id() {
        return memberGrade_id;
    }

    public void setMemberGrade_id(long memberGrade_id) {
        this.memberGrade_id = memberGrade_id;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }

    public List<RightDesc> getRightsDec() {
        return rightsDec;
    }

    public void setRightsDec(List<RightDesc> rightsDec) {
        this.rightsDec = rightsDec;
    }
}

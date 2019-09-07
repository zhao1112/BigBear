package com.yunqin.bearmall.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Charge implements Parcelable {

    private boolean isSelect;
    private int type;

    private String title;
    private String membershipPrice;
    private String payPrice;
    private int isAllow;
    private long virtualRechargeProudct_id;
    private String img;
    private String code;
    private String denomination;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMembershipPrice() {
        return membershipPrice;
    }

    public void setMembershipPrice(String membershipPrice) {
        this.membershipPrice = membershipPrice;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public int getIsAllow() {
        return isAllow;
    }

    public void setIsAllow(int isAllow) {
        this.isAllow = isAllow;
    }

    public long getVirtualRechargeProudct_id() {
        return virtualRechargeProudct_id;
    }

    public void setVirtualRechargeProudct_id(long virtualRechargeProudct_id) {
        this.virtualRechargeProudct_id = virtualRechargeProudct_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        dest.writeInt(this.type);
        dest.writeString(this.title);
        dest.writeString(this.membershipPrice);
        dest.writeString(this.payPrice);
        dest.writeInt(this.isAllow);
        dest.writeLong(this.virtualRechargeProudct_id);
        dest.writeString(this.img);
        dest.writeString(this.code);
        dest.writeString(this.denomination);
    }

    public Charge() {
    }

    protected Charge(Parcel in) {
        this.isSelect = in.readByte() != 0;
        this.type = in.readInt();
        this.title = in.readString();
        this.membershipPrice = in.readString();
        this.payPrice = in.readString();
        this.isAllow = in.readInt();
        this.virtualRechargeProudct_id = in.readLong();
        this.img = in.readString();
        this.code = in.readString();
        this.denomination = in.readString();
    }

    public static final Creator<Charge> CREATOR = new Creator<Charge>() {
        @Override
        public Charge createFromParcel(Parcel source) {
            return new Charge(source);
        }

        @Override
        public Charge[] newArray(int size) {
            return new Charge[size];
        }
    };
}

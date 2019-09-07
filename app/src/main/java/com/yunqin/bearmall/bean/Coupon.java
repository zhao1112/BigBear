package com.yunqin.bearmall.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Coupon implements Parcelable {

    private long usersTicket_id;
    private String limitDate;
    private int discountType;
    private String discountValue;
    private int status;
    private int type;
    private String createdDate;
    private int expireDays;
    private String title;
    private String limitStartDate;
    private boolean isUsable;

    public String getLimitStartDate() {
        return limitStartDate;
    }

    public void setLimitStartDate(String limitStartDate) {
        this.limitStartDate = limitStartDate;
    }

    public boolean isUsable() {
        return isUsable;
    }

    public void setUsable(boolean usable) {
        isUsable = usable;
    }

    public long getUsersTicket_id() {
        return usersTicket_id;
    }

    public void setUsersTicket_id(long usersTicket_id) {
        this.usersTicket_id = usersTicket_id;
    }

    public String getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(String limitDate) {
        this.limitDate = limitDate;
    }

    public int getDiscountType() {
        return discountType;
    }

    public void setDiscountType(int discountType) {
        this.discountType = discountType;
    }

    public String getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(String discountValue) {
        this.discountValue = discountValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getExpireDays() {
        return expireDays;
    }

    public void setExpireDays(int expireDays) {
        this.expireDays = expireDays;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.usersTicket_id);
        dest.writeString(this.limitDate);
        dest.writeInt(this.discountType);
        dest.writeString(this.discountValue);
        dest.writeInt(this.status);
        dest.writeInt(this.type);
        dest.writeString(this.createdDate);
        dest.writeInt(this.expireDays);
        dest.writeString(this.title);
    }

    public Coupon() {
    }

    protected Coupon(Parcel in) {
        this.usersTicket_id = in.readLong();
        this.limitDate = in.readString();
        this.discountType = in.readInt();
        this.discountValue = in.readString();
        this.status = in.readInt();
        this.type = in.readInt();
        this.createdDate = in.readString();
        this.expireDays = in.readInt();
        this.title = in.readString();
    }

    public static final Creator<Coupon> CREATOR = new Creator<Coupon>() {
        @Override
        public Coupon createFromParcel(Parcel source) {
            return new Coupon(source);
        }

        @Override
        public Coupon[] newArray(int size) {
            return new Coupon[size];
        }
    };
}

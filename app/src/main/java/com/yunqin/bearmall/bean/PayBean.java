package com.yunqin.bearmall.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PayBean implements Parcelable {


    private int ordersId;
    private String amount;
    private String outTradeNo;
    private int isNeedPay;
    private String btAmount;

    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public int getIsNeedPay() {
        return isNeedPay;
    }

    public void setIsNeedPay(int isNeedPay) {
        this.isNeedPay = isNeedPay;
    }

    public String getBtAmount() {
        return btAmount;
    }

    public void setBtAmount(String btAmount) {
        this.btAmount = btAmount;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ordersId);
        dest.writeString(this.amount);
        dest.writeString(this.outTradeNo);
        dest.writeInt(this.isNeedPay);
        dest.writeString(this.btAmount);
    }

    public PayBean() {
    }

    protected PayBean(Parcel in) {
        this.ordersId = in.readInt();
        this.amount = in.readString();
        this.outTradeNo = in.readString();
        this.isNeedPay = in.readInt();
        this.btAmount = in.readString();
    }

    public static final Creator<PayBean> CREATOR = new Creator<PayBean>() {
        @Override
        public PayBean createFromParcel(Parcel source) {
            return new PayBean(source);
        }

        @Override
        public PayBean[] newArray(int size) {
            return new PayBean[size];
        }
    };
}

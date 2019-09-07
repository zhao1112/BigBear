package com.newversions.tbk.entity;

import java.io.Serializable;

public class LikeGuessEntity implements Serializable {

    /**
     * mineCommision : 0.7
     * itemId : 548094662163
     * name : 买2送10副】李良济四物汤原料女袋泡茶八珍汤煲汤中药材白芍当归
     * price : 39.8
     * discountPrice : 39.8
     * couponAmount : 0
     */

    private String mineCommision;
    private String itemId;
    private String name;
    private String price;
    private String discountPrice;
    private String couponAmount;
    private String outIcon;
    private String couponStartDate;
    private String couponEndDate;
    private int sellNum;
    private String nick;
    /**商品描述分*/
    private double dsr;
    /**卖家服务*/
    private double dse;
    /**物流服务*/
    private double dss;
    /**商家图片*/
    private String sellerLogo;
    private int tmall;

    public int getTmall() {
        return tmall;
    }

    public void setTmall(int tmall) {
        this.tmall = tmall;
    }

    public double getDsr() {
        return dsr;
    }

    public void setDsr(double dsr) {
        this.dsr = dsr;
    }

    public double getDse() {
        return dse;
    }

    public void setDse(double dse) {
        this.dse = dse;
    }

    public double getDss() {
        return dss;
    }

    public void setDss(double dss) {
        this.dss = dss;
    }

    public String getSellerLogo() {
        return sellerLogo;
    }

    public void setSellerLogo(String sellerLogo) {
        this.sellerLogo = sellerLogo;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getSellNum() {
        return sellNum;
    }

    public void setSellNum(int sellNum) {
        this.sellNum = sellNum;
    }

    public String getCouponStartDate() {
        return couponStartDate;
    }

    public void setCouponStartDate(String couponStartDate) {
        this.couponStartDate = couponStartDate;
    }

    public String getCouponEndDate() {
        return couponEndDate;
    }

    public void setCouponEndDate(String couponEndDate) {
        this.couponEndDate = couponEndDate;
    }

    public String getOutIcon() {
        return outIcon;
    }

    public void setOutIcon(String outIcon) {
        this.outIcon = outIcon;
    }

    public String getMineCommision() {
        return mineCommision;
    }

    public void setMineCommision(String mineCommision) {
        this.mineCommision = mineCommision;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }
}

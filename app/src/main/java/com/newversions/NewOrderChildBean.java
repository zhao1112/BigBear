package com.newversions;

import java.io.Serializable;

/**
 * Create By Master
 * On 2019/1/21 12:21
 */
public class NewOrderChildBean implements Serializable {


    private String name;
    private String imgUrl;
    private String price;
    private String guiGe;
    private String count;
    private int productId;

    private int skuId;
    private int iMap;

    public NewOrderChildBean(String name, String imgUrl, String price, String guiGe, String count, int productId, int skuId, int iMap) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
        this.guiGe = guiGe;
        this.count = count;
        this.productId = productId;
        this.skuId = skuId;
        this.iMap = iMap;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public int getiMap() {
        return iMap;
    }

    public void setiMap(int iMap) {
        this.iMap = iMap;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGuiGe() {
        return guiGe;
    }

    public void setGuiGe(String guiGe) {
        this.guiGe = guiGe;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


}

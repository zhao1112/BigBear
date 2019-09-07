package com.newversions;

import java.io.Serializable;
import java.util.List;

/**
 * Create By Master
 * On 2019/1/21 12:21
 */
public class NewOrderBean implements Serializable {

    private String name;
    private String imgUrl;
    private List<NewOrderChildBean> childBeans;


    public NewOrderBean(String name, String imgUrl, List<NewOrderChildBean> childBeans) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.childBeans = childBeans;
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

    public List<NewOrderChildBean> getChildBeans() {
        return childBeans;
    }

    public void setChildBeans(List<NewOrderChildBean> childBeans) {
        this.childBeans = childBeans;
    }
}

package com.bbcoupon.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.base
 * @DATE 2020/5/6
 */
public class ImageSelectInfor implements Serializable {

    private List<ImageBean> imageBean;

    public List<ImageBean> getImageBean() {
        return imageBean;
    }

    public void setImageBean(List<ImageBean> imageBean) {
        this.imageBean = imageBean;
    }

    public static class ImageBean implements Serializable {
        private boolean isSelect;
        private String image;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}

package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author Master
 * @create 2018/8/21 16:26
 */
public class FiltrateBean {

    private int attrIndex;
    private List<Bean> options;
    private String attrName;


    public int getAttrIndex() {
        return attrIndex;
    }

    public void setAttrIndex(int attrIndex) {
        this.attrIndex = attrIndex;
    }

    public List<Bean> getOptions() {
        return options;
    }

    public void setOptions(List<Bean> options) {
        this.options = options;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public static class Bean {
        private int attrIndex;
        private String optionsName;
        private boolean checked = false;

        public int getAttrIndex() {
            return attrIndex;
        }

        public void setAttrIndex(int attrIndex) {
            this.attrIndex = attrIndex;
        }

        public String getOptionsName() {
            return optionsName;
        }

        public void setOptionsName(String optionsName) {
            this.optionsName = optionsName;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }


}

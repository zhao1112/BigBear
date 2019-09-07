package com.yunqin.bearmall.bean;

/**
 * Create By Master
 * On 2019/3/27 15:26
 */
public class PopBean {

    private String msg;
    private int code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private PopupAdBean popupAd;

        public PopupAdBean getPopupAd() {
            return popupAd;
        }

        public void setPopupAd(PopupAdBean popupAd) {
            this.popupAd = popupAd;
        }

        public static class PopupAdBean {

            private long popupAd_id;
            private String img;
            private int skipType;
            private long source_id;
            private int type;

            public long getPopupAd_id() {
                return popupAd_id;
            }

            public void setPopupAd_id(long popupAd_id) {
                this.popupAd_id = popupAd_id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getSkipType() {
                return skipType;
            }

            public void setSkipType(int skipType) {
                this.skipType = skipType;
            }

            public long getSource_id() {
                return source_id;
            }

            public void setSource_id(long source_id) {
                this.source_id = source_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}

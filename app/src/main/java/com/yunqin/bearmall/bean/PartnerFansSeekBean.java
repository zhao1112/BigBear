package com.yunqin.bearmall.bean;

import java.util.List;

public class PartnerFansSeekBean {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"fans":[{"createdDate":"2019-12-12 15:10:16","level":1,"mobile":"18576684975","id":12392,"iconUrl":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/20180816140417.png"}]}
     */

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
        private List<FansBean> fans;

        public List<FansBean> getFans() {
            return fans;
        }

        public void setFans(List<FansBean> fans) {
            this.fans = fans;
        }

        public static class FansBean {
            /**
             * createdDate : 2019-12-12 15:10:16
             * level : 1
             * mobile : 18576684975
             * id : 12392
             * iconUrl : https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/20180816140417.png
             */

            private String createdDate;
            private int level;
            private String mobile;
            private int id;
            private String iconUrl;

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }
        }
    }
}

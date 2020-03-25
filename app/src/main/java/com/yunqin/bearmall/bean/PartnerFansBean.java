package com.yunqin.bearmall.bean;

import java.util.List;

public class PartnerFansBean {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"fans":[{"createdDate":"2020-03-24 10:18:03","weixin":"Ryantaoyf","level":0,"mobile":"19937621228","id":12503,
     * "iconUrl":"http://thirdwx.qlogo
     * .cn/mmopen/vi_32/Q84LDm8PxTibyWbTrekiatCcF6ibuJ84uBS67bqkUfMNgPC8YiagPiaocez0D1CiaWwWic1UA8PJrkBqBxTVxLicYv92ug/132"},{
     * "createdDate":"2020-03-23 19:46:54","weixin":"here-fei","level":0,"mobile":"13691417122","id":12502,"iconUrl":"http://thirdwx
     * .qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIpUVbgxubOkLo6jmS7Xdh77sadlPhOjybSaT3wGm2fYqxDmOetVloO5b0LEc0k3zicHnJUCk9pvOA/132"},{"createdDate
     * ":"2020-03-16 20:37:46","weixin":null,"level":0,"mobile":"18576684975777","id":12495,"iconUrl":"https://wx.qlogo
     * .cn/mmopen/vi_32/Q0j4TwGTfTLicZJ5PS6Q3liaG2PWibkv2rSf5UCuibibNkKFz6OrTWNGJSCUOQQbIFa7dCqtHGs0AsT27wRXNZiaZsTA/132"},{"createdDate
     * ":"2020-03-16 20:33:54","weixin":null,"level":0,"mobile":"185766849756666","id":12494,"iconUrl":"https://wx.qlogo
     * .cn/mmopen/vi_32/Q0j4TwGTfTLicZJ5PS6Q3liaG2PWibkv2rSf5UCuibibNkKFz6OrTWNGJSCUOQQbIFa7dCqtHGs0AsT27wRXNZiaZsTA/132"},{"createdDate
     * ":"2020-03-16 20:27:22","weixin":null,"level":0,"mobile":"185766849755556","id":12493,"iconUrl":"https://wx.qlogo
     * .cn/mmopen/vi_32/Q0j4TwGTfTLicZJ5PS6Q3liaG2PWibkv2rSf5UCuibibNkKFz6OrTWNGJSCUOQQbIFa7dCqtHGs0AsT27wRXNZiaZsTA/132"},{"createdDate
     * ":"2020-03-16 20:26:20","weixin":null,"level":0,"mobile":"1523681325211","id":12492,"iconUrl":"https://wx.qlogo
     * .cn/mmopen/vi_32/DYAIOgq83erVViboZRicLI5rpN1ecZqwHG0S9aBDSuzJw0HYlTl7S6lvUbvRlsG5Ua8XYiaVFQbCAC2RTuLrsa6qA/132"},{"createdDate
     * ":"2020-03-16 20:24:37","weixin":null,"level":0,"mobile":"18576684975555","id":12491,"iconUrl":"https://wx.qlogo
     * .cn/mmopen/vi_32/Q0j4TwGTfTLicZJ5PS6Q3liaG2PWibkv2rSf5UCuibibNkKFz6OrTWNGJSCUOQQbIFa7dCqtHGs0AsT27wRXNZiaZsTA/132"},{"createdDate
     * ":"2020-03-16 19:57:46","weixin":null,"level":0,"mobile":"18576684975333","id":12489,"iconUrl":"https://wx.qlogo
     * .cn/mmopen/vi_32/Q0j4TwGTfTLicZJ5PS6Q3liaG2PWibkv2rSf5UCuibibNkKFz6OrTWNGJSCUOQQbIFa7dCqtHGs0AsT27wRXNZiaZsTA/132"},{"createdDate
     * ":"2020-03-16 19:54:43","weixin":null,"level":0,"mobile":"152368132521","id":12488,"iconUrl":"https://wx.qlogo
     * .cn/mmopen/vi_32/DYAIOgq83erVViboZRicLI5rpN1ecZqwHG0S9aBDSuzJw0HYlTl7S6lvUbvRlsG5Ua8XYiaVFQbCAC2RTuLrsa6qA/132"},{"createdDate
     * ":"2020-03-16 19:54:23","weixin":null,"level":0,"mobile":"18576684975222","id":12487,"iconUrl":"https://wx.qlogo
     * .cn/mmopen/vi_32/Q0j4TwGTfTLicZJ5PS6Q3liaG2PWibkv2rSf5UCuibibNkKFz6OrTWNGJSCUOQQbIFa7dCqtHGs0AsT27wRXNZiaZsTA/132"}]}
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
             * createdDate : 2020-03-24 10:18:03
             * weixin : Ryantaoyf
             * level : 0
             * mobile : 19937621228
             * id : 12503
             * iconUrl : http://thirdwx.qlogo
             * .cn/mmopen/vi_32/Q84LDm8PxTibyWbTrekiatCcF6ibuJ84uBS67bqkUfMNgPC8YiagPiaocez0D1CiaWwWic1UA8PJrkBqBxTVxLicYv92ug/132
             */

            private String createdDate;
            private String weixin;
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

            public String getWeixin() {
                return weixin;
            }

            public void setWeixin(String weixin) {
                this.weixin = weixin;
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

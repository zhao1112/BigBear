package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.bean
 * @DATE 2019/11/27
 */
public class MineBannerBean {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"platformBanner":[{"image":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/201908/d7a50b81-f342-4c0c-9730-a729b00ec6f0.jpg","urlType":true,"targetType":1,"id":36,"title":"测试1",
     * "type":true,"target":"http://testapi.bbbearmall.com/view/hd/list"},{"image":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/201907/57995ee9-fa07-40c5-b6c3-952d4a4685ed.jpg","urlType":true,"targetType":1,"id":37,"title":"测试2",
     * "type":true,"target":"http://testapi.bbbearmall.com/view/hd/list"},{"image":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/201908/234dec5a-fe01-44d2-ae6c-e4aa1cc6863f.png","urlType":true,"targetType":1,"id":38,"title":"测试3",
     * "type":true,"target":"http://testapi.bbbearmall.com/view/hd/list"},{"image":"https://shopxxbbc.oss-cn-beijing.aliyuncs
     * .com/upload/image/201910/563bf32e-2cb5-464b-9e01-756ad16ab587.png","urlType":true,"targetType":1,"id":39,"title":"测试4",
     * "type":true,"target":"http://testapi.bbbearmall.com/view/hd/list"}]}
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
        private List<PlatformBannerBean> platformBanner;

        public List<PlatformBannerBean> getPlatformBanner() {
            return platformBanner;
        }

        public void setPlatformBanner(List<PlatformBannerBean> platformBanner) {
            this.platformBanner = platformBanner;
        }

        public static class PlatformBannerBean {
            /**
             * image : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201908/d7a50b81-f342-4c0c-9730-a729b00ec6f0.jpg
             * urlType : true
             * targetType : 1
             * id : 36
             * title : 测试1
             * type : true
             * target : http://testapi.bbbearmall.com/view/hd/list
             */

            private String image;
            private boolean urlType;
            private int targetType;
            private int id;
            private String title;
            private boolean type;
            private String target;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public boolean isUrlType() {
                return urlType;
            }

            public void setUrlType(boolean urlType) {
                this.urlType = urlType;
            }

            public int getTargetType() {
                return targetType;
            }

            public void setTargetType(int targetType) {
                this.targetType = targetType;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public boolean isType() {
                return type;
            }

            public void setType(boolean type) {
                this.type = type;
            }

            public String getTarget() {
                return target;
            }

            public void setTarget(String target) {
                this.target = target;
            }
        }
    }
}

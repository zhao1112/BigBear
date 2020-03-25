package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.bean
 * @DATE 2020/3/21
 */
public class SearchBannerBean {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"platformBanner":[{"image":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/201912/e5135a1b-c295-4a75-a9b8-985f0c4b2b7e.png","targetType":30,"id":252,"title":"测试数据",
     * "target":"http://testapi.bbbearmall.com/view/hd/list"},{"image":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202003/43e9c6e4-dd7f-4b6b-b54e-894c13865007.png","targetType":30,"id":301,"title":"测试数据2","target":"www.baidu
     * .com"},{"image":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202003/b92372f8-6bc2-4b01-a2ff-c81131b159ae.jpg",
     * "targetType":30,"id":351,"title":"搜索轮播3","target":"www.baidu.com"},{"image":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202003/5d20b917-d125-4573-9446-61ef8969a136.png","targetType":30,"id":352,"title":"搜索轮播4","target":null}]}
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
             * image : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201912/e5135a1b-c295-4a75-a9b8-985f0c4b2b7e.png
             * targetType : 30
             * id : 252
             * title : 测试数据
             * target : http://testapi.bbbearmall.com/view/hd/list
             */

            private String image;
            private int targetType;
            private int id;
            private String title;
            private String target;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
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

            public String getTarget() {
                return target;
            }

            public void setTarget(String target) {
                this.target = target;
            }
        }
    }
}

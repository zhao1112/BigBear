package com.yunqin.bearmall.bean;

/**
 * @author AYWang
 * @create 2018/8/24
 * @Describe
 */
public class ShareBean {
    /**
     * msg : 请求成功
     * code : 1
     * data : {"imgUrl":"https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/6eac40f8-c1e2-4a47-a008-89aa17554d59.jpg","speciality":"智能手机","wxProgramId":"gh_0c4ffc9fbb48","wxProgramPageImageUrl":"http://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/7b447a28-5613-4285-b32d-a15e238e6915.jpg","shareUrl":"https://api.bbbearmall.com/view/findGuideArticleDetailPage?guideArticle_id=502","source_id":502,"title":"超级智能手机推荐","type":0,"wxProgramPageUrl":"pages/index"}
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
        /**
         * imgUrl : https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/6eac40f8-c1e2-4a47-a008-89aa17554d59.jpg
         * speciality : 智能手机
         * wxProgramId : gh_0c4ffc9fbb48
         * wxProgramPageImageUrl : http://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/7b447a28-5613-4285-b32d-a15e238e6915.jpg
         * shareUrl : https://api.bbbearmall.com/view/findGuideArticleDetailPage?guideArticle_id=502
         * source_id : 502
         * title : 超级智能手机推荐
         * type : 0
         * wxProgramPageUrl : pages/index
         */

        private String imgUrl;
        private String speciality;
        private String wxProgramId;
        private String wxProgramPageImageUrl;
        private String shareUrl;
        private int source_id;
        private String title;
        private int type;
        private String wxProgramPageUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getSpeciality() {
            return speciality;
        }

        public void setSpeciality(String speciality) {
            this.speciality = speciality;
        }

        public String getWxProgramId() {
            return wxProgramId;
        }

        public void setWxProgramId(String wxProgramId) {
            this.wxProgramId = wxProgramId;
        }

        public String getWxProgramPageImageUrl() {
            return wxProgramPageImageUrl;
        }

        public void setWxProgramPageImageUrl(String wxProgramPageImageUrl) {
            this.wxProgramPageImageUrl = wxProgramPageImageUrl;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public int getSource_id() {
            return source_id;
        }

        public void setSource_id(int source_id) {
            this.source_id = source_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getWxProgramPageUrl() {
            return wxProgramPageUrl;
        }

        public void setWxProgramPageUrl(String wxProgramPageUrl) {
            this.wxProgramPageUrl = wxProgramPageUrl;
        }
    }
}

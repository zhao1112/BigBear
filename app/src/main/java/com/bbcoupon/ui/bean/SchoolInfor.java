package com.bbcoupon.ui.bean;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.bean
 * @DATE 2020/6/1
 */
public class SchoolInfor {


    /**
     * msg : 请求成功
     * code : 1
     * data3 : [{"coverimage":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202006/c290ba3f-91cb-429f-b5f5-91deed3cb5e5.jpg","releaseTime":"1小时前","isOverhead":1,"id":857,"views_num":"3
     * .2k","title":"测试测试","likes_num":"0","type":1,"url":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/media/202006/71484ba4-b71b-4387-a833-1b6e5894f1a0.mp4"},{"coverimage":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202006/cc668fbf-769a-4275-9779-5a82f4caac60.jpg","releaseTime":"1小时前","isOverhead":1,"id":856,"views_num":"3
     * .2k","title":"测试测试测试测试11111","likes_num":"0","type":1,"url":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/media/202006/041ffa19-b3b7-4278-b162-0b7f63b9c482.mp4"},{"coverimage":null,"releaseTime":"1小时前","isOverhead":1,
     * "id":853,"views_num":"0","title":"测试测试","likes_num":"1.2k","type":0,"url":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202006/1bd06cee-e72a-42c0-88db-6635fe3df878.jpg"},{"coverimage":null,"releaseTime":"2020-06-02","isOverhead":1,
     * "id":860,"views_num":"0","title":"11111","likes_num":"0","type":0,"url":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202006/f56618cc-473d-4c2c-8e09-825e53747b55.png"},{"coverimage":null,"releaseTime":"1小时前","isOverhead":0,
     * "id":859,"views_num":"0","title":"测试测试","likes_num":"0","type":0,"url":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202006/f097c517-b1c2-444a-9ffb-02c393c90708.png"},{"coverimage":null,"releaseTime":"1小时前","isOverhead":0,
     * "id":858,"views_num":"0","title":"测试测试","likes_num":"0","type":0,"url":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202006/82705a1d-c681-4414-a97c-b18329b37812.png"},{"coverimage":null,"releaseTime":"1小时前","isOverhead":0,
     * "id":855,"views_num":"0","title":"1111111","likes_num":"0","type":0,"url":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202006/672125c9-dafa-480a-afd8-70ffdbc4fda5.jpg"},{"coverimage":null,"releaseTime":"1小时前","isOverhead":0,
     * "id":854,"views_num":"3.2k","title":"测试测试","likes_num":"0","type":0,"url":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202006/6485abf0-2c9c-4cef-ae8d-06229ec88d1c.jpg"}]
     * data2 : [{"image":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202006/b649d774-5ca8-449c-9ba3-08914419d1f2
     * .jpg","name":"新手教程","id":302},{"image":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202006/a341e57e-e1a7-40a3-9854-2f21ec8447dc.jpg","name":"个人提升","id":303},{"image":"https://shopxxbbc-test
     * .oss-cn-beijing.aliyuncs.com/upload/image/202006/69f82ac4-37e0-4557-88c6-4e81c9ea54a7.jpg","name":"视频直播","id":304},{"image":"https
     * ://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202006/e892a1c7-189d-4371-9679-ec8d644df7fb.jpg","name":"热门推荐","id":305
     * },{"image":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202006/f69e51e0-3df0-4353-a610-3098bd1777ad.jpg",
     * "name":"拉新邀粉","id":306},{"image":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202006/f5c11da7-2f2b-4dd5-ab15-1f441821ea1a.jpg","name":"三木讲堂","id":307},{"image":"https://shopxxbbc-test
     * .oss-cn-beijing.aliyuncs.com/upload/image/202006/7ef422c8-3ab5-404f-bb8a-6c37d65db8d0.jpg","name":"百问百答","id":308},{"image":"https
     * ://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202006/ac820806-77d8-41cc-857c-a281435221a3.jpg","name":"最新资讯","id":309}]
     * data1 : [{"image":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202006/e56d738d-0715-4292-bdc6-1501617b105c
     * .png","businesscollegeId":856},{"image":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202006/3a2e1a44-1df5-4c36-b590-b6b9055587db.png","businesscollegeId":860},{"image":"https://shopxxbbc-test
     * .oss-cn-beijing.aliyuncs.com/upload/image/202006/aef8969f-fbb5-4b4a-8050-dcfe002b56cf.jpg","businesscollegeId":857}]
     */

    private String msg;
    private int code;
    private List<Data3Bean> data3;
    private List<Data2Bean> data2;
    private List<Data1Bean> data1;

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

    public List<Data3Bean> getData3() {
        return data3;
    }

    public void setData3(List<Data3Bean> data3) {
        this.data3 = data3;
    }

    public List<Data2Bean> getData2() {
        return data2;
    }

    public void setData2(List<Data2Bean> data2) {
        this.data2 = data2;
    }

    public List<Data1Bean> getData1() {
        return data1;
    }

    public void setData1(List<Data1Bean> data1) {
        this.data1 = data1;
    }

    public static class Data3Bean {
        /**
         * coverimage : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202006/c290ba3f-91cb-429f-b5f5-91deed3cb5e5.jpg
         * releaseTime : 1小时前
         * isOverhead : 1
         * id : 857
         * views_num : 3.2k
         * title : 测试测试
         * likes_num : 0
         * type : 1
         * url : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/media/202006/71484ba4-b71b-4387-a833-1b6e5894f1a0.mp4
         */

        private String coverimage;
        private String releaseTime;
        private int isOverhead;
        private int id;
        private String views_num;
        private String title;
        private String likes_num;
        private int type;
        private String url;

        public String getCoverimage() {
            return coverimage;
        }

        public void setCoverimage(String coverimage) {
            this.coverimage = coverimage;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }

        public int getIsOverhead() {
            return isOverhead;
        }

        public void setIsOverhead(int isOverhead) {
            this.isOverhead = isOverhead;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getViews_num() {
            return views_num;
        }

        public void setViews_num(String views_num) {
            this.views_num = views_num;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLikes_num() {
            return likes_num;
        }

        public void setLikes_num(String likes_num) {
            this.likes_num = likes_num;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Data2Bean {
        /**
         * image : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202006/b649d774-5ca8-449c-9ba3-08914419d1f2.jpg
         * name : 新手教程
         * id : 302
         */

        private String image;
        private String name;
        private int id;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class Data1Bean {
        /**
         * image : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202006/e56d738d-0715-4292-bdc6-1501617b105c.png
         * businesscollegeId : 856
         */

        private String image;
        private int businesscollegeId;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getBusinesscollegeId() {
            return businesscollegeId;
        }

        public void setBusinesscollegeId(int businesscollegeId) {
            this.businesscollegeId = businesscollegeId;
        }
    }
}

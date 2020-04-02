package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.bean
 * @DATE 2020/4/1
 */
public class PropagandaBean {


    /**
     * msg : 请求成功
     * code : 1
     * data : [{"launchTime":"2020-03-28 16:51:28","images":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/201907/c311c1d3-10ec-40be-a27b-55fce7063cda.jpg,https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/201912/1242c9f6-8332-441c-b1f2-4dbda3a1fed2.png,https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202003/47768d46-637f-45ae-a52d-198d7d97befc.jpg,https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202003/d2b1ae72-bbe8-48fe-b38a-1a4302681a78.jpg,https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202003/5b134128-985a-45a0-8074-4d09800cca95.jpg","material_desc":"今夜你回不回来，你的爱还在不在。如果你的心已经离开，我宁愿没有未来",
     * "videos":null,"id":54908961,"share_num":1052},{"launchTime":"2020-03-28 16:51:28","images":"https://hbimg.huabanimg
     * .com/855a1b0c89a5ab3a43134e3131dd33941d709bdd2c540-ensCKw_fw658","material_desc":"我和你吻别在无人的街，让风痴笑我不能拒绝。我和你吻别在狂乱的夜，我的心，等着迎接伤悲",
     * "videos":null,"id":54908962,"share_num":1052},{"launchTime":"2020-03-28 16:51:28","images":"https://hbimg.huabanimg
     * .com/701c31c27d6a1c4fdd5b03b3525ce1e94156b4295d25b9-k2yKfk_fw658","material_desc":"我和你吻别在无人的街,让风痴笑我不能拒绝。\n我和你吻别在狂乱的夜，我的心，等着迎接伤悲",
     * "videos":null,"id":54908963,"share_num":1052},{"launchTime":"2020-03-28 16:51:28","images":"",
     * "material_desc":"谁轻轻叫唤我，唤起心中爱火。幸运只因有着你，不再流浪与错过","videos":"https://cloud.video.taobao
     * .com//play/u/1644241023/p/1/e/6/t/1/257445029587.mp4","id":54908964,"share_num":1052},{"launchTime":"2020-03-28 16:51:28",
     * "images":"https://hbimg.huabanimg.com/132990fbb48ab8e311c537f23de453f5042795b182ce9-c6QMYC_fw658","material_desc":"浪里白条浪里浪",
     * "videos":"","id":54908965,"share_num":1052},{"launchTime":"2020-03-28 16:51:28","images":"https://shopxxbbc-test.oss-cn-beijing
     * .aliyuncs.com/upload/image/201907/c311c1d3-10ec-40be-a27b-55fce7063cda.jpg,https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/201912/1242c9f6-8332-441c-b1f2-4dbda3a1fed2.png,https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202003/47768d46-637f-45ae-a52d-198d7d97befc.jpg,https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202003/d2b1ae72-bbe8-48fe-b38a-1a4302681a78.jpg,https://shopxxbbc-test.oss-cn-beijing.aliyuncs
     * .com/upload/image/202003/5b134128-985a-45a0-8074-4d09800cca95.jpg",
     * "material_desc":"你总说等一等想一想看一看，抢占先机很重要。今年注定是社交电商快速发展的一年，你还在顾虑啥？不管你在顾虑啥，我们都能解决","videos":null,"id":5490896,"share_num":12398}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * launchTime : 2020-03-28 16:51:28
         * images : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201907/c311c1d3-10ec-40be-a27b-55fce7063cda.jpg,
         * https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201912/1242c9f6-8332-441c-b1f2-4dbda3a1fed2.png,
         * https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202003/47768d46-637f-45ae-a52d-198d7d97befc.jpg,
         * https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202003/d2b1ae72-bbe8-48fe-b38a-1a4302681a78.jpg,
         * https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202003/5b134128-985a-45a0-8074-4d09800cca95.jpg
         * material_desc : 今夜你回不回来，你的爱还在不在。如果你的心已经离开，我宁愿没有未来
         * videos : null
         * id : 54908961
         * share_num : 1052
         */

        private String launchTime;
        private String images;
        private String material_desc;
        private String videos;
        private int id;
        private int share_num;

        public String getLaunchTime() {
            return launchTime;
        }

        public void setLaunchTime(String launchTime) {
            this.launchTime = launchTime;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getMaterial_desc() {
            return material_desc;
        }

        public void setMaterial_desc(String material_desc) {
            this.material_desc = material_desc;
        }

        public String getVideos() {
            return videos;
        }

        public void setVideos(String videos) {
            this.videos = videos;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getShare_num() {
            return share_num;
        }

        public void setShare_num(int share_num) {
            this.share_num = share_num;
        }
    }
}

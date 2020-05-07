package com.bbcoupon.ui.bean;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.bean
 * @DATE 2020/5/7
 */
public class MeetingInfor {

    /**
     * msg : 请求成功
     * data : {"image":"https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202005/a0bdc6be-13f9-492c-94b9-b92c555add63.jpg",
     * "colour":"red","title":"测试数据","contnet":"420天貓親子節，滿300減40，享2件75折,復制(￥85211MBhB5X￥)打開手機Tao寶即可。","url":"https://s.click.taobao
     * .com/t?e=m%3D2%26s%3DevUXqbl%2BqNEcQipKwQzePCperVdZeJviyK8Cckff7TVRAdhuF14FMR5nWJTpv4Mhxq3IhSJN6GTTwEz2I8onAShzyKZASHd58u
     * %2B065IQTftXjTIBNsEf%2FFQD%2FoYT4N
     * %2F0u3ex8PaaVqDLP4XO97Tuip7xDSuPmzLWC2TKqEFvn7gehppSckYlU8tA8Qks8BYBXdZXtfxfyx2uOLrdHTHPFWH9kXM4D5iKLRzIL%2BzdRHRbK7u
     * %2F4x92zWitoudRtKuNkeMqUwSQcLSwn1IXvusdyogaseAKBk0cEzJFLUun%2BFGDWrJcI
     * %2B9mMpHyRB5t2u09YeJnSHHTFoR8QgttS1UTkGapL9J5r9qFltmYRBBBr7PRSGQpyDJeSt8HngduECFbaeNmVpjiNWs6ZQEtU
     * %2BPxK743SiwkZk59IvUSjheXEWaHRJzlN%2BgNRsYl7w3%2FA2kb&pvid=25635011&union_lens=lensId:0b183db1_0db4_171ee277083_78a9"}
     * code : 1
     */

    private String msg;
    private DataBean data;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * image : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202005/a0bdc6be-13f9-492c-94b9-b92c555add63.jpg
         * colour : red
         * title : 测试数据
         * contnet : 420天貓親子節，滿300減40，享2件75折,復制(￥85211MBhB5X￥)打開手機Tao寶即可。
         * url : https://s.click.taobao
         * .com/t?e=m%3D2%26s%3DevUXqbl%2BqNEcQipKwQzePCperVdZeJviyK8Cckff7TVRAdhuF14FMR5nWJTpv4Mhxq3IhSJN6GTTwEz2I8onAShzyKZASHd58u
         * %2B065IQTftXjTIBNsEf%2FFQD%2FoYT4N
         * %2F0u3ex8PaaVqDLP4XO97Tuip7xDSuPmzLWC2TKqEFvn7gehppSckYlU8tA8Qks8BYBXdZXtfxfyx2uOLrdHTHPFWH9kXM4D5iKLRzIL%2BzdRHRbK7u
         * %2F4x92zWitoudRtKuNkeMqUwSQcLSwn1IXvusdyogaseAKBk0cEzJFLUun%2BFGDWrJcI
         * %2B9mMpHyRB5t2u09YeJnSHHTFoR8QgttS1UTkGapL9J5r9qFltmYRBBBr7PRSGQpyDJeSt8HngduECFbaeNmVpjiNWs6ZQEtU
         * %2BPxK743SiwkZk59IvUSjheXEWaHRJzlN%2BgNRsYl7w3%2FA2kb&pvid=25635011&union_lens=lensId:0b183db1_0db4_171ee277083_78a9
         */

        private String image;
        private String colour;
        private String title;
        private String contnet;
        private String url;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getColour() {
            return colour;
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContnet() {
            return contnet;
        }

        public void setContnet(String contnet) {
            this.contnet = contnet;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

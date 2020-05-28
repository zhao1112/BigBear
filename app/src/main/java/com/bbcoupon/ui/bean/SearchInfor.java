package com.bbcoupon.ui.bean;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.bean
 * @DATE 2020/5/27
 */
public class SearchInfor {

    /**
     * msg : 请求成功
     * code : 1
     * data : {"itemId":"608699227386","image":"https://img.alicdn
     * .com/bao/uploaded/i1/2456631333/O1CN01Vr2wJc1LiYYazirqO_!!2456631333-0-pixelsss.jpg","tkfee3":"5.12","discountPrice":"339",
     * "title":"多功能分离式二合一蓝牙通话智能手环男女测血压心率运动手表华为苹果vivo魅族oppo小米4手机通用跑步计步器","tmall":"1","url":"https://uland.taobao
     * .com/coupon/edetail?e=DfXrGaxGaowE%2BdAb1JoOOu%2BDqWsTXpIineB2PV3rEb%2FsVH%2BmoI4uO1s%2FYCa0Uk
     * %2BiCFtPZGs8yAqgUSPPibD7dkn4AOAOTQC0IJ1GtccYOa4CCNPIeD%2FCaOT3b4zuHjpz1ug731VBEQm4W4aFJRXLDFlmT5swLWTvStEn4IzAWfjZeF9VVoymoRTYs
     * %2FKEV5Xg8%2BKK120p87U%3D&traceId=0b8fdcc015905483349505599e&union_lens=lensId:TAPI@1590548334@0b5797e1_0dac_17254118989_6c87@01
     * &xId=7kfZMhSsq7oaHk8WeknVzpJFqGLx5LCbfddRMXhCJlewfeY3wTPDOKRfTghFw2BP3nRbZfzyJebvGj389CUHfdLB7fjKUNg0ixGsBSOy9zin"}
     * type : 1
     */

    private String msg;
    private int code;
    private DataBean data;
    private int type;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class DataBean {
        /**
         * itemId : 608699227386
         * image : https://img.alicdn.com/bao/uploaded/i1/2456631333/O1CN01Vr2wJc1LiYYazirqO_!!2456631333-0-pixelsss.jpg
         * tkfee3 : 5.12
         * discountPrice : 339
         * title : 多功能分离式二合一蓝牙通话智能手环男女测血压心率运动手表华为苹果vivo魅族oppo小米4手机通用跑步计步器
         * tmall : 1
         * url : https://uland.taobao.com/coupon/edetail?e=DfXrGaxGaowE%2BdAb1JoOOu%2BDqWsTXpIineB2PV3rEb%2FsVH%2BmoI4uO1s%2FYCa0Uk
         * %2BiCFtPZGs8yAqgUSPPibD7dkn4AOAOTQC0IJ1GtccYOa4CCNPIeD
         * %2FCaOT3b4zuHjpz1ug731VBEQm4W4aFJRXLDFlmT5swLWTvStEn4IzAWfjZeF9VVoymoRTYs%2FKEV5Xg8%2BKK120p87U%3D&traceId
         * =0b8fdcc015905483349505599e&union_lens=lensId:TAPI@1590548334@0b5797e1_0dac_17254118989_6c87@01&xId
         * =7kfZMhSsq7oaHk8WeknVzpJFqGLx5LCbfddRMXhCJlewfeY3wTPDOKRfTghFw2BP3nRbZfzyJebvGj389CUHfdLB7fjKUNg0ixGsBSOy9zin
         */

        private String itemId;
        private String image;
        private String tkfee3;
        private String discountPrice;
        private String title;
        private String tmall;
        private String url;

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTkfee3() {
            return tkfee3;
        }

        public void setTkfee3(String tkfee3) {
            this.tkfee3 = tkfee3;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTmall() {
            return tmall;
        }

        public void setTmall(String tmall) {
            this.tmall = tmall;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

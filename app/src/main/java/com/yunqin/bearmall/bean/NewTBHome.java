package com.yunqin.bearmall.bean;

import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.base
 * @DATE 2020/3/20
 */
public class NewTBHome {


    private String msg;
    private int code;
    private String activeTitle;
    private TopBgBean top_bg;
    private InterCopywritingBean interCopywriting;
    private SellWellCopywritingBean sellWellCopywriting;
    private SelectedCopywritingBean selectedCopywriting;
    private CenterBgBean center_bg;
    private List<CommodityBean> commodity;
    private List<TianMaoCommodityOneBean> tianMaoCommodityOne;
    private List<BannerThreeBean> bannerThree;
    private List<BannerTwoBean> bannerTwo;
    private List<ClassificationBean> classification;
    private List<TianMaoCommodityTwoBean> tianMaoCommodityTwo;
    private List<BannerOneBean> bannerOne;

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

    public String getActiveTitle() {
        return activeTitle;
    }

    public void setActiveTitle(String activeTitle) {
        this.activeTitle = activeTitle;
    }

    public TopBgBean getTop_bg() {
        return top_bg;
    }

    public void setTop_bg(TopBgBean top_bg) {
        this.top_bg = top_bg;
    }

    public InterCopywritingBean getInterCopywriting() {
        return interCopywriting;
    }

    public void setInterCopywriting(InterCopywritingBean interCopywriting) {
        this.interCopywriting = interCopywriting;
    }

    public SellWellCopywritingBean getSellWellCopywriting() {
        return sellWellCopywriting;
    }

    public void setSellWellCopywriting(SellWellCopywritingBean sellWellCopywriting) {
        this.sellWellCopywriting = sellWellCopywriting;
    }

    public SelectedCopywritingBean getSelectedCopywriting() {
        return selectedCopywriting;
    }

    public void setSelectedCopywriting(SelectedCopywritingBean selectedCopywriting) {
        this.selectedCopywriting = selectedCopywriting;
    }

    public CenterBgBean getCenter_bg() {
        return center_bg;
    }

    public void setCenter_bg(CenterBgBean center_bg) {
        this.center_bg = center_bg;
    }

    public List<CommodityBean> getCommodity() {
        return commodity;
    }

    public void setCommodity(List<CommodityBean> commodity) {
        this.commodity = commodity;
    }

    public List<TianMaoCommodityOneBean> getTianMaoCommodityOne() {
        return tianMaoCommodityOne;
    }

    public void setTianMaoCommodityOne(List<TianMaoCommodityOneBean> tianMaoCommodityOne) {
        this.tianMaoCommodityOne = tianMaoCommodityOne;
    }

    public List<BannerThreeBean> getBannerThree() {
        return bannerThree;
    }

    public void setBannerThree(List<BannerThreeBean> bannerThree) {
        this.bannerThree = bannerThree;
    }

    public List<BannerTwoBean> getBannerTwo() {
        return bannerTwo;
    }

    public void setBannerTwo(List<BannerTwoBean> bannerTwo) {
        this.bannerTwo = bannerTwo;
    }

    public List<ClassificationBean> getClassification() {
        return classification;
    }

    public void setClassification(List<ClassificationBean> classification) {
        this.classification = classification;
    }

    public List<TianMaoCommodityTwoBean> getTianMaoCommodityTwo() {
        return tianMaoCommodityTwo;
    }

    public void setTianMaoCommodityTwo(List<TianMaoCommodityTwoBean> tianMaoCommodityTwo) {
        this.tianMaoCommodityTwo = tianMaoCommodityTwo;
    }

    public List<BannerOneBean> getBannerOne() {
        return bannerOne;
    }

    public void setBannerOne(List<BannerOneBean> bannerOne) {
        this.bannerOne = bannerOne;
    }

    public static class TopBgBean {
        /**
         * target : #FE0820
         */

        private String target;

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }
    }

    public static class InterCopywritingBean {
        /**
         * image : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202003/b0928dbd-aed7-47b7-a09e-92acd13f6fd4.png
         * title : 天猫国际
         * content : 全球大牌一网打尽，直营正品保证
         */

        private String image;
        private String title;
        private String content;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class SellWellCopywritingBean {
        /**
         * image : http://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202003/bg_rexiao@3x.png
         * title : 热销排行
         * content : 全网2小时热销排行榜单
         */

        private String image;
        private String title;
        private String content;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class SelectedCopywritingBean {
        /**
         * image : http://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/202003/bg_tianmaojingxuan@3x.png
         * title : 天猫超市
         * content : 每日更新，免邮好货出单快
         */

        private String image;
        private String title;
        private String content;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class CenterBgBean {
        /**
         * target : #DC143C
         */

        private String target;

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }
    }

    public static class CommodityBean {
        /**
         * name : 运动裤女春季2020新款韩版休闲宽松束脚卫裤黑色九分高腰休闲裤子
         * discountPrice : 19.8
         * itemId : 587444053112
         * couponAmount : 10
         * price : 29.8
         * outIcon : http://img.alicdn.com/bao/uploaded/i4/2271824235/O1CN01VPRqni1h9fvDxujiH_!!0-item_pic.jpg
         */

        private String name;
        private String discountPrice;
        private String itemId;
        private String couponAmount;
        private String price;
        private String outIcon;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(String couponAmount) {
            this.couponAmount = couponAmount;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOutIcon() {
            return outIcon;
        }

        public void setOutIcon(String outIcon) {
            this.outIcon = outIcon;
        }
    }

    public static class TianMaoCommodityOneBean {
        /**
         * itemId : 538302337328
         * couponAmount : 5.00
         * price : 49.90
         * outIcon : https://img.alicdn.com/bao/uploaded/i3/725677994/O1CN01RuGuk728vIjtmClQX_!!0-item_pic.jpg
         * discountPrice : 49.90
         * name : 【第二件0元】 实发丽邦4层卷纸40卷
         * sellerName : 天猫超市
         * commision : 0.53
         * tmall : 1
         * sellNum : 6793
         */

        private String itemId;
        private String couponAmount;
        private String price;
        private String outIcon;
        private String discountPrice;
        private String name;
        private String sellerName;
        private double commision;
        private String tmall;
        private String sellNum;

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(String couponAmount) {
            this.couponAmount = couponAmount;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOutIcon() {
            return outIcon;
        }

        public void setOutIcon(String outIcon) {
            this.outIcon = outIcon;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSellerName() {
            return sellerName;
        }

        public void setSellerName(String sellerName) {
            this.sellerName = sellerName;
        }

        public double getCommision() {
            return commision;
        }

        public void setCommision(double commision) {
            this.commision = commision;
        }

        public String getTmall() {
            return tmall;
        }

        public void setTmall(String tmall) {
            this.tmall = tmall;
        }

        public String getSellNum() {
            return sellNum;
        }

        public void setSellNum(String sellNum) {
            this.sellNum = sellNum;
        }
    }

    public static class BannerThreeBean {
        /**
         * image : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201912/1242c9f6-8332-441c-b1f2-4dbda3a1fed2.png
         * urlType : true
         * targetType : 3
         * id : 32
         * title : 首页单品
         * type : true
         * target : 572998490104
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

    public static class BannerTwoBean {
        /**
         * image : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201912/ac486251-b638-42ba-9074-372f2c8fafa0.png
         * urlType : null
         * targetType : 30
         * id : 251
         * title : null
         * type : true
         * target : http://testapi.bbbearmall.com/view/hd/list
         */

        private String image;
        private Object urlType;
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

        public Object getUrlType() {
            return urlType;
        }

        public void setUrlType(Object urlType) {
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

    public static class ClassificationBean {
        /**
         * img : http://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/icon/A01_9.9.png
         * createdDate : null
         * lastModifiedDate : null
         * name : 9块9
         * show : true
         * id : 1
         * sort : 1
         * type : 1
         * version : null
         * url : -4
         */

        private String img;
        private Object createdDate;
        private Object lastModifiedDate;
        private String name;
        private boolean show;
        private int id;
        private int sort;
        private int type;
        private Object version;
        private String url;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Object getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(Object createdDate) {
            this.createdDate = createdDate;
        }

        public Object getLastModifiedDate() {
            return lastModifiedDate;
        }

        public void setLastModifiedDate(Object lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getVersion() {
            return version;
        }

        public void setVersion(Object version) {
            this.version = version;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class TianMaoCommodityTwoBean {
        /**
         * itemId : 582789968558
         * couponAmount : 3.00
         * price : 19.80
         * outIcon : https://img.alicdn.com/bao/uploaded/i2/440741285/O1CN01QJkTjS1LMZWxl7riU_!!440741285.jpg
         * discountPrice : 19.80
         * name : 韩国进口火鸡面5包装*2
         * sellerName : 和风细语进口食品
         * commision : 1.0494
         * tmall : 0
         * sellNum : 11549
         */

        private String itemId;
        private String couponAmount;
        private String price;
        private String outIcon;
        private String discountPrice;
        private String name;
        private String sellerName;
        private double commision;
        private String tmall;
        private String sellNum;

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(String couponAmount) {
            this.couponAmount = couponAmount;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOutIcon() {
            return outIcon;
        }

        public void setOutIcon(String outIcon) {
            this.outIcon = outIcon;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSellerName() {
            return sellerName;
        }

        public void setSellerName(String sellerName) {
            this.sellerName = sellerName;
        }

        public double getCommision() {
            return commision;
        }

        public void setCommision(double commision) {
            this.commision = commision;
        }

        public String getTmall() {
            return tmall;
        }

        public void setTmall(String tmall) {
            this.tmall = tmall;
        }

        public String getSellNum() {
            return sellNum;
        }

        public void setSellNum(String sellNum) {
            this.sellNum = sellNum;
        }
    }

    public static class BannerOneBean {
        /**
         * image : https://shopxxbbc-test.oss-cn-beijing.aliyuncs.com/upload/image/201908/16d4958c-b4e4-4b39-94c7-4339c086c598.jpg
         * urlType : null
         * targetType : 1
         * id : -49
         * title : 11
         * type : true
         * target : www.baidu.com
         */

        private String image;
        private Object urlType;
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

        public Object getUrlType() {
            return urlType;
        }

        public void setUrlType(Object urlType) {
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

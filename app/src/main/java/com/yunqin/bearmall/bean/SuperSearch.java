package com.yunqin.bearmall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.bean
 * @DATE 2019/10/30
 */
public class SuperSearch implements Serializable {

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

    public static class DataBean implements Serializable {
        private String presale_end_time;
        private String category_name;
        private String score2;
        private String yunfeixian;
        private String score3;
        private String shop_dsr;
        private String coupon_info_money;
        private String type_one_id;
        private String level_one_category_id;
        private String presale_tail_end_time;
        private String haitao;
        private String user_type;
        private String coupon_id;
        private String taoqianggou;
        private String pinpai_name;
        private String date_time_yongjin;
        private String shop_title;
        private String small_images;
        private String seller_id;
        private String juhuasuan;
        private String tao_title;
        private String favcount;
        private String tao_id;
        private String pict_url;
        private String coupon_info;
        private String commentCount;
        private String volume;
        private String yongjin_type;
        private String zhibo_url;
        private String presale_discount_fee_text;
        private String size;
        private String pcDescContent;
        private String sellCount;
        private String score1;
        private String coupon_start_time;
        private String coupon_end_time;
        private String code;
        private String coupon_remain_count;
        private String title;
        private String presale_tail_start_time;
        private String nick;
        private String jianjie;
        private String creditLevel;
        private String presale_deposit;
        private String category_id;
        private String jinpaimaijia;
        private String tkrate3;
        private String tkfee3;
        private String shopIcon;
        private String level_one_category_name;
        private String presale_start_time;
        private String coupon_total_count;
        private String white_image;
        private String provcity;
        private String quanhou_jiage;
        private String item_url;
        private String pinpai;
        private String jiyoujia;

        public String getPresale_end_time() {
            return presale_end_time;
        }

        public void setPresale_end_time(String presale_end_time) {
            this.presale_end_time = presale_end_time;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getScore2() {
            return score2;
        }

        public void setScore2(String score2) {
            this.score2 = score2;
        }

        public String getYunfeixian() {
            return yunfeixian;
        }

        public void setYunfeixian(String yunfeixian) {
            this.yunfeixian = yunfeixian;
        }

        public String getScore3() {
            return score3;
        }

        public void setScore3(String score3) {
            this.score3 = score3;
        }

        public String getShop_dsr() {
            return shop_dsr;
        }

        public void setShop_dsr(String shop_dsr) {
            this.shop_dsr = shop_dsr;
        }

        public String getCoupon_info_money() {
            return coupon_info_money;
        }

        public void setCoupon_info_money(String coupon_info_money) {
            this.coupon_info_money = coupon_info_money;
        }

        public String getType_one_id() {
            return type_one_id;
        }

        public void setType_one_id(String type_one_id) {
            this.type_one_id = type_one_id;
        }

        public String getLevel_one_category_id() {
            return level_one_category_id;
        }

        public void setLevel_one_category_id(String level_one_category_id) {
            this.level_one_category_id = level_one_category_id;
        }

        public String getPresale_tail_end_time() {
            return presale_tail_end_time;
        }

        public void setPresale_tail_end_time(String presale_tail_end_time) {
            this.presale_tail_end_time = presale_tail_end_time;
        }

        public String getHaitao() {
            return haitao;
        }

        public void setHaitao(String haitao) {
            this.haitao = haitao;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(String coupon_id) {
            this.coupon_id = coupon_id;
        }

        public String getTaoqianggou() {
            return taoqianggou;
        }

        public void setTaoqianggou(String taoqianggou) {
            this.taoqianggou = taoqianggou;
        }

        public String getPinpai_name() {
            return pinpai_name;
        }

        public void setPinpai_name(String pinpai_name) {
            this.pinpai_name = pinpai_name;
        }

        public String getDate_time_yongjin() {
            return date_time_yongjin;
        }

        public void setDate_time_yongjin(String date_time_yongjin) {
            this.date_time_yongjin = date_time_yongjin;
        }

        public String getShop_title() {
            return shop_title;
        }

        public void setShop_title(String shop_title) {
            this.shop_title = shop_title;
        }

        public String getSmall_images() {
            return small_images;
        }

        public void setSmall_images(String small_images) {
            this.small_images = small_images;
        }

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }

        public String getJuhuasuan() {
            return juhuasuan;
        }

        public void setJuhuasuan(String juhuasuan) {
            this.juhuasuan = juhuasuan;
        }

        public String getTao_title() {
            return tao_title;
        }

        public void setTao_title(String tao_title) {
            this.tao_title = tao_title;
        }

        public String getFavcount() {
            return favcount;
        }

        public void setFavcount(String favcount) {
            this.favcount = favcount;
        }

        public String getTao_id() {
            return tao_id;
        }

        public void setTao_id(String tao_id) {
            this.tao_id = tao_id;
        }

        public String getPict_url() {
            return pict_url;
        }

        public void setPict_url(String pict_url) {
            this.pict_url = pict_url;
        }

        public String getCoupon_info() {
            return coupon_info;
        }

        public void setCoupon_info(String coupon_info) {
            this.coupon_info = coupon_info;
        }

        public String getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(String commentCount) {
            this.commentCount = commentCount;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getYongjin_type() {
            return yongjin_type;
        }

        public void setYongjin_type(String yongjin_type) {
            this.yongjin_type = yongjin_type;
        }

        public String getZhibo_url() {
            return zhibo_url;
        }

        public void setZhibo_url(String zhibo_url) {
            this.zhibo_url = zhibo_url;
        }

        public String getPresale_discount_fee_text() {
            return presale_discount_fee_text;
        }

        public void setPresale_discount_fee_text(String presale_discount_fee_text) {
            this.presale_discount_fee_text = presale_discount_fee_text;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getPcDescContent() {
            return pcDescContent;
        }

        public void setPcDescContent(String pcDescContent) {
            this.pcDescContent = pcDescContent;
        }

        public String getSellCount() {
            return sellCount;
        }

        public void setSellCount(String sellCount) {
            this.sellCount = sellCount;
        }

        public String getScore1() {
            return score1;
        }

        public void setScore1(String score1) {
            this.score1 = score1;
        }

        public String getCoupon_start_time() {
            return coupon_start_time;
        }

        public void setCoupon_start_time(String coupon_start_time) {
            this.coupon_start_time = coupon_start_time;
        }

        public String getCoupon_end_time() {
            return coupon_end_time;
        }

        public void setCoupon_end_time(String coupon_end_time) {
            this.coupon_end_time = coupon_end_time;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCoupon_remain_count() {
            return coupon_remain_count;
        }

        public void setCoupon_remain_count(String coupon_remain_count) {
            this.coupon_remain_count = coupon_remain_count;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPresale_tail_start_time() {
            return presale_tail_start_time;
        }

        public void setPresale_tail_start_time(String presale_tail_start_time) {
            this.presale_tail_start_time = presale_tail_start_time;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getJianjie() {
            return jianjie;
        }

        public void setJianjie(String jianjie) {
            this.jianjie = jianjie;
        }

        public String getCreditLevel() {
            return creditLevel;
        }

        public void setCreditLevel(String creditLevel) {
            this.creditLevel = creditLevel;
        }

        public String getPresale_deposit() {
            return presale_deposit;
        }

        public void setPresale_deposit(String presale_deposit) {
            this.presale_deposit = presale_deposit;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getJinpaimaijia() {
            return jinpaimaijia;
        }

        public void setJinpaimaijia(String jinpaimaijia) {
            this.jinpaimaijia = jinpaimaijia;
        }

        public String getTkrate3() {
            return tkrate3;
        }

        public void setTkrate3(String tkrate3) {
            this.tkrate3 = tkrate3;
        }

        public String getTkfee3() {
            return tkfee3;
        }

        public void setTkfee3(String tkfee3) {
            this.tkfee3 = tkfee3;
        }

        public String getShopIcon() {
            return shopIcon;
        }

        public void setShopIcon(String shopIcon) {
            this.shopIcon = shopIcon;
        }

        public String getLevel_one_category_name() {
            return level_one_category_name;
        }

        public void setLevel_one_category_name(String level_one_category_name) {
            this.level_one_category_name = level_one_category_name;
        }

        public String getPresale_start_time() {
            return presale_start_time;
        }

        public void setPresale_start_time(String presale_start_time) {
            this.presale_start_time = presale_start_time;
        }

        public String getCoupon_total_count() {
            return coupon_total_count;
        }

        public void setCoupon_total_count(String coupon_total_count) {
            this.coupon_total_count = coupon_total_count;
        }

        public String getWhite_image() {
            return white_image;
        }

        public void setWhite_image(String white_image) {
            this.white_image = white_image;
        }

        public String getProvcity() {
            return provcity;
        }

        public void setProvcity(String provcity) {
            this.provcity = provcity;
        }

        public String getQuanhou_jiage() {
            return quanhou_jiage;
        }

        public void setQuanhou_jiage(String quanhou_jiage) {
            this.quanhou_jiage = quanhou_jiage;
        }

        public String getItem_url() {
            return item_url;
        }

        public void setItem_url(String item_url) {
            this.item_url = item_url;
        }

        public String getPinpai() {
            return pinpai;
        }

        public void setPinpai(String pinpai) {
            this.pinpai = pinpai;
        }

        public String getJiyoujia() {
            return jiyoujia;
        }

        public void setJiyoujia(String jiyoujia) {
            this.jiyoujia = jiyoujia;
        }
    }
}

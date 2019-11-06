package com.yunqin.bearmall.util;


import android.util.Log;

import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.util
 * @DATE 2019/9/21
 */
public class ConstantScUtil {

    public static void sensorsTrack(String eventName, Map<String, String> props) {
        if (props != null && props.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            for (String key : props.keySet()) {
                try {
                    if (!StringUtils.isEmpty(key)) {
                        try {
                            jsonObject.put(key, props.get(key));
                            Log.i("sensorsTrack", key);
                            Log.i("sensorsTrack", props.get(key));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            SensorsDataAPI.sharedInstance().track(eventName, jsonObject);
        }
    }

    //神策登录统计
    public static void sensorsLogin(String value) {
        Map<String, String> map = new HashMap<>();
        map.put("login_method", value);
        ConstantScUtil.sensorsTrack("wechatLoginClick", map);
    }

    //神策验证码统计
    public static void sebsorsCode() {
        ConstantScUtil.sensorsTrack("getCode", null);
    }

    //神策授权统计
    public static void sensorsAuthorized(String bool, String reason) {
        Map<String, String> map = new HashMap<>();
        map.put("is_authorized", bool);
        map.put("fail_reason", reason);
        ConstantScUtil.sensorsTrack("authorized", map);
    }

    //神策邀请好友
    public static void sensorsInviteFriends(String text) {
        Map<String, String> map_type = new HashMap<>();
        map_type.put("entrance_type", text);
        ConstantScUtil.sensorsTrack("inviteClick", map_type);
    }

    //神策分享统计
    public static void sensorsType(String value) {
        Map<String, String> map = new HashMap<>();
        map.put("type_name", value);
        ConstantScUtil.sensorsTrack("shareType", map);
    }

    //神策邀请码统计
    public static void sensorsInvitation(String mode) {
        Map<String, String> map = new HashMap<>();
        map.put("login_method", mode);
        ConstantScUtil.sensorsTrack("invitationCode", map);
    }

    //神策搜索按钮统计
    public static void sebsorsSearch(int type) {
        if (type == 8) {
            ConstantScUtil.sensorsTrack("searchButtonClick", null);
        }
    }

    //神策搜索结果统计
    public static void searchResult(boolean search, String keyword, int position_number, String commodity_id, String commodity_name,
                                    String first_commodity, String second_commodity, String store_name, String commodity_bought_count,
                                    String coupon_amount, String predictive_commission, String present_price, String coupon_price) {
        if (search) {
            Map<String, String> map = new HashMap<>();
            map.put("key_word", keyword);
            position_number++;
            map.put("position_number", position_number + "");
            map.put("commodity_id", commodity_id);
            map.put("commodity_name", commodity_name);
            map.put("first_commodity", first_commodity);
            map.put("second_commodity", second_commodity);
            map.put("store_name", store_name);
            map.put("commodity_bought_count", commodity_bought_count);
            map.put("coupon_amount", coupon_amount);
            map.put("predictive_commission", predictive_commission);
            map.put("present_price", present_price);
            map.put("coupon_price", coupon_price);
            ConstantScUtil.sensorsTrack("searchResultClick", map);
        }
    }

    //神策浏览商品详情页
    public static void searchDetail(String commodity_id, String commodity_name, String first_commodity, String second_commodity,
                                    String store_name, String commodity_bought_count, String coupon_amount, String predictive_commission,
                                    String present_price, String coupon_price) {
        Log.i("searchDetail", commodity_id);
        Map<String, String> map = new HashMap<>();
        map.put("commodity_id", commodity_id);
        map.put("commodity_name", commodity_name);
        map.put("first_commodity", first_commodity);
        map.put("second_commodity", second_commodity);
        map.put("store_name", store_name);
        map.put("commodity_bought_count", commodity_bought_count);
        map.put("coupon_amount", coupon_amount);
        map.put("predictive_commission", predictive_commission);
        map.put("present_price", present_price);
        map.put("coupon_price", coupon_price);
        ConstantScUtil.sensorsTrack("commodityDetail", map);
    }

    //神策领券购买/立即领取
    public static void bannerClick(String page_type, String banner_belong_area, String banner_type, String banner_name, String banner_id,
                                   String url, String banner_rank) {
        Map<String, String> map = new HashMap<>();
        map.put("page_type", page_type);
        map.put("banner_belong_area", banner_belong_area);
        map.put("banner_type", banner_type);
        map.put("banner_name", banner_name);
        map.put("banner_id", banner_id);
        map.put("url", url);
        map.put("banner_rank", banner_rank);
        ConstantScUtil.sensorsTrack("bannerClick", map);
    }

    //点击会员充值
    public static void VIPRechargeClick() {
        ConstantScUtil.sensorsTrack("VIPRechargeClick", null);
    }

    //会员类型选择
    public static void VIPRechargeTypeClick(String vip_type, String vip_original_price, String vip_present_price) {
        Map<String, String> map = new HashMap<>();
        map.put("vip_type", vip_type);
        map.put("vip_original_price", vip_original_price);
        map.put("vip_present_price", vip_present_price);
        ConstantScUtil.sensorsTrack("VIPRechargeTypeClick", map);
    }

    //点击立即充值
    public static void VIPRechargeSubmit(String vip_type, String vip_present_price) {
        Map<String, String> map = new HashMap<>();
        map.put("vip_type", vip_type);
        map.put("vip_present_price", vip_present_price);
        ConstantScUtil.sensorsTrack("VIPRechargeSubmit", map);
    }

    //会员支付方式
    public static void VIPPayType(String vip_pay_type) {
        Map<String, String> map = new HashMap<>();
        map.put("vip_pay_type", vip_pay_type);
        ConstantScUtil.sensorsTrack("VIPPayType", map);
    }

    //神策分享统计
    public static void searchShare(String commodity_id, String commodity_name, String store_name, String coupon_amount,
                                   String predictive_commission, String present_price, String coupon_price) {
        Map<String, String> map = new HashMap<>();
        map.put("commodity_id", commodity_id);
        map.put("commodity_name", commodity_name);
        map.put("store_name", store_name);
        map.put("coupon_amount", coupon_amount);
        map.put("predictive_commission", predictive_commission);
        map.put("present_price", present_price);
        map.put("coupon_price", coupon_price);
        ConstantScUtil.sensorsTrack("shareClick", map);
    }


    //神策分享方式统计
    public static void searchShareType(String commodity_id, String commodity_name, String store_name, String coupon_amount,
                                       String predictive_commission, String present_price, String coupon_price, String share_type,
                                       String is_share_success) {
        Map<String, String> map = new HashMap<>();
        map.put("commodity_id", commodity_id);
        map.put("commodity_name", commodity_name);
        map.put("store_name", store_name);
        map.put("coupon_amount", coupon_amount);
        map.put("predictive_commission", predictive_commission);
        map.put("present_price", present_price);
        map.put("coupon_price", coupon_price);
        map.put("share_type", share_type);
        map.put("is_share_success", is_share_success);
        ConstantScUtil.sensorsTrack("shareMethod", map);
    }

    //点击充值icon
    public static void phoneFeeRecharge() {
        ConstantScUtil.sensorsTrack("phoneFeeRecharge", null);
    }

    //话费面额选择
    public static void phoneFeeAmountChoose(String phone_number, String phone_fee_amount, String phone_fee_price) {
        Map<String, String> map = new HashMap<>();
        map.put("phone_number", phone_number);
        map.put("phone_fee_amount", phone_fee_amount);
        map.put("phone_fee_price", phone_fee_price);
        ConstantScUtil.sensorsTrack("phoneFeeAmountChoose", map);
    }

    //点击立即充值
    public static void phoneFeeSubmit(String phone_number, String phone_fee_amount, String phone_fee_price, String coupon_amount,
                                      String coupon_quantity) {
        Map<String, String> map = new HashMap<>();
        map.put("phone_number", phone_number);
        map.put("phone_fee_amount", phone_fee_amount);
        map.put("phone_fee_price", phone_fee_price);
        map.put("coupon_amount", coupon_amount);
        map.put("coupon_quantity", coupon_quantity);
        ConstantScUtil.sensorsTrack("phoneFeeSubmit", map);
    }

    //阅读小说
    public static void novelRead() {
        ConstantScUtil.sensorsTrack("novelRead", null);
    }

    //购买名品折扣券
    public static void BrandCoupon() {
        ConstantScUtil.sensorsTrack("BrandCoupon", null);
    }

    //信用卡申请
    public static void cardApply() {
        ConstantScUtil.sensorsTrack("cardApply", null);
    }

    //广告
    public static void showAd() {
        ConstantScUtil.sensorsTrack("AD", null);
    }

    //点击0元兑
    public static void exchangeClick() {
        ConstantScUtil.sensorsTrack("exchangeClick", null);
    }

    //选择地址
    public static void addrChoose(String address) {
        Map<String, String> map = new HashMap<>();
        map.put("address", address);
        ConstantScUtil.sensorsTrack("addrChoose", map);
    }

    //确认地址
    public static void conformAddr() {
        ConstantScUtil.sensorsTrack("conformAddr", null);
    }

    //commodityDetail
    public static void commodityDetail(String commodity_id, String commodity_name, String commodity_type, String commodity_bought_count,
                                       String bearmall_price, String candy_price) {
        Map<String, String> map = new HashMap<>();
        map.put("commodity_id", commodity_id);
        map.put("commodity_name", commodity_name);
        map.put("commodity_type", commodity_type);
        map.put("commodity_bought_count", commodity_bought_count);
        map.put("bearmall_price", bearmall_price);
        map.put("candy_price", candy_price);
        ConstantScUtil.sensorsTrack("commodityDetail2", map);
    }
}

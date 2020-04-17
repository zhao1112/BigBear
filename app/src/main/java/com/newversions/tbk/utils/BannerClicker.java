package com.newversions.tbk.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.iBookStar.views.YmConfig;
import com.newversions.CardListWebActivity;
import com.newversions.InviteFriendActivity;
import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.GoodsDetailActivity;
import com.newversions.tbk.activity.ProductSumActivity;
import com.newversions.tbk.activity.WebActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.activity.ChargeActivity;
import com.yunqin.bearmall.ui.activity.DailyTasksActivity;
import com.yunqin.bearmall.ui.activity.LimitedActivity;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.SellwellActivity;
import com.yunqin.bearmall.ui.activity.ShopActivity;
import com.yunqin.bearmall.ui.activity.ZeorExchangeActivity;
import com.yunqin.bearmall.util.ArouseTaoBao;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.util.ConstUtils;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.StarActivityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BannerClicker {
    public static void bannerClick(Activity activity, int targetType, String target, String title) {
        Log.i("bannerClick", "targetType: " + targetType + "--- target" + target + "---" + title);
        Bundle bundle = new Bundle();
        switch (targetType) {
            case 1://外部淘宝链接
                ArouseTaoBao arouseTaoBao = new ArouseTaoBao(activity);
                if (arouseTaoBao.checkPackage("com.taobao.taobao")) {
                    arouseTaoBao.openTaoBao(target);
                } else {
                    Toast.makeText(activity, "您未安装淘宝，请先安装", Toast.LENGTH_LONG);
                }
                //TODO[banner点击]
                ConstantScUtil.bannerClick("首页", "轮播图", "活动", title, targetType + "", target, targetType + "");
                break;
            case 3://单品
                GoodsDetailActivity.startGoodsDetailActivity(activity, target, Constants.COMMISSION_TYPE_ONE);
                break;
            case 10://99
            case 11://抢购
            case 12://大额
                ProductSumActivity.startProductSumActivity(activity, target + "", 3, title);
                break;
            case 13://充值
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(activity);
                    return;
                }
                activity.startActivity(new Intent(activity, ChargeActivity.class));
                break;
            case 14://信用卡
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(activity);
                    return;
                }
                String token = BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token();
                String url1 = BuildConfig.BASE_URL + "/view/getCreditCardBankPage?access_token=" + token;
                CardListWebActivity.startActivity(activity, url1, "信用卡申请");
                //TODO[信用卡申请]
                ConstantScUtil.cardApply();
                break;
            case 15://赚钱
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(activity);
                    return;
                }
                DailyTasksActivity.starActivity(activity);
                break;
            case 16://0购
                activity.startActivity(new Intent(activity, ZeorExchangeActivity.class));
                break;
            case 17://邀请
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(activity);
                    return;
                }
                InviteFriendActivity.startActivity(activity);
                break;
            case 18://小说
                //TODO[阅读小说]
                ConstantScUtil.novelRead();
                if (BearMallAplication.getInstance().getUser() != null) {
                    Map<String, String> map = new HashMap<>();
                    RetrofitApi.request(activity, RetrofitApi.createApi(Api.class).getUserId(map), new RetrofitApi.IResponseListener() {
                        @Override
                        public void onSuccess(String data) throws JSONException {
                            JSONObject jsonObject = new JSONObject(data);
                            String uId = jsonObject.getString("userId");
                            Log.d("@YY", "topBarClick: getAccess_token-->" + uId);
                            YmConfig.setOutUserId(uId);
                            YmConfig.openReader();
                        }

                        @Override
                        public void onNotNetWork() {
                        }

                        @Override
                        public void onFail(Throwable e) {
                            YmConfig.openReader();
                        }
                    });
                } else {
                    YmConfig.openReader();
                }
                break;
            case 19://券
                Intent intent = new Intent(activity, WebActivity.class);
                intent.putExtra(Constants.INTENT_KEY_URL, CommonUtils.getParam365(target));
                intent.putExtra(Constants.INTENT_KEY_TITLE, "名品抵扣券");
                activity.startActivity(intent);
                //TODO[购买名品折扣券]
                ConstantScUtil.BrandCoupon();
                break;
            case 30:
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(activity);
                    return;
                }
                if (("红包0元购").equals(title)) {
                    WebActivity.startWebActivity(activity, ConstUtils.WEB_TYPE, target, title);
                } else {
                    WebActivity.startWebActivity(activity, ConstUtils.WEB_TYPE_OTHER, target, title);
                }
                //TODO[banner点击]
                ConstantScUtil.bannerClick("首页", "轮播图", "活动", title, targetType + "", target, targetType + "");
                break;
            case 20://0元兑
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(activity);
                    return;
                }
                WebActivity.startWebActivity(activity, ConstUtils.WEB_TYPE, target, title);
                //TODO[banner点击]
                ConstantScUtil.bannerClick("首页", "轮播图", "活动", title, targetType + "", target, targetType + "");
                break;
            case 21://店铺
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(activity);
                    return;
                }
                bundle.putString("store_id", target);
                StarActivityUtil.starActivity(activity, ShopActivity.class, bundle);
                break;
            case 100://聚划算
                bundle.putString("TYPE", "5");
                SellwellActivity.openSellwellActivity(activity, SellwellActivity.class, bundle);
                break;
            case 101://9.9
                bundle.putString("TYPE", "4");
                SellwellActivity.openSellwellActivity(activity, SellwellActivity.class, bundle);
                break;
            case 102://限时抢购
                bundle.putString("KEYWORD", title);
                LimitedActivity.openLimitedActivity(activity, LimitedActivity.class, bundle);
                break;
            case 103://热销榜单
                bundle.putString("TYPE", "1");
                SellwellActivity.openSellwellActivity(activity, SellwellActivity.class, bundle);
                break;
            case 104://天猫超市
                bundle.putString("TYPE", "2");
                SellwellActivity.openSellwellActivity(activity, SellwellActivity.class, bundle);
                break;
            case 105://天猫国际
                bundle.putString("TYPE", "3");
                SellwellActivity.openSellwellActivity(activity, SellwellActivity.class, bundle);
                break;
        }
    }


}

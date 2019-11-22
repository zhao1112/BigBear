package com.newversions.tbk.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.iBookStar.views.YmConfig;
import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.ProductSumActivity;
import com.newversions.tbk.activity.WebActivity;
import com.newversions.tbk.entity.TBKHomeEntity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.activity.ChargeActivity;
import com.yunqin.bearmall.ui.activity.DailyTasksActivity;
import com.yunqin.bearmall.ui.activity.InvitationActivity2;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.ZeroMoneyActivity;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.util.ConstUtils;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.StarActivityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TopBarClicker {
    public static void topBarClick(Activity activity, TBKHomeEntity.ClassificationBean bean) {
        Log.i("topBarClick", bean.getType() + "---" + bean.getUrl());
        // TODO: 2019/7/15 0015 根据类型执行操作，快速导航
        switch (bean.getType()) {
            case 1:
                // TODO: 2019/7/17 0017 特殊分类，分类id 使用id字段
                ProductSumActivity.startProductSumActivity(activity, bean.getId() + "", 3, bean.getName());
                break;
            case 2:
                ProductSumActivity.startProductSumActivity(activity, bean.getId() + "", 9, bean.getName());
                break;
            case 3:
                ProductSumActivity.startProductSumActivity(activity, bean.getId() + "", 5, bean.getName());
                break;
            case 4:
                // TODO 充值中心
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(activity);
                    return;
                }
                activity.startActivity(new Intent(activity, ChargeActivity.class));
                //TODO[点击充值icon]
                ConstantScUtil.phoneFeeRecharge();
                break;
            case 5:
//                // TODO: 2019/7/17 0017 信用卡
//                if (BearMallAplication.getInstance().getUser() == null) {
//                    LoginActivity.starActivity(activity);
//                    return;
//                }
//                String token = BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token();
//                String url1 = BuildConfig.BASE_URL + "/view/getCreditCardBankPage?access_token=" + token;
//                CardListWebActivity.startActivity(activity, url1, "信用卡申请");
//                //TODO[信用卡申请]
//                ConstantScUtil.cardApply();
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(activity);
                    return;
                }
                WebActivity.startWebActivity(activity, ConstUtils.WEB_TYPE, bean.getUrl(), bean.getName());
                break;
            case 6:
                // TODO 每日任务
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(activity);
                    return;
                }
                DailyTasksActivity.starActivity(activity);
                break;
            case 7:
                // TODO 糖果0元兑
                activity.startActivity(new Intent(activity, ZeroMoneyActivity.class));
                //TODO[点击0元兑]
                ConstantScUtil.exchangeClick();
                break;
            case 8:
                // TODO 邀请赚赏金
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(activity);
                    return;
                }
                StarActivityUtil.starActivity(activity, InvitationActivity2.class);
                //TODO[邀请好友]
                ConstantScUtil.sensorsInviteFriends("首页：邀请好友");
                break;
            case 9:
                //TODO[阅读小说]
                ConstantScUtil.novelRead();
                // TODO: 2019/7/17 0017 小说
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
            case 10:
                Intent intent = new Intent(activity, WebActivity.class);
                String tempUrl = CommonUtils.getParam365(bean.getUrl());
                intent.putExtra(Constants.INTENT_KEY_URL, tempUrl);
                intent.putExtra(Constants.INTENT_KEY_TITLE, "名品抵扣券");
                activity.startActivity(intent);
                Log.i("topBarClick", "topBarClick: " + tempUrl);
                //TODO[购买名品折扣券]
                ConstantScUtil.BrandCoupon();
                break;
            // TODO 会员商城

            // TODO 砍价随意拿

            // TODO 我要借钱

            // TODO 游戏中心
        }
    }
}

package com.newversions.tbk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.gson.JsonObject;
import com.iBookStar.views.YmConfig;
import com.newversions.CardListWebActivity;
import com.newversions.InviteFriendActivity;
import com.newversions.MemberMallActivity;
import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.ProductSumActivity;
import com.newversions.tbk.activity.WebActivity;
import com.newversions.tbk.entity.TBKHomeEntity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.ui.activity.BargainFreeActivity;
import com.yunqin.bearmall.ui.activity.ChargeActivity;
import com.yunqin.bearmall.ui.activity.DailyTasksActivity;
import com.yunqin.bearmall.ui.activity.InvitationActivity;
import com.yunqin.bearmall.ui.activity.InvitationActivity2;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.ZanWeiKaiFangActivity;
import com.yunqin.bearmall.ui.activity.ZeroMoneyActivity;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.DeviceUtils;
import com.yunqin.bearmall.util.StarActivityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TopBarClicker {
    public static void topBarClick(Activity activity, TBKHomeEntity.ClassificationBean bean) {
        // TODO: 2019/7/15 0015 根据类型执行操作，快速导航
        switch (bean.getType()) {
            case 1:
                // TODO: 2019/7/17 0017 特殊分类，分类id 使用id字段
                ProductSumActivity.startProductSumActivity(activity, bean.getId() + "", 3, bean.getName());
                break;
            case 2:

                ProductSumActivity.startProductSumActivity(activity, bean.getId() + "", 9, bean.getName());
                break;
            case 4:
                // TODO 充值中心
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(activity);
                    return;
                }
                activity.startActivity(new Intent(activity, ChargeActivity.class));
                break;
            case 5:
                // TODO: 2019/7/17 0017 信用卡
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(activity);
                    return;
                }

                String token = BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token();
                String url1 = BuildConfig.BASE_URL + "/view/getCreditCardBankPage?access_token=" + token;

                CardListWebActivity.startActivity(activity, url1, "信用卡申请");
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
                break;
            case 8:
                // TODO 邀请赚赏金
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(activity);
                    return;
                }
                //StarActivityUtil.starActivity(activity, InvitationActivity.class);
                StarActivityUtil.starActivity(activity, InvitationActivity2.class);
                //TODO[邀请好友]
                Map<String,String> map_type = new HashMap<>();
                map_type.put("entrance_type","首页：邀请好友");
                ConstantScUtil.sensorsTrack("inviteClick",map_type);
//                InviteFriendActivity.startActivity(activity);
//                StarActivityUtil.starActivity(getActivity(), VipCenterActivity.class);
                break;
            case 9:
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
                String url = "http://tq.365taoquan.cn/seller/app/classify?machineCode=" + DeviceUtils.getUniqueId(activity) + "&agentId=292";
                Log.d("TAG", "topBarClick:---- " + url);
                Intent intent = new Intent(activity, WebActivity.class);
                intent.putExtra(Constants.INTENT_KEY_URL, url);
                intent.putExtra(Constants.INTENT_KEY_TITLE, "名品抵扣券");
                activity.startActivity(intent);

                break;
//            case R.id.n_v_home_3:
//                // TODO 会员商城
//                StarActivityUtil.starActivity(activity, MemberMallActivity.class);
//                break;
//            case R.id.n_v_home_5:
//                // TODO 砍价随意拿
//                activity.startActivity(new Intent(activity, BargainFreeActivity.class));
//                break;
//            case R.id.n_v_home_7:
//                // TODO 我要借钱
//                if (BearMallAplication.getInstance().getUser() == null) {
//                    LoginActivity.starActivity(activity);
//                    return;
//                }
////                activity.mPresenter.getLoanData();
//                break;
////                Toast.makeText(getActivity(), "暂未开放", Toast.LENGTH_SHORT).show();
//            case R.id.n_v_home_8:
//                // TODO 游戏中心
//                activity.startActivity(new Intent(activity, ZanWeiKaiFangActivity.class));
////                Toast.makeText(getActivity(), "暂未开放", Toast.LENGTH_SHORT).show();
//                break;
        }


    }

}

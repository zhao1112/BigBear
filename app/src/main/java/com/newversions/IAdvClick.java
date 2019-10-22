package com.newversions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.trade.biz.applink.adapter.AlibcFailModeType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.google.gson.Gson;
import com.newversions.detail.NewProductDetailActivity;
import com.newversions.hd.HdActivity;
import com.newversions.tbk.activity.GoodsDetailActivity;
import com.newversions.tbk.activity.WebActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.LoanBean;
import com.yunqin.bearmall.eventbus.ChangeFragment;
import com.yunqin.bearmall.ui.activity.BargainFreeActivity;
import com.yunqin.bearmall.ui.activity.BargainFreeDetailActivity;
import com.yunqin.bearmall.ui.activity.ChargeActivity;
import com.yunqin.bearmall.ui.activity.HomeActivity;
import com.yunqin.bearmall.ui.activity.LoanActivity;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.VanguardListPageActivity;
import com.yunqin.bearmall.ui.activity.VipCenterActivity;
import com.yunqin.bearmall.ui.activity.ZeroMoneyActivity;
import com.yunqin.bearmall.ui.activity.ZeroMoneyDetailsActivity;
import com.yunqin.bearmall.util.ArouseTaoBao;
import com.yunqin.bearmall.util.DeviceUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一封装广告点击事件
 */
public class IAdvClick {

    public static void click(Context context, int type, int skipType, long sourceId, String adUrl) {
        Log.i("IAdvClick", "type: " + type + "\n" + "skipType: " + skipType + "\n" + "sourceId: " + sourceId + "\n" + adUrl);
        if (type == 0) {
            Intent intent = new Intent(context, NewProductDetailActivity.class);
            intent.putExtra("productId", "" + sourceId);
            intent.putExtra("sku_id", "");
            context.startActivity(intent);
        } else if (type == 1) {
            // 说明展示不处理
        } else if (type == 2) {
            if (skipType == 1) {
                EventBus.getDefault().post(new ChangeFragment(1));
                if (context instanceof HomeActivity) {
                    return;
                }
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
                return;
            }
            // 推荐导购
            String guidelUrl = BuildConfig.BASE_URL + "/view/findGuideArticleDetailPage?guideArticle_id=" + sourceId;
            VanguardListPageActivity.startH5Activity(context, guidelUrl, "导购详情");
        } else if (type == 3) {
            // 会员商城
            context.startActivity(new Intent(context, MemberMallActivity.class));
        } else if (type == 4) {
            // 特价活动
            if (skipType == 0) {
                Intent intent = new Intent(context, NewProductDetailActivity.class);
                intent.putExtra("productId", "" + sourceId);
                intent.putExtra("sku_id", "");
                context.startActivity(intent);
            } else {
//                VanguardListPageActivity.startH5Activity(context, VanguardListPageActivity.loadUrlActivity + sourceId, "活动详情");
                Intent intent = new Intent(context, HdActivity.class);
                intent.putExtra("discountCampaign_id", "" + sourceId);
                context.startActivity(intent);
            }
        } else if (type == 5) {
            if (skipType == 0) {
                Intent intent = new Intent(context, ZeroMoneyDetailsActivity.class);
                intent.putExtra("groupPurchasing_id", sourceId + "");
                context.startActivity(intent);
            } else {
                context.startActivity(new Intent(context, ZeroMoneyActivity.class));
            }
        } else if (type == 6) {
            if (skipType == 0) {
                Intent intent = new Intent(context, BargainFreeDetailActivity.class);
                intent.putExtra(BargainFreeDetailActivity.BARGAIN_PRODUCT_ID, sourceId);
                intent.putExtra(BargainFreeDetailActivity.BARGAIN_IS_ONGOING, false);
                context.startActivity(intent);
            } else {
                context.startActivity(new Intent(context, BargainFreeActivity.class));
            }
        } else if (type == 7) {
            // TODO 充值中心

            if (BearMallAplication.getInstance().getUser() == null) {

                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                return;
            }
            context.startActivity(new Intent(context, ChargeActivity.class));


        } else if (type == 8) {
            // TODO 会员中心
            if (BearMallAplication.getInstance().getUser() == null) {

                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                return;
            }

            VipCenterActivity.startVipCenterActivity(context, "", "");
        } else if (type == 9) {
            // TODO 信用卡中心

            if (BearMallAplication.getInstance().getUser() == null) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                return;
            }

            String token = BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token();
            String url = BuildConfig.BASE_URL + "/view/getCreditCardBankPage?access_token=" + token;

            CardListWebActivity.startActivity(context, url, "信用卡申请");

        } else if (type == 10) {


            HashMap map = new HashMap();
            map.put("deviceType", "安卓");
            map.put("device", DeviceUtils.getUniqueId(context));

            RetrofitApi.request(context, RetrofitApi.createApi(Api.class).getLoanSupermarketData(map), new RetrofitApi.IResponseListener() {

                @Override
                public void onSuccess(String data) throws JSONException {
                    LoanBean loanBean = new Gson().fromJson(data, LoanBean.class);
                    LoanActivity.startLoanActivity(context, loanBean.getData().getLinkSite(), "我要借钱");

                }

                @Override
                public void onNotNetWork() {

                }

                @Override
                public void onFail(Throwable e) {
//                    Toast.makeText(context,"网络错误，请稍后重试",Toast.LENGTH_SHORT).show();
                }
            });


        } else if (type == 11) {

            if (BearMallAplication.getInstance().getUser() == null) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                return;
            }


            while (!(BearMallAplication.getInstance().getActivityStack().getTopAct() instanceof HomeActivity)) {
                BearMallAplication.getInstance().getActivityStack().finishActivity(BearMallAplication.getInstance().getActivityStack().getTopAct());
            }

            EventBus.getDefault().post(new ChangeFragment(2));


        } else if (type == 12) {
            if (BearMallAplication.getInstance().getUser() == null) {

                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                return;
            }

            VipCenterActivity.startVipCenterActivity(context, "", "");
        } else if (type == 13) {
            if (!TextUtils.isEmpty(adUrl)) {
//                AlibcLogin alibcLogin = AlibcLogin.getInstance();
//                alibcLogin.showLogin(new AlibcLoginCallback() {
//                    @Override
//                    public void onSuccess(int result, String userId, String nick) {
//                        Log.i("onFailure", "userId: " + userId + "nick: " + nick);
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//                        Log.i("onFailure", "onFailure: " + s.toString() + "Code: " + i);
//                    }
//                });

                ArouseTaoBao arouseTaoBao = new ArouseTaoBao(context);
                if (arouseTaoBao.checkPackage("com.taobao.taobao")) {
                    arouseTaoBao.openTaoBao(adUrl);
                } else {
                    Toast.makeText(context, "您未安装淘宝，请先安装", Toast.LENGTH_LONG);
                }
            }
        }
    }
}

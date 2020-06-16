package com.yunqin.bearmall.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.trade.biz.applink.adapter.AlibcFailModeType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.bbcoupon.util.ConstantUtil;
import com.newversions.tbk.activity.WebActivity;
import com.yunqin.bearmall.ui.activity.LoginActivity;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.util
 * @DATE 2020/4/6
 */
public class AuntTao {

    private static String TAG = "AuntTao";
    private static String Invitation = "INVITATION";

    public static void AuntTabo(Context context) {
        if (ConstantUtil.getInvitation() != null) {
            boolean authorization = (boolean) SharedPreferencesHelper.get(context, ConstantUtil.AUTHORIZATION_SUCCESSFUL, false);
            if (authorization) {
                String InvitationCode = (String) SharedPreferencesHelper.get(context, Invitation, "");
                if (ConstantUtil.getInvitation().equals(InvitationCode)) {
                    openByUrlTaobao(CommonUtils.TabUrl, context);
                } else {
                    logOut(context);
                }
            } else {
                Authorization(context);
            }
        } else {
            LoginActivity.starActivity((Activity) context);
        }
    }


    //淘宝登录
    public static void Authorization(Context context) {
        AlibcLogin.getInstance().showLogin(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int i, String s, String s1) {
                SharedPreferencesHelper.put(context, Invitation, ConstantUtil.getInvitation());
                SharedPreferencesHelper.put(context, ConstantUtil.AUTHORIZATION_SUCCESSFUL, true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        openByUrlTaobao(CommonUtils.TabUrl, context);
                    }
                }, 200);
                Log.i(TAG, "获取淘宝用户信息: " + AlibcLogin.getInstance().getSession());
            }

            @Override
            public void onFailure(int code, String msg) {
                // code：错误码  msg： 错误信息
                Log.i(TAG, "code: " + code + "----msg" + msg);
            }
        });
    }

    //淘宝登出
    public static void logOut(Context context) {
        AlibcLogin.getInstance().logout(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int loginResult, String openId, String userNick) {
                Authorization(context);
                Log.i(TAG, "获取淘宝用户信息: " + AlibcLogin.getInstance().getSession());
            }

            @Override
            public void onFailure(int code, String msg) {
                // code：错误码  msg： 错误信息
                Log.i(TAG, "code: " + code + "----msg" + msg);
                Authorization(context);
            }
        });
    }

    //阿里百川打开授权url
    public static void openByUrlTaobao(String url, Context context) {
        AlibcShowParams showParams = new AlibcShowParams();
        showParams.setOpenType(OpenType.Native);
        showParams.setClientType("taobao");
        showParams.setNativeOpenFailedMode(AlibcFailModeType.AlibcNativeFailModeNONE);
        AlibcTrade.openByUrl((Activity) context, "", url, new WebView(context), new MyWebViewClient(context), new WebChromeClient(),
                showParams, null, null, new MyAlibcTradeCallback());
    }

    //拦截授权成功url
    public static class MyWebViewClient extends WebViewClient {

        private Context context;

        public MyWebViewClient(Context context) {
            this.context = context;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            WebActivity.startWebActivity(context, 10, url, "淘宝授权");
            Log.e(TAG, "onPageStarted：" + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e(TAG, "onPageFinished：" + url);
        }
    }

    //阿里百川授权回调
    public static class MyAlibcTradeCallback implements AlibcTradeCallback {

        @Override
        public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
            Log.e(TAG, "open detail page success");
        }

        @Override
        public void onFailure(int i, String s) {
            Log.e(TAG, "code: " + i + "  ---  msg: " + s);
        }
    }

}

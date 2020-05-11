package com.yunqin.bearmall.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ali.auth.third.core.MemberSDK;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.login.LoginService;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.alibaba.baichuan.trade.common.utils.AlibcLogger;
import com.bbcoupon.util.ConstantUtil;
import com.newversions.tbk.activity.GoodsDetailActivity;
import com.newversions.tbk.activity.WebActivity;
import com.yunqin.bearmall.ui.activity.HomeActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.util
 * @DATE 2020/4/6
 */
public class AuntTao {

    public Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void AuntTabo() {

        boolean authorization = (boolean) SharedPreferencesHelper.get(context, ConstantUtil.AUTHORIZATION_SUCCESSFUL, false);
        if (authorization) {
            openByUrlTaobao(CommonUtils.TabUrl);
        } else {
            AlibcLogin alibcLogin = AlibcLogin.getInstance();
            alibcLogin.showLogin(new AlibcLoginCallback() {
                @Override
                public void onSuccess(int loginResult, String openId, String userNick) {
                    // 参数说明：
                    // loginResult(0--登录初始化成功；1--登录初始化完成；2--登录成功)
                    // openId：用户id
                    // userNick: 用户昵称
                    SharedPreferencesHelper.put(context, ConstantUtil.AUTHORIZATION_SUCCESSFUL, true);
                    openByUrlTaobao(CommonUtils.TabUrl);
                    Log.i("alibcLogin", "获取淘宝用户信息: " + AlibcLogin.getInstance().getSession());
                }

                @Override
                public void onFailure(int code, String msg) {
                    // code：错误码  msg： 错误信息
                    Log.i("alibcLogin", "code: " + code + "----msg" + msg);
                    if (onBack != null) {
                        onBack.onFailure(code);
                    }
                }
            });
        }
    }

    public void openByUrlTaobao(String url) {

        AlibcShowParams showParams = new AlibcShowParams();
        showParams.setOpenType(OpenType.Native);
        showParams.setBackUrl("");
        AlibcTaokeParams taokeParams = new AlibcTaokeParams("", "", "");

        Map<String, String> trackParams = new HashMap<>();

        AlibcTrade.openByUrl((Activity) context, "", url, new WebView(context),
                new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        WebActivity.startWebActivity(context, 10, url, "淘宝授权");
                        Log.e("WebViewActivity---", url);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);

                    }

                }, new WebChromeClient(), showParams, taokeParams, trackParams, new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(AlibcTradeResult tradeResult) {

                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }

    public interface OnBack {
        void onFailure(int code);
    }

    public OnBack onBack;

    public void setOnBack(OnBack onBack) {
        this.onBack = onBack;
    }


}

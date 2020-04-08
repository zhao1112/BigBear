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
import com.alibaba.baichuan.trade.common.utils.AlibcLogger;
import com.newversions.tbk.activity.GoodsDetailActivity;
import com.newversions.tbk.activity.WebActivity;

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
        LoginService loginService = MemberSDK.getService(LoginService.class);
        if (loginService != null) {
            loginService.auth((Activity) context, new LoginCallback() {
                @Override
                public void onSuccess(Session session) {
                    Log.e("WebViewActivity", "s");
                    openByUrlTaobao(CommonUtils.TabUrl);
                }

                @Override
                public void onFailure(int i, String s) {
                    if (i == 111) {
                        Toast.makeText(context, "请不要频繁调用", Toast.LENGTH_LONG).show();
                    }
                    Log.e("WebViewActivity", s);
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

}

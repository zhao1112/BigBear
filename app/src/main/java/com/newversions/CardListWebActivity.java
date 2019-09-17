package com.newversions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newversions.view.ICustomDialog3;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.ZhuanQianBean;

import org.json.JSONException;

/**
 * 通用点击无效，单独配置
 * 申请信用卡列表页面
 */

public class CardListWebActivity extends BaseActivity {

    private WebView webView;

    private boolean REPORT_ADV = false;


    public static void startActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, CardListWebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }


    @Override
    public int layoutId() {
        return R.layout.activity_card_list_web;
    }

    @Override
    public void init() {
        TextView top_title = findViewById(R.id.top_title);


        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");

        if (title != null && title.equals("幸运大抽奖")) {
            REPORT_ADV = true;
        }


        top_title.setText(title);

        webView = findViewById(R.id.web_view);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebViewClient(new IWebViewClient());


        webView.loadUrl(url);

        findViewById(R.id.new_version_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardListWebActivity.this.finish();
            }
        });
    }


    class IWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }


        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            REPORT_ADV = false;// 加载网页失败了
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (REPORT_ADV) {
                REPORT_ADV = false;
                commitAdvShow();
            }
        }


        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }


        @Override
        public void onReceivedLoginRequest(WebView view, String realm, @Nullable String account, String args) {
            super.onReceivedLoginRequest(view, realm, account, args);
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

    }


    @Override
    public void onBackPressed() {

        CardListWebActivity.this.finish();

//        if (webView.canGoBack()) {
//            webView.goBack();
//        } else {
//            finish();
//        }
    }


    private void commitAdvShow() {


        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).partLuckyDraw(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getLuckyDrawInfo(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {


                ZhuanQianBean zhuanQianBean = new Gson().fromJson(data, ZhuanQianBean.class);


                if (zhuanQianBean != null && zhuanQianBean.getData() != null
                        && zhuanQianBean.getData().getDrawActivityInfo() != null &&
                        zhuanQianBean.getData().getDrawActivityInfo().size() > 0) {
                    new ICustomDialog3.Builder(CardListWebActivity.this)
                            .setCustomTextIds(zhuanQianBean)
                            .build()
                            .show();
                }


            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });


    }


}

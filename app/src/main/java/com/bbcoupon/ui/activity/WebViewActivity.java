package com.bbcoupon.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bbcoupon.util.ConstantUtil;
import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.GoodsDetailActivity;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.ui.activity.NewWebViewActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/5/9
 */
public class WebViewActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.webview)
    WebView mWebview;
    @BindView(R.id.toolbar_clear)
    ImageView mToolbarClear;
    @BindView(R.id.toolbar_refresh)
    ImageView mToolbarRefresh;

    public static void openWebViewActivity(Activity activity, Bundle bundle) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void init() {
        String web_url = getIntent().getStringExtra("Web_Url");
        String web_tiele = getIntent().getStringExtra("Web_Tiele");
        String web_type = getIntent().getStringExtra("Web_Type");

        if (TextUtils.isEmpty(web_url)) {
            return;
        }
        if (!TextUtils.isEmpty(web_tiele)) {
            mTitle.setText(web_tiele);
        }

        if (ConstantUtil.DOUBLING_RULE.equals(web_type)) {
            mToolbarRefresh.setVisibility(View.GONE);
        }

        mWebview.loadUrl(web_url);
        setWebview(mWebview);
        mWebview.setWebViewClient(new MyWebViewClint());
    }

    @SuppressLint("JavascriptInterface")
    public void setWebview(WebView webview) {
        webview.addJavascriptInterface(this, "android");
        WebSettings web = webview.getSettings();
        web.setUserAgentString(web.getUserAgentString() + " WebView");
        web.setJavaScriptEnabled(true);  //支持js
        web.setUseWideViewPort(false);  //将图片调整到适合webview的大小
        web.setSupportZoom(true);  //支持缩放
        web.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        web.supportMultipleWindows();  //多窗口
        web.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
        web.setAllowFileAccess(true);  //设置可以访问文件
        web.setDomStorageEnabled(true);// 打开本地缓存提供JS调用,至关重要
        web.setAppCacheMaxSize(1024 * 1024 * 8);// 实现8倍缓存
        web.setAllowFileAccess(true);
        web.setAppCacheEnabled(true);
        web.setDatabaseEnabled(true);
    }


    @OnClick({R.id.toolbar_back, R.id.toolbar_clear, R.id.toolbar_refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                if (mWebview.canGoBack()) {
                    mWebview.goBack();
                } else {
                    finish();
                }
                break;
            case R.id.toolbar_clear:
                finish();
                break;
            case R.id.toolbar_refresh:
                mWebview.reload();
                break;
        }
    }


    public class MyWebViewClint extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //设定加载开始的操作
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            //设定加载结束的操作

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            //设定加载失败
            showToast("加载失败");
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            //设定加载资源的操作
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebview != null) {
            try {
                mWebview.setVisibility(View.GONE);
                mWebview.removeAllViews();
                mWebview.clearHistory();
                mWebview.clearCache(true);
                mWebview.destroy();
            } catch (Throwable t) {

            }
            mWebview = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

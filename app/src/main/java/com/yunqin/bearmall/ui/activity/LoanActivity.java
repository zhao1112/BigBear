package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.util.ShareUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2019/4/18
 * @Describe
 */
public class LoanActivity extends BaseActivity {

    @BindView(R.id.content)
    RelativeLayout content;

    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;


    WebView webView;

    private String loadUrl = "";


    public static void startLoanActivity(Context mContext, String loadUrl, String activityName) {
        Intent intent = new Intent(mContext, LoanActivity.class);
        intent.putExtra("loadUrl", loadUrl);
        intent.putExtra("activityName", activityName);
        mContext.startActivity(intent);
    }


    @OnClick({R.id.toolbar_back})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                backClick();
                break;
        }
    }


    @Override
    public int layoutId() {
        return R.layout.activity_loan;
    }

    @Override
    public void init() {

        if (getIntent() != null) {
            try {
                loadUrl = (String) getIntent().getExtras().get("loadUrl");
                Log.e("LoaNuRL",loadUrl);
                toolbar_title.setText((String) getIntent().getExtras().get("activityName"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        webView = new WebView(BearMallAplication.getInstance());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(params);
        content.addView(webView);

        WebSettings webSettings = webView.getSettings();
        /*LOAD_CACHE_ELSE_NETWORK是默认的网络缓存机制这里不建议使用，建议使用LOAD_DEFAULT。
        前者在H5页面更新后容易出现页面错乱。
        如果网页的cache-control为no-cache，在模式LOAD_DEFAULT下，根据cache-control（网页是否更新过）
        决定是否从网络上取数据，如果更新过就重新获取没有更新过使用缓存，断网时会出现错误页面；
        在LOAD_CACHE_ELSE_NETWORK模式下，无论是否有网络，只要本地有缓存，都使用缓存。
        本地没有缓存时才从网络上获取。
        如果网页的的cache-control为max-age=60，在两种模式下都使用本地缓存数据
        为了建立完善的缓存机制，建议先判断是否网络情况正常，正常情况使用使用LOAD_DEFAULT，
        无网络时，使用LOAD_CACHE_ELSE_NETWORK。
        LOAD_CACHE_ONLY:不使用网络，只读取本地缓存数据
        LOAD_DEFAULT:根据cache-control决定是否从网络上取数据。
        LOAD_CACHE_NORMAL: API level 17中已经废弃, 从API level 11开始作用同LOAD_DEFAULT模式
        LOAD_NO_CACHE:不使用缓存，只从网络获取数据.
        LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据
        */
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setJavaScriptEnabled(true);//设置支持JS
        /* 设置为使用webview推荐的窗口，主要是为了配合下一个属性 */
        webSettings.setUseWideViewPort(true);
        /* 设置网页自适应屏幕大小，该属性必须和上一属性配合使用 */
        webSettings.setLoadWithOverviewMode(true);
        /* 提高网页渲染的优先级 */
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);


        //解决WebView链接为https，内容图片为http，图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }
        webView.loadUrl(loadUrl);
        webView.setWebViewClient(new SafeWebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    hiddenLoadingView();
                    progressBar1.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    showLoading();
                    progressBar1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar1.setProgress(newProgress);//设置进度值
                }
            }

        });
    }

    public class SafeWebViewClient extends WebViewClient {

        /**
         * 当WebView得页面Scale值发生改变时回调
         */
        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
        }

        /**
         * 是否在 WebView 内加载页面
         *
         * @param view
         * @param url
         * @return
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        /**
         * WebView 开始加载页面时回调，一次Frame加载对应一次回调
         *
         * @param view
         * @param url
         * @param favicon
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        /**
         * WebView 完成加载页面时回调，一次Frame加载对应一次回调
         *
         * @param view
         * @param url
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        /**
         * WebView 加载页面资源时会回调，每一个资源产生的一次网络加载，除非本地有当前 url 对应有缓存，否则就会加载。
         *
         * @param view WebView
         * @param url  url
         */
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        /**
         * WebView 可以拦截某一次的 request 来返回我们自己加载的数据，这个方法在后面缓存会有很大作用。
         *
         * @param view    WebView
         * @param request 当前产生 request 请求
         * @return WebResourceResponse
         */
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

        /**
         * WebView 访问 url 出错
         *
         * @param view
         * @param request
         * @param error
         */
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        /**
         * WebView ssl 访问证书出错，handler.cancel()取消加载，handler.proceed()对然错误也继续加载
         *
         * @param view
         * @param handler
         * @param error
         */
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
        }
    }


    private void backClick() {
        if (webView.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
            webView.goBack();
        } else {//当webview处于第一页面时,直接退出程序
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.setVisibility(View.GONE);
            webView.removeAllViews();
            webView.destroy();
        }
    }

}

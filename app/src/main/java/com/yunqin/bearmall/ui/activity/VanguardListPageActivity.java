package com.yunqin.bearmall.ui.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.arch.persistence.room.util.StringUtil;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.newversions.detail.NewProductDetailActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.ui.activity.contract.VanguardListPageContract;
import com.yunqin.bearmall.ui.activity.presenter.VanguardListPagePresenter;
import com.yunqin.bearmall.util.ShareUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2018/7/24
 * @Describe
 */
public class VanguardListPageActivity extends BaseActivity implements VanguardListPageContract.UI {

    @BindView(R.id.content)
    RelativeLayout content;

    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.toolbar_right)
    RelativeLayout toolbar_right;

    private VanguardListPageContract.Persenter persenter;
    private String guideArticle_id;

    private ShareBean shareBean = null;

    private boolean webViewIsLoadFinish;

    WebView webView;

    private String loadUrl = "";

    public static final String loadUrlVanguar = BuildConfig.BASE_URL + "/view/findVanguardListPage?access_token=" + (BearMallAplication.getInstance().getUser() == null ? "" : BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
    public static final String loadUrlDxpage = BuildConfig.BASE_URL + "/view/findListenDxPage";
    public static final String loadUrlIntropage = BuildConfig.BASE_URL + "/view/findIntroPage";

    public static final String loadUrlActivity = BuildConfig.BASE_URL + "/view/findArticleDetailPage?article_id=";

    public static void startH5Activity(Context mContext, String loadUrl, String activityName) {
        Intent intent = new Intent(mContext, VanguardListPageActivity.class);
        intent.putExtra("loadUrl", loadUrl);
        intent.putExtra("activityName", activityName);
        mContext.startActivity(intent);
    }


    @Override
    public int layoutId() {
        return R.layout.activity_vanguard_listpage;
    }

    @OnClick({R.id.toolbar_back, R.id.toolbar_right})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                backClick();
                break;
            case R.id.toolbar_right:
                if (!webViewIsLoadFinish) {
                    return;
                }
                if (shareBean == null) {
                    showLoading();
                    guideArticle_id = loadUrl.split("=")[1];
                    Constans.params.clear();
                    Constans.params.put("source_id", guideArticle_id);
                    Constans.params.put("type", 0 + "");
                    persenter.getShareData(Constans.params);
                } else {
                    ShareUtil.Share(this, shareBean.getData(),true);
                }
                break;
        }
    }


    @Override
    public void init() {

        persenter = new VanguardListPagePresenter(this, this);

        if (getIntent() != null) {
            try {

                loadUrl = (String) getIntent().getExtras().get("loadUrl");
                toolbar_title.setText((String) getIntent().getExtras().get("activityName"));
                if (toolbar_title.getText().toString().equals("导购详情")) {
                    toolbar_right.setVisibility(View.VISIBLE);
                } else {
                    toolbar_right.setVisibility(View.GONE);
                }

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
        webView.setWebViewClient(new myWebcliend());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    hiddenLoadingView();
                    webViewIsLoadFinish = true;
                    progressBar1.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    showLoading();
                    webViewIsLoadFinish = false;
                    progressBar1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar1.setProgress(newProgress);//设置进度值
                }
            }

        });
    }


    class myWebcliend extends WebViewClient {

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            if (url.isEmpty()) return null;
            Log.e("shouldInter",url);
            if (url.contains("/view/findProductDetailPage")) {
                String id = url.split("=")[1];

                Intent intent = new Intent(VanguardListPageActivity.this, NewProductDetailActivity.class);
                intent.putExtra("productId", "" + id);
                intent.putExtra("sku_id", "");
                startActivity(intent);








            }
            return null;
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            if (url.isEmpty()) return null;
            Log.e("should",url);
            if (url.contains("/view/findProductDetailPage")) {
                String id = url.split("=")[1];


                Intent intent = new Intent(VanguardListPageActivity.this, NewProductDetailActivity.class);
                intent.putExtra("productId", "" + id);
                intent.putExtra("sku_id", "");
                startActivity(intent);

            }
            return null;
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.isEmpty()) return true;
            return true;
            //return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (!webView.getSettings().getLoadsImagesAutomatically()) {
                webView.getSettings().setLoadsImagesAutomatically(true);
            }
        }
    }

    //设置返回键动作（防止按返回键直接退出程序)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

    //导购详情分享
    @Override
    public void attach(ShareBean shareBean) {
        hiddenLoadingView();
        this.shareBean = shareBean;
        ShareUtil.Share(this, shareBean.getData(),true);
    }

    @Override
    public void onError() {
        this.shareBean = null;
        hiddenLoadingView();
    }


}

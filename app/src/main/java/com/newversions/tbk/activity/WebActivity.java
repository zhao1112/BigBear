package com.newversions.tbk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSONException;
import com.google.gson.Gson;
import com.newversions.tbk.Constants;
import com.newversions.tbk.utils.MyWebViewClient;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.Checkzero;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.util.ArouseTaoBao;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class WebActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar;
    @BindView(R.id.tv_top)
    TextView tvTop;
    @BindView(R.id.im_search)
    ImageView imSearch;
    @BindView(R.id.im_share)
    ImageView imShare;
    @BindView(R.id.im_buy)
    ImageView imBuy;
    @BindView(R.id.rl_bottom)
    LinearLayout rlBottom;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    //    private TBKShareEntity tbkShareEntity;
    private int type;
    private String goodsId;

    public static void startWebActivity(Context activity, int type, String url, String title) {
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra(Constants.INTENT_KEY_TITLE, title);
        intent.putExtra(Constants.INTENT_KEY_TYPE, type);
        intent.putExtra(Constants.INTENT_KEY_URL, url);
        activity.startActivity(intent);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void init() {
        MyWebViewClient myWebViewClient = new MyWebViewClient(this);
        type = getIntent().getIntExtra(Constants.INTENT_KEY_TYPE, 1);
        //打开
        String stringUrl = getIntent().getStringExtra(Constants.INTENT_KEY_URL);
        String title = getIntent().getStringExtra(Constants.INTENT_KEY_TITLE);
        toolbarTitle.setText(title);
        mWebView.loadUrl(stringUrl);
        mWebView.addJavascriptInterface(this, "android");
        setWebViewAttribute(mWebView);
        mWebView.setWebViewClient(myWebViewClient);
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }

        });
        /* 同上,重写WebViewClient可以监听网页的跳转和资源加载等等... */
        mWebView.setWebViewClient(new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith("scheme:") || url.startsWith("scheme:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }

                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                int index;
                if (type == 66 && (index = url.indexOf("&id=")) > 0) {
                    rlBottom.setVisibility(View.VISIBLE);

                    tvTop.setVisibility(View.VISIBLE);
                    goodsId = url.substring(index + 4);
                    goodsId = goodsId.substring(0, goodsId.indexOf("&"));
                } else {
                    rlBottom.setVisibility(View.GONE);
                    tvTop.setVisibility(View.GONE);
                }
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                showToast("加载失败");
                super.onReceivedError(view, errorCode, description, failingUrl);
            }


        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            try {
                mWebView.setVisibility(View.GONE);
                mWebView.removeAllViews();
                mWebView.clearHistory();
                mWebView.clearCache(true);
                mWebView.destroy();
            } catch (Throwable t) {
            }
            mWebView = null;
        }
    }

    /**
     * 设置WebView 属性
     */
    public static void setWebViewAttribute(WebView view) {
        WebSettings webSettings = view.getSettings();

        webSettings.setJavaScriptEnabled(true);  //支持js

        webSettings.setUseWideViewPort(false);  //将图片调整到适合webview的大小

        webSettings.setSupportZoom(true);  //支持缩放

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局

        webSettings.supportMultipleWindows();  //多窗口

        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存

        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);// 打开本地缓存提供JS调用,至关重要
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);// 实现8倍缓存
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);

    }

    @OnClick({R.id.tv_top, R.id.toolbar_back, R.id.im_search, R.id.im_share, R.id.im_buy, R.id.toolbar_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_close:
                finish();
                break;
            case R.id.toolbar_back:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }
                break;
            case R.id.tv_top:
                break;
            case R.id.im_search:
                if (BearMallAplication.getInstance().getUser() != null) {
                    getMsg();
                } else {
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                    LoginActivity.starActivity(this);
                }


                break;
            case R.id.im_share:
//                if (tbkShareEntity == null) {
//                    return;
//                }

//                Intent intent = new Intent(this, ShareComissionActivity.class);
//                intent.putExtra(Constants.INTENT_KEY_URL, tbkShareEntity.getItemUrl());
//                intent.putExtra(Constants.INTENT_KEY_TYPE, 4);
//                intent.putExtra(Constants.INTENT_KEY_ID, tbkShareEntity.getNumIid() + "");
//                startActivity(intent);
                break;
            case R.id.im_buy:
//                if (tbkShareEntity == null) {
//                    return;
//                }
//                showLoading();
//                Toast.makeText(this, "正在跳转淘宝", Toast.LENGTH_LONG).show();
                if (checkPackage("com.taobao.taobao")) {
//                    AlibcPage alibcPage = new AlibcPage(tbkShareEntity.getCouponStartFee());
//                    AlibcShowParams alibcShowParams = new AlibcShowParams();
//                    alibcShowParams.setTitle("   ");
//                    alibcShowParams.setOpenType(OpenType.Native);
//                    AlibcTrade.show(this, alibcPage, alibcShowParams, null, null, new AlibcTradeCallback() {
//
//                        @Override
//                        public void onTradeSuccess(TradeResult tradeResult) {
//
//                        }
//
//                        @Override
//                        public void onFailure(int code, String msg) {
//                            //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
//
//                        }
//                    });


                } else {
                    showToast("请先下载淘宝");
                    hiddenLoadingView();
                }

                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        hiddenLoadingView();
    }

    private void getMsg() {
        showLoading();
//        RetrofitFactory.getInstence().API()
//                .getTmailGoodDetail(CacheInfo.getUserID(this), goodsId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<TBKShareEntity>() {
//                    @Override
//                    protected void onSuccees(BaseEntity<TBKShareEntity> t) throws Exception {
//                        discussProgressDialog();
//                        tbkShareEntity = t.getData();
//                        tvTop.setText("预估收益  ¥" + tbkShareEntity.getCommissionRate() + "元");
//                        imSearch.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                        discussProgressDialog();
//                    }
//                });
    }

    public boolean checkPackage(String packageName) {

        if (packageName == null || "".equals(packageName))

            return false;

        try {

            this.getPackageManager().getApplicationInfo(packageName, PackageManager
                    .GET_UNINSTALLED_PACKAGES);

            return true;

        } catch (PackageManager.NameNotFoundException e) {

            return false;

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @JavascriptInterface
    public void buy(String type, String sendurl)

    {
        Log.i("setWebViewAttribute", type + "----" + sendurl);
        if (TextUtils.isEmpty(sendurl)) {
            return;
        }
        if (BearMallAplication.getInstance().getUser() == null) {
            LoginActivity.starActivity(this);
        } else {
            if ("1".equals(type)) {
                if (sendurl != null) {
                    setFrequency(sendurl);
                }
            } else {
                if (sendurl != null) {
                    toTaobao(sendurl);
                }
            }
        }
    }

    public void setFrequency(String sendurl) {
        RetrofitApi.request(WebActivity.this, RetrofitApi.createApi(Api.class).getCheckzero(),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        Log.i("onSuccess", data);
                        if (data != null) {
                            Checkzero checkzero = new Gson().fromJson(data, Checkzero.class);
                            if (checkzero.getData().getSuccess() == 1) {
                                toTaobao(sendurl);
                                updateFreeInfo();
                            }
                            if (checkzero.getData().getSuccess() == 0) {
                                Toast.makeText(WebActivity.this, "限领取1次新人红包", Toast.LENGTH_LONG).show();
                            }
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

    private void updateFreeInfo() {
        Map<String, String> map = new HashMap<>();
        if (BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() == null) {
            return;
        }
        map.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
        RetrofitApi.request(WebActivity.this, RetrofitApi.createApi(Api.class).getUpdateFreeInfo(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws org.json.JSONException {

            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    public void toTaobao(String sendurl) {
        ArouseTaoBao arouseTaoBao = new ArouseTaoBao(WebActivity.this);
        if (arouseTaoBao.checkPackage("com.taobao.taobao")) {
            setclickzero(sendurl);
            arouseTaoBao.openTaoBao(sendurl);
        } else {
            showToast("请先下载淘宝");
            hiddenLoadingView();
        }
    }

    public void setclickzero(String sendurl) {
        Map<String, String> map = new HashMap<>();
        map.put("sendUrl", sendurl);
        RetrofitApi.request(WebActivity.this, RetrofitApi.createApi(Api.class).clickzero(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws org.json.JSONException {

            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    //设置手机屏幕亮度变暗
    private void lightoff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
    }

    //设置手机屏幕亮度显示正常
    private void lighton() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
    }

}

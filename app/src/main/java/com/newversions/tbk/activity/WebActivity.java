package com.newversions.tbk.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.newversions.tbk.Constants;
import com.newversions.tbk.utils.MyWebViewClient;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.contract.WebContract;
import com.yunqin.bearmall.ui.activity.presenter.WebPresenter;
import com.yunqin.bearmall.util.ArouseTaoBao;
import com.yunqin.bearmall.util.ConstUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class WebActivity extends BaseActivity implements View.OnClickListener, WebContract.UI {

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
    @BindView(R.id.rl_bottom2)
    LinearLayout mRlBottom2;
    //    private TBKShareEntity tbkShareEntity;
    private int type;
    private String goodsId;
    private PopupWindow mMPopupWindow;
    private String mStringUrl;
    private WebPresenter mWebPresenter;
    private String itemId;
    private String mTitle;
    String newUrl = null;

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
        mStringUrl = getIntent().getStringExtra(Constants.INTENT_KEY_URL);
        mTitle = getIntent().getStringExtra(Constants.INTENT_KEY_TITLE);
        toolbarTitle.setText(mTitle);
        if (mStringUrl.contains("hd/list")) {
            newUrl = mStringUrl + "?recommendCode=" + BearMallAplication.getInstance().getUser().getRecommendCode();
        } else {
            newUrl = mStringUrl;
        }
        mWebView.loadUrl(newUrl);
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
                if (url.startsWith("scheme:") || url.startsWith("scheme:") || url.startsWith("alipays:")) {
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
                if (ConstUtils.WEB_TYPE == type && view.getProgress() == 100) {
                    mRlBottom2.setVisibility(View.VISIBLE);
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

        mWebPresenter = new WebPresenter(this, this);
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
        webSettings.setUserAgentString(webSettings.getUserAgentString() + " WebView");
        webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setUseWideViewPort(false);  //将图片调整到适合webview的大小
        webSettings.setSupportZoom(true);  //支持缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setDomStorageEnabled(true);// 打开本地缓存提供JS调用,至关重要
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);// 实现8倍缓存
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
    }

    @OnClick({R.id.tv_top, R.id.toolbar_back, R.id.im_search, R.id.im_share, R.id.im_buy, R.id.toolbar_close, R.id.invitation_button})
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
                break;
            case R.id.im_buy:
                if (checkPackage("com.taobao.taobao")) {
                } else {
                    showToast("请先下载淘宝");
                    hiddenLoadingView();
                }
                break;
            case R.id.invitation_button:
                showDialog();
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
    public void buy(String type, String sendurl) {
        Log.i("setWebViewAttribute", type + "----" + sendurl);
        if (TextUtils.isEmpty(sendurl)) {
            return;
        }
        if (BearMallAplication.getInstance().getUser() == null) {
            LoginActivity.starActivity(this);
        } else {
            if ("1".equals(type)) {
                if (sendurl != null) {
                    showLoading();
                    itemId = sendurl;
                    mWebPresenter.Checkzero();
                }
            } else if ("2".equals(type)) {
                if (sendurl != null) {
                    itemId = sendurl;
                    mWebPresenter.Checkinvitation();
                }
            } else {
                if (sendurl != null) {
                    showLoading();
                    Log.i("mWebPresenter", "sendurl = " + sendurl);
                    mWebPresenter.Clickurl(BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token(), sendurl);
                }
            }
        }
    }

    public void toTaobao(String sendurl) {
        ArouseTaoBao arouseTaoBao = new ArouseTaoBao(WebActivity.this);
        if (arouseTaoBao.checkPackage("com.taobao.taobao")) {
            arouseTaoBao.openTaoBao(sendurl);
        } else {
            showToast("请先下载淘宝");
            hiddenLoadingView();
        }
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

    private void showDialog() {
        lightoff();
        View view = LayoutInflater.from(WebActivity.this).inflate(R.layout.share_video_layout, null);
        mMPopupWindow = new PopupWindow();
        mMPopupWindow.setContentView(view);
        mMPopupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        mMPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置背景图片， 必须设置，不然动画没作用
        mMPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mMPopupWindow.setFocusable(false);
        // 设置点击popupwindow外屏幕其它地方消失
        mMPopupWindow.setOutsideTouchable(true);
        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        mMPopupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        view.findViewById(R.id.canle).setOnClickListener(this);
        view.findViewById(R.id.wx_share).setOnClickListener(this);
        view.findViewById(R.id.pyq_share).setOnClickListener(this);
        view.findViewById(R.id.qq_share).setOnClickListener(this);
        mMPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lighton();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.canle:
                mMPopupWindow.dismiss();
                break;
            case R.id.wx_share:
                final IWXAPI api1 = WXAPIFactory.createWXAPI(WebActivity.this, null);
                api1.registerApp(Constans.WX_APPID);  //将APP注册到微信
                if (api1.isWXAppInstalled()) {
                    showShare(Wechat.NAME);
                    mMPopupWindow.dismiss();
                } else {
                    Toast.makeText(WebActivity.this, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.pyq_share:
                final IWXAPI api = WXAPIFactory.createWXAPI(WebActivity.this, null);
                api.registerApp(Constans.WX_APPID);  //将APP注册到微信
                if (api.isWXAppInstalled()) {
                    showShare(WechatMoments.NAME);
                    mMPopupWindow.dismiss();
                } else {
                    Toast.makeText(WebActivity.this, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.qq_share:
                if (isQQClientAvailable(WebActivity.this)) {
                    showShare(QQ.NAME);
                    mMPopupWindow.dismiss();
                } else {
                    Toast.makeText(WebActivity.this, "请先安装QQ客户端", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void showShare(String platform2) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.app_0image);
        Platform platform = ShareSDK.getPlatform(platform2);//获取平台对象
        Platform.ShareParams shareParams = new Platform.ShareParams();//分享的参数
        shareParams.setTitle("每天都送免单商品！快跟我一起来领吧！");
        Log.i("showShare", BearMallAplication.getInstance().getUser().getRecommendCode());
        shareParams.setTitleUrl(mStringUrl);
        shareParams.setImageData(bitmap);//图片的地址
        shareParams.setText("天天抢免单！手慢无！");
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setUrl(mStringUrl);
        platform.share(shareParams);
    }

    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onNotNetWork() {
        hiddenLoadingView();
        showToast("网络不给力呀！");
    }

    @Override
    public void onFail(Throwable e) {
        hiddenLoadingView();
        Log.i("onFail", "onFail: " + e.getMessage());
        showToast(e.getMessage());
    }

    @Override
    public void onCheckzero() {
        mWebPresenter.Clickurl(BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token(), itemId);
    }

    @Override
    public void onOneTipe(String tipe) {
        hiddenLoadingView();
        showToast(tipe);
    }

    @Override
    public void onClickurl(String url) {
        hiddenLoadingView();
        toTaobao(url);
    }

    @Override
    public void onCheckinvitation() {
        mWebPresenter.Clickurl(BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token(), itemId);
        RefreshActivity();
    }

    public void RefreshActivity() {
        WebActivity.startWebActivity(WebActivity.this, type, mStringUrl, mTitle);
        finish();
    }
}

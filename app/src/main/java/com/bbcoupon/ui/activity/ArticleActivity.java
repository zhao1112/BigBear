package com.bbcoupon.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.wireless.security.open.middletier.fc.IFCActionCallback;
import com.bbcoupon.ui.adapter.ArticleAdapter;
import com.bbcoupon.ui.bean.ArticleInfor;
import com.bbcoupon.ui.bean.BaseInfor;
import com.bbcoupon.ui.bean.CommentInfor;
import com.bbcoupon.ui.bean.ContentInfor;
import com.bbcoupon.ui.bean.RequestInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bbcoupon.util.ConstantUtil;
import com.bbcoupon.util.ShareUtils;
import com.bbcoupon.util.WindowUtils;
import com.bbcoupon.widget.RefreshSchoolView;
import com.jaren.lib.view.LikeView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.newversions.tbk.utils.MyWebViewClient;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.ui.activity.BCMessageActivity;
import com.yunqin.bearmall.util.ShareUtil;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/6/2
 */
public class ArticleActivity extends BaseActivity implements View.OnClickListener, RequestContract.RequestView, TextWatcher {

    @BindView(R.id.ar_web)
    WebView mArWeb;
    @BindView(R.id.a_recycler)
    RecyclerView mARecycler;
    @BindView(R.id.a_refresh)
    TwinklingRefreshLayout mARefresh;
    @BindView(R.id.flVideoContainer)
    FrameLayout mFlVideoContainer;
    @BindView(R.id.linear)
    LinearLayout mLinear;
    @BindView(R.id.sc_comment)
    LinearLayout mScComment;
    @BindView(R.id.sc_scroll)
    NestedScrollView mScScroll;
    @BindView(R.id.se_live)
    LikeView mSeLive;
    @BindView(R.id.chat)
    ImageView mChat;
    @BindView(R.id.home_search)
    LinearLayout mHomeSearch;
    @BindView(R.id.ar_see_sume)
    TextView mArSeeSume;
    @BindView(R.id.ar_like_sume)
    TextView mArLikeSume;

    private ArticleAdapter articleAdapter;
    private boolean chat = true;
    private EditText ar_edit;
    private String articleid, aritcleimage, articletitle, aritclvoide;
    private RequestPresenter presenter;
    private int page = 1;
    private String webUrl = BuildConfig.BASE_URL + "/view/sharevideo/list?id=";
    private boolean isLike = true;
    private TextView text_conten;
    private Platform platform;


    public static void openArticleActivity(Activity activity, Class cla, Bundle bundle) {
        Intent intent = new Intent(activity, cla);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }


    @Override
    public int layoutId() {
        return R.layout.activity_article;
    }

    @Override
    public void init() {

        articleid = getIntent().getStringExtra("ARTICLEID");
        aritcleimage = getIntent().getStringExtra("ARITCLEIMAGE");
        articletitle = getIntent().getStringExtra("ARTICLETITLE");
        if (TextUtils.isEmpty(articleid)) {
            return;
        }

        webUrl = webUrl + articleid;
        Log.e("ARTICLEID", webUrl);

        presenter = new RequestPresenter();
        presenter.setRelation(this);
        Map<String, String> map = new HashMap<>();
        map.put("id", articleid);
        presenter.onNumberOfDetails(ArticleActivity.this, map);

        setWeb();

        mARefresh.setHeaderView(new RefreshHeadView(ArticleActivity.this));
        mARefresh.setBottomView(new RefreshBottomView(ArticleActivity.this));
        mARefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page = 1;
                articleAdapter.deleteData();
                getData(page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                page++;
                getData(page);
            }
        });

        mARecycler.setNestedScrollingEnabled(false);
        mARecycler.setLayoutManager(new LinearLayoutManager(ArticleActivity.this));
        articleAdapter = new ArticleAdapter(ArticleActivity.this);
        mARecycler.setAdapter(articleAdapter);

        mScScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {
                //判断某个控件是否可见
                Rect scrollBounds = new Rect();
                mScScroll.getHitRect(scrollBounds);
                if (mScComment.getLocalVisibleRect(scrollBounds)) {//可见
                    chat = false;
                    mChat.setImageDrawable(getResources().getDrawable(R.mipmap.art_wenzhang));
                } else {//完全不可见
                    chat = true;
                    mChat.setImageDrawable(getResources().getDrawable(R.mipmap.art_chat));
                }
            }
        });

        getData(page);
    }

    private void getData(int page) {
        Map<String, String> map = new HashMap<>();
        map.put("id", articleid);
        map.put("page", page + "");
        map.put("pageSize", "10");
        presenter.onCommentList(ArticleActivity.this, map);
    }

    private void setWeb() {

        try {
            if (Build.VERSION.SDK_INT >= 16) {
                Class<?> clazz = mArWeb.getSettings().getClass();
                Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(mArWeb.getSettings(), true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mArWeb.getSettings().setMixedContentMode(mArWeb.getSettings().MIXED_CONTENT_ALWAYS_ALLOW);
        }

        CookieManager cookieManager = CookieManager.getInstance();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("android");

        cookieManager.setCookie(webUrl, stringBuffer.toString());
        cookieManager.setAcceptCookie(true);

        mArWeb.loadUrl(webUrl);
        mArWeb.addJavascriptInterface(this, "android");
        setWebview(mArWeb);
        mArWeb.setWebViewClient(new MyWebViewClint());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        text_conten.setText(ar_edit.getText().toString().length() + "/200");
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public class MyWebViewClint extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("MyWebViewClint", url);
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.e("MyWebViewClint--开始", url);
            //设定加载开始的操作
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            float v = mArWeb.getContentHeight() * mArWeb.getScale();

            Log.e("MyWebViewClint--结束", v + "");
            //设定加载结束的操作
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScComment.setVisibility(View.VISIBLE);
                }
            }, 1000);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.e("MyWebViewClint--失败", failingUrl);
            //设定加载失败
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            Log.e("MyWebViewClint--", url);
            //设定加载资源的操作
        }
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof CommentInfor) {
            CommentInfor commentInfor = (CommentInfor) data;
            if (commentInfor != null && commentInfor.getData() != null && commentInfor.getData().size() > 0) {
                mARefresh.setBottomView(new RefreshBottomView(ArticleActivity.this));
                articleAdapter.addData(commentInfor.getData());
            } else {
                mARefresh.setBottomView(new RefreshSchoolView(ArticleActivity.this));
            }
        }
        if (data instanceof ArticleInfor) {
            ArticleInfor articleInfor = (ArticleInfor) data;
            if (articleInfor != null) {
                if (articleInfor.getData().getComment_num() != null) {
                    mArSeeSume.setText(articleInfor.getData().getComment_num());
                }
                if (articleInfor.getData().getLikes_num() != null) {
                    mArLikeSume.setText(articleInfor.getData().getLikes_num());
                }
                if (isLike) {
                    if (articleInfor.getData().getIsGiveTheThumbsUp() == 1) {
                        mSeLive.setAllowRandomDotColor(true);
                        mSeLive.toggleWithoutAnimator();
                    }
                    isLike = false;
                }
            }
        }
        if (data instanceof BaseInfor) {
            BaseInfor baseInfor = (BaseInfor) data;
            if (baseInfor.getCode() == 1) {
                showToast("评论提交成功");
                mScScroll.scrollTo(0, mScComment.getTop());
                Map<String, String> map = new HashMap<>();
                map.put("id", articleid);
                presenter.onNumberOfDetails(ArticleActivity.this, map);
                page = 1;
                articleAdapter.deleteData();
                getData(page);
            }
        }
        if (data instanceof ContentInfor) {
            ContentInfor requestInfor = (ContentInfor) data;
            if (requestInfor.getCode() == 1) {
                Map<String, String> map = new HashMap<>();
                map.put("id", articleid);
                presenter.onNumberOfDetails(ArticleActivity.this, map);
            }
        }
        if (data instanceof RequestInfor){
            RequestInfor requestInfor = (RequestInfor) data;
            if (requestInfor.getCode() == 1) {
                PopupWindow popupWindow = WindowUtils.timeShowOnly(ArticleActivity.this, R.layout.popup_tisp, R.style.TispAnim, 0);
                TextView value_tisp = popupWindow.getContentView().findViewById(R.id.value_tisp);
                value_tisp.setText("分享成功，获得" + requestInfor.getValue() + "个糖果，点击查看详情>>");
                popupWindow.getContentView().findViewById(R.id.top_tisp).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArticleActivity.this.startActivity(new Intent(ArticleActivity.this, BCMessageActivity.class));
                        WindowUtils.dismissOnly();
                    }
                });
            }
        }
        hiddenLoadingView();
        mARefresh.finishRefreshing();
        mARefresh.finishLoadmore();
    }

    @Override
    public void onNotNetWork() {
        hiddenLoadingView();
        mARefresh.finishRefreshing();
        mARefresh.finishLoadmore();
    }

    @Override
    public void onFail(Throwable e) {
        hiddenLoadingView();
        mARefresh.finishRefreshing();
        mARefresh.finishLoadmore();
    }

    private class MyWebChromeClient extends WebChromeClient {
        CustomViewCallback mCallback;

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            Log.i("ToVmp", "onShowCustomView");
            fullScreen();
            if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
                View v = ArticleActivity.this.getWindow().getDecorView();
                v.setSystemUiVisibility(View.GONE);
            } else if (Build.VERSION.SDK_INT >= 19) {
                //for new api versions.
                View decorView = getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(uiOptions);
            }
            mLinear.setVisibility(View.GONE);
            mFlVideoContainer.setVisibility(View.VISIBLE);
            mFlVideoContainer.addView(view);
            mCallback = callback;
            super.onShowCustomView(view, callback);
        }

        @Override
        public void onHideCustomView() {
            Log.i("ToVmp", "onHideCustomView");
            fullScreen();
            if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
                View v = ArticleActivity.this.getWindow().getDecorView();
                v.setSystemUiVisibility(View.VISIBLE);
            } else if (Build.VERSION.SDK_INT >= 19) {
                //for new api versions.
                View decorView = getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                decorView.setSystemUiVisibility(uiOptions);
            }
            mLinear.setVisibility(View.VISIBLE);
            mFlVideoContainer.setVisibility(View.GONE);
            mFlVideoContainer.removeAllViews();
            super.onHideCustomView();
        }
    }

    private void fullScreen() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            Log.i("ToVmp", "横屏");
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            Log.i("ToVmp", "竖屏");
        }
    }

    @SuppressLint("JavascriptInterface")
    public void setWebview(WebView webview) {
        WebSettings web = webview.getSettings();
        web.setDefaultTextEncodingName("utf-8");
        // 设置可以支持缩放
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
        // 启用硬件加速
        webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webview.setWebChromeClient(new MyWebChromeClient());
        // 自适应屏幕
        web.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
    }

    @JavascriptInterface
    public void play() {
        int netWorkStatus = ConstantUtil.getNetWorkStatus(ArticleActivity.this);
        switch (netWorkStatus) {
            case 1:
                showToast("当前是Wifi网络");
                break;
            case 2:
                showToast("当前是2G网络，请注意流量消耗");
                break;
            case 3:
                showToast("当前是3G网络，请注意流量消耗");
                break;
            case 4:
                showToast("当前是4G网络，请注意流量消耗");
                break;

        }
        Log.e("JavascriptInterface", "play: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.setUntying(this);
        if (mArWeb != null) {
            try {
                mArWeb.removeAllViews();
                mArWeb.clearHistory();
                mArWeb.clearCache(true);
                mArWeb.destroy();
            } catch (Throwable t) {

            }
            mArWeb = null;
        }
    }

    @OnClick({R.id.ar_back, R.id.ar_refresh, R.id.sc_chat, R.id.se_live, R.id.home_search, R.id.sc_hearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ar_back:
                finish();
                break;
            case R.id.ar_refresh:
                mScScroll.fullScroll(NestedScrollView.FOCUS_UP);
                mARefresh.startRefresh();
                mArWeb.reload();
                break;
            case R.id.sc_chat:
                if (chat) {
                    mScScroll.scrollTo(0, mScComment.getTop());
                } else {
                    mScScroll.fullScroll(NestedScrollView.FOCUS_UP);
                }
                break;
            case R.id.se_live:
                if (ConstantUtil.isSchoolClick()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", articleid);
                    if (mSeLive.isChecked()) {
                        map.put("type", "2");
                    } else {
                        map.put("type", "1");
                    }
                    presenter.onTheThumbsUp(ArticleActivity.this, map);
                    mSeLive.toggle();
                }
                break;
            case R.id.home_search:
                PopupWindow popupWindow = WindowUtils.ShowSex(ArticleActivity.this, R.layout.item_srt_popup, mHomeSearch);
                popupWindow.getContentView().findViewById(R.id.ar_clear).setOnClickListener(this);
                popupWindow.getContentView().findViewById(R.id.ar_release).setOnClickListener(this);
                text_conten = popupWindow.getContentView().findViewById(R.id.text_conten);
                ar_edit = popupWindow.getContentView().findViewById(R.id.ar_edit);
                ar_edit.setFocusable(true);
                ar_edit.setFocusableInTouchMode(true);
                ar_edit.requestFocus();
                ar_edit.addTextChangedListener(this);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showKeyboard(ar_edit);
                    }
                }, 500);
                break;
            case R.id.sc_hearch:
                PopupWindow viewContent = WindowUtils.ShowVirtual(ArticleActivity.this, R.layout.popup_school_share,
                        R.style.bottom_animation, 1);
                viewContent.getContentView().findViewById(R.id.sc_clear_bus).setOnClickListener(this);
                viewContent.getContentView().findViewById(R.id.sc_wx_share).setOnClickListener(this);
                viewContent.getContentView().findViewById(R.id.sc_moments_share).setOnClickListener(this);
                viewContent.getContentView().findViewById(R.id.sc_qq_share).setOnClickListener(this);
                viewContent.getContentView().findViewById(R.id.sc_qq_moments_share).setOnClickListener(this);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ar_clear:
                hiddenKeyboard();
                WindowUtils.dismissBrightness(ArticleActivity.this);
                break;
            case R.id.ar_release:
                Map<String, String> map = new HashMap<>();
                if (TextUtils.isEmpty(ar_edit.getText().toString()) || ar_edit.getText().toString().trim().isEmpty()) {
                    showToast("评论内容不能为空");
                    return;
                }
                if (ar_edit.getText().toString().length() <= 200) {
                    map.put("id", articleid);
                    map.put("comments", ar_edit.getText().toString());
                    presenter.onaddComment(ArticleActivity.this, map);
                    hiddenKeyboard();
                    WindowUtils.dismissBrightness(ArticleActivity.this);
                } else {
                    showToast("评论过长，最多200字");
                }
                break;
            case R.id.sc_clear_bus:
                WindowUtils.dismissBrightness(ArticleActivity.this);
                break;
            case R.id.sc_wx_share:
                if (TextUtils.isEmpty(articletitle)) {
                    articletitle = "商学院";
                }
                platform = ShareUtils.shareContent(Wechat.NAME, articletitle, aritcleimage, webUrl);
                platform.setPlatformActionListener(new SchoolActionListener());
                WindowUtils.dismissBrightness(ArticleActivity.this);
                break;
            case R.id.sc_moments_share:
                if (TextUtils.isEmpty(articletitle)) {
                    articletitle = "商学院";
                }
                platform = ShareUtils.shareContent(WechatMoments.NAME, articletitle, aritcleimage, webUrl);
                platform.setPlatformActionListener(new SchoolActionListener());
                WindowUtils.dismissBrightness(ArticleActivity.this);
                break;
            case R.id.sc_qq_share:
                if (TextUtils.isEmpty(articletitle)) {
                    articletitle = "商学院";
                }
                platform = ShareUtils.shareContent(QQ.NAME, articletitle, aritcleimage, webUrl);
                platform.setPlatformActionListener(new SchoolActionListener());
                WindowUtils.dismissBrightness(ArticleActivity.this);
                break;
            case R.id.sc_qq_moments_share:
                if (TextUtils.isEmpty(articletitle)) {
                    articletitle = "商学院";
                }
                platform = ShareUtils.shareContent(QZone.NAME, articletitle, aritcleimage, webUrl);
                platform.setPlatformActionListener(new SchoolActionListener());
                WindowUtils.dismissBrightness(ArticleActivity.this);
                break;
        }
    }

    private class SchoolActionListener implements PlatformActionListener {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            Map<String, String> map = new HashMap<>();
            map.put("id", articleid);
            presenter.onShareSumUp(ArticleActivity.this, map);
            Map<String, String> hasMap = new HashMap<>();
            hasMap.put("type", "2");
            hasMap.put("content",articleid);
            presenter.onCandySharing(ArticleActivity.this, hasMap);
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(Platform platform, int i) {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mArWeb.canGoBack()) {
            mArWeb.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

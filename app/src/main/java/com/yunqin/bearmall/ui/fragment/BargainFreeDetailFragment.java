package com.yunqin.bearmall.ui.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.BargainDetail;
import com.yunqin.bearmall.bean.ProductDetail;
import com.yunqin.bearmall.eventbus.BargainFreeEvent;
import com.yunqin.bearmall.eventbus.ProductMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class BargainFreeDetailFragment extends BaseFragment{
    private static final String PRODUCT_DETAIL = "/view/findProductDetailPage?product_id=";
    private BargainDetail bargainDetail;
    private long product_id;

    @BindView(R.id.product_web_view)
    WebView mWebView;

    @Override
    public int layoutId() {
        return R.layout.fragment_product_detail;
    }

    @Override
    public void init() {
        WebSettings webSettings = mWebView.getSettings();

        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("gbk");
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportMultipleWindows(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(BargainFreeEvent messageEvent) {
        Log.e("ProductDetailFragment", messageEvent.getMessage());
        String serverJsonData = messageEvent.getMessage();

        parseJsonData(serverJsonData);

    }


    private void parseJsonData(String serverJsonData) {
        if (serverJsonData != null && !serverJsonData.isEmpty()) {
            bargainDetail = new Gson().fromJson(serverJsonData, BargainDetail.class);
            BargainDetail.BargainProduct  bargainProduct = bargainDetail.getData().getBargainProduct();

            if(bargainProduct != null){
                product_id = bargainProduct.getProduct_id();

                mWebView.loadUrl(BuildConfig.BASE_URL+PRODUCT_DETAIL+product_id);
                mWebView.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(BuildConfig.BASE_URL+PRODUCT_DETAIL+product_id);
                        return true;
                    }
                });
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}

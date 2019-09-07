package com.newversions.tbk.utils;

import android.content.Context;
import android.content.res.Resources;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yunqin.bearmall.R;


/**
 * Created by admin on 2017/11/17.
 */

public class MyWebViewClient extends WebViewClient {


    private Context context;

    /**
     * 构造函数，提示加载完成
     *
     */
    public MyWebViewClient(Context context) {

        this.context = context;
    }

    /**
     * 隐藏加载提示框
     *
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        String js = getClearAdDivJs(context);
        view.loadUrl(js);

    }

    /**
     * 屏蔽webview 广告
     *
     * @param view
     * @param url
     * @return
     */
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        url = url.toLowerCase();
        if (!ADFilterTool.hasAd(context, url)) {
            return super.shouldInterceptRequest(view, url);//正常加载
        } else {
            return new WebResourceResponse(null,null,null);
        }
    }

    public static String getClearAdDivJs(Context context) {
        String js = "javascript:";
        Resources res = context.getResources();
        String[] adDivs = res.getStringArray(R.array.adBlockDiv);
        for (int i = 0; i < adDivs.length; i++) {

            js += "var adDiv" + i + "= document.getElementById('" + adDivs[i] + "');if(adDiv" + i + " != null)adDiv" + i + ".parentNode.removeChild(adDiv" + i + ");";
        }
        return js;
    }


}


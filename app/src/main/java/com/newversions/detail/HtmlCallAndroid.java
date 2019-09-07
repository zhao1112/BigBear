package com.newversions.detail;

import android.webkit.JavascriptInterface;

/**
 * Create By Master
 * On 2019/1/27 15:52
 */
public class HtmlCallAndroid {

    private HtmlFoundation htmlFoundation;

    public HtmlCallAndroid(Object object) {
        if (object instanceof HtmlFoundation) {
            htmlFoundation = (HtmlFoundation) object;
        }
    }

    @JavascriptInterface
    public void onResultHeight(int message) {
        if (htmlFoundation != null) {
            htmlFoundation.onResultHeight(message);
        }
    }


}

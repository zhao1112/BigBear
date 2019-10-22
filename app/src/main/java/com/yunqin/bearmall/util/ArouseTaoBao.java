package com.yunqin.bearmall.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.trade.biz.applink.adapter.AlibcFailModeType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.newversions.tbk.activity.GoodsDetailActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.util
 * @DATE 2019/10/22
 */
public class ArouseTaoBao {

    private Context mContext;

    public ArouseTaoBao(Context context) {
        this.mContext = context;

    }

    public void openTaoBao(String url) {
        AlibcShowParams alibcShowParams = new AlibcShowParams();
        alibcShowParams.setOpenType(OpenType.Native);
        alibcShowParams.setClientType("taobao");
        alibcShowParams.setBackUrl(url);
        alibcShowParams.setNativeOpenFailedMode(AlibcFailModeType.AlibcNativeFailModeJumpH5);

        AlibcTaokeParams taokeParams = new AlibcTaokeParams("", "", "");
        taokeParams.setPid("mm_446530152_629950029_109291250388");

        Map<String, String> trackParams = new HashMap<>();

        AlibcTrade.openByUrl((Activity) mContext, "", url, null, new WebViewClient(),
                new WebChromeClient(), alibcShowParams, taokeParams, trackParams, new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {

                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Log.i("onFailure", "code: " + i + "  meg: " + s);
                    }
                });
    }


    /**
     * 检测该包名所对应的应用是否存在
     *
     * @param packageName
     * @return
     */


    public  boolean checkPackage(String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            mContext.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}

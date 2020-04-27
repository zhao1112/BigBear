package com.bbcoupon.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.widget.Toast;


import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.util.PopUtil;

import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.util
 * @DATE 2020/4/22
 */
public class ShareUtils {

    private static ShareUtils shareUtil = null;
    public Context context;

    public static ShareUtils getInstance() {
        if (shareUtil == null) {
            synchronized (ShareUtils.class) {
                if (shareUtil == null) {
                    shareUtil = new ShareUtils();
                }
            }
        }
        return shareUtil;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    //分享
    public Platform shareContent(String platforms, String title, String content, String image) {
        Platform.ShareParams params = new Platform.ShareParams();
        params.setTitle(title);
        params.setText(content);
        params.setImageUrl(image);
        params.setShareType(Platform.SHARE_IMAGE);
        Platform platform = ShareSDK.getPlatform(platforms);
        platform.share(params);
        return platform;
    }

    //单图分享
    public Platform shareContent(String platforms, String image) {
        Platform.ShareParams params = new Platform.ShareParams();
        params.setImageUrl(image);
        params.setShareType(Platform.SHARE_IMAGE);
        Platform platform = ShareSDK.getPlatform(platforms);
        platform.share(params);
        return platform;
    }

    //单图分享
    public Platform shareContent(String platforms, Bitmap image) {
        Platform.ShareParams params = new Platform.ShareParams();
        params.setImageData(image);
        params.setShareType(Platform.SHARE_IMAGE);
        Platform platform = ShareSDK.getPlatform(platforms);
        platform.share(params);
        return platform;
    }

    //分享
    public Platform shareContent(String platforms, String title, String content, Bitmap image) {
        Platform.ShareParams params = new Platform.ShareParams();
        params.setTitle(title);
        params.setText(content);
        params.setImageData(image);
        params.setShareType(Platform.SHARE_IMAGE);
        Platform platform = ShareSDK.getPlatform(platforms);
        platform.share(params);
        return platform;
    }

    //判断用户是否安装微信
    public static boolean isWXClientAvailable(Context context) {
        final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
        api.registerApp(Constans.WX_APPID);  //将APP注册到微信
        if (api.isWXAppInstalled()) {
            return true;
        } else {
            return false;
        }
    }

    //判断用户是否安装QQ
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

}

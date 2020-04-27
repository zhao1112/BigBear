package com.bbcoupon.util;

import android.content.Context;
import android.graphics.Bitmap;


import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;

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

    //判断用户是否安装QQ

}

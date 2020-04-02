package com.yunqin.bearmall.util;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.mob.MobSDK;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;

import java.io.File;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ShareVideoUtils {


    public static void share(Context context,String videoUrl,String title,String subTitle,String imagePath,String filePath){

        Dialog shareDialog = new Dialog(context, R.style.ProductDialog);
        shareDialog.setCanceledOnTouchOutside(true);

        View view = LayoutInflater.from(context).inflate(R.layout.share_video_layout, null);
        shareDialog.setContentView(view);
        Window window = shareDialog.getWindow();
        shareDialog.show();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        view.findViewById(R.id.wx_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IWXAPI api = WXAPIFactory.createWXAPI(context, null);
                api.registerApp(Constans.WX_APPID);  //将APP注册到微信
                if (api.isWXAppInstalled()) {
                    shareVideo(Wechat.NAME,videoUrl,title,subTitle,imagePath);
//                    shareNormal(context,Wechat.NAME,title,subTitle,imagePath,filePath);
                    shareDialog.dismiss();
                } else {
                    Toast.makeText(context, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.pyq_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IWXAPI api = WXAPIFactory.createWXAPI(context, null);
                api.registerApp(Constans.WX_APPID);  //将APP注册到微信
                if (api.isWXAppInstalled()) {
//                    shareNormal(context,WechatMoments.NAME,title,subTitle,imagePath,filePath);
                    shareVideo(WechatMoments.NAME,videoUrl,title,subTitle,imagePath);
                    shareDialog.dismiss();
                } else {
                    Toast.makeText(context, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.qq_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ShareUtil.isQQClientAvailable(context)){
//                    shareNormal(context,QQ.NAME,title,subTitle,imagePath,filePath);
                    shareVideo(QQ.NAME,videoUrl,title,subTitle,imagePath);
                    shareDialog.dismiss();
                }else {
                    Toast.makeText(context, "请先安装QQ客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.canle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.dismiss();
            }
        });

    }

    private static void shareVideo(String plat,String videoUrl,String title,String subTitle,String imageUrl){
        Log.e("shareVideo", videoUrl );
        Platform platform = ShareSDK.getPlatform(plat);
        Platform.ShareParams shareParams = new  Platform.ShareParams();
        shareParams.setText(subTitle);
        shareParams.setTitle(title);
        shareParams.setUrl(videoUrl);
        shareParams.setImageUrl(imageUrl);
        shareParams.setScence(0);
        shareParams.setShareType(Platform.SHARE_VIDEO);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        platform.share(shareParams);
    }


    private static void shareNormal(Context context,String name,String title,String subTitle,String imageUrl,String fileUrl){
        Platform platform = ShareSDK.getPlatform(name);
        Platform.ShareParams shareParams = new  Platform.ShareParams();
        shareParams.setText(subTitle);
        shareParams.setTitle(title);
        shareParams.setFilePath(fileUrl);
        shareParams.setUrl(imageUrl);
//        shareParams.setImagePath(imageUrl);
        shareParams.setShareType(Platform.SHARE_FILE);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        platform.share(shareParams);
    }


}

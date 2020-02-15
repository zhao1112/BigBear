package com.yunqin.bearmall.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.bean.ShareBean;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * @author AYWang
 * @create 2018/8/22
 * @Describe
 */
public class CShareUtil {

    private static Context mContext;
    private static ShareBean.DataBean shareBean;


    public static void Share(Context context, ShareBean.DataBean shareBeanN) {
        mContext = context;
        shareBean = shareBeanN;

        permissionsChecker = new PermissionsChecker(context);

        final Dialog shareDialog = new Dialog(context, R.style.ProductDialog);
        shareDialog.setCanceledOnTouchOutside(true);
        creatShowDialog(context, shareDialog);
    }

    private static ImageView sharePictureView;


    public static void creatShowDialog(final Context context, final Dialog shareDialog) {
        View view = LayoutInflater.from(context).inflate(R.layout.c_share_dialog_layout_cop, null);
        shareDialog.setContentView(view);
        Window window = shareDialog.getWindow();
        shareDialog.show();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


        sharePictureView = view.findViewById(R.id.shard_top_image);
        Glide.with(context).load(shareBean.getImgUrl()).into(sharePictureView);

        view.findViewById(R.id.wx_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
                api.registerApp(Constans.WX_APPID);  //将APP注册到微信
                if (api.isWXAppInstalled()) {
                    fenXiangImg(Wechat.NAME);
                    shareDialog.dismiss();
                } else {
                    Toast.makeText(context, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.pyq_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final IWXAPI api = WXAPIFactory.createWXAPI(mContext, null);
                api.registerApp(Constans.WX_APPID);  //将APP注册到微信
                if (api.isWXAppInstalled()) {
                    fenXiangImg(WechatMoments.NAME);
                    shareDialog.dismiss();
                } else {
                    Toast.makeText(mContext, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.qq_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQQClientAvailable(mContext)) {
                    qqShare();
                    shareDialog.dismiss();
                } else {
                    Toast.makeText(context, "请先安装QQ客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });


        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 保存到手机
                if (permissionsChecker.lacksPermissions(PERMISSIONS)) {
                    showMissingPermissionDialog();
                } else {
                    try {
                        Bitmap bitmap = ImageLoadUt.captureView(sharePictureView);
                        if (saveImageToGallery(mContext, bitmap)) {
                            Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "保存失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Throwable throwable) {
                        Toast.makeText(mContext, "保存失败", Toast.LENGTH_SHORT).show();
                    }
                    shareDialog.dismiss();
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

    private static final String[] PERMISSIONS = new String[]{WRITE_EXTERNAL_STORAGE};
    private static PermissionsChecker permissionsChecker;


    private static void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("帮助");
        builder.setMessage(R.string.string_help_text);
        // 拒绝, 退出应用
        builder.setNegativeButton("关闭", null);
        builder.setPositiveButton("设置", (dialog, which) -> startAppSettings());
        builder.show();
    }

    // 启动应用的设置
    private static void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + mContext.getPackageName()));
        mContext.startActivity(intent);
    }


    //普通的分享
    public static void shareNormal(String platform, Context context) {

        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(shareBean.getTitle());
        oks.setTitleUrl(shareBean.getShareUrl());
        oks.setText(shareBean.getSpeciality());
        oks.setImageUrl(shareBean.getImgUrl());
        oks.setUrl(shareBean.getShareUrl());
        oks.setComment("说点什么吧");
        oks.setSite("大熊酷朋");
        oks.setSiteUrl(shareBean.getShareUrl());
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.e("Share", "普通分享成功");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.e("Share", "普通分享失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.e("Share", "普通分享取消");
            }
        });
        if (platform != null) {
            oks.setPlatform(platform);
        }
        oks.show(context);
    }


    private static synchronized void qqShare() {

        if (permissionsChecker.lacksPermissions(PERMISSIONS)) {
            showMissingPermissionDialog();
            return;
        }

        QQ.ShareParams shareParams = new QQ.ShareParams();
        shareParams.setImageUrl(shareBean.getImgUrl());
        Platform qqPlatform = ShareSDK.getPlatform(QQ.NAME);
        if (!qqPlatform.isClientValid()) {
            //客户端不可用，
            return;
        }
        // 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        qqPlatform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.d("QQ-Share", "onComplete ---->  分享成功");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.d("QQ-Share", "onError ---->  分享失败" + throwable.getStackTrace().toString());
                Log.d("QQ-Share", "onError ---->  分享失败" + throwable.getMessage());
                throwable.getMessage();
                throwable.printStackTrace();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.d("QQ-Share", "onCancel ---->  分享取消");
            }
        });
        // 执行图文分享
        qqPlatform.share(shareParams);

    }


    private static void fenXiangImg(String platFromName) {
        Platform platform = ShareSDK.getPlatform(platFromName);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setImageUrl(shareBean.getImgUrl());
//        shareParams.setImageUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548761334961&di=411c3a36b53f5639ebecb547d00aa8a6&imgtype=0&src=http%3A%2F%2Fcimage.tianjimedia.com%2FuploadImages%2F2016%2F11%2F20161117095009377001.jpg");
        shareParams.setShareType(Platform.SHARE_IMAGE);
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


    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Img";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}

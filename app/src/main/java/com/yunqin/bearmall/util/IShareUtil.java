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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.bean.ShareCallBackBean;
import com.yunqin.bearmall.widget.PriceView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * @author AYWang
 * @create 2018/8/22
 * @Describe
 */
public class IShareUtil {

    private static Context mContext;
    private static ShareBean.DataBean shareBean;
    private static PermissionsChecker permissionsChecker;


    private static LinearLayout top_img_t;
    private static LinearLayout share_bottom_s;
    private static TextView p_name;
    private static PriceView price_view;


    public static void Share(Context context, ShareBean.DataBean shareBeanN) {
        mContext = context;
        shareBean = shareBeanN;
        permissionsChecker = new PermissionsChecker(context);
        final Dialog shareDialog = new Dialog(context, R.style.ProductDialog);
        shareDialog.setCanceledOnTouchOutside(true);
        creatShowDialog(context, shareDialog);
    }

    private static final String[] PERMISSIONS = new String[]{WRITE_EXTERNAL_STORAGE};

    public static void creatShowDialog(final Context context, final Dialog shareDialog) {
        View view = LayoutInflater.from(context).inflate(R.layout.share_dialog_layout_cop, null);
        shareDialog.setContentView(view);
        Window window = shareDialog.getWindow();
        shareDialog.show();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        // TODO 图片保存到本地 18888888888
        top_img_t = view.findViewById(R.id.top_img_t);

        p_name = view.findViewById(R.id.p_name);
        share_bottom_s = view.findViewById(R.id.share_bottom_s);
        price_view = view.findViewById(R.id.price_view);


        ImageView imageView1 = view.findViewById(R.id.shard_top_image);
        ImageView imageView2 = view.findViewById(R.id.share_qr_code);
        loadImg(imageView1, imageView2);

        view.findViewById(R.id.save).setVisibility(View.VISIBLE);
        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 请求权限，保存本地
                if (permissionsChecker.lacksPermissions(PERMISSIONS)) {
                    showMissingPermissionDialog();
                } else {

                    if (imgUrl.equals("")) {
                        Toast.makeText(mContext, "图片加载中,请稍后再试!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        Bitmap bitmap = ImageLoadUt.captureView(top_img_t);
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
        view.findViewById(R.id.wx_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
                api.registerApp(Constans.WX_APPID);  //将APP注册到微信
                if (api.isWXAppInstalled()) {
                    shareMiniProgram();
                    shareDialog.dismiss();
                } else {
                    Toast.makeText(context, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.qq_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQQClientAvailable(mContext)) {
                    shareNormal(QQ.NAME, context);
                    shareDialog.dismiss();
                } else {
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

    private static String imgUrl = "";

    private static void loadImg(ImageView imageView,ImageView imageView2) {

        Map<String, String> map = new HashMap<>();
        map.put("product_id", String.valueOf(shareBean.getSource_id()));

        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).getProductQrInfoForShare(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.optInt("code") == 1) {
                        JSONObject json = jsonObject.optJSONObject("data");
                        imageView.setVisibility(View.VISIBLE);
                        share_bottom_s.setVisibility(View.VISIBLE);
                        imgUrl = json.optString("productImage");
                        String qrUrl = json.optString("wxQrUrl");

                        p_name.setText(json.optString("productName") + "");

                        price_view.setPrice(json.optString("membershipPrice"),json.optString("price"),json.optBoolean("isSurportMsp"),0,0,"","");


                        Glide.with(mContext).load(imgUrl).into(imageView);
                        Glide.with(mContext).load(qrUrl).into(imageView2);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNotNetWork() {
                Glide.with(mContext).load("").into(imageView);
            }

            @Override
            public void onFail(Throwable e) {
                Glide.with(mContext).load("").into(imageView);
            }
        });
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
        oks.setSite("大熊商城");
        oks.setSiteUrl(shareBean.getShareUrl());
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.e("Share", "普通分享成功");
                shareCallback(shareBean);
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

    //分享小程序
    public static void shareMiniProgram() {
        Platform platform = ShareSDK.getPlatform(Wechat.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setText(shareBean.getSpeciality());
        shareParams.setTitle(shareBean.getTitle());
        shareParams.setUrl(shareBean.getShareUrl());

        shareParams.setAuthor("大熊商城");
        shareParams.setWxUserName(shareBean.getWxProgramId());
        shareParams.setWxPath(shareBean.getWxProgramPageUrl());
        shareParams.setImageUrl(shareBean.getWxProgramPageImageUrl());
        shareParams.setShareType(Platform.SHARE_WXMINIPROGRAM);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.e("Share", "分享小程序成功");
                shareCallback(shareBean);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.e("Share", "分享小程序失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.e("Share", "分享小程序取消");
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


    public static void shareCallback(ShareBean.DataBean shareBean) {
        Map map = new HashMap();
        map.put("source_id", shareBean.getSource_id() + "");
        map.put("type", shareBean.getType() + "");
        map.put("isShared", 1 + "");
        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).callBackForShare(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                //todo 熊波，分享结果回调
                try {
                    ShareCallBackBean shareCallBackBean = new Gson().fromJson(data, ShareCallBackBean.class);
                    if (shareCallBackBean.getData().getIsReward() == 1) {
                        ToastGetBC.getInstence().show(mContext, "+" + shareCallBackBean.getData().getValue() + "BC");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }


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

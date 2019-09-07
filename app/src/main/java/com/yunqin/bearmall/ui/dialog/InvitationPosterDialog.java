package com.yunqin.bearmall.ui.dialog;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.mob.MobSDK;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.utils.BitmapHelper;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseDialog;
import com.yunqin.bearmall.util.PermissionsChecker;
import com.yunqin.bearmall.widget.Highlight.HighlightLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
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
 * @create 2018/7/28
 * @Describe
 */
public class InvitationPosterDialog extends BaseDialog implements View.OnClickListener {

    private static Context mContext;

    private static final int SAVE_SUCCESS = 0;//保存图片成功
    private static final int SAVE_FAILURE = 1;//保存图片失败
    private static final int SAVE_BEGIN = 2;//开始保存图片
    final HighlightLinearLayout canle;
    final LinearLayout save, qq_share, sina_share, pyq_share, wx_share;
    static ImageView image;

    private static String ImageUrl = "";

    static final String[] PERMISSIONS = new String[]{WRITE_EXTERNAL_STORAGE};

    private PermissionsChecker permissionsChecker;
    private static final long ALPHA_DURATION = 2000;
    public static final int APPLY_PERMISSION = 1;

    private boolean isFinishLoading = false;


    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//window.requestWindowFeature(Window.FEATURE_NO_TITLE); 用在activity中，去标题
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

    }

    public InvitationPosterDialog(@NonNull Context context) {
        super(context, R.style.ProductDialog);
        mContext = context;
        permissionsChecker = new PermissionsChecker(mContext);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_invitation_poster, null);
        ButterKnife.bind(view);
        canle = view.findViewById(R.id.canle);
        save = view.findViewById(R.id.save);
        qq_share = view.findViewById(R.id.qq_share);
        sina_share = view.findViewById(R.id.sina_share);
        pyq_share = view.findViewById(R.id.pyq_share);
        wx_share = view.findViewById(R.id.wx_share);
        image = view.findViewById(R.id.image);
        canle.setOnClickListener(this);
        save.setOnClickListener(this);
        qq_share.setOnClickListener(this);
        sina_share.setOnClickListener(this);
        pyq_share.setOnClickListener(this);
        wx_share.setOnClickListener(this);
        image.setOnClickListener(this);

        getPosterData(image);

        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        show();

        WindowManager m = ((Activity) context).getWindowManager();
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);

//        download();
    }

    @Override
    public void onClick(View v) {
        if (ImageUrl.equals("")) {
            Toast.makeText(mContext, "当前网络较差，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()) {
            case R.id.canle:
                dismiss();
                break;
            case R.id.save:
                beginAlpha();
                break;
            case R.id.qq_share:
                if(isQQClientAvailable(mContext)){
                    shareNormal(QQ.NAME);
                    dismiss();
                }else {
                    Toast.makeText(mContext, "请先安装QQ客户端", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sina_share:
                break;
            case R.id.pyq_share:
                if(!isFinishLoading){
                    Toast.makeText(mContext,"图片还没加载完",Toast.LENGTH_SHORT).show();
                    return;
                }
                final IWXAPI api = WXAPIFactory.createWXAPI(mContext, null);
                api.registerApp(Constans.WX_APPID);  //将APP注册到微信
                if (api.isWXAppInstalled()) {
                    shareNormal(WechatMoments.NAME);
                    dismiss();
                }else {
                    Toast.makeText(mContext, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.wx_share:
                if(!isFinishLoading){
                    Toast.makeText(mContext,"图片还没加载完",Toast.LENGTH_SHORT).show();
                    return;
                }
                final IWXAPI api1 = WXAPIFactory.createWXAPI(mContext, null);
                api1.registerApp(Constans.WX_APPID);  //将APP注册到微信
                if (api1.isWXAppInstalled()) {
                    shareNormal(Wechat.NAME);
                    dismiss();
                }else {
                    Toast.makeText(mContext, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.image:
                dismiss();
                break;
        }
    }

    private static RequestOptions options;
    public static RequestOptions getOptions(int resouseID) {
        if (options == null) {
            options = new RequestOptions()
                    //加载成功之前占位图
                    .placeholder(resouseID)
                    //加载错误之后的错误图
                    .error(resouseID)
                    //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                    .centerCrop()
                    //跳过内存缓存
                    .skipMemoryCache(true)
                    //缓存所有版本的图像
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        return options;
    }

    private void getPosterData(ImageView imageView) {

        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).createInviteFriendsImage(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                try {
                    Glide.with(mContext).setDefaultRequestOptions(getOptions(R.drawable.default_product)).load(new JSONObject(data).optString("data"))
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    isFinishLoading = true;
                                    return false;
                                }
                            }).into(imageView);
                    ImageUrl = new JSONObject(data).optString("data");
                } catch (JSONException e) {
                    throw e;
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                //打开dialog 如果网络错误或者请求错误 延时两秒关闭dialog
                new Handler().postDelayed(() -> dismiss(), 1000);
            }
        });
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SAVE_BEGIN:
                    Toast.makeText(mContext, "开始保存图片", Toast.LENGTH_SHORT).show();
                    break;
                case SAVE_SUCCESS:
                    Toast.makeText(mContext, "图片保存成功,请到相册查找", Toast.LENGTH_SHORT).show();
                    break;
                case SAVE_FAILURE:
                    Toast.makeText(mContext, "图片保存失败,请稍后再试", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    /**
     * 保存二维码到本地相册
     */
    private void saveImageToPhotos(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "InvitationPoster");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            mHandler.obtainMessage(SAVE_FAILURE).sendToTarget();
            return;
        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        mHandler.obtainMessage(SAVE_SUCCESS).sendToTarget();
    }

    /**
     * 将URL转化成bitmap形式
     *
     * @param url
     * @return bitmap type
     */
    public final void returnBitMap(String url) {
        Glide.with(mContext).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                //保存图片必须在子线程中操作，是耗时操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        saveImageToPhotos(mContext, resource);
                    }
                }).start();
            }
        });
    }


    private void beginAlpha() {

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(ALPHA_DURATION);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //没有考虑用户永久拒绝的情况
                if (permissionsChecker.lacksPermissions(PERMISSIONS)) {
                    showMissingPermissionDialog();
                } else {
                    mHandler.obtainMessage(SAVE_BEGIN).sendToTarget();
                    returnBitMap(ImageUrl);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animatorSet.start();
    }

    // 显示缺失权限提示
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("帮助");
        builder.setMessage(R.string.string_help_text);

        // 拒绝, 退出应用
        builder.setNegativeButton("关闭", (dialog, which) -> {
            Toast.makeText(mContext, "缺少必要权限，无法保存", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        builder.setPositiveButton("设置", (dialog, which) -> startAppSettings());

        builder.show();
    }

    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + mContext.getPackageName()));
        mContext.startActivity(intent);
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


    //普通的分享
    public static void shareNormal(String  name) {
        Platform platform = ShareSDK.getPlatform(name);
        Platform.ShareParams shareParams = new  Platform.ShareParams();
//        shareParams.setImagePath(imagePath);
        shareParams.setImageUrl(ImageUrl);
        shareParams.setShareType(Platform.SHARE_IMAGE);
        platform.share(shareParams);
    }


//    public static String testImage;
//    private  static  String imagePath ;
//    private String imageUrl;
//    private void download(){
//        new Thread() {
//            public void run() {
//                imageUrl ="https://shopxxbbc.oss-cn-beijing.aliyuncs.com/upload/image/201808/11057_source.png";
//                try {
//                    testImage = BitmapHelper.downloadBitmap(MobSDK.getContext(), imageUrl);
//                    imagePath = testImage;
//                } catch (Throwable t) {
//                    t.printStackTrace();
//                    testImage = null;
//                }
//            }
//        }.start();
//    }


}

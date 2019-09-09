package com.yunqin.bearmall.ui.dialog;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.util.PermissionsChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.dialog
 * @DATE 2019/8/30
 */
public class NewInvitationDialog implements View.OnClickListener {

    public Context mContext;
    public static String mIageUrl;

    private static final int SAVE_SUCCESS = 0;//保存图片成功
    private static final int SAVE_FAILURE = 1;//保存图片失败
    private static final int SAVE_BEGIN = 2;//开始保存图片

    static final String[] PERMISSIONS = new String[]{WRITE_EXTERNAL_STORAGE};

    private PermissionsChecker permissionsChecker;
    private static final long ALPHA_DURATION = 2000;
    public static final int APPLY_PERMISSION = 1;
    private PopupWindow mPopupWindow;


    public NewInvitationDialog(Context context, String imageUrl) {
        this.mContext = context;
        this.mIageUrl = imageUrl;
        permissionsChecker = new PermissionsChecker(mContext);
    }

    public void showShareDialog(setLiangDu setLiangDu) {
        mPopupWindow = new PopupWindow(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_share, null);
        mPopupWindow.setContentView(view);
        mPopupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置背景图片， 必须设置，不然动画没作用
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);

        // 设置点击popupwindow外屏幕其它地方消失
        mPopupWindow.setOutsideTouchable(true);

        LinearLayout share_wx = view.findViewById(R.id.share_wx);
        LinearLayout share_pyq = view.findViewById(R.id.share_pyq);
        LinearLayout share_qq = view.findViewById(R.id.share_qq);
        LinearLayout share_xz = view.findViewById(R.id.share_xz);
        LinearLayout share_view = view.findViewById(R.id.share_view);
        share_view.post(new Runnable() {
            @Override
            public void run() {
                int width = share_view.getWidth();
                int height = width * 146 / 360;
                ViewGroup.LayoutParams layoutParams = share_view.getLayoutParams();
                layoutParams.width = width;
                layoutParams.height = height;
                share_view.setLayoutParams(layoutParams);
            }
        });

        share_wx.setOnClickListener(this);
        share_pyq.setOnClickListener(this);
        share_qq.setOnClickListener(this);
        share_xz.setOnClickListener(this);


        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setLiangDu.lightoff();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (mIageUrl.equals("")) {
            Toast.makeText(mContext, "当前网络较差，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()) {
            case R.id.share_wx:
                if (mIageUrl == null) {
                    Toast.makeText(mContext, "图片还没加载完", Toast.LENGTH_SHORT).show();
                    return;
                }
                final IWXAPI api1 = WXAPIFactory.createWXAPI(mContext, null);
                api1.registerApp(Constans.WX_APPID);  //将APP注册到微信
                if (api1.isWXAppInstalled()) {
                    shareNormal(Wechat.NAME);
                    mPopupWindow.dismiss();
                } else {
                    Toast.makeText(mContext, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.share_pyq:
                if (mIageUrl == null) {
                    Toast.makeText(mContext, "图片还没加载完", Toast.LENGTH_SHORT).show();
                    return;
                }
                final IWXAPI api = WXAPIFactory.createWXAPI(mContext, null);
                api.registerApp(Constans.WX_APPID);  //将APP注册到微信
                if (api.isWXAppInstalled()) {
                    shareNormal(WechatMoments.NAME);
                    mPopupWindow.dismiss();
                } else {
                    Toast.makeText(mContext, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.share_qq:
                if (isQQClientAvailable(mContext)) {
                    shareNormal(QQ.NAME);
                    mPopupWindow.dismiss();
                } else {
                    Toast.makeText(mContext, "请先安装QQ客户端", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.share_xz:
                beginAlpha();
                break;
        }
    }

    public interface setLiangDu {
        void lightoff();
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
    public static void shareNormal(String name) {//name 分享到那个平台
        Platform platform = ShareSDK.getPlatform(name);//获取平台对象
        Platform.ShareParams shareParams = new Platform.ShareParams();//分享的参数
        //shareParams.setImagePath(imagePath);
        shareParams.setImageUrl(mIageUrl);//图片的地址
        shareParams.setShareType(Platform.SHARE_IMAGE);//分享类型     // 设置成分享网页
        platform.share(shareParams);
    }

    //保存
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
                    returnBitMap(mIageUrl);
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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SAVE_BEGIN:
                    Toast.makeText(mContext, "开始保存图片", Toast.LENGTH_SHORT).show();
                    break;
                case SAVE_SUCCESS:
                    Toast.makeText(mContext, "图片保存成功,请到相册查找", Toast.LENGTH_SHORT).show();
                    mPopupWindow.dismiss();
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


    // 显示缺失权限提示
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("帮助");
        builder.setMessage(R.string.string_help_text);

        // 拒绝, 退出应用
        builder.setNegativeButton("关闭", (dialog, which) -> {
            Toast.makeText(mContext, "缺少必要权限，无法保存", Toast.LENGTH_SHORT).show();
            mPopupWindow.dismiss();
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


}

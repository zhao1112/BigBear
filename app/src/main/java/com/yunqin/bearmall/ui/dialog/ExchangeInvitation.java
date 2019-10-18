package com.yunqin.bearmall.ui.dialog;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;

import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;


/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.dialog
 * @DATE 2019/10/18
 */
public class ExchangeInvitation implements View.OnClickListener {

    private Context context;
    private String exchangeUrl;
    private PopupWindow mPopupWindow;

    public ExchangeInvitation(Context context, String exchangeUrl) {
        this.context = context;
        this.exchangeUrl = exchangeUrl;
    }

    public void showShareDialog(setLiangDu setLiangDu) {
        mPopupWindow = new PopupWindow(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_exchangeinvitation, null);
        mPopupWindow.setContentView(view);
        mPopupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置背景图片， 必须设置，不然动画没作用
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        // 设置点击popupwindow外屏幕其它地方消失
        mPopupWindow.setFocusable(false);
        mPopupWindow.setOutsideTouchable(false);

        LinearLayout ex_wx = view.findViewById(R.id.ex_wx);
        LinearLayout ex_pyq = view.findViewById(R.id.ex_pyq);
        LinearLayout ex_qq = view.findViewById(R.id.ex_qq);
        //   LinearLayout ex_xz = view.findViewById(R.id.ex_xz);
        TextView ex_back = view.findViewById(R.id.ex_back);
        ex_wx.setOnClickListener(this);
        ex_pyq.setOnClickListener(this);
        ex_qq.setOnClickListener(this);
        //  ex_xz.setOnClickListener(this);
        ex_back.setOnClickListener(this);
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
        switch (view.getId()) {
            case R.id.ex_back:
                mPopupWindow.dismiss();
                break;
            case R.id.ex_qq:
                if (exchangeUrl == null) {
                    Toast.makeText(context, "分享错误，请重新尝试", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isQQClientAvailable(context)) {
                    showShare(QQ.NAME);
                    mPopupWindow.dismiss();
                } else {
                    Toast.makeText(context, "请先安装QQ客户端", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ex_pyq:
                if (exchangeUrl == null) {
                    Toast.makeText(context, "分享错误，请重新尝试", Toast.LENGTH_SHORT).show();
                    return;
                }
                final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
                api.registerApp(Constans.WX_APPID);  //将APP注册到微信
                if (api.isWXAppInstalled()) {
                    showShare(WechatMoments.NAME);
                    mPopupWindow.dismiss();
                } else {
                    Toast.makeText(context, "分享错误，请重新尝试", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ex_wx:
                if (exchangeUrl == null) {
                    Toast.makeText(context, "分享错误，请重新尝试", Toast.LENGTH_SHORT).show();
                    return;
                }
                final IWXAPI api1 = WXAPIFactory.createWXAPI(context, null);
                api1.registerApp(Constans.WX_APPID);  //将APP注册到微信
                if (api1.isWXAppInstalled()) {
                    showShare(Wechat.NAME);
                    mPopupWindow.dismiss();
                } else {
                    Toast.makeText(context, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    public interface setLiangDu {
        void lightoff();
    }

    private void showShare(String platform) {
        Platform platforms = ShareSDK.getPlatform(platform);//获取平台对象
        Platform.ShareParams shareParams = new Platform.ShareParams();//分享的参数
        shareParams.setTitle("每天都送免单商品！快跟我一起来领吧！");
        shareParams.setTitleUrl(exchangeUrl);
        shareParams.setText("天天抢免单！手慢无！");
        shareParams.setImageData(getBitmap());
        shareParams.setUrl(exchangeUrl);
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        platforms.share(shareParams);
    }

    public Bitmap getBitmap() {
        Bitmap bitmap = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.ad_bg_top_)).getBitmap();
        return bitmap;
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

}

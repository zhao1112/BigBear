package com.yunqin.bearmall.util;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.BargainRecordBean;
import com.yunqin.bearmall.bean.BargainShareDetailBean;
import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.bean.ShareCallBackBean;
import com.yunqin.bearmall.inter.ShareCallBack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * @author AYWang
 * @create 2018/8/22
 * @Describe
 */
public class BargainShareUtil {

    private static Context mContext;
//    private static String title;//分享的标题
//    private static String shareUrl;//分享链接 分享出去后打开的链接
//    private static String speciality;//分享的内容描述
//    private static String imgUrl;//分享的图标
//
//    private static String wxProgramId;//小程序ID  一般写死
//    private static String wxProgramPageUrl;//小程序页面路径  一般写死
//    private static String wxProgramPageImageUrl;//小程序页面图片  小于128K

    private static ShareBean.DataBean shareBean;
    private static Dialog shareDialog;



    public static void Share(Context context, ShareBean.DataBean shareBeanN, BargainShareDetailBean bargainShareDetailBean, ShareCallBack shareCallBack){
        mContext = context;
        shareBean = shareBeanN;
        shareDialog = new Dialog(context, R.style.ProductDialog);
        shareDialog.setCanceledOnTouchOutside(true);
        creatShowDialog(context,bargainShareDetailBean, shareDialog,shareCallBack);
    }


    public static  void creatShowDialog(final Context context,BargainShareDetailBean bargainShareDetailBean, final Dialog shareDialog,ShareCallBack shareCallBack){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_bargain_share, null);

        TextView tv_title = view.findViewById(R.id.dialog_share_title_tv);
        TextView bargain_tv = view.findViewById(R.id.dialog_share_bargain_tv);
        LinearLayout first_share_layout = view.findViewById(R.id.dialog_share_first_share_layout);
        TextView first_share_tv = view.findViewById(R.id.dialog_share_first_share_tv);
        TextView total_share_tv = view.findViewById(R.id.dialog_share_total_share_tv);


        tv_title.setText("邀请好友帮砍价");


        shareDialog.setContentView(view);
        Window window = shareDialog.getWindow();
        shareDialog.show();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);



/**

        if(bargainShareDetailBean.getData().getBargainCount() == 1){//系统第一刀
            tv_title.setText("邀请好友帮砍价");
            bargain_tv.setText("已砍大熊零售价¥"+bargainShareDetailBean.getData().getBargainedDetails().getBargainedAmount()+",分享成功后，可以多砍一刀哦");
            first_share_layout.setVisibility(View.GONE);
        }else if(bargainShareDetailBean.getData().getBargainCount() == 2){//首次分享，第二刀
            tv_title.setText("继续分享两个群聊");
            bargain_tv.setText("首次分享成功，多砍了¥"+bargainShareDetailBean.getData().getBargainedDetails().getBargainedAmount());
            first_share_layout.setVisibility(View.VISIBLE);
            first_share_tv.setText(bargainShareDetailBean.getData().getBargainedDetails().getShareWxCount()+"");
            total_share_tv.setText("/"+bargainShareDetailBean.getData().getBargainedDetails().getShareWxNeedCount());
        }else if(bargainShareDetailBean.getData().getBargainCount() == 3){
            tv_title.setText("砍到"+bargainShareDetailBean.getData().getBargainedDetails().getCriticalRatio()+"，获得多砍 ¥"+bargainShareDetailBean.getData().getBargainedDetails().getBargainMoreAmount() +"奖励");
            bargain_tv.setText("成功分享2个群聊，多砍了¥"+bargainShareDetailBean.getData().getBargainedDetails().getBargainedAmount());
            first_share_layout.setVisibility(View.GONE);
        }else if(bargainShareDetailBean.getData().getBargainCount() == 4){
            tv_title.setText("继续分享，邀请更多好友帮砍");
            bargain_tv.setText("砍到"+bargainShareDetailBean.getData().getBargainedDetails().getBargainedRatio()+"，获得多砍 ¥"+bargainShareDetailBean.getData().getBargainedDetails().getBargainedAmount() +"奖励");
            first_share_layout.setVisibility(View.GONE);
        }



      */





        view.findViewById(R.id.dialog_share_wechat_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("微信分享","微信分享");
                final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
                api.registerApp(Constans.WX_APPID);  //将APP注册到微信
                if (api.isWXAppInstalled()) {
                    shareMiniProgram(shareCallBack);
//                    shareDialog.dismiss();
                } else {
                    Toast.makeText(context, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.dialog_share_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isQQClientAvailable(mContext)){
                    shareNormal(QQ.NAME, context,shareCallBack);
//                    shareDialog.dismiss();
                }else {
                    Toast.makeText(context, "请先安装QQ客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.dialog_share_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.dismiss();
            }
        });
    }


    //普通的分享
    public static void shareNormal(String platform,Context context,ShareCallBack shareCallBack) {

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
                Log.e("Share","普通分享成功");
                shareDialog.dismiss();
                shareCallBack.onComplete();

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                shareCallBack.onError();
                Log.e("Share","普通分享失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                shareCallBack.onCancel();
                Log.e("Share","普通分享取消");
            }
        });
        if (platform != null) {
            oks.setPlatform(platform);
        }
        oks.show(context);
    }

    //分享小程序
    public static void shareMiniProgram(ShareCallBack shareCallBack){
        Platform platform = ShareSDK.getPlatform(Wechat.NAME);
        Platform.ShareParams shareParams = new  Platform.ShareParams();
        shareParams.setText(shareBean.getSpeciality());
        shareParams.setTitle(shareBean.getTitle());
        shareParams.setUrl(shareBean.getShareUrl());
        shareParams.setAuthor("大熊酷朋");
        shareParams.setWxUserName(shareBean.getWxProgramId());
        shareParams.setWxPath(shareBean.getWxProgramPageUrl());
        shareParams.setImageUrl(shareBean.getWxProgramPageImageUrl());
        shareParams.setShareType(Platform.SHARE_WXMINIPROGRAM);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.e("Share","分享小程序成功");
                shareDialog.dismiss();
                shareCallBack.onComplete();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                shareCallBack.onError();
                Log.e("Share","分享小程序失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                shareCallBack.onCancel();
                Log.e("Share","分享小程序取消");
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


//    public static void  shareCallback(ShareBean.DataBean shareBean){
//        Map map = new HashMap();
//        map.put("source_id",shareBean.getSource_id()+"");
//        map.put("type",shareBean.getType()+"");
//        map.put("isShared",1+"");
//        RetrofitApi.request(mContext, RetrofitApi.createApi(Api.class).callBackForShare(map), new RetrofitApi.IResponseListener() {
//            @Override
//            public void onSuccess(String data) {
//                try {
//                    ShareCallBackBean shareCallBackBean = new Gson().fromJson(data,ShareCallBackBean.class);
//                    if(shareCallBackBean.getData().getIsReward() == 1){
//                        ToastGetBC.getInstence().show(mContext, "+" + shareCallBackBean.getData().getValue()+"BC");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onNotNetWork() {
//
//            }
//
//            @Override
//            public void onFail(Throwable e) {
//
//            }
//        });
//    }




}

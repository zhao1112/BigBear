package com.newversions.tbk.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bbcoupon.ui.activity.CustomGoodesActivity;
import com.bbcoupon.ui.activity.MeetingplaceActivity;
import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.WebActivity;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.bean.NewTBHome;
import com.yunqin.bearmall.eventbus.ChangeFragment;
import com.yunqin.bearmall.ui.activity.ChargeActivity;
import com.yunqin.bearmall.ui.activity.LimitedActivity;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.NewWebViewActivity;
import com.yunqin.bearmall.ui.activity.SellwellActivity;
import com.yunqin.bearmall.util.ArouseTaoBao;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.util.ConstUtils;
import com.yunqin.bearmall.util.ConstantScUtil;

import org.greenrobot.eventbus.EventBus;

public class TopBarClicker {
    public static void topBarClick(Activity activity, NewTBHome.ClassificationBean bean) {
        // TODO: 2019/7/15 0015 根据类型执行操作，快速导航
        Log.i("topBarClick", bean.getSort() + "---" + bean.getUrl());
        Bundle bundle = new Bundle();
        ArouseTaoBao arouseTaoBao = new ArouseTaoBao(activity);
        switch (bean.getType()) {
            case 1://9.9
                bundle.putString("TYPE", "4");
                SellwellActivity.openSellwellActivity(activity, SellwellActivity.class, bundle);
                break;
            case 2://限时抢购
                bundle.putString("KEYWORD", bean.getName());
                LimitedActivity.openLimitedActivity(activity, LimitedActivity.class, bundle);
                break;
            case 3://聚划算
                bundle.putString("TYPE", "5");
                SellwellActivity.openSellwellActivity(activity, SellwellActivity.class, bundle);
                break;
            case 4://天猫超市k
            case 5://天猫超市
                if (arouseTaoBao.checkPackage("com.taobao.taobao")) {
                    arouseTaoBao.openTaoBao(bean.getUrl());
                } else {
                    Toast.makeText(activity, "您未安装淘宝，请先安装", Toast.LENGTH_LONG);
                }
                break;
            case 6:
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(activity);
                    return;
                }
                WebActivity.startWebActivity(activity, ConstUtils.WEB_TYPE, bean.getUrl(), bean.getName());
                break;
            case 7:
                Intent intent = new Intent(activity, WebActivity.class);
                String tempUrl = CommonUtils.getParam365(bean.getUrl());
                intent.putExtra(Constants.INTENT_KEY_URL, tempUrl);
                intent.putExtra(Constants.INTENT_KEY_TITLE, "名品抵扣券");
                activity.startActivity(intent);
                ConstantScUtil.BrandCoupon();
                break;
            case 8:
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(activity);
                    return;
                }
                activity.startActivity(new Intent(activity, ChargeActivity.class));
                ConstantScUtil.phoneFeeRecharge();
                break;
            case 9:
                EventBus.getDefault().post(new ChangeFragment(2));
                break;
            case 10:
                EventBus.getDefault().post(new ChangeFragment(3));
                break;
            case 11:
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(activity);
                    return;
                }
                WebActivity.startWebActivity(activity, ConstUtils.WEB_TYPE_OTHER, bean.getUrl(), bean.getName());
                break;
            case 100://聚划算
                bundle.putString("TYPE", "5");
                SellwellActivity.openSellwellActivity(activity, SellwellActivity.class, bundle);
                break;
            case 101://9.9
                bundle.putString("TYPE", "4");
                SellwellActivity.openSellwellActivity(activity, SellwellActivity.class, bundle);
                break;
            case 102://限时抢购
                bundle.putString("KEYWORD", bean.getName());
                LimitedActivity.openLimitedActivity(activity, LimitedActivity.class, bundle);
                break;
            case 103://热销榜单
                bundle.putString("TYPE", "1");
                SellwellActivity.openSellwellActivity(activity, SellwellActivity.class, bundle);
                break;
            case 104://天猫超市
                bundle.putString("TYPE", "2");
                SellwellActivity.openSellwellActivity(activity, SellwellActivity.class, bundle);
                break;
            case 105://天猫国际
                bundle.putString("TYPE", "3");
                SellwellActivity.openSellwellActivity(activity, SellwellActivity.class, bundle);
                break;
            case 108://自定义商品
                bundle.putString("TYPE_TARGET", bean.getUrl());
                CustomGoodesActivity.openCustomGoodesActivity(activity,CustomGoodesActivity.class,bundle);
                break;
            case 109://内部天猫国际，天猫超市
                bundle.putString("Web_Url", bean.getUrl());
                bundle.putString("Web_Tiele", bean.getName());
                NewWebViewActivity.openNewWebViewActivity(activity, bundle);
                break;
            case 110://唤醒小程序
                IWXAPI api = WXAPIFactory.createWXAPI(activity, Constans.WX_APPID);
                WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
                req.userName = "gh_0c4ffc9fbb48"; // 填小程序原始id
                req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
                api.sendReq(req);
                break;
            case 111:
                break;
        }
    }
}

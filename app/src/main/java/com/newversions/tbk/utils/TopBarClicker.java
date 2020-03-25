package com.newversions.tbk.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.iBookStar.views.YmConfig;
import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.ProductSumActivity;
import com.newversions.tbk.activity.WebActivity;
import com.newversions.tbk.entity.TBKHomeEntity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.NewTBHome;
import com.yunqin.bearmall.ui.activity.ChargeActivity;
import com.yunqin.bearmall.ui.activity.DailyTasksActivity;
import com.yunqin.bearmall.ui.activity.InvitationActivity2;
import com.yunqin.bearmall.ui.activity.LimitedActivity;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.SellwellActivity;
import com.yunqin.bearmall.ui.activity.ZeorExchangeActivity;
import com.yunqin.bearmall.util.ArouseTaoBao;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.util.ConstUtils;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.StarActivityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TopBarClicker {
    public static void topBarClick(Activity activity, NewTBHome.ClassificationBean bean) {
        // TODO: 2019/7/15 0015 根据类型执行操作，快速导航
        Log.i("topBarClick", bean.getSort() + "---" + bean.getUrl());
        Bundle bundle = new Bundle();
        ArouseTaoBao arouseTaoBao = new ArouseTaoBao(activity);
        switch (bean.getSort()) {
            case 1://9.9
                bundle.putString("TYPE", "4");
                SellwellActivity.openSellwellActivity(activity, SellwellActivity.class, bundle);
                break;
            case 2://限时抢购
                bundle.putString(Constants.INTENT_KEY_ID, bean.getId() + "");
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
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(activity);
                    return;
                }
                DailyTasksActivity.starActivity(activity);
                break;
            case 10:
                activity.startActivity(new Intent(activity, ZeorExchangeActivity.class));
                ConstantScUtil.exchangeClick();
                break;
        }
    }
}

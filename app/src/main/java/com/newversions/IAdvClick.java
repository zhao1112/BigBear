package com.newversions;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.bbcoupon.ui.activity.MeetingplaceActivity;
import com.newversions.tbk.activity.WebActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.util.ArouseTaoBao;
import com.yunqin.bearmall.util.ConstUtils;

/**
 * 统一封装广告点击事件
 */
public class IAdvClick {
    public static void click(Context context, int type, int skipType, long sourceId, String adUrl) {
        Log.i("IAdvClick", "type: " + type + "\n" + "skipType: " + skipType + "\n" + "sourceId: " + sourceId + "\n" + adUrl);
        Bundle bundle = new Bundle();
        switch (type) {
            case 13:
                ArouseTaoBao arouseTaoBao = new ArouseTaoBao(context);
                if (arouseTaoBao.checkPackage("com.taobao.taobao")) {
                    arouseTaoBao.openTaoBao(adUrl);
                } else {
                    Toast.makeText(context, "您未安装淘宝，请先安装", Toast.LENGTH_LONG);
                }
                break;
            case 14:
                bundle.putString("MEETINGPLACE", adUrl);
                MeetingplaceActivity.openMeetingplaceActivity((Activity) context, bundle);
                break;
            case 15:
                if (TextUtils.isEmpty(adUrl)) {
                    return;
                }
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity((Activity) context);
                    return;
                }
                if (("红包0元购").equals("")) {
                    WebActivity.startWebActivity(context, ConstUtils.WEB_TYPE, adUrl, "");
                } else {
                    WebActivity.startWebActivity(context, ConstUtils.WEB_TYPE_OTHER, adUrl, "");
                }
                break;
        }
    }
}

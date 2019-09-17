package com.yunqin.bearmall.ui.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.newversions.detail.NewProductDetailActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.util.StarActivityUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Master
 * @create 2018/8/31 18:05
 */
public class EmptyActivity extends BaseActivity {

    private static final String DAO_GOU_WEN_ZHANG = "0";
    private static final String FEN_XIANG_SHANG_PIN = "1";
    private static final String TANG_GUO_DUO_BAO = "2";
    private static final String LING_YUAN_PIN_TUAN = "3";
    private static final String KAN_JIA_MIAN_FEI_NA = "4";
    private static final String KAN_JIA_MIAN_FEI_NA_BANG_KAN = "5";
    private static final String PU_TONG_YAO_QING = "6";//普通邀请    会员
    private static final String QU_DAO_YAO_QING = "7";// 渠道邀请   会员
    private static final String TE_YAO_HUI_YUAN = "8";// 特邀

    @Override
    public int layoutId() {
        return R.layout.empty_layout;
    }

    @Override
    public void init() {
        if (!isOtherUIExisting(this)) {
            this.startActivity(new Intent(this, HomeActivity.class));
            Observable.timer(2, TimeUnit.SECONDS)
                    .observeOn(Schedulers.newThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> goToTag());
        } else {
            goToTag();
        }

    }



    private void goToTag() {
        Uri uri = EmptyActivity.this.getIntent().getData();
        if (uri != null) {
            String type = uri.getQueryParameter("type");
            String source_id = uri.getQueryParameter("source_id");
            String invitationCode = uri.getQueryParameter("invitationCode");
            if (DAO_GOU_WEN_ZHANG.equals(type)) {
                VanguardListPageActivity.startH5Activity(EmptyActivity.this, BuildConfig.BASE_URL + "/view/findGuideArticleDetailPage?guideArticle_id=" + source_id, "导购详情");
            } else if (FEN_XIANG_SHANG_PIN.equals(type)) {

                Intent intent = new Intent(EmptyActivity.this, NewProductDetailActivity.class);
                intent.putExtra("productId", "" + source_id);
                intent.putExtra("sku_id", "");
                EmptyActivity.this.startActivity(intent);

            } else if (TANG_GUO_DUO_BAO.equals(type)) {
                StarActivityUtil.starActivity(EmptyActivity.this, SweetSnatchActivity.class);
            } else if (LING_YUAN_PIN_TUAN.equals(type)) {
                Intent intent = new Intent(EmptyActivity.this, ZeroMoneyDetailsActivity.class);
                intent.putExtra("groupPurchasing_id", source_id);
                EmptyActivity.this.startActivity(intent);
            } else if (KAN_JIA_MIAN_FEI_NA.equals(type)) {
                // TODO 砍价免费拿详情
                Intent intent = new Intent(this, BargainFreeDetailActivity.class);
                intent.putExtra(BargainFreeDetailActivity.BARGAIN_PRODUCT_ID, Long.parseLong(source_id));
                intent.putExtra(BargainFreeDetailActivity.BARGAIN_IS_ONGOING, false);
                startActivity(intent);
            } else if (KAN_JIA_MIAN_FEI_NA_BANG_KAN.equals(type)) {
                // TODO 帮好友砍价
                HelpFriendCutDownThePriceActivity.starActivity(EmptyActivity.this, source_id);
            } else if (PU_TONG_YAO_QING.equals(type) || QU_DAO_YAO_QING.equals(type)) {

//                Intent intent = new Intent(this, VipCenterActivity.class);
//                intent.putExtra("type", type);
//                intent.putExtra("invitationCode", invitationCode);
//                startActivity(intent);
                VipCenterActivity.startVipCenterActivity(this,type,invitationCode);

            } else if (TE_YAO_HUI_YUAN.equals(type)) {
//                Intent intent = new Intent(this, InviteVipActivity.class);
//                intent.putExtra("type", type);
//                intent.putExtra("invitationCode", invitationCode);
//                startActivity(intent);
                InviteVipActivity.startInviteVipActivity(this,type,invitationCode);
            }
        }
        BearMallAplication.getInstance().getActivityStack().finishActivity(EmptyActivity.this);
    }


    /**
     * 判断是否有别的页面存在
     *
     * @param context
     * @return
     */
    private boolean isOtherUIExisting(Context context) {
        boolean existing = false;
        try {
            String currClassName = getClass().getName();
            String currPackageName = getPackageName();
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(10);
            if (list.size() <= 0) {
                existing = false;
            }
            for (ActivityManager.RunningTaskInfo info : list) {
                String activityName = info.baseActivity.getClassName();
                if (activityName.equals(currClassName)) {
                    continue;
                }
                if (info.baseActivity.getPackageName().equals(currPackageName)) {
                    existing = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            existing = false;
        }
        return existing;
    }


}

package com.bbcoupon.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bbcoupon.util.ImageUtil;
import com.bbcoupon.util.ShareUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.util.PopUtil;
import com.yunqin.bearmall.util.ShareUtil;
import com.yunqin.bearmall.widget.DownLoadImage;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/4/22
 */
public class MeetingplaceActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.me_image)
    ImageView mMeImage;
    @BindView(R.id.me_password)
    TextView me_password;
    @BindView(R.id.me_go)
    TextView mMeGo;
    @BindView(R.id.me_shear)
    TextView mMeShear;

    private PopUtil popUtil;
    private ShareUtils shareUtil;
    private DownLoadImage downLoadImage;
    private String image = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1091882071,1476673950&fm=26&gp=0.jpg";
    private RoundedCorners roundedCorners = new RoundedCorners(10);
    private RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
    private String image2 = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3381390367,3810581293&fm=26&gp=0.jpg";
    private Bitmap bitmap;
    private Bitmap bitmap1;

    @Override
    public int layoutId() {
        return R.layout.activity_messting;
    }

    @Override
    public void init() {

        popUtil = PopUtil.getInstance();
        popUtil.setContext(this);
        shareUtil = ShareUtils.getInstance();
        shareUtil.setContext(this);

        setKeyBg(me_password, "#ff438ff1");
        setKeyBg(mMeShear, "#fffbdb4a");
        //启动动画
        setAnimation();

        Glide.with(this).load(image).into(mMeImage);

        Glide.with(this).asBitmap().load(image2).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                bitmap = ImageUtil.zoomImage(resource, 200, 200);
            }
        });
    }

    //设置动画
    private void setAnimation() {
        ScaleAnimation scaleAni = new ScaleAnimation(0.8f, 1.0f, 0.8f, 1.0f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //设置动画执行的时间，单位是毫秒
        scaleAni.setDuration(500);
        // 设置动画重复次数
        // -1或者Animation.INFINITE表示无限重复，正数表示重复次数，0表示不重复只播放一次
        scaleAni.setRepeatCount(Animation.INFINITE);
        // 设置动画模式（Animation.REVERSE设置循环反转播放动画,Animation.RESTART每次都从头开始）
        scaleAni.setRepeatMode(Animation.REVERSE);
        // 启动动画
        mMeGo.startAnimation(scaleAni);
    }

    //设置按钮
    public void setKeyBg(TextView view, String color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(60);
        drawable.setColor(Color.parseColor(color));
        view.setBackground(drawable);
    }

    @OnClick({R.id.toolbar_back, R.id.me_password, R.id.me_shear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.me_password:
                break;
            case R.id.me_shear:
                View popView = popUtil.getPopView(R.layout.item_share_meepop, 0);
                popView.findViewById(R.id.me_clear).setOnClickListener(this);
                popView.findViewById(R.id.wx_share).setOnClickListener(this);
                popView.findViewById(R.id.moments_share).setOnClickListener(this);
                popView.findViewById(R.id.dwon_share).setOnClickListener(this);
                ImageView imageView = popView.findViewById(R.id.me_image);
                Glide.with(this).asBitmap().load(image).apply(options).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (bitmap != null) {
                            bitmap1 = ImageUtil.compoundBitmap(resource, bitmap);
                            imageView.setImageBitmap(bitmap1);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.me_clear:
                popUtil.dismissPopupWindow();
                break;
            case R.id.wx_share:
                if (bitmap1 != null) {
                    shareUtil.shareContent(Wechat.NAME, bitmap1);
                    popUtil.dismissPopupWindow();
                }
                break;
            case R.id.moments_share:
                if (bitmap1 != null) {
                    shareUtil.shareContent(WechatMoments.NAME, bitmap1);
                    popUtil.dismissPopupWindow();
                }
                break;
            case R.id.dwon_share:
                if (bitmap1 != null) {
                    try {
                        downLoadImage = DownLoadImage.getInstance();
                        downLoadImage.setContext(this);
                        downLoadImage.saveBitmap(bitmap1);
                        popUtil.dismissPopupWindow();
                        showToast("保存成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("dismissPopupWindow", e.getMessage());
                    }
                }
                break;
        }
    }

}

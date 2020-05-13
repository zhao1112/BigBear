package com.bbcoupon.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bbcoupon.ui.bean.MeetingInfor;
import com.bbcoupon.ui.bean.MeetingShareInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bbcoupon.util.CopyTextUtil;
import com.bbcoupon.util.ImageUtil;
import com.bbcoupon.util.JurisdictionUtil;
import com.bbcoupon.util.ShareUtils;
import com.bbcoupon.util.WindowUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.newversions.tbk.activity.GoodsDetailActivity;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.util.ArouseTaoBao;
import com.yunqin.bearmall.util.AuntTao;
import com.yunqin.bearmall.widget.DownLoadImage;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/4/22
 */
public class MeetingplaceActivity extends BaseActivity implements View.OnClickListener, RequestContract.RequestView {

    @BindView(R.id.me_image)
    ImageView mMeImage;
    @BindView(R.id.me_password)
    TextView me_password;
    @BindView(R.id.me_go)
    TextView mMeGo;
    @BindView(R.id.me_shear)
    TextView mMeShear;
    @BindView(R.id.taopassword)
    TextView mTaopassword;
    @BindView(R.id.bg_color)
    LinearLayout mBgColor;
    @BindView(R.id.titile)
    TextView titile;
    @BindView(R.id.view_style)
    LinearLayout view_style;

    private DownLoadImage downLoadImage;
    private RoundedCorners roundedCorners = new RoundedCorners(10);
    private RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
    private String meetingplace;
    private RequestPresenter presenter;
    private String url;
    private String Taopassword;
    private String image;
    private String images;

    public static void openMeetingplaceActivity(Activity activity, Bundle bundle) {
        Intent intent = new Intent(activity, MeetingplaceActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_messting;
    }

    @Override
    public void init() {

        meetingplace = getIntent().getStringExtra("MEETINGPLACE");

        setKeyBg(mMeShear, "#fffbdb4a");
        setKeyBg(me_password, "#fffbdb4a");

        presenter = new RequestPresenter();
        presenter.setRelation(this);

        //启动动画
        setAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoading();
        Map<String, String> map = new HashMap<>();
        map.put("target", meetingplace);
        presenter.onMeetingplace(MeetingplaceActivity.this, map);
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

    public void setKeyBg(LinearLayout view, String color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.RECTANGLE);
        drawable.setColor(Color.parseColor(color));
        view.setBackground(drawable);
    }

    @OnClick({R.id.toolbar_back, R.id.me_password, R.id.me_shear, R.id.me_go, R.id.toolbar_refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.me_password:
                if (!TextUtils.isEmpty(Taopassword) && !TextUtils.isEmpty(url)) {
                    CopyTextUtil.CopyText(MeetingplaceActivity.this, Taopassword);
                    showToast("复制成功");
                } else {
                    showToast("活动失效，查看其它活动");
                }
                break;
            case R.id.me_shear:
                if (!TextUtils.isEmpty(meetingplace) && !TextUtils.isEmpty(url) && !TextUtils.isEmpty(image)) {
                    Map<String, String> map = new HashMap<>();
                    map.put("target", meetingplace);
                    map.put("url", url);
                    map.put("imageUrl", image);
                    presenter.ontMeetingplaceSearch(MeetingplaceActivity.this, map);
                } else {
                    showToast("活动失效，查看其它活动");
                }
                break;
            case R.id.me_go:
                if (!TextUtils.isEmpty(url)) {
                    ArouseTaoBao arouseTaoBao = new ArouseTaoBao(MeetingplaceActivity.this);
                    if (arouseTaoBao.checkPackage("com.taobao.taobao")) {
                        arouseTaoBao.openTaoBao(url);
                    } else {
                        showToast("请先下载淘宝");
                    }
                } else {
                    showToast("活动失效，查看其它活动");
                }
                break;
            case R.id.toolbar_refresh:
                showLoading();
                Map<String, String> map = new HashMap<>();
                map.put("target", meetingplace);
                presenter.onMeetingplace(MeetingplaceActivity.this, map);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.me_clear:
                WindowUtils.dismissBrightness(MeetingplaceActivity.this);
                break;
            case R.id.wx_share:
                if (images != null) {
                    if (ShareUtils.isWXClientAvailable(MeetingplaceActivity.this)) {
                        ShareUtils.shareContent(Wechat.NAME, images);
                    } else {
                        Toast.makeText(MeetingplaceActivity.this, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                    }
                }
                WindowUtils.dismissBrightness(MeetingplaceActivity.this);
                break;
            case R.id.moments_share:
                if (images != null) {
                    if (ShareUtils.isWXClientAvailable(MeetingplaceActivity.this)) {
                        ShareUtils.shareContent(WechatMoments.NAME, images);
                    } else {
                        Toast.makeText(MeetingplaceActivity.this, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                    }
                }
                WindowUtils.dismissBrightness(MeetingplaceActivity.this);
                break;
            case R.id.dwon_share:
                JurisdictionUtil.Jurisdiction(MeetingplaceActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                if (JurisdictionUtil.IsJurisdiction()) {
                    if (images != null) {
                        Log.e("downLoadImage", images);
                        try {
                            downLoadImage = DownLoadImage.getInstance();
                            downLoadImage.setContext(this);
                            downLoadImage.DownLoadImag(new String[]{images});
                            showToast("保存成功");
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("dismissPopupWindow", e.getMessage());
                        }
                    }
                    WindowUtils.dismissBrightness(MeetingplaceActivity.this);
                } else {
                    showToast("缺少必要权限");
                }
                break;
        }
    }

    @Override
    public void onSuccess(Object data) {
        //主题会场
        if (data instanceof MeetingInfor) {
            try {
                setKeyBg(me_password, ((MeetingInfor) data).getData().getColour());
                setKeyBg(mBgColor, ((MeetingInfor) data).getData().getColour());
                mMeGo.setTextColor(Color.parseColor(((MeetingInfor) data).getData().getColour()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Glide.with(this)
                    .asBitmap()
                    .load(((MeetingInfor) data).getData().getImage())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            Bitmap bitmap = ImageUtil.ImageMatrix(resource, mMeImage);
                            mMeImage.setImageBitmap(bitmap);
                        }
                    });
            titile.setText(((MeetingInfor) data).getData().getTitle());
            mTaopassword.setText(((MeetingInfor) data).getData().getContnet());
            if (((MeetingInfor) data).getData().getUrl() != null) {
                url = ((MeetingInfor) data).getData().getUrl();
            } else {
                showToast("活动失效，查看其它活动");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
            }
            image = ((MeetingInfor) data).getData().getImage();
            Log.e("onSuccess", ((MeetingInfor) data).getData().getImage());
            Taopassword = ((MeetingInfor) data).getData().getContnet();
            view_style.setVisibility(View.GONE);
            hiddenLoadingView();
        }
        //分享图片
        if (data instanceof MeetingShareInfor) {
            if (((MeetingShareInfor) data).getData() != null && ((MeetingShareInfor) data).getData() != null) {
                images = ((MeetingShareInfor) data).getData();
                try {
                    View view = WindowUtils.ShowBrightness(MeetingplaceActivity.this, R.layout.item_share_meepop, 1);
                    ImageView me_clear = view.findViewById(R.id.me_clear);
                    LinearLayout wx_share = view.findViewById(R.id.wx_share);
                    LinearLayout moments_share = view.findViewById(R.id.moments_share);
                    LinearLayout dwon_share = view.findViewById(R.id.dwon_share);
                    ImageView imageView = view.findViewById(R.id.me_image);
                    me_clear.setOnClickListener(this);
                    wx_share.setOnClickListener(this);
                    moments_share.setOnClickListener(this);
                    dwon_share.setOnClickListener(this);
                    Log.e("imageView", ((MeetingShareInfor) data).getData());
                    Glide.with(this)
                            .asBitmap()
                            .load(((MeetingShareInfor) data).getData()).apply(options)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    Bitmap bitmap = ImageUtil.ImageMatrix(resource, imageView);
                                    imageView.setImageBitmap(bitmap);
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onNotNetWork() {

    }

    @Override
    public void onFail(Throwable e) {
        MeetingInfor meetingInfor = new Gson().fromJson(e.getMessage(), MeetingInfor.class);
        if (meetingInfor.getCode() == 3) {
            AuntTao auntTao = new AuntTao();
            auntTao.setContext(MeetingplaceActivity.this);
            auntTao.AuntTabo();
            finish();
        }
        Log.e("Throwable", e.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.setUntying(this);
    }
}

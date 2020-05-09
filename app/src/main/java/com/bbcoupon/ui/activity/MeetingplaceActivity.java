package com.bbcoupon.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bbcoupon.ui.bean.MeetingInfor;
import com.bbcoupon.ui.bean.MeetingShareInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bbcoupon.util.CopyTextUtil;
import com.bbcoupon.util.ShareUtils;
import com.bbcoupon.util.WindowUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.util.ArouseTaoBao;
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
        Map<String, String> map = new HashMap<>();
        map.put("target", meetingplace);
        presenter.onMeetingplace(MeetingplaceActivity.this, map);

        //启动动画
        setAnimation();
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

    @OnClick({R.id.toolbar_back, R.id.me_password, R.id.me_shear, R.id.me_go})
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
                if (images != null) {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
            Glide.with(this).load(((MeetingInfor) data).getData().getImage()).into(mMeImage);
            mTaopassword.setText(((MeetingInfor) data).getData().getContnet());
            if (((MeetingInfor) data).getData().getUrl() != null) {
                url = ((MeetingInfor) data).getData().getUrl();
            } else {
                showToast("活动失效，查看其它活动");
            }
            image = ((MeetingInfor) data).getData().getImage();
            Taopassword = ((MeetingInfor) data).getData().getContnet();

        }
        //分享图片
        if (data instanceof MeetingShareInfor) {
            if (((MeetingShareInfor) data).getData() != null && ((MeetingShareInfor) data).getData().getData() != null) {
                View view = WindowUtils.ShowBrightness(MeetingplaceActivity.this, R.layout.item_share_meepop, 1);
                view.findViewById(R.id.me_clear).setOnClickListener(this);
                view.findViewById(R.id.wx_share).setOnClickListener(this);
                view.findViewById(R.id.moments_share).setOnClickListener(this);
                view.findViewById(R.id.dwon_share).setOnClickListener(this);
                ImageView imageView = view.findViewById(R.id.me_image);
                Glide.with(this).load(((MeetingShareInfor) data).getData().getData()).apply(options).into(imageView);
                images = ((MeetingShareInfor) data).getData().getData();
            }
        }

    }

    @Override
    public void onNotNetWork() {

    }

    @Override
    public void onFail(Throwable e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.setUntying(this);
    }
}

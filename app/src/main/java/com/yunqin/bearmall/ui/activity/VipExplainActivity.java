package com.yunqin.bearmall.ui.activity;


import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.UserPromotion;
import com.yunqin.bearmall.inter.ChangeHeadImageCallBack;
import com.yunqin.bearmall.ui.activity.contract.VipContract;
import com.yunqin.bearmall.ui.activity.presenter.Vippresenter;
import com.yunqin.bearmall.util.CommonUtil;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.DialogUtils;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.util.UploadScreenshots;
import com.yunqin.bearmall.widget.CircleImageView;
import com.yunqin.bearmall.widget.OpenGoodsDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VipExplainActivity extends BaseActivity implements VipContract.UI, ChangeHeadImageCallBack,
        UploadScreenshots.OnUpLoadCallBack {

    @BindView(R.id.vip_head)
    CircleImageView mVipHead;
    @BindView(R.id.vip_name)
    TextView mVipName;
    @BindView(R.id.vip_equity_image1)
    ImageView mVipEquityImage1;
    @BindView(R.id.vip_equity_text1)
    TextView mVipEquityText1;
    @BindView(R.id.vip_equity_iamge2)
    ImageView mVipEquityIamge2;
    @BindView(R.id.vip_equity_text2)
    TextView mVipEquityText2;
    @BindView(R.id.vip_equity_image3)
    ImageView mVipEquityImage3;
    @BindView(R.id.vip_equity_text3)
    TextView mVipEquityText3;
    @BindView(R.id.vip_view_logon2)
    RelativeLayout mVipViewLogon2;
    @BindView(R.id.vip_view_v1)
    RelativeLayout mVipViewV1;
    @BindView(R.id.vip_view_v2)
    RelativeLayout mVipViewV2;
    @BindView(R.id.vip_view_partner)
    RelativeLayout mVipViewPartner;
    @BindView(R.id.vip_sercice)
    RelativeLayout mVipSercice;
    @BindView(R.id.vip_equity1)
    LinearLayout mVipEquity1;
    @BindView(R.id.vip_equity2)
    LinearLayout mVipEquity2;
    @BindView(R.id.vip_equity3)
    LinearLayout mVipEquity3;
    @BindView(R.id.vip_service_head)
    CircleImageView mVipServiceHead;
    @BindView(R.id.vip_service_name)
    TextView mVipServiceName;
    @BindView(R.id.vip_service_jue)
    TextView mVipServiceJue;
    @BindView(R.id.vip_service_code)
    TextView mVipServiceCode;
    @BindView(R.id.vip_service_image)
    ImageView mVipServiceImage;
    @BindView(R.id.vip_service_wx)
    TextView mVipServiceWx;
    //注册会员
    @BindView(R.id.vip_logon_one_text)
    TextView mVipLogonOneText;
    @BindView(R.id.vip_logon_bar_one)
    ProgressBar mVipLogonBarOne;
    @BindView(R.id.vip_logon_text_one)
    TextView mVipLogonTextOne;
    @BindView(R.id.vip_logon_three_text)
    TextView mVipLogonThreeText;
    @BindView(R.id.vip_logon_bar_two)
    ProgressBar mVipLogonBarTwo;
    @BindView(R.id.vip_logon_text_two)
    TextView mVipLogonTextTwo;
    @BindView(R.id.vip_logon_three_text2)
    TextView mVipLogonThreeText2;
    @BindView(R.id.vip_logon_bar_two2)
    ProgressBar mVipLogonBarTwo2;
    @BindView(R.id.vip_logon_text_two2)
    TextView mVipLogonTextTwo2;
    @BindView(R.id.vip_logon_wx)
    TextView vip_logon_wx;
    @BindView(R.id.vip_logon_invitation)
    TextView vip_logon_invitation;
    @BindView(R.id.vip_logon_image)
    TextView vip_logon_image;
    @BindView(R.id.vip_logon_extension)
    TextView vip_logon_extension;
    @BindView(R.id.vip_logon_extension2)
    TextView vip_logon_extension2;
    @BindView(R.id.vip_logon_up)
    Button vip_logon_up;
    //超级会员
    @BindView(R.id.vip_v1_invitation)
    TextView vip_v1_invitation;
    @BindView(R.id.vip_v1_extension)
    TextView vip_v1_extension;
    @BindView(R.id.vip_v1_one_text)
    TextView vip_v1_one_text;
    @BindView(R.id.vip_v1_bar_one)
    ProgressBar vip_v1_bar_one;
    @BindView(R.id.vip_v1_text_one)
    TextView vip_v1_text_one;
    @BindView(R.id.vip_v1_two_text)
    TextView vip_v1_two_text;
    @BindView(R.id.vip_v1_bar_two)
    ProgressBar vip_v1_bar_two;
    @BindView(R.id.vip_v1_text_two)
    TextView vip_v1_text_two;
    @BindView(R.id.vip_v1_three_text)
    TextView vip_v1_three_text;
    @BindView(R.id.vip_v1_bar_three)
    ProgressBar vip_v1_bar_three;
    @BindView(R.id.vip_v1_text_three)
    TextView vip_v1_text_three;
    @BindView(R.id.vip_v1_up)
    Button vip_v1_up;
    //大团长v1、2、3
    @BindView(R.id.vip_v2_invitation)
    TextView vip_v2_invitation;
    @BindView(R.id.vip_v2_extension)
    TextView vip_v2_extension;
    @BindView(R.id.vip_v2_one_text)
    TextView vip_v2_one_text;
    @BindView(R.id.vip_v2_bar_one)
    ProgressBar vip_v2_bar_one;
    @BindView(R.id.vip_v2_text_one)
    TextView vip_v2_text_one;
    @BindView(R.id.vip_v2_two_text)
    TextView vip_v2_two_text;
    @BindView(R.id.vip_v2_bar_two)
    ProgressBar vip_v2_bar_two;
    @BindView(R.id.vip_v2_text_two)
    TextView vip_v2_text_two;
    @BindView(R.id.vip_v2_up)
    Button vip_v2_up;
    //大团长v4
    @BindView(R.id.vip_partner_invitation)
    TextView vip_partner_invitation;
    @BindView(R.id.vip_partner_extension)
    TextView vip_partner_extension;
    @BindView(R.id.vip_partner_one_text1)
    TextView vip_partner_one_text1;
    @BindView(R.id.vip_partner_bar_one1)
    ProgressBar vip_partner_bar_one1;
    @BindView(R.id.vip_partner_text_one1)
    TextView vip_partner_text_one1;
    @BindView(R.id.vip_partner_one_text2)
    TextView vip_partner_one_text2;
    @BindView(R.id.vip_partner_bar_one2)
    ProgressBar vip_partner_bar_one2;
    @BindView(R.id.vip_partner_text_one2)
    TextView vip_partner_text_one2;
    @BindView(R.id.vip_partner_two_text)
    TextView vip_partner_two_text;
    @BindView(R.id.vip_partner_bar_two)
    ProgressBar vip_partner_bar_two;
    @BindView(R.id.vip_partner_text_two)
    TextView vip_partner_text_two;
    @BindView(R.id.vip_partner_up)
    Button vip_partner_up;

    private RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.mine_user_icon_defult)
            .error(R.drawable.mine_user_icon_defult)
            .centerCrop();
    private boolean invitation = true;
    private boolean checkImage = true;
    private boolean extension = true;
    private String wxId = null;
    private static final int CODE_GALLERY_REQUEST = 0;
    private Uri imageUri;
    private int Type_Vip;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    vip_logon_image.setText("审核中");
                    showToast("上传成功");
                    break;
                case 1:
                    showToast("上传失败");
                    break;
            }
        }
    };
    private Vippresenter mVippresenter;
    private PopupWindow mMPopupWindow;
    private int mAudit;


    public static void opneVipExplainActivity(Activity context, Class cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
        context.overridePendingTransition(0, R.anim.activity_stay);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_vip_explain;
    }

    @Override
    public void init() {

        setTranslucentStatus();
        mAudit = getIntent().getIntExtra("audit", 0);
        if (mAudit == 0 || mAudit == 2) {
            vip_logon_up.setText("立即升级");
            vip_v1_up.setText("立即升级");
            vip_v2_up.setText("立即升级");
            vip_partner_up.setText("立即升级");
        } else if (mAudit == 1) {
            vip_logon_up.setText("审核中");
            vip_v1_up.setText("审核中");
            vip_v2_up.setText("审核中");
            vip_partner_up.setText("审核中");
        }
        mVippresenter = new Vippresenter(this);
        mVippresenter.start(this);
    }

    @OnClick({R.id.vip_back, R.id.vip_service_copy, R.id.vip_logon_tips, R.id.vip_logon_invitation, R.id.vip_logon_extension,
            R.id.vip_logon_image, R.id.vip_logon_extension2, R.id.vip_v1_tips, R.id.vip_v1_invitation, R.id.vip_v1_extension,
            R.id.vip_v2_tips, R.id.vip_v2_invitation, R.id.vip_v2_extension, R.id.vip_partner_tips, R.id.vip_partner_invitation,
            R.id.vip_partner_extension, R.id.vip_logon_up, R.id.vip_v1_up, R.id.vip_v2_up, R.id.vip_partner_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vip_back:
                finish();
                break;
            case R.id.vip_service_copy://复制微信号
                if (!TextUtils.isEmpty(wxId)) {
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, wxId));
                    showToast("复制成功");
                }
                break;
            case R.id.vip_logon_tips://注册会员说明
            case R.id.vip_v1_tips:
            case R.id.vip_v2_tips:
            case R.id.vip_partner_tips:

                break;
            case R.id.vip_logon_invitation://邀请
            case R.id.vip_v1_invitation:
            case R.id.vip_v2_invitation:
            case R.id.vip_partner_invitation:
                if (invitation) {
                    //邀请好友页
                    openInvitationActivity2();
                }
                break;
            case R.id.vip_logon_image:
                if (checkImage) {
                    //上传截图
                    DialogUtils.changeVipImage(this, this);
                }
                break;
            case R.id.vip_logon_extension://去推广
            case R.id.vip_logon_extension2:
            case R.id.vip_v1_extension:
            case R.id.vip_v2_extension:
            case R.id.vip_partner_extension:
                if (extension) {
                    opneHomeActivity();
                }
                break;
            case R.id.vip_logon_up://升级
            case R.id.vip_v1_up:
            case R.id.vip_v2_up:
            case R.id.vip_partner_up:
                if (mAudit != 1) {
                    mVippresenter.onDeduction(VipExplainActivity.this, Type_Vip + "");
                }
                break;
        }
    }

    @Override
    public void resultData(UserPromotion promotion) {
        if (promotion != null) {
            //设置页面
            showVipView(promotion);
            //设置值
            setViewValue(promotion);
            //微信号
            wxId = promotion.getData().getParentWXId();
            //会员类型
            Type_Vip = promotion.getType();
        }
    }

    @Override
    public void onFail(Throwable throwable) {

    }

    @Override
    public void onNotNetWork() {

    }

    @Override
    public void getDeduction(int code) {
        if (code == 1) {
            showPopUp();
            vip_logon_up.setText("审核中");
            vip_v1_up.setText("审核中");
            vip_v2_up.setText("审核中");
            vip_partner_up.setText("审核中");
            mVippresenter.start(this);
        } else {
            showToast("网络不佳");
        }
    }

    private void showVipView(UserPromotion promotion) {
        try {
            UserPromotion.Data data = promotion.getData();
            switch (promotion.getType()) {
                case 1://注册会员
                    mVipEquityText1.setText("佣金提高20%");
                    mVipEquityImage1.setImageDrawable(getResources().getDrawable(R.mipmap.vip_yongjin));
                    mVipEquityText2.setText("额外推荐收益");
                    mVipEquityIamge2.setImageDrawable(getResources().getDrawable(R.mipmap.vip_ewaishouyi));
                    mVipEquityText3.setText("商学院VIP课程");
                    mVipEquityImage3.setImageDrawable(getResources().getDrawable(R.mipmap.vip_kecheng));
                    //任务状态
                    if (data.getCheckWXState() == 0) {
                        checkImage = false;
                        vip_logon_image.setText("审核中");
                    } else if (data.getCheckWXState() == 1) {
                        checkImage = false;
                        vip_logon_image.setText("已完成");
                    } else if (data.getCheckWXState() == 2) {
                        checkImage = true;
                        vip_logon_image.setText("审核失败");
                    } else if (data.getCheckWXState() == 4) {
                        checkImage = true;
                        vip_logon_image.setText("上传截图");
                    }
                    if (data.getInvitationIsDisplay() == 1) {
                        invitation = false;
                        vip_logon_invitation.setText("已完成");
                    } else {
                        invitation = true;
                        vip_logon_invitation.setText("去邀请");
                    }
                    if (data.getOrderIsDisplay() == 1) {
                        extension = false;
                        vip_logon_extension.setText("已完成");
                        vip_logon_extension2.setText("已完成");
                    } else {
                        extension = true;
                        vip_logon_extension.setText("去推广");
                        vip_logon_extension2.setText("去推广");
                    }
                    //是否可以升级
                    if (data.getIsDisplay1() == 1 || data.getIsDisplay2() == 1) {
                        vip_logon_up.setVisibility(View.VISIBLE);
                    }
                    mVipEquity1.setVisibility(View.VISIBLE);
                    mVipEquity2.setVisibility(View.VISIBLE);
                    mVipEquity3.setVisibility(View.VISIBLE);
                    mVipViewLogon2.setVisibility(View.VISIBLE);
                    mVipViewV1.setVisibility(View.GONE);
                    mVipViewV2.setVisibility(View.GONE);
                    mVipViewPartner.setVisibility(View.GONE);
                    break;
                case 2://超级会员
                    mVipEquityText1.setText("佣金提高60%");
                    mVipEquityImage1.setImageDrawable(getResources().getDrawable(R.mipmap.vip_yongjin));
                    mVipEquityText2.setText("额外团队收益");
                    mVipEquityIamge2.setImageDrawable(getResources().getDrawable(R.mipmap.vip_ewaishouyi));
                    mVipEquityText3.setText("商学院所有课程");
                    mVipEquityImage3.setImageDrawable(getResources().getDrawable(R.mipmap.vip_kecheng2));
                    //任务状态
                    if (data.getInvitationIsDisplay() == 1) {
                        invitation = false;
                        vip_v1_invitation.setText("已完成");
                    } else {
                        invitation = true;
                        vip_v1_invitation.setText("去邀请");
                    }
                    if (data.getActivityIsDisplay() == 1) {
                        extension = false;
                        vip_v1_extension.setText("已完成");
                    } else {
                        extension = true;
                        vip_v1_extension.setText("去推广");
                    }
                    //是否可以升级
                    if (data.getIsDisplay() == 1) {
                        vip_v1_up.setVisibility(View.VISIBLE);
                    }
                    mVipEquity1.setVisibility(View.VISIBLE);
                    mVipEquity2.setVisibility(View.VISIBLE);
                    mVipEquity3.setVisibility(View.VISIBLE);
                    mVipViewV1.setVisibility(View.VISIBLE);
                    mVipViewLogon2.setVisibility(View.GONE);
                    mVipViewV2.setVisibility(View.GONE);
                    mVipViewPartner.setVisibility(View.GONE);
                    break;
                case 3://大团长v1
                    mVipEquityText1.setText("团队收益提高10%");
                    mVipEquityImage1.setImageDrawable(getResources().getDrawable(R.mipmap.vip_v1_comm));
                    //任务状态
                    if (data.getInvitationIsDisplay() == 1) {
                        invitation = false;
                        vip_v2_invitation.setText("已完成");
                    } else {
                        invitation = true;
                        vip_v2_invitation.setText("去邀请");
                    }
                    if (data.getActivityIsDisplay() == 1) {
                        extension = false;
                        vip_v2_extension.setText("已完成");
                    } else {
                        extension = true;
                        vip_v2_extension.setText("去推广");
                    }
                    //是否可以升级
                    if (data.getIsDisplay() == 1) {
                        vip_v2_up.setVisibility(View.VISIBLE);
                    }
                    mVipEquity1.setVisibility(View.VISIBLE);
                    mVipViewV2.setVisibility(View.VISIBLE);
                    mVipViewV1.setVisibility(View.GONE);
                    mVipViewLogon2.setVisibility(View.GONE);
                    mVipViewPartner.setVisibility(View.GONE);
                    break;
                case 4://大团长v2
                    mVipEquityText1.setText("团队收益提高5%");
                    mVipEquityImage1.setImageDrawable(getResources().getDrawable(R.mipmap.vip_v2_comm));
                    //任务状态
                    if (data.getInvitationIsDisplay() == 1) {
                        invitation = false;
                        vip_v2_invitation.setText("已完成");
                    } else {
                        invitation = true;
                        vip_v2_invitation.setText("去邀请");
                    }
                    if (data.getActivityIsDisplay() == 1) {
                        extension = false;
                        vip_v2_extension.setText("已完成");
                    } else {
                        extension = true;
                        vip_v2_extension.setText("去推广");
                    }
                    //是否可以升级
                    if (data.getIsDisplay() == 1) {
                        vip_v2_up.setVisibility(View.VISIBLE);
                    }
                    mVipEquity1.setVisibility(View.VISIBLE);
                    mVipViewV2.setVisibility(View.VISIBLE);
                    mVipViewV1.setVisibility(View.GONE);
                    mVipViewLogon2.setVisibility(View.GONE);
                    mVipViewPartner.setVisibility(View.GONE);
                    break;
                case 5://大团长v3
                    mVipEquityText1.setText("团队收益提高2.5%");
                    mVipEquityImage1.setImageDrawable(getResources().getDrawable(R.mipmap.vip_v3_comm));
                    //任务状态
                    if (data.getInvitationIsDisplay() == 1) {
                        invitation = false;
                        vip_v2_invitation.setText("已完成");
                    } else {
                        invitation = true;
                        vip_v2_invitation.setText("去邀请");
                    }
                    if (data.getActivityIsDisplay() == 1) {
                        extension = false;
                        vip_v2_extension.setText("已完成");
                    } else {
                        extension = true;
                        vip_v2_extension.setText("去推广");
                    }
                    //是否可以升级
                    if (data.getIsDisplay() == 1) {
                        vip_v2_up.setVisibility(View.VISIBLE);
                    }
                    mVipEquity1.setVisibility(View.VISIBLE);
                    mVipViewV2.setVisibility(View.VISIBLE);
                    mVipViewV1.setVisibility(View.GONE);
                    mVipViewLogon2.setVisibility(View.GONE);
                    mVipViewPartner.setVisibility(View.GONE);
                    break;
                case 6://大团长v4
                    mVipEquityText1.setText("平台额外5%佣金补贴");
                    mVipEquityImage1.setImageDrawable(getResources().getDrawable(R.mipmap.vip_v2_comm));
                    mVipEquityText2.setText("独立运营管理后台");
                    mVipEquityIamge2.setImageDrawable(getResources().getDrawable(R.mipmap.vip_admin_));
                    //任务状态
                    if (data.getInvitationIsDisplay() == 1) {
                        invitation = false;
                        vip_partner_invitation.setText("已完成");
                    } else {
                        invitation = true;
                        vip_partner_invitation.setText("去邀请");
                    }
                    if (data.getActivityIsDisplay() == 1) {
                        extension = false;
                        vip_partner_extension.setText("已完成");
                    } else {
                        extension = true;
                        vip_partner_extension.setText("去推广");
                    }
                    //是否可以升级
                    if (data.getIsDisplay() == 1) {
                        vip_partner_up.setVisibility(View.VISIBLE);
                    }
                    mVipEquity1.setVisibility(View.VISIBLE);
                    mVipEquity2.setVisibility(View.VISIBLE);
                    mVipViewPartner.setVisibility(View.VISIBLE);
                    mVipViewV2.setVisibility(View.GONE);
                    mVipViewV1.setVisibility(View.GONE);
                    mVipViewLogon2.setVisibility(View.GONE);
                    break;
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setViewValue(UserPromotion promotion) {
        //设置页面共同拥有值
        try {
            UserPromotion.Data data = promotion.getData();
            Glide.with(this).setDefaultRequestOptions(requestOptions).load(data.getIconUrl()).into(mVipHead);
            mVipName.setText(data.getNickName());
            Glide.with(this).setDefaultRequestOptions(requestOptions).load(data.getParentIconUrl()).into(mVipServiceHead);
            mVipServiceName.setText(data.getParentNickName());
            mVipServiceJue.setText(data.getParentRole());
            mVipServiceCode.setText("邀请码：" + data.getParentInviteCode());
            Glide.with(this).load(data.getParentWXCode()).into(mVipServiceImage);
            mVipServiceWx.setText("微信号：" + data.getParentWXId());
            switch (promotion.getType()) {
                case 1://注册会员
                    mVipLogonOneText.setText("邀请" + data.getTotalNumber() + "名注册会员");
                    mVipLogonBarOne.setMax(data.getTotalNumber());
                    mVipLogonBarOne.setProgress(data.getFansNum());
                    mVipLogonTextOne.setText(data.getFansNum() + "/" + data.getTotalNumber() + "人");
                    mVipLogonThreeText.setText("完成" + data.getOrderTotalNumber() + "单（0元购除外）");
                    mVipLogonBarTwo.setMax(data.getOrderTotalNumber());
                    mVipLogonBarTwo.setProgress(data.getOrderNumber());
                    mVipLogonTextTwo.setText(data.getOrderNumber() + "/" + data.getOrderTotalNumber() + "单");
                    vip_logon_wx.setText("建立" + data.getWeChatGroupNumber() + "人以上微信群");
                    mVipLogonThreeText2.setText("完成" + data.getOrderTotalNumber() + "单（0元购除外）");
                    mVipLogonBarTwo2.setMax(data.getOrderTotalNumber());
                    mVipLogonBarTwo2.setProgress(data.getOrderNumber());
                    mVipLogonTextTwo2.setText(data.getOrderNumber() + "/" + data.getOrderTotalNumber() + "单");
                    break;
                case 2://超级会员
                    vip_v1_one_text.setText("直邀超级会员" + data.getTotalNumber() + "人以上");
                    vip_v1_bar_one.setMax(data.getTotalNumber());
                    vip_v1_bar_one.setProgress(data.getFansNum());
                    vip_v1_text_one.setText(data.getFansNum() + "/" + data.getTotalNumber() + "人");
                    vip_v1_two_text.setText("直邀或间接超级会员" + data.getTwoLevelTotalNumber() + "人以上");
                    vip_v1_bar_two.setMax(data.getTwoLevelTotalNumber());
                    vip_v1_bar_two.setProgress(data.getTwoLevelFans());
                    vip_v1_text_two.setText(data.getTwoLevelFans() + "/" + data.getTwoLevelTotalNumber() + "人");
                    vip_v1_three_text.setText("活跃度" + data.getActivityLevelTotalNumber() + "以上");
                    vip_v1_bar_three.setMax(data.getActivityLevelTotalNumber());
                    vip_v1_bar_three.setProgress(data.getActivityLevel());
                    vip_v1_text_three.setText(data.getActivityLevel() + "/" + data.getActivityLevelTotalNumber());
                    break;
                case 3://大团长v1
                case 4://大团长v2
                case 5://大团长v3
                    vip_v2_one_text.setText("团队超级会员" + data.getTeamSuperMemberTotal() + "人以上");
                    vip_v2_bar_one.setMax(data.getTeamSuperMemberTotal());
                    vip_v2_bar_one.setProgress(data.getSuperMemNumber());
                    vip_v2_text_one.setText(data.getSuperMemNumber() + "/" + data.getTeamSuperMemberTotal() + "人");
                    vip_v2_two_text.setText("活跃度" + data.getActivityLevelTotalNumber() + "以上");
                    vip_v2_bar_two.setMax(data.getActivityLevelTotalNumber());
                    vip_v2_bar_two.setProgress(data.getActivityLevel());
                    vip_v2_text_two.setText(data.getActivityLevel() + "/" + data.getActivityLevelTotalNumber());
                    break;
                case 6://大团长v4
                    vip_partner_one_text1.setText("直邀超级会员" + data.getClassAVipNum() + "人以上");
                    vip_partner_bar_one1.setMax(data.getClassAVipNum());
                    vip_partner_bar_one1.setProgress(data.getFansNum());
                    vip_partner_text_one1.setText(data.getFansNum() + "/" + data.getClassAVipNum() + "人");
                    vip_partner_one_text2.setText("团队超级会员" + data.getTeamSuperMemberTotal() + "人以上");
                    vip_partner_bar_one2.setMax(data.getTeamSuperMemberTotal());
                    vip_partner_bar_one2.setProgress(data.getSuperMemNumber());
                    vip_partner_text_one2.setText(data.getSuperMemNumber() + "/" + data.getTeamSuperMemberTotal() + "人");
                    vip_partner_two_text.setText("活跃度" + data.getActivityLevelTotalNumber() + "以上");
                    vip_partner_bar_two.setMax(data.getActivityLevelTotalNumber());
                    vip_partner_bar_two.setProgress(data.getActivityLevel());
                    vip_partner_text_two.setText(data.getActivityLevel() + "/" + data.getActivityLevelTotalNumber());
                    break;
            }
            mVipSercice.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openInvitationActivity2() {
        StarActivityUtil.starActivity(this, InvitationActivity2.class);
        //TODO[邀请好友]
        ConstantScUtil.sensorsInviteFriends("升级vip页面：邀请好友");
    }

    private void opneHomeActivity() {
        Intent intenta =
                new Intent(this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intenta);
    }

    private void showPopUp() {
        OpenGoodsDetail.lightoff(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_upgrade, null);
        mMPopupWindow = new PopupWindow();
        mMPopupWindow.setContentView(view);
        mMPopupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        mMPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置背景图片， 必须设置，不然动画没作用
        mMPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mMPopupWindow.setAnimationStyle(R.style.CenterAnimation);
        mMPopupWindow.setFocusable(true);
        // 设置点击popupwindow外屏幕其它地方消失
        mMPopupWindow.setOutsideTouchable(true);
        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        mMPopupWindow.showAtLocation(view, Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        view.findViewById(R.id.vip_upgrade_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMPopupWindow.dismiss();
            }
        });

        mMPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                OpenGoodsDetail.lighton(VipExplainActivity.this);
            }
        });
    }


    @Override
    public void choseWhat(int flag) {
        //1 相机  2 相册else
        ImageSelector.builder()
                .useCamera(false) // 设置是否使用拍照
                .setCrop(false)
                .setSingle(true)  //设置是否单选
                .start(this, CODE_GALLERY_REQUEST); // 打开相册

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_GALLERY_REQUEST) {
            if (data != null) {
                ArrayList<String> backPics = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
                for (int i = 0; i < backPics.size(); i++) {
                    try {
                        File file = new File(backPics.get(i));
                        imageUri = Uri.fromFile(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                UploadScreenshot(imageUri);
            }
        }
    }

    private void UploadScreenshot(Uri uri) {
        UploadScreenshots.okHttpScreenshots(this, uri, this);
    }


    @Override
    public void onSuccessID(String data) {
        Message message = new Message();
        try {
            JSONObject jsonObject = new JSONObject(data);
            if (jsonObject.optInt("code") == 1) {
                checkImage = false;
                message.what = 0;
                mHandler.sendMessage(message);
            } else {
                message.what = 1;
                mHandler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFail(String fail) {
        Message message = new Message();
        message.what = 1;
        mHandler.sendMessage(message);
    }
}

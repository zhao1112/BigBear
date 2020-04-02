package com.yunqin.bearmall.ui.activity;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
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
import com.newversions.tbk.activity.WebActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.UserPromotion;
import com.yunqin.bearmall.eventbus.VipUpgrade;
import com.yunqin.bearmall.inter.ChangeHeadImageCallBack;
import com.yunqin.bearmall.ui.activity.contract.VipContract;
import com.yunqin.bearmall.ui.activity.presenter.Vippresenter;
import com.yunqin.bearmall.util.ConstUtils;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.DialogUtils;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.util.UploadScreenshots;
import com.yunqin.bearmall.widget.CircleImageView;
import com.yunqin.bearmall.widget.OpenGoodsDetail;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.bean.ImageInfo;

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
    private String upUrl1 = "http://api.bbbearmall.com/view/getUpgradeInfo1";
    private String upUrl2 = "http://api.bbbearmall.com/view/getUpgradeInfo2";
    private String upUrl3 = "http://api.bbbearmall.com/view/getUpgradeInfo3";
    private String upUrl4 = "http://api.bbbearmall.com/view/getUpgradeInfo4";
    private String upUrl5 = "http://api.bbbearmall.com/view/getUpgradeInfo5";
    private String upUrl6 = "http://api.bbbearmall.com/view/getUpgradeInfo6";
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
    private boolean Audit = true;
    private int state2 = 0;
    private String wxImage;


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

        if (BearMallAplication.getInstance().getUser() == null) {
            return;
        }

        int mAudit = getIntent().getIntExtra("audit", 0);
        if (mAudit == 0 || mAudit == 2) {
            vip_logon_up.setText("立即升级");
            vip_v1_up.setText("立即升级");
            vip_v2_up.setText("立即升级");
            vip_partner_up.setText("立即升级");
        } else if (mAudit == 1) {
            Audit = false;
            vip_logon_up.setText("审核中");
            vip_v1_up.setText("审核中");
            vip_v2_up.setText("审核中");
            vip_partner_up.setText("审核中");
        }

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(BearMallAplication.getInstance().getUser().getData().getMember().getIconUrl())
                .into(mVipHead);
        mVipName.setText(BearMallAplication.getInstance().getUser().getData().getMember().getNickName());

        mVippresenter = new Vippresenter(this);
        mVippresenter.start(this);
    }

    @OnClick({R.id.vip_back, R.id.vip_service_copy, R.id.vip_logon_tips, R.id.vip_logon_invitation, R.id.vip_logon_extension,
            R.id.vip_logon_image, R.id.vip_logon_extension2, R.id.vip_v1_tips, R.id.vip_v1_invitation, R.id.vip_v1_extension,
            R.id.vip_v2_tips, R.id.vip_v2_invitation, R.id.vip_v2_extension, R.id.vip_partner_tips, R.id.vip_partner_invitation,
            R.id.vip_partner_extension, R.id.vip_logon_up, R.id.vip_v1_up, R.id.vip_v2_up, R.id.vip_partner_up, R.id.vip_service_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vip_back:
                finish();
                break;
            case R.id.vip_service_image:
                if (!TextUtils.isEmpty(wxImage)) {
                    seeImage(wxImage);
                }
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
                setWeb(Type_Vip);
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
                if (Audit) {
                    mVippresenter.onDeduction(VipExplainActivity.this, Type_Vip + "");
                } else {
//                    showPopUp(1);
                }
                break;

        }
    }

    private void setWeb(int type_vip) {
        switch (type_vip) {
            case 1://注册会员
                WebActivity.startWebActivity(VipExplainActivity.this, 0, upUrl1, "规则说明");
                break;
            case 2://超级会员
                WebActivity.startWebActivity(VipExplainActivity.this, 0, upUrl2, "规则说明");
                break;
            case 3://大团长v1
                WebActivity.startWebActivity(VipExplainActivity.this, 0, upUrl3, "规则说明");
                break;
            case 4://大团长v2
                WebActivity.startWebActivity(VipExplainActivity.this, 0, upUrl4, "规则说明");
                break;
            case 5://大团长v3
                WebActivity.startWebActivity(VipExplainActivity.this, 0, upUrl5, "规则说明");
                break;
            case 6://大团长v4
                WebActivity.startWebActivity(VipExplainActivity.this, 0, upUrl6, "规则说明");
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
            //微信是否显示
            if (!TextUtils.isEmpty(promotion.getData().getParentWXCode()) || !TextUtils.isEmpty(promotion.getData().getParentWXId())) {
                Log.e("resultData", "resultData: " + promotion.getData().getParentWXCode());
                Log.e("resultData", "resultData: " + promotion.getData().getParentWXId());
                mVipSercice.setVisibility(View.VISIBLE);

            } else {
                mVipSercice.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onFail(Throwable throwable) {
        Toast.makeText(VipExplainActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNotNetWork() {

    }

    @Override
    public void getDeduction(int code) {
        if (code == 1) {
            if (Type_Vip == 2) {
                vip_logon_up.setText("审核中");
                vip_v1_up.setText("审核中");
                vip_v2_up.setText("审核中");
                vip_partner_up.setText("审核中");
//                showPopUp(1);
                Audit = false;
                showToast("审核已提交，请等待结果");
            } else {
                Audit = true;
                showPopUp(0);
            }
        } else {
            showToast("网络不佳");
        }
    }

    private void showVipView(UserPromotion promotion) {
        try {
            UserPromotion.Data data = promotion.getData();
            switch (promotion.getType()) {
                case 1://注册会员
                    setImageView("额外推荐收益", "商学院VIP课程", null, R.mipmap.vip_ewaishouyi, R.mipmap.vip_kecheng, 0);
                    hideImageView();
                    showImageView(2);
                    //任务状态
                    isCheckWXState(data.getCheckWXState(), vip_logon_image);
                    isInvitationDisplay(data.getInvitationIsDisplay(), vip_logon_invitation);
                    isOrderDisplay(data.getOrderIsDisplay(), vip_logon_extension, vip_logon_extension2);
                    //是否可以升级
                    isDisplay(data.getIsDisplay1(), data.getIsDisplay2(), vip_logon_up);
                    //升级按钮
                    hideUpVip();
                    showUpVip(1);
                    break;
                case 2://超级会员
                    setImageView("佣金提高57%", "额外团队收益", "商学院所有课程", R.mipmap.vip_yongjin, R.mipmap.vip_ewaishouyi, R.mipmap.vip_kecheng2);
                    hideImageView();
                    showImageView(3);
                    //任务状态
                    isInvitationDisplay(data.getInvitationIsDisplay(), vip_v1_invitation);
                    isActivityDisplay(data.getActivityIsDisplay(), vip_v1_extension);
                    //是否可以升级
                    isDisplay(data.getIsDisplay(), state2, vip_v1_up);
                    //升级按钮
                    hideUpVip();
                    showUpVip(2);
                    break;
                case 3://大团长v1
                    setImageView("团队收益提高10%", null, null, R.mipmap.vip_v1_comm, 0, 0);
                    hideImageView();
                    showImageView(1);
                    //任务状态
                    isInvitationDisplay(data.getInvitationIsDisplay(), vip_v2_invitation);
                    isActivityDisplay(data.getActivityIsDisplay(), vip_v2_extension);
                    //是否可以升级
                    isDisplay(data.getIsDisplay(), state2, vip_v2_up);
                    //升级按钮
                    hideUpVip();
                    showUpVip(3);
                    break;
                case 4://大团长v2
                    setImageView("团队收益提高5%", null, null, R.mipmap.vip_v2_comm, 0, 0);
                    hideImageView();
                    showImageView(1);
                    //任务状态
                    isInvitationDisplay(data.getInvitationIsDisplay(), vip_v2_invitation);
                    isActivityDisplay(data.getActivityIsDisplay(), vip_v2_extension);
                    //是否可以升级
                    isDisplay(data.getIsDisplay(), state2, vip_v2_up);
                    //升级按钮
                    hideUpVip();
                    showUpVip(3);
                    break;
                case 5://大团长v3
                    setImageView("团队收益提高2.5%", null, null, R.mipmap.vip_v3_comm, 0, 0);
                    hideImageView();
                    showImageView(1);
                    //任务状态
                    isInvitationDisplay(data.getInvitationIsDisplay(), vip_v2_invitation);
                    isActivityDisplay(data.getActivityIsDisplay(), vip_v2_extension);
                    //是否可以升级
                    isDisplay(data.getIsDisplay(), state2, vip_v2_up);
                    //升级按钮
                    hideUpVip();
                    showUpVip(3);
                    break;
                case 6://大团长v4
                    setImageView("平台额外5%佣金补贴", "独立运营管理后台", null, R.mipmap.vip_v2_comm, R.mipmap.vip_admin_, 0);
                    hideImageView();
                    showImageView(2);
                    //任务状态
                    isInvitationDisplay(data.getInvitationIsDisplay(), vip_partner_invitation);
                    isActivityDisplay(data.getActivityIsDisplay(), vip_partner_extension);
                    //是否可以升级
                    isDisplay(data.getIsDisplay(), state2, vip_partner_up);
                    //升级按钮
                    hideUpVip();
                    showUpVip(4);
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
            Glide.with(this).setDefaultRequestOptions(requestOptions).load(data.getParentIconUrl()).into(mVipServiceHead);
            mVipServiceName.setText(data.getParentNickName());
            mVipServiceJue.setText(data.getParentRole());
            mVipServiceCode.setText("邀请码：" + data.getParentInviteCode());
            wxImage = data.getParentWXCode();
            Glide.with(this).load(data.getParentWXCode()).into(mVipServiceImage);

            mVipServiceWx.setText("微信号：" + data.getParentWXId());
            switch (promotion.getType()) {
                case 1://注册会员
                    setTask(mVipLogonOneText, mVipLogonBarOne, mVipLogonTextOne, "邀请" + data.getTotalNumber() + "名注册会员",
                            data.getFansNum() + "/" + data.getTotalNumber() + "人", data.getTotalNumber(), data.getFansNum());
                    setTask(mVipLogonThreeText, mVipLogonBarTwo, mVipLogonTextTwo, "完成" + data.getOrderTotalNumber() + "单（0元购除外）",
                            data.getOrderNumber() + "/" + data.getOrderTotalNumber() + "单", data.getOrderTotalNumber(),
                            data.getOrderNumber());
                    vip_logon_wx.setText("建立" + data.getWeChatGroupNumber() + "人以上微信群");
                    setTask(mVipLogonThreeText2, mVipLogonBarTwo2, mVipLogonTextTwo2, "完成" + data.getOrderTotalNumber() + "单（0元购除外）",
                            data.getOrderNumber() + "/" + data.getOrderTotalNumber() + "单", data.getOrderTotalNumber(),
                            data.getOrderNumber());
                    break;
                case 2://超级会员
                    setTask(vip_v1_one_text, vip_v1_bar_one, vip_v1_text_one, "直邀超级会员" + data.getTotalNumber() + "人以上",
                            data.getFansNum() + "/" + data.getTotalNumber() + "人", data.getTotalNumber(), data.getFansNum());
                    setTask(vip_v1_two_text, vip_v1_bar_two, vip_v1_text_two, "直邀和间接超级会员" + data.getTwoLevelTotalNumber() + "人以上",
                            data.getTwoLevelFans() + "/" + data.getTwoLevelTotalNumber() + "人", data.getTwoLevelTotalNumber(),
                            data.getTwoLevelFans());
                    setTask(vip_v1_three_text, vip_v1_bar_three, vip_v1_text_three, "活跃度" + data.getActivityLevelTotalNumber() + "以上",
                            data.getActivityLevel() + "/" + data.getActivityLevelTotalNumber(), data.getActivityLevelTotalNumber(),
                            data.getActivityLevel());
                    break;
                case 3://大团长v1
                case 4://大团长v2
                case 5://大团长v3
                    setTask(vip_v2_one_text, vip_v2_bar_one, vip_v2_text_one, "团队超级会员" + data.getTeamSuperMemberTotal() + "人以上",
                            data.getSuperMemNumber() + "/" + data.getTeamSuperMemberTotal() + "人", data.getTeamSuperMemberTotal(),
                            data.getSuperMemNumber());
                    setTask(vip_v2_two_text, vip_v2_bar_two, vip_v2_text_two, "活跃度" + data.getActivityLevelTotalNumber() + "以上",
                            data.getActivityLevel() + "/" + data.getActivityLevelTotalNumber(), data.getActivityLevelTotalNumber(),
                            data.getActivityLevel());
                    break;
                case 6://大团长v4
                    setTask(vip_partner_one_text1, vip_partner_bar_one1, vip_partner_text_one1, "直邀超级会员" + data.getClassAVipNum() + "人以上",
                            data.getFansNum() + "/" + data.getClassAVipNum() + "人", data.getClassAVipNum(), data.getFansNum());
                    setTask(vip_partner_one_text2, vip_partner_bar_one2, vip_partner_text_one2,
                            "团队超级会员" + data.getTeamSuperMemberTotal() + "人以上",
                            data.getSuperMemNumber() + "/" + data.getTeamSuperMemberTotal() + "人", data.getTeamSuperMemberTotal(),
                            data.getSuperMemNumber());
                    setTask(vip_partner_two_text, vip_partner_bar_two, vip_partner_text_two,
                            "活跃度" + data.getActivityLevelTotalNumber() + "以上",
                            data.getActivityLevel() + "/" + data.getActivityLevelTotalNumber(), data.getActivityLevelTotalNumber(),
                            data.getActivityLevel());
                    break;
            }
//            mVipSercice.setVisibility(View.VISIBLE);
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

    private void showPopUp(int type) {
        OpenGoodsDetail.lightoff(this);
        View view;
        if (type == 0) {
            view = LayoutInflater.from(this).inflate(R.layout.dialog_upgrade, null);
        } else {
            view = LayoutInflater.from(this).inflate(R.layout.dialog_super_upgrade, null);
        }
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
                if (Type_Vip == 6) {
                    finish();
                } else {
                    mVippresenter.start(VipExplainActivity.this);
                }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new VipUpgrade());
    }

    private void isInvitationDisplay(int state, TextView textView) {
        if (state == 1) {
            invitation = false;
            textView.setText("已完成");
            textView.setBackground(getResources().getDrawable(R.drawable.bg_vip_success));
        } else {
            invitation = true;
            textView.setText("去邀请");
        }
    }

    private void isDisplay(int state, int state2, TextView textView) {
        if (state == 1 || state2 == 1) {
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void isActivityDisplay(int state, TextView textView) {
        if (state == 1) {
            extension = false;
            textView.setText("已完成");
            textView.setBackground(getResources().getDrawable(R.drawable.bg_vip_success));
        } else {
            extension = true;
            textView.setText("去推广");
        }
    }

    private void isOrderDisplay(int state, TextView textView, TextView textView2) {
        if (state == 1) {
            extension = false;
            textView.setText("已完成");
            textView2.setText("已完成");
            textView.setBackground(getResources().getDrawable(R.drawable.bg_vip_success));
            textView2.setBackground(getResources().getDrawable(R.drawable.bg_vip_success));
        } else {
            extension = true;
            textView.setText("去推广");
            textView2.setText("去推广");
        }
    }

    private void isCheckWXState(int state, TextView textView) {
        if (state == 0) {
            checkImage = false;
            textView.setText("审核中");
            textView.setBackground(getResources().getDrawable(R.drawable.bg_vip_success));
        } else if (state == 1) {
            checkImage = false;
            textView.setText("已完成");
            textView.setBackground(getResources().getDrawable(R.drawable.bg_vip_success));
        } else if (state == 2) {
            checkImage = true;
            textView.setText("审核失败");
        } else if (state == 4) {
            checkImage = true;
            textView.setText("上传截图");
        }
    }

    private void setImageView(String equity, String equity2, String equity3, int drawable, int drawable2, int drawable3) {
        if (!TextUtils.isEmpty(equity)) {
            mVipEquityText1.setText(equity);
            mVipEquityImage1.setImageDrawable(getResources().getDrawable(drawable));
        }
        if (!TextUtils.isEmpty(equity2)) {
            mVipEquityText2.setText(equity2);
            mVipEquityIamge2.setImageDrawable(getResources().getDrawable(drawable2));
        }
        if (!TextUtils.isEmpty(equity3)) {
            mVipEquityText3.setText(equity3);
            mVipEquityImage3.setImageDrawable(getResources().getDrawable(drawable3));
        }
    }

    private void showImageView(int state) {
        switch (state) {
            case 1:
                mVipEquity1.setVisibility(View.VISIBLE);
                break;
            case 2:
                mVipEquity1.setVisibility(View.VISIBLE);
                mVipEquity2.setVisibility(View.VISIBLE);
                break;
            case 3:
                mVipEquity1.setVisibility(View.VISIBLE);
                mVipEquity2.setVisibility(View.VISIBLE);
                mVipEquity3.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void hideImageView() {
        mVipEquity1.setVisibility(View.GONE);
        mVipEquity2.setVisibility(View.GONE);
        mVipEquity3.setVisibility(View.GONE);
    }

    private void showUpVip(int state) {
        switch (state) {
            case 1:
                mVipViewLogon2.setVisibility(View.VISIBLE);
                break;
            case 2:
                mVipViewV1.setVisibility(View.VISIBLE);
                break;
            case 3:
                mVipViewV2.setVisibility(View.VISIBLE);
                break;
            case 4:
                mVipViewPartner.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void hideUpVip() {
        mVipViewLogon2.setVisibility(View.GONE);
        mVipViewV1.setVisibility(View.GONE);
        mVipViewV2.setVisibility(View.GONE);
        mVipViewPartner.setVisibility(View.GONE);
    }

    private void setTask(TextView textView, ProgressBar progressBar, TextView textView2, String value, String value2, int max,
                         int progress) {
        textView.setText(value);
        progressBar.setMax(max);
        progressBar.setProgress(progress);
        textView2.setText(value2);
    }

    public void seeImage(String position) {
        final List<ImageInfo> imageInfoList = new ArrayList<>();
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setOriginUrl(position);// 原图url
        imageInfo.setThumbnailUrl(position);// 缩略图url
        imageInfoList.add(imageInfo);
        ImagePreview.LoadStrategy loadStrategy = ImagePreview.LoadStrategy.Default;
        ImagePreview
                .getInstance()
                .setContext(VipExplainActivity.this)
                .setIndex(0)
                .setImageInfoList(imageInfoList)
                .setLoadStrategy(loadStrategy)
                .setEnableDragClose(true)
                .setEnableUpDragClose(true)
                .setShowDownButton(true)
                .start();
    }
}

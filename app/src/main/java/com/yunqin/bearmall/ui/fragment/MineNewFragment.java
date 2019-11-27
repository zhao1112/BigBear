package com.yunqin.bearmall.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.newreward.SpecialRequestUI.SpecialRequestActivity;
import com.newreward.privilegeUI.ActivityPrivilege;
import com.newversions.CreditCardActivity;
import com.newversions.IAdvClick;
import com.newversions.ServiceActivity;
import com.newversions.help.HelpActivity;
import com.newversions.push.MyPushActivity;
import com.newversions.tbk.activity.MyTBKCollectionActivity;
import com.newversions.tbk.activity.ShareComissionActivity;
import com.newversions.util.SharedPreferencesManager;
import com.newversions.view.ICustomDialog;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.BannerBean;
import com.yunqin.bearmall.bean.DayliTaskBCInfo;
import com.yunqin.bearmall.bean.MessageItemCount;
import com.yunqin.bearmall.bean.MineBannerBean;
import com.yunqin.bearmall.bean.OrderNumberBean;
import com.yunqin.bearmall.bean.PopBean;
import com.yunqin.bearmall.bean.UserBTInfo;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.eventbus.PopWindowEvent;
import com.yunqin.bearmall.ui.activity.AddressActivity;
import com.yunqin.bearmall.ui.activity.BankCardActivity;
import com.yunqin.bearmall.ui.activity.InformationFragmentActivity;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.MineOrderActivity;
import com.yunqin.bearmall.ui.activity.MyAllCommentActivity;
import com.yunqin.bearmall.ui.activity.OpenVipActivity;
import com.yunqin.bearmall.ui.activity.PropertyActivity;
import com.yunqin.bearmall.ui.activity.RefundActivity;
import com.yunqin.bearmall.ui.activity.RenewVipActivity;
import com.yunqin.bearmall.ui.activity.SettingActivity;
import com.yunqin.bearmall.ui.activity.VipCenterActivity;
import com.yunqin.bearmall.ui.activity.ZanWeiKaiFangActivity;
import com.yunqin.bearmall.ui.activity.ZeroMoneyDetailsActivity;
import com.yunqin.bearmall.ui.fragment.contract.MineContract;
import com.yunqin.bearmall.ui.fragment.presenter.MinePresenter;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.DialogUtils;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.DotView;
import com.yunqin.bearmall.widget.MineVipItemView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MineNewFragment extends BaseFragment implements MineContract.UI {

    @BindView(R.id.head_image)
    ImageView headImage;
    @BindView(R.id.user_name)
    TextView userNameView;
    @BindView(R.id.vip_container)
    View vipContainer;
    @BindView(R.id.vip_left_days)
    TextView vipLeftDaysView;
    @BindView(R.id.login_text)
    TextView loginText;
    @BindView(R.id.vip_button)
    Button vip_button;
    @BindView(R.id.vip_top_text)
    TextView vipTopText;
    @BindView(R.id.vip_mark)
    TextView vipMarkView;
    @BindView(R.id.renew)
    TextView renewView;
    @BindView(R.id.head_vip_icon)
    ImageView vipIcon;
    @BindView(R.id.invite_container)
    View inviteContainer;
    @BindView(R.id.open_vip_container)
    View openViewContainer;

    @BindView(R.id.head_invite_count)
    TextView headInviteCountText;
    @BindView(R.id.head_invite_now)
    TextView inviteNowText;


    //订单数量显示
    @BindView(R.id.daifukuan_number)
    TextView daifukuan_number;
    @BindView(R.id.daifahuo_number)
    TextView daifahuo_number;
    @BindView(R.id.daishouhuo_number)
    TextView daishouhuo_number;
    @BindView(R.id.daipingjia_number)
    TextView daipingjia_number;
    @BindView(R.id.tuihuo_number)
    TextView tuihuo_number;

    @BindView(R.id.tip)
    TextView tip;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.persion)
    RelativeLayout persion;
    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.look_all_order)
    LinearLayout lookAllOrder;
    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.layout_wait_pay)
    LinearLayout layoutWaitPay;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.layout_wait_send_out_goods)
    LinearLayout layoutWaitSendOutGoods;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.layout_wait_get_goods)
    LinearLayout layoutWaitGetGoods;
    @BindView(R.id.image4)
    ImageView image4;
    @BindView(R.id.layout_wait_comment)
    LinearLayout layoutWaitComment;
    @BindView(R.id.image5)
    ImageView image5;
    @BindView(R.id.layout_after_sale)
    LinearLayout layoutAfterSale;
    @BindView(R.id.container_wallet)
    MineVipItemView containerWallet;
    @BindView(R.id.container_bank_card)
    MineVipItemView containerBankCard;
    @BindView(R.id.container_xin_yong_ka)
    MineVipItemView containerXinYongKa;
    @BindView(R.id.container_tg)
    MineVipItemView containerTg;
    @BindView(R.id.container_game_center)
    MineVipItemView containerGameCenter;
    @BindView(R.id.container_fav)
    MineVipItemView containerFav;
    @BindView(R.id.container_comment)
    MineVipItemView containerComment;
    @BindView(R.id.container_address)
    MineVipItemView containerAddress;
    @BindView(R.id.container_help)
    MineVipItemView containerHelp;
    @BindView(R.id.container_im)
    MineVipItemView containerIm;
    Unbinder unbinder;


    private long lastupdateTime;

    private MineContract.Presenter presenter;

    private RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.mine_user_icon_defult)
            .error(R.drawable.mine_user_icon_defult)
            .centerCrop();


    @Override
    public int layoutId() {
        return R.layout.fragment_new_mine;
    }

    @Override
    public void init() {

        EventBus.getDefault().register(this);

        presenter = new MinePresenter(this);
        setDarkStatusIcon(true);
        setViewStatus();
    }


    private boolean isVisibility = false;


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventGet(PopWindowEvent event) {

        if (isVisibility) {
            presenter.initAdData(getActivity());
        }
    }

    @Override
    public void resultAdData(String key, String value, String data) {

        PopBean popBean = new Gson().fromJson(data, PopBean.class);
        if (popBean.getData() != null && popBean.getData().getPopupAd() != null
                && popBean.getData().getPopupAd().getImg() != null) {

            SharedPreferencesManager.setParam(getActivity(), key, value);

            new ICustomDialog.Builder(getActivity())
                    // 设置布局
                    .setLayoutResId(R.layout.dialog_first_ad)
                    // 点击空白是否消失
                    .setCanceledOnTouchOutside(false)
                    // 点击返回键是否消失
                    .setCancelable(true)
                    // 设置Dialog的绝对位置
                    .setDialogPosition(Gravity.CENTER)
                    // 设置自定义动画
                    .setAnimationResId(R.style.CenterAnimation)
                    .setWindow(true)
                    // 设置监听ID
                    .setListenedItems(new int[]{R.id.ad_img, R.id.ad_close})
                    .setImageViewResource(R.id.ad_img, popBean.getData().getPopupAd().getImg())
                    // 设置回掉
                    .setOnDialogItemClickListener((thisDialog, clickView) -> {
                        thisDialog.dismiss();
                        if (clickView.getId() == R.id.ad_img) {
                            IAdvClick.click(getActivity(), popBean.getData().getPopupAd().getType(),
                                    popBean.getData().getPopupAd().getSkipType(), popBean.getData().getPopupAd().getSource_id(),
                                    popBean.getData().getPopupAd().getAdUrl());
                            //TODO[点击广告]
                            ConstantScUtil.showAd();
                        }
                    }).build().show();
        }


    }

    @Override
    public void onResume() {
        super.onResume();

        if (isVisibility) {
            presenter.initAdData(getActivity());

            requestData();

        }

        if (BearMallAplication.getInstance().getUser() != null) {
            presenter.getTaskInfoData(getActivity());
            setViewStatus();
            if ((Boolean) SharedPreferencesHelper.get(getActivity(), "isFirstBind", false)) {
                SharedPreferencesHelper.put(getActivity(), "isFirstBind", false);
                DialogUtils.newUserRegiestGet(getActivity());
            }
        } else {
            setViewStatus();

            setOrderNumber(daifukuan_number, 0);
            setOrderNumber(daifahuo_number, 0);
            setOrderNumber(daishouhuo_number, 0);
            setOrderNumber(daipingjia_number, 0);
            setOrderNumber(tuihuo_number, 0);


        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        isVisibility = !hidden;
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setViewStatus();
            requestData();
            presenter.initAdData(getActivity());
        }

    }

    public void setViewStatus() {

        UserInfo userInfo = BearMallAplication.getInstance().getUser();
        if (userInfo != null) {
            userNameView.setVisibility(View.VISIBLE);
            loginText.setVisibility(View.GONE);
            setUserInfo(userInfo);
            presenter.getOrderNumberInfo(getActivity());
            tvCode.setVisibility(View.VISIBLE);
            tvCopy.setVisibility(View.VISIBLE);
        } else {
            vipIcon.setVisibility(View.GONE);
            userNameView.setVisibility(View.INVISIBLE);
            vipContainer.setVisibility(View.INVISIBLE);
            loginText.setVisibility(View.VISIBLE);
            tvCode.setVisibility(View.GONE);
            tvCopy.setVisibility(View.GONE);
            vip_button.setVisibility(View.VISIBLE);
            vip_button.setText("立即开通");
            vip_button.setTag(0);
            tip.setVisibility(View.VISIBLE);
            vipTopText.setText("大熊会员更多权益供您使用");
            tip.setText("半年立即可赚1688元");
            inviteContainer.setVisibility(View.GONE);
            Glide.with(this).setDefaultRequestOptions(requestOptions).load("error").into(headImage);
        }
    }

    private void setUserInfo(UserInfo userInfo) {
        long currentTime = System.currentTimeMillis();
        if (vip_button.getVisibility() != View.VISIBLE) {
            vip_button.setVisibility(View.VISIBLE);
        }
        if (currentTime - lastupdateTime > 30 * 60 * 1000) {
            presenter.updateUserInfo(getActivity());
        } else {
            UserInfo.DataBean.MemberBean dataBean = userInfo.getData().getMember();
            if (dataBean.isMember()) {
                vipContainer.setVisibility(View.VISIBLE);
                vipMarkView.setBackgroundResource(R.drawable.shape_vip_mark);
                vipLeftDaysView.setText(String.format("剩余%d天，", dataBean.getRestDays()));
                vipIcon.setVisibility(View.VISIBLE);
                if (dataBean.getSpecInviteUsableCount() > 0) {
                    inviteContainer.setVisibility(View.VISIBLE);
                } else {
                    inviteContainer.setVisibility(View.GONE);
                }
                String unUserCount = "本月剩余<font color=\"#E3A832\">" + dataBean.getRestPrivilegeCount() + "项特权</font>未使用";
                vipTopText.setText(Html.fromHtml(unUserCount));
                tip.setVisibility(View.GONE);
                vip_button.setText("立即查看");
                vip_button.setTag(1);
                int count = dataBean.getSpecInviteUsableCount();
                String textStr = "本次活动您还可邀请<font color=\"#E75B56\">" + count + "</font>名好友特价成为会员，";
                headInviteCountText.setText(Html.fromHtml(textStr));
            } else {
                inviteContainer.setVisibility(View.GONE);
                if (dataBean.isOpendMember()) {
                    vipIcon.setVisibility(View.VISIBLE);
                    vipContainer.setVisibility(View.VISIBLE);
                    vipMarkView.setBackgroundResource(R.drawable.shape_vip_gray_mark);
                    vipLeftDaysView.setText("剩余0天，");
                    tip.setVisibility(View.VISIBLE);
                    vipTopText.setText("大熊会员更多权益供您使用");
                    tip.setText("半年立即可赚1688元");
                    vip_button.setText("立即续费");
                } else {
                    vipContainer.setVisibility(View.INVISIBLE);
                    vipIcon.setVisibility(View.INVISIBLE);
                    tip.setVisibility(View.VISIBLE);
                    vipTopText.setText("大熊会员更多权益供您使用");
                    tip.setText("半年立即可赚1688元");
                    vip_button.setText("立即开通");
                }
            }
            userNameView.setText(userInfo.getData().getMember().getNickName());
            Glide.with(this).setDefaultRequestOptions(requestOptions).load(userInfo.getData().getMember().getIconUrl()).into(headImage);
            tvCode.setText("邀请码：" + userInfo.getRecommendCode());
        }
    }

    @OnClick({R.id.container_wallet, R.id.container_bank_card, R.id.container_tg, R.id.container_game_center
            , R.id.container_fav, R.id.container_comment, R.id.container_address, R.id.container_help, R.id.container_im,
            R.id.mine_setting, R.id.renew, R.id.vip_button, R.id.look_all_order, R.id.login_text, R.id.layout_wait_send_out_goods,
            R.id.layout_wait_pay, R.id.layout_wait_get_goods, R.id.layout_wait_comment, R.id.layout_after_sale,
            R.id.head_invite_now, R.id.container_xin_yong_ka, R.id.xiao_xi, R.id.tv_copy

    })
    public void onViewClick(View view) {

        switch (view.getId()) {

            case R.id.tv_copy:
                if (BearMallAplication.getInstance().getUser() != null) {
                    ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null,
                            BearMallAplication.getInstance().getUser().getRecommendCode()));
                    showToast("复制成功");
                }
                break;
            case R.id.xiao_xi:
                InformationFragmentActivity.start(getActivity());
                break;


            case R.id.container_xin_yong_ka:

                if (isUnLogin()) {
                    LoginActivity.starActivity(getActivity());
                } else {
                    CreditCardActivity.startActivity(getActivity());
                }


                break;

            case R.id.container_wallet:

                if (isUnLogin()) {
                    LoginActivity.starActivity(getActivity());
                } else {
                    PropertyActivity.startPropertyActivity(getActivity(), 1, null, null, null, null);
                }

                break;

            case R.id.container_bank_card:
                if (isUnLogin()) {
                    LoginActivity.starActivity(getActivity());
                } else {
                    BankCardActivity.startActivity(getActivity());
                }
                break;

            case R.id.container_tg:
                //推广
                if (isUnLogin()) {
                    LoginActivity.starActivity(getActivity());
                } else {
                    startActivity(new Intent(getActivity(), MyPushActivity.class));
                }

                break;

            case R.id.container_game_center:
                //游戏中心

                startActivity(new Intent(getActivity(), ZanWeiKaiFangActivity.class));

                break;

            case R.id.container_fav:
                //收藏
                if (isUnLogin()) {
                    LoginActivity.starActivity(getActivity());
                } else {
                    StarActivityUtil.starActivity(getActivity(), MyTBKCollectionActivity.class);
                }
                break;

            case R.id.container_comment:
                //评价
                if (isUnLogin()) {
                    LoginActivity.starActivity(getActivity());
                } else {
                    StarActivityUtil.starActivity(getActivity(), MyAllCommentActivity.class);
                }

                break;

            case R.id.container_address:
                //收货地址
                if (isUnLogin()) {
                    LoginActivity.starActivity(getActivity());
                } else {
                    StarActivityUtil.starActivity(getActivity(), AddressActivity.class);
                }


                break;

            case R.id.container_help:
                //帮助中心

                startActivity(new Intent(getActivity(), HelpActivity.class));

                break;

            case R.id.container_im:
                //在线客服

                ServiceActivity.start(getActivity());

                break;

            case R.id.mine_setting:
                //跳转设置

                if (isUnLogin()) {
                    LoginActivity.starActivity(getActivity());
                } else {
                    StarActivityUtil.starActivity(getActivity(), SettingActivity.class);
                }

                break;

            case R.id.look_all_order:
                //查看全部订单
                if (isUnLogin()) {
                    LoginActivity.starActivity(getActivity());
                } else {
                    StarActivityUtil.starActivity(getActivity(), MineOrderActivity.class);
                }

                break;

            case R.id.layout_wait_pay:
                //待付款
                if (isUnLogin()) {
                    LoginActivity.starActivity(getActivity());
                } else {
                    MineOrderActivity.start(getActivity(), MineOrderActivity.FragmentType.AWAIT_ZHIFU);
                }

                break;

            case R.id.layout_wait_send_out_goods:
                //待发货
                if (isUnLogin()) {
                    LoginActivity.starActivity(getActivity());
                } else {
                    MineOrderActivity.start(getActivity(), MineOrderActivity.FragmentType.AWAIT_FAHUO);
                }

                break;

            case R.id.layout_wait_get_goods:
                //待收货
                if (isUnLogin()) {
                    LoginActivity.starActivity(getActivity());
                } else {
                    MineOrderActivity.start(getActivity(), MineOrderActivity.FragmentType.AWAIT_SHOUHUO);
                }

                break;

            case R.id.layout_wait_comment:
                //待评价
                if (isUnLogin()) {
                    LoginActivity.starActivity(getActivity());
                } else {
                    MineOrderActivity.start(getActivity(), MineOrderActivity.FragmentType.AWAIT_PINGJIA);
                }

                break;

            case R.id.layout_after_sale:
                //退货
                if (isUnLogin()) {
                    LoginActivity.starActivity(getActivity());
                } else {
                    StarActivityUtil.starActivity(getActivity(), RefundActivity.class);
                }

                break;

            case R.id.login_text:

                LoginActivity.starActivity(getActivity());

                break;

            case R.id.vip_button:
                try {
                    int tag = (int) view.getTag();
                    if (tag == 1) {
                        Intent mIntent = new Intent(getActivity(), ActivityPrivilege.class);
                        startActivity(mIntent);
                    } else {
//                        Intent intent = new Intent(getActivity(), VipCenterActivity.class);
//                        startActivity(intent);
                        jump2VipActivity();
                    }
                } catch (Exception e) {
//                    Intent intent = new Intent(getActivity(), VipCenterActivity.class);
//                    startActivity(intent);
                    jump2VipActivity();
                }
                break;

            case R.id.head_invite_now:

                invite();

                break;

            case R.id.renew:

                startRenewActivity();

                break;


        }

    }

    private void startRenewActivity() {

//        Intent intent = new Intent(getActivity(),RenewVipActivity.class);
        RenewVipActivity.startRenewVipActivity(getActivity(), null, null);

    }

    private void invite() {

        UserInfo userInfo = BearMallAplication.getInstance().getUser();
        UserInfo.DataBean.MemberBean dataBean = userInfo.getData().getMember();
        if (dataBean.getSpecInviteUsableCount() > 0) {
            startActivity(new Intent(getActivity(), SpecialRequestActivity.class));
        } else {
            showToast("暂无可用名额");
        }


    }

    public void setOrderNumber(TextView textView, int number) {
        if (number > 0) {
            textView.setVisibility(View.VISIBLE);
            if (number < 100) {
                textView.setText(number + "");
            } else {
                textView.setText("99+");
            }
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    public void attachUserBTInfo(UserBTInfo userBTInfo) {

    }

    @Override
    public void initBannerTop(BannerBean bannerBean) {

    }

    @Override
    public void initTaskInfo(String taskInfo) {

        DayliTaskBCInfo dayliTaskBCInfo = new Gson().fromJson(taskInfo, DayliTaskBCInfo.class);
        if (dayliTaskBCInfo.getData().getIsSignToday() == 1) {

        } else {
            if (BearMallAplication.getInstance().getUser() != null) {
                DialogUtils.signInDialog(getActivity());
            }
        }

    }

    @Override
    public void initOrderNumberInfo(String orderNumberInfo) {

        try {
            OrderNumberBean orderNumberBean = new Gson().fromJson(orderNumberInfo, OrderNumberBean.class);
            OrderNumberBean.DataBean.OrdersNumRecordBean ordersNumRecordBean = orderNumberBean.getData().getOrdersNumRecord();
            setOrderNumber(daifukuan_number, ordersNumRecordBean.getWaitPayNum());
            setOrderNumber(daifahuo_number, ordersNumRecordBean.getWaitSendNum());
            setOrderNumber(daishouhuo_number, ordersNumRecordBean.getWaitReceiveNum());
            setOrderNumber(daipingjia_number, ordersNumRecordBean.getWaitAssNum());
            setOrderNumber(tuihuo_number, ordersNumRecordBean.getAfterSalesNum());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdateUserInfo(UserInfo userInfo) {
        lastupdateTime = System.currentTimeMillis();
        setUserInfo(userInfo);
    }

    @Override
    public void onProfit(Double todayprofit, Double cashAmount, Double thismonthprofit) {

    }

    @Override
    public void onLunboTu(MineBannerBean mineBannerBean) {

    }


    private boolean isUnLogin() {

        UserInfo userInfo = BearMallAplication.getInstance().getUser();

        return userInfo == null;
    }


    @BindView(R.id.dot_view)
    DotView dot_view;


    public void requestData() {
        Map timeMap = new HashMap();
        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime0", 0l) != 0l) {
            timeMap.put("lastTime0", (long) SharedPreferencesHelper.get(getActivity(), "lastTime0", 0l) + "");
        }
        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime1", 0l) != 0l) {
            timeMap.put("lastTime1", (long) SharedPreferencesHelper.get(getActivity(), "lastTime1", 0l) + "");
        }
        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime2", 0l) != 0l) {
            timeMap.put("lastTime2", (long) SharedPreferencesHelper.get(getActivity(), "lastTime2", 0l) + "");
        }
        if ((long) SharedPreferencesHelper.get(getActivity(), "lastTime3", 0l) != 0l) {
            timeMap.put("lastTime3", (long) SharedPreferencesHelper.get(getActivity(), "lastTime3", 0l) + "");
        }

        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getUnreadMessageCount(timeMap),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) {
                        MessageItemCount messageItemCount = new Gson().fromJson(data, MessageItemCount.class);
                        int count = messageItemCount.getData().getUnreadMessageCount();
                        dot_view.setShowNum(count);
                    }

                    @Override
                    public void onNotNetWork() {

                    }

                    @Override
                    public void onFail(Throwable e) {
                    }
                });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void jump2VipActivity() {
        UserInfo user = BearMallAplication.getInstance().getUser();
        if (user == null) {
            LoginActivity.starActivity(getActivity());
        } else {
            boolean member = BearMallAplication.getInstance().getUser().getData().getMember().isMember();
            boolean opendMember = BearMallAplication.getInstance().getUser().getData().getMember().isOpendMember();
            Log.i("jump2VipActivity", "jump2VipActivity: " + member);
            Log.i("jump2VipActivity", "jump2VipActivity: " + opendMember);
            if (member || opendMember) {
                RenewVipActivity.startRenewVipActivity(getActivity(), null, null);
            } else {
                OpenVipActivity.startOpenVipActivity(getActivity(), null, null);
            }
        }
    }
}

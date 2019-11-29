package com.yunqin.bearmall.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.newversions.IAdvClick;
import com.newversions.ServiceActivity;
import com.newversions.help.HelpActivity;
import com.newversions.tbk.activity.MyTBKCollectionActivity;
import com.newversions.tbk.activity.WebActivity;
import com.newversions.tbk.utils.BannerClicker;
import com.newversions.util.SharedPreferencesManager;
import com.newversions.view.ICustomDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
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
import com.yunqin.bearmall.ui.activity.FansActivity;
import com.yunqin.bearmall.ui.activity.InformationFragmentActivity;
import com.yunqin.bearmall.ui.activity.InvitationActivity2;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.MineOrderActivity;
import com.yunqin.bearmall.ui.activity.MyAllCommentActivity;
import com.yunqin.bearmall.ui.activity.OpenVipActivity;
import com.yunqin.bearmall.ui.activity.PropertyActivity;
import com.yunqin.bearmall.ui.activity.RenewVipActivity;
import com.yunqin.bearmall.ui.activity.SettingActivity;
import com.yunqin.bearmall.ui.fragment.contract.MineContract;
import com.yunqin.bearmall.ui.fragment.presenter.MinePresenter;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.DialogUtils;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.CircleImageView;
import com.yunqin.bearmall.widget.DotView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Master
 */
public class MineFragment extends BaseFragment implements MineContract.UI {

    @BindView(R.id.mine_head)
    CircleImageView mMineHead;
    @BindView(R.id.mine_nickname)
    TextView mMineNickname;
    @BindView(R.id.image_vip)
    ImageView mImageVip;
    @BindView(R.id.vip_icon)
    TextView mVipIcon;
    @BindView(R.id.mine_vip)
    RelativeLayout mMineVip;
    @BindView(R.id.mine_login)
    TextView mMineLogin;
    @BindView(R.id.mine_top)
    RelativeLayout mMineTop;
    @BindView(R.id.mine_code)
    TextView mMineCode;
    @BindView(R.id.mine_copy)
    TextView mMineCopy;
    @BindView(R.id.mine_news)
    LinearLayout mMineNews;
    @BindView(R.id.mine_set)
    ImageView mMineSet;
    @BindView(R.id.tuanz)
    ImageView mTuanz;
    @BindView(R.id.mine_commander)
    TextView mMineCommander;
    @BindView(R.id.mine_withdrawal_price)
    TextView mMineWithdrawalPrice;
    @BindView(R.id.mine_today_price)
    TextView mMineTodayPrice;
    @BindView(R.id.mine_month_price)
    TextView mMineMonthPrice;
    @BindView(R.id.mine_banner)
    Banner mBanner;
    @BindView(R.id.mine_vip_data)
    TextView mMineVipData;
    @BindView(R.id.code)
    RelativeLayout mCode;
    @BindView(R.id.mine_one)
    RelativeLayout mMineOne;
    @BindView(R.id.mine_regimental)
    RelativeLayout mMineRegimental;
    @BindView(R.id.mine_withdrawal)
    LinearLayout mMineWithdrawal;
    @BindView(R.id.mine_today)
    LinearLayout mMineToday;
    @BindView(R.id.mine_month)
    LinearLayout mMineMonth;
    @BindView(R.id.two)
    RelativeLayout mTwo;
    @BindView(R.id.mine_wallet)
    LinearLayout mMineWallet;
    @BindView(R.id.mine_order)
    LinearLayout mMineOrder;
    @BindView(R.id.mine_fraction)
    LinearLayout mMineFraction;
    @BindView(R.id.mine_share)
    LinearLayout mMineShare;
    @BindView(R.id.three)
    LinearLayout mThree;
    @BindView(R.id.mine_save)
    LinearLayout mMineSave;
    @BindView(R.id.mine_comment)
    LinearLayout mMineComment;
    @BindView(R.id.mine_address)
    LinearLayout mMineAddress;
    @BindView(R.id.mine_materiel)
    LinearLayout mMineMateriel;
    @BindView(R.id.mine_course)
    LinearLayout mMineCourse;
    @BindView(R.id.mine_problem)
    LinearLayout mMineProblem;
    @BindView(R.id.mine_secvice)
    LinearLayout mMineSecvice;
    @BindView(R.id.dot_view)
    DotView mDotView;
    @BindView(R.id.daifukuan_number)
    TextView mDaifukuanNumber;
    @BindView(R.id.openvip)
    RelativeLayout openvip;
    @BindView(R.id.four)
    LinearLayout mFour;
    @BindView(R.id.xis)
    RelativeLayout mXis;
    @BindView(R.id.open_vip_one)
    TextView mOpenVipOne;


    private RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.mine_user_icon_defult)
            .error(R.drawable.mine_user_icon_defult)
            .centerCrop();

    private long lastupdateTime;
    private MinePresenter mPresenter;
    private boolean isVisibility = false;
    private List<MineBannerBean.DataBean.PlatformBannerBean> mPlatformBanner;
    private static final String url = "https://cloud.video.taobao.com//play/u/1101907235/p/1/e/6/t/1/243540803180.mp4";
    private static final float RATIO = 1.725f;
    private static final float ONE = 2.02f;
    private static final float THREE = 4.10f;
    private static final float BANNER = 4.31f;
    private static final float FOUR = 2.24f;
    private static final float XIS = 2.33f;

    @Override
    public int layoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        setshowUI();
        mPresenter = new MinePresenter(this);
        initUserView();
        mBanner.isAutoPlay(true);
        mBanner.setDelayTime(3000);
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (mPlatformBanner != null && position >= 0 && position < mPlatformBanner.size()) {
                    //bannner点击处理
                    if (position >= 0 && position < mPlatformBanner.size()) {
                        BannerClicker.bannerClick(getActivity(), mPlatformBanner.get(position).getTargetType(),
                                mPlatformBanner.get(position).getTarget(), mPlatformBanner.get(position).getTitle());
                    }
                }
            }
        });
        mPresenter.onLunboTu(getActivity());
    }

    private void setshowUI() {

        mMineOne.post(new Runnable() {
            @Override
            public void run() {
                int width = mMineOne.getWidth();
                float mHeight = width / ONE;
                ViewGroup.LayoutParams layoutParams = mMineOne.getLayoutParams();
                layoutParams.width = width;
                layoutParams.height = (int) mHeight;
                mMineOne.setLayoutParams(layoutParams);
            }
        });

        mTwo.post(new Runnable() {
            @Override
            public void run() {
                int width = mTwo.getWidth();
                float mHeight = width / RATIO;
                ViewGroup.LayoutParams layoutParams = mTwo.getLayoutParams();
                layoutParams.width = width;
                layoutParams.height = (int) mHeight;
                mTwo.setLayoutParams(layoutParams);
            }
        });

//        mXis.post(new Runnable() {
//            @Override
//            public void run() {
//                int width = mXis.getWidth();
//                float mHeight = width / XIS;
//                ViewGroup.LayoutParams layoutParams = mXis.getLayoutParams();
//                layoutParams.width = width;
//                layoutParams.height = (int) mHeight;
//                mXis.setLayoutParams(layoutParams);
//            }
//        });

        mThree.post(new Runnable() {
            @Override
            public void run() {
                int width = mThree.getWidth();
                float mHeight = width / THREE;
                ViewGroup.LayoutParams layoutParams = mThree.getLayoutParams();
                layoutParams.width = width;
                layoutParams.height = (int) mHeight;
                mThree.setLayoutParams(layoutParams);
            }
        });


        mFour.post(new Runnable() {
            @Override
            public void run() {
                int width = mFour.getWidth();
                float mHeight = width / FOUR;
                ViewGroup.LayoutParams layoutParams = mFour.getLayoutParams();
                layoutParams.width = width;
                layoutParams.height = (int) mHeight;
                mFour.setLayoutParams(layoutParams);
            }
        });
    }

    private void initUserView() {
        UserInfo userInfo = BearMallAplication.getInstance().getUser();
        if (userInfo != null) {
            mCode.setVisibility(View.VISIBLE);
            mMineNickname.setVisibility(View.VISIBLE);
            mMineLogin.setVisibility(View.GONE);
            setVipData(userInfo);
            mPresenter.getOrderNumberInfo(getActivity());
            mPresenter.onProfit(getActivity());
        } else {
            mMineLogin.setVisibility(View.VISIBLE);
            mMineVip.setVisibility(View.GONE);
            mCode.setVisibility(View.GONE);
            mMineNickname.setVisibility(View.GONE);
            mMineVipData.setVisibility(View.GONE);
            mOpenVipOne.setVisibility(View.GONE);
            openvip.setVisibility(View.GONE);
            mMineWithdrawalPrice.setText("0.00");
            mMineTodayPrice.setText("0.00");
            mMineMonthPrice.setText("0.00");
            Glide.with(this).setDefaultRequestOptions(requestOptions).load("error").into(mMineHead);
        }
    }

    private void setVipData(UserInfo userInfo) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastupdateTime > 30 * 60 * 1000) {
            mPresenter.updateUserInfo(getActivity());
        } else {
            UserInfo.DataBean.MemberBean dataBean = userInfo.getData().getMember();
            if (dataBean.isMember()) {
                mMineVip.setVisibility(View.VISIBLE);
                mMineVipData.setVisibility(View.VISIBLE);
                openvip.setVisibility(View.GONE);
                mMineVipData.setText("剩余" + dataBean.getRestDays() + "天，");
                mOpenVipOne.setVisibility(View.VISIBLE);
            } else {
                mMineVip.setVisibility(View.GONE);
                mMineVipData.setVisibility(View.GONE);
                mOpenVipOne.setVisibility(View.GONE);
                openvip.setVisibility(View.VISIBLE);
            }
        }
        mMineNickname.setText(userInfo.getData().getMember().getNickName());
        Glide.with(this).setDefaultRequestOptions(requestOptions).load(userInfo.getData().getMember().getIconUrl()).into(mMineHead);
        mMineCode.setText(userInfo.getRecommendCode());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventGet(PopWindowEvent event) {
        if (isVisibility) {
            mPresenter.initAdData(getActivity());
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        isVisibility = !hidden;
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initUserView();
            requestData();
            mPresenter.initAdData(getActivity());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisibility) {
            mPresenter.initAdData(getActivity());
            requestData();
        }
        if (BearMallAplication.getInstance().getUser() != null) {
            mPresenter.getTaskInfoData(getActivity());
            initUserView();
            if ((Boolean) SharedPreferencesHelper.get(getActivity(), "isFirstBind", false)) {
                SharedPreferencesHelper.put(getActivity(), "isFirstBind", false);
                DialogUtils.newUserRegiestGet(getActivity());
            }
        } else {
            initUserView();
            setOrderNumber(mDaifukuanNumber, 0);
        }
    }

    public void setOrderNumber(TextView textView, int number) {
        if (number > 0) {
            if (number < 100) {
                textView.setText(number + "");
            } else {
                textView.setText("99+");
            }
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    private void requestData() {
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
                        mDotView.setShowNum(count);
                    }

                    @Override
                    public void onNotNetWork() {

                    }

                    @Override
                    public void onFail(Throwable e) {
                    }
                });
    }

    @OnClick({R.id.mine_vip_data, R.id.mine_copy, R.id.mine_news, R.id.mine_set, R.id.mine_withdrawal, R.id.mine_today, R.id.mine_month,
            R.id.mine_wallet, R.id.mine_order, R.id.mine_fraction, R.id.mine_share, R.id.mine_save, R.id.mine_comment, R.id.mine_address,
            R.id.mine_materiel, R.id.mine_send, R.id.mine_course, R.id.mine_problem, R.id.mine_secvice, R.id.mine_login, R.id.openvip,
            R.id.wallet_image, R.id.order_image, R.id.fans_image, R.id.share_image, R.id.open_vip_one})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_vip_data://续费
            case R.id.open_vip_one:
                RenewVipActivity.startRenewVipActivity(getActivity(), null, null);
                break;
            case R.id.mine_copy://复制
                if (BearMallAplication.getInstance().getUser() != null) {
                    ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null,
                            BearMallAplication.getInstance().getUser().getRecommendCode()));
                    showToast("复制成功");
                }
                break;
            case R.id.mine_news://通知
                InformationFragmentActivity.start(getActivity());
                break;
            case R.id.mine_set://设置
                if (BearMallAplication.getInstance().getUser() != null) {
                    StarActivityUtil.starActivity(getActivity(), SettingActivity.class);
                } else {
                    LoginActivity.starActivity(getActivity());
                }
                break;
            case R.id.mine_withdrawal:
                break;
            case R.id.mine_today:
                break;
            case R.id.mine_month:
                break;
            case R.id.mine_wallet://我的钱包
            case R.id.wallet_image:
                if (BearMallAplication.getInstance().getUser() != null) {
                    PropertyActivity.startPropertyActivity(getActivity(), 1, null, null, null, null);
                } else {
                    LoginActivity.starActivity(getActivity());
                }
                break;
            case R.id.mine_order://订单
            case R.id.order_image:
                if (BearMallAplication.getInstance().getUser() != null) {
                    StarActivityUtil.starActivity(getActivity(), MineOrderActivity.class);
                } else {
                    LoginActivity.starActivity(getActivity());
                }
                break;
            case R.id.mine_fraction://粉丝
            case R.id.fans_image:
//                if (BearMallAplication.getInstance().getUser() != null) {
//                    FansActivity.openFansActivity(getActivity(), FansActivity.class);
//                } else {
//                    LoginActivity.starActivity(getActivity());
//                }
                showToast("暂未开放");
                break;
            case R.id.mine_share://分享
            case R.id.share_image:
                if (BearMallAplication.getInstance().getUser() != null) {
                    StarActivityUtil.starActivity(getActivity(), InvitationActivity2.class);
                    //TODO[邀请好友]
                    ConstantScUtil.sensorsInviteFriends("我的页面：邀请好友");
                } else {
                    LoginActivity.starActivity(getActivity());
                }
                break;
            case R.id.mine_save://我收藏
                if (BearMallAplication.getInstance().getUser() != null) {
                    StarActivityUtil.starActivity(getActivity(), MyTBKCollectionActivity.class);
                } else {
                    LoginActivity.starActivity(getActivity());
                }
                break;
            case R.id.mine_comment://我的评价
                if (BearMallAplication.getInstance().getUser() != null) {
                    StarActivityUtil.starActivity(getActivity(), MyAllCommentActivity.class);
                } else {
                    LoginActivity.starActivity(getActivity());
                }
                break;
            case R.id.mine_address://收货地址
                if (BearMallAplication.getInstance().getUser() != null) {
                    StarActivityUtil.starActivity(getActivity(), AddressActivity.class);
                } else {
                    LoginActivity.starActivity(getActivity());
                }
                break;
            case R.id.mine_materiel://地推物料
                showToast("暂未开放");
                break;
            case R.id.mine_send://发圈文案
                showToast("暂未开放");
                break;
            case R.id.mine_course://新手教程
                WebActivity.startWebActivity(getActivity(), 200, url, "新手教程");
                break;
            case R.id.mine_problem://常见问题
                startActivity(new Intent(getActivity(), HelpActivity.class));
                break;
            case R.id.mine_secvice://联系客服
                ServiceActivity.start(getActivity());
                break;
            case R.id.mine_login:
                LoginActivity.starActivity(getActivity());
                break;
            case R.id.openvip://开通会员
                jump2VipActivity();
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void resultAdData(String key, String value, String data) {
        PopBean popBean = new Gson().fromJson(data, PopBean.class);
        if (popBean.getData() != null && popBean.getData().getPopupAd() != null && popBean.getData().getPopupAd().getImg() != null) {
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
            OrderNumberBean.DataBean.OrdersNumRecordBean ordersNumRecordBean = orderNumberBean.getData().getOrdersNumRecord();//
            int orderNumber =
                    ordersNumRecordBean.getWaitPayNum() + ordersNumRecordBean.getWaitSendNum() + ordersNumRecordBean.getWaitReceiveNum() + ordersNumRecordBean.getWaitAssNum();
            setOrderNumber(mDaifukuanNumber, orderNumber);
//            setOrderNumber(tuihuo_number, ordersNumRecordBean.getAfterSalesNum());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateUserInfo(UserInfo userInfo) {
        lastupdateTime = System.currentTimeMillis();
        setVipData(userInfo);
    }

    @Override
    public void onProfit(double todayprofit, double cashAmount, double thismonthprofit) {
        mMineWithdrawalPrice.setText(doubleToString(cashAmount));
        mMineTodayPrice.setText(doubleToString(todayprofit));
        mMineMonthPrice.setText(doubleToString(thismonthprofit));
        Log.i("onProfit", "onProfit: " + todayprofit + "cashAmount" + cashAmount + "thismonthprofit" + thismonthprofit);
    }

    public static String doubleToString(double num){
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }

    @Override
    public void onLunboTu(MineBannerBean mineBannerBean) {
        if (mineBannerBean.getData() != null && mineBannerBean.getData().getPlatformBanner().size() > 0 && mineBannerBean.getData().getPlatformBanner() != null) {
            mPlatformBanner = mineBannerBean.getData().getPlatformBanner();
            List<String> bannerList = new ArrayList<>();
            for (int i = 0; i < mPlatformBanner.size(); i++) {
                bannerList.add(mPlatformBanner.get(i).getImage());
            }
            mBanner.setImages(bannerList);
            mBanner.setImageLoader(new GlideImageLoader());
            mBanner.start();
            mBanner.setVisibility(View.VISIBLE);
            Log.i("GlideImageLoader", "onLunboTu: ");
            mBanner.post(new Runnable() {
                @Override
                public void run() {
                    int width = mBanner.getWidth();
                    float mHeight = width / BANNER;
                    ViewGroup.LayoutParams layoutParams = mBanner.getLayoutParams();
                    layoutParams.width = width;
                    layoutParams.height = (int) mHeight;
                    mBanner.setLayoutParams(layoutParams);
                }
            });
        } else {
            mBanner.setVisibility(View.GONE);
        }
    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            Glide.with(context)
                    .load(path)
                    .apply(new RequestOptions().placeholder(R.drawable.default_product))
                    .into(imageView);
        }
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

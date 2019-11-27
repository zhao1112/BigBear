package com.yunqin.bearmall.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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
import com.newversions.push.MyPushActivity;
import com.newversions.tbk.activity.MyTBKCollectionActivity;
import com.newversions.util.SharedPreferencesManager;
import com.newversions.view.ICustomDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.loader.ImageLoaderInterface;
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
import com.yunqin.bearmall.ui.activity.InformationFragmentActivity;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.MineOrderActivity;
import com.yunqin.bearmall.ui.activity.MyAllCommentActivity;
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
import com.yunqin.bearmall.widget.TopBanner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.banner)
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


    private RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.mine_user_icon_defult)
            .error(R.drawable.mine_user_icon_defult)
            .centerCrop();

    private long lastupdateTime;
    private MinePresenter mPresenter;
    private boolean isVisibility = false;
    private List<MineBannerBean.DataBean.PlatformBannerBean> mPlatformBanner;

    @Override
    public int layoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        immerseStatusBar();
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
                }
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
                String renew = "立即续费<font color=\"#FFE534\">";
                mMineVipData.setText(String.format("剩余%d天，" + Html.fromHtml(renew), dataBean.getRestDays()));
            } else {
                mMineVip.setVisibility(View.GONE);
                mMineVipData.setVisibility(View.GONE);
            }
        }
        mMineNickname.setText(userInfo.getData().getMember().getNickName());
        Glide.with(this).setDefaultRequestOptions(requestOptions).load(userInfo.getData().getMember().getIconUrl()).into(mMineHead);
        mMineCode.setText("邀请码：" + userInfo.getRecommendCode());
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
            R.id.mine_materiel, R.id.mine_send, R.id.mine_course, R.id.mine_problem, R.id.mine_secvice, R.id.mine_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_vip_data://续费
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
                if (BearMallAplication.getInstance().getUser() != null) {
                    PropertyActivity.startPropertyActivity(getActivity(), 1, null, null, null, null);
                } else {
                    LoginActivity.starActivity(getActivity());
                }
                break;
            case R.id.mine_order://订单
                if (BearMallAplication.getInstance().getUser() != null) {
                    StarActivityUtil.starActivity(getActivity(), MineOrderActivity.class);
                } else {
                    LoginActivity.starActivity(getActivity());
                }
                break;
            case R.id.mine_fraction:
                break;
            case R.id.mine_share:
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

                break;
            case R.id.mine_send://发圈文案
                showToast("抱歉！该工能暂未开放");
                break;
            case R.id.mine_course:
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
    public void onProfit(Double todayprofit, Double cashAmount, Double thismonthprofit) {
        mMineWithdrawalPrice.setText(cashAmount + "");
        mMineTodayPrice.setText(todayprofit + "");
        mMineMonthPrice.setText(thismonthprofit + "");
        Log.i("onProfit", "onProfit: " + todayprofit);
    }

    @Override
    public void onLunboTu(MineBannerBean mineBannerBean) {
        if (mineBannerBean.getData() != null && mineBannerBean.getData().getPlatformBanner().size() > 0 &&
                mineBannerBean.getData().getPlatformBanner() != null) {
            mPlatformBanner = mineBannerBean.getData().getPlatformBanner();
            List<String> bannerList = new ArrayList<>();
            for (int i = 0; i < mPlatformBanner.size(); i++) {
                bannerList.add(mPlatformBanner.get(i).getImage());
            }
            mBanner.setImages(bannerList);
            mBanner.setImageLoader(new GlideImageLoader());
            mBanner.start();
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

}

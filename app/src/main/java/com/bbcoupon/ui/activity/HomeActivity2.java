package com.bbcoupon.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bbcoupon.base.BaseActivity2;
import com.bbcoupon.widget.SwitchFragment2;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.newreward.SpecialRequestUI.SpecialRequestActivity;
import com.newreward.bean.SpecialRequest;
import com.newversions.tbk.activity.WebActivity;
import com.newversions.view.ICustomDialog;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.CartItemCount;
import com.yunqin.bearmall.bean.MessageItemCount;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.eventbus.ChangeFragment;
import com.yunqin.bearmall.eventbus.GetMessageEvent;
import com.yunqin.bearmall.eventbus.TrolleyCountEvent;
import com.yunqin.bearmall.service.BearMallIntentService;
import com.yunqin.bearmall.service.BearMallPushService;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.ui.activity.contract.HomeContract;
import com.yunqin.bearmall.ui.activity.presenter.HomePresenter;
import com.yunqin.bearmall.update.CheckForUpdateHelper;
import com.yunqin.bearmall.util.ConstUtils;
import com.yunqin.bearmall.util.SharedPreferencesHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.Jzvd;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/4/23
 */
public class HomeActivity2 extends BaseActivity2 implements RadioGroup.OnCheckedChangeListener, HomeContract.UI {

    @BindView(R.id.ho_group)
    RadioGroup mHoGroup;
    @BindView(R.id.content)
    FrameLayout mContent;
    @BindView(R.id.home)
    RadioButton mHome;
    @BindView(R.id.business)
    RadioButton mBusiness;
    @BindView(R.id.make)
    RadioButton mMake;
    @BindView(R.id.zeor)
    RadioButton mZeor;
    @BindView(R.id.mine)
    RadioButton mMine;

    private SwitchFragment2 mSwitchFragment;
    private HomePresenter presenter;
    private static HomeActivity2 homeActivity;
    private long firstPressedTime;

    @Override
    public int layoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void init() {
        mHoGroup.setOnCheckedChangeListener(this);

        mSwitchFragment = new SwitchFragment2(getSupportFragmentManager());
        mSwitchFragment.showFragment(1, R.id.content, HomeActivity2.this);

        BearMallAplication.initUM(HomeActivity2.this);
        presenter = new HomePresenter(this);
        // com.getui.demo.DemoPushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(this.getApplicationContext(), BearMallPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), BearMallIntentService.class);
        EventBus.getDefault().register(this);
        homeActivity = this;

        //判断是否登录
        if (BearMallAplication.getInstance().getUser() == null) {
            LoginActivity.starActivity(HomeActivity2.this);
        }

        //是否弹框
        try {
            UserInfo userInfo = ((BearMallAplication) getApplication()).getUser();
            if (userInfo != null && userInfo.getData().getMember().isMember()) {
                getSpecInvitationPageInfo();
            }
        } catch (Exception e) {

        }

        //暂时不知道干啥的
        Intent intent = getIntent();
        if (intent != null) {
            String content = intent.getStringExtra("switchFragment");
            if (content != null && content.equals("TrolleyFragment")) {
                selecd(4);
                mSwitchFragment.showFragment(4, R.id.content, HomeActivity2.this);
            } else {
                selecd(1);
                mSwitchFragment.showFragment(1, R.id.content, HomeActivity2.this);
            }
        } else {
            selecd(1);
            mSwitchFragment.showFragment(1, R.id.content, HomeActivity2.this);
        }

        String bearmall_url = intent.getStringExtra("bearmall_url");
        String type = intent.getStringExtra("type");
        String title = intent.getStringExtra("title");

        if (!TextUtils.isEmpty(bearmall_url) && !TextUtils.isEmpty(type)) {
            if ("1".equals(type)) {
                if (BearMallAplication.getInstance().getUser() != null) {
                    WebActivity.startWebActivity(HomeActivity2.this, ConstUtils.WEB_TYPE, bearmall_url, title);
                }
            }
        }

        //获取积分
        new CheckForUpdateHelper().checkForUpdate(this, 1);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        Log.e("onCheckedChanged", "onCheckedChanged: " + i);
        switch (i) {
            case R.id.home:
                mSwitchFragment.showFragment(1, R.id.content, HomeActivity2.this);
                break;
            case R.id.make:
                mSwitchFragment.showFragment(2, R.id.content, HomeActivity2.this);
                break;
            case R.id.business:
                mSwitchFragment.showFragment(3, R.id.content, HomeActivity2.this);
                break;
            case R.id.zeor:
                mSwitchFragment.showFragment(4, R.id.content, HomeActivity2.this);
                break;
            case R.id.mine:
                mSwitchFragment.showFragment(5, R.id.content, HomeActivity2.this);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getCartItemCount(this, new HashMap<>());
        requestData();
    }

    @Override
    public void getCartItemCount(String data) {
        //app启动获取购物车数量
        CartItemCount cartItemCount = new Gson().fromJson(data, CartItemCount.class);
        int count = cartItemCount.getData().getItemCount();
        Log.e("Product", count + "");
    }

    @Override
    public void getMessageItemCount(String data) {
        MessageItemCount messageItemCount = new Gson().fromJson(data, MessageItemCount.class);
        int count = messageItemCount.getData().getUnreadMessageCount();
    }

    @Override
    public void onFail(Throwable e) {

    }

    private void getSpecInvitationPageInfo() {
        RetrofitApi.request4(this, RetrofitApi.createApi(Api.class).getSpecInvitationPageInfo(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                SpecialRequest specialRequest = new Gson().fromJson(data, SpecialRequest.class);
                SpecialRequest.DataBean.SpecInvitationPageInfoBean specInvitationPageInfoBean =
                        specialRequest.getData().getSpecInvitationPageInfo();
                String title = String.format("您已获得特价邀请好友成为会员资格，立即邀请好友，共同享受会员权益!(本次可邀请%d位好友)",
                        specInvitationPageInfoBean.getSpecInviteUsableCount());
                String time = String.format("活动时间为%s—%s", specInvitationPageInfoBean.getActivityStartDate(),
                        specInvitationPageInfoBean.getActivityEndDate());
                try {
                    new ICustomDialog.Builder(HomeActivity2.this)
                            // 设置布局
                            .setLayoutResId(R.layout.join_member_layout1)
                            // 点击空白是否消失
                            .setCanceledOnTouchOutside(true)
                            // 点击返回键是否消失
                            .setCancelable(true)
                            // 设置Dialog的绝对位置
                            .setDialogPosition(Gravity.CENTER)
                            // 设置自定义动画
                            .setAnimationResId(0)
                            // 设置监听ID
                            .setListenedItems(new int[]{R.id.join_member_ok})
                            .setCustomTextIds(new int[]{R.id.title, R.id.time})
                            .setCustomTextContents(new String[]{title, time})
                            // 设置回掉
                            .setOnDialogItemClickListener(new ICustomDialog.OnDialogItemClickListener() {
                                @Override
                                public void onDialogItemClick(ICustomDialog thisDialog, View clickView) {
                                    startActivity(new Intent(HomeActivity2.this, SpecialRequestActivity.class));
                                    thisDialog.dismiss();
                                }
                            })
                            .build().show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNotNetWork() {
            }

            @Override
            public void onFail(Throwable e) {
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventGet(ChangeFragment changeFragment) {
        // TODO: 2018/7/28  待解决
        new Handler().postDelayed(() -> {
            selecd(changeFragment.getI());
            mSwitchFragment.showFragment(changeFragment.getI(), R.id.content, HomeActivity2.this);
        }, 800);
    }

    public void selecd(int id) {
        switch (id) {
            case 1:
                mHome.setChecked(true);
                break;
            case 2:
                mMake.setChecked(true);
                break;
            case 3:
                mBusiness.setChecked(true);
                break;
            case 4:
                mZeor.setChecked(true);
                break;
            case 5:
                mMine.setChecked(true);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void gETReceive(GetMessageEvent getMessageEvent) {
        requestData();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(TrolleyCountEvent messageEvent) {
        int count = messageEvent.getCount();
        Log.e("ProductMessageEvent", count + "");
    }

    public void requestData() {
        Map timeMap = new HashMap();
        if ((long) SharedPreferencesHelper.get(this, "lastTime0", 0l) != 0l) {
            timeMap.put("lastTime0", (long) SharedPreferencesHelper.get(this, "lastTime0", 0l) + "");
        }
        if ((long) SharedPreferencesHelper.get(this, "lastTime1", 0l) != 0l) {
            timeMap.put("lastTime1", (long) SharedPreferencesHelper.get(this, "lastTime1", 0l) + "");
        }
        if ((long) SharedPreferencesHelper.get(this, "lastTime2", 0l) != 0l) {
            timeMap.put("lastTime2", (long) SharedPreferencesHelper.get(this, "lastTime2", 0l) + "");
        }
        if ((long) SharedPreferencesHelper.get(this, "lastTime3", 0l) != 0l) {
            timeMap.put("lastTime3", (long) SharedPreferencesHelper.get(this, "lastTime3", 0l) + "");
        }
        presenter.getMessageItemCount(this, timeMap);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        if (System.currentTimeMillis() - firstPressedTime < 2000) {
            super.onBackPressed();
            System.exit(0);
        } else {
            showToast("再按一次退出");
            firstPressedTime = System.currentTimeMillis();
        }
    }

}

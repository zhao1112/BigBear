package com.yunqin.bearmall.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.newversions.view.ICustomDialog;
import com.newreward.SpecialRequestUI.SpecialRequestActivity;
import com.newreward.bean.SpecialRequest;
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
import com.yunqin.bearmall.ui.activity.contract.HomeContract;
import com.yunqin.bearmall.ui.activity.presenter.HomePresenter;
import com.yunqin.bearmall.update.CheckForUpdateHelper;
import com.yunqin.bearmall.util.RudenessScreenHelper;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.util.SwitchFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.jzvd.Jzvd;

/**
 * @author Master
 */
public class HomeActivity extends BaseActivity implements HomeContract.UI {

    @BindView(R.id.bbl)
    BottomBarLayout bottomBar;
    @BindView(R.id.shopcar_bar_item)
    BottomBarItem shopcar_bar_item;
    @BindView(R.id.content)
    FrameLayout content;

    private HomePresenter presenter;

    private static HomeActivity homeActivity;

    private SwitchFragment switchFragment;


    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    private boolean isInit = false;

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getCartItemCount(this, new HashMap<>());
        requestData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("FENGGOU", "onRestart");
    }

    @Override
    public void init() {
        //初始化友盟
        BearMallAplication.initUM(HomeActivity.this);

        presenter = new HomePresenter(this);
        // com.getui.demo.DemoPushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(this.getApplicationContext(), BearMallPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), BearMallIntentService.class);

        EventBus.getDefault().register(this);
        homeActivity = this;

        switchFragment = new SwitchFragment(getSupportFragmentManager());

        Intent intent = getIntent();
        if (intent != null) {
            String content = intent.getStringExtra("switchFragment");
            if (content != null && content.equals("TrolleyFragment")) {
                switchFragment.chooseFragment(SwitchFragment.FRAGMENT_TYPE.APP_TROLLEY);
            } else {
                switchFragment.chooseFragment(SwitchFragment.FRAGMENT_TYPE.APP_HOME);
                bottomBar.setCurrentItem(0);
            }
        } else {
            switchFragment.chooseFragment(SwitchFragment.FRAGMENT_TYPE.APP_HOME);
        }

        bottomBar.setOnItemSelectedListener((bottomBarItem, previousPosition, currentPosition) -> {

            if (currentPosition == 1) {
                if (BearMallAplication.getInstance().getUser() == null) {
                    LoginActivity.starActivity(HomeActivity.this);
                    new Handler().postDelayed(() -> {
                        bottomBar.setCurrentItem(previousPosition);
                    }, 800);

                    return;
                }
            }

            // previousPosition 如果等于  currentPosition  就相当于 在目前选中的图标上进行点击，  可进行刷新操作什么的
            if (switchFragment != null) {
                switchFragment.chooseFragment(currentPosition);
            }
        });

        try {
            UserInfo userInfo = ((BearMallAplication) getApplication()).getUser();
            if (userInfo != null && userInfo.getData().getMember().isMember()) {
                getSpecInvitationPageInfo();
            }
        } catch (Exception e) {
        }

        new CheckForUpdateHelper().checkForUpdate(this, 1);

        if (BearMallAplication.getInstance().getUser() == null) {
            LoginActivity.starActivity(HomeActivity.this);
        }

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
                new ICustomDialog.Builder(HomeActivity.this)
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
                                startActivity(new Intent(HomeActivity.this, SpecialRequestActivity.class));
                                thisDialog.dismiss();
                            }
                        })
                        .build().show();
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            String content = intent.getStringExtra("switchFragment");
            if (content != null && content.equals("TrolleyFragment")) {
                bottomBar.setCurrentItem(3);
                switchFragment.chooseFragment(SwitchFragment.FRAGMENT_TYPE.APP_TROLLEY);
            } else {
                bottomBar.setCurrentItem(0);
                switchFragment.chooseFragment(SwitchFragment.FRAGMENT_TYPE.APP_HOME);
            }
        }

    }

    public void switch2Fragment(int postion) {
        switch (postion) {
            case 0:
                bottomBar.setCurrentItem(0);
                switchFragment.chooseFragment(SwitchFragment.FRAGMENT_TYPE.APP_HOME);
                break;

            case 1:
                bottomBar.setCurrentItem(2);
                switchFragment.chooseFragment(SwitchFragment.FRAGMENT_TYPE.APP_RECOMMEND);
                break;

            case 2:
                bottomBar.setCurrentItem(1);
                switchFragment.chooseFragment(SwitchFragment.FRAGMENT_TYPE.APP_INFORMATION);
                break;
            case 3:
                bottomBar.setCurrentItem(3);
                switchFragment.chooseFragment(SwitchFragment.FRAGMENT_TYPE.APP_TROLLEY);
                break;
            default:
                break;

        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventGet(ChangeFragment changeFragment) {
        // TODO: 2018/7/28  待解决
        new Handler().postDelayed(() -> {
            switch2Fragment(changeFragment.getI());
        }, 800);

    }

    private long firstPressedTime;

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

    @Override
    public void getCartItemCount(String data) {
        //app启动获取购物车数量
        CartItemCount cartItemCount = new Gson().fromJson(data, CartItemCount.class);
        int count = cartItemCount.getData().getItemCount();
        Log.e("Product", count + "");
        //todo textview显示count即可
//        shopcar_bar_item.setUnreadNum(count);
    }

    @Override
    public void getMessageItemCount(String data) {
        MessageItemCount messageItemCount = new Gson().fromJson(data, MessageItemCount.class);
        int count = messageItemCount.getData().getUnreadMessageCount();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(TrolleyCountEvent messageEvent) {
        //购物车数量发生变化
        int count = messageEvent.getCount();
        Log.e("ProductMessageEvent", count + "");
        //todo textview显示count即可
//        shopcar_bar_item.setUnreadNum(count);
    }

    @Override
    public void onFail(Throwable e) {
        shopcar_bar_item.setUnreadNum(0);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void gETReceive(GetMessageEvent getMessageEvent) {
        requestData();
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        new RudenessScreenHelper(getApplication(), 750).activate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

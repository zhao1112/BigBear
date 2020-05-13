package com.bbcoupon.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bbcoupon.util.WindowUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.newversions.passwd.PwdActivity;
import com.newversions.passwd.SettingPwdActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.SettingBean;
import com.yunqin.bearmall.eventbus.PayPasswordEvent;
import com.yunqin.bearmall.eventbus.TrolleyCountEvent;
import com.yunqin.bearmall.inter.JoinZeroCallBack;
import com.yunqin.bearmall.ui.activity.AboutBearMall;
import com.yunqin.bearmall.ui.activity.SettingActivity;
import com.yunqin.bearmall.ui.activity.SugestionBack;
import com.yunqin.bearmall.ui.activity.contract.SettingContract;
import com.yunqin.bearmall.ui.activity.presenter.SettingPresenter;
import com.yunqin.bearmall.util.CacheClearManager;
import com.yunqin.bearmall.util.DialogUtils;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.CircleImageView;
import com.yunqin.bearmall.widget.SwitchButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/4/21
 */
public class SettingsActivity extends BaseActivity implements SettingContract.UI, JoinZeroCallBack, View.OnClickListener {

    @BindView(R.id.sett_head_img)
    CircleImageView mSettHeadImg;
    @BindView(R.id.set_switch)
    SwitchButton mSetSwitch;
    @BindView(R.id.set_cache)
    TextView mSetCache;

    private SettingPresenter settingPresenter;
    private SettingBean settingBean;
    private boolean isSetPayPwd = false;
    private String cacheSizeNumber = "";

    @Override
    public int layoutId() {
        return R.layout.activity_new_setting;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        settingPresenter = new SettingPresenter(this, this);

        //接收消息开关
        if (SharedPreferencesHelper.get(SettingsActivity.this, "isPush", "0").equals("0")) {
            mSetSwitch.setChecked(false);
        } else {
            mSetSwitch.setChecked(true);
        }
        mSetSwitch.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    SharedPreferencesHelper.put(SettingsActivity.this, "isPush", "1");
                    showToast("已关闭消息推送");
                    PushManager.getInstance().turnOffPush(SettingsActivity.this);
                } else {
                    SharedPreferencesHelper.put(SettingsActivity.this, "isPush", "0");
                    showToast("已打开消息推送");
                    PushManager.getInstance().turnOnPush(SettingsActivity.this);
                }
            }
        });
        //缓存
        try {
            cacheSizeNumber = CacheClearManager.getTotalCacheSize(SettingsActivity.this);
            mSetCache.setText(cacheSizeNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        settingPresenter.start(this);
    }

    @Override
    public void bindWx() {

    }

    @Override
    public void outSussess() {
        hiddenLoadingView();
        showToast("退出登录");
        BearMallAplication.getInstance().setNullUser();
        SharedPreferencesHelper.clearWhichOne(SettingsActivity.this, "isPush");
        EventBus.getDefault().post(new TrolleyCountEvent(0));
        finish();
    }

    @Override
    public void outFail() {
        hiddenLoadingView();
        showToast("退出失败");
    }

    @Override
    public void changeNickNameSussess() {

    }

    @Override
    public void attachData(String data) {
        try {
            settingBean = new Gson().fromJson(data, SettingBean.class);
            Glide.with(this)
                    .setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult))
                    .load(settingBean.getData().getInfo().getIconUrl())
                    .into(mSettHeadImg);
            isSetPayPwd = settingBean.getData().getInfo().isSetPayPwd();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFail() {

    }

    @OnClick({R.id.toolbar_back, R.id.sett_head, R.id.set_phone, R.id.set_balance, R.id.set_wipecache, R.id.set_feedback,
            R.id.set_about, R.id.set_sign_out})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.sett_head://设置个人资料
                if (settingBean != null && settingBean.getData().getInfo().getIconUrl() != null) {
                    Intent intent = new Intent(SettingsActivity.this, PersonalActivity.class);
                    intent.putExtra("Heald_Image", settingBean.getData().getInfo().getIconUrl());
                    startActivity(intent);
                }
                break;
            case R.id.set_phone://修改手机号

                break;
            case R.id.set_balance://余额密码
                if (isSetPayPwd) {
                    startActivity(new Intent(SettingsActivity.this, PwdActivity.class));
                } else {
                    startActivity(new Intent(SettingsActivity.this, SettingPwdActivity.class));
                }
                break;
            case R.id.set_wipecache://清理缓存
                if (cacheSizeNumber.equals("0KB")) {
                    showToast("暂无缓存");
                    return;
                }
                DialogUtils.tipSearchDialog(this, 200, "当前共有缓存" + cacheSizeNumber, this);
                break;
            case R.id.set_feedback://帮助与反馈
                StarActivityUtil.starActivity(this, SugestionBack.class);
                break;
            case R.id.set_about://关于大熊
                StarActivityUtil.starActivity(this, AboutBearMall.class);
                break;
            case R.id.set_sign_out://退出登录
                PopupWindow popupWindow = WindowUtils.ShowVirtual(SettingsActivity.this, R.layout.popup_item_out,
                        R.style.bottom_animation, 1);
                TextView close = popupWindow.getContentView().findViewById(R.id.close_p);
                TextView out = popupWindow.getContentView().findViewById(R.id.out_p);
                close.setOnClickListener(this);
                out.setOnClickListener(this);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventGet(PayPasswordEvent event) {
        isSetPayPwd = event.isSetPwd();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void sureBtn(int flag) {
        if (flag == 200) {
            try {
                showLoading();
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        CacheClearManager.clearAllCache(SettingsActivity.this);
                        Thread.sleep(3000);
                        e.onNext("123");
                        e.onComplete();
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(String stringStringMap) {
                                hiddenLoadingView();
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                                try {
                                    mSetCache.setText(CacheClearManager.getTotalCacheSize(SettingsActivity.this));
                                } catch (Exception e) {

                                }
                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void canle() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_p:
                WindowUtils.dismissBrightness(SettingsActivity.this);
                break;
            case R.id.out_p:
                WindowUtils.dismissBrightness(SettingsActivity.this);
                showLoading();
                settingPresenter.out(this);
                break;
        }
    }
}

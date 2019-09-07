package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.eventbus.PayPasswordEvent;
import com.yunqin.bearmall.ui.fragment.BankFragment;
import com.yunqin.bearmall.ui.fragment.NoPaymentPasswordBankFragment;
import com.yunqin.bearmall.util.NetUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

public class BankCardActivity extends BaseActivity implements View.OnClickListener {

    private NoPaymentPasswordBankFragment mNoPaymentPasswordBankFragment;
    private BankFragment mBankFragment;

    private FragmentManager fragmentManager;


    @BindView(R.id.error_layout)
    View errorView;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, BankCardActivity.class);
        context.startActivity(intent);
    }


    @Override
    public int layoutId() {
        return R.layout.activity_bank_card;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        fragmentManager = getSupportFragmentManager();
        findViewById(R.id.reset_load_data).setOnClickListener(this);
        initData();
    }


    private void initData() {
        if (!NetUtils.isConnected(this)) {
            errorView.setVisibility(View.VISIBLE);
            return;
        }

        showLoading();
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).validatePaymentPwdStatus(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                hiddenLoadingView();

                errorView.setVisibility(View.GONE);

                JSONObject jsonObject = new JSONObject(data);
                JSONObject jsonObject1 = jsonObject.optJSONObject("data");

                int validateResult = jsonObject1.optInt("validateResult");

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (validateResult == 0) {

                    immerseStatusBar(Color.WHITE);
                    changStatusIconCollor(true);

                    if (mNoPaymentPasswordBankFragment == null) {
                        mNoPaymentPasswordBankFragment = new NoPaymentPasswordBankFragment();
                        fragmentTransaction.add(R.id.bank_container, mNoPaymentPasswordBankFragment);
                    }

                    if (mBankFragment != null) {
                        fragmentTransaction.hide(mBankFragment);
                    }
                    fragmentTransaction.show(mNoPaymentPasswordBankFragment);
                    fragmentTransaction.commit();
                } else {

                    immerseStatusBar(Color.TRANSPARENT);
                    changStatusIconCollor(false);

                    if (mBankFragment == null) {
                        mBankFragment = new BankFragment();
                        fragmentTransaction.add(R.id.bank_container, mBankFragment);
                    }

                    if (mNoPaymentPasswordBankFragment != null) {
                        fragmentTransaction.hide(mNoPaymentPasswordBankFragment);
                    }
                    fragmentTransaction.show(mBankFragment);
                    fragmentTransaction.commit();
                }


            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
            }
        });
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.reset_load_data) {
            initData();
        }


    }


    private boolean isUpadte = false;

    @Override
    protected void onResume() {
        super.onResume();

        if (isUpadte) {

            isUpadte = false;

            immerseStatusBar(Color.TRANSPARENT);
            changStatusIconCollor(false);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (mBankFragment == null) {
                mBankFragment = new BankFragment();
                fragmentTransaction.add(R.id.bank_container, mBankFragment);
            }

            if (mNoPaymentPasswordBankFragment != null) {
                fragmentTransaction.hide(mNoPaymentPasswordBankFragment);
            }

            fragmentTransaction.show(mBankFragment);
            fragmentTransaction.commit();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventGet(PayPasswordEvent event) {
        isUpadte = event.isSetPwd();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }













    private void immerseStatusBar(int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            //让应用主题内容占用系统状态栏的空间,注意:下面两个参数必须一起使用 stable 牢固的
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置状态栏颜色为透明
            getWindow().setStatusBarColor(color);
        }
    }


}

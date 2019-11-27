package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newversions.CardListWebActivity;
import com.yunqin.bearmall.AdConstants;
import com.yunqin.bearmall.CustomRotateAnim;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.UserBTInfo;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.ShadowLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenchen on 2018/7/21.
 */

public class PropertyActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.total_sweet)
    TextView totalSweetTextView;
    @BindView(R.id.today_sweet)
    TextView todaySweetTextView;
    @BindView(R.id.text_view)
    TextView text_view;
    @BindView(R.id.toolbar_title)
    TextView titleTextView;
    @BindView(R.id.not_net_group)
    LinearLayout not_net_group;
    @BindView(R.id.toolbar_right_text)
    TextView rightTextView;
    @BindView(R.id.money_rest)
    TextView restMoneyTextView;
    @BindView(R.id.coupon_text)
    TextView couponTextView;
    @BindView(R.id.rest_money_layout)
    ShadowLayout rest_money_layout;
    @BindView(R.id.coupon_layout)
    ShadowLayout coupon_layout;
    @BindView(R.id.property_layout)
    ShadowLayout property_layout;
    @BindView(R.id.img_kai_)
    ImageView img_k;

    private String withdrawFrom;

    public static void startPropertyActivity(Activity context, int type, String total, String today, String coupon, String rest) {
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE", type);
        bundle.putString("TOTAL", total);
        bundle.putString("TODAY", today);
        bundle.putString("COUPON", coupon);
        bundle.putString("REST", rest);
        StarActivityUtil.starActivity(context, PropertyActivity.class, bundle);

    }

    @Override
    public int layoutId() {
        return R.layout.activity_property;
    }

    @Override
    public void init() {
        initView();
        setupData();
        getBCData();
        showAnim();
    }

    private void showAnim() {
        Animation mAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_big_small);
        img_k.setAnimation(mAnimation);
        mAnimation.start();
    }

    private void initView() {
        titleTextView.setText("全部资产");
    }

    private void setupData() {
        int type = getIntent().getIntExtra("TYPE", 1);
        String total = getIntent().getStringExtra("TOTAL");
        String today = getIntent().getStringExtra("TODAY");
        String coupon = getIntent().getStringExtra("COUPON");
        String restMoney = getIntent().getStringExtra("REST");

        if (type == 1) {
            text_view.setText("全部资产");
            titleTextView.setText("全部资产");
            rest_money_layout.setVisibility(View.VISIBLE);
            coupon_layout.setVisibility(View.VISIBLE);
        } else if (type == 2) {
            text_view.setText("糖果资产");
            titleTextView.setText("糖果资产");
            rest_money_layout.setVisibility(View.GONE);
            coupon_layout.setVisibility(View.GONE);
        } else if (type == 3) {
            text_view.setText("赏金资产");
            titleTextView.setText("赏金资产");
            rest_money_layout.setVisibility(View.VISIBLE);
            coupon_layout.setVisibility(View.GONE);
            property_layout.setVisibility(View.GONE);
        }

        setText(today, total, coupon, restMoney);
    }


    @OnClick({R.id.toolbar_back, R.id.property_layout, R.id.toolbar_right_text, R.id.coupon_layout, R.id.rest_money_layout,
            R.id.img_kai_})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_kai_:
                CardListWebActivity.startActivity(this, AdConstants.STRING_WO_DE_QIAN_BAO, "");
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.property_layout:
//                StarActivityUtil.starActivity(this,SweetRecordWithTypeActivity.class);
                SweetRecordWithTypeActivity.startSweetRecordWithTypeActivity(this, totalSweetTextView.getText().toString(),
                        todaySweetTextView.getText().toString());
                break;
            case R.id.toolbar_right_text:
                SweetRecordActivity.startIncomeActivity(2, null, this);
                break;
            case R.id.coupon_layout:
                Intent intent = new Intent(this, CouponActivity.class);
                startActivity(intent);
                break;
            case R.id.rest_money_layout:
                BalanceActivity.startBalanceActivity(this, restMoneyTextView.getText().toString().trim(), withdrawFrom);
                break;
        }
    }

    private void setText(String todaySum, String total, String coupon, String rest) {
        if (TextUtils.isEmpty(coupon)) {
            coupon = "0";
        }
        if (TextUtils.isEmpty(rest)) {
            rest = "0.00";
        }

        if (TextUtils.isEmpty(total) || TextUtils.equals("-1", total)) {
            total = "0";
            showLoading();
        }
        if (TextUtils.isEmpty(todaySum) || TextUtils.equals("-1", todaySum)) {
            todaySum = "0";
        }

        totalSweetTextView.setText(total);
        todaySweetTextView.setText(String.format("今日获取：%s", todaySum));
        couponTextView.setText(String.format("剩余%s张", coupon));
        restMoneyTextView.setText(rest);
    }

    public void getBCData() {
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getUserBTData(new HashMap<String, String>()),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) {
                        hiddenLoadingView();
                        UserBTInfo userBTInfo = new Gson().fromJson(data, UserBTInfo.class);
                        withdrawFrom = userBTInfo.getData().getWithdrawFrom();
                        String todaySum = userBTInfo.getData().getTodayCreditSum();
                        String total = userBTInfo.getData().getBCbanlance();
                        String coupon = String.valueOf(userBTInfo.getData().getTicketCount());
                        String rest = userBTInfo.getData().getBanlance();
                        setText(todaySum, total, coupon, rest);
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
    protected void onResume() {
        super.onResume();
        getBCData();
    }
}

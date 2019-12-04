package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.FansAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.StairFans;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.util.SharedPreferencesHelper;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class FansActivity extends BaseActivity {

    @BindView(R.id.fans_size)
    TextView mFansSize;
    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.wx_name)
    TextView mWxName;
    @BindView(R.id.xtable)
    XTabLayout mXtable;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.wx_id)
    TextView mWxId;
    @BindView(R.id.copy)
    TextView mCopy;

    private int page = 1;
    private int pageSize = 10;

    private static final String[] xTitle = new String[]{"全部", "直邀粉丝", "推荐粉丝"};

    private RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.mine_user_icon_defult)
            .error(R.drawable.mine_user_icon_defult)
            .centerCrop()
            .circleCropTransform();

    public static void openFansActivity(Activity context, Class cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
        context.overridePendingTransition(0, R.anim.activity_stay);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_fans;
    }

    @Override
    public void init() {
        setTranslucentStatus();
        if (BearMallAplication.getInstance().getUser() != null) {
            UserInfo user = BearMallAplication.getInstance().getUser();
            Glide.with(FansActivity.this).load(user.getData().getMember().getIconUrl()).apply(requestOptions).into(mImage);
            mWxName.setText("昵称：" + user.getData().getMember().getNickName());
            mWxId.setText("邀请码：" + user.getRecommendCode());
            mCopy.setVisibility(View.VISIBLE);
            getStairFans();
        } else {
            mCopy.setVisibility(View.GONE);
        }


        for (int i = 0; i < xTitle.length; i++) {
            mXtable.addTab(mXtable.newTab().setText(xTitle[i]));
        }

        FansAdapter fansAdapter = new FansAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(fansAdapter);
        mViewpager.setOffscreenPageLimit(1);
        mXtable.setupWithViewPager(mViewpager);
        mViewpager.setCurrentItem(0);
    }


    private void getStairFans() {
        Map<String, String> map = new HashMap<>();
        map.put("openTime", "0");
        map.put("openCount", "0");
        map.put("page", page + "");
        map.put("pageSize", pageSize + "");
        RetrofitApi.request(FansActivity.this, RetrofitApi.createApi(Api.class).StairFans(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                Log.d("data", data);
                StairFans stairFans = new Gson().fromJson(data, StairFans.class);
                mFansSize.setText(stairFans.getData().getOneSize() + stairFans.getData().getTwoSize() + "");
            }

            @Override
            public void onNotNetWork() {
                Log.i("SecondFans", "onNotNetWork: ");
            }

            @Override
            public void onFail(Throwable e) {
                Log.i("SecondFans", "onFail: ");
            }
        });

    }

    @OnClick({R.id.back, R.id.copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.copy:
                ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, BearMallAplication.getInstance().getUser().getRecommendCode()));
                showToast("复制成功");
                break;
        }
    }

}

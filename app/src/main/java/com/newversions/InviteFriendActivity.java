package com.newversions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.util.CShareUtil;
import com.yunqin.bearmall.widget.LoadingView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InviteFriendActivity extends BaseActivity implements View.OnClickListener {


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, InviteFriendActivity.class);
        context.startActivity(intent);
    }

    private TextView price_new, price_old, tip_b_1;

    private boolean IS_SUCCESS = false;


    @Override
    public int layoutId() {
        return R.layout.activity_invite_friend;
    }

    @Override
    public void init() {
        price_new = findViewById(R.id.price_new);
        price_old = findViewById(R.id.price_old);
        tip_b_1 = findViewById(R.id.tip_b_1);

        findViewById(R.id.request_btn).setOnClickListener(this);
        findViewById(R.id.toolbar_back).setOnClickListener(view -> finish());

        initData();
    }

    private void initData() {
        showLoadings();

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getInvitationPageInfo(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                hideLoadings();
                IS_SUCCESS = true;


                JSONObject jsonObject = new JSONObject(data);
                JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                if (jsonObject1 != null) {
                    JSONObject jsonObject2 = jsonObject1.optJSONObject("invitationPageInfo");
                    if (jsonObject2 != null) {
                        String commission = jsonObject2.optString("commission", "");
                        String rewardPhoneFee = jsonObject2.optString("rewardPhoneFee", "");
                        String invitePrice = jsonObject2.optString("invitePrice", "");
                        String showPrice = jsonObject2.optString("showPrice", "");

                        price_new.setText(invitePrice);
                        price_old.setText(String.format("原价%s/半年", showPrice));
                        tip_b_1.setText(String.format("送出后您得  %s  元现金，好友得  %S  元话费", commission, rewardPhoneFee));

                    }
                }


            }

            @Override
            public void onNotNetWork() {
                hideLoadings();
            }

            @Override
            public void onFail(Throwable e) {
                hideLoadings();
                IS_SUCCESS = false;
            }
        });


    }


    @Override
    public void onClick(View view) {

        if (!IS_SUCCESS) {
            return;
        }


        showLoadings();

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("type", "6");
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getShareParams(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                hideLoadings();
                ShareBean shareBean = new Gson().fromJson(data, ShareBean.class);
                CShareUtil.Share(InviteFriendActivity.this, shareBean.getData());
            }

            @Override
            public void onNotNetWork() {
                hideLoadings();
            }

            @Override
            public void onFail(Throwable e) {
                hideLoadings();
            }
        });
    }


    private LoadingView loadingProgress;

    private void showLoadings() {
        if (loadingProgress == null) {
            loadingProgress = LoadingView.createDialog(this);
            loadingProgress.setCancelable(false);
            loadingProgress.setCanceledOnTouchOutside(false);
        }
        loadingProgress.show();
    }

    private void hideLoadings() {
        if (loadingProgress != null) {
            loadingProgress.dismiss();
        }
    }


}

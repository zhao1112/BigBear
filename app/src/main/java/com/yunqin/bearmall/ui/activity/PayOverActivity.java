package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.MemberBeanResponse;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.widget.CustomRecommendView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Master
 * @create 2018/8/23 14:34
 */
public class PayOverActivity extends BaseActivity {

    @BindView(R.id.pay_status)
    TextView pay_status;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.recommend_view)
    CustomRecommendView mCustomRecommendView;

    public static void start(Context context, String content) {
        Intent intent = new Intent(context, PayOverActivity.class);
        intent.putExtra("status", content);
        context.startActivity(intent);
    }


    @Override
    public int layoutId() {
        return R.layout.activity_pay_over;
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        String status = intent.getStringExtra("status");
        toolbar_title.setText("支付结果");
        if (status != null && !"".equals(status)) {
            pay_status.setText(status);
        } else {
            pay_status.setText("支付已提交");
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mCustomRecommendView.setManager(gridLayoutManager);
        mCustomRecommendView.hideTopLayout();
        mCustomRecommendView.start(this);

        updateUser();

    }

    private void updateUser() {
        if (BearMallAplication.getInstance().getUser() != null) {

            RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getMemberInfo(new HashMap<>()), new RetrofitApi.IResponseListener() {
                @Override
                public void onSuccess(String data) {
                    try {
                        Log.e("用户信息",data);
                        UserInfo userInfo = BearMallAplication.getInstance().getUser();
                        UserInfo.DataBean dataBean = userInfo.getData();
                        MemberBeanResponse response = new Gson().fromJson(data, MemberBeanResponse.class);
                        UserInfo.DataBean.MemberBean memberBean = response.getData();
                        UserInfo.Identity identity = response.getIdentity();
                        dataBean.setMember(memberBean);
                        userInfo.setData(dataBean);
                        userInfo.setIdentity(identity);
                        BearMallAplication.getInstance().setUser(userInfo);
                    } catch (JsonSyntaxException e) {
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
    }

    @OnClick({R.id.toolbar_back, R.id.go_to_order})
    public void onSelect(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.go_to_order:
                startActivity(new Intent(this, MineOrderActivity.class));
                finish();
                break;
            default:
                break;
        }
    }


}

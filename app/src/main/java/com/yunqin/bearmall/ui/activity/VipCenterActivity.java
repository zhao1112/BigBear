package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newversions.view.VerticalViewPager;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.ShareBean;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.util.CShareUtil;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class VipCenterActivity extends BaseActivity {


    public static void startVipCenterActivity(Context context, String invitationType, String invitationCode) {

        Intent intent = new Intent(context, VipCenterActivity.class);
        intent.putExtra("invitationType", invitationType);
        intent.putExtra("invitationCode", invitationCode);
        context.startActivity(intent);

    }


    @BindView(R.id.toolbar_title)
    TextView titleView;

    @BindView(R.id.open_vip)
    Button openVipBtn;

    private String invitationType;
    private String invitationCode;

    @BindView(R.id.vertical_view_pager)
    VerticalViewPager mVerticalViewPager;


    @Override
    public int layoutId() {
        return R.layout.activity_vip_center;
    }

    @Override
    public void init() {

        invitationType = getIntent().getStringExtra("invitationType");
        invitationCode = getIntent().getStringExtra("invitationCode");

        titleView.setText("会员中心");

        UserInfo userInfo = ((BearMallAplication) getApplication()).getUser();
        if (userInfo != null) {
            boolean isMember = userInfo.getData().getMember().isMember();
            boolean isOpend = userInfo.getData().getMember().isOpendMember();
            if (isMember || isOpend) {
//                openVipBtn.setText("立即续费");
                openVipBtn.setTag(1);
            } else {
                openVipBtn.setTag(0);
            }
        } else {
            openVipBtn.setTag(-1);
        }

    }

    private void jump2VipActivity(int tag) {

        if (tag == 0) {
            OpenVipActivity.startOpenVipActivity(this, invitationType, invitationCode);
        } else if (tag == 1) {
            RenewVipActivity.startRenewVipActivity(this, invitationType, invitationCode);
        } else {
            LoginActivity.starActivity(this);
        }

    }

    private ShareBean shareBean;

    @OnClick({R.id.activity_desc, R.id.toolbar_back, R.id.open_vip, R.id.right_icon, R.id.bottom_image})
    public void onViewClick(View view) {

        switch (view.getId()) {

            case R.id.bottom_image:
                mVerticalViewPager.scroolToBottom();
                break;


            case R.id.toolbar_back:

                finish();

                break;

            case R.id.open_vip:

                // TODO 开通会员修改为分享

//                int tag = (int) view.getTag();
//                jump2VipActivity(tag);



                if (shareBean != null) {
                    CShareUtil.Share(VipCenterActivity.this, shareBean.getData());
                    return;
                }


                showLoading();

                Map<String, String> mHashMap = new HashMap<>();
                mHashMap.put("type", "6");
                RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getShareParams(mHashMap), new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        hiddenLoadingView();
                        shareBean = new Gson().fromJson(data, ShareBean.class);
                        CShareUtil.Share(VipCenterActivity.this, shareBean.getData());
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










                break;


            case R.id.right_icon:


                if (shareBean != null) {
                    CShareUtil.Share(VipCenterActivity.this, shareBean.getData());
                    return;
                }


                showLoading();

                Map<String, String> mHashMap1 = new HashMap<>();
                mHashMap1.put("type", "6");
                RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getShareParams(mHashMap1), new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        hiddenLoadingView();
                        shareBean = new Gson().fromJson(data, ShareBean.class);
                        CShareUtil.Share(VipCenterActivity.this, shareBean.getData());
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

                break;

            case R.id.activity_desc:

                String guidelUrl = BuildConfig.BASE_URL + "/view/activityDescriptionPage";
                VanguardListPageActivity.startH5Activity(this, guidelUrl, "活动说明");


                break;

        }

    }


}

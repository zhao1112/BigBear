package com.yunqin.bearmall.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.newversions.tbk.activity.InputIncomCodeActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.util.ConstantScUtil;
import com.yunqin.bearmall.util.DeviceUtils;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.util.StringUtils;
import com.yunqin.bearmall.widget.CircleImageView;
import com.yunqin.bearmall.widget.DelEditText;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2018/7/19
 * @Describe
 */
public class LoginBindPhone extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView titleTextView;
    @BindView(R.id.bind_name)
    TextView bind_name;
    @BindView(R.id.head_img)
    CircleImageView head_img;
    @BindView(R.id.get_img_code)
    ImageView get_img_code;
    @BindView(R.id.img_code)
    DelEditText img_code;
    @BindView(R.id.getcode_btn)
    Button getcode_btn;
    @BindView(R.id.msg_Code)
    DelEditText msg_Code;
    @BindView(R.id.phone_number)
    DelEditText phone_number;
    @BindView(R.id.bind_btn)
    HighlightButton bind_btn;

    private String icon;
    private String name;
    private String loginType;
    private String openID;

    @Override
    public int layoutId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    public void init() {
        Bundle bundle = getIntent().getExtras();
        icon = (String) bundle.get("icon");
        name = (String) bundle.get("name");
        loginType = (String) bundle.get("loginType");
        openID = (String) bundle.get("open_id");
        titleTextView.setText("账号绑定");
        bind_name.setText(Html.fromHtml(getResources().getString(R.string.bind_name, name)));
        Glide.with(this).setDefaultRequestOptions(BearMallAplication.getOptions(R.drawable.mine_user_icon_defult)).load(icon).into(head_img);
        getImageCode();
    }

    @OnClick({R.id.toolbar_back, R.id.getcode_btn, R.id.get_img_code, R.id.bind_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.getcode_btn:
                showLoading();
                sendMsgCode();
                break;
            case R.id.get_img_code:
                getImageCode();
                break;
            case R.id.bind_btn:
                bindPhone();
                break;
        }
    }

    public void getImageCode() {
        Constans.params.clear();
        Constans.params.put("machine_id", DeviceUtils.getUniqueId(this));
        RetrofitApi.requestImageCode(this, RetrofitApi.createApi(Api.class).getImageCode(Constans.params),
                new RetrofitApi.ImageCodeResponseListener() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        try {
                            get_img_code.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                    }
                });
    }

    public void sendMsgCode() {
        if (phone_number.getText() == null || phone_number.getText().toString().equals("")) {
            showToast("请先输入手机号");
            return;
        }
        if (phone_number.getText() == null || !CommonUtils.isPhoneNumber(phone_number.getText().toString())) {
            showToast("请输入正确的手机号");
            return;
        }

        if (img_code.getText() == null || img_code.getText().toString().equals("")) {
            showToast("请输入图片验证码");
            return;
        }

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("mobile", phone_number.getText().toString().trim());
        mHashMap.put("vCod", img_code.getText().toString().trim());
        mHashMap.put("loginType", loginType);
        mHashMap.put("validType", 3 + "");
        mHashMap.put("machine_id", DeviceUtils.getUniqueId(this));
        mHashMap.put("open_id", openID);

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getMsgCode(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                hiddenLoadingView();
                CommonUtils.showCountDown(getcode_btn, CommonUtils.waittime, 1000);
                Toast.makeText(LoginBindPhone.this, "随机码发送成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
                getImageCode();
                img_code.setText("");
            }
        });
    }

    public void bindPhone() {
        if (phone_number.getText() == null || phone_number.getText().toString().equals("")) {
            showToast("请先输入手机号");
            return;
        }
        if (phone_number.getText() == null || !CommonUtils.isPhoneNumber(phone_number.getText().toString())) {
            showToast("请输入正确的手机号");
            return;
        }

        if (msg_Code.getText() == null || msg_Code.getText().toString().equals("")) {
            showToast("请输入随机码");
            return;
        }

        if (img_code.getText() == null || img_code.getText().toString().equals("")) {
            showToast("请输入图片验证码");
            return;
        }

        //TODO[获取验证码]
        ConstantScUtil.sebsorsCode();

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("mobile", phone_number.getText().toString().trim());
        mHashMap.put("loginType", loginType);
        mHashMap.put("validType", 3 + "");
        mHashMap.put("open_id", openID);
        mHashMap.put("smsVCod", msg_Code.getText().toString());
        mHashMap.put("iconUrl", icon);
        mHashMap.put("nickName", name);
        mHashMap.put("cid", (String) SharedPreferencesHelper.get(BearMallAplication.getInstance().getApplicationContext(), "clientid", ""));

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).memberLoginBind(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                UserInfo userInfo = new Gson().fromJson(data, UserInfo.class);
                if (StringUtils.isEmpty(userInfo.getParentCode())) {
                    InputIncomCodeActivity.startInputIncomCodeActivity(LoginBindPhone.this,
                            userInfo.getData().getToken().getAccess_token(), "微信");
                    finish();
                } else {
                    BearMallAplication.getInstance().setUser(userInfo);
                    showToast("绑定成功");
                    if (userInfo.getData().getIsFirstLogin() == 1) {
                        SharedPreferencesHelper.put(LoginBindPhone.this, "isFirstBind", true);
                        SharedPreferencesHelper.put(LoginBindPhone.this, "firstLoginReward", userInfo.getData().getFirstLoginReward());
                    }
                    finish();
                }
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                getImageCode();
                img_code.setText("");
            }
        });
    }

}

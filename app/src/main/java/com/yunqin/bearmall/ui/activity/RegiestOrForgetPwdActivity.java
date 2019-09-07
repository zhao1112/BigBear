package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.eventbus.FinishEvent;
import com.yunqin.bearmall.ui.activity.contract.RegisterOrResetContract;
import com.yunqin.bearmall.ui.activity.presenter.RegisterOrResetPersenter;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.util.DeviceUtils;
import com.yunqin.bearmall.util.SharedPreferencesHelper;
import com.yunqin.bearmall.widget.DelEditText;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RegiestOrForgetPwdActivity extends BaseActivity implements RegisterOrResetContract.UI {

    public static final int FORMREGIEST = 1;
    public static final int FORMFORGETPWD = 2;

    private int fromWhere;

    @BindView(R.id.pwd_layout)
    LinearLayout pwdLayout;

    @BindView(R.id.input_pwd_layout)
    LinearLayout inputPwdLayout;

    @BindView(R.id.regiedt_forgetpwd_layout)
    LinearLayout bottomLayout;

    @BindView(R.id.login_btn)
    HighlightButton loginBtn;

    @BindView(R.id.getcode_btn)
    Button getcodeBtn;

    @BindView(R.id.get_img_code)
    ImageView getImgCode;

    @BindView(R.id.bottom_tip)
    LinearLayout bottomTip;

    @BindView(R.id.toolbar_title)
    TextView titleTextView;

    @BindView(R.id.phone_number)
    DelEditText phone_number;

    @BindView(R.id.psw)
    DelEditText psw;

    @BindView(R.id.msg_Code)
    DelEditText msg_Code;

    @BindView(R.id.img_code)
    DelEditText img_code;

    RegisterOrResetContract.Presenter presenter;

    private String id ;

    public static void starActivity(Activity mContext, int formWhere) {
        Intent intent = new Intent(mContext, RegiestOrForgetPwdActivity.class);
        intent.putExtra("formWhere", formWhere);
        mContext.startActivity(intent);
        mContext.overridePendingTransition(R.anim.activity_in, R.anim.activity_stay);
    }

    public static void starActivity(Activity mContext, int formWhere,String phoneNumber) {
        Intent intent = new Intent(mContext, RegiestOrForgetPwdActivity.class);
        intent.putExtra("formWhere", formWhere);
        intent.putExtra("phoneNumber", phoneNumber);
        mContext.startActivity(intent);
        mContext.overridePendingTransition(R.anim.activity_in, R.anim.activity_stay);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_regiest_forgetpwd;
    }

    @Override
    public void init() {
        id = DeviceUtils.getUniqueId(this);
        fromWhere = (int) getIntent().getExtras().get("formWhere");

        if(getIntent().getExtras().get("phoneNumber") != null){
            phone_number.setText((String)getIntent().getExtras().get("phoneNumber"));
            phone_number.cliearX();
            phone_number.setFocusable(false);
            phone_number.setClickable(false);
            psw.setFocusable(true);
            psw.setFocusableInTouchMode(true);
            psw.requestFocus();
        }

        bottomLayout.setVisibility(View.GONE);
        changeAcLayout(fromWhere);
        if (fromWhere == FORMREGIEST) {
            titleTextView.setText("账号注册");
        } else {
            titleTextView.setText("重置密码");
        }
        presenter = new RegisterOrResetPersenter(this);
        presenter.perfromVerificationCode(this,id);
    }

    @OnClick({R.id.getcode_btn, R.id.get_img_code, R.id.toolbar_back, R.id.login_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.getcode_btn:
//                if(phone_number.getText()== null || phone_number.getText().toString().equals("")){
//                    showToast("请先输入手机号");
//                    return;
//                }
//                if(phone_number.getText()== null || !CommonUtils.isPhoneNumber(phone_number.getText().toString())){
//                    showToast("请输入正确的手机号");
//                    return;
//                }

                if(img_code.getText()== null || img_code.getText().toString().equals("")){
                    showToast("请输入图形验证码");
                    return;
                }

                Constans.params.clear();
                if (fromWhere == FORMREGIEST) {
                    Constans.params.put("validType",2+"");
                }else {
                    Constans.params.put("validType",4+"");
                }
                Constans.params.put("machine_id", id);
                Constans.params.put("mobile",BearMallAplication.getInstance().getUser().getData().getMember().getMobile());
                Constans.params.put("vCod",img_code.getText().toString());
                presenter.sendMsgCode(this,Constans.params);
                CommonUtils.showCountDown(getcodeBtn, CommonUtils.waittime, 1000);
                break;
            case R.id.get_img_code:
                presenter.perfromVerificationCode(this,id);
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.login_btn:
                regiestOrReset();
                break;
        }
    }


    /**
     * 用户注册和重新设置密码
     */
    private void regiestOrReset() {
//        if(phone_number.getText() == null || phone_number.getText().toString().equals("")){
//            showToast("请先输入手机号");
//            return;
//        }
//        if(phone_number.getText()== null || !CommonUtils.isPhoneNumber(phone_number.getText().toString())){
//            showToast("请输入正确的手机号");
//            return;
//        }
        if(psw.getText().toString().equals("")){
            showToast("请输入密码");
            return;
        }
        if(psw.getText().toString().length() < 6 || psw.getText().toString().length() > 20){
            showToast("请输入6-20的密码");
            return;
        }
        if(img_code.getText().toString().equals("")){
            showToast("请输入图片码");
            return;
        }
        if(msg_Code.getText().toString().equals("")){
            showToast("请输入随机码");
            return;
        }

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("mobile",BearMallAplication.getInstance().getUser().getData().getMember().getMobile());
        mHashMap.put("vCod",img_code.getText().toString().trim());
        mHashMap.put("smsVCod",msg_Code.getText().toString().trim());
        mHashMap.put("machine_id", DeviceUtils.getUniqueId(this));
        if (fromWhere == FORMREGIEST) {
            mHashMap.put("password",psw.getText().toString().trim());
            presenter.performRegister(this, mHashMap);
        } else {
            mHashMap.put("newPassword",psw.getText().toString().trim());
            presenter.perfromReset(this, mHashMap);
        }
    }

    //根据不同的值 去显示不同的布局
    private void changeAcLayout(int fromWhere) {
        if (fromWhere == FORMREGIEST) {
            pwdLayout.setVisibility(View.GONE);
            loginBtn.setText("注册");
        } else {
            loginBtn.setText("提交");
            bottomTip.setVisibility(View.GONE);
            pwdLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void finish() {
        super.finish();
        // TODO: 2018/7/18 页面关闭动画
//        overridePendingTransition(R.anim.activity_stay, R.anim.activity_out);
    }

    @Override
    public void attachVCod(Bitmap bitmap) {
        getImgCode.setImageBitmap(bitmap);
    }

    @Override
    public void registerSuccess(UserInfo userInfo) {
        // TODO 注册成功
        BearMallAplication.getInstance().setUser(userInfo);
        showToast("注册成功");
        SharedPreferencesHelper.put(RegiestOrForgetPwdActivity.this,"isFirstBind",true);
        SharedPreferencesHelper.put(RegiestOrForgetPwdActivity.this,"firstLoginReward",userInfo.getData().getFirstLoginReward());
        EventBus.getDefault().post(new FinishEvent());
        finish();
    }

    @Override
    public void registerFail(String msg) {
        // TODO 注册失败
        showToast("注册失败：" + msg);
        presenter.perfromVerificationCode(this,id);
    }

    @Override
    public void resetSuccess() {
        // TODO 重置成功
        showToast("密码重置成功");
        finish();
    }

    @Override
    public void resetFail(String msg) {
        // TODO 重置失败
        showToast("密码重置失败：" + msg);
        presenter.perfromVerificationCode(this,id);
    }


}

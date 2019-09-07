package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.newversions.passwd.IMD5;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.eventbus.UpdateBankEvent;
import com.yunqin.bearmall.widget.LoadingView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yunqin.bearmall.ui.activity.BankAddVerifyActivity.BankVerify.BANK_ADD;
import static com.yunqin.bearmall.ui.activity.BankAddVerifyActivity.BankVerify.BANK_SUBTRACT;

public class BankAddVerifyActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_pass1;
    private TextView tv_pass2;
    private TextView tv_pass3;
    private TextView tv_pass4;
    private TextView tv_pass5;
    private TextView tv_pass6;
    private List<TextView> passwordlist;

    private StringBuffer pwdStringBuffer;
    protected LoadingView loadingProgress;

    private int mType = 0;
    private String bankId;

    @IntDef({BANK_ADD, BANK_SUBTRACT})
    public @interface BankVerify {
        int BANK_ADD = 0x01;
        int BANK_SUBTRACT = 0x02;
    }


    public static void startActivity(Context context, @BankVerify int type, String bankId) {
        Intent intent = new Intent(context, BankAddVerifyActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("bankId", bankId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_add_verify);

        try {
            mType = getIntent().getIntExtra("type", 0);
            bankId = getIntent().getStringExtra("bankId");
        } catch (Exception e) {

        }

        if (mType == 0) {
            finish();
            return;
        }

        passwordlist = new ArrayList<>();
        pwdStringBuffer = new StringBuffer();

        tv_pass1 = findViewById(R.id.tv_pass1);
        tv_pass2 = findViewById(R.id.tv_pass2);
        tv_pass3 = findViewById(R.id.tv_pass3);
        tv_pass4 = findViewById(R.id.tv_pass4);
        tv_pass5 = findViewById(R.id.tv_pass5);
        tv_pass6 = findViewById(R.id.tv_pass6);
        passwordlist.add(tv_pass1);
        passwordlist.add(tv_pass2);
        passwordlist.add(tv_pass3);
        passwordlist.add(tv_pass4);
        passwordlist.add(tv_pass5);
        passwordlist.add(tv_pass6);

        findViewById(R.id.new_version_back).setOnClickListener(this);
        findViewById(R.id.next_button).setOnClickListener(this);
        findViewById(R.id.keyboard_1).setOnClickListener(this);
        findViewById(R.id.keyboard_2).setOnClickListener(this);
        findViewById(R.id.keyboard_3).setOnClickListener(this);
        findViewById(R.id.keyboard_4).setOnClickListener(this);
        findViewById(R.id.keyboard_5).setOnClickListener(this);
        findViewById(R.id.keyboard_6).setOnClickListener(this);
        findViewById(R.id.keyboard_7).setOnClickListener(this);
        findViewById(R.id.keyboard_8).setOnClickListener(this);
        findViewById(R.id.keyboard_9).setOnClickListener(this);
        findViewById(R.id.keyboard_0).setOnClickListener(this);
        findViewById(R.id.keyboard_del).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.new_version_back:
                finish();
                break;
            case R.id.next_button:
                commitPassWord();
                break;
            case R.id.keyboard_1:
                assemblePwd(1);
                break;
            case R.id.keyboard_2:
                assemblePwd(2);
                break;
            case R.id.keyboard_3:
                assemblePwd(3);
                break;
            case R.id.keyboard_4:
                assemblePwd(4);
                break;
            case R.id.keyboard_5:
                assemblePwd(5);
                break;
            case R.id.keyboard_6:
                assemblePwd(6);
                break;
            case R.id.keyboard_7:
                assemblePwd(7);
                break;
            case R.id.keyboard_8:
                assemblePwd(8);
                break;
            case R.id.keyboard_9:
                assemblePwd(9);
                break;
            case R.id.keyboard_del:
                assemblePwd(-1);
                break;
            default:
                break;

        }
    }

    /**
     * 验证密码
     */
    private void commitPassWord() {


        if (pwdStringBuffer.length() == 6) {
            showLoading();
            Map<String, String> mHashMap = new HashMap<>();
            mHashMap.put("paymentPwd", IMD5.md5(pwdStringBuffer.toString()));
            RetrofitApi.request(this, RetrofitApi.createApi(Api.class).validPayPassword(mHashMap), new RetrofitApi.IResponseListener() {
                @Override
                public void onSuccess(String data) throws JSONException {

                    JSONObject jsonObject = new JSONObject(data);

                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    String resValidCode = null;
                    if (jsonObject1 != null) {
                        resValidCode = jsonObject1.optString("resValidCode");
                    }

//                    if (resValidCode == null && "".equals(resValidCode)) {
//                        Toast.makeText(BankAddVerifyActivity.this, "resValidCode 为空", Toast.LENGTH_SHORT).show();
//                        return;
//                    }

                    if (mType == BankVerify.BANK_ADD) {
                        BindingBankActivity.startActivity(BankAddVerifyActivity.this, resValidCode);
                        BankAddVerifyActivity.this.finish();
                    } else if (mType == BankVerify.BANK_SUBTRACT) {
                        unbundleBank(resValidCode);
                    }
                }

                @Override
                public void onNotNetWork() {
                    hideLoading();
                }

                @Override
                public void onFail(Throwable e) {
                    hideLoading();
                }
            });
        } else {
            Toast.makeText(this, "请输入6位支付密码", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 组装代码
     *
     * @param pwd
     */
    private void assemblePwd(int pwd) {
        if (pwd == -1) {
            if (pwdStringBuffer.length() > 0) {
                pwdStringBuffer.deleteCharAt(pwdStringBuffer.length() - 1);
            }
            setPwd(pwdStringBuffer);
            return;
        }
        if (pwdStringBuffer.length() == 6) {
            return;
        }
        pwdStringBuffer.append(pwd);
        setPwd(pwdStringBuffer);
    }


    private void setPwd(StringBuffer stringBuffer) {
        resetPwd();
        for (int i = 0; i < stringBuffer.length(); i++) {
            passwordlist.get(i).setText("1");
        }
    }

    private void resetPwd() {
        for (int i = 0; i < passwordlist.size(); i++) {
            passwordlist.get(i).setText("");
        }
    }


    private void showLoading() {
        if (loadingProgress == null) {
            loadingProgress = LoadingView.createDialog(this);
            loadingProgress.setCancelable(false);
            loadingProgress.setCanceledOnTouchOutside(false);
        }
        loadingProgress.show();
    }

    private void hideLoading() {
        if (loadingProgress != null) {
            loadingProgress.dismiss();
        }
    }


    /**
     * 真·傻逼接口
     * 解绑银行卡
     */

    private void unbundleBank(String resValidCode) {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("userBankId", bankId);
        mHashMap.put("resValidCode", resValidCode);

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).unbindUserBankCard(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                Toast.makeText(BankAddVerifyActivity.this, "解绑成功", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new UpdateBankEvent(true));
                BankAddVerifyActivity.this.finish();
            }

            @Override
            public void onNotNetWork() {

                hideLoading();

            }

            @Override
            public void onFail(Throwable e) {
                hideLoading();
            }
        });


    }


}

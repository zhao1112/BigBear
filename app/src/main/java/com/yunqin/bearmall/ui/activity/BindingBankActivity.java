package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.newversions.view.ICustomDialog;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.eventbus.UpdateBankListEvent;
import com.yunqin.bearmall.widget.LoadingView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BindingBankActivity extends AppCompatActivity implements View.OnClickListener {


    public static void startActivity(Context context, String resValidCode) {
        Intent intent = new Intent(context, BindingBankActivity.class);
        intent.putExtra("resValidCode", resValidCode);
        context.startActivity(intent);
    }

    private String resValidCode;
    private EditText mMasterName;
    private EditText mMasterId;
    private EditText mMasterBankId;
    private EditText mMasterPhone;
    private RelativeLayout mChangeCheckBox;
    private Button mNextButton;
    private CheckBox mCheckBox;
    private LinearLayout mBearAttorneyLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding_bank);
        resValidCode = getIntent().getStringExtra("resValidCode");
        if (resValidCode == null || "".equals(resValidCode)) {
            return;
        }

        initViews();
    }

    private void initViews() {
        mMasterName = findViewById(R.id.master_name_et);
        mMasterId = findViewById(R.id.master_id_et);
        mMasterBankId = findViewById(R.id.master_bank_id_et);
        mMasterPhone = findViewById(R.id.master_phone_et);
        mChangeCheckBox = findViewById(R.id.change_check_box);
        mChangeCheckBox.setOnClickListener(this);
        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(this);
        mCheckBox = findViewById(R.id.check_box);
        mBearAttorneyLayout = findViewById(R.id.bear_attorney_layout);
        mBearAttorneyLayout.setOnClickListener(this);


        findViewById(R.id.new_version_back).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_check_box:
                mCheckBox.setChecked(!mCheckBox.isChecked());
                break;
            case R.id.bear_attorney_layout:
                Toast.makeText(BindingBankActivity.this, "21321321312", Toast.LENGTH_SHORT).show();
                break;
            case R.id.next_button:
                pushBank();
                break;
            case R.id.new_version_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void pushBank() {

        mCheckBox.setChecked(true);

        String masterName = mMasterName.getText().toString();
        String masterId = mMasterId.getText().toString();
        String masterBankId = mMasterBankId.getText().toString();
        String masterPhone = mMasterPhone.getText().toString();

        if ("".equals(masterName)) {
            Toast.makeText(this, "持卡人姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(masterId)) {
            Toast.makeText(this, "持卡人身份证号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(masterBankId)) {
            Toast.makeText(this, "持卡人银行卡号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(masterPhone)) {
            Toast.makeText(this, "持卡人手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (masterPhone.length() != 11 || !masterPhone.startsWith("1")) {
            Toast.makeText(this, "手机号格式错误", Toast.LENGTH_SHORT).show();
            return;
        }

        if (masterId.length() != 15 && masterId.length() != 18) {
            Toast.makeText(this, "持卡人身份证号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!mCheckBox.isChecked()) {
            Toast.makeText(this, "请同意《大熊代扣授权书》", Toast.LENGTH_SHORT).show();
            return;
        }


        showLoading();

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("accountName", masterName);
        mHashMap.put("idCard", masterId);
        mHashMap.put("bankCardNo", masterBankId);
        mHashMap.put("phoneNo", masterPhone);
        mHashMap.put("resValidCode", resValidCode);

        RetrofitApi.request3(BindingBankActivity.this, RetrofitApi.createApi(Api.class).userBankVaild(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                hideLoading();
                JSONObject jsonObject = new JSONObject(data);
                int code = jsonObject.optInt("code");
                String mag = jsonObject.optString("msg");
                if (code == 1) {
                    JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                    long userBankId = jsonObject1.optLong("userBankId");

                    PhoneVerifyActivity.startActivity(BindingBankActivity.this, userBankId);
                    BindingBankActivity.this.finish();
                } else {
                    new ICustomDialog.Builder(BindingBankActivity.this)
                            // 设置布局
                            .setLayoutResId(R.layout.add_bank_error_layout)
                            // 点击空白是否消失
                            .setCanceledOnTouchOutside(true)
                            // 点击返回键是否消失
                            .setCancelable(true)
                            // 设置Dialog的绝对位置
                            .setDialogPosition(Gravity.CENTER)
                            // 设置自定义动画
                            .setAnimationResId(0)
                            // 设置监听ID
                            .setListenedItems(new int[]{R.id._ok})
                            .setCustomTextIds(new int[]{R.id._tip})
                            .setCustomTextContents(new String[]{mag})

                            // 设置回掉
                            .setOnDialogItemClickListener((thisDialog, clickView) -> thisDialog.dismiss())
                            .build().show();
                }
            }

            @Override
            public void onNotNetWork() {
                hideLoading();
                Toast.makeText(BindingBankActivity.this, "当前无网络连接", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(Throwable e) {
                hideLoading();
                Toast.makeText(BindingBankActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private LoadingView loadingProgress;

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


}

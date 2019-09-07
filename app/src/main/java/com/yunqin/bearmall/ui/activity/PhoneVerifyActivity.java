package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.widget.LoadingView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 验证手机号
 */

public class PhoneVerifyActivity extends AppCompatActivity implements View.OnClickListener {

    public static void startActivity(Context context, long userBankId) {
        Intent intent = new Intent(context, PhoneVerifyActivity.class);
        intent.putExtra("userBankId", userBankId);
        context.startActivity(intent);
    }


    private long userBankId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verify);

        userBankId = getIntent().getLongExtra("userBankId", -100);

        if (userBankId == -100) {
            return;
        }
        initViews();
    }

    private Button get_code_;
    private TextView error_info_tv;
    private EditText rand_code;

    private void initViews() {
        get_code_ = findViewById(R.id.get_code_);
        get_code_.setOnClickListener(this);
        findViewById(R.id.next_button).setOnClickListener(this);
        error_info_tv = findViewById(R.id.error_info_tv);
        rand_code = findViewById(R.id.rand_code);

        findViewById(R.id.new_version_back).setOnClickListener(this);

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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_version_back:
                finish();
                break;
            case R.id.next_button:
                userBankvalidSmsCode();
                break;
            case R.id.get_code_:
                createSmsCodeForVerifyBank();
                success();
                break;
            default:
                break;
        }
    }


    /**
     * 下发短信验证码
     */
    private void createSmsCodeForVerifyBank() {

        showLoading();

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("userBankId", String.valueOf(userBankId));

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).createSmsCodeForVerifyBank(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                hideLoading();
                success();
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

    private void success() {
        int codeTimes = 60;

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(codeTimes - 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return codeTimes - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        get_code_.setEnabled(false);
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {
                        get_code_.setText(value + "秒");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        get_code_.setEnabled(true);
                        get_code_.setText("重新发送");
                    }
                });
    }


    /**
     * 验证绑定
     */
    private void userBankvalidSmsCode() {

        error_info_tv.setText("");

        String smsCode = rand_code.getText().toString();

        if ("".equals(smsCode)) {
            Toast.makeText(this, "请填写验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        showLoading();
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("userBankId", String.valueOf(userBankId));
        mHashMap.put("smsVCod", smsCode);


        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).userBankvalidSmsCode(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                VerifySuccessActivity.startActivity(PhoneVerifyActivity.this);
                PhoneVerifyActivity.this.finish();
            }

            @Override
            public void onNotNetWork() {
                hideLoading();
            }

            @Override
            public void onFail(Throwable e) {
                hideLoading();
                try {
                    JSONObject jsonObject = new JSONObject(e.getMessage());
                    error_info_tv.setText(jsonObject.optString("msg"));
                } catch (Exception ex) {
                }
            }
        });


    }


}

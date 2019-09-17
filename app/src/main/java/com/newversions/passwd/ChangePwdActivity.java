package com.newversions.passwd;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.widget.LoadingView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.newversions.passwd.ChangePwdActivity.PwStatus.TYPE_ONE;
import static com.newversions.passwd.ChangePwdActivity.PwStatus.TYPE_THREE;
import static com.newversions.passwd.ChangePwdActivity.PwStatus.TYPE_TWO;


public class ChangePwdActivity extends BaseActivity implements View.OnClickListener {

    private TextView hint_tip;
    private TextView tv_pass1;
    private TextView tv_pass2;
    private TextView tv_pass3;
    private TextView tv_pass4;
    private TextView tv_pass5;
    private TextView tv_pass6;

    private StringBuffer oldPwdStringBuffer;
    private StringBuffer newPwdStringBuffer;
    private StringBuffer againPwdStringBuffer;
    private List<TextView> pwdTvs;

    private int CURRTYPE;

    @IntDef({TYPE_ONE, TYPE_TWO, TYPE_THREE})
    public @interface PwStatus {
        int TYPE_ONE = 0;
        int TYPE_TWO = 1;
        int TYPE_THREE = 3;
    }


    @Override
    public int layoutId() {
        return R.layout.new_version_activity_change_pwd;
    }

    @Override
    public void init() {
        initData();
        findViews();
        initClick();
    }


    private void initData() {
        CURRTYPE = TYPE_ONE;
        pwdTvs = new ArrayList<>();
        oldPwdStringBuffer = new StringBuffer();
        newPwdStringBuffer = new StringBuffer();
        againPwdStringBuffer = new StringBuffer();
    }

    private void initClick() {
        findViewById(R.id.keyboard_0).setOnClickListener(this);
        findViewById(R.id.keyboard_1).setOnClickListener(this);
        findViewById(R.id.keyboard_2).setOnClickListener(this);
        findViewById(R.id.keyboard_3).setOnClickListener(this);
        findViewById(R.id.keyboard_4).setOnClickListener(this);
        findViewById(R.id.keyboard_5).setOnClickListener(this);
        findViewById(R.id.keyboard_6).setOnClickListener(this);
        findViewById(R.id.keyboard_7).setOnClickListener(this);
        findViewById(R.id.keyboard_8).setOnClickListener(this);
        findViewById(R.id.keyboard_9).setOnClickListener(this);
        findViewById(R.id.keyboard_del).setOnClickListener(this);
        findViewById(R.id.next_button).setOnClickListener(this);
        findViewById(R.id.n_v_cancel).setOnClickListener(this);
    }

    private void findViews() {
        tv_pass1 = findViewById(R.id.tv_pass1);
        tv_pass2 = findViewById(R.id.tv_pass2);
        tv_pass3 = findViewById(R.id.tv_pass3);
        tv_pass4 = findViewById(R.id.tv_pass4);
        tv_pass5 = findViewById(R.id.tv_pass5);
        tv_pass6 = findViewById(R.id.tv_pass6);
        hint_tip = findViewById(R.id.hint_tip);
        pwdTvs.add(tv_pass1);
        pwdTvs.add(tv_pass2);
        pwdTvs.add(tv_pass3);
        pwdTvs.add(tv_pass4);
        pwdTvs.add(tv_pass5);
        pwdTvs.add(tv_pass6);
        hint_tip.setText("请输入旧的支付密码");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.keyboard_0:
                assemblePwd(0);
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
            case R.id.next_button:
                changeStatus();
                break;
            case R.id.n_v_cancel:
                finish();
                break;
            default:
                break;
        }
    }


    private void assemblePwd(int pwd) {
        if (CURRTYPE == TYPE_ONE) {
            if (pwd == -1) {
                if (oldPwdStringBuffer.length() > 0) {
                    oldPwdStringBuffer.deleteCharAt(oldPwdStringBuffer.length() - 1);
                }
                setPwd(oldPwdStringBuffer);
                return;
            }
            if (oldPwdStringBuffer.length() == 6) {
                return;
            }
            oldPwdStringBuffer.append(pwd);
            setPwd(oldPwdStringBuffer);
        } else if (CURRTYPE == TYPE_TWO) {
            if (pwd == -1) {
                if (newPwdStringBuffer.length() > 0) {
                    newPwdStringBuffer.deleteCharAt(newPwdStringBuffer.length() - 1);
                }
                setPwd(newPwdStringBuffer);
                return;
            }

            if (newPwdStringBuffer.length() == 6) {
                return;
            }
            newPwdStringBuffer.append(pwd);
            setPwd(newPwdStringBuffer);
        } else {
            if (pwd == -1) {
                if (againPwdStringBuffer.length() > 0) {
                    againPwdStringBuffer.deleteCharAt(againPwdStringBuffer.length() - 1);
                }
                setPwd(againPwdStringBuffer);
                return;
            }

            if (againPwdStringBuffer.length() == 6) {
                return;
            }
            againPwdStringBuffer.append(pwd);
            setPwd(againPwdStringBuffer);
        }
    }

    private void changeStatus() {
        if (CURRTYPE == TYPE_ONE) {
            if (oldPwdStringBuffer.length() == 6) {
                // TODO 验证密码是否输入正确
                verifyPwd();
            } else {
                Toast.makeText(this, "请输入6位密码", Toast.LENGTH_SHORT).show();
            }
        } else if (CURRTYPE == TYPE_TWO) {
            if (newPwdStringBuffer.length() == 6) {
                resetPwd();
                CURRTYPE = TYPE_THREE;
                hint_tip.setText("请再次输入新的支付密码");
            } else {
                Toast.makeText(this, "请输入6位密码", Toast.LENGTH_SHORT).show();
            }
        } else if (CURRTYPE == TYPE_THREE) {
            if (againPwdStringBuffer.length() == 6) {
                synchronizationPwd();
            } else {
                Toast.makeText(this, "请输入6位密码", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void verifyPwd() {
        showLoadings();
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("paymentPwd", IMD5.md5(oldPwdStringBuffer.toString()));
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).validPayPassword(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                hideLoadings();
                JSONObject jsonObject = new JSONObject(data);
                if (jsonObject.optInt("code") == 1) {
                    resetPwd();
                    CURRTYPE = TYPE_TWO;
                    hint_tip.setText("请输入新的6位支付密码");
                } else {
                    resetStub();
                }
            }

            @Override
            public void onNotNetWork() {
                hideLoadings();
            }

            @Override
            public void onFail(Throwable e) {
                hideLoadings();
                resetStub();
            }
        });


    }


    private void synchronizationPwd() {
        // TODO 网络请求,同步密码

        if (newPwdStringBuffer.toString().equals(againPwdStringBuffer.toString())) {

            showLoadings();
            Map<String, String> mHashMap = new HashMap<>();
            mHashMap.put("oldPaymentPwd", IMD5.md5(oldPwdStringBuffer.toString()));
            mHashMap.put("newPaymentPwd", IMD5.md5(newPwdStringBuffer.toString()));

            RetrofitApi.request(this, RetrofitApi.createApi(Api.class).updatePayPassword(mHashMap), new RetrofitApi.IResponseListener() {
                @Override
                public void onSuccess(String data) throws JSONException {
                    hideLoadings();
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.optInt("code") == 1) {
                        // TODO 修改成功
                        View view = LayoutInflater.from(ChangePwdActivity.this).inflate(R.layout.new_version_toast, null);
                        TextView textView = view.findViewById(R.id.content);
                        textView.setText("修改成功");
                        Toast toast = new Toast(ChangePwdActivity.this);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(view);
                        toast.show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ChangePwdActivity.this.finish();
                            }
                        }, 2000);
                    } else {
                        resetStub();
                    }
                }

                @Override
                public void onNotNetWork() {
                    hideLoadings();
                }

                @Override
                public void onFail(Throwable e) {
                    hideLoadings();
                    resetStub();
                }
            });
        } else {
            resetStub();
            Toast.makeText(this, "两次输入的密码不一致,请重新输入", Toast.LENGTH_SHORT).show();
        }


    }


    private void setPwd(StringBuffer stringBuffer) {
        resetPwd();
        for (int i = 0; i < stringBuffer.length(); i++) {
            pwdTvs.get(i).setText("1");
        }
    }

    private void resetPwd() {
        for (int i = 0; i < pwdTvs.size(); i++) {
            pwdTvs.get(i).setText("");
        }
    }


    protected LoadingView loadingProgress;

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


    private void resetStub() {
        CURRTYPE = TYPE_ONE;
        oldPwdStringBuffer.setLength(0);
        newPwdStringBuffer.setLength(0);
        againPwdStringBuffer.setLength(0);
        hint_tip.setText("请输入旧的支付密码");
        resetPwd();
    }


}

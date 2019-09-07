package com.newversions.passwd;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.IntDef;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.newversions.passwd.ResetPwdFragment.PwStatus.TYPE_ONE;
import static com.newversions.passwd.ResetPwdFragment.PwStatus.TYPE_TWO;

/**
 * Create By Master
 * On 2019/1/16 15:04
 */
public class ResetPwdFragment extends BaseFragment {

    @BindView(R.id.hint_tip)
    TextView hint_tip;
    @BindView(R.id.tv_pass1)
    TextView tv_pass1;
    @BindView(R.id.tv_pass2)
    TextView tv_pass2;
    @BindView(R.id.tv_pass3)
    TextView tv_pass3;
    @BindView(R.id.tv_pass4)
    TextView tv_pass4;
    @BindView(R.id.tv_pass5)
    TextView tv_pass5;
    @BindView(R.id.tv_pass6)
    TextView tv_pass6;

    private List<TextView> pwdTvs;
    private int CURRTYPE;

    private StringBuffer newPwdStringBuffer;
    private StringBuffer againPwdStringBuffer;

    @IntDef({TYPE_ONE, TYPE_TWO})
    public @interface PwStatus {
        int TYPE_ONE = 0;
        int TYPE_TWO = 1;
    }


    private FragmentToActivityInte fragmentToActivityInter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentToActivityInte) {
            fragmentToActivityInter = (FragmentToActivityInte) context;
        }
    }

    @Override
    public int layoutId() {
        return R.layout.new_reset_fragment_layout;
    }

    @Override
    public void init() {
        CURRTYPE = TYPE_ONE;
        newPwdStringBuffer = new StringBuffer();
        againPwdStringBuffer = new StringBuffer();
        pwdTvs = new ArrayList<>();
        pwdTvs.add(tv_pass1);
        pwdTvs.add(tv_pass2);
        pwdTvs.add(tv_pass3);
        pwdTvs.add(tv_pass4);
        pwdTvs.add(tv_pass5);
        pwdTvs.add(tv_pass6);
        hint_tip.setText("请重置支付密码");
    }


    @OnClick({R.id.keyboard_0, R.id.keyboard_1, R.id.keyboard_2,
            R.id.keyboard_3, R.id.keyboard_4, R.id.keyboard_5,
            R.id.keyboard_6, R.id.keyboard_7, R.id.keyboard_8,
            R.id.keyboard_9, R.id.keyboard_del, R.id.next_button})
    public void initClick(View view) {
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
            default:
                break;
        }
    }


    private void assemblePwd(int pwd) {
        if (CURRTYPE == TYPE_ONE) {
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
        } else if (CURRTYPE == TYPE_TWO) {

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
            if (newPwdStringBuffer.length() == 6) {
                resetPwd();
                CURRTYPE = TYPE_TWO;
                hint_tip.setText("请再次输入支付密码");
                fragmentToActivityInter.changeTitle("请再次输入支付密码");
            } else {
                Toast.makeText(getActivity(), "请输入6位密码", Toast.LENGTH_SHORT).show();
            }
        } else if (CURRTYPE == TYPE_TWO) {
            if (againPwdStringBuffer.length() == 6) {
                synchronizationPwd();
            } else {
                Toast.makeText(getActivity(), "请输入6位密码", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void synchronizationPwd() {
        if (newPwdStringBuffer.toString().equals(againPwdStringBuffer.toString())) {

            showLoading();

            Map<String, String> mHashMap = new HashMap<>();
            mHashMap.put("paymentPwd", IMD5.md5(newPwdStringBuffer.toString()));
            mHashMap.put("type", "2");
            mHashMap.put("validCodeForReset", fragmentToActivityInter.callBackCode(null));

            RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).setPayPassword(mHashMap), new RetrofitApi.IResponseListener() {
                @Override
                public void onSuccess(String data) throws JSONException {
                    hiddenLoadingView();
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.optInt("code") == 1) {
                        // 设置成功
                        View view = LayoutInflater.from(getActivity()).inflate(R.layout.new_version_toast, null);
                        TextView textView = view.findViewById(R.id.content);
                        textView.setText("重置成功");
                        Toast toast = new Toast(getActivity());
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(view);
                        toast.show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getActivity().finish();
                            }
                        }, 2000);
                    }
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
        } else {
            Toast.makeText(getActivity(), "两次输入的密码不一致请重新输入", Toast.LENGTH_SHORT).show();
            resetStub();
        }
    }

    private void resetStub() {
        CURRTYPE = TYPE_ONE;
        newPwdStringBuffer.setLength(0);
        againPwdStringBuffer.setLength(0);
        hint_tip.setText("请重置支付密码");
        fragmentToActivityInter.changeTitle("请重置支付密码");
        resetPwd();
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


}

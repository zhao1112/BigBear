package com.newversions.passwd;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Create By Master
 * On 2019/1/16 15:04
 */
public class RetrievePwdFragment extends BaseFragment {

    private FragmentToActivityInte fragmentToActivityInter;
    private boolean isNext = false;


    @BindView(R.id.img_code)
    ImageView img_code;
    @BindView(R.id.next_button)
    Button next_button;
    @BindView(R.id.tip)
    TextView tip;

    @BindView(R.id.img_code_ed)
    EditText img_code_ed;
    @BindView(R.id.random_code_ed)
    EditText random_code_ed;
    @BindView(R.id.text_phone)
    TextView text_phone;
    @BindView(R.id.send_random_code)
    Button send_random_code;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentToActivityInte) {
            fragmentToActivityInter = (FragmentToActivityInte) context;
        }
    }

    @Override
    public int layoutId() {
        return R.layout.new_retrieve_fragment_layout;
    }

    @Override
    public void init() {
        if (fragmentToActivityInter != null) {
            fragmentToActivityInter.changeTitle("找回支付密码");
        }
        text_phone.setText(BearMallAplication.getInstance().getUser().getData().getMember().getMobile());
        initNetworkRequestImg();
    }


    @OnClick({R.id.img_code, R.id.next_button, R.id.send_random_code})
    public void viewClick(View view) {
        switch (view.getId()) {
            case R.id.img_code:
                initNetworkRequestImg();
                break;
            case R.id.next_button:
                next();
                break;
            case R.id.send_random_code:
                getRandomCode();
                break;
        }
    }

    private void getRandomCode() {
        String imgCode = img_code_ed.getText().toString();

        if (img_code_ed.getText().length() == 0) {
            Toast.makeText(getActivity(), "请输入图形验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        showLoading();
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("vCod", imgCode);
        mHashMap.put("mobile", text_phone.getText().toString());
        mHashMap.put("validType", "5");

        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getMsgCode(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                hiddenLoadingView();
                isNext = true;
                daojishiButton();
                // TODO 倒计时
            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
                initNetworkRequestImg();
                img_code_ed.setText("");
                try {
                    JSONObject jsonObject = new JSONObject(e.getMessage());
                    tip.setText(jsonObject.optString("msg"));
                } catch (Exception ex) {
                    tip.setText(e.getMessage());
                }
            }
        });
    }

    private void daojishiButton() {
        final int count = 60;//倒计时10秒
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//ui线程中进行控件更新
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        send_random_code.setEnabled(false);
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long num) {
                send_random_code.setText(num + "s");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                //恢复原来初始状态
                send_random_code.setEnabled(true);
                send_random_code.setText("发送随机码");
            }
        });
    }

    private void next() {

        if (img_code_ed.getText().length() == 0) {
            Toast.makeText(getActivity(), "请输入图形验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (random_code_ed.getText().length() == 0) {
            Toast.makeText(getActivity(), "请输入随机码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isNext) {

            showLoading();

            Map<String, String> mHashMap = new HashMap<>();
            mHashMap.put("mobile", text_phone.getText().toString());
            mHashMap.put("smsVCod", random_code_ed.getText().toString());

            RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).validSmsCode(mHashMap), new RetrofitApi.IResponseListener() {
                @Override
                public void onSuccess(String data) throws JSONException {
                    hiddenLoadingView();
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.optInt("code") == 1) {
                        String code = jsonObject.optJSONObject("data").optString("validCodeForReset");
                        fragmentToActivityInter.callBackCode(code);
                        fragmentToActivityInter.chooseFragment();
                    }
                }

                @Override
                public void onNotNetWork() {
                    hiddenLoadingView();
                }

                @Override
                public void onFail(Throwable e) {
                    hiddenLoadingView();
                    try {
                        JSONObject jsonObject = new JSONObject(e.getMessage());
                        tip.setText(jsonObject.optString("msg"));
                    } catch (Exception ex) {
                        tip.setText(e.getMessage());
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "随机码错误", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初始化IMG
     */
    private void initNetworkRequestImg() {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("0", "0");
        RetrofitApi.requestImageCode(getActivity(), RetrofitApi.createApi(Api.class).getImageCode(mHashMap), new RetrofitApi.ImageCodeResponseListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                try {
                    img_code.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Throwable e) {
            }
        });
    }
}

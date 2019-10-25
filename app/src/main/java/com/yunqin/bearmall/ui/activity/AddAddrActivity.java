package com.yunqin.bearmall.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.Address;
import com.yunqin.bearmall.util.CommonUtils;
import com.yunqin.bearmall.util.CustomAddressProvider;
import com.yunqin.bearmall.widget.SwitchButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import butterknife.BindView;
import butterknife.OnClick;
import chihane.jdaddressselector.AddressSelector;
import chihane.jdaddressselector.BottomDialog;
import chihane.jdaddressselector.OnAddressSelectedListener;
import chihane.jdaddressselector.model.City;
import chihane.jdaddressselector.model.County;
import chihane.jdaddressselector.model.Province;
import chihane.jdaddressselector.model.Street;
import io.reactivex.Observable;

/**
 * @author Master
 */
public class AddAddrActivity extends BaseActivity {

    @BindView(R.id.ed_consignee_name)
    EditText ed_consignee_name;
    @BindView(R.id.ed_consignee_number)
    EditText ed_consignee_number;
    @BindView(R.id.ed_consignee_addr)
    EditText ed_consignee_addr;
    @BindView(R.id.default_addr)
    SwitchButton default_addr;
    @BindView(R.id.add_address)
    TextView AddAddressTextView;
    BottomDialog mAddressSelectedDialog;
    @BindView(R.id.toolbar_title)
    TextView titleTextView;

    /**
     * 是修改还是新增
     */
    private static boolean ADD_OR_UPDATE = false;

    private static long area_id;
    private static long receiver_id;

    private static boolean isUpdateAddressList = false;

    @Override
    public int layoutId() {
        return R.layout.activity_address_layout;
    }

    @Override
    public void init() {

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            ed_consignee_name.setText(bundle.getString("name"));
            ed_consignee_number.setText(bundle.getString("number"));
            AddAddressTextView.setText(bundle.getString("address"));
            ed_consignee_addr.setText(bundle.getString("address_"));
            titleTextView.setText(bundle.getString("title"));
            default_addr.setChecked(bundle.getBoolean("checked"));
            area_id = bundle.getInt("area_id");
            receiver_id = bundle.getInt("receiver_id");
            ADD_OR_UPDATE = true;
        } else {
            ADD_OR_UPDATE = false;
            titleTextView.setText("新建收货地址");
        }

        ed_consignee_name.setSelection(ed_consignee_name.getText().toString().length());
    }

    CountDownLatch countDownLatch;

    @OnClick({R.id.addr_save, R.id.add_address, R.id.toolbar_back})
    public void saveAddress(View view) {
        switch (view.getId()) {
            case R.id.add_address:
                // 地址选择器
                final boolean[] isNext = {false};

                if (BearMallAplication.getAddress() == null) {

                    countDownLatch = new CountDownLatch(1);

                    RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getAreaList(), new RetrofitApi.IResponseListener() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                BearMallAplication.mAddress = new Gson().fromJson(data, Address.class);
                                isNext[0] = false;
                                countDownLatch.countDown();
                            } catch (JsonSyntaxException e) {
                                showToast("网络连接错误，请检查网络连接");
                                isNext[0] = true;
                                countDownLatch.countDown();
                            }
                        }

                        @Override
                        public void onNotNetWork() {
                            showToast("网络连接错误，请检查网络连接");
                            isNext[0] = true;
                            countDownLatch.countDown();
                        }

                        @Override
                        public void onFail(Throwable e) {
                            showToast("网络连接错误，请检查网络连接");
                            isNext[0] = true;
                            countDownLatch.countDown();
                        }
                    });
                }

                try {
                    if (countDownLatch != null) {
                        countDownLatch.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (isNext[0]) {
                    return;
                }

                AddressSelector selector = new AddressSelector(this);
                selector.setOnAddressSelectedListener(onAddressSelectedListener);
                selector.setAddressProvider(new CustomAddressProvider(BearMallAplication.getAddress()));

                if (mAddressSelectedDialog == null) {
                    mAddressSelectedDialog = new BottomDialog(this);
                    mAddressSelectedDialog.setContentView(selector.getView());
                    mAddressSelectedDialog.setOnAddressSelectedListener(onAddressSelectedListener);
                }
                mAddressSelectedDialog.show();
                break;
            case R.id.addr_save:
                if (ed_consignee_name.getText().toString().length() <= 0) {
                    showToast("请填写联系人姓名");
                    break;
                }

                if (TextUtils.isEmpty(ed_consignee_number.getText().toString())) {
                    showToast("请先输入手机号");
                    return;
                }
                if (!CommonUtils.isPhoneNumber(ed_consignee_number.getText().toString())) {
                    showToast("请输入正确的手机号");
                    return;
                }
                if (AddAddressTextView.getText().toString().equals(getResources().getString(R.string.addr_select))) {
                    showToast("请选择地址");
                    break;
                }
                if (ed_consignee_addr.getText().toString().length() <= 0) {
                    showToast("请填写详细地址");
                    break;
                }
                addAddress();
                break;
            case R.id.toolbar_back:
                finishActivity();
                break;
        }
    }

    /**
     * 地址选择接口回调
     */
    private OnAddressSelectedListener onAddressSelectedListener = new OnAddressSelectedListener() {
        @Override
        public void onAddressSelected(Province province, City city, County county, Street street) {
            mAddressSelectedDialog.dismiss();
            StringBuffer stringBuffer = new StringBuffer();
            if (province != null) {
                stringBuffer.append(province.name);
                area_id = province.id;
            }
            if (city != null) {
                stringBuffer.append(" ").append(city.name);
                area_id = city.id;
            }
            if (county != null) {
                stringBuffer.append(" ").append(county.name);
                area_id = county.id;
            }
            if (street != null) {
                stringBuffer.append(" ").append(street.name);
                area_id = street.id;
            }
            AddAddressTextView.setText(stringBuffer.toString());
        }
    };


    /**
     * 新增收货地址
     * 修改收货地址
     */
    private void addAddress() {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("consignee", ed_consignee_name.getText().toString());
        mHashMap.put("phone", ed_consignee_number.getText().toString());
        mHashMap.put("areaName", AddAddressTextView.getText().toString());
        mHashMap.put("address", ed_consignee_addr.getText().toString());
        mHashMap.put("area_id", String.valueOf(area_id));
        mHashMap.put("isDefault", String.valueOf(default_addr.isChecked() ? 1 : 0));

        Observable<String> observable;
        if (ADD_OR_UPDATE) {
            mHashMap.put("receiver_id", String.valueOf(receiver_id));
            observable = RetrofitApi.createApi(Api.class).editReceiver(mHashMap);
        } else {
            observable = RetrofitApi.createApi(Api.class).addReceiver(mHashMap);
        }


        RetrofitApi.request(this, observable, new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.optInt("code") == 1) {
                        isUpdateAddressList = true;
                        finishActivity();
                    } else {
                    }
                } catch (JSONException e) {
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


    private void finishActivity() {
        Intent intent = new Intent();
        intent.putExtra("type", isUpdateAddressList ? 0 : 1);
        setResult(100, intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        finishActivity();
    }
}

package com.yunqin.bearmall.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.widget.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;
import chihane.jdaddressselector.AddressSelector;
import chihane.jdaddressselector.BottomDialog;
import chihane.jdaddressselector.OnAddressSelectedListener;
import chihane.jdaddressselector.model.City;
import chihane.jdaddressselector.model.County;
import chihane.jdaddressselector.model.Province;
import chihane.jdaddressselector.model.Street;

/**
 * @author Master
 */
public class AddrActivity extends BaseActivity {

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

    @Override
    public int layoutId() {
        return R.layout.activity_more_type;
    }

    @Override
    public void init() {
        titleTextView.setText("新建收货地址");
    }

    @OnClick({R.id.addr_save, R.id.add_address, R.id.toolbar_back})
    public void saveAddress(View view) {
        switch (view.getId()) {
            case R.id.add_address:
                // 地址选择器
                if (mAddressSelectedDialog == null) {
                    mAddressSelectedDialog = new BottomDialog(this);
                    mAddressSelectedDialog.setOnAddressSelectedListener(onAddressSelectedListener);
                }
                mAddressSelectedDialog.show();
                break;
            case R.id.addr_save:

                if (ed_consignee_name.getText().toString().length() <= 0) {
                    showToast("请填写联系人姓名");
                    break;
                }

                if (ed_consignee_number.getText().toString().length() <= 0) {
                    showToast("请填写手机号码");
                    break;
                }
                if (AddAddressTextView.getText().toString().equals(getResources().getString(R.string.addr_select))) {
                    showToast("请选择地址");
                    break;
                }
                if (ed_consignee_addr.getText().toString().length() <= 0) {
                    showToast("请填写详细地址");
                    break;
                }
                // TODO 地址保存
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }


    private OnAddressSelectedListener onAddressSelectedListener = new OnAddressSelectedListener() {
        @Override
        public void onAddressSelected(Province province, City city, County county, Street street) {
            mAddressSelectedDialog.dismiss();
            StringBuffer stringBuffer = new StringBuffer();
            if (province != null) {
                stringBuffer.append(province.name);
            }
            if (city != null) {
                stringBuffer.append(" ").append(city.name);
            }
            if (county != null) {
                stringBuffer.append(" ").append(county.name);
            }
            if (street != null) {
                stringBuffer.append(" ").append(street.name);
            }
            AddAddressTextView.setText(stringBuffer.toString());
        }
    };

}

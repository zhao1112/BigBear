package com.yunqin.bearmall.ui.fragment;

import android.content.Intent;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.newversions.passwd.SettingPwdActivity;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;

import butterknife.BindView;

/**
 * Create By Master
 * On 2019/2/28 15:56
 */
public class NoPaymentPasswordBankFragment extends BaseFragment {

    @BindView(R.id.next_button)
    Button next_button;

    @BindView(R.id.new_version_back)
    RelativeLayout back;

    @Override
    public int layoutId() {
        return R.layout.no_payment_password_bank_layout;
    }

    @Override
    public void init() {
        next_button.setOnClickListener(view -> startActivity(new Intent(getActivity(), SettingPwdActivity.class)));
        back.setOnClickListener(view -> getActivity().finish());
    }

}

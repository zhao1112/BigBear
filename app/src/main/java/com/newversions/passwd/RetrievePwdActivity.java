package com.newversions.passwd;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunqin.bearmall.R;

public class RetrievePwdActivity extends AppCompatActivity implements View.OnClickListener, FragmentToActivityInte {

    private FragmentManager fragmentManager;
    private ResetPwdFragment resetPwdFragment;
    private RetrievePwdFragment retrievePwdFragment;

    private TextView new_version_title;
    private RelativeLayout new_version_back;
    private TextView n_v_cancel;

    private String validCodeForReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_version_activity_retrieve_pwd);
        findViews();
        initializePage();
    }

    private void findViews() {
        new_version_back = findViewById(R.id.new_version_back);
        new_version_back.setOnClickListener(this);
        n_v_cancel = findViewById(R.id.n_v_cancel);
        n_v_cancel.setOnClickListener(this);
        new_version_title = findViewById(R.id.new_version_title);
    }

    private void initializePage() {
        fragmentManager = getSupportFragmentManager();
        retrievePwdFragment = new RetrievePwdFragment();
        resetPwdFragment = new ResetPwdFragment();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, retrievePwdFragment);
        fragmentTransaction.add(R.id.fragment_container, resetPwdFragment);
        fragmentTransaction.hide(resetPwdFragment);
        fragmentTransaction.show(retrievePwdFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_version_back:
                finish();
                break;
            case R.id.n_v_cancel:
                finish();
                break;
        }
    }


    @Override
    public void chooseFragment() {
        hiddenKeyboard();
        new_version_back.setVisibility(View.VISIBLE);
        n_v_cancel.setVisibility(View.INVISIBLE);
        changeTitle("请重置支付密码");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(retrievePwdFragment);
        fragmentTransaction.show(resetPwdFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void changeTitle(String title) {
        new_version_title.setText(title);
    }

    @Override
    public String callBackCode(String code) {
        if (code != null) {
            validCodeForReset = code;
        }
        return validCodeForReset;
    }

    public void hiddenKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}

package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.ui.fragment.BackstangeTabFragment;
import com.yunqin.bearmall.ui.fragment.PartnerFragmentFans;

import butterknife.BindView;

public class BackstageActivity extends BaseActivity {

    @BindView(R.id.backstange_radiogroup)
    RadioGroup mBackstangeRadiogroup;
    @BindView(R.id.backstange_manage_rb)
    RadioButton mBackstangeManage_Rb;
    @BindView(R.id.backstange_fanc_rb)
    RadioButton mBackstangeFanc_Rb;

    private Fragment mBackstFragment;
    private BackstangeTabFragment mBackstangeTabFragment;
    private EditText mPartnerTtext;
    private Button mPartnerButton;
    private ImageView mPartenrReturn;
    private PartnerFragmentFans mPartnerFragmentFans;
    private RelativeLayout mSeekPartnerRe;


    @Override
    public int layoutId() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return R.layout.activity_backstage;
    }

    @Override
    public void init() {
        initView();
        initDate();

        setTranslucentStatus();

        mBackstangeTabFragment = new BackstangeTabFragment();
        mPartnerFragmentFans = new PartnerFragmentFans();

        mBackstangeRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.backstange_manage_rb:
                        switchFragment(mBackstangeTabFragment);
                        mPartnerTtext.setHint("输入订单号/手机号/邀请码");
                        mPartnerTtext.setCursorVisible(false);
                        break;
                    case R.id.backstange_fanc_rb:
                        mPartnerTtext.setHint("输入手机号/邀请码");
                        switchFragment(mPartnerFragmentFans);
                        mPartnerTtext.setCursorVisible(false);
                        break;
                }
            }
        });

        mBackstangeManage_Rb.setChecked(true);

    }


    private void initView() {
        //合伙人订单输入框
        mPartnerTtext = findViewById(R.id.partner_text);
        //合伙人搜索订单
        mPartnerButton = findViewById(R.id.partner_button);
        //返回按钮
        mPartenrReturn = findViewById(R.id.partenr_return);
        mPartenrReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initDate() {
        mSeekPartnerRe = findViewById(R.id.seek_partner_re);

        mSeekPartnerRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPartnerTtext.getHint().equals("输入订单号/手机号/邀请码")) {
                    Intent intent = new Intent(getApplicationContext(), PartnerOrderSeekActivity.class);
                    startActivity(intent);
                } else if (mPartnerTtext.getHint().equals("输入手机号/邀请码")) {
                    Intent intent = new Intent(getApplicationContext(), PartnerFansSeekActivity.class);
                    startActivity(intent);
                }
            }
        });
        mPartnerTtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPartnerTtext.getHint().equals("输入订单号/手机号/邀请码")) {
                    Intent intent = new Intent(getApplicationContext(), PartnerOrderSeekActivity.class);
                    startActivity(intent);
                } else if (mPartnerTtext.getHint().equals("输入手机号/邀请码")) {
                    Intent intent = new Intent(getApplicationContext(), PartnerFansSeekActivity.class);
                    startActivity(intent);
                }
            }
        });


        mPartnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mSearchPar = mPartnerTtext.getText().toString();

            }
        });
    }

    private void switchFragment(Fragment fragment) {
        if (fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().hide(mBackstFragment).show(fragment).commit();
            mBackstFragment = fragment;
        } else {
            if (mBackstFragment == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.backstage_franelayout, fragment).commit();
            } else {
                getSupportFragmentManager().beginTransaction().hide(mBackstFragment).add(R.id.backstage_franelayout, fragment).commit();
            }
            mBackstFragment = fragment;
        }
    }

}


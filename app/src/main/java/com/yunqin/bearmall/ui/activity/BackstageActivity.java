package com.yunqin.bearmall.ui.activity;

import android.support.v4.app.Fragment;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.ui.fragment.BackstangeTabFragment;

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

    @Override
    public int layoutId() {
        return R.layout.activity_backstage;
    }

    @Override
    public void init() {
        setTranslucentStatus();

        mBackstangeTabFragment = new BackstangeTabFragment();

        mBackstangeRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.backstange_manage_rb:
                        switchFragment(mBackstangeTabFragment);
                        break;
                }
            }
        });

        mBackstangeManage_Rb.setChecked(true);

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

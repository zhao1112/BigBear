package com.newversions;

import android.view.View;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create By Master
 * On 2019/1/9 9:46
 */
public class MemberMallActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @Override
    public int layoutId() {
        return R.layout.activity_member_mall;
    }

    @Override
    public void init() {
        toolbar_title.setText("会员商城");
    }


    @OnClick(R.id.toolbar_back)
    public void backClick(View view) {
        this.finish();
    }


}

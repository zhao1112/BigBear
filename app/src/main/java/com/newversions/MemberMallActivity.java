package com.newversions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yunqin.bearmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Create By Master
 * On 2019/1/9 9:46
 */
public class MemberMallActivity extends AppCompatActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_mall);
        ButterKnife.bind(this);
        toolbar_title.setText("会员商城");
    }


    @OnClick(R.id.toolbar_back)
    public void backClick(View view) {
        this.finish();
    }


}

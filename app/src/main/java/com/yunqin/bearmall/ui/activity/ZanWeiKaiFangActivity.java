package com.yunqin.bearmall.ui.activity;

import android.view.View;
import android.widget.ImageView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author AYWang
 * @create 2019/2/14
 * @Describe
 */
public class ZanWeiKaiFangActivity extends BaseActivity {
    @BindView(R.id.toolbar_back)
    ImageView toolbar_back;
    @Override
    public int layoutId() {
        return R.layout.activity_zanweikaifang;
    }

    @Override
    public void init() {
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

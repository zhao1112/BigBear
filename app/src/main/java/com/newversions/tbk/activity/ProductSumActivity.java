package com.newversions.tbk.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CpuUsageInfo;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.newversions.tbk.Constants;
import com.newversions.tbk.fragment.ProductSumFragment;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 根据类别查看商品
 */
public class ProductSumActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.content)
    FrameLayout content;

    public static void startProductSumActivity(Activity activity,String groupId,int type,String title){
        Intent intent = new Intent(activity,ProductSumActivity.class);
        intent.putExtra(Constants.INTENT_KEY_ID,groupId);
        intent.putExtra(Constants.INTENT_KEY_TITLE,title);
        intent.putExtra(Constants.INTENT_KEY_TYPE,type);
        activity.startActivity(intent);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_product_sum;
    }

    @Override
    public void init() {
        toolbarTitle.setText(getIntent().getStringExtra(Constants.INTENT_KEY_TITLE));
        ProductSumFragment fragment = ProductSumFragment.getInstance(getIntent().getStringExtra(Constants.INTENT_KEY_ID), getIntent().getIntExtra(Constants.INTENT_KEY_TYPE, 1));
        getSupportFragmentManager().beginTransaction().add(R.id.content,fragment).commit();
    }



    @OnClick(R.id.toolbar_back)
    public void onViewClicked() {
        finish();
    }
}

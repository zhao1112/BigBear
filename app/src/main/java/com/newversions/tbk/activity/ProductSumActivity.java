package com.newversions.tbk.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.newversions.tbk.Constants;
import com.newversions.tbk.fragment.ProductSumFragment;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.ProductSumAdapter2;
import com.yunqin.bearmall.adapter.ProductSumAdapter3;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.SearchData;
import com.yunqin.bearmall.widget.RefreshHeadView;
import com.yunqin.bearmall.widget.SwitchButton;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 根据类别查看商品
 */
public class ProductSumActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.content)
    FrameLayout content;

    public static void startProductSumActivity(Activity activity, String groupId, int type, String title) {
        Intent intent = new Intent(activity, ProductSumActivity.class);
        intent.putExtra(Constants.INTENT_KEY_ID, groupId);
        intent.putExtra(Constants.INTENT_KEY_TITLE, title);
        intent.putExtra(Constants.INTENT_KEY_TYPE, type);
        activity.startActivity(intent);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_product_sum;
    }

    @Override
    public void init() {
        toolbarTitle.setText(getIntent().getStringExtra(Constants.INTENT_KEY_TITLE));
        ProductSumFragment fragment = ProductSumFragment.getInstance(getIntent().getStringExtra(Constants.INTENT_KEY_ID), getIntent().getIntExtra(Constants.INTENT_KEY_TYPE, 1),getIntent().getStringExtra(Constants.INTENT_KEY_TITLE));
        getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).commit();
    }


    @OnClick(R.id.toolbar_back)
    public void onViewClicked() {
        finish();
    }
}

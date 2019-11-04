package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.GoodsDetailActivity;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.SuperSearchAdapter;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.SuperSearch;

import java.util.List;

public class SuperSearchActivity extends BaseActivity implements View.OnClickListener {


    private RecyclerView mSu_rcl;

    @Override
    public int layoutId() {
        return R.layout.activity_super_search;
    }

    public static void openSuperSearchActivity(Context context, SuperSearch search, String title) {
        Intent intent = new Intent(context, SuperSearchActivity.class);
        intent.putExtra("search_data", search);
        intent.putExtra("search_title", title);
        context.startActivity(intent);
    }

    @Override
    public void init() {
        showLoading();
        SuperSearch search = (SuperSearch) getIntent().getSerializableExtra("search_data");
        String search_title = getIntent().getStringExtra("search_title");

        mSu_rcl = findViewById(R.id.su_rcl);
        RelativeLayout showdata = findViewById(R.id.data);
        findViewById(R.id.su_back).setOnClickListener(this);
        TextView su_title = findViewById(R.id.su_title);
        su_title.setText(search_title);

        if (search.getCode() != 1) {
            showdata.setVisibility(View.VISIBLE);
            hiddenLoadingView();
            return;
        }

        mSu_rcl.setVisibility(View.VISIBLE);
        mSu_rcl.setLayoutManager(new GridLayoutManager(SuperSearchActivity.this, 2));
        List<SuperSearch.DataBean> data = search.getData();
        SuperSearchAdapter superSearchAdapter = new SuperSearchAdapter(SuperSearchActivity.this, data);
        mSu_rcl.setAdapter(superSearchAdapter);
        hiddenLoadingView();
        superSearchAdapter.setgoodsId(new SuperSearchAdapter.goodsId() {
            @Override
            public void getGoodsId(String id) {
                GoodsDetailActivity.startGoodsDetailActivity(SuperSearchActivity.this, id, Constants.GOODS_TYPE_TBK_SEARCH);
            }
        });

    }

    @Override
    public void onClick(View view) {
        finish();
    }
}

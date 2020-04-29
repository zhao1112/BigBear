package com.bbcoupon.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bbcoupon.ui.adapter.CustomAdapter;
import com.bbcoupon.ui.bean.CustomInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.GoodsDetailActivity;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.activity
 * @DATE 2020/4/26
 */
public class CustomGoodesActivity extends BaseActivity implements RequestContract.RequestView {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.image_top)
    ImageView mImageTop;
    @BindView(R.id.nulldata)
    ConstraintLayout mNulldata;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.bg_layout)
    LinearLayout mBgLayout;

    private RequestPresenter requestPresenter;
    private int page = 1;
    private CustomAdapter customAdapter;
    private String type_target;
    private String type_title;

    public static void openCustomGoodesActivity(Activity context, Class cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
        context.overridePendingTransition(0, R.anim.activity_stay);
    }


    @Override
    public int layoutId() {
        return R.layout.activity_customgoodes;
    }

    @Override
    public void init() {

        try {
            type_target = getIntent().getStringExtra("TYPE_TARGET");
            type_title = getIntent().getStringExtra("TYPE_TITLE");
        } catch (Exception e) {
            e.printStackTrace();
        }

        requestPresenter = new RequestPresenter();
        requestPresenter.setRelation(this);

        mRefreshLayout.setHeaderView(new RefreshHeadView(this));
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page = 1;
                customAdapter.cleanList();
                getCustomGoods();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                page++;
                getCustomGoods();
            }
        });
        mRecycler.setNestedScrollingEnabled(false);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        customAdapter = new CustomAdapter(this);
        mRecycler.setAdapter(customAdapter);

        getCustomGoods();


        customAdapter.setOnClickProductSumItem(new CustomAdapter.onClickProductSumItem2() {
            @Override
            public void onItem(int position, CustomInfor.CommodityListBean bean) {
                Intent intent = new Intent(CustomGoodesActivity.this, GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_KEY_ID, bean.getItemId() + "");
                intent.putExtra(Constants.INTENT_KEY_TYPE, Constants.GOODS_TYPE_TBK);
                intent.putExtra(Constants.INTENT_KEY_COMM, bean.getCommision());
                intent.putExtra(Constants.INTENT_KEY_COMMISSION, Constants.COMMISSION_TYPE_ONE);
                startActivity(intent);
            }
        });

        mTitle.setText(type_title);
        mNulldata.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.toolbar_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        requestPresenter.setUntying(this);
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof CustomInfor) {
            try {
                CustomInfor customInfor = (CustomInfor) data;
                Glide.with(CustomGoodesActivity.this)
                        .load(customInfor.getPlatformList().getImage())
                        .apply(new RequestOptions().placeholder(R.drawable.default_product_small))
                        .into(mImageTop);
                mBgLayout.setBackgroundColor(Color.parseColor(customInfor.getPlatformList().getContent()));
                if (customInfor.getCommodityList() != null && customInfor.getCommodityList().size() > 0) {
                    customAdapter.addList(customInfor.getCommodityList());
                    mNulldata.setVisibility(View.GONE);
                }
            } catch (Exception e) {

            }
            mRefreshLayout.finishRefreshing();
            mRefreshLayout.finishLoadmore();
        }
    }

    @Override
    public void onNotNetWork() {
        mRefreshLayout.finishRefreshing();
        mRefreshLayout.finishLoadmore();
    }

    @Override
    public void onFail(Throwable e) {
        mRefreshLayout.finishRefreshing();
        mRefreshLayout.finishLoadmore();
    }

    public void getCustomGoods() {
        Map<String, String> map = new HashMap<>();
        map.put("target", type_target);
        map.put("page", page + "");
        map.put("pageSize", "10");
        requestPresenter.onCustom(this, map);
    }

}

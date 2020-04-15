package com.yunqin.bearmall.ui.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.DailyAdapter;
import com.yunqin.bearmall.adapter.MonthlyAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.DailyIncomeBean;
import com.yunqin.bearmall.bean.MonthlyBean;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author LWP
 * @PACKAGE com.yunqin.bearmall.ui.activity
 * @DATE 2020/4/13
 */
public class MonthlyIncomeActivity extends BaseActivity {

    @BindView(R.id.nulldata)
    ConstraintLayout mNulldata;
    @BindView(R.id.recycle)
    RecyclerView mRecycle;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    private MonthlyAdapter mMonthlyAdapter;

    @Override
    public int layoutId() {
        return R.layout.activity_monthly;
    }

    @Override
    public void init() {

        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setHeaderView(new RefreshHeadView(MonthlyIncomeActivity.this));
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mMonthlyAdapter.clearDataList();
                getMonthlyHistory1();
            }
        });

        mRecycle.setLayoutManager(new LinearLayoutManager(MonthlyIncomeActivity.this));
        mMonthlyAdapter = new MonthlyAdapter(MonthlyIncomeActivity.this);
        mRecycle.setAdapter(mMonthlyAdapter);

        getMonthlyHistory1();
        mNulldata.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.toolbar_back)
    public void onViewClicked() {
        finish();
    }

    public void getMonthlyHistory1() {
        showLoading();
        Map<String, String> map = new HashMap<>();
        RetrofitApi.request(MonthlyIncomeActivity.this, RetrofitApi.createApi(Api.class).getMonthlyHistory1(map),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        if (!TextUtils.isEmpty(data)) {
                            MonthlyBean monthlyBean = new Gson().fromJson(data, MonthlyBean.class);
                            if (monthlyBean.getData() != null && monthlyBean.getData().size() > 0) {
                                mMonthlyAdapter.addDataLis(monthlyBean.getData());
                                mNulldata.setVisibility(View.GONE);
                                getMonthlyHistory2();
                            }
                        }
                    }

                    @Override
                    public void onNotNetWork() {
                        mRefreshLayout.finishRefreshing();
                        hiddenLoadingView();
                    }

                    @Override
                    public void onFail(Throwable e) {
                        mRefreshLayout.finishRefreshing();
                        hiddenLoadingView();
                    }
                });
    }

    public void getMonthlyHistory2() {
        showLoading();
        Map<String, String> map = new HashMap<>();
        RetrofitApi.request(MonthlyIncomeActivity.this, RetrofitApi.createApi(Api.class).getMonthlyHistory2(map),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        if (!TextUtils.isEmpty(data)) {
                            MonthlyBean monthlyBean = new Gson().fromJson(data, MonthlyBean.class);
                            if (monthlyBean.getData() != null && monthlyBean.getData().size() > 0) {
                                mMonthlyAdapter.addDataLis(monthlyBean.getData());
                                mNulldata.setVisibility(View.GONE);
                            }
                        }
                        mRefreshLayout.finishRefreshing();
                        hiddenLoadingView();
                    }

                    @Override
                    public void onNotNetWork() {
                        mRefreshLayout.finishRefreshing();
                        hiddenLoadingView();
                    }

                    @Override
                    public void onFail(Throwable e) {
                        mRefreshLayout.finishRefreshing();
                        hiddenLoadingView();
                    }
                });
    }
}

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
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.DailyIncomeBean;
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
public class DailyIncomeActivity extends BaseActivity {

    @BindView(R.id.nulldata)
    ConstraintLayout mNulldata;
    @BindView(R.id.recycle)
    RecyclerView mRecycle;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    private DailyAdapter mDailyAdapter;

    @Override
    public int layoutId() {
        return R.layout.activity_daily_income;
    }

    @Override
    public void init() {

        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setHeaderView(new RefreshHeadView(DailyIncomeActivity.this));
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mDailyAdapter.clearDataList();
                getJapaneseHistory1();
            }
        });

        mRecycle.setLayoutManager(new LinearLayoutManager(DailyIncomeActivity.this));
        mDailyAdapter = new DailyAdapter(DailyIncomeActivity.this);
        mRecycle.setAdapter(mDailyAdapter);

        getJapaneseHistory1();
        mNulldata.setVisibility(View.VISIBLE);
    }


    @OnClick(R.id.toolbar_back)
    public void onViewClicked() {
        finish();
    }

    public void getJapaneseHistory1() {
        showLoading();
        Map<String, String> map = new HashMap<>();
        RetrofitApi.request(DailyIncomeActivity.this, RetrofitApi.createApi(Api.class).getJapaneseHistory1(map),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        if (!TextUtils.isEmpty(data)) {
                            DailyIncomeBean dailyIncomeBean = new Gson().fromJson(data, DailyIncomeBean.class);
                            if (dailyIncomeBean.getData() != null && dailyIncomeBean.getData().size() > 0) {
                                mDailyAdapter.addDataLis(dailyIncomeBean.getData(),1);
                                mNulldata.setVisibility(View.GONE);
                                getJapaneseHistory2();
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

    public void getJapaneseHistory2() {
        showLoading();
        Map<String, String> map = new HashMap<>();
        RetrofitApi.request(DailyIncomeActivity.this, RetrofitApi.createApi(Api.class).getJapaneseHistory2(map),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        if (!TextUtils.isEmpty(data)) {
                            DailyIncomeBean dailyIncomeBean = new Gson().fromJson(data, DailyIncomeBean.class);
                            if (dailyIncomeBean.getData() != null && dailyIncomeBean.getData().size() > 0) {
                                mDailyAdapter.addDataLis(dailyIncomeBean.getData(),2);
                                mNulldata.setVisibility(View.GONE);
                                getJapaneseHistory3();
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

    public void getJapaneseHistory3() {
        showLoading();
        Map<String, String> map = new HashMap<>();
        RetrofitApi.request(DailyIncomeActivity.this, RetrofitApi.createApi(Api.class).getJapaneseHistory3(map),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        if (!TextUtils.isEmpty(data)) {
                            DailyIncomeBean dailyIncomeBean = new Gson().fromJson(data, DailyIncomeBean.class);
                            if (dailyIncomeBean.getData() != null && dailyIncomeBean.getData().size() > 0) {
                                mDailyAdapter.addDataLis(dailyIncomeBean.getData(),2);
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

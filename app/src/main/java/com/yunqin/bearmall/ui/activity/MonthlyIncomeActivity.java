package com.yunqin.bearmall.ui.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private List<MonthlyBean.DataBean> list = new ArrayList<>();
    private boolean one = false;
    private boolean two = false;

    @Override
    public int layoutId() {
        return R.layout.activity_monthly;
    }

    @Override
    public void init() {

        for (int i = 0; i < 6; i++) {
            list.add(0, new MonthlyBean.DataBean());
        }

        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setHeaderView(new RefreshHeadView(MonthlyIncomeActivity.this));
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mMonthlyAdapter.clearDataList();
                one = false;
                two = false;
                getMonthlyHistory1();
                getMonthlyHistory2();
            }
        });

        mRecycle.setLayoutManager(new LinearLayoutManager(MonthlyIncomeActivity.this));
        mMonthlyAdapter = new MonthlyAdapter(MonthlyIncomeActivity.this);
        mRecycle.setAdapter(mMonthlyAdapter);

        getMonthlyHistory1();
        getMonthlyHistory2();

    }

    @OnClick(R.id.toolbar_back)
    public void onViewClicked() {
        finish();
    }

    public void Hidden() {
        try {
            hiddenLoadingView();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
        }
    }

    public void setData(List<MonthlyBean.DataBean> list) {
        try {
            if (one && two) {
                mMonthlyAdapter.addDataLis(list);
                Hidden();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
        }
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
                                try {
                                    for (int i = 0; i < monthlyBean.getData().size(); i++) {
                                        list.set(i, monthlyBean.getData().get(i));
                                    }
                                    one = true;
                                    setData(list);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e("Exception---***", e.getMessage());
                                }
                            }
                        }
                    }

                    @Override
                    public void onNotNetWork() {
                        mRefreshLayout.finishRefreshing();
                        Hidden();
                    }

                    @Override
                    public void onFail(Throwable e) {
                        mRefreshLayout.finishRefreshing();
                        Hidden();
                    }
                });
    }

    public void getMonthlyHistory2() {
        Map<String, String> map = new HashMap<>();
        RetrofitApi.request(MonthlyIncomeActivity.this, RetrofitApi.createApi(Api.class).getMonthlyHistory2(map),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        if (!TextUtils.isEmpty(data)) {
                            MonthlyBean monthlyBean = new Gson().fromJson(data, MonthlyBean.class);
                            if (monthlyBean.getData() != null && monthlyBean.getData().size() > 0) {
                                try {
                                    for (int i = 0; i < monthlyBean.getData().size(); i++) {
                                        list.set(i + 3, monthlyBean.getData().get(i));
                                    }
                                    two = true;
                                    setData(list);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e("Exception-----", e.getMessage());
                                }
                            }
                        }
                        mRefreshLayout.finishRefreshing();
                    }

                    @Override
                    public void onNotNetWork() {
                        mRefreshLayout.finishRefreshing();
                        Hidden();
                    }

                    @Override
                    public void onFail(Throwable e) {
                        mRefreshLayout.finishRefreshing();
                        Hidden();
                    }
                });
    }
}

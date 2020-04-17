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
public class DailyIncomeActivity extends BaseActivity {

    @BindView(R.id.nulldata)
    ConstraintLayout mNulldata;
    @BindView(R.id.recycle)
    RecyclerView mRecycle;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    private DailyAdapter mDailyAdapter;
    private List<DailyIncomeBean.DataBean> list = new ArrayList<>();
    private boolean one = false;
    private boolean two = false;
    private boolean three = false;

    @Override
    public int layoutId() {
        return R.layout.activity_daily_income;
    }

    @Override
    public void init() {

        for (int i = 0; i < 7; i++) {
            list.add(i, new DailyIncomeBean.DataBean());
        }

        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setHeaderView(new RefreshHeadView(DailyIncomeActivity.this));
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mDailyAdapter.clearDataList();
                one = false;
                two = false;
                three = false;
                getJapaneseHistory1();
                getJapaneseHistory2();
                getJapaneseHistory3();
            }
        });

        mRecycle.setLayoutManager(new LinearLayoutManager(DailyIncomeActivity.this));
        mDailyAdapter = new DailyAdapter(DailyIncomeActivity.this);
        mRecycle.setAdapter(mDailyAdapter);

        getJapaneseHistory1();
        getJapaneseHistory2();
        getJapaneseHistory3();
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
        }
    }

    private void setData(List<DailyIncomeBean.DataBean> list) {
        try {
            if (one && two && three) {
                mDailyAdapter.addDataLis(list);
                Hidden();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getJapaneseHistory1() {
        try {
            showLoading();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<>();
        RetrofitApi.request(DailyIncomeActivity.this, RetrofitApi.createApi(Api.class).getJapaneseHistory1(map),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        if (!TextUtils.isEmpty(data)) {
                            DailyIncomeBean dailyIncomeBean = new Gson().fromJson(data, DailyIncomeBean.class);
                            if (dailyIncomeBean.getData() != null && dailyIncomeBean.getData().size() > 0) {
                                try {
                                    for (int i = 0; i < dailyIncomeBean.getData().size(); i++) {
                                        list.set(i, dailyIncomeBean.getData().get(i));
                                    }
                                    one = true;
                                    setData(list);
                                } catch (Exception e) {
                                    e.printStackTrace();
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


    public void getJapaneseHistory2() {
        Map<String, String> map = new HashMap<>();
        RetrofitApi.request(DailyIncomeActivity.this, RetrofitApi.createApi(Api.class).getJapaneseHistory2(map),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        if (!TextUtils.isEmpty(data)) {
                            DailyIncomeBean dailyIncomeBean = new Gson().fromJson(data, DailyIncomeBean.class);
                            if (dailyIncomeBean.getData() != null && dailyIncomeBean.getData().size() > 0) {
                                try {
                                    for (int i = 0; i < dailyIncomeBean.getData().size(); i++) {
                                        list.set(i + 2, dailyIncomeBean.getData().get(i));
                                    }
                                    two = true;
                                    setData(list);
                                } catch (Exception e) {
                                    e.printStackTrace();
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

    public void getJapaneseHistory3() {
        Map<String, String> map = new HashMap<>();
        RetrofitApi.request(DailyIncomeActivity.this, RetrofitApi.createApi(Api.class).getJapaneseHistory3(map),
                new RetrofitApi.IResponseListener() {
                    @Override
                    public void onSuccess(String data) throws JSONException {
                        if (!TextUtils.isEmpty(data)) {
                            DailyIncomeBean dailyIncomeBean = new Gson().fromJson(data, DailyIncomeBean.class);
                            if (dailyIncomeBean.getData() != null && dailyIncomeBean.getData().size() > 0) {
                                try {
                                    for (int i = 0; i < dailyIncomeBean.getData().size(); i++) {
                                        list.set(i + 4, dailyIncomeBean.getData().get(i));
                                    }
                                    three = true;
                                    setData(list);
                                } catch (Exception e) {
                                    e.printStackTrace();
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

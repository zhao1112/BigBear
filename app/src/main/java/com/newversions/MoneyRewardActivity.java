package com.newversions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.ui.activity.LoginActivity;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 赏金消息
 */
public class MoneyRewardActivity extends BaseActivity implements View.OnClickListener {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MoneyRewardActivity.class);
        context.startActivity(intent);
    }

    private TwinklingRefreshLayout mTwinklingRefreshLayout;
    private RecyclerView mRecyclerView;
    private View mEmptyView;
    private HighlightButton highlightButton;
    private TextView tip;

    private MoneyRewardAdapter moneyRewardAdapter;

    private boolean hasNext = false;
    private int index = 1;

    @Override
    public int layoutId() {
        return R.layout.new_activity_money_reward;
    }

    @Override
    public void init() {
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BearMallAplication.getInstance().getUser() != null) {
            // 登陆过
            mTwinklingRefreshLayout.setVisibility(View.VISIBLE);
            refreshData();
        } else {
            // 未登录
            mTwinklingRefreshLayout.setVisibility(View.GONE);
            tip.setText("请先登录");
            highlightButton.setVisibility(View.VISIBLE);
        }
    }

    private void initViews() {
        TextView top = findViewById(R.id.top);
        top.setText("佣金消息");
        mTwinklingRefreshLayout = findViewById(R.id.refresh_layout);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEmptyView = findViewById(R.id.empty_view);
        highlightButton = findViewById(R.id.login_btn);
        tip = findViewById(R.id.tip);
        findViewById(R.id.new_version_back).setOnClickListener(this);
        highlightButton.setOnClickListener(this);

        mTwinklingRefreshLayout.setHeaderView(new RefreshHeadView(this));
        mTwinklingRefreshLayout.setBottomView(new RefreshBottomView(this));

        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                // 刷新
                refreshData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (hasNext) {
                    // 加载更多
                    loadData();
                } else {
                    mTwinklingRefreshLayout.finishLoadmore();
                }
            }
        });

        if (BearMallAplication.getInstance().getUser() != null) {
            // 登陆过
            mTwinklingRefreshLayout.setVisibility(View.VISIBLE);
            refreshData();
        } else {
            // 未登录
            mTwinklingRefreshLayout.setVisibility(View.GONE);
            tip.setText("请先登录");
            highlightButton.setVisibility(View.VISIBLE);
        }
    }


    private void initData() {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("category", "1");
        mHashMap.put("page_number", String.valueOf(index));
        mHashMap.put("page_size", "20");
        getHttpData(mHashMap, new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                MoneyRewardBean moneyRewardBean = new Gson().fromJson(data, MoneyRewardBean.class);
                if (moneyRewardBean.getData() != null && moneyRewardBean.getData().getList() != null) {
                    changeView(moneyRewardBean.getData().getHas_more() == 0 ? false : true);
                    if (moneyRewardBean.getData().getList().size() > 0) {
                        mTwinklingRefreshLayout.setVisibility(View.VISIBLE);
                        moneyRewardAdapter = new MoneyRewardAdapter(MoneyRewardActivity.this, moneyRewardBean.getData().getList());
                        mRecyclerView.setAdapter(moneyRewardAdapter);
                    } else {
                        mTwinklingRefreshLayout.setVisibility(View.GONE);
                        tip.setText("暂无数据");
                        highlightButton.setVisibility(View.GONE);
                    }
                } else {
                    mTwinklingRefreshLayout.setVisibility(View.GONE);
                    tip.setText("暂无数据");
                    highlightButton.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });


    }

    private void refreshData() {
        index = 1;
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("category", "1");
        mHashMap.put("page_number", String.valueOf(index));
        mHashMap.put("page_size", "20");

        getHttpData(mHashMap, new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                mTwinklingRefreshLayout.finishRefreshing();
                MoneyRewardBean moneyRewardBean = new Gson().fromJson(data, MoneyRewardBean.class);
                if (moneyRewardBean.getData() != null && moneyRewardBean.getData().getList() != null) {
                    changeView(moneyRewardBean.getData().getHas_more() == 0 ? false : true);
                    if (moneyRewardBean.getData().getList().size() > 0) {
                        mTwinklingRefreshLayout.setVisibility(View.VISIBLE);
                        if (moneyRewardAdapter == null) {
                            moneyRewardAdapter = new MoneyRewardAdapter(MoneyRewardActivity.this, moneyRewardBean.getData().getList());
                            mRecyclerView.setAdapter(moneyRewardAdapter);
                        } else {
                            List<MoneyRewardBean.DataBean.ListBean> listBeans = new ArrayList<>();
                            for (int i = 0; i < moneyRewardBean.getData().getList().size(); i++) {
                                if (moneyRewardBean.getData().getList().get(i).getOrderNo() != "null" &&
                                        !TextUtils.isEmpty(moneyRewardBean.getData().getList().get(i).getOrderNo())) {
                                    listBeans.add(moneyRewardBean.getData().getList().get(i));
                                }
                            }
                            moneyRewardAdapter.setData(listBeans);
                        }
                    } else {
                        mTwinklingRefreshLayout.setVisibility(View.GONE);
                        tip.setText("暂无数据");
                        highlightButton.setVisibility(View.GONE);
                    }
                } else {
                    mTwinklingRefreshLayout.setVisibility(View.GONE);
                    tip.setText("暂无数据");
                    highlightButton.setVisibility(View.GONE);
                }

            }


            @Override
            public void onNotNetWork() {
                mTwinklingRefreshLayout.finishRefreshing();
            }

            @Override
            public void onFail(Throwable e) {
                mTwinklingRefreshLayout.finishRefreshing();
            }
        });
    }

    private void loadData() {
        index++;
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("category", "1");
        mHashMap.put("page_number", String.valueOf(index));
        mHashMap.put("page_size", "20");
        getHttpData(mHashMap, new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                mTwinklingRefreshLayout.finishLoadmore();
                MoneyRewardBean moneyRewardBean = new Gson().fromJson(data, MoneyRewardBean.class);
                if (moneyRewardBean.getData() != null && moneyRewardBean.getData().getList() != null) {
                    changeView(moneyRewardBean.getData().getHas_more() == 0 ? false : true);
                    if (moneyRewardBean.getData().getList().size() > 0) {
                        if (moneyRewardAdapter == null) {
                            moneyRewardAdapter = new MoneyRewardAdapter(MoneyRewardActivity.this, moneyRewardBean.getData().getList());
                            mRecyclerView.setAdapter(moneyRewardAdapter);
                        } else {
                            List<MoneyRewardBean.DataBean.ListBean> listBeans = new ArrayList<>();
                            for (int i = 0; i < moneyRewardBean.getData().getList().size(); i++) {
                                if (moneyRewardBean.getData().getList().get(i).getOrderNo() != "null" &&
                                        !TextUtils.isEmpty(moneyRewardBean.getData().getList().get(i).getOrderNo())) {
                                    listBeans.add(moneyRewardBean.getData().getList().get(i));
                                }
                            }
                            moneyRewardAdapter.addData(listBeans);
                        }
                    }
                }
            }

            @Override
            public void onNotNetWork() {
                mTwinklingRefreshLayout.finishLoadmore();
            }

            @Override
            public void onFail(Throwable e) {
                mTwinklingRefreshLayout.finishLoadmore();
            }
        });
    }

    private void getHttpData(Map<String, String> mHashMap, RetrofitApi.IResponseListener listener) {
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getPushMessageList(mHashMap), listener);
    }


    private void changeView(boolean hasMore) {
        hasNext = hasMore;
        if (hasNext) {
            mTwinklingRefreshLayout.setBottomView(new RefreshBottomView(MoneyRewardActivity.this));
        } else {
            mTwinklingRefreshLayout.setBottomView(new RefreshFooterView(MoneyRewardActivity.this));
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_version_back:
                finish();
                break;
            case R.id.login_btn:
                LoginActivity.starActivity(this);
                break;
        }
    }
}

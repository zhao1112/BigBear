package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.newversions.EventAdapter;
import com.newversions.MoneyRewardActivity;
import com.newversions.MoneyRewardAdapter;
import com.newversions.MoneyRewardBean;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.BuildConfig;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.ActivityMessageData;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenchen on 2018/8/13.
 */

public class EventMessageActivity extends BaseActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, EventMessageActivity.class);
        context.startActivity(intent);
    }

//    private TwinklingRefreshLayout mTwinklingRefreshLayout;
//    private RecyclerView mRecyclerView;
//    private View mEmptyView;
//    private HighlightButton highlightButton;
//    private TextView tip;
//
//    private EventAdapter moneyRewardAdapter;
//
//    private boolean hasNext = false;
//    private int index = 1;

    @Override
    public int layoutId() {
        return R.layout.new_activity_money_reward;
    }

    @Override
    public void init() {
//        initViews();
        findViewById(R.id.login_btn).setVisibility(View.GONE);
        findViewById(R.id.refresh_layout).setVisibility(View.GONE);
        findViewById(R.id.new_version_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
////        if (BearMallAplication.getInstance().getUser() != null) {
////            // 登陆过
////            mTwinklingRefreshLayout.setVisibility(View.VISIBLE);
////            initData();
////        } else {
////            // 未登录
////            mTwinklingRefreshLayout.setVisibility(View.GONE);
////            tip.setText("请先登录");
////            highlightButton.setVisibility(View.VISIBLE);
////        }
//    }
//
//    private void initViews() {
//        TextView top = findViewById(R.id.top);
//        top.setText("活动消息");
//        mTwinklingRefreshLayout = findViewById(R.id.refresh_layout);
//        mRecyclerView = findViewById(R.id.recycler_view);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mEmptyView = findViewById(R.id.empty_view);
//        highlightButton = findViewById(R.id.login_btn);
//        tip = findViewById(R.id.tip);
//        findViewById(R.id.new_version_back).setOnClickListener(this);
//        highlightButton.setOnClickListener(this);
//
//        mTwinklingRefreshLayout.setHeaderView(new RefreshHeadView(this));
//        mTwinklingRefreshLayout.setBottomView(new RefreshBottomView(this));
//
//        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
//            @Override
//            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
//                // 刷新
//                refreshData();
//            }
//
//            @Override
//            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
//                if (hasNext) {
//                    // 加载更多
//                    loadData();
//                } else {
//                    mTwinklingRefreshLayout.finishLoadmore();
//                }
//            }
//        });
//
//        if (BearMallAplication.getInstance().getUser() != null) {
//            // 登陆过
//            mTwinklingRefreshLayout.setVisibility(View.VISIBLE);
//            initData();
//        } else {
//            // 未登录
//            mTwinklingRefreshLayout.setVisibility(View.GONE);
//            tip.setText("请先登录");
//            highlightButton.setVisibility(View.VISIBLE);
//        }
//    }
//
//
//    private void initData() {
//        Map<String, String> mHashMap = new HashMap<>();
//        mHashMap.put("category", "3");
//        mHashMap.put("page_number", String.valueOf(index));
//        mHashMap.put("page_size", "20");
//        getHttpData(mHashMap, new RetrofitApi.IResponseListener() {
//            @Override
//            public void onSuccess(String data) throws JSONException {
//                ActivityMessageData moneyRewardBean = new Gson().fromJson(data, ActivityMessageData.class);
//                if (moneyRewardBean.getData() != null && moneyRewardBean.getData().getList() != null) {
//                    changeView(moneyRewardBean.getData().getHas_more() == 0 ? false : true);
//                    if (moneyRewardBean.getData().getList().size() > 0) {
//                        mTwinklingRefreshLayout.setVisibility(View.VISIBLE);
//                        moneyRewardAdapter = new EventAdapter(EventMessageActivity.this, moneyRewardBean.getData().getList());
//                        mRecyclerView.setAdapter(moneyRewardAdapter);
//                    } else {
//                        mTwinklingRefreshLayout.setVisibility(View.GONE);
//                        tip.setText("暂无数据");
//                        highlightButton.setVisibility(View.GONE);
//                    }
//                } else {
//                    mTwinklingRefreshLayout.setVisibility(View.GONE);
//                    tip.setText("暂无数据");
//                    highlightButton.setVisibility(View.GONE);
//                }
//
//            }
//
//            @Override
//            public void onNotNetWork() {
//
//            }
//
//            @Override
//            public void onFail(Throwable e) {
//
//            }
//        });
//
//
//    }
//
//    private void refreshData() {
//        index = 1;
//        Map<String, String> mHashMap = new HashMap<>();
//        mHashMap.put("category", "3");
//        mHashMap.put("page_number", String.valueOf(index));
//        mHashMap.put("page_size", "20");
//
//        getHttpData(mHashMap, new RetrofitApi.IResponseListener() {
//            @Override
//            public void onSuccess(String data) throws JSONException {
//
//                mTwinklingRefreshLayout.finishRefreshing();
//                ActivityMessageData moneyRewardBean = new Gson().fromJson(data, ActivityMessageData.class);
//                if (moneyRewardBean.getData() != null && moneyRewardBean.getData().getList() != null) {
//                    changeView(moneyRewardBean.getData().getHas_more() == 0 ? false : true);
//                    if (moneyRewardBean.getData().getList().size() > 0) {
//                        mTwinklingRefreshLayout.setVisibility(View.VISIBLE);
//                        if (moneyRewardAdapter == null) {
//                            moneyRewardAdapter = new EventAdapter(EventMessageActivity.this, moneyRewardBean.getData().getList());
//                            mRecyclerView.setAdapter(moneyRewardAdapter);
//                        } else {
//                            moneyRewardAdapter.setData(moneyRewardBean.getData().getList());
//                        }
//                    } else {
//                        mTwinklingRefreshLayout.setVisibility(View.GONE);
//                        tip.setText("暂无数据");
//                        highlightButton.setVisibility(View.GONE);
//                    }
//                } else {
//                    mTwinklingRefreshLayout.setVisibility(View.GONE);
//                    tip.setText("暂无数据");
//                    highlightButton.setVisibility(View.GONE);
//                }
//
//            }
//
//
//            @Override
//            public void onNotNetWork() {
//                mTwinklingRefreshLayout.finishRefreshing();
//            }
//
//            @Override
//            public void onFail(Throwable e) {
//                mTwinklingRefreshLayout.finishRefreshing();
//            }
//        });
//    }
//
//    private void loadData() {
//        index++;
//        Map<String, String> mHashMap = new HashMap<>();
//        mHashMap.put("category", "3");
//        mHashMap.put("page_number", String.valueOf(index));
//        mHashMap.put("page_size", "20");
//        getHttpData(mHashMap, new RetrofitApi.IResponseListener() {
//            @Override
//            public void onSuccess(String data) throws JSONException {
//                mTwinklingRefreshLayout.finishLoadmore();
//                ActivityMessageData moneyRewardBean = new Gson().fromJson(data, ActivityMessageData.class);
//                if (moneyRewardBean.getData() != null && moneyRewardBean.getData().getList() != null) {
//                    changeView(moneyRewardBean.getData().getHas_more() == 0 ? false : true);
//                    if (moneyRewardBean.getData().getList().size() > 0) {
//                        if (moneyRewardAdapter == null) {
//                            moneyRewardAdapter = new EventAdapter(EventMessageActivity.this, moneyRewardBean.getData().getList());
//                            mRecyclerView.setAdapter(moneyRewardAdapter);
//                        } else {
//                            moneyRewardAdapter.addData(moneyRewardBean.getData().getList());
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onNotNetWork() {
//                mTwinklingRefreshLayout.finishLoadmore();
//            }
//
//            @Override
//            public void onFail(Throwable e) {
//                mTwinklingRefreshLayout.finishLoadmore();
//            }
//        });
//    }
//
//    private void getHttpData(Map<String, String> mHashMap, RetrofitApi.IResponseListener listener) {
//        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getPushMessageList(mHashMap), listener);
//    }
//
//
//    private void changeView(boolean hasMore) {
//        hasNext = hasMore;
//        if (hasNext) {
//            mTwinklingRefreshLayout.setBottomView(new RefreshBottomView(EventMessageActivity.this));
//        } else {
//            mTwinklingRefreshLayout.setBottomView(new RefreshFooterView(EventMessageActivity.this));
//        }
//    }
//
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.new_version_back:
//                finish();
//                break;
//            case R.id.login_btn:
//                LoginActivity.starActivity(this);
//                break;
//        }
//    }
}

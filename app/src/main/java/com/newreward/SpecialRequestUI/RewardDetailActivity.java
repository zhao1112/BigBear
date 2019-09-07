package com.newreward.SpecialRequestUI;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.newreward.apdater.RewardDetailAdapter;
import com.newreward.bean.RewardDetailBean;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2019/2/18
 * @Describe 今日获得赏金数
 */
public class RewardDetailActivity extends BaseActivity {

    public static void startRewardDetailActivity(Activity context) {
        StarActivityUtil.starActivity(context, RewardDetailActivity.class);
    }


    @BindView(R.id.record_refresh_layout)
    TwinklingRefreshLayout refreshLayout;

    @BindView(R.id.record_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar_title)
    TextView titleTextView;

    @BindView(R.id.not_net_group)
    LinearLayout not_net_group;

    @BindView(R.id.empty)
    LinearLayout empty;

    private List<RewardDetailBean.DataBean.ListBean> datas;
    private RewardDetailAdapter rewardDetailAdapter;

    private boolean isloadMore;
    private int index;
    private boolean hasMore = true;

    @Override
    public int layoutId() {
        return R.layout.activity_reward_detail;
    }

    @Override
    public void init() {
        titleTextView.setText("今日赏金");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout.setHeaderView(new RefreshHeadView(this));
        refreshLayout.setBottomView(new RefreshBottomView(this));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                isloadMore = false;
                index = 1;
                getData(index);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {

                if (hasMore) {
                    isloadMore = true;
                    index++;
                    getData(index);
                } else {
                    refreshLayout.finishLoadmore();
                }

            }
        });
        datas = new ArrayList<>();
        rewardDetailAdapter = new RewardDetailAdapter(datas, this, 0);
        recyclerView.setAdapter(rewardDetailAdapter);
        refreshLayout.startRefresh();
    }

    private void getData(int page) {
        Map<String, String> params = new HashMap<>();
        params.put("page_number", page + "");
        params.put("page_size", "10");
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getMemberTodayRewardDatils(params), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    finishRefrsh();
                    not_net_group.setVisibility(View.GONE);
                    RewardDetailBean rewardDetailBean = new Gson().fromJson(data, RewardDetailBean.class);

                    if (rewardDetailBean.getData().hasMore()) {
                        hasMore = false;
                        refreshLayout.setBottomView(new RefreshFooterView(RewardDetailActivity.this));
                    } else {
                        hasMore = true;
                        refreshLayout.setBottomView(new RefreshBottomView(RewardDetailActivity.this));
                    }

                    if (isloadMore) {
                        datas.addAll(rewardDetailBean.getData().getList());
                    } else {
                        datas.clear();

                        if (rewardDetailBean.getData().getList().size() <= 0) {
                            empty.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            empty.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                        datas.addAll(rewardDetailBean.getData().getList());
                    }
                    rewardDetailAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNotNetWork() {
                finishRefrsh();
                not_net_group.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFail(Throwable e) {
                finishRefrsh();
            }
        });
    }


    @OnClick({R.id.toolbar_back})
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.toolbar_back:

                finish();

                break;
            default:
                break;
        }

    }

    private void finishRefrsh() {
        if (isloadMore) {
            refreshLayout.finishLoadmore();
        } else {
            refreshLayout.finishRefreshing();
        }

    }
}

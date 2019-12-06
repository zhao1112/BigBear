package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.RecordAdapter;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.SweetRecord;
import com.yunqin.bearmall.bean.UserInfo;
import com.yunqin.bearmall.ui.activity.contract.SwweetRecordContract;
import com.yunqin.bearmall.ui.activity.presenter.SweetRecordPresenter;
import com.yunqin.bearmall.ui.fragment.RecordFilterFragment;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.example.lamor.Faceplate;

/**
 * Created by chenchen on 2018/7/21.
 */

public class SweetRecordActivity extends BaseActivity implements SwweetRecordContract.UI, View.OnClickListener,
        RecordFilterFragment.OnFilterConditionChangedListener {

    private static final String TYPE = "type";
    private static final String ID = "id";

    /**
     * 0 收入 1 收入withID 2 消费
     *
     * @param type
     */
    public static void startIncomeActivity(int type, String id, Activity context) {
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        bundle.putString(ID, id);
        StarActivityUtil.starActivity(context, SweetRecordActivity.class, bundle);
    }


    @BindView(R.id.not_net_group)
    LinearLayout not_net_group;
    @BindView(R.id.record_refresh_layout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.record_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.top_right_text)
    TextView topRighTextView;
    @BindView(R.id.top_left_text)
    TextView topLeftTextView;
    @BindView(R.id.top_left_times)
    TextView leftTimesTextView;
    @BindView(R.id.toolbar_title)
    TextView titleTextView;
    @BindView(R.id.toolbar_right_text)
    TextView rightTextView;
    @BindView(R.id.empty)
    LinearLayout empty;

    private SweetRecordPresenter presenter;
    private boolean isloadMore;
    private int index;
    private List<SweetRecord.RecordBean> datas;
    private RecordAdapter adapter;
    private int type;
    //消费明细需要
    private int queryIdentify;
    //分别查询各项收入时需要
    private String id;

    private RecordFilterFragment fragment;

    @Override
    public int layoutId() {
        return R.layout.activity_income;
    }

    @Override
    public void init() {

        Faceplate.getDefault().performInjectLayoutLayers(this);

        type = getIntent().getIntExtra(TYPE, 0);
        id = getIntent().getStringExtra(ID);

        if (type == 0 || type == 1) {
            titleTextView.setText("收益记录");
            leftTimesTextView.setTextColor(Color.parseColor("#23A064"));
        } else {
            titleTextView.setText("BC糖果消费记录");
            leftTimesTextView.setTextColor(Color.parseColor("#666666"));
            rightTextView.setText("过滤");
        }

        try {
            UserInfo userBTInfo = BearMallAplication.getInstance().getUser();
            if (userBTInfo != null) {
                topRighTextView.setText(String.format(" 大熊ID：%s", userBTInfo.getData().getMember().getBigBearNumber()));
            }
        } catch (Exception e) {

        }

        presenter = new SweetRecordPresenter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout.setHeaderView(new RefreshHeadView(this));
        refreshLayout.setBottomView(new RefreshBottomView(this));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                isloadMore = false;
                index = 1;
                presenter.refresh(type, id, queryIdentify, SweetRecordActivity.this, index);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (hasMore) {
                    isloadMore = true;
                    index++;
                    presenter.refresh(type, id, queryIdentify, SweetRecordActivity.this, index);
                } else {
                    refreshLayout.finishLoadmore();
                }
            }
        });
//        refreshLayout.setEnableLoadmore(false);

        presenter.start(this);
        datas = new ArrayList<>();
        //0 收入 1 支出
        adapter = new RecordAdapter(datas, this, type == 2 ? 1 : 0);
        recyclerView.setAdapter(adapter);
        refreshLayout.startRefresh();

    }

    private void initFragment() {
        rightTextView.setText("收起");
        fragment = new RecordFilterFragment();
        fragment.setListener(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.filter_container, fragment);
//        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    private void hide() {
        rightTextView.setText("过滤");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    private void show() {
        rightTextView.setText("收起");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.show(fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    private void remove() {
        if (fragment == null) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }


    private void finishRefrsh() {
        if (isloadMore) {
            refreshLayout.finishLoadmore();
        } else {
            refreshLayout.finishRefreshing();
        }
    }

    private boolean hasMore = true;

    @Override
    public void finishLoadedData(SweetRecord record) {
        not_net_group.setVisibility(View.GONE);
        finishRefrsh();
        if (record.isSuccese()) {
            SweetRecord.DataBean dataBean = record.getData();
            if (dataBean.hasMore()) {
                hasMore = true;
                refreshLayout.setBottomView(new RefreshBottomView(this));
            } else {
                hasMore = false;
                refreshLayout.setBottomView(new RefreshFooterView(this));
            }
            if (isloadMore) {
                if (type == 2) {
                } else {
                    datas.addAll(dataBean.getIncomeDetail());
                }
            } else {
                datas.clear();
                if (type == 0 || type == 1) {
                    if (record.getData().getIncomeDetail().size() <= 0) {
                        empty.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        empty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    int count = dataBean.getIncomeCount();
                    leftTimesTextView.setTextColor(Color.parseColor("#23A064"));
                    topLeftTextView.setText("累计挖矿获利次数：");
                    leftTimesTextView.setText("" + count);
                    datas.addAll(dataBean.getIncomeDetail());
                } else {
                    if (record.getData().getPurchaseDetail().size() <= 0) {
                        empty.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        empty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    int count = dataBean.getPurchaseCount();
                    leftTimesTextView.setText("" + count);
                    leftTimesTextView.setTextColor(Color.parseColor("#666666"));
                    topLeftTextView.setText("累计挖矿消费次数：");
                    if (rightTextView.getVisibility() != View.VISIBLE && queryIdentify == 0) {
                        rightTextView.setVisibility(View.VISIBLE);
                    }
                    datas.addAll(dataBean.getPurchaseDetail());
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void failedLoadedData() {
        finishRefrsh();
    }


    @Override
    public void onNotNetWork() {
        not_net_group.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (type == 2) {
            if (fragment == null) {
                super.onBackPressed();
            } else if (!fragment.isHidden()) {
                hide();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.toolbar_back, R.id.toolbar_right_text, R.id.reset_load_data})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_right_text:
                if (fragment == null) {
                    initFragment();
                } else if (!fragment.isHidden()) {
                    hide();
                } else {
                    show();
                }
                break;
            case R.id.reset_load_data:
                isloadMore = false;
                index = 1;
                presenter.refresh(type, id, queryIdentify, SweetRecordActivity.this, index);
                break;
            default:
                break;
        }
    }

    @Override
    public void onChanged(String condition) {
        if (!TextUtils.isEmpty(condition)) {
            queryIdentify = Integer.parseInt(condition);
            refreshLayout.startRefresh();
        }
    }
}

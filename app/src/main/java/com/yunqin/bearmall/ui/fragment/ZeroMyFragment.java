package com.yunqin.bearmall.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.ZeroMyAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.ZeroMyItemBean;
import com.yunqin.bearmall.eventbus.CountDownFinishEvent;
import com.yunqin.bearmall.inter.ChangeCallBack;
import com.yunqin.bearmall.ui.activity.ZeroMoneyActivity;
import com.yunqin.bearmall.ui.fragment.contract.ZeroMyContract;
import com.yunqin.bearmall.ui.fragment.presenter.ZeroMyPresenter;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2018/8/2
 * @Describe
 */
public class ZeroMyFragment extends BaseFragment implements ZeroMyContract.UI, ChangeCallBack {
    @BindView(R.id.zero_list)
    RecyclerView zeroList;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;

    @BindView(R.id.no_net)
    View no_net;

    private ZeroMyContract.presenter presenter;

    ZeroMyAdapter zeroMyAdapter;

    private List<ZeroMyItemBean.DataBean.MemberGroupRecordListBean> mlist = new ArrayList<>();

    @Override
    public int layoutId() {
        return R.layout.fragment_zero_my;
    }


    @Override
    public void init() {
        EventBus.getDefault().register(this);

        presenter = new ZeroMyPresenter(getActivity(), this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        zeroList.setLayoutManager(linearLayoutManager);

        zeroMyAdapter = new ZeroMyAdapter(getActivity(), mlist, this);
        zeroList.setAdapter(zeroMyAdapter);

        initRefresh();
    }

    private void initRefresh() {
        refreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
        refreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        //refreshLayout.setEnableOverScroll(false);//越界回弹效果
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                presenter.start(false);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                presenter.start(true);
            }
        });
        refreshLayout.startRefresh();
    }

    @Override
    public void attachData(String data, boolean isLoadMore) {
        onFinishRe();
        no_net.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.VISIBLE);
        ZeroMyItemBean zeroMyItemBean = new Gson().fromJson(data, ZeroMyItemBean.class);
        if (zeroMyItemBean.getData().getHas_more() == 0) {
            refreshLayout.setEnableLoadmore(false);
        } else {
            refreshLayout.setEnableLoadmore(true);
        }

        if (!isLoadMore) {
            mlist.clear();
            if (zeroMyItemBean.getData().getMemberGroupRecordList().size() <= 0) {
                emptyView.setVisibility(View.VISIBLE);
                zeroList.setVisibility(View.GONE);

            } else {
                emptyView.setVisibility(View.GONE);
                zeroList.setVisibility(View.VISIBLE);
            }
        }

        // 校对倒计时
        for (ZeroMyItemBean.DataBean.MemberGroupRecordListBean itemInfo : zeroMyItemBean.getData().getMemberGroupRecordList()) {
            long curTime = System.currentTimeMillis();
            itemInfo.setEndTime(curTime + itemInfo.getRestTime());
        }

        mlist.addAll(zeroMyItemBean.getData().getMemberGroupRecordList());
        zeroMyAdapter.notifyDataSetChanged();
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Override
    public void onError() {
        onFinishRe();
        emptyView.setVisibility(View.GONE);
        no_net.setVisibility(View.VISIBLE);
        zeroList.setVisibility(View.GONE);
    }

    @Override
    public void onNoNet() {
        emptyView.setVisibility(View.GONE);
        no_net.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.GONE);
    }

    public void onFinishRe() {
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
    }

    @OnClick(R.id.reset_load_data)
    void click(View view) {
        refreshLayout.startRefresh();
    }

    @Override
    public void change() {
        ((ZeroMoneyActivity) getActivity()).switch2Fragment();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(CountDownFinishEvent countDownFinishEvent) {

        int whichRefresh = countDownFinishEvent.getWhichRefresh();

        if (whichRefresh == 1) {
            presenter.start(false);
        }
    }

}

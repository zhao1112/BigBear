package com.yunqin.bearmall.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.ZeroGoodsAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.BannerBean;
import com.yunqin.bearmall.bean.ZeroGoodsBean;
import com.yunqin.bearmall.eventbus.CountDownFinishEvent;
import com.yunqin.bearmall.ui.fragment.contract.ZeroGoodsContract;
import com.yunqin.bearmall.ui.fragment.presenter.ZeroGoodsPresenter;
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
public class ZeroGoodsFragment extends BaseFragment implements ZeroGoodsContract.UI {
    @BindView(R.id.zero_list)
    RecyclerView zeroList;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

    @BindView(R.id.no_net)
    View noNetView;


    ZeroGoodsAdapter zeroGoodsAdapter;

    private List<ZeroGoodsBean.DataBean.GroupPurchasingListBean> mlist = new ArrayList<>();

    private ZeroGoodsContract.presenter presenter;

    @Override
    public int layoutId() {
        return R.layout.fragment_zero_goods;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
    }

    @Override
    public void init() {
        showLoading();

        presenter = new ZeroGoodsPresenter(getActivity(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        zeroList.setLayoutManager(linearLayoutManager);
        zeroGoodsAdapter = new ZeroGoodsAdapter(getActivity(), mlist);
        zeroList.setAdapter(zeroGoodsAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start(false);
        presenter.getBannerData();
        presenter.getCenterAdData();
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
                presenter.getBannerData();
                presenter.getCenterAdData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                presenter.start(true);
            }
        });
    }

    public void onFinishRe() {
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
    }

    @Override
    public void attachData(ZeroGoodsBean zeroGoodsBean, boolean isLoadMore) {
        hiddenLoadingView();
        noNetView.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.VISIBLE);
        onFinishRe();
        if (zeroGoodsBean.getData().getHas_more() == 0) {
            refreshLayout.setEnableLoadmore(false);
        } else {
            refreshLayout.setEnableLoadmore(true);
        }
        if (!isLoadMore) {
            mlist.clear();
        }
        mlist.addAll(zeroGoodsBean.getData().getGroupPurchasingList());
        zeroGoodsAdapter.notifyDataSetChanged();
    }

    @Override
    public void attachBannerData(BannerBean bannerBean) {
        zeroGoodsAdapter.setBannerData(bannerBean);
    }

    @Override
    public void attachCenterAdData(BannerBean bannerBean) {
        if (bannerBean != null && bannerBean.getData().getAdMobileList().size() > 0) {
            zeroGoodsAdapter.setCenterAdUrl(bannerBean.getData().getAdMobileList().get(0));
        }
    }

    @Override
    public void onError() {
        hiddenLoadingView();
        onFinishRe();
    }

    @OnClick(R.id.reset_load_data)
    void click(View view) {
        refreshLayout.startRefresh();
    }

    @Override
    public void noNet() {
        hiddenLoadingView();
        noNetView.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.GONE);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(CountDownFinishEvent countDownFinishEvent) {

        int whichRefresh = countDownFinishEvent.getWhichRefresh();

        if (whichRefresh == 0) {
            presenter.start(false);
        }
    }


}

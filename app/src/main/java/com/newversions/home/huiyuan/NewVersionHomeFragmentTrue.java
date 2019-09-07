package com.newversions.home.huiyuan;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.newversions.home.HomeBean;
import com.newversions.home.NewHomeAd;
import com.newversions.home.NewVersionHomeContract;
import com.newversions.home.NewVersionHomePresenter;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.BannerBean;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import butterknife.BindView;

/**
 * Create By Master
 * On 2019/1/3 16:13
 */
public class NewVersionHomeFragmentTrue extends BaseFragment implements NewVersionHomeContractTrue.View {

    @BindView(R.id.n_v_recycler_view)
    public RecyclerView mRecyclerView;
    @BindView(R.id.n_v_refreshLayout)
    TwinklingRefreshLayout mTwinklingRefreshLayout;
    @BindView(R.id.n_v_not_net)
    View mNoNetView;

    private boolean hasNext = false;
    private NewVersionHomeAdapterTrue mNewVersionHomeAdapter;
    private NewVersionHomeContractTrue.Presenter mPresenter;

    @Override
    public int layoutId() {
        return R.layout.new_version_home_layout;
    }

    @Override
    public void init() {
        mPresenter = new NewVersionHomePresenterTrue(getActivity(), this);
        mTwinklingRefreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
//        mTwinklingRefreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        mTwinklingRefreshLayout.setBottomView(new RefreshFooterView(getActivity()));
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRefresh();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (hasNext) {
                    mPresenter.onLoadMore();
                } else {
                    mTwinklingRefreshLayout.finishLoadmore();
                }
            }
        });

        mNewVersionHomeAdapter = new NewVersionHomeAdapterTrue(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mNewVersionHomeAdapter.getItemColumnCount(position);
            }
        });
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mNewVersionHomeAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

                int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();

                Log.e("RRR", "" + isSlidingToLast);
                Log.e("RRR", "lastVisibleItem " + lastVisibleItem);
                Log.e("RRR", "totalItemCount " + totalItemCount);


                if (isSlidingToLast && lastVisibleItem == (totalItemCount - 1)) {
                    mPresenter.onLoadMore();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy > 0;
            }
        });
        mPresenter.init();
    }

    @Override
    public void showLoad() {
        showLoading();
    }

    @Override
    public void hideLoad() {
        hiddenLoadingView();
    }

    @Override
    public void onNotNetWork() {
//        mNoNetView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefreshFinish() {
        mTwinklingRefreshLayout.finishRefreshing();
    }

    @Override
    public void onLoadMoreFinish() {
        mTwinklingRefreshLayout.finishLoadmore();
    }

    @Override
    public void onHasMore(boolean hasMore) {
        this.hasNext = hasMore;
        if (hasMore) {
            mTwinklingRefreshLayout.setEnableLoadmore(false);
        } else {
            mTwinklingRefreshLayout.setEnableLoadmore(true);
        }
    }

    @Override
    public void attachData(HuiYuanHomeBean homeBean) {
        mNoNetView.setVisibility(View.GONE);
        mNewVersionHomeAdapter.setData(homeBean);
    }

    @Override
    public void attachAddData(HuiYuanHomeBean homeBean) {
        mNewVersionHomeAdapter.addData(homeBean);
    }

    @Override
    public void attachBannerData(BannerBean homeAd) {
        mNewVersionHomeAdapter.setBannerData(homeAd);
    }

}

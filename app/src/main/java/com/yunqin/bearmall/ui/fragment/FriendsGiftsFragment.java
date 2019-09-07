package com.yunqin.bearmall.ui.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.SweetsAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.SweetsBt;
import com.yunqin.bearmall.ui.fragment.contract.GiveFriendsContract;
import com.yunqin.bearmall.ui.fragment.presenter.FriendsGiftsPresenter;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 好友赠送糖果Fragment
 */
public class FriendsGiftsFragment extends BaseFragment implements GiveFriendsContract.UI {


    @BindView(R.id.list_view)
    ListView list_view;

    @BindView(R.id.empty_text)
    TextView empty_text;

    @BindView(R.id.empty_view)
    View view;

    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

    @BindView(R.id.not_net)
    View not_net;


    GiveFriendsContract.presenter presenter;

    SweetsAdapter sweetsAdapter;

    private int page_numer = 1;

    private int isLoadMoreOrRefresh = 1;


    @OnClick({R.id.reset_load_data})
    public void onSelect(View view) {
        Constans.params.clear();
        Constans.params.put("page_number", "1");
        presenter.refresh(Constans.params);
    }


    @Override
    public int layoutId() {
        return R.layout.fragment_bt_sweets;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLayout.startRefresh();
    }

    @Override
    public void init() {
        refreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
        refreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        presenter = new FriendsGiftsPresenter(getActivity(), this);
        sweetsAdapter = new SweetsAdapter(getActivity(), 2);
        list_view.setEmptyView(view);
        list_view.setAdapter(sweetsAdapter);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page_numer = 1;
                isLoadMoreOrRefresh = 1;
                Constans.params.clear();
                Constans.params.put("page_number", "" + page_numer);
                presenter.refresh(Constans.params);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (hasMore) {
                    isLoadMoreOrRefresh = 2;
                    Constans.params.clear();
                    Constans.params.put("page_number", ++page_numer + "");
                    presenter.refresh(Constans.params);
                } else {
                    refreshLayout.finishLoadmore();
                }

            }
        });
        refreshLayout.startRefresh();
    }


    private boolean hasMore = true;

    @Override
    public void attachhData(SweetsBt sweetsBt) {

        not_net.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.VISIBLE);

        if (sweetsBt.getData().getHas_more() == 0) {
//            refreshLayout.setEnableLoadmore(false);
            hasMore = false;
            refreshLayout.setBottomView(new RefreshFooterView(getActivity()));
        } else {
//            refreshLayout.setEnableLoadmore(true);
            hasMore = true;
            refreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        }
        if (isLoadMoreOrRefresh == 1) {
            sweetsAdapter.setData(sweetsBt.getData().getPresentList());
        } else {
            sweetsAdapter.addData(sweetsBt.getData().getPresentList());
        }
        onFinishRe();
    }

    @Override
    public void onError() {
        onFinishRe();
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
        not_net.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.GONE);
    }

    public void onFinishRe() {
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
    }

}

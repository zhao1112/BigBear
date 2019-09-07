package com.yunqin.bearmall.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.GuideOneTypeAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.GuideBean;
import com.yunqin.bearmall.ui.activity.presenter.GuidePresent;
import com.yunqin.bearmall.ui.fragment.contract.GuideContract;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenchen on 2018/7/27.
 */

public class GuideFragment extends BaseFragment implements GuideContract.IUI {

    public static GuideFragment guideFragmentWithID(String id) {

        GuideFragment fragment = new GuideFragment();

        Bundle bundle = new Bundle();

        bundle.putString("GID", id);

        fragment.setArguments(bundle);

        return fragment;
    }

    @BindView(R.id.toolbar_back)
    ImageView backView;

    @BindView(R.id.toolbar_title)
    TextView titleView;


    @BindView(R.id.guide_refresh_layout)
    TwinklingRefreshLayout refreshLayout;

    @BindView(R.id.guide_recycler)
    RecyclerView recyclerView;

    private boolean isloadMore;
    private int index;
    private GuidePresent present;
    //    private GuideAdapter adapter;
    private GuideOneTypeAdapter adapter;
    private List<GuideBean.Article> datas;
    private String gID;

    @Override
    public int layoutId() {
        return R.layout.fragment_guide;
    }

    @Override
    public void init() {

        backView.setVisibility(View.INVISIBLE);
        titleView.setText("推荐");

        Bundle bundle = getArguments();
        if (bundle != null) {
            gID = bundle.getString("GID");
        }

        present = new GuidePresent(this);
        present.start(getContext());

        datas = new ArrayList<>();


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setNestedScrollingEnabled(false);
        refreshLayout.setHeaderView(new RefreshHeadView(getContext()));
        refreshLayout.setBottomView(new RefreshBottomView(getContext()));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                isloadMore = false;
                index = 1;
                present.refrshData(index, gID);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (hasMore) {
                    isloadMore = true;
                    index++;
                    present.refrshData(index, gID);
                } else {
                    refreshLayout.finishLoadmore();
                }
            }
        });
//        refreshLayout.setEnableLoadmore(false);
//        adapter = new GuideOneTypeAdapter(datas, getActivity());
//        recyclerView.setAdapter(adapter);

        showLoading();
//        present.refrshData(1, gID);

        refreshLayout.startRefresh();

    }

    @Override
    public void onGetGuideListData(GuideBean guideBean) {
        not_net.setVisibility(View.GONE);
        finishRefrsh();
        List<GuideBean.Article> datas = guideBean.getData().getGuideArticleList();
        if (isloadMore) {
            this.datas.addAll(datas);
            adapter.notifyDataSetChanged();
        } else {
            this.datas.clear();
            this.datas.addAll(datas);
            if (adapter == null){
                adapter = new GuideOneTypeAdapter(this.datas, getActivity());
                recyclerView.setAdapter(adapter);
            }else {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onLoadError() {
        finishRefrsh();
    }


    @BindView(R.id.not_net)
    View not_net;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onNotNetWork() {
        finishRefrsh();
        not_net.setVisibility(View.VISIBLE);
    }

    private boolean hasMore = true;

    @Override
    public void onHasMore(boolean hasMore) {
        this.hasMore = hasMore;
        if (hasMore) {
            refreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        } else {
            refreshLayout.setBottomView(new RefreshFooterView(getActivity()));
        }
    }

    private void finishRefrsh() {
        hiddenLoadingView();
        if (isloadMore) {
            refreshLayout.finishLoadmore();
        } else {
            refreshLayout.finishRefreshing();
        }

    }

    @OnClick({R.id.reset_load_data})
    public void onSelect(View view) {
        isloadMore = false;
        index = 1;
        present.refrshData(index, gID);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}

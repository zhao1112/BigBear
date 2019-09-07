package com.yunqin.bearmall.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.RefundAdapter;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.ui.activity.contract.RefundActivityContract;
import com.yunqin.bearmall.ui.activity.presenter.RefundPresenter;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.example.lamor.Faceplate;
import cn.example.lamor.container.MultiStateConfigurator;
import cn.example.lamor.container.MultiStateContainer;
import cn.example.lamor.container.StateController;
import cn.example.lamor.utils.MultiStateLayout;

/**
 * 售后退款
 *
 * @author Master
 */

public class RefundActivity extends BaseActivity implements RefundActivityContract.UI, MultiStateConfigurator, MultiStateContainer {

    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;

    @BindView(R.id.toolbar_title)
    TextView textView;

    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout twinklingRefreshLayout;


    RefundActivityContract.Presenter present;


    @Override
    public int layoutId() {
        return R.layout.activity_refund;
    }

    @Override
    public void init() {
        Faceplate.getDefault().performInjectLayoutLayers(this);
        textView.setText("退货/售后");
        present = new RefundPresenter(this, this);
        present.init(this);
    }


    @OnClick({R.id.toolbar_back})
    public void click(View view) {
        finish();
    }


    @Override
    public void attachAdapter(RefundAdapter adapter) {

        if (adapter == null || adapter.getData() == null || adapter.getData().size() == 0) {
            mStateController.showEmpty(false);
            return;
        }

        mStateController.showContent(false);

        recycle_view.setLayoutManager(new LinearLayoutManager(this));
        recycle_view.setAdapter(adapter);

        twinklingRefreshLayout.setHeaderView(new RefreshHeadView(this));
        twinklingRefreshLayout.setBottomView(new RefreshBottomView(this));
        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {


            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                present.shuaXin(RefundActivity.this);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (hasMore) {
                    present.jiaZaiGengDuo(RefundActivity.this);
                } else {
                    twinklingRefreshLayout.finishLoadmore();
                }
            }
        });


    }


    private boolean hasMore = true;


    @Override
    public void showLoad() {
        showLoading();
    }

    @Override
    public void hideLoad() {
        hiddenLoadingView();
    }

    @Override
    public void jiaZaiGengDuoFinish(boolean isMore) {
        hasMore = isMore;
        if (isMore) {
            twinklingRefreshLayout.setBottomView(new RefreshBottomView(this));
        } else {
            twinklingRefreshLayout.setBottomView(new RefreshFooterView(this));
        }
        twinklingRefreshLayout.finishLoadmore();
    }

    @Override
    public void shuaXinFinish() {
        twinklingRefreshLayout.finishRefreshing();
    }


    private StateController mStateController;


    @Override
    public void onNotNetWork() {
        mStateController.showError(false);
    }

    @Override
    public void attachState(MultiStateLayout multiStateLayout) {
        View view = LayoutInflater.from(this).inflate(R.layout.network_error_layout, multiStateLayout, false);
        HighlightButton mHighlightButton = view.findViewById(R.id.reset_load_data);
        mHighlightButton.setOnClickListener(v -> present.init(RefundActivity.this));
        multiStateLayout.attachLayout(MultiStateLayout.STATE_ERROR, view);
        multiStateLayout.attachLayout(MultiStateLayout.STATE_EMPTY, R.layout.empty_refund);
    }

    @Override
    public Object getTarget() {
        return recycle_view;
    }

    @Override
    public void onRefTypeReturned(StateController stateController) {
        this.mStateController = stateController;
    }
}

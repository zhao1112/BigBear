package com.bbcoupon.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bbcoupon.ui.adapter.SweetAdapter;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.video.videocontroller.StandardVideoController;
import com.video.videoplayer.player.PlayerActivity;
import com.video.videoplayer.player.VideoView;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.SweetRecord;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.fragment
 * @DATE 2020/6/16
 */
public class SweetFragment extends BaseFragment implements RequestContract.RequestView {

    @BindView(R.id.sweet_swnume)
    TextView mSweetSwnume;
    @BindView(R.id.sweet_recycler)
    RecyclerView mSweetRecycler;
    @BindView(R.id.sweet_swrefresh)
    TwinklingRefreshLayout mSweetSwrefresh;
    @BindView(R.id.sweet_top)
    LinearLayout mSweetTop;
    @BindView(R.id.bearmall_video)
    VideoView mBearmallVideo;

    private SweetAdapter sweetAdapter;
    private RequestPresenter presenter;
    private int page = 1;
    private boolean hasMore = true;

    @Override
    public int layoutId() {
        return R.layout.fragment_sweet;
    }

    @Override
    public void init() {

        mSweetSwnume.setText("今日个人获得糖果数：1433 个");
        mSweetTop.setVisibility(View.VISIBLE);

        mSweetSwrefresh.setHeaderView(new RefreshHeadView(getActivity()));
        mSweetSwrefresh.setBottomView(new RefreshBottomView(getActivity()));
        mSweetSwrefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page = 1;
                sweetAdapter.deleteList();
                getonIncomeRecordList();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                page++;
                getonIncomeRecordList();
            }
        });

        sweetAdapter = new SweetAdapter(getActivity());
        mSweetRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSweetRecycler.setAdapter(sweetAdapter);

        presenter = new RequestPresenter();
        presenter.setRelation(this);

        getonIncomeRecordList();
    }

    private void getonIncomeRecordList() {
        Map<String, String> map = new HashMap<>();
        map.put("page_number", page + "");
        map.put("page_size", "10");
        presenter.onIncomeRecordList(getActivity(), map);
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof SweetRecord) {//今日糖果记录
            SweetRecord sweetRecord = (SweetRecord) data;
            if (sweetRecord.getData().hasMore()) {
                hasMore = true;
                mSweetSwrefresh.setBottomView(new RefreshBottomView(getActivity()));
            } else {
                hasMore = false;
                mSweetSwrefresh.setBottomView(new RefreshFooterView(getActivity()));
            }
            if (sweetRecord.getData().getIncomeDetail() != null && sweetRecord.getData().getIncomeDetail().size() > 0) {
                sweetAdapter.addList(sweetRecord.getData().getIncomeDetail());
            } else {

            }
        }
        mSweetSwrefresh.finishRefreshing();
        mSweetSwrefresh.finishLoadmore();
    }

    @Override
    public void onNotNetWork() {
        mSweetSwrefresh.finishRefreshing();
        mSweetSwrefresh.finishLoadmore();
    }

    @Override
    public void onFail(Throwable e) {
        mSweetSwrefresh.finishRefreshing();
        mSweetSwrefresh.finishLoadmore();
    }
}

package com.bbcoupon.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bbcoupon.ui.adapter.SweetFanshAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import butterknife.BindView;

/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.fragment
 * @DATE 2020/6/16
 */
public class FansSweetFragment extends BaseFragment {

    @BindView(R.id.sweet_fans_swnume)
    TextView mSweetFansSwnume;
    @BindView(R.id.sweet_fansh_recycler)
    RecyclerView mSweetFanshRecycler;
    @BindView(R.id.sweet_fansh_swrefresh)
    TwinklingRefreshLayout mSweetFanshSwrefresh;


    private SweetFanshAdapter sweetFanshAdapter;

    @Override
    public int layoutId() {
        return R.layout.fragment_fanssweet;
    }

    @Override
    public void init() {

        mSweetFansSwnume.setText("今日粉丝推荐糖果获得数：6589个");

        mSweetFanshSwrefresh.setHeaderView(new RefreshHeadView(getActivity()));
        mSweetFanshSwrefresh.setBottomView(new RefreshBottomView(getActivity()));
        mSweetFanshSwrefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {

            }
        });

        sweetFanshAdapter = new SweetFanshAdapter(getActivity());
        mSweetFanshRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSweetFanshRecycler.setAdapter(sweetFanshAdapter);

    }

}

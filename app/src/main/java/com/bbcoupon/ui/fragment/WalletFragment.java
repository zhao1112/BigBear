package com.bbcoupon.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bbcoupon.ui.adapter.SweetAdapter;
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
public class WalletFragment extends BaseFragment {

    @BindView(R.id.sweet_swnume)
    TextView mSweetSwnume;
    @BindView(R.id.sweet_recycler)
    RecyclerView mSweetRecycler;
    @BindView(R.id.sweet_swrefresh)
    TwinklingRefreshLayout mSweetSwrefresh;
    @BindView(R.id.sweet_top)
    LinearLayout mSweetTop;

    private SweetFanshAdapter sweetAdapter;

    @Override
    public int layoutId() {
        return R.layout.fragment_sweet;
    }

    @Override
    public void init() {

        mSweetTop.setVisibility(View.GONE);

        mSweetSwrefresh.setHeaderView(new RefreshHeadView(getActivity()));
        mSweetSwrefresh.setBottomView(new RefreshBottomView(getActivity()));
        mSweetSwrefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {

            }
        });

        sweetAdapter = new SweetFanshAdapter(getActivity());
        mSweetRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSweetRecycler.setAdapter(sweetAdapter);

    }

}

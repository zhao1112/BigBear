package com.yunqin.bearmall.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.MenuShopGoodsAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.ShopGoods;
import com.yunqin.bearmall.ui.fragment.contract.ShopGoodsContract;
import com.yunqin.bearmall.ui.fragment.presenter.ShopGoodsPresenter;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;

/**
 * 点击Menu 跳转到商品
 *
 * @author Master
 */

public class ShopGoodsFragment extends BaseFragment implements ShopGoodsContract.UI {


    public static final int PRICE = 0;
    public static final int SALES = 1;
    public static final int SCORE = 2;

    @BindView(R.id.menu_goods_recycler_view)
    RecyclerView mRecyclerView;


    @BindViews({R.id.text1, R.id.text2, R.id.text3})
    List<TextView> textViews;

    @BindViews({R.id.view1, R.id.view2, R.id.view3})
    List<View> views;

    @BindViews({R.id.price, R.id.sales, R.id.score})
    List<LinearLayout> linearLayouts;

    @BindView(R.id.sort)
    LinearLayout sort;

    @BindView(R.id.no_data_layout)
    LinearLayout no_data_layout;

    @BindView(R.id.twink_layout)
    TwinklingRefreshLayout mTwinklingRefreshLayout;

    ShopGoodsContract.Presenter presenter;


    private Map<String, String> mHashMap = new HashMap<>();


    @Override
    public int layoutId() {
        return R.layout.fragment_menu_goods;
    }

    @Override
    public void init() {
        mRecyclerView.setNestedScrollingEnabled(false);
        sort.setVisibility(View.GONE);
        mHashMap.clear();

        try {
            Bundle bundle = getArguments();
            mHashMap.put("store_id", (String) bundle.get("store_id"));
            mHashMap.put("tag_id", (String) bundle.get("tag_id"));
        } catch (Exception e) {
        }

        presenter = new ShopGoodsPresenter(getActivity(), this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);


        mTwinklingRefreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
        mTwinklingRefreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                // TODO 刷新
                presenter.onShuaXin();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                // TODO 加载更多
                if (hasMore) {
                    presenter.onJiaZai();
                } else {
                    mTwinklingRefreshLayout.finishLoadmore();
                }

            }
        });

        presenter.star();


    }

    @Override
    public void attachAdapter(MenuShopGoodsAdapter adapter, ShopGoods shopGoods) {
        // TODO 设置Adapter
        if (shopGoods.getData().getProductList().size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            no_data_layout.setVisibility(View.GONE);
            mRecyclerView.setAdapter(adapter);
        } else {
            no_data_layout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public Map getParams() {
        return mHashMap;
    }


    private boolean hasMore = true;

    @Override
    public void onHasMore(boolean hasMore) {
        this.hasMore = hasMore;
        if (hasMore) {
            mTwinklingRefreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        } else {
            mTwinklingRefreshLayout.setBottomView(new RefreshFooterView(getActivity()));
        }
    }


    @Override
    public void shuaXinFinish() {
        mTwinklingRefreshLayout.finishRefreshing();
    }

    @Override
    public void jiaZaiFinish() {
        mTwinklingRefreshLayout.finishLoadmore();
    }
}

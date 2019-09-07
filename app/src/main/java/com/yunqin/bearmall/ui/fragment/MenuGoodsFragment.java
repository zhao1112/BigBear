package com.yunqin.bearmall.ui.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.MenuGoodsAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.MenuGoods;
import com.yunqin.bearmall.inter.OnFragmentInteractionListener;
import com.yunqin.bearmall.ui.fragment.contract.MenuGoodsContract;
import com.yunqin.bearmall.ui.fragment.presenter.MenuGoodsPresenter;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

import static com.yunqin.bearmall.ui.fragment.contract.MenuGoodsContract.Rank.NONE;

/**
 * 点击Menu 跳转到商品
 *
 * @author Master
 */

public class MenuGoodsFragment extends BaseFragment implements MenuGoodsContract.UI {

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


    @BindView(R.id.twink_layout)
    TwinklingRefreshLayout mTwinklingRefreshLayout;


    MenuGoodsContract.Presenter presenter;

    private int LAST_INDEX = 0;
    private int RANK = MenuGoodsContract.Rank.NONE;

    @Override
    public int layoutId() {
        return R.layout.fragment_menu_goods;
    }


    private String valueType = "";
    private String Category_id = "";


    @Override
    public void init() {

        mTwinklingRefreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
        mTwinklingRefreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                // TODO 刷新
                presenter.priceRank(RANK);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                // TODO 加载更多
                if (hasMore) {
                    presenter.loadMore(RANK);
                } else {
                    mTwinklingRefreshLayout.finishLoadmore();
                }

            }
        });


        presenter = new MenuGoodsPresenter(getActivity(), this);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getString("data", null) != null) {
            String data = bundle.getString("data");
            valueType = bundle.getString("valueType", "");
            presenter.startBundle(data);
        } else if (bundle != null && bundle.getInt("Category_id", -1) != -1) {
            Category_id = String.valueOf(bundle.getInt("Category_id"));
            presenter.priceRank(NONE);
        } else {
            presenter.priceRank(NONE);
        }
    }


    @BindView(R.id.no_data_layout)
    LinearLayout no_data_layout;

    private OnFragmentInteractionListener mOnFragmentInteractionListener = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mOnFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            mOnFragmentInteractionListener = null;
        }


    }

    @Override
    public void attachAdapter(MenuGoodsAdapter adapter) {
        // TODO 设置Adapter
        if (mOnFragmentInteractionListener != null) {
            mOnFragmentInteractionListener.updateFiltrateView();
        }
        hiddenLoadingView();
        if (adapter == null || adapter.getData().getData().getProductList().size() == 0) {
            no_data_layout.setVisibility(View.VISIBLE);
            mTwinklingRefreshLayout.setVisibility(View.GONE);
        } else {
            no_data_layout.setVisibility(View.GONE);
            mTwinklingRefreshLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setAdapter(adapter);
        }

    }

    @Override
    public String getValueType() {
        return valueType;
    }


    @Override
    public String getCategory_id() {
        return Category_id;
    }

    @Override
    public void finishRefresh(boolean isMore) {
        hiddenLoadingView();
        mTwinklingRefreshLayout.finishRefreshing();
        mTwinklingRefreshLayout.finishLoadmore();
    }


    @Override
    public void updateView(MenuGoodsAdapter menuShopsAdapter) {

        if (mOnFragmentInteractionListener != null) {
            mOnFragmentInteractionListener.updateFiltrateView();
        }

        hiddenLoadingView();
        if (menuShopsAdapter.getData().getData().getProductList().size() == 0) {
            no_data_layout.setVisibility(View.VISIBLE);
            mTwinklingRefreshLayout.setVisibility(View.GONE);
        } else {
            no_data_layout.setVisibility(View.GONE);
            mTwinklingRefreshLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.price, R.id.sales, R.id.score})
    void onClick(View view) {
        showLoading();
        switch (view.getId()) {
            case R.id.price:
                changeTextColor(PRICE);
                presenter.priceRank(RANK == MenuGoodsContract.Rank.UP ? MenuGoodsContract.Rank.DOWN : MenuGoodsContract.Rank.UP);
                RANK = RANK == MenuGoodsContract.Rank.UP ? MenuGoodsContract.Rank.DOWN : MenuGoodsContract.Rank.UP;
                break;
            case R.id.sales:
                if (LAST_INDEX == SALES) {
                    hiddenLoadingView();
                    return;
                }
                changeTextColor(SALES);
                presenter.salesRank();
                break;
            case R.id.score:
                if (LAST_INDEX == SCORE) {
                    hiddenLoadingView();
                    return;
                }
                changeTextColor(SCORE);
                presenter.scoreRank();
                break;
        }
    }

    public void changeTextColor(int whichone) {
        LAST_INDEX = whichone;
        for (int i = 0; i < textViews.size(); i++) {
            if (whichone == i) {
                views.get(i).setVisibility(View.VISIBLE);
                textViews.get(i).setTextColor(Color.parseColor("#23A064"));
            } else {
                views.get(i).setVisibility(View.INVISIBLE);
                textViews.get(i).setTextColor(Color.parseColor("#666666"));
            }
        }
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
    public void showLoad() {
        showLoading();
    }

    @Override
    public void hideLoad() {
        hiddenLoadingView();
    }

    public MenuGoods getData() {
        if (presenter.getData() == null) {
            return null;
        }
        return presenter.getData().getData();
    }


    public void updateSearch(String searchData, String attr_) {
        valueType = searchData;
        Category_id = "";

        presenter.updateSearch(searchData, attr_);
    }


}

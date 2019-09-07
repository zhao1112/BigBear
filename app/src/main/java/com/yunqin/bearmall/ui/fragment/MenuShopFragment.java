package com.yunqin.bearmall.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.MenuShopsAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.MenuShop;
import com.yunqin.bearmall.inter.OnFragmentInteractionListener;
import com.yunqin.bearmall.ui.fragment.contract.MenuShopFragmentContract;
import com.yunqin.bearmall.ui.fragment.presenter.MenuShopFragmentPresenter;
import com.yunqin.bearmall.widget.CustomRecommendView;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import butterknife.BindView;

/**
 * @author AYWang
 * @create 2018/7/13
 * @Describe
 */
public class MenuShopFragment extends BaseFragment implements MenuShopFragmentContract.UI {


    @BindView(R.id.shop_list)
    RecyclerView shopList;


    @BindView(R.id.custom_recommend_view)
    CustomRecommendView mCustomRecommendView;


    @BindView(R.id.refresh_view)
    TwinklingRefreshLayout mTwinklingRefreshLayout;


    private MenuShopFragmentContract.Presenter menuShopFragmentPresenter;

    private String searchValue = "";
    private String Category_id = "";

    @Override
    public int layoutId() {
        return R.layout.fragment_menu_shop;
    }

    @Override
    public void init() {


        mTwinklingRefreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
        mTwinklingRefreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                // TODO 刷新
                menuShopFragmentPresenter.start(getActivity(), searchValue, Category_id);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                // TODO 加载更多

                if (hasMore) {
                    menuShopFragmentPresenter.loadMore(getActivity(), searchValue, Category_id);
                } else {
                    mTwinklingRefreshLayout.finishLoadmore();
                }
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            searchValue = bundle.getString("searchValue", "");
            Category_id = String.valueOf(bundle.getInt("Category_id"));
        }
        shopList.setLayoutManager(new LinearLayoutManager(getActivity()));
        menuShopFragmentPresenter = new MenuShopFragmentPresenter(this, getActivity());
        menuShopFragmentPresenter.start(getActivity(), searchValue, Category_id);
    }


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
    public void attachAdapter(MenuShopsAdapter menuShopsAdapter) {

        if (menuShopsAdapter.getData() == null || menuShopsAdapter.getData().size() == 0) {
            if (mCustomRecommendView.getVisibility() == View.VISIBLE) {
                return;
            }
            mTwinklingRefreshLayout.setVisibility(View.GONE);
            mCustomRecommendView.setVisibility(View.VISIBLE);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            mCustomRecommendView.setImageSrc(R.drawable.search_null);
            mCustomRecommendView.setTvContent("抱歉，没有找到你想要的东东");
            mCustomRecommendView.setManager(gridLayoutManager);
            mCustomRecommendView.start(getActivity());
        } else {
            mCustomRecommendView.setVisibility(View.GONE);
            mTwinklingRefreshLayout.setVisibility(View.VISIBLE);

            shopList.setAdapter(menuShopsAdapter);
        }

    }

    @Override
    public void updateView(MenuShopsAdapter menuShopsAdapter) {

        if (menuShopsAdapter.getData() == null || menuShopsAdapter.getData().size() == 0) {
            if (mCustomRecommendView.getVisibility() == View.VISIBLE) {
                return;
            }
            mTwinklingRefreshLayout.setVisibility(View.GONE);
            mCustomRecommendView.setVisibility(View.VISIBLE);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            mCustomRecommendView.setImageSrc(R.drawable.search_null);
            mCustomRecommendView.setTvContent("抱歉，没有找到你想要的东东");
            mCustomRecommendView.setManager(gridLayoutManager);
            mCustomRecommendView.start(getActivity());
        } else {
            mCustomRecommendView.setVisibility(View.GONE);
            mTwinklingRefreshLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading(boolean hasMore) {
        hiddenLoadingView();
        mTwinklingRefreshLayout.finishRefreshing();
        mTwinklingRefreshLayout.finishLoadmore();


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


    public void updateSearch(String searchData) {

        searchValue = searchData;
        Category_id = "";

        menuShopFragmentPresenter.updateSearch(searchData);
    }


    public MenuShop getData() {
        if (menuShopFragmentPresenter.getData() == null) {
            return null;
        }
        return menuShopFragmentPresenter.getData();
    }


}

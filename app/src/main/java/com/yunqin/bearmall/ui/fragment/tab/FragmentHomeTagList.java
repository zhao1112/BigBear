package com.yunqin.bearmall.ui.fragment.tab;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.MenuGoodsAdapter;
import com.yunqin.bearmall.adapter.SearchGoodsAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.MenuGoods;
import com.yunqin.bearmall.ui.fragment.contract.TagListPageContract;
import com.yunqin.bearmall.ui.fragment.presenter.TagListPagePresenter;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import butterknife.BindView;
import cn.example.lamor.container.MultiStateConfigurator;
import cn.example.lamor.container.MultiStateContainer;
import cn.example.lamor.container.StateController;
import cn.example.lamor.utils.MultiStateLayout;

/**
 * @author AYWang
 * @create 2018/7/30
 * @Describe 首页导航类目列表
 */
public class FragmentHomeTagList extends BaseFragment implements TagListPageContract.UI, MultiStateConfigurator, MultiStateContainer {

    @BindView(R.id.goods_list)
    RecyclerView goods_list;

    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

    private String category_id = "";
    private TagListPageContract.Presenter presenter;

    private SearchGoodsAdapter menuGoodsAdapter;


    private StateController mStateController;

    private boolean hasMore = true;

    private boolean isMember = false;


    @Override
    public int layoutId() {
        return R.layout.fragment_tag_list;
    }

    @Override
    public void init() {
        menuGoodsAdapter = new SearchGoodsAdapter(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return menuGoodsAdapter.getItemColumnCount(position);
            }
        });
        goods_list.setLayoutManager(gridLayoutManager);
        goods_list.setAdapter(menuGoodsAdapter);

        presenter = new TagListPagePresenter(getActivity(), this);

        if (getArguments() != null) {
            category_id = getArguments().getString("category_id");
            isMember = getArguments().getBoolean("isMember", false);
        }
        ((TagListPagePresenter) presenter).setMember(isMember);
        presenter.start(false, category_id);



        initRefresh();
    }

    private void initRefresh() {
        refreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
        refreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                presenter.start(false, category_id);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (hasMore) {
                    presenter.start(true, category_id);
                } else {
                    refreshLayout.finishLoadmore();
                }
            }
        });
    }


    @Override
    public void onHasMore(boolean hasMore) {
        this.hasMore = hasMore;
        if (hasMore) {
            refreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        } else {
            refreshLayout.setBottomView(new RefreshFooterView(getActivity()));
        }
    }


    @Override
    public void attachData(MenuGoods menuGoods, boolean isLoadMore) {
        onFinishRe();
        mStateController.showContent(false);
//        if (menuGoods.getData().getHas_more() == 0) {
//            refreshLayout.setEnableLoadmore(false);
//        } else {
//            refreshLayout.setEnableLoadmore(true);
//        }

        if (isLoadMore) {
            menuGoodsAdapter.addData(menuGoods);
        } else {
            menuGoodsAdapter.setData(menuGoods);
        }
    }

    @Override
    public void onError() {
        onFinishRe();
    }

    @Override
    public void onNotNetWork() {
        onFinishRe();
        mStateController.showError(false);
    }

    public void onFinishRe() {
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
    }

    private HighlightButton mHighlightButton;


    @Override
    public void attachState(MultiStateLayout multiStateLayout) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.network_error_layout, multiStateLayout, false);
        mHighlightButton = view.findViewById(R.id.reset_load_data);
        mHighlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.start(false, category_id);
            }
        });
        multiStateLayout.attachLayout(MultiStateLayout.STATE_ERROR, view);
    }

    @Override
    public Object getTarget() {
        return refreshLayout;
    }

    @Override
    public void onRefTypeReturned(StateController stateController) {
        this.mStateController = stateController;
    }
}

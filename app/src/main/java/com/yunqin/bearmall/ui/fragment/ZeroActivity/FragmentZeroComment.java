package com.yunqin.bearmall.ui.fragment.ZeroActivity;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.ProductCommentAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.ProductDetail;
import com.yunqin.bearmall.bean.ReviewListBean;
import com.yunqin.bearmall.bean.ZeroActivityBean;
import com.yunqin.bearmall.eventbus.ZeroActivityMessageEvent;
import com.yunqin.bearmall.ui.fragment.contract.ProductCommentFragmentContact;
import com.yunqin.bearmall.ui.fragment.presenter.ProductCommentFragmentPresenter;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FragmentZeroComment extends BaseFragment implements ProductCommentFragmentContact.UI {

    @BindView(R.id.list_view)
    ListView list_view;
//    @BindView(R.id.empty_text)
//    TextView empty_text;
    @BindView(R.id.empty_view)
    View view;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

    ProductCommentFragmentContact.presenter presenter;
    ProductCommentAdapter productCommentAdapter;
    private int page_numer = 1;
    private int isLoadMoreOrRefresh = 1;
    private long product_id;
    private ZeroActivityBean.DataBean.GroupPurchasingItemBean itemBean;
    private List<ProductDetail.ReviewList> mReviewList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_product_comment;
    }

    @Override
    public void init() {
        mReviewList = new ArrayList<>();
        refreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
        refreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        presenter = new ProductCommentFragmentPresenter(getActivity(), this);
        productCommentAdapter = new ProductCommentAdapter(getActivity(), mReviewList);
        list_view.setEmptyView(view);
        list_view.setAdapter(productCommentAdapter);


        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page_numer = 1;
                isLoadMoreOrRefresh = 1;
                Constans.params.clear();
                Constans.params.put("page_number", "" + page_numer);
                Constans.params.put("product_id", product_id + "");
                presenter.refresh(Constans.params);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (hasMore) {
                    isLoadMoreOrRefresh = 2;
                    Constans.params.clear();
                    Constans.params.put("page_number", ++page_numer + "");
                    Constans.params.put("product_id", ++product_id + "");
                    presenter.refresh(Constans.params);
                } else {
                    refreshLayout.finishLoadmore();
                }

            }
        });

    }

    //直接使用activity返回的数据刷新列表
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ZeroActivityMessageEvent zeroActivityMessageEvent) {
        Log.e("ProductCommentFragment", zeroActivityMessageEvent.getMessage());
        String serverJsonData = zeroActivityMessageEvent.getMessage();

        parseJsonData(serverJsonData);
    }

    private void parseJsonData(String serverJsonData) {
        if (serverJsonData != null && !serverJsonData.isEmpty()) {
            ZeroActivityBean zeroActivityBean = new Gson().fromJson(serverJsonData, ZeroActivityBean.class);
            itemBean = zeroActivityBean.getData().getGroupPurchasingItem();

            if (itemBean != null) {
                product_id = itemBean.getProduct_id();
                Constans.params.clear();
                Constans.params.put("page_number", page_numer + "");
                Constans.params.put("product_id", product_id + "");
                presenter.refresh(Constans.params);
            }
        }
    }

    @Override
    public void attachhData(ReviewListBean reviewListBean) {
//        if(reviewListBean.getData().getHas_more() == 0){
//            refreshLayout.setEnableLoadmore(false);
//        }else {
//            refreshLayout.setEnableLoadmore(true);
//        }
        if (isLoadMoreOrRefresh == 1) {
            productCommentAdapter.setData(reviewListBean.getData().getReviewList());
        } else {
            productCommentAdapter.addData(reviewListBean.getData().getReviewList());
        }
        onFinishRe();
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

    @Override
    public void onError() {
        onFinishRe();
    }

    public void onFinishRe() {
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}

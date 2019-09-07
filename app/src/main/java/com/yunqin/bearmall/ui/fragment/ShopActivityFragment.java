package com.yunqin.bearmall.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.ShopActivityFragmentAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.ShopActivityBean;
import com.yunqin.bearmall.inter.ShopActCallBack;
import com.yunqin.bearmall.ui.activity.BargainFreeDetailActivity;
import com.yunqin.bearmall.ui.activity.ZeroMoneyDetailsActivity;
import com.yunqin.bearmall.ui.fragment.contract.ShopActivityFragmentContract;
import com.yunqin.bearmall.ui.fragment.presenter.ShopActivityFragmentPresenter;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author AYWang
 * @create 2018/8/10
 * @Describe
 */
public class ShopActivityFragment extends BaseFragment implements ShopActivityFragmentContract.UI, ShopActCallBack {
    @BindView(R.id.shop_activity)
    ListView shopActivity;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;


    private int page_number = 1;
    private ShopActivityFragmentContract.presenter presenter;

    private String store_id;

    ShopActivityFragmentAdapter shopActivityFragmentAdapter;

    private List<ShopActivityBean.DataBean.ListBean> mlist = new ArrayList<>();

    @Override
    public int layoutId() {
        return R.layout.fragment_shop_activity;
    }

    @Override
    public void init() {
        try {
            Bundle bundle = getArguments();
            store_id = (String) bundle.get("store_id");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Constans.params.clear();
        Constans.params.put("store_id", store_id);
        Constans.params.put("page_number", page_number + "");
        presenter = new ShopActivityFragmentPresenter(getActivity(), this);
        presenter.start(Constans.params, false);

        shopActivityFragmentAdapter = new ShopActivityFragmentAdapter(getActivity(), mlist, this);
        shopActivity.setEmptyView(emptyView);
        shopActivity.setAdapter(shopActivityFragmentAdapter);

        initRefresh();
    }

    private void initRefresh() {
        refreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
        refreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        //refreshLayout.setEnableOverScroll(false);//越界回弹效果
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                Constans.params.clear();
                Constans.params.put("store_id", store_id);
                Constans.params.put("page_number", page_number + "");
                presenter.start(Constans.params, false);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (hasMore) {
                    Constans.params.clear();
                    Constans.params.put("store_id", store_id);
                    Constans.params.put("page_number", ++page_number + "");
                    presenter.start(Constans.params, true);
                } else {
                    refreshLayout.finishLoadmore();
                }

            }
        });
    }

    @Override
    public void attachhData(String data, boolean isLoadMore) {
        onFinishRe();
        ShopActivityBean shopActivityBean = new Gson().fromJson(data, ShopActivityBean.class);
//        if (shopActivityBean.getData().getHas_more() == 0) {
//            refreshLayout.setEnableLoadmore(false);
//        } else {
//            refreshLayout.setEnableLoadmore(true);
//        }
        if (!isLoadMore) {
            mlist.clear();
        }
        mlist.addAll(shopActivityBean.getData().getList());
        onHasMore(shopActivityBean.getData().getHas_more() == 1);
        shopActivityFragmentAdapter.notifyDataSetChanged();
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
    public void clickBtn(ShopActivityBean.DataBean.ListBean listBean, int flag) {
        if (flag == 1) {
            Intent intent = new Intent(getActivity(), ZeroMoneyDetailsActivity.class);
            int groupPurchasing_id = listBean.getGroupPurchasing_id();
            intent.putExtra("groupPurchasing_id", groupPurchasing_id + "");
            getActivity().startActivity(intent);
        } else if (flag == 2) {
            Intent intent = new Intent(getActivity(), BargainFreeDetailActivity.class);
            int product_id = listBean.getBargainProduct_id();
            intent.putExtra(BargainFreeDetailActivity.BARGAIN_PRODUCT_ID, (long) product_id);
            getActivity().startActivity(intent);
        }
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


}

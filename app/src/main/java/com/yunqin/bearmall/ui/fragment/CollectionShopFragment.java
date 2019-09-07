package com.yunqin.bearmall.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.Constans;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.MyCollectionShopAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.CollectionShop;
import com.yunqin.bearmall.eventbus.CollectionShopEvent;
import com.yunqin.bearmall.widget.CustomRecommendView;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2018/7/21
 * @Describe
 */
public class CollectionShopFragment extends BaseFragment {

    @BindView(R.id.empty)
    CustomRecommendView empty;

    @BindView(R.id.collection_shop_list)
    ListView collection_shop_list;

    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

    private Map map = new HashMap();
    private int page_numer = 1;
    private int isLoadMoreOrRefresh = 1;

    private MyCollectionShopAdapter myCollectionShopsAdapter;

    @BindView(R.id.not_net)
    View notNetView;



    @OnClick({R.id.reset_load_data})
    public void onSelect(View view) {
        page_numer = 1;
        isLoadMoreOrRefresh = 1;
        getCollectionShopsDatas();
    }


    @Override
    public int layoutId() {
        return R.layout.collection_shop_layout;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventGet(CollectionShopEvent collectionEvent) {
        myCollectionShopsAdapter.remove(collectionEvent.getCollectionIndex());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void init() {

        EventBus.getDefault().register(this);

        empty.setImageSrc(R.drawable.collection_shop_empty);
        empty.setTvContent("您还没有收藏过店铺");
        empty.setTvBottom("- 精选推荐 -");
        empty.setManager(new GridLayoutManager(getActivity(), 2));
        empty.start(getActivity());

        myCollectionShopsAdapter = new MyCollectionShopAdapter(getActivity());
        collection_shop_list.setAdapter(myCollectionShopsAdapter);
        collection_shop_list.setEmptyView(empty);

        initRefresh();
    }

    private void initRefresh() {
        refreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
        refreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page_numer = 1;
                isLoadMoreOrRefresh = 1;
                getCollectionShopsDatas();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if(hasMore){
                    ++page_numer;
                    isLoadMoreOrRefresh = 2;
                    getCollectionShopsDatas();
                }else{
                    refreshLayout.finishLoadmore();
                }

            }
        });

        refreshLayout.startRefresh();
    }

    private boolean hasMore = true;

    public void getCollectionShopsDatas() {
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getStoreFavoriteList(getParams()), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                onFinishRe();

                refreshLayout.setVisibility(View.VISIBLE);
                notNetView.setVisibility(View.GONE);


                CollectionShop collectionShop = new Gson().fromJson(data, CollectionShop.class);
                if (collectionShop.getData().getHas_more() == 0) {
                    hasMore = false;
                    refreshLayout.setBottomView(new RefreshFooterView(getActivity()));
                } else {
                    hasMore = true;
                    refreshLayout.setBottomView(new RefreshBottomView(getActivity()));
                }
                if (isLoadMoreOrRefresh == 1) {
                    myCollectionShopsAdapter.setData(collectionShop.getData().getStoreFavoriteList());
                } else {
                    myCollectionShopsAdapter.addData(collectionShop.getData().getStoreFavoriteList());
                }
            }

            @Override
            public void onNotNetWork() {
                onFinishRe();
                refreshLayout.setVisibility(View.GONE);
                notNetView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFail(Throwable e) {
                onFinishRe();
            }
        });
    }

    private Map getParams() {
        map.clear();
        map.put("page_number", page_numer + "");
        return map;
    }


    public void onFinishRe() {
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
    }
}

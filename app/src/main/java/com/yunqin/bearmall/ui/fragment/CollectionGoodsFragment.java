package com.yunqin.bearmall.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.newversions.detail.NewProductDetailActivity;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.MyCollectionGoodsAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.CollectionGoods;
import com.yunqin.bearmall.eventbus.CollectionGoodsEvent;
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
public class CollectionGoodsFragment extends BaseFragment {

    @BindView(R.id.empty)
    CustomRecommendView empty;

    @BindView(R.id.collection_godos_list)
    ListView collection_godos_list;

    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

    MyCollectionGoodsAdapter myCollectionGoodsAdapter;

    @BindView(R.id.not_net)
    View notNetView;


    private Map map = new HashMap();
    private int page_numer = 1;
    private int isLoadMoreOrRefresh = 1;


    @OnClick({R.id.reset_load_data})
    public void onSelect(View view) {
        page_numer = 1;
        isLoadMoreOrRefresh = 1;
        getCollectionGoodsDatas();
    }


    @Override
    public int layoutId() {
        return R.layout.collection_goods_layout;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventGet(CollectionGoodsEvent collectionEvent) {
        myCollectionGoodsAdapter.remove(collectionEvent.getCollectionIndex());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        empty.setImageSrc(R.drawable.collection_goods_empty);
        empty.setTvContent("您还没有收藏过商品");
        empty.setTvBottom("- 精选推荐 -");
        empty.setManager(new GridLayoutManager(getActivity(), 2));
        empty.start(getActivity());

        myCollectionGoodsAdapter = new MyCollectionGoodsAdapter(getActivity());
        collection_godos_list.setAdapter(myCollectionGoodsAdapter);
        collection_godos_list.setEmptyView(empty);

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
                getCollectionGoodsDatas();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (hasMore) {
                    ++page_numer;
                    isLoadMoreOrRefresh = 2;
                    getCollectionGoodsDatas();
                } else {
                    refreshLayout.finishLoadmore();
                }

            }
        });

        refreshLayout.startRefresh();

        collection_godos_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), NewProductDetailActivity.class);
                intent.putExtra("productId", "" + myCollectionGoodsAdapter.listBeanGet().get(position).getProduct_id());
                intent.putExtra("sku_id", "");
                startActivity(intent);




            }
        });
    }

    private boolean hasMore = true;

    private void getCollectionGoodsDatas() {
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getProductFavoriteList(getParams()), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                onFinishRe();

                refreshLayout.setVisibility(View.VISIBLE);
                notNetView.setVisibility(View.GONE);


                CollectionGoods collectionGoods = new Gson().fromJson(data, CollectionGoods.class);
                if (collectionGoods.getData().getHas_more() == 0) {

                    hasMore = false;
                    refreshLayout.setBottomView(new RefreshFooterView(getActivity()));

                } else {
                    hasMore = true;
                    refreshLayout.setBottomView(new RefreshBottomView(getActivity()));

                }
                if (isLoadMoreOrRefresh == 1) {
                    myCollectionGoodsAdapter.setData(collectionGoods.getData().getProductFavoriteList());
                } else {
                    myCollectionGoodsAdapter.addData(collectionGoods.getData().getProductFavoriteList());
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

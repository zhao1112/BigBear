package com.newversions.tbk.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.newversions.tbk.entity.TBKOrder;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaoBaoChildFragment extends BaseFragment {
    private String title;
    @BindView(R.id.rlv)
    RecyclerView rlv;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private HomeAdapter homeAdapter;
    private List<TBKOrder.TaokeOrderBean> mList = new ArrayList();
    private int page = 1;
    private int pageSize=10;
    private int type = 1;
    private boolean hasMore = true;
    String TAG= "@@yy";

    @Override
    public int layoutId() {
        return R.layout.fragment_tao_bao_child;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("#dong", "onResume: 4" );
    }

    @Override
    public void init() {
        Log.d("TAG", "init: ------");
        refreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page = 1;
                mList.clear();
                getOrder();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (hasMore) {
                    page++;
                    getOrder();

                } else {
                    refreshLayout.finishLoadmore();
                }
            }
        });

        rlv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        homeAdapter = new HomeAdapter(R.layout.orderitem_layout, mList);

        homeAdapter.bindToRecyclerView(rlv);
        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        homeAdapter.setEmptyView(R.layout.no_video_layout);
        Bundle arguments = getArguments();
        title = arguments.getString("title");

        if (title.equals("全部")) {
            type = 0;
        } else if (title.equals("已付款")) {
            type=1;
        } else if (title.equals("已结算")) {
            type = 3;
        } else if (title.equals("已失效")) {
            type = 4;
        }
        getOrder();
    }

    private void getOrder() {
        Log.i("@@yy", "getOrder: "+title);
        showLoading();
        HashMap<String,String> map = new HashMap<>();
        map.put("type",String.valueOf(type));
        map.put("page",String.valueOf(page));
        map.put("pageSize",String.valueOf(pageSize));
        map.put("userId","12241");
        RetrofitApi.request(getContext(), RetrofitApi.createApi(Api.class).getTBKOrderList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                TBKOrder tbkOrder = new Gson().fromJson(data, TBKOrder.class);
                mList.addAll(tbkOrder.getTaokeOrder());
                if(page == 1){
                    homeAdapter.setNewData(mList);
                }else{
                    homeAdapter.addData(tbkOrder.getTaokeOrder());
                }
                Log.i(TAG, "onSuccess: "+mList.size());
                if(page<tbkOrder.getPages()){
                    hasMore = true;
                }else{
                    hasMore = false;
                }
                hiddenLoadingView();
                refreshLayout.onFinishRefresh();
                refreshLayout.onFinishLoadMore();
            }

            @Override
            public void onNotNetWork() {
                refreshLayout.onFinishRefresh();
                refreshLayout.onFinishLoadMore();
                hiddenLoadingView();
            }

            @Override
            public void onFail(Throwable e) {
                refreshLayout.onFinishRefresh();
                refreshLayout.onFinishLoadMore();
                hiddenLoadingView();
            }
        });
    }

    class HomeAdapter extends BaseQuickAdapter<TBKOrder.TaokeOrderBean, BaseViewHolder> {
        public HomeAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper,TBKOrder.TaokeOrderBean item) {
            Glide.with(mContext).load(item.getImage()).into((ImageView) helper.getView(R.id.img));
            helper.setText(R.id.title, item.getItemInfo() != null ? item.getItemInfo() : "");
            helper.setText(R.id.ordernum, item.getOrderNo() != null ? "订单号:" + item.getOrderNo() : "");
            helper.setText(R.id.price, item.getPayAmount() != null ? "金额: " + item.getPayAmount() : "");
            helper.setText(R.id.commission, item.getCommissionAmount() != null ? "预估返佣: " + item.getCommissionAmount() : "");
        }
    }
}

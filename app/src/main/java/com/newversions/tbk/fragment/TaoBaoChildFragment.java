package com.newversions.tbk.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bbcoupon.util.CopyTextUtil;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.TaoBaoAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.TaoBaoBeanNew;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.util.HashMap;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaoBaoChildFragment extends BaseFragment {


    @BindView(R.id.rlv_tao)
    RecyclerView rlv;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.nulldata)
    ConstraintLayout mNulldata;

    private int page = 1;
    private int pageSize = 10;
    private int type = 1;
    private boolean hasMore = true;
    private TaoBaoAdapter mTaoBaoAdapter;
    private String title;
    private String orderType = "1";

    @Override
    public int layoutId() {
        return R.layout.fragment_tao_bao_child;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("#dong", "onResume: 4");
    }


    @Override
    public void init() {

        Bundle arguments = getArguments();
        title = arguments.getString("title");

        if (title.equals("全部")) {
            type = 0;
        } else if (title.equals("待返佣")) {
            type = 1;
        } else if (title.equals("已到账")) {
            type = 3;
        } else if (title.equals("已失效")) {
            type = 4;
        }

        mTaoBaoAdapter = new TaoBaoAdapter(getActivity());
        rlv.setLayoutManager(new LinearLayoutManager(getContext()));
        rlv.setAdapter(mTaoBaoAdapter);

        refreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page = 1;
                if (mTaoBaoAdapter.getItem()) {
                    mTaoBaoAdapter.clearData();
                }
                Log.e("orderType", "onRefresh: " + orderType);
                getTBOrder(orderType);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refresh) {
                if (hasMore) {
                    page++;
                    getTBOrder(orderType);
                } else {
                    refreshLayout.finishRefreshing();
                    refreshLayout.finishLoadmore();
                }
            }
        });

        mTaoBaoAdapter.setOnChildClickListener(new TaoBaoAdapter.OnChildClickListener() {
            @Override
            public void onCopyOrderId(String oderid) {
                if (!TextUtils.isEmpty(oderid)) {
                    CopyTextUtil.CopyText(getActivity(), oderid);
                    showToast("复制成功");
                }
            }
        });

        mNulldata.setVisibility(View.VISIBLE);

        getTBOrder(orderType);
    }

    public void setOrder(String orderType) {
        this.orderType = orderType;
        this.hasMore = false;
        refreshLayout.startRefresh();
    }

    private void getTBOrder(String orderType) {
        showLoading();
        HashMap<String, String> map = new HashMap<>();
        map.put("page_size", String.valueOf(pageSize));
        map.put("page_number", String.valueOf(page));
        map.put("taoOrderStatus", String.valueOf(type));
        map.put("orderType", orderType);
        Log.e("orderType", orderType);
        RetrofitApi.request(getContext(), RetrofitApi.createApi(Api.class).TaoOrderList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                TaoBaoBeanNew taoBaoBeanNew = new Gson().fromJson(data, TaoBaoBeanNew.class);
                if (taoBaoBeanNew.getData() != null && taoBaoBeanNew.getData().size() > 0) {
                    mTaoBaoAdapter.addData(taoBaoBeanNew.getData(), String.valueOf(type));
                    if (taoBaoBeanNew.getData().size() >= 10) {
                        hasMore = true;
                        try {
                            refreshLayout.setBottomView(new RefreshBottomView(getActivity()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        hasMore = false;
                        try {
                            refreshLayout.setBottomView(new RefreshFooterView(getActivity()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    mNulldata.setVisibility(View.GONE);
                } else {
                    if (!hasMore) {
                        mNulldata.setVisibility(View.VISIBLE);
                    }
                }
                refreshLayout.finishRefreshing();
                refreshLayout.finishLoadmore();
                hiddenLoadingView();
            }

            @Override
            public void onNotNetWork() {
                refreshLayout.finishRefreshing();
                refreshLayout.finishLoadmore();
                hiddenLoadingView();
            }

            @Override
            public void onFail(Throwable e) {
                refreshLayout.finishRefreshing();
                refreshLayout.finishLoadmore();
                hiddenLoadingView();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

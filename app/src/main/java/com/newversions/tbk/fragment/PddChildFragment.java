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
import com.yunqin.bearmall.adapter.PddAdapter;
import com.yunqin.bearmall.adapter.TaoBaoAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.PddBean;
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
public class PddChildFragment extends BaseFragment {


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
    private PddAdapter mTaoBaoAdapter;
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
        Log.d("TaoBaoAdapter", "init: ------");
        mNulldata.setVisibility(View.GONE);

        Bundle arguments = getArguments();
        title = arguments.getString("title");

        if (title.equals("全部")) {
            type = 1;
        } else if (title.equals("待返佣")) {
            type = 2;
        } else if (title.equals("已到账")) {
            type = 3;
        } else if (title.equals("已失效")) {
            type = 4;
        }

        mTaoBaoAdapter = new PddAdapter(getActivity(), type);
        rlv.setLayoutManager(new LinearLayoutManager(getContext()));
        rlv.setAdapter(mTaoBaoAdapter);

        refreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page = 1;
                mTaoBaoAdapter.clearData();
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

        mTaoBaoAdapter.setOnChildClickListener(new PddAdapter.OnChildClickListener() {
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

    private void getTBOrder(String orderType) {
        showLoading();
        HashMap<String, String> map = new HashMap<>();
        map.put("pageSize", String.valueOf(pageSize));
        map.put("page", String.valueOf(page));
        map.put("Status", String.valueOf(type));
        map.put("orderType", orderType);
        Log.e("orderType", orderType);
        RetrofitApi.request(getContext(), RetrofitApi.createApi(Api.class).getPddOrderList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                Log.e("onSuccess", data);
                PddBean pddBean = new Gson().fromJson(data, PddBean.class);
                if (pddBean.getData() != null && pddBean.getData().size() > 0) {
                    mTaoBaoAdapter.addData(pddBean.getData());
                    if (pddBean.getData().size() >= 10) {
                        hasMore = true;
                        refreshLayout.setBottomView(new RefreshBottomView(getActivity()));
                    } else {
                        hasMore = false;
                        refreshLayout.setBottomView(new RefreshFooterView(getActivity()));
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
                mNulldata.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFail(Throwable e) {
                refreshLayout.finishRefreshing();
                refreshLayout.finishLoadmore();
                hiddenLoadingView();
                mNulldata.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setOrder(String orderType) {
        this.orderType = orderType;
        this.hasMore = false;
        refreshLayout.startRefresh();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

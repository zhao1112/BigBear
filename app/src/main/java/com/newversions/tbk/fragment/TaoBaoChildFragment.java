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
            type = 0;
        } else if (title.equals("已付款")) {
            type = 1;
        } else if (title.equals("已结算")) {
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
                mTaoBaoAdapter.clearData();
                getTBOrder();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refresh) {
                if (hasMore) {
                    page++;
                    getTBOrder();
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
                    ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, oderid));
                    showToast("复制成功");
                }
            }
        });
        getTBOrder();
    }

    private void getTBOrder() {
        showLoading();
        HashMap<String, String> map = new HashMap<>();
        map.put("page_size", String.valueOf(pageSize));
        map.put("page_number", String.valueOf(page));
        map.put("taoOrderStatus", String.valueOf(type));
        RetrofitApi.request(getContext(), RetrofitApi.createApi(Api.class).TaoOrderList(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                TaoBaoBeanNew taoBaoBeanNew = new Gson().fromJson(data, TaoBaoBeanNew.class);
                if (taoBaoBeanNew.getData().size() > 0 && taoBaoBeanNew.getData() != null) {
                    mTaoBaoAdapter.addData(taoBaoBeanNew.getData());
                    if (taoBaoBeanNew.getData().size() >= 10) {
                        hasMore = true;
                        refreshLayout.setBottomView(new RefreshBottomView(getActivity()));
                    } else {
                        hasMore = false;
                        refreshLayout.setBottomView(new RefreshFooterView(getActivity()));
                    }
                    mNulldata.setVisibility(View.GONE);
                } else {
                    mNulldata.setVisibility(View.VISIBLE);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

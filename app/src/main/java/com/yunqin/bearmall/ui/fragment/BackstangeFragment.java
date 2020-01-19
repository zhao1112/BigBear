package com.yunqin.bearmall.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.BackstangeAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.BackstangeOrderBean;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class BackstangeFragment extends BaseFragment {


    @BindView(R.id.backstage_frag_reclcler)
    RecyclerView mBackstangeFrag_Reclcler;
    @BindView(R.id.backstange_frag_refresh)
    TwinklingRefreshLayout mBackstangeFragRefresh;
    @BindView(R.id.nulldata)
    ConstraintLayout mNulldata;

    private int page = 1;
    private int pageSize = 10;
    private int type=1;
    private boolean hasMore = true;
    private BackstangeAdapter mBackstangeAdapter;
    private String title;

    @Override
    public int layoutId() {
        return R.layout.backstangefragment;
    }

    @Override
    public void init() {
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

        mBackstangeAdapter = new BackstangeAdapter(getContext());
        mBackstangeFrag_Reclcler.setLayoutManager(new LinearLayoutManager(getContext()));
        mBackstangeFrag_Reclcler.setAdapter(mBackstangeAdapter);

        mBackstangeFragRefresh.setHeaderView(new RefreshHeadView(getActivity()));
        mBackstangeFragRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page = 1;
                mBackstangeAdapter.clearData();
                getTabBackage();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refresh) {
                if (hasMore) {
                    page++;
                    getTabBackage();
                } else {
                    mBackstangeFragRefresh.finishRefreshing();
                    mBackstangeFragRefresh.finishLoadmore();
                }
            }
        });

        mBackstangeAdapter.setOnBackstangeCopyListener(new BackstangeAdapter.OnBackstangeCopyListener() {
            @Override
            public void onCopyOrderNo(String orderNo) {
                if (!TextUtils.isEmpty(orderNo)) {
                    ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, orderNo));
                    showToast("复制成功");
                }
            }
        });


        getTabBackage();
    }

    private void getTabBackage() {
        showLoading();
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(pageSize));
        map.put("type", String.valueOf(type));

        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).screenOrders(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                Log.e("screenOrders",data);
                BackstangeOrderBean backstangeOrderBean = new Gson().fromJson(data, BackstangeOrderBean.class);
                if (backstangeOrderBean.getData() != null && backstangeOrderBean.getData().getOrders().size() > 0) {
                    mBackstangeAdapter.addData(backstangeOrderBean.getData().getOrders());
                    if (backstangeOrderBean.getData().getOrders().size() >= 10) {
                        hasMore = true;
                        mBackstangeFragRefresh.setBottomView(new RefreshBottomView(getActivity()));
                    } else {
                        hasMore = false;
                        mBackstangeFragRefresh.setBottomView(new RefreshFooterView(getActivity()));
                    }
                    mNulldata.setVisibility(View.GONE);
                } else {
                    mNulldata.setVisibility(View.VISIBLE);
                }
                mBackstangeFragRefresh.finishRefreshing();
                mBackstangeFragRefresh.finishLoadmore();
                hiddenLoadingView();

            }

            @Override
            public void onNotNetWork() {
                mBackstangeFragRefresh.finishRefreshing();
                mBackstangeFragRefresh.finishLoadmore();
                hiddenLoadingView();
                mNulldata.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFail(Throwable e) {
                mBackstangeFragRefresh.finishRefreshing();
                mBackstangeFragRefresh.finishLoadmore();
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

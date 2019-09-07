package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.BankTXAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.BankTxBean;
import com.yunqin.bearmall.widget.LoadingView;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 提现申请记录
 */

public class WithdrawalRecordActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, WithdrawalRecordActivity.class);
        context.startActivity(intent);
    }


    private RecyclerView mRecyclerView;
    private TwinklingRefreshLayout mTwinklingRefreshLayout;
    private View mEmptyView;

    private LoadingView loadingProgress;

    private void showLoading() {
        if (loadingProgress == null) {
            loadingProgress = LoadingView.createDialog(this);
            loadingProgress.setCancelable(false);
            loadingProgress.setCanceledOnTouchOutside(false);
        }
        loadingProgress.show();
    }

    private void hideLoading() {
        if (loadingProgress != null) {
            loadingProgress.dismiss();
        }
    }

    private BankTXAdapter bankTXAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal_record);
        initVeiws();
        showLoading();
        getData();
    }


    private int pagePosition = 1;
    private boolean loadMore = false;


    private void initVeiws() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTwinklingRefreshLayout = findViewById(R.id.tw_layout);

        mTwinklingRefreshLayout.setHeaderView(new RefreshHeadView(this));
        mTwinklingRefreshLayout.setBottomView(new RefreshBottomView(this));
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                // TODO 刷新
                getData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (loadMore) {
                    addData();
                } else {
                    mTwinklingRefreshLayout.finishLoadmore();
                }
            }
        });

        mEmptyView = findViewById(R.id.empty_view);

        bankTXAdapter = new BankTXAdapter(this);
        mRecyclerView.setAdapter(bankTXAdapter);

    }


    private void getData() {
        pagePosition = 1;
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "30");
        mHashMap.put("page_number", String.valueOf(pagePosition));

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getMemberWithdrawDetails(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                BankTxBean bankTxBean = new Gson().fromJson(data, BankTxBean.class);
                List<BankTxBean.DataBean.WithdrawListBean> withdrawList = bankTxBean.getData().getWithdrawList();

                hideLoading();
                mTwinklingRefreshLayout.finishRefreshing();

                if (withdrawList.size() > 0) {
                    mEmptyView.setVisibility(View.GONE);

                    bankTXAdapter.setData(withdrawList);
                    loadMore = bankTxBean.getData().getHas_more() == 1;
                    mTwinklingRefreshLayout.setEnableLoadmore(loadMore);

                } else {
                    mEmptyView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNotNetWork() {
                hideLoading();
                mTwinklingRefreshLayout.finishRefreshing();
            }

            @Override
            public void onFail(Throwable e) {
                hideLoading();
                mTwinklingRefreshLayout.finishRefreshing();
                mEmptyView.setVisibility(View.VISIBLE);
            }
        });


    }


    private void addData() {
        pagePosition++;
        showLoading();
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "30");
        mHashMap.put("page_number", String.valueOf(pagePosition));

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getMemberWithdrawDetails(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                BankTxBean bankTxBean = new Gson().fromJson(data, BankTxBean.class);
                List<BankTxBean.DataBean.WithdrawListBean> withdrawList = bankTxBean.getData().getWithdrawList();

                bankTXAdapter.addData(withdrawList);
                loadMore = bankTxBean.getData().getHas_more() == 1;
                mTwinklingRefreshLayout.setEnableLoadmore(loadMore);

                mTwinklingRefreshLayout.finishLoadmore();

            }

            @Override
            public void onNotNetWork() {
                mTwinklingRefreshLayout.finishLoadmore();
            }

            @Override
            public void onFail(Throwable e) {
                mTwinklingRefreshLayout.finishLoadmore();
            }
        });


    }

}

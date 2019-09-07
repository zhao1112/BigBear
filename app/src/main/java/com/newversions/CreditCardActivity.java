package com.newversions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.CreditCardBean;
import com.yunqin.bearmall.widget.LoadingView;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * 信用卡界面
 */

public class CreditCardActivity extends AppCompatActivity implements View.OnClickListener {

    private TwinklingRefreshLayout mTwinklingRefreshLayout;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CreditCardActivity.class);
        context.startActivity(intent);
    }

    private RecyclerView mRecyclerView;
    private LinearLayout mEmptyView;
    private IBankStatusAdapter iBankStatusAdapter;

    private boolean hasMore = false;
    private int position = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);
        initViews();
        showLoading();
    }

    private void initViews() {

        findViewById(R.id.new_version_right).setOnClickListener(this);
        findViewById(R.id.new_version_back).setOnClickListener(this);

        mTwinklingRefreshLayout = findViewById(R.id.refresh_layout);
        mEmptyView = findViewById(R.id.empty_view);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        iBankStatusAdapter = new IBankStatusAdapter(this);
        mRecyclerView.setAdapter(iBankStatusAdapter);

        mTwinklingRefreshLayout.setHeaderView(new RefreshHeadView(this));
        mTwinklingRefreshLayout.setBottomView(new RefreshBottomView(this));
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                // TODO 刷新
                position = 1;
                loadData(position);

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                // TODO 加载更多
                if (hasMore) {
                    position++;
                    loadData(position);
                } else {
                    mTwinklingRefreshLayout.finishLoadmore();
                }
            }
        });
        loadData(position);
    }


    private void loadData(int page) {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_size", "10");
        mHashMap.put("page_number", String.valueOf(page));

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getUserCreditCardRecordList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                hideLoading();
                CreditCardBean cardBean = new Gson().fromJson(data, CreditCardBean.class);
                hasMore = cardBean.getData().getHas_more() == 0 ? false : true;

                mTwinklingRefreshLayout.setEnableLoadmore(hasMore);
                mTwinklingRefreshLayout.finishLoadmore();
                mTwinklingRefreshLayout.finishRefreshing();

                if (cardBean.getData() != null && cardBean.getData().getCardList() != null &&
                        cardBean.getData().getCardList().size() > 0) {
                    if (page == 1) {
                        iBankStatusAdapter.setData(cardBean.getData().getCardList());
                    } else {
                        iBankStatusAdapter.addData(cardBean.getData().getCardList());
                    }
                } else {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNotNetWork() {
                hideLoading();
                mEmptyView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFail(Throwable e) {
                hideLoading();
                mEmptyView.setVisibility(View.VISIBLE);
            }
        });


    }


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


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.new_version_back) {
            finish();
        } else if (view.getId() == R.id.new_version_right) {

            CardListWebActivity.startActivity(this, "https://m.hhrcard.com/coprogress/banklist?coopsource=2", "进度查询");


//            if (mEmptyView.getVisibility() == View.VISIBLE) {
//
//            } else {
//                CardListWebActivity.startActivity(this, "https://m.hhrcard.com/coprogress/banklist?coopsource=2", "进度查询");
//            }
        }


    }
}

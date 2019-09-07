package com.newversions.hd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.bean.BannerBean;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 活动二级页面
 */
public class HdActivity extends AppCompatActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.twink_layout)
    TwinklingRefreshLayout mTwinklingRefreshLayout;

    private int defaultPage = 1;
    private HdRecyclerAdapter hdRecyclerAdapter;
    private boolean isSlidingToLast = false;
    private boolean jiaziagengduo = true;


    private String discountCampaign_id = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hd);
        ButterKnife.bind(this);
        discountCampaign_id = getIntent().getStringExtra("discountCampaign_id");
        init();
    }

    public void init() {
        mTwinklingRefreshLayout.setHeaderView(new RefreshHeadView(this));
        mTwinklingRefreshLayout.setBottomView(new RefreshFooterView(this));
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                // TODO 刷新
                defaultPage = 1;
                initData();
                initAdv();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                mTwinklingRefreshLayout.finishLoadmore();
            }
        });
        hdRecyclerAdapter = new HdRecyclerAdapter(this);
        mRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(hdRecyclerAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();

                if (isSlidingToLast && lastVisibleItem == (totalItemCount - 1)) {
                    defaultPage++;
                    initData();
                }


                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        initData();
        initAdv();
    }

    private void initAdv() {
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("positionType", "7");
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getAdMobileList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                BannerBean bannerBean = new Gson().fromJson(data, BannerBean.class);
                hdRecyclerAdapter.setTopImgData(bannerBean);
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    private void initData() {

        if (defaultPage != 1 && !jiaziagengduo) {
            mTwinklingRefreshLayout.setEnableLoadmore(true);
            Log.e("FFF", "没有下一页不让加载");
            return;
        }


        Map<String, String> mHashMap = new HashMap<>();

        mHashMap.put("page_size", "10");
        mHashMap.put("page_number", String.valueOf(defaultPage));
        mHashMap.put("discountCampaign_id", discountCampaign_id);


        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getDiscountProductList(mHashMap), new RetrofitApi.IResponseListener() {

            @Override
            public void onSuccess(String data) throws JSONException {
                ItemBean itemBean = new Gson().fromJson(data, ItemBean.class);
                if (itemBean != null && itemBean.getData() != null) {
                    initTitle(itemBean.getData().getName());
                    if (defaultPage == 1) {
                        hdRecyclerAdapter.setData(itemBean.getData().getDiscountProductList());
                    }else{
                        hdRecyclerAdapter.addData(itemBean.getData().getDiscountProductList());
                    }


                    mTwinklingRefreshLayout.onFinishRefresh();
                    jiaziagengduo = itemBean.getData().getHas_more() == 0 ? false : true;
                    mTwinklingRefreshLayout.setEnableLoadmore(!jiaziagengduo);

                }

            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    private void initTitle(String data) {
        toolbar_title.setText(data);
    }


// ===========================================================

    @OnClick(R.id.toolbar_back)
    public void backClick(View view) {
        finish();
    }

}

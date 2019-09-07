package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.BalanceDetailAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.Balance;
import com.yunqin.bearmall.bean.BalanceDetailResponse;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

public class BalanceDetailActivity extends BaseActivity {

    public static void startBalanceDetailActivity(Context context, String balance){

        Intent intent = new Intent(context,BalanceDetailActivity.class);
        intent.putExtra("MONEY",balance);
        context.startActivity(intent);

    }

    @BindView(R.id.toolbar_title)
    TextView titleView;
    @BindView(R.id.top_bar_bg)
    View barBg;
    @BindView(R.id.toolbar_back)
    ImageView backImage;

    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    private BalanceDetailAdapter adapter;
    private List<Balance> datas = new ArrayList<>();
    private boolean isloadMore;
    private int pageIndex = 1;
    private  String balance;

    private int scrollY = 0;
    //0 白色  1 黑色
    private int flag ;

    @Override
    public int layoutId() {
        return R.layout.activity_balance_detail;
    }

    @Override
    public void init() {


        immerseStatusBar();
        balance = getIntent().getStringExtra("MONEY");
        titleView.setText("余额明细");
//        backImage.setImageResource(R.drawable.icon_nav_arrow_white);

        refreshLayout.setHeaderView(new RefreshHeadView(this));
        refreshLayout.setBottomView(new RefreshBottomView(this));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                isloadMore = false;
                pageIndex = 1;
                loadData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                isloadMore = true;
                loadData();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BalanceDetailAdapter(this,datas,balance);
        recyclerView.setAdapter(adapter);

        refreshLayout.startRefresh();


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.i("test", "onScrolled: "+dy);
                if (barBg.getHeight()==0){
                    return;
                }
                scrollY += dy;
                if (scrollY >= barBg.getHeight()){
                    if (flag != 1){
                        changStatusIconCollor(true);
                        flag = 1;
                        barBg.setAlpha(1);
                        titleView.setTextColor(Color.BLACK);
                    }
                }else if (scrollY <= 0){
                    if (flag != 0){
                        changStatusIconCollor(false);
                        flag = 0;
                        barBg.setAlpha(0);
                        titleView.setTextColor(Color.WHITE);
                    }

                }else {
                    float percent =  (float)scrollY/(float)barBg.getHeight();
                    int color = Color.rgb((int) ((1-percent)*255),(int)((1-percent)*255),(int)((1-percent)*255));
                    barBg.setAlpha(percent);
                    titleView.setTextColor(color);
                }

            }
        });


    }

    private void loadData(){

        Map<String, String> params = new HashMap<>();
        params.put("page_size", "50");
        params.put("page_number", pageIndex + "");

        Observable<String> observable = RetrofitApi.createApi(Api.class).getMemberBalanceDatils(params);
        RetrofitApi.request(this, observable, new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {

                hiddenLoadingView();
                if (isloadMore){
                    refreshLayout.finishLoadmore();
                }else {
                    refreshLayout.finishRefreshing();
                }

                BalanceDetailResponse response = new Gson().fromJson(data,BalanceDetailResponse.class);
                if (response.isSuccess()){

                    BalanceDetailResponse.DataBean dataBean = response.getData();
                    balance = dataBean.getBalance();
                    adapter.changeHeaderPrice(balance);

                    if (dataBean.getHas_more() == 1){
                        refreshLayout.setEnableLoadmore(true);
                    }else {
                        refreshLayout.setEnableLoadmore(false);
                    }

                    List<Balance> balances = dataBean.getList();
                    if (isloadMore){
                        datas.addAll(balances);
                    }else {
                        datas.clear();
                        datas.addAll(balances);
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onNotNetWork() {

                hiddenLoadingView();
                if (isloadMore){
                    refreshLayout.finishLoadmore();
                }else {
                    refreshLayout.finishRefreshing();
                }


            }

            @Override
            public void onFail(Throwable e) {

                hiddenLoadingView();
                if (isloadMore){
                    refreshLayout.finishLoadmore();
                }else {
                    refreshLayout.finishRefreshing();
                }

            }
        });



    }


    @OnClick({R.id.toolbar_back})
    public void onViewClick(View view){

        switch (view.getId()){

            case R.id.toolbar_back:

                finish();

                break;

        }

    }


}

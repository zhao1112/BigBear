package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.LogisticsAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.Logistics;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 物流信息
 *
 * @author Master
 * @create 2018/8/23 17:43
 */
public class LogisticsActivity extends BaseActivity {


    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout mTwinklingRefreshLayout;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.order_number)
    TextView order_number;

    @BindView(R.id.order_courier)
    TextView order_courier;

    @BindView(R.id.no_data)
    LinearLayout mLinearLayout;

    private String orders_id;
    private Logistics mLogistics;


    public static void start(Context context, String orders_id) {
        Intent intent = new Intent(context, LogisticsActivity.class);
        intent.putExtra("order_id", orders_id);
        context.startActivity(intent);
    }


    @Override
    public int layoutId() {
        return R.layout.activity_logistics_layout;
    }

    @Override
    public void init() {
        toolbar_title.setText("物流信息");
        mTwinklingRefreshLayout.setEnableRefresh(false);
        mTwinklingRefreshLayout.setEnableLoadmore(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        Intent intent = getIntent();
        orders_id = intent.getStringExtra("order_id");
        if (orders_id == null || "".equals(orders_id)) {
            finish();
            return;
        }
        initData();
    }

    @OnClick({R.id.toolbar_back})
    public void onSelect(View view) {
        finish();
    }


    private void initData() {

        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("orders_id", orders_id);

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getLogisticsList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                mLogistics = new Gson().fromJson(data, Logistics.class);
                initView();
            }

            @Override
            public void onNotNetWork() {

            }

            @Override
            public void onFail(Throwable e) {
                Toast.makeText(LogisticsActivity.this, "数据请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {

        if (mLogistics != null
                && mLogistics.getData() != null
                && mLogistics.getData().getOrderShippingList() != null
                && mLogistics.getData().getOrderShippingList().size() > 0
                && mLogistics.getData().getOrderShippingList().get(0) != null
                && mLogistics.getData().getLogisticsList() != null
                && mLogistics.getData().getLogisticsList().size() > 0
                && mLogistics.getData().getLogisticsList().get(0) != null
                && mLogistics.getData().getLogisticsList().get(0).size() > 0) {

            order_number.setText(String.format("订单编号: %s", mLogistics.getData().getOrderShippingList().get(0).getTrackingNo()));
            order_courier.setText(String.format("国内承运人: %s", mLogistics.getData().getOrderShippingList().get(0).getDeliveryCorp()));

            LogisticsAdapter logisticsAdapter = new LogisticsAdapter(this, mLogistics.getData().getLogisticsList().get(0));
            mRecyclerView.setAdapter(logisticsAdapter);
        } else {
            mLinearLayout.setVisibility(View.VISIBLE);
        }
    }
}

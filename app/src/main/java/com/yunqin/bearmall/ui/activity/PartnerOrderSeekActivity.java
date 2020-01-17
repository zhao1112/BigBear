package com.yunqin.bearmall.ui.activity;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.PartnerOrderSeekAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.PartnerOrderSeekBean;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.util.HashMap;

public class PartnerOrderSeekActivity extends BaseActivity {

    private EditText mPartnerOrderText;
    private Button mPartnerOrderButton;
    private ImageView mPartenrOrderReturn;
 //   private TwinklingRefreshLayout mPartenrOrderRefresh;
    private RecyclerView mPartenrOrderRecyclerview;
    private PartnerOrderSeekAdapter partnerOrderSeekAdapter;

    int page;
    private boolean hasMore = true;
    private ConstraintLayout mNulldata;

    @Override
    public int layoutId() {
        return R.layout.activity_partner_order_seek;
    }

    @Override
    public void init() {
        setTranslucentStatus();
        //输入框
        mPartnerOrderText = findViewById(R.id.partner_order_text);
        //搜索按钮
        mPartnerOrderButton = findViewById(R.id.partner_order_button);
        //返回
        mPartenrOrderReturn = findViewById(R.id.partenr_order_return);
        //刷新
        //mPartenrOrderRefresh = findViewById(R.id.partenr_order_refresh);
        //条目
        mPartenrOrderRecyclerview = findViewById(R.id.partenr_order_recyclerview);
        mNulldata = findViewById(R.id.nulldata);

        mPartenrOrderReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        partnerOrderSeekAdapter = new PartnerOrderSeekAdapter(this);
        mPartenrOrderRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mPartenrOrderRecyclerview.setAdapter(partnerOrderSeekAdapter);


        initDate();
    }

    private void initDate() {

        mPartnerOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seek = mPartnerOrderText.getText().toString();
                HashMap map = new HashMap<>();
                map.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
                map.put("searchPar", seek);
                RetrofitApi.request(getApplicationContext(), RetrofitApi.createApi(Api.class).partenerScreenOrders(map), new RetrofitApi.IResponseListener() {

                    @Override
                    public void onSuccess(String data) throws JSONException {
                        PartnerOrderSeekBean partnerOrderSeekBean = new Gson().fromJson(data, PartnerOrderSeekBean.class);
                        if (partnerOrderSeekBean.getData().getOrders() != null && partnerOrderSeekBean.getData().getOrders().size() > 0) {
                            partnerOrderSeekAdapter.addData(partnerOrderSeekBean.getData().getOrders());
                            mNulldata.setVisibility(View.GONE);
                            hiddenLoadingView();

                        } else {
                            mNulldata.setVisibility(View.VISIBLE);
                        }
                        //mPartenrOrderRefresh.finishRefreshing();
                        //mPartenrOrderRefresh.finishLoadmore();
                        hiddenLoadingView();
                    }

                    @Override
                    public void onNotNetWork() {
                        //mPartenrOrderRefresh.finishRefreshing();
                        //mPartenrOrderRefresh.finishLoadmore();
                        hiddenLoadingView();
                        mNulldata.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFail(Throwable e) {
                        //mPartenrOrderRefresh.finishRefreshing();
                        //mPartenrOrderRefresh.finishLoadmore();
                        hiddenLoadingView();
                        mNulldata.setVisibility(View.VISIBLE);

                    }
                });
            }
        });

    }


}

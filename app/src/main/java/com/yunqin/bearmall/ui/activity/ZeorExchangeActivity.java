package com.yunqin.bearmall.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.ZeroAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.DayliTaskBCInfo;
import com.yunqin.bearmall.bean.ZeroGoodsBean;
import com.yunqin.bearmall.ui.dialog.ActivityTextTipDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ZeorExchangeActivity extends BaseActivity {

    @BindView(R.id.sume_zeor)
    TextView sume_zeor;
    @BindView(R.id.recycle_zeor)
    RecyclerView recycle_zeor;

    private int page_number = 1;
    private ZeroAdapter zeroAdapter;
    private String sume;
    private ActivityTextTipDialog activityTextTipDialog;

    @Override
    public int layoutId() {
        return R.layout.activity_zeorexchange;
    }

    @Override
    public void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycle_zeor.setLayoutManager(linearLayoutManager);
        zeroAdapter = new ZeroAdapter(this);
        recycle_zeor.setAdapter(zeroAdapter);
        initDatas();
        getZeorData();
    }

    @OnClick({R.id.guiz_zeor, R.id.more_zeor, R.id.banck})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.guiz_zeor:
                if (activityTextTipDialog == null) {
                    activityTextTipDialog = new ActivityTextTipDialog(ZeorExchangeActivity.this);
                } else {
                    activityTextTipDialog.show();
                }
                break;
            case R.id.more_zeor:
                DailyTasksActivity.starActivity(this);
                break;
            case R.id.banck:
                finish();
                break;
        }
    }

    private void initDatas() {

        HashMap<String, String> mHashMap = new HashMap<>();
        mHashMap.put("positionType", "28");

        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getDailyTaskAllReward(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                DayliTaskBCInfo dayliTaskBCInfo = new Gson().fromJson(data, DayliTaskBCInfo.class);
                if (dayliTaskBCInfo != null && dayliTaskBCInfo.getData() != null && dayliTaskBCInfo.getData().getRewardDetails() != null &&
                        dayliTaskBCInfo.getData().getRewardDetails().getBCbanlance() != null) {
                    DayliTaskBCInfo.DataBean.RewardDetailsBean detailsBean = dayliTaskBCInfo.getData().getRewardDetails();
                    sume_zeor.setText(detailsBean.getBCbanlance());
                    sume = detailsBean.getBCbanlance();
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

    public void getZeorData() {
        Map<String, String> map = new HashMap<>();
        map.put("page_number", page_number + "");
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getGrouppurchasingIndex(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    ZeroGoodsBean zeroGoodsBean = new Gson().fromJson(data, ZeroGoodsBean.class);
                    if (zeroGoodsBean != null && zeroGoodsBean.getData() != null && zeroGoodsBean.getData().getGroupPurchasingList() != null &&
                            zeroGoodsBean.getData().getGroupPurchasingList().size() > 0) {
                        zeroAdapter.setData(zeroGoodsBean.getData().getGroupPurchasingList());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
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
}

package com.newversions.tbk.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newversions.tbk.entity.RecordEntity;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseActivity;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IncomeRecordActivity extends BaseActivity {

    @BindView(R.id.tv_income_day)
    TextView tvIncomeDay;
    @BindView(R.id.tv_income_month)
    TextView tvIncomeMonth;
    @BindView(R.id.rb_withdraw_record)
    RadioButton rbWithdrawRecord;
    @BindView(R.id.rb_income_record)
    RadioButton rbIncomeRecord;
    @BindView(R.id.rg_record)
    RadioGroup rgRecord;
    @BindView(R.id.record_rcl)
    RecyclerView recordRcl;
    List<RecordEntity.DataBean.IncomeDetailBean> mList = new ArrayList<>();
    private boolean hasMore = true;
    private static final int RECORD_TYPE_WITHDRAW = 1;
    private static final int RECORD_TYPE_INCOME = 2;
    private int page = 1;
    private MyAdapter adapter;
    private boolean isFirst = true;
    @Override
    public int layoutId() {
        return R.layout.activity_income_record;
    }

    @Override
    public void init() {
//        getRecord(RECORD_TYPE_WITHDRAW);
        rgRecord.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_income_record) {
                getRecord(RECORD_TYPE_INCOME);
            } else {
                getRecord(RECORD_TYPE_WITHDRAW);
            }
        });
        recordRcl.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new MyAdapter(R.layout.item_incom_record, mList);
        adapter.bindToRecyclerView(recordRcl);
        adapter.setEmptyView(R.layout.no_video_layout);
        adapter.openLoadAnimation();
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if(isFirst){
                    isFirst = false;
                    adapter.loadMoreComplete();
                    return;
                }
                if (hasMore) {
                    page++;
                    getRecord(RECORD_TYPE_INCOME);
                }else{
                    adapter.loadMoreEnd();
                }
            }
        }, recordRcl);
        getRecord(RECORD_TYPE_INCOME);
    }


    @OnClick(R.id.toolbar_back)
    public void onViewClicked() {
        finish();
    }

    public void getRecord(int type) {
        showLoading();
        HashMap<String, String> map = new HashMap<>();
//        map.put("key",String.valueOf(type));
        map.put("page",String.valueOf(page));
        map.put("pageSize","10");
        RetrofitApi.request(this, RetrofitApi.createApi(Api.class).getIncomRecord(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                hiddenLoadingView();
                RecordEntity recordEntity = new Gson().fromJson(data, RecordEntity.class);
                tvIncomeDay.setText(recordEntity.getOnTheDay().getEstimatedEarningsToday()+"");
                tvIncomeMonth.setText(recordEntity.getDuringTheMonth().getEstimatedEarningsToday()+"");
                hasMore = recordEntity.getData().getHas_more() == 1;
                adapter.loadMoreComplete();
                mList.addAll(recordEntity.getData().getIncomeDetail());
                if(page== 1){
                    adapter.setNewData(mList);
                }else{
                    adapter.addData(recordEntity.getData().getIncomeDetail());
                }

            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
                adapter.loadMoreComplete();
            }

            @Override
            public void onFail(Throwable e) {
                adapter.loadMoreComplete();
                hiddenLoadingView();
            }
        });
    }

    class MyAdapter extends BaseQuickAdapter<RecordEntity.DataBean.IncomeDetailBean, BaseViewHolder> {

        public MyAdapter(int layoutResId, @Nullable List<RecordEntity.DataBean.IncomeDetailBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, RecordEntity.DataBean.IncomeDetailBean item) {
            helper.setText(R.id.tv_title,item.getMemo());
            helper.setText(R.id.tv_time,item.getCreatedDate());
            helper.setText(R.id.tv_money,item.getCredit());
        }
    }
}

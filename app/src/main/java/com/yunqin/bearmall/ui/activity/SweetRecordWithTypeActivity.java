package com.yunqin.bearmall.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.RecordAllTypeAdapter;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.SweetRecordAllType;
import com.yunqin.bearmall.ui.activity.contract.SweetRecordWithTypeContract;
import com.yunqin.bearmall.ui.activity.presenter.SweetRecordAllTypePresenter;
import com.yunqin.bearmall.util.StarActivityUtil;
import com.yunqin.bearmall.widget.Highlight.HighlightButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.example.lamor.Faceplate;
import cn.example.lamor.container.MultiStateConfigurator;
import cn.example.lamor.container.MultiStateContainer;
import cn.example.lamor.container.StateController;
import cn.example.lamor.utils.MultiStateLayout;

/**
 * Created by chenchen on 2018/7/23.
 */

public class SweetRecordWithTypeActivity extends BaseActivity implements SweetRecordWithTypeContract.IUI, View.OnClickListener,
        MultiStateContainer, MultiStateConfigurator {

    private static final String TOTAL = "total";
    private static final String TODAY = "today";

    public static void startSweetRecordWithTypeActivity(Activity context, String total, String today) {
        Bundle bundle = new Bundle();
        bundle.putString(TODAY, today);
        bundle.putString(TOTAL, total);
        StarActivityUtil.starActivity(context, SweetRecordWithTypeActivity.class, bundle);
    }

    @BindView(R.id.toolbar_title)
    TextView titleView;
    @BindView(R.id.topBar)
    RelativeLayout topContainer;
    @BindView(R.id.toolbar_back)
    ImageView backImage;
    @BindView(R.id.all_type_recycler)
    RecyclerView recyclerView;

    private RecordAllTypeAdapter adapter;
    private List<SweetRecordAllType.DataBean> dataBeans;
    private SweetRecordAllTypePresenter presenter;

    @Override
    public int layoutId() {
        return R.layout.activity_sweet_record_all_type;
    }

    @Override
    public void init() {
        Faceplate.getDefault().performInjectLayoutLayers(this);
        immerseStatusBar();
        initView();
        presenter = new SweetRecordAllTypePresenter(this);
        showLoading();
        presenter.start(this);

    }

    private void initView() {

        titleView.setText("收益记录");

        backImage.setImageResource(R.drawable.icon_nav_arrow_white);
        titleView.setTextColor(getResources().getColor(R.color.white));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataBeans = new ArrayList<>();
        //占位
        dataBeans.add(0, new SweetRecordAllType.DataBean());
        String today = getIntent().getStringExtra(TODAY);
        String total = getIntent().getStringExtra(TOTAL);
        adapter = new RecordAllTypeAdapter(total, today, this, dataBeans);
        recyclerView.setAdapter(adapter);

        adapter.setOnRefreshClickListener(new RecordAllTypeAdapter.OnRefreshClickListener() {
            @Override
            public void onRefreshClicked() {
                showLoading();
                presenter.start(SweetRecordWithTypeActivity.this);
            }
        });
    }


    @OnClick({R.id.toolbar_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    @Override
    public void onGetData(SweetRecordAllType data) {
//        mStateController.showContent(false);
        hiddenLoadingView();
        if (adapter != null) {
            List<SweetRecordAllType.DataBean> beans = data.getData();
            dataBeans.addAll(beans);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onNotNetWork() {
//        mStateController.showError(false);
        hiddenLoadingView();
    }

    @Override
    public void onLoadError() {
        hiddenLoadingView();
    }

//    private StateController mStateController;

    @Override
    public void attachState(MultiStateLayout multiStateLayout) {
//        View view = LayoutInflater.from(this).inflate(R.layout.network_error_layout, multiStateLayout, false);
//        HighlightButton mHighlightButton = view.findViewById(R.id.reset_load_data);
//        mHighlightButton.setOnClickListener(v -> presenter.start(this));
//        multiStateLayout.attachLayout(MultiStateLayout.STATE_ERROR, view);
    }

    @Override
    public Object getTarget() {
        return recyclerView;
    }

    @Override
    public void onRefTypeReturned(StateController stateController) {
//        this.mStateController = stateController;
    }
}

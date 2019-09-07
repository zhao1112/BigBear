package com.yunqin.bearmall.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.MessageAdapter;
import com.yunqin.bearmall.base.BaseActivity;
import com.yunqin.bearmall.bean.ActivityMessageData;
import com.yunqin.bearmall.bean.BaseMessage;
import com.yunqin.bearmall.ui.activity.contract.MessageContract;
import com.yunqin.bearmall.ui.activity.presenter.MessagePresenter;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenchen on 2018/8/13.
 */

public abstract class BaseMessageActivity<T extends BaseMessage, Y> extends BaseActivity implements MessageContract.IView{

    @BindView(R.id.toolbar_title)
    TextView titleView;
    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<T> datas;
    protected boolean isloadMore;
    private int index;
    public MessageAdapter<T> adapter;
    private MessagePresenter presenter;


    @Override
    public int layoutId() {
        return R.layout.activity_bc_message;
    }

    @Override
    public void init() {

        datas = new ArrayList<>();
        presenter = new MessagePresenter(this);
        presenter.start(this);

        titleView.setText(setBarTitle());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout.setHeaderView(new RefreshHeadView(this));
        refreshLayout.setBottomView(new RefreshBottomView(this));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {

                isloadMore = false;
                index = 1;
                presenter.refreshData(getCategory(), index);

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {

                if (hasMore) {
                    isloadMore = true;
                    index++;
                    presenter.refreshData(getCategory(), index);
                } else {
                    refreshLayout.finishLoadmore();
                }


            }
        });
        adapter = new MessageAdapter<>(datas, this, getCategory());
        recyclerView.setAdapter(adapter);

        refreshLayout.startRefresh();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    protected abstract int getCategory();

    @OnClick({R.id.toolbar_back})
    protected void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.toolbar_back:

                doBeforeClose();

                finish();

                break;

        }

    }


    public void changeData(List<T> datas) {

        hiddenLoadingView();
        iLinearLayout.setVisibility(View.GONE);

        if (isloadMore) {

            refreshLayout.finishLoadmore();

            this.datas.addAll(datas);

        } else {

            refreshLayout.finishRefreshing();

            this.datas.clear();

            this.datas.addAll(datas);

        }

        adapter.notifyDataSetChanged();

    }


    private boolean hasMore = true;


    public void setCanLoadMore(boolean isCan) {

        if (refreshLayout != null) {
            this.hasMore = isCan;

            if (isCan) {
                refreshLayout.setBottomView(new RefreshBottomView(this));
            } else {
                refreshLayout.setBottomView(new RefreshFooterView(this));
            }
        }

    }


    @BindView(R.id.not_net_group)
    LinearLayout iLinearLayout;

    @Override
    public void onLoadError() {
        if (isloadMore) {
            refreshLayout.finishLoadmore();
        } else {
            refreshLayout.finishRefreshing();
        }

        // TODO chengPan添加，失败显示无网界面
        hiddenLoadingView();
        iLinearLayout.setVisibility(View.VISIBLE);


    }

    @Override
    public void onLoadData(String data) {

        Class<Y> clazz = (Class<Y>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];

        Y object = new Gson().fromJson(data, clazz);

        onLoadedServerData(object);

    }

    protected abstract void onLoadedServerData(Y object);
    protected abstract void onStartDetails(int article_id);




    public void doBeforeClose() {

    }

    abstract String setBarTitle();


    /**
     * chengPan 添加重试按钮
     *         显示Loading
     *         隐藏Loading
     * @param view
     */
    @OnClick(R.id.reset_load_data)
    public void onSelect_(View view) {
        showLoading();
        presenter.refreshData(getCategory(), 1);
    }


}

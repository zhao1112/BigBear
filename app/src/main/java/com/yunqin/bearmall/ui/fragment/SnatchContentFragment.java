package com.yunqin.bearmall.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.SnatchContentAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.SnatchContent;
import com.yunqin.bearmall.bean.Treasure;
import com.yunqin.bearmall.ui.fragment.contract.SnatchContentContract;
import com.yunqin.bearmall.ui.fragment.presenter.SnatchPresenter;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenchen on 2018/8/6.
 */

public class SnatchContentFragment extends BaseFragment implements SnatchContentContract.IView{

    public static SnatchContentFragment fragment(int tag,int isToday){

        SnatchContentFragment fragment = new SnatchContentFragment();

        Bundle bundle = new Bundle();

        bundle.putInt("TAG",tag);

        bundle.putInt("TODAY",isToday);

        fragment.setArguments(bundle);

        return fragment;

    }


    @BindView(R.id.snatch_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout refreshLayout;

    private boolean isloadMore;
    private int index;
    private int tag;
    private int today;
    private SnatchContentAdapter adapter;
    private List<Treasure> datas;
    private SnatchPresenter presenter;

    @Override
    public int layoutId() {
        return R.layout.fragment_snatch_content;
    }

    @Override
    public void init() {

        Bundle bundle = getArguments();

        if (bundle != null){

            tag = bundle.getInt("TAG",0);

            today = bundle.getInt("TODAY",0);

        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);

        datas = new ArrayList<>();
        presenter = new SnatchPresenter(this);

        presenter.start(getContext());
        adapter = new SnatchContentAdapter(getActivity(), datas,today);

        refreshLayout.setHeaderView(new RefreshHeadView(getContext()));
        refreshLayout.setBottomView(new RefreshBottomView(getContext()));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {

                isloadMore = false;
                index = 1;
                presenter.refreshData(index,tag,today);

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {

                isloadMore = true;
                index++;
                presenter.refreshData(index,tag,today);

            }
        });
        recyclerView.setAdapter(adapter);
//        refreshLayout.startRefresh();
        index = 1;
        presenter.refreshData(index,tag,today);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableOverScroll(false);

    }

    @Override
    public void onLoadedData(SnatchContent content) {

        finishRefrsh();

        if (content.getCode() == 1){

            SnatchContent.DataBean bean = content.getData();

            if (bean.getHas_more() == 1){

                refreshLayout.setEnableLoadmore(true);
            }

            List<Treasure> treasures = bean.getTreasureList();

            if (treasures != null && treasures.size()>0){
                Treasure treasure = treasures.get(0);
                if (treasure.getStatus() == 1){
                    dealTreasuer(treasures);
                }
            }

            if (isloadMore){

                this.datas.addAll(treasures);

            }else {

                datas.clear();

                datas.addAll(treasures);

            }

            adapter.notifyDataSetChanged();

        }
    }

    private void dealTreasuer(List<Treasure> treasures) {

        for (int i=0;i<treasures.size();i++){

            Treasure info = treasures.get(i);

            long curTime = System.currentTimeMillis();

            info.setEndTime(curTime + info.getRestTime());

        }

    }


    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null){
            adapter.startRefreshTime();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (adapter != null){
            adapter.cancelRefreshTime();
        }
    }


    private void finishRefrsh(){

        if (isloadMore){
            refreshLayout.finishLoadmore();
        }else {
            refreshLayout.finishRefreshing();
        }

    }

}

package com.yunqin.bearmall.ui.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.MySnatchAdapter;
import com.yunqin.bearmall.adapter.SnatchContentAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.MySnatch;
import com.yunqin.bearmall.bean.SnatchContent;
import com.yunqin.bearmall.bean.Treasure;
import com.yunqin.bearmall.ui.fragment.contract.MySnatchContract;
import com.yunqin.bearmall.ui.fragment.presenter.MySnatchPresent;
import com.yunqin.bearmall.ui.fragment.presenter.SnatchPresenter;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenchen on 2018/8/4.
 */

public class MySnatchFragment extends BaseFragment implements MySnatchContract.IView{

    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.my_snatch_recycler)
    RecyclerView recyclerView;

    private boolean isloadMore;
    private int index;
    private int tag;
    private MySnatchPresent present;
    private List<Treasure> datas;
    private MySnatchAdapter adapter;

    @Override
    public int layoutId() {
        return R.layout.fragment_my_snatch;
    }

    @Override
    public void init() {

        present = new MySnatchPresent(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);

        datas = new ArrayList<>();
        present.start(getContext());


        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setHeaderView(new RefreshHeadView(getContext()));
        refreshLayout.setBottomView(new RefreshBottomView(getContext()));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {

                isloadMore = false;
                index = 1;
                present.refreshData(index);

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {

                isloadMore = true;
                index++;
                present.refreshData(index);

            }
        });

        refreshLayout.startRefresh();

    }


    @Override
    public void onLoadedData(MySnatch data) {

        finishRefrsh();

        if (data.getCode() == 1){

            MySnatch.DataBean bean = data.getData();

            if (bean.getHas_more() == 1){

                refreshLayout.setEnableLoadmore(true);
            }else {

                refreshLayout.setEnableLoadmore(false);
            }

            List<Treasure> treasures = bean.getMemberTreasureList();

            dealTime(treasures);

            if (isloadMore){

                this.datas.addAll(treasures);

                adapter.notifyDataSetChanged();

            }else {

                datas.clear();

                datas.addAll(treasures);

                if (adapter == null){

                    adapter = new MySnatchAdapter(getActivity(),datas);

                    recyclerView.setAdapter(adapter);

                }else {

                    adapter.notifyDataSetChanged();

                }

            }

        }

    }

    @Override
    public void onLoadError() {
        finishRefrsh();
    }

    private void dealTime(List<Treasure> treasures) {

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

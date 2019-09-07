package com.yunqin.bearmall.ui.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.MyTransferAdapter;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.Voucher;
import com.yunqin.bearmall.ui.fragment.contract.MyTransferVoucherContract;
import com.yunqin.bearmall.ui.fragment.presenter.MyTransferVoucherPersenter;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshFooterView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author AYWang
 * @create 2018/7/21
 * @Describe
 */
public class MyTransferVoucherFragment extends BaseFragment implements MyTransferVoucherContract.UI {

    @BindView(R.id.empty)
    LinearLayout empty;

    @BindView(R.id.transfer_list)
    ListView transferList;

    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

    @BindView(R.id.not_net)
    View not_net;


    MyTransferVoucherContract.Persenter persenter;

    private MyTransferAdapter myTransferAdapter;

    private int page_numer = 1;
    private int isLoadMoreOrRefresh = 1;
    private Map map = new HashMap();

    @Override
    public int layoutId() {
        return R.layout.fragment_transfer_voucher;
    }

    @Override
    public void init() {
        refreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
        refreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        persenter = new MyTransferVoucherPersenter(getActivity(), this);
        myTransferAdapter = new MyTransferAdapter(getActivity());
        transferList.setAdapter(myTransferAdapter);
        transferList.setEmptyView(empty);

        persenter.getData(getParams());
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page_numer = 1;
                isLoadMoreOrRefresh = 1;
                persenter.getData(getParams());
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (hasMore) {
                    isLoadMoreOrRefresh = 2;
                    ++page_numer;
                    persenter.getData(getParams());
                } else {
                    refreshLayout.finishLoadmore();
                }

            }
        });
    }


    private boolean hasMore = true;


    @Override
    public void attach(Voucher voucher) {

        transferList.setVisibility(View.VISIBLE);
        not_net.setVisibility(View.GONE);

        if (voucher.getData().getHas_more() == 0) {
            hasMore = false;
            refreshLayout.setBottomView(new RefreshFooterView(getActivity()));
        } else {
            hasMore = true;
            refreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        }
        if (isLoadMoreOrRefresh == 1) {
            myTransferAdapter.setData(voucher.getData().getUsersTicketList());
        } else {
            myTransferAdapter.addData(voucher.getData().getUsersTicketList());
        }
        onFinishRe();
    }

    @Override
    public void onError() {
        onFinishRe();
    }

    @Override
    public void onNotNetWork() {
        onFinishRe();
        transferList.setVisibility(View.GONE);
        not_net.setVisibility(View.VISIBLE);

    }

    public void onFinishRe() {
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
    }

    private Map getParams() {
        map.clear();
        map.put("page_number", "" + page_numer);
        map.put("queryType", "2");
        map.put("ticketType", "0");
        return map;
    }


    @OnClick({R.id.reset_load_data})
    public void onChildClick(View view) {
        persenter.getData(getParams());
    }


}

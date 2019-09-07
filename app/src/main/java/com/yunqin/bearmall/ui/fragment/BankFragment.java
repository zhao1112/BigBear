package com.yunqin.bearmall.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.BankAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.BankBean;
import com.yunqin.bearmall.eventbus.UpdateBankEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create By Master
 * On 2019/2/28 15:56
 */
public class BankFragment extends BaseFragment {


    @BindView(R.id.bank_refresh_layout)
    TwinklingRefreshLayout mTwinklingRefreshLayout;
    @BindView(R.id.bank_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.new_version_back)
    RelativeLayout back;

    private BankAdapter mBankAdapter;

    @Override
    public int layoutId() {
        return R.layout.bank_layout;
    }

    @Override
    public void init() {

        EventBus.getDefault().register(this);

        mTwinklingRefreshLayout.setEnableRefresh(false);
        mTwinklingRefreshLayout.setEnableLoadmore(false);

//        mTwinklingRefreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
//        mTwinklingRefreshLayout.setBottomView(new RefreshBottomView(getActivity()));
//        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
//            @Override
//            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
//                refreshLayout.finishRefreshing();
//            }
//
//            @Override
//            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
//                refreshLayout.finishLoadmore();
//            }
//        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBankAdapter = new BankAdapter(getActivity());
        mRecyclerView.setAdapter(mBankAdapter);
        back.setOnClickListener(view -> getActivity().finish());

        initData();
    }

    private void initData() {
        showLoading();
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getUsersBankInfo(), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                BankBean bankBean = new Gson().fromJson(data, BankBean.class);
                mBankAdapter.setBankBeans(bankBean.getData().getList());
                hiddenLoadingView();
            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
            }

            @Override
            public void onFail(Throwable e) {
                hiddenLoadingView();
            }
        });
    }


    private boolean uodateList = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventGet(UpdateBankEvent event) {
        uodateList = event.isSetPwd();
    }


    @OnClick({R.id.jei_chu_bang_ding})
    public void cheageList(View view) {
        if (mBankAdapter != null && mBankAdapter.getData().size() > 0) {
            mBankAdapter.updatelist(true);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (uodateList) {
            uodateList = false;
            initData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        } catch (Exception e) {

        }
    }
}

package com.yunqin.bearmall.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.newversions.tbk.Constants;
import com.newversions.tbk.activity.GoodsDetailActivity;
import com.yunqin.bearmall.BearMallAplication;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.Juhuasuan_Time_Adapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.HotBean;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class JuhuasuanTimeFragment extends BaseFragment {

    @BindView(R.id.rcl)
    RecyclerView rcl;
    @BindView(R.id.n_v_refreshLayout)
    TwinklingRefreshLayout mTwinklingRefreshLayout;
    @BindView(R.id.nulldata)
    ConstraintLayout mNulldata;

    private boolean isLoadMore = false;
    private boolean isFlash = false;
    private Juhuasuan_Time_Adapter productSumAdapter2;
    private int sortType = 0;
    private int page = 1;
    private int pageSize = 10;
    private boolean hasNext = true;
    private int mCid;
    private String mType;


    public static JuhuasuanTimeFragment getInstance(int goodsId, String type) {
        Bundle bundle = new Bundle();
        bundle.putInt("CID", goodsId);
        bundle.putString("TYPE", type);
        JuhuasuanTimeFragment fragment = new JuhuasuanTimeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int layoutId() {
        return R.layout.gragment_hot;
    }

    @Override
    public void init() {
        mCid = getArguments().getInt("CID");
        mType = getArguments().getString("TYPE");
        Log.e("getListData_2", mCid + "");

        mTwinklingRefreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                isFlash = true;
                page = 1;
                productSumAdapter2.cleanList();
                getListData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (hasNext) {
                    isLoadMore = true;
                    page++;
                    getListData();
                } else {
                    mTwinklingRefreshLayout.finishLoadmore();
                }
            }
        });
        rcl.setNestedScrollingEnabled(false);
        rcl.setLayoutManager(new LinearLayoutManager(getActivity()));
        productSumAdapter2 = new Juhuasuan_Time_Adapter(getActivity(), mCid);
        rcl.setAdapter(productSumAdapter2);


        productSumAdapter2.setOnClickProductSumItem(new Juhuasuan_Time_Adapter.onClickProductSumItem2() {
            @Override
            public void onItem(int position, HotBean.CommodityListBean bean) {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra(Constants.INTENT_KEY_ID, bean.getItemId() + "");
                intent.putExtra(Constants.INTENT_KEY_TYPE, Constants.GOODS_TYPE_TBK);
                intent.putExtra(Constants.INTENT_KEY_COMM, bean.getCommision());
                intent.putExtra(Constants.INTENT_KEY_COMMISSION, Constants.COMMISSION_TYPE_ONE);
                startActivity(intent);
            }
        });

        getListData();
        mNulldata.setVisibility(View.VISIBLE);
    }

    private void getListData() {
        showLoading();
        HashMap<String, String> map = new HashMap<>();
        map.put("sortType", String.valueOf(sortType));//排序规则
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(pageSize));
        map.put("type", mType);
        int jhsType = mCid + 1;
        switch (mType) {
            case "5":
                map.put("jhsType", jhsType + "");
                break;
        }

        if (BearMallAplication.getInstance().getUser() != null && BearMallAplication.getInstance().getUser().getData() != null && BearMallAplication.getInstance().getUser().getData().getToken() != null && BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() != null) {
            map.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
        }
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getSecondHotSelling(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                Log.e("getSecondHotSelling", data);
                HotBean searchData = new Gson().fromJson(data, HotBean.class);
                if (searchData != null && searchData.getCommodityList() != null && searchData.getCommodityList().size() > 0) {
                    productSumAdapter2.addList(searchData.getCommodityList());
                    mNulldata.setVisibility(View.GONE);
                }

                if (isFlash) {
                    mTwinklingRefreshLayout.finishRefreshing();
                    isFlash = false;
                }
                if (isLoadMore) {
                    mTwinklingRefreshLayout.finishLoadmore();
                    isLoadMore = false;
                }

                hiddenLoadingView();
            }

            @Override
            public void onNotNetWork() {
                hiddenLoadingView();
                mNulldata.setVisibility(View.VISIBLE);
                if (isFlash) {
                    mTwinklingRefreshLayout.finishRefreshing();
                    isFlash = false;
                }
                if (isLoadMore) {
                    mTwinklingRefreshLayout.finishLoadmore();
                    isLoadMore = false;
                }
            }

            @Override
            public void onFail(Throwable e) {
                mNulldata.setVisibility(View.VISIBLE);
                hiddenLoadingView();
                if (isFlash) {
                    mTwinklingRefreshLayout.finishRefreshing();
                    isFlash = false;
                }
                if (isLoadMore) {
                    mTwinklingRefreshLayout.finishLoadmore();
                    isLoadMore = false;
                }
            }
        });


    }
}

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
import com.yunqin.bearmall.adapter.HotAdapter2;
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

public class HotFragment extends BaseFragment {

    @BindView(R.id.rcl)
    RecyclerView rcl;
    @BindView(R.id.n_v_refreshLayout)
    TwinklingRefreshLayout mTwinklingRefreshLayout;
    @BindView(R.id.nulldata)
    ConstraintLayout mNulldata;
    Unbinder unbinder;

    private boolean isLoadMore = false;
    private boolean isFlash = false;
    private HotAdapter2 productSumAdapter2;
    private int sortType = 0;
    private int page = 1;
    private int pageSize = 10;
    private boolean hasNext = true;
    private int mCid;
    private String mType;


    public static HotFragment getInstance(int goodsId, String type) {
        Bundle bundle = new Bundle();
        bundle.putInt("CID", goodsId);
        bundle.putString("TYPE", type);
        HotFragment fragment = new HotFragment();
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
                switch (mType) {
                    case "2":
                        switch (mCid) {
                            case 0:
                                getListData();
                                break;
                            default:
                                getListData2();
                                break;
                        }
                        break;
                    default:
                        getListData();
                        break;
                }
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (hasNext) {
                    isLoadMore = true;
                    page++;
                    switch (mType) {
                        case "2":
                            switch (mCid) {
                                case 0:
                                    getListData();
                                    break;
                                default:
                                    getListData2();
                                    break;
                            }
                            break;
                        default:
                            getListData();
                            break;
                    }
                } else {
                    mTwinklingRefreshLayout.finishLoadmore();
                }
            }
        });
        rcl.setNestedScrollingEnabled(false);
        rcl.setLayoutManager(new LinearLayoutManager(getActivity()));
        productSumAdapter2 = new HotAdapter2(getActivity());
        rcl.setAdapter(productSumAdapter2);


        productSumAdapter2.setOnClickProductSumItem(new HotAdapter2.onClickProductSumItem2() {
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

        switch (mType) {
            case "2":
                switch (mCid) {
                    case 0:
                        getListData();
                        break;
                    default:
                        getListData2();
                        break;
                }
                break;
            default:
                getListData();
                break;
        }

        mNulldata.setVisibility(View.VISIBLE);
    }

    private void getListData() {
        showLoading();
        HashMap<String, String> map = new HashMap<>();
        map.put("sortType", String.valueOf(sortType));//排序规则
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(pageSize));
        if (mType.equals("4")) {
            map.put("cid", "0");
            if (mCid != 0) {
                map.put("category", mCid + "");
            }
        } else {
            map.put("cid", mCid + "");
        }
        map.put("type", mType);
        if (BearMallAplication.getInstance().getUser() != null && BearMallAplication.getInstance().getUser().getData() != null && BearMallAplication.getInstance().getUser().getData().getToken() != null && BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token() != null) {
            map.put("access_token", BearMallAplication.getInstance().getUser().getData().getToken().getAccess_token());
        }
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getHotSelling(map), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
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


    private void getListData2() {
        showLoading();
        HashMap<String, String> map = new HashMap<>();
        map.put("sortType", String.valueOf(sortType));//排序规则
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(pageSize));
        map.put("type", mType);
        switch (mType) {
            case "2":
                map.put("tmallType", mCid + "");
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

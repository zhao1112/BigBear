package com.yunqin.bearmall.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.newversions.MemberMallActivity;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.CouponListAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.Coupon;
import com.yunqin.bearmall.bean.CouponResponse;
import com.yunqin.bearmall.ui.activity.ChargeActivity;
import com.yunqin.bearmall.util.ToastUtils;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class CouponListFragment extends BaseFragment implements CouponListAdapter.OnItemClickListener {

    public static CouponListFragment instance(int position, int type) {
        CouponListFragment couponListFragment = new CouponListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
        bundle.putInt("TYPE", type);
        couponListFragment.setArguments(bundle);
        return couponListFragment;
    }


    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout refreshLayout;

    @BindView(R.id.coupon_list)
    RecyclerView recyclerView;

    private int position;
    private int ticketType = -1;
    private int pageIndex = 1;
    private boolean isloadMore;
    private CouponListAdapter adapter;
    private OnLoadedCouponDataListener onLoadedCouponDataListener;
    private List<Coupon> datas = new ArrayList<>();

    public CouponListFragment setOnLoadedCouponDataListener(OnLoadedCouponDataListener onLoadedCouponDataListener) {
        this.onLoadedCouponDataListener = onLoadedCouponDataListener;
        return this;
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_counpon_list;
    }

    @Override
    public void init() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("POSITION");
            ticketType = bundle.getInt("TYPE", -1);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        refreshLayout.setHeaderView(new RefreshHeadView(getContext()));
        refreshLayout.setBottomView(new RefreshBottomView(getContext()));
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                isloadMore = false;
                pageIndex = 1;
                loadData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                isloadMore = true;
                loadData();
            }
        });


        refreshLayout.startRefresh();


    }


    private void loadData() {

        Map<String, String> params = new HashMap<>();
        params.put("queryType", position + "");
        params.put("page_size", "20");
        params.put("page_number", pageIndex + "");
        if (ticketType != -1) {
            params.put("ticketType", ticketType + "");
        }

        RetrofitApi.request(getContext(), RetrofitApi.createApi(Api.class).getMemberTicketDetails(params), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                if (isloadMore) {
                    refreshLayout.finishLoadmore();
                } else {
                    refreshLayout.finishRefreshing();
                }
                CouponResponse response = new Gson().fromJson(data, CouponResponse.class);
                CouponResponse.DataBean dataBean = response.getData();
                int total = dataBean.getTotalCount();
                if (onLoadedCouponDataListener != null) {
                    onLoadedCouponDataListener.onLoadedData(position, total);
                }
                int hasMore = dataBean.getHas_more();
                if (hasMore == 1) {
                    pageIndex++;
                    refreshLayout.setEnableLoadmore(true);
                } else {
                    refreshLayout.setEnableLoadmore(false);
                }
                List<Coupon> coupons = dataBean.getUsersTicketList();
                if (isloadMore) {
                    datas.addAll(coupons);
                    adapter.notifyDataSetChanged();
                } else {
                    datas.clear();
                    datas.addAll(coupons);
                    if (adapter == null) {
                        adapter = new CouponListAdapter(getActivity(), datas, CouponListFragment.this);
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onNotNetWork() {
                if (isloadMore) {
                    refreshLayout.finishLoadmore();
                } else {
                    refreshLayout.finishRefreshing();
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (isloadMore) {
                    refreshLayout.finishLoadmore();
                } else {
                    refreshLayout.finishRefreshing();
                }
            }
        });

    }

    @Override
    public void onClick(int position, int clickTicketType, Coupon couponGet) {
        if (!couponGet.isUsable()) {
            ToastUtils.showToast(getContext(), "当前优惠券不在可用期内");
            return;
        }
        //这个一般为点击后返回优惠券信息
        if (this.position == 0 && this.ticketType != -1) {
            Coupon coupon = datas.get(position);
            Intent intent = new Intent();
            intent.putExtra("COUPON", coupon);
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        } else if (this.position == 0) {
            //点击可能是跳转  0 现金  1 话费
            if (clickTicketType == 0) {
                startActivity(new Intent(getActivity(), MemberMallActivity.class));
            } else {
                startActivity(new Intent(getActivity(), ChargeActivity.class));
            }

        }

    }


    public interface OnLoadedCouponDataListener {

        public void onLoadedData(int position, int totalCount);

    }


}

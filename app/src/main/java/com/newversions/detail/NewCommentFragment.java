package com.newversions.detail;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create By Master
 * On 2019/1/10 15:02
 */
public class NewCommentFragment extends BaseFragment {


    @BindView(R.id.empty_view)
    View view;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout twinklingRefreshLayout;
    @BindView(R.id.list_view)
    ListView list_view;

    private List<NewReviewListBean.DataBean.ReviewListBean> mReviewList = new ArrayList<>();
    private int page_numer = 1;
    private boolean onHasMore = false;
    private FragmentToActivityInter fragmentToActivityInter;
    private NewVersionProductCommentAdapter productCommentAdapter;
    private String product_id;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentToActivityInter) {
            fragmentToActivityInter = (FragmentToActivityInter) context;
        }
    }

    @Override
    public int layoutId() {
        return R.layout.new_version_product_fragment_pinglun;
    }

    @Override
    public void init() {

        product_id = getArguments().getString("productId");

        twinklingRefreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
        twinklingRefreshLayout.setBottomView(new RefreshBottomView(getActivity()));
        productCommentAdapter = new NewVersionProductCommentAdapter(getActivity(), mReviewList);
        list_view.setEmptyView(view);
        list_view.setAdapter(productCommentAdapter);
        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                shuaxin();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (onHasMore) {
                    jiazaigengduo();
                } else {
                    refreshLayout.finishLoadmore();
                }

            }
        });
        shuaxin();
    }

    private void shuaxin() {
        page_numer = 1;
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_number", String.valueOf(page_numer));
        mHashMap.put("product_id", product_id);

        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getProductReviewList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                twinklingRefreshLayout.finishRefreshing();
                NewReviewListBean newReviewListBean = new Gson().fromJson(data, NewReviewListBean.class);

                if (newReviewListBean.getData().getHas_more() == 0) {
                    onHasMore = false;
                    twinklingRefreshLayout.setEnableLoadmore(false);
                    productCommentAdapter.setData(newReviewListBean.getData().getReviewList());
                } else {
                    onHasMore = true;
                    twinklingRefreshLayout.setEnableLoadmore(true);
                    productCommentAdapter.setData(newReviewListBean.getData().getReviewList());
                }

            }

            @Override
            public void onNotNetWork() {
                twinklingRefreshLayout.finishRefreshing();
            }

            @Override
            public void onFail(Throwable e) {
                twinklingRefreshLayout.finishRefreshing();
            }
        });

    }

    private void jiazaigengduo() {
        page_numer++;
        Map<String, String> mHashMap = new HashMap<>();
        mHashMap.put("page_number", String.valueOf(page_numer));
        mHashMap.put("product_id", product_id);

        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getProductReviewList(mHashMap), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) throws JSONException {
                twinklingRefreshLayout.finishLoadmore();

                NewReviewListBean newReviewListBean = new Gson().fromJson(data, NewReviewListBean.class);

                if (newReviewListBean.getData().getHas_more() == 0) {
                    onHasMore = false;
                    twinklingRefreshLayout.setEnableLoadmore(false);
                    productCommentAdapter.addData(newReviewListBean.getData().getReviewList());
                } else {
                    onHasMore = true;
                    twinklingRefreshLayout.setEnableLoadmore(true);
                }


            }

            @Override
            public void onNotNetWork() {
                twinklingRefreshLayout.finishLoadmore();
            }

            @Override
            public void onFail(Throwable e) {
                twinklingRefreshLayout.finishLoadmore();
            }
        });
    }


    @OnClick(R.id.new_version_pinglun_back)
    public void viewClick(View view) {
        switch (view.getId()) {
            case R.id.new_version_pinglun_back:
                fragmentToActivityInter.chooseFragment(true);
                break;
        }
    }


}

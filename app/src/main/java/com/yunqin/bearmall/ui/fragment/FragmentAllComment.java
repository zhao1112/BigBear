package com.yunqin.bearmall.ui.fragment;

import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.adapter.AllCommentAdapter;
import com.yunqin.bearmall.api.Api;
import com.yunqin.bearmall.api.RetrofitApi;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.bean.CommentBean;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @author AYWang
 * @create 2018/7/27
 * @Describe
 */
public class FragmentAllComment extends BaseFragment {

    @BindView(R.id.empty)
    LinearLayout empty;

    @BindView(R.id.list_view)
    ListView list_view;

    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

    private Map map = new HashMap();
    private int page_numer = 1;
    private int isLoadMoreOrRefresh = 1;
    private AllCommentAdapter allCommentAdapter;

    @Override
    public int layoutId() {
        return R.layout.fragment_all_comment;
    }

    @Override
    public void init() {
        allCommentAdapter = new AllCommentAdapter(getActivity());
        list_view.setAdapter(allCommentAdapter);
        list_view.setEmptyView(empty);
        initRefresh();
    }

    private void initRefresh() {
        refreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
        refreshLayout.setBottomView(new RefreshBottomView(getActivity()));
//        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableOverScroll(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page_numer = 1;
                isLoadMoreOrRefresh = 1;
                getAllCommentList();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                ++page_numer;
                isLoadMoreOrRefresh = 2;
                getAllCommentList();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLayout.startRefresh();
    }

    //得到全部的商品评价
    private void getAllCommentList() {
        RetrofitApi.request(getActivity(), RetrofitApi.createApi(Api.class).getMemberReviewList(getParams()), new RetrofitApi.IResponseListener() {
            @Override
            public void onSuccess(String data) {
                onFinishRe();
                CommentBean commentBean = new Gson().fromJson(data, CommentBean.class);
                if (commentBean.getData().getHas_more() == 0) {
                    refreshLayout.setEnableLoadmore(false);
                } else {
                    refreshLayout.setEnableLoadmore(true);
                }
                if(isLoadMoreOrRefresh == 1){
                    allCommentAdapter.setData(commentBean.getData().getReviewList());
                }else {
                    allCommentAdapter.addData(commentBean.getData().getReviewList());
                }
            }

            @Override
            public void onNotNetWork() {
                onFinishRe();
            }

            @Override
            public void onFail(Throwable e) {
                onFinishRe();
            }
        });
    }
    public void onFinishRe() {
        refreshLayout.finishLoadmore();
        refreshLayout.finishRefreshing();
    }

    private Map getParams() {
        map.clear();
        map.put("page_number", page_numer + "");
        map.put("hasImages",0+"");
        return map;
    }

}

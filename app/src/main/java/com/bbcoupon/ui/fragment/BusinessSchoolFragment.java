package com.bbcoupon.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bbcoupon.ui.activity.ArticleActivity;
import com.bbcoupon.ui.activity.ArticleListTwoActivity;
import com.bbcoupon.ui.activity.ArticleSearchActivity;
import com.bbcoupon.ui.adapter.SchoolAdapter;
import com.bbcoupon.ui.bean.SchoolInfor;
import com.bbcoupon.ui.contract.RequestContract;
import com.bbcoupon.ui.presenter.RequestPresenter;
import com.bbcoupon.widget.RefreshSchoolView;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseFragment;
import com.yunqin.bearmall.widget.RefreshBottomView;
import com.yunqin.bearmall.widget.RefreshHeadView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author LWP
 * @PACKAGE com.bbcoupon.ui.fragment
 * @DATE 2020/6/1
 */
public class BusinessSchoolFragment extends BaseFragment implements RequestContract.RequestView {

    @BindView(R.id.sc_recycler)
    RecyclerView mScRecycler;
    @BindView(R.id.sc_refresh)
    TwinklingRefreshLayout mScRefresh;

    private SchoolAdapter schoolAdapter;
    private RequestPresenter presenter;
    private int page = 1;

    @Override
    public int layoutId() {
        return R.layout.fragment_b_school;
    }

    @Override
    public void init() {

        presenter = new RequestPresenter();
        presenter.setRelation(this);

        mScRefresh.setHeaderView(new RefreshHeadView(getActivity()));
        mScRefresh.setBottomView(new RefreshBottomView(getActivity()));
        mScRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                schoolAdapter.deleteDataList();
                page = 1;
                setList(page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                page++;
                setList(page);
            }
        });

        mScRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        schoolAdapter = new SchoolAdapter(getActivity());
        mScRecycler.setAdapter(schoolAdapter);

        //文章詳情
        schoolAdapter.setOnArticle(new SchoolAdapter.OnArticle() {
            @Override
            public void setArticle(int id) {
                Bundle bundle = new Bundle();
                bundle.putString("ARTICLEID", id + "");
                ArticleActivity.openArticleActivity(getActivity(),ArticleActivity.class,bundle);
            }

            @Override
            public void setArticelIcon(int id, String title) {
                Bundle bundle = new Bundle();
                bundle.putString("ARTICLEID", id + "");
                bundle.putString("ARTICLETITLE", title);
                ArticleListTwoActivity.openArticleListTwoActivity(getActivity(), ArticleListTwoActivity.class, bundle);
            }

            @Override
            public void setBannerId(int id) {
                Bundle bundle = new Bundle();
                bundle.putString("ARTICLEID", id + "");
                ArticleActivity.openArticleActivity(getActivity(),ArticleActivity.class,bundle);
            }
        });

        setList(page);
    }


    @OnClick(R.id.art_search)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), ArticleSearchActivity.class));
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof SchoolInfor) {
            SchoolInfor schoolInfor = (SchoolInfor) data;
            if (schoolInfor != null) {
                if (page == 1) {
                    schoolAdapter.addDataList(schoolInfor);
                } else {
                    if (schoolInfor.getData3() != null && schoolInfor.getData3().size() > 0) {
                        mScRefresh.setBottomView(new RefreshBottomView(getActivity()));
                        schoolAdapter.addBottomList(schoolInfor.getData3());
                    } else {
                        mScRefresh.setBottomView(new RefreshSchoolView(getActivity()));
                    }
                }
            }
        }
        hiddenLoadingView();
        mScRefresh.finishRefreshing();
        mScRefresh.finishLoadmore();
    }

    @Override
    public void onNotNetWork() {
        hiddenLoadingView();
        mScRefresh.finishRefreshing();
        mScRefresh.finishLoadmore();
    }

    @Override
    public void onFail(Throwable e) {
        hiddenLoadingView();
        mScRefresh.finishRefreshing();
        mScRefresh.finishLoadmore();
    }

    public void setList(int page) {
        showLoading();
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("pageSize", "10");
        presenter.onAllArticleList(getActivity(), map);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.setUntying(this);
    }
}
